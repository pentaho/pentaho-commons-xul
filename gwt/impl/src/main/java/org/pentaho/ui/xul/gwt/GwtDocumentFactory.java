/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

public class GwtDocumentFactory {
  public static Document createDocument() {
    return new GwtDomDocument();
  }

  public static Element createElement( String name ) {
    return new GwtDomElement( name );
  }

}
