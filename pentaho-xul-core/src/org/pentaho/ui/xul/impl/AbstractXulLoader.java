package org.pentaho.ui.xul.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.util.ResourceBundleTranslator;

public abstract class AbstractXulLoader implements XulLoader {

  protected XulParser parser;

  protected String rootDir = "/";

  protected Object outerContext = null;

  private static final Log logger = LogFactory.getLog(AbstractXulLoader.class);

  private ResourceBundle mainBundle = null;

  public AbstractXulLoader() throws XulException {

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);

    try {
      parser = new XulParser();
    } catch (Exception e) {
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: " + e.getMessage(), e);
    }

  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Object xulDocument) throws IllegalArgumentException, XulException {
    Document document = (Document)xulDocument;
    try {
      xulDocument = preProcess((Document)xulDocument);

      String processedDoc = performIncludeTranslations(document.asXML());
      String localOutput = (mainBundle != null) ? ResourceBundleTranslator.translate(processedDoc, mainBundle)
          : processedDoc;
      
      SAXReader rdr = new SAXReader();
      //localOutput = localOutput.replace("UTF-8", "ISO-8859-1");
      

//      System.out.println("============ Post Processed: ============");
//      System.out.println(localOutput);
//      System.out.println("============ End Post Processed: ============");
      
      
      final Document doc = rdr.read(new StringReader(localOutput));

//      System.out.println("============ After Parse: ============");
//      System.out.println(doc.asXML());
//      System.out.println("============ After Parse: ============");
      
      
      XulDomContainer container = new XulWindowContainer(this);
      container.setOuterContext(outerContext);
      parser.setContainer(container);
      parser.parseDocument(doc.getRootElement());

      return container;

    } catch (Exception e) {
      throw new XulException(e);
    }
  }

  public void setRootDir(String loc) {
    if(!rootDir.equals("/")){      //lets only set this once
      return;
    }
    if (loc.lastIndexOf("/") > 0 && loc.indexOf(".xul") > -1) { //exists and not first char
      rootDir = loc.substring(0, loc.lastIndexOf("/") + 1);
    } else {
      rootDir = loc;
      if (!(loc.lastIndexOf('/') == loc.length())) { //no trailing slash, add it
        rootDir = rootDir + "/";
      }
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Object xulDocument) throws IllegalArgumentException, XulException {
    Document document = (Document)xulDocument;
    XulDomContainer container = new XulFragmentContainer(this);
    container.setOuterContext(outerContext);

    parser.reset();
    parser.setContainer(container);
    parser.parseDocument(document.getRootElement());

    return container;
  }

  public XulComponent createElement(String elementName) throws XulException {
    return parser.getElement(elementName);
  }

  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException {

    Document doc = getDocFromClasspath(resource);

    setRootDir(resource);

    ResourceBundle res;
    try {
      res = ResourceBundle.getBundle(resource.replace(".xul", ""));
    } catch (MissingResourceException e) {
      return loadXul(doc);
    }

    return loadXul(resource, res);

  }

  public XulDomContainer loadXul(String resource, Object bundle) throws XulException {

    final Document doc = getDocFromClasspath(resource);

    setRootDir(resource);
    mainBundle = (ResourceBundle) bundle;
    return this.loadXul(doc);

  }

  public XulDomContainer loadXulFragment(String resource) throws IllegalArgumentException, XulException {

    Document doc = getDocFromClasspath(resource);

    setRootDir(resource);

    ResourceBundle res;
    try {
      res = ResourceBundle.getBundle(resource.replace(".xul", ""));
    } catch (MissingResourceException e) {
      return loadXulFragment(doc);
    }
    return loadXulFragment(resource, res);
  }

  public XulDomContainer loadXulFragment(String resource, Object bundle) throws XulException {

    try {

      InputStream in = getClass().getClassLoader().getResourceAsStream(resource);

      String localOutput = ResourceBundleTranslator.translate(in, (ResourceBundle) bundle);

      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read(new StringReader(localOutput));

      setRootDir(resource);

      return this.loadXulFragment(doc);
    } catch (DocumentException e) {
      throw new XulException("Error parsing Xul Document", e);
    } catch (IOException e) {
      throw new XulException("Error loading Xul Document into Freemarker", e);
    }
  }

  private List<String> includedSources = new ArrayList<String>();

  private List<String> resourceBundles = new ArrayList<String>();

  public String performIncludeTranslations(String input) throws XulException {
    String output = input;
    for (String includeSrc : includedSources) {
      try {
        ResourceBundle res = ResourceBundle.getBundle(includeSrc.replace(".xul", ""));
        output = ResourceBundleTranslator.translate(output, res);
      } catch (MissingResourceException e) {
        continue;
      } catch (IOException e) {

        throw new XulException(e);
      }
    }
    for (String resource : resourceBundles) {
      logger.info("Processing Resource Bundle: " + resource);
      try {
        ResourceBundle res = ResourceBundle.getBundle(resource);
        if (res == null) {
          continue;
        }
        output = ResourceBundleTranslator.translate(output, res);
      } catch (MissingResourceException e) {
        continue;
      } catch (IOException e) {

        throw new XulException(e);
      }
    }
    return output;
  }

  public void register(String tagName, String className) {
    parser.registerHandler(tagName, className);
  }

  public String getRootDir() {
    return this.rootDir;
  }

  public Document preProcess(Document srcDoc) throws XulException {

    XPath xpath = new DefaultXPath("//pen:include");

    HashMap uris = new HashMap();
    uris.put("xul", "http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul");
    uris.put("pen", "http://www.pentaho.org/2008/xul");

    xpath.setNamespaceURIs(uris);

    List<Element> eles = xpath.selectNodes(srcDoc);

    for (Element ele : eles) {
      String src = "";

      src = this.getRootDir() + ele.attributeValue("src");

      String resourceBundle = ele.attributeValue("resource");
      if (resourceBundle != null) {
        resourceBundles.add(resourceBundle);
      }

      InputStream in = getClass().getClassLoader().getResourceAsStream(src);

      if (in != null) {
        logger.info("Adding include src: " + src);
        includedSources.add(src);
      } else {
        //try fully qualified name
        src = ele.attributeValue("src");
        in = getClass().getClassLoader().getResourceAsStream(src);
        if (in != null) {
          includedSources.add(src);
          logger.info("Adding include src: " + src);
        } else {
          logger.error("Could not resolve include: " + src);
        }

      }

      final Document doc = getDocFromInputStream(in);

      Element root = doc.getRootElement();
      String ignoreRoot = ele.attributeValue("ignoreroot");
      if (root.getName().equals("overlay")) {
        processOverlay(root, ele.getDocument().getRootElement());
      } else if (ignoreRoot == null || ignoreRoot.equalsIgnoreCase("false")) {
        logger.info("Including entire file: " + src);
        List contentOfParent = ele.getParent().content();
        int index = contentOfParent.indexOf(ele);
        contentOfParent.set(index, root);

        //process any overlay children
        List<Element> overlays = ele.elements();
        for (Element overlay : overlays) {
          logger.info("Processing overlay within include");

          this.processOverlay(overlay.attributeValue("src"), srcDoc);
        }
      } else {
        logger.info("Including children: " + src);
        List contentOfParent = ele.getParent().content();
        int index = contentOfParent.indexOf(ele);
        contentOfParent.remove(index);
        List children = root.elements();
        for (int i = children.size() - 1; i >= 0; i--) {
          contentOfParent.add(index, children.get(i));
        }

        //process any overlay children
        List<Element> overlays = ele.elements();
        for (Element overlay : overlays) {
          logger.info("Processing overlay within include");

          this.processOverlay(overlay.attributeValue("src"), srcDoc);
        }
      }
    }

    return srcDoc;
  }

  private Document getDocFromInputStream(InputStream in) throws XulException {
    try {

      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      StringBuffer buf = new StringBuffer();
      String line;
      while ((line = reader.readLine()) != null) {
        buf.append(line);
      }
      in.close();
      
      String upperedIdDoc = this.upperCaseIDAttrs(buf.toString());
      SAXReader rdr = new SAXReader();
      return rdr.read(new StringReader(upperedIdDoc));
    } catch (Exception e) {
      throw new XulException(e);
    }
  }

  private Document getDocFromClasspath(String src) throws XulException {
    InputStream in = getClass().getClassLoader().getResourceAsStream(this.getRootDir() + src);
    if (in != null) {
      Document doc = getDocFromInputStream(in);
      return doc;
    } else {
      //try fully qualified name
      in = getClass().getClassLoader().getResourceAsStream(src);
      if (in != null) {
        return getDocFromInputStream(in);
      } else {
        throw new XulException("Can no locate Xul document [" + src + "]");
      }
    }
  }

  private void processOverlay(String overlaySrc, Document doc) {
    try {
      final Document overlayDoc = getDocFromClasspath(overlaySrc);
      processOverlay(overlayDoc.getRootElement(), doc.getRootElement());
    } catch (Exception e) {
      logger.error("Could not load include overlay document: " + overlaySrc, e);
    }
  }

  private void processOverlay(Element overlayEle, Element srcEle) {
    for (Object child : overlayEle.elements()) {
      Element overlay = (Element) child;
      String overlayId = overlay.attributeValue("ID");
      logger.info("Processing overlay\nID: " + overlayId);
      Element sourceElement = srcEle.getDocument().elementByID(overlayId);
      if (sourceElement == null) {
        logger.error("Could not find corresponding element in src doc with id: " + overlayId);
        continue;
      }
      logger.info("Found match in source doc:");

      String removeElement = overlay.attributeValue("removeelement");
      if (removeElement != null && removeElement.equalsIgnoreCase("true")) {
        sourceElement.getParent().remove(sourceElement);
      } else {
        
        List attribs = overlay.attributes();
        
        //merge in attributes
        for(Object o : attribs){
          Attribute atr = (Attribute) o;
          sourceElement.addAttribute(atr.getName(), atr.getValue());
        }
        
        Document targetDocument = srcEle.getDocument();

        //lets start out by just including everything
        for (Object overlayChild : overlay.elements()) {
          Element pluckedElement = (Element) overlay.content().remove(overlay.content().indexOf(overlayChild));
          
          

          String insertBefore = pluckedElement.attributeValue("insertbefore");
          String insertAfter = pluckedElement.attributeValue("insertafter");
          String position = pluckedElement.attributeValue("position");
          
          
          //determine position to place it
          int positionToInsert = -1;
          if(insertBefore != null){
            Element insertBeforeTarget = sourceElement.elementByID(insertBefore);
            positionToInsert = sourceElement.elements().indexOf(insertBeforeTarget);
            
          } else if(insertAfter != null){
            Element insertAfterTarget = sourceElement.elementByID(insertAfter);
            positionToInsert = sourceElement.elements().indexOf(insertAfterTarget);
            if(positionToInsert != -1){
              positionToInsert++; //we want to be after that point;
            }
          } else if(position != null){
            int pos = Integer.parseInt(position);
            positionToInsert = (pos <= sourceElement.elements().size()) ? pos : -1;
          }
          if(positionToInsert == -1){
            //default to last
            positionToInsert = sourceElement.elements().size();
          }
          if(positionToInsert > sourceElement.elements().size()){
            sourceElement.elements().add(pluckedElement);
          } else {
            sourceElement.elements().add(positionToInsert, pluckedElement);
          }
          logger.info("processed overlay child: " + ((Element) overlayChild).getName() + " : "
              + pluckedElement.getName());
        }
      }
    }
  }
  
  private InputStream getInputStreamForSrc(String src){
    InputStream in = getClass().getClassLoader().getResourceAsStream(this.getRootDir() + src);
    if (in == null){
      //try fully qualified name
      in = getClass().getClassLoader().getResourceAsStream(src);
      if (in == null) {
        logger.error("Cant find overlay source");
      }
    }
    return in;
  }

  public void processOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container) throws XulException {

    InputStream in = getInputStreamForSrc(overlaySrc);
    Document doc = null;
    ResourceBundle res = null;
    try {
      res = ResourceBundle.getBundle(overlaySrc.replace(".xul", ""));
      if(res == null){
        res = ResourceBundle.getBundle((this.getRootDir() + overlaySrc).replace(".xul", ""));
        if(res == null){
          logger.error("could not find resource bundle, defaulting to main");
          res = mainBundle;
        }
      }
    } catch (MissingResourceException e) {
      logger.warn("no default resource bundle available: "+overlaySrc);
    }
    
    String runningTranslatedOutput = getDocFromInputStream(in).asXML();     //TODO IOUtils this
    if(res != null){
      try{
        runningTranslatedOutput = ResourceBundleTranslator.translate(runningTranslatedOutput, res);
  
       } catch(IOException e){
        logger.error("Error loading resource bundle for overlay: "+overlaySrc, e);
      }
    }
      
    //check for top-level message bundle and apply it
    if(this.mainBundle != null){
      try{
          
        runningTranslatedOutput = ResourceBundleTranslator.translate(runningTranslatedOutput, this.mainBundle);
        try{
          SAXReader rdr = new SAXReader();
          String upperedIdDoc = this.upperCaseIDAttrs(runningTranslatedOutput.toString());
          doc = rdr.read(new StringReader(upperedIdDoc)); 
        } catch(DocumentException e){
          logger.error("Error loading XML while applying top level message bundle to overlay file:", e);
        }
      } catch(IOException e){
        logger.error("Error loading Resource Bundle File to apply to overlay: ",e);
      }
    } else {
      try{
        SAXReader rdr = new SAXReader();
        String upperedIdDoc = this.upperCaseIDAttrs(runningTranslatedOutput.toString());
        doc = rdr.read(new StringReader(upperedIdDoc)); 
      } catch(DocumentException e){
        logger.error("Error loading XML while applying top level message bundle to overlay file:", e);
      }
    } 
    
  
    
    
    Element overlayRoot = doc.getRootElement();

    for (Object child : overlayRoot.elements()) {
      Element overlay = (Element) child;
      String overlayId = overlay.attributeValue("ID");

      org.pentaho.ui.xul.dom.Element sourceElement = targetDocument.getElementById(overlayId);
      if (sourceElement == null) {
        logger.warn("Cannot overlay element with id [" + overlayId + "] as it does not exist in the target document.");
        continue;
      }

      for (Object childToParse : overlay.elements()) {
        Element childElement = (Element) childToParse;
        
        logger.info("Processing overlay on element with id: " + overlayId);
        parser.reset();
        parser.setContainer(container);
        XulComponent c = parser.parse(childElement, (XulContainer) sourceElement);
        
        String insertBefore = childElement.attributeValue("insertbefore");
        String insertAfter = childElement.attributeValue("insertafter");
        String position = childElement.attributeValue("position");
        
        XulContainer sourceContainer = ((XulContainer) sourceElement);
        
        //determine position to place it
        int positionToInsert = -1;
        if(insertBefore != null){
          org.pentaho.ui.xul.dom.Element insertBeforeTarget = targetDocument.getElementById(insertBefore);
          positionToInsert = sourceContainer.getChildNodes().indexOf(insertBeforeTarget);
        } else if(insertAfter != null){
          org.pentaho.ui.xul.dom.Element insertAfterTarget = targetDocument.getElementById(insertAfter);
          positionToInsert = sourceContainer.getChildNodes().indexOf(insertAfterTarget);
          if (positionToInsert != -1)
          {
            positionToInsert += 1;
          }
        } else if(position != null){
          int pos = Integer.parseInt(position);
          positionToInsert = (pos <= sourceContainer.getChildNodes().size()) ? pos : -1;
        }
        if(positionToInsert == -1 || positionToInsert == sourceContainer.getChildNodes().size()) {
        	//default to previous behavior
          sourceContainer.addChild(c);
        } else {
        	sourceContainer.addChildAt(c, positionToInsert);
        }
        
        logger.info("added child: " + c);
      }
      
      List attribs = overlay.attributes();
      
      //merge in attributes
      for(Object o : attribs){
        Attribute atr = (Attribute) o;
        try{
          BeanUtils.setProperty(sourceElement, atr.getName(), atr.getValue());
          sourceElement.setAttribute(atr.getName(), atr.getValue());
        } catch(InvocationTargetException e){
          logger.error(e);
        } catch(IllegalAccessException e){
          logger.error(e);
        }
      }

    }

  }

  public void removeOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument, XulDomContainer container)
      throws XulException {

    final Document doc = getDocFromClasspath(overlaySrc);

    Element overlayRoot = doc.getRootElement();

    for (Object child : overlayRoot.elements()) {
      Element overlay = (Element) child;

      for (Object childToParse : overlay.elements()) {
        String childId = ((Element) childToParse).attributeValue("ID");

        org.pentaho.ui.xul.dom.Element prevOverlayedEle = targetDocument.getElementById(childId);
        if (prevOverlayedEle == null) {
          logger.info("Source Element from target document is null: " + childId);
          continue;
        }

        prevOverlayedEle.getParent().removeChild(prevOverlayedEle);

      }

    }
  }

  private String upperCaseIDAttrs(String src) {

    String result = src.replace(" id=", " ID=");
    return result;

  }

  public void setOuterContext(Object context) {
    outerContext = context;
  }

  public boolean isRegistered(String elementName) {
    return this.parser.handlers.containsKey(elementName);
  }

}
