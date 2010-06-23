package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtDomDocument;

public class GwtDocumentFactory {
  public static Document createDocument() {
    return new GwtDomDocument();
  }
  
  public static Element createElement(String name) {
    return new GwtDomElement(name);
  }
  
}
