/**
 * 
 */
package org.pentaho.ui.xul.swt;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swt.tags.SwtWindow;
import org.pentaho.ui.xul.util.ResourceBundleTranslator;

/**
 * @author NBaker
 *
 */
public class SwtXulLoader implements XulLoader {

  private XulParser parser;
  public SwtXulLoader() throws XulException{

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
    

    try{
      parser = new XulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
    
    //attach Renderers
    parser.registerHandler("WINDOW", "org.pentaho.ui.xul.swt.tags.SwtWindow");
    parser.registerHandler("BUTTON", "org.pentaho.ui.xul.swt.tags.SwtButton");
    parser.registerHandler("BOX", "org.pentaho.ui.xul.swt.tags.SwtBox");
    parser.registerHandler("VBOX", "org.pentaho.ui.xul.swt.tags.SwtVbox");
    parser.registerHandler("HBOX", "org.pentaho.ui.xul.swt.tags.SwtHbox");
    parser.registerHandler("LABEL", "org.pentaho.ui.xul.swt.tags.SwtLabel");
    parser.registerHandler("TEXTBOX", "org.pentaho.ui.xul.swt.tags.SwtTextbox");
    parser.registerHandler("GROUPBOX", "org.pentaho.ui.xul.swt.tags.SwtGroupbox");
    parser.registerHandler("CAPTION", "org.pentaho.ui.xul.swt.tags.SwtCaption");
    parser.registerHandler("LISTBOX", "org.pentaho.ui.xul.swt.tags.SwtListbox");
    parser.registerHandler("LISTITEM", "org.pentaho.ui.xul.swt.tags.SwtListitem");
    parser.registerHandler("SCRIPT", "org.pentaho.ui.xul.swt.tags.SwtScript");
    parser.registerHandler("CHECKBOX", "org.pentaho.ui.xul.swt.tags.SwtCheckbox");
    parser.registerHandler("MESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtMessageBox");
    parser.registerHandler("ERRORMESSAGEBOX", "org.pentaho.ui.xul.swt.tags.SwtErrorMessageBox");
    parser.registerHandler("DECK", "org.pentaho.ui.xul.swt.tags.SwtDeck");
    parser.registerHandler("TREE", "org.pentaho.ui.xul.swt.tags.SwtTree");
    parser.registerHandler("TREECOLS", "org.pentaho.ui.xul.swt.tags.SwtTreeCols");
    parser.registerHandler("TREECOL", "org.pentaho.ui.xul.swt.tags.SwtTreeCol");
    parser.registerHandler("TREECHILDREN", "org.pentaho.ui.xul.swt.tags.SwtTreeChildren");
    parser.registerHandler("TREEITEM", "org.pentaho.ui.xul.swt.tags.SwtTreeItem");
    parser.registerHandler("TREEROW", "org.pentaho.ui.xul.swt.tags.SwtTreeRow");
    parser.registerHandler("TREECELL", "org.pentaho.ui.xul.swt.tags.SwtTreeCell");
    parser.registerHandler("PROGRESSMETER", "org.pentaho.ui.xul.swt.tags.SwtProgressmeter");
    
     
  }
  
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException{

    XulDomContainer container = new XulWindowContainer(this);
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    // SWT has no notion of an "onload" event, so we must simulate it...
    
    Element maybeWindow = container.getDocumentRoot().getRootElement();
    if ( maybeWindow instanceof SwtWindow){
      SwtWindow window = (SwtWindow) maybeWindow;
      window.notifyListeners(XulWindow.EVENT_ON_LOAD);
    }

    return container;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException {
    XulDomContainer container = new XulFragmentContainer(this);
    parser.reset();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement()).getRootElement();
    
    //not sure this is needed
    //parser.getDocumentRoot().addChild(element);
    
    return container;
  }
  
  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException{

      InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream(resource);

      if(in == null){
        throw new IllegalArgumentException("XUL definition file not found: " + resource);
      }
      
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

        InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream(resource);
        
      	String localOutput = ResourceBundleTranslator.translate(in, bundle);
      	
	      SAXReader rdr = new SAXReader();
	      final Document doc = rdr.read(new StringReader(localOutput));
	      
	      return this.loadXul(doc);
      } catch(DocumentException e){
    	  throw new XulException("Error parsing Xul Document", e);
      } catch(IOException e){
    	  throw new XulException("Error loading Xul Document into Freemarker", e);
      }
  }
  
  public XulDomContainer loadXulFragment(String resource) throws IllegalArgumentException, XulException{

      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream(resource);

      if(in == null){
        throw new IllegalArgumentException("File not found");
      }
      
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

        InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream(resource);
        
      	String localOutput = ResourceBundleTranslator.translate(in, bundle);
      	
	      SAXReader rdr = new SAXReader();
	      final Document doc = rdr.read(new StringReader(localOutput));
	      
	      return this.loadXulFragment(doc);
      } catch(DocumentException e){
    	  throw new XulException("Error parsing Xul Document", e);
      } catch(IOException e){
    	  throw new XulException("Error loading Xul Document into Freemarker", e);
      }
  }
  

  public XulComponent createElement(String elementName) throws XulException{
  	return parser.getElement(elementName);
  }
  

  public void register(String tagName, String className) {
    parser.registerHandler(tagName, className);
  }

}