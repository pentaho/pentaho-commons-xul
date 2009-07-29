package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.gwt.GwtDomDocument;

public class GwtDocumentFactory {
  public static Document createDocument() {
    return new GwtDomDocument();
  }
}
