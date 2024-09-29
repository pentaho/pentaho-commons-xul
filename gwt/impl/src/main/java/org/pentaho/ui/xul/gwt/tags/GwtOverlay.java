/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtOverlay extends AbstractGwtXulContainer implements XulOverlay {

  public static void register() {
    GwtXulParser.registerHandler( "overlay", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtOverlay();
      }
    } );
  }

  public GwtOverlay() {
    super( "overlay" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
  }

  public String getSrc() {
    return "";
  }

  public void setSrc( String src ) {

  }

  public boolean isDisabled() {
    return false;
  }

  public void setDisabled( boolean disabled ) {
  }

  @Override
  public void layout() {
    // No layout required
  }

  public String getOverlayUri() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getResourceBundleUri() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOverlayXml() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSource() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getPriority() {
    // TODO Auto-generated method stub
    return 9999;
  }

}
