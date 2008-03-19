/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public class XulWindowContainer extends XulDomContainer{
  private List<Document> windows;
  
  
  public XulWindowContainer(){
    super();
    windows = new ArrayList<Document>();
  }

  public Document getDocumentRoot(){
    return this.windows.get(0);
  }

  public void addDocument(Document document){
    this.windows.add(document);
  }
  
}
