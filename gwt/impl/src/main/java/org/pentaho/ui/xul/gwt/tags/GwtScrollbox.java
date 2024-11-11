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

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulScrollbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtScrollbox extends AbstractGwtXulContainer implements XulScrollbox {

  static final String ELEMENT_NAME = "pen:scrollbox"; //$NON-NLS-1$

  private SimplePanel panel;
  boolean overflowx = true;
  boolean overflowy = true;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtScrollbox();
      }
    } );
  }

  public GwtScrollbox() {
    super( ELEMENT_NAME );
    panel = new SimplePanel();
    if ( isOverflowx() && isOverflowy() ) {
      panel.getElement().getStyle().setProperty( "overflow", "auto" );
    } else {
      if ( !isOverflowx() ) {
        panel.getElement().getStyle().setProperty( "overflow-x", "hidden" );
      }
      if ( !isOverflowy() ) {
        panel.getElement().getStyle().setProperty( "overflow-y", "hidden" );
      }
      if ( isOverflowx() ) {
        panel.getElement().getStyle().setProperty( "overflow-x", "visible" );
      }
      if ( isOverflowy() ) {
        panel.getElement().getStyle().setProperty( "overflow-y", "visible" );
      }
    }
    setManagedObject( panel );
  }

  @Override
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( hasAttribute( srcEle, "overflowx" ) ) {
      try {
        setWidth( Integer.parseInt( srcEle.getAttribute( "overflowx" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    if ( hasAttribute( srcEle, "overflowy" ) ) {
      try {
        setHeight( Integer.parseInt( srcEle.getAttribute( "overflowy" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }

  public void setWidth( int size ) {
    panel.setWidth( size + "px" );
  }

  public void setHeight( int size ) {
    panel.setHeight( size + "px" );
  }

  public void layout() {
    panel.clear();
    for ( XulComponent child : this.getChildNodes() ) {
      panel.add( (Widget) child.getManagedObject() );
      break;
    }
  }

  public boolean isOverflowx() {
    return overflowx;
  }

  public void setOverflowx( boolean overflowx ) {
    this.overflowx = overflowx;
  }

  public boolean isOverflowy() {
    return overflowy;
  }

  public void setOverflowy( boolean overflowy ) {
    this.overflowy = overflowy;
  }

  private boolean hasAttribute( com.google.gwt.xml.client.Element ele, String attr ) {
    return ( ele.hasAttribute( attr ) && ele.getAttribute( attr ).trim().length() > 0 );
  }
}
