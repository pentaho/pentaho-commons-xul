/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public class XulFragmentContainer extends XulDomContainer {
  private Document fragment;
  private XulLoader xulLoader;
  
  
  public XulFragmentContainer(XulLoader xulLoader){
    super();
    this.xulLoader = xulLoader;
  }

  public Document getDocumentRoot(){
    return fragment;
  }

  public void addDocument(Document document){
    this.fragment = document;
  }

  @Override
  public XulMessageBox createMessageBox(String message) {
  	return null;
  }

  @Override
  public void close() {
  }

  @Override
  public XulFragmentContainer loadFragment(String xulLocation) throws XulException{
    System.out.println("loadFragment not implemented in XulFragmentContainer");
    return null;
  }
    
}
