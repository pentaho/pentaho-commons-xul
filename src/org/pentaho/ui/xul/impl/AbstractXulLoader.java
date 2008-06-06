package org.pentaho.ui.xul.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

public abstract class AbstractXulLoader implements XulLoader{

  protected XulParser parser;
  protected String rootDir = "/";
  protected Object outerContext = null;
  private static final Log logger = LogFactory.getLog(AbstractXulLoader.class);
  
  private ResourceBundle mainBundle = null;
  
  public AbstractXulLoader() throws XulException{

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    
    try{
      parser = new XulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
     
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException{
    
    try{
      xulDocument = preProcess(xulDocument);
  
      String processedDoc = performIncludeTranslations(xulDocument.asXML());
      String localOutput = (mainBundle != null)
        ? ResourceBundleTranslator.translate(processedDoc, mainBundle)
        : processedDoc;
      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read(new StringReader(localOutput));
      
      XulDomContainer container = new XulWindowContainer(this);
      container.setOuterContext(outerContext);
      parser.setContainer(container);
      parser.parseDocument(doc.getRootElement());
     
      return container;
    
    } catch(Exception e){
      throw new XulException(e);
    }
  }
  
  private void setRootDir(String loc){
    if(loc.lastIndexOf("/") > 0){ //exists and not first char
      rootDir = loc.substring(0,loc.lastIndexOf("/")+1);
    }
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException {
    XulDomContainer container = new XulFragmentContainer(this);
    container.setOuterContext(outerContext);
    
    parser.reset();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    return container;
  }
  
  public XulComponent createElement(String elementName) throws XulException{
    return parser.getElement(elementName);
  }
  
  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException{

      InputStream in = getClass().getClassLoader().getResourceAsStream(resource);

      if(in == null){
        throw new IllegalArgumentException("File not found");
      }

      setRootDir(resource);
      
      ResourceBundle res;
      try{
        res = ResourceBundle.getBundle(resource.replace(".xul", ""));
      } catch(MissingResourceException e){
        try{
            SAXReader rdr = new SAXReader();
            final Document doc = rdr.read(in);
            return loadXul(doc);
        } catch(DocumentException ex){
            throw new XulException("Error parsing Xul Document", ex);
        }
      }
      
      return loadXul(resource, res);
      
  }
  
  public XulDomContainer loadXul(String resource, ResourceBundle bundle) throws  XulException{
    
      try{

        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        
        SAXReader rdr = new SAXReader();
        final Document doc = rdr.read(in);
       

        setRootDir(resource);
        mainBundle = bundle;
        return this.loadXul(doc);
      } catch(DocumentException e){
        throw new XulException("Error parsing Xul Document", e);
      }
  }
  
  public XulDomContainer loadXulFragment(String resource) throws IllegalArgumentException, XulException{

      InputStream in = getClass().getClassLoader().getResourceAsStream(resource);

      if(in == null){
        throw new IllegalArgumentException("File not found");
      }
      
      setRootDir(resource);
      
      ResourceBundle res;
      try{
        res = ResourceBundle.getBundle(resource.replace(".xul", ""));
      } catch(MissingResourceException e){
        try{
            SAXReader rdr = new SAXReader();
            final Document doc = rdr.read(in);
            return loadXulFragment(doc);
        } catch(DocumentException ex){
            throw new XulException("Error parsing Xul Document", ex);
        }
      }
      return loadXulFragment(resource, res);
  }
  
  public XulDomContainer loadXulFragment(String resource, ResourceBundle bundle) throws  XulException{

      try{

        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        
        String localOutput = ResourceBundleTranslator.translate(in, bundle);
        
        
        SAXReader rdr = new SAXReader();
        final Document doc = rdr.read(new StringReader(localOutput));
        
        setRootDir(resource);
        
        return this.loadXulFragment(doc);
      } catch(DocumentException e){
        throw new XulException("Error parsing Xul Document", e);
      } catch(IOException e){
        throw new XulException("Error loading Xul Document into Freemarker", e);
      }
  }
  
  private List<String> includedSources = new ArrayList<String>();
  private List<String> resourceBundles = new ArrayList<String>();
  
  public String performIncludeTranslations(String input) throws XulException{
    String output = input;
    for(String includeSrc : includedSources){
      try{
        ResourceBundle res = ResourceBundle.getBundle(includeSrc.replace(".xul", ""));
        output = ResourceBundleTranslator.translate(output, res);
      } catch(MissingResourceException e){
        continue;
      } catch(IOException e){
      
        throw new XulException(e);
      }
    }
    for(String resource : resourceBundles){
      logger.info("Processing Resource Bundle: "+resource);
      try{
        ResourceBundle res = ResourceBundle.getBundle(resource);
        if(res == null){
          continue;
        }
        output = ResourceBundleTranslator.translate(output, res);
      } catch(MissingResourceException e){
        continue;
      } catch(IOException e){
      
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
  
  public Document preProcess(Document srcDoc) throws XulException{
    
    SAXReader rdr = new SAXReader();
    try{
      String upperedIdDoc = this.upperCaseIDAttrs(srcDoc.asXML());
      srcDoc = rdr.read(new StringReader(upperedIdDoc));
    } catch(Exception e){
      throw new XulException(e);
    }
    
    XPath xpath = new DefaultXPath("//pen:include");
    
    HashMap uris = new HashMap();
    uris.put("xul", "http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul");
    uris.put("pen", "http://www.pentaho.org/2008/xul");
    
    xpath.setNamespaceURIs(uris);
    
    List<Element> eles = xpath.selectNodes(srcDoc);

    for(Element ele : eles){
      String src = "";
      try{
          
          src = this.getRootDir()+ele.attributeValue("src");
          
          String resourceBundle = ele.attributeValue("resource");
          if(resourceBundle != null){
            resourceBundles.add(resourceBundle);
          }
          
          InputStream in = getClass().getClassLoader().getResourceAsStream(src);
          if(in != null){
            logger.info("Adding include src: "+src);
            includedSources.add(src);
          } else { //try fully qualified name
            src = ele.attributeValue("src");
            in = getClass().getClassLoader().getResourceAsStream(src);
            if(in != null){
              includedSources.add(src);
              logger.info("Adding include src: "+src);
            } else {
              logger.error("Could not resolve include: "+src);
            }
            
          }
          
          final Document doc = rdr.read(in);
          
          Element root = doc.getRootElement();
          String ignoreRoot = ele.attributeValue("ignoreroot");
          if(root.getName().equals("overlay")){
            processOverlay(root, ele.getDocument().getRootElement());
          } else if(ignoreRoot == null || ignoreRoot.equalsIgnoreCase("false")){
            logger.info("Including entire file: "+src);
            List contentOfParent = ele.getParent().content();
            int index = contentOfParent.indexOf(ele);
            contentOfParent.set(index, root);
            
          } else {
            logger.info("Including children: "+src);
            List contentOfParent = ele.getParent().content();
            int index = contentOfParent.indexOf(ele);
            contentOfParent.remove(index);
            List children = root.elements();
            for(int i=children.size()-1; i >=0; i--){
              contentOfParent.add(index, children.get(i));
            }
          }
          
          
      } catch(DocumentException ex){
          throw new XulException("Error parsing Xul Document: "+src, ex);
      }
    }

    //upper-case all nodes again
    try{
      String upperedIdDoc = this.upperCaseIDAttrs(srcDoc.asXML());
      srcDoc = rdr.read(new StringReader(upperedIdDoc));
    } catch(Exception e){
      throw new XulException(e);
    }
    
    return srcDoc;
  }
  
  private void processOverlay(Element overlayEle, Element srcEle){
    for(Object child : overlayEle.elements()){
      Element overlay = (Element) child;
      String overlayId = overlay.attributeValue("id");
      logger.info("Processing overlay\nID: "+overlayId);
      Element sourceElement = srcEle.getDocument().elementByID(overlayId);
      if(sourceElement == null){
        continue;
      }
      logger.info("Found match in source doc:");
      
      String removeElement = overlay.attributeValue("removeelement");
      if(removeElement != null && removeElement.equalsIgnoreCase("true")){
        sourceElement.getParent().remove(sourceElement);
      } else {
        
        //lets start out by just including everything
        for(Object overlayChild : overlay.elements()){
          Element pluckedElement = (Element) overlay.content().remove(overlay.content().indexOf(overlayChild));
          sourceElement.add(pluckedElement);
          logger.info("processed overlay child: "+((Element)overlayChild).getName()+" : "+pluckedElement.getName());
        }
      }
    }
  }
  
  public void processOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument, XulDomContainer container) throws XulException{
    try{

      InputStream in = getClass().getClassLoader().getResourceAsStream(overlaySrc);
      
      //String localOutput = ResourceBundleTranslator.translate(in, bundle);
      
      SAXReader rdr = new SAXReader();
      //final Document doc = rdr.read(new StringReader(localOutput));
      final Document doc = rdr.read(in);
     
      Element overlayRoot = doc.getRootElement();
      
      for(Object child : overlayRoot.elements()){
        Element overlay = (Element) child;
        String overlayId = overlay.attributeValue("id");

        org.pentaho.ui.xul.dom.Element sourceElement = targetDocument.getElementById(overlayId);
        if(sourceElement == null){
          logger.info("Source Element from target document is null: "+overlayId);
          continue;
        }
        
        for(Object childToParse : overlay.elements()){
          logger.info("Processing overlay\nID: "+overlayId);
          parser.reset();
          parser.setContainer(container);
          XulComponent c = parser.parse((Element) childToParse, (XulContainer) sourceElement);
          sourceElement.addChild(c);
          ((XulContainer) sourceElement).addComponent(c);
          ((XulContainer) sourceElement).addChild(c);
        }
        
      }
      
    } catch(DocumentException e){
      throw new XulException(e);
    }
  }
  
  public void removeOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument, XulDomContainer container) throws XulException{
    try{

      InputStream in = getClass().getClassLoader().getResourceAsStream(overlaySrc);
      
      //String localOutput = ResourceBundleTranslator.translate(in, bundle);
      
      SAXReader rdr = new SAXReader();
      //final Document doc = rdr.read(new StringReader(localOutput));
      final Document doc = rdr.read(in);
     
      Element overlayRoot = doc.getRootElement();
      
      for(Object child : overlayRoot.elements()){
        Element overlay = (Element) child;
        
        for(Object childToParse : overlay.elements()){
          String childId = ((Element) childToParse).attributeValue("id");
          
          org.pentaho.ui.xul.dom.Element prevOverlayedEle = targetDocument.getElementById(childId);
          if(prevOverlayedEle == null){
            logger.info("Source Element from target document is null: "+childId);
            continue;
          }
          
          prevOverlayedEle.getParent().removeChild(prevOverlayedEle);
          
          
        }
        
      }
      
    } catch(DocumentException e){
      throw new XulException(e);
    }
  }
  

  private String upperCaseIDAttrs(String src){

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

  