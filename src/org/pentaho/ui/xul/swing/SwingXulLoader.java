/**
 * 
 */
package org.pentaho.ui.xul.swing;

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
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.impl.XulFragmentContainer;
import org.pentaho.ui.xul.impl.XulParser;
import org.pentaho.ui.xul.impl.XulWindowContainer;
import org.pentaho.ui.xul.util.ResourceBundleTranslator;

/**
 * @author nbaker
 *
 */
public class SwingXulLoader implements XulLoader {

  private XulParser parser;
  public SwingXulLoader() throws XulException{

    DocumentFactory.registerDOMClass(DocumentDom4J.class);
    DocumentFactory.registerElementClass(ElementDom4J.class);
  
    
    try{
      parser = new XulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
     

    //attach Renderers
    parser.registerHandler("WINDOW", "org.pentaho.ui.xul.swing.tags.SwingWindow");
    parser.registerHandler("BUTTON", "org.pentaho.ui.xul.swing.tags.SwingButton");
    parser.registerHandler("VBOX", "org.pentaho.ui.xul.swing.tags.SwingVbox");
    parser.registerHandler("HBOX", "org.pentaho.ui.xul.swing.tags.SwingHbox");
    parser.registerHandler("LABEL", "org.pentaho.ui.xul.swing.tags.SwingLabel");
    parser.registerHandler("TEXTBOX", "org.pentaho.ui.xul.swing.tags.SwingTextbox");
    parser.registerHandler("SCRIPT", "org.pentaho.ui.xul.swing.tags.SwingScript");
    parser.registerHandler("SPACER", "org.pentaho.ui.xul.swing.tags.SwingSpacer");
    parser.registerHandler("CHECKBOX", "org.pentaho.ui.xul.swing.tags.SwingCheckbox");
    parser.registerHandler("RADIO", "org.pentaho.ui.xul.swing.tags.SwingRadio");
    parser.registerHandler("RADIOGROUP", "org.pentaho.ui.xul.swing.tags.SwingRadioGroup");
    parser.registerHandler("GROUPBOX", "org.pentaho.ui.xul.swing.tags.SwingGroupbox");
    parser.registerHandler("CAPTION", "org.pentaho.ui.xul.swing.tags.SwingCaption");
    parser.registerHandler("LISTBOX", "org.pentaho.ui.xul.swing.tags.SwingListbox");
    parser.registerHandler("LISTITEM", "org.pentaho.ui.xul.swing.tags.SwingListitem");
    parser.registerHandler("MESSAGEBOX", "org.pentaho.ui.xul.swing.tags.SwingMessageBox");
    parser.registerHandler("DECK", "org.pentaho.ui.xul.swing.tags.SwingDeck");
    parser.registerHandler("MENUBAR", "org.pentaho.ui.xul.swing.tags.SwingMenubar");
    parser.registerHandler("MENU", "org.pentaho.ui.xul.swing.tags.SwingMenu");
    parser.registerHandler("MENUPOPUP", "org.pentaho.ui.xul.swing.tags.SwingMenupopup");
    parser.registerHandler("MENUITEM", "org.pentaho.ui.xul.swing.tags.SwingMenuitem");
    parser.registerHandler("MENUSEPARATOR", "org.pentaho.ui.xul.swing.tags.SwingMenuseparator");
    parser.registerHandler("TREE", "org.pentaho.ui.xul.swing.tags.SwingTree");
    parser.registerHandler("TREECOLS", "org.pentaho.ui.xul.swing.tags.SwingTreeCols");
    parser.registerHandler("TREECOL", "org.pentaho.ui.xul.swing.tags.SwingTreeCol");
    parser.registerHandler("TREECHILDREN", "org.pentaho.ui.xul.swing.tags.SwingTreeChildren");
    parser.registerHandler("TREEITEM", "org.pentaho.ui.xul.swing.tags.SwingTreeItem");
    parser.registerHandler("TREEROW", "org.pentaho.ui.xul.swing.tags.SwingTreeRow");
    parser.registerHandler("TREECELL", "org.pentaho.ui.xul.swing.tags.SwingTreeCell");
    

    parser.registerHandler("TABBOX", "org.pentaho.ui.xul.swing.tags.SwingTabbox");
    parser.registerHandler("TABS", "org.pentaho.ui.xul.swing.tags.SwingTabs");
    parser.registerHandler("TAB", "org.pentaho.ui.xul.swing.tags.SwingTab");
    parser.registerHandler("TABPANELS", "org.pentaho.ui.xul.swing.tags.SwingTabpanels");
    parser.registerHandler("TABPANEL", "org.pentaho.ui.xul.swing.tags.SwingTabpanel");
    parser.registerHandler("DIALOG", "org.pentaho.ui.xul.swing.tags.SwingDialog");
    parser.registerHandler("DIALOGHEADER", "org.pentaho.ui.xul.swing.tags.SwingDialogheader");
    parser.registerHandler("PROGRESSMETER", "org.pentaho.ui.xul.swing.tags.SwingProgressmeter");
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException{

    XulDomContainer container = new XulWindowContainer(this);
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
   
    return container;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Document xulDocument) throws IllegalArgumentException, XulException {
    XulDomContainer container = new XulFragmentContainer(this);
    parser.reset();
    parser.setContainer(container);
    parser.parseDocument(xulDocument.getRootElement());
    
    return container;
  }
  
  public XulComponent createElement(String elementName) throws XulException{
  	return parser.getElement(elementName);
  }
  
  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException{

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

  public void register(String tagName, String className) {
    parser.registerHandler(tagName, className);
  }
  
}
