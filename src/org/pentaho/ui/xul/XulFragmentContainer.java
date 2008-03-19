/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public class XulFragmentContainer extends XulDomContainer {
  private Document fragment;
  
  
  public XulFragmentContainer(){
    super();
  }

  public Document getDocumentRoot(){
    return fragment;
  }

  public void addDocument(Document document){
    this.fragment = document;
  }
  
}
