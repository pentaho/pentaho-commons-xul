/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

/**
 * 
 */

package org.pentaho.ui.xul.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.BindingProvider;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.util.Align;

import java.util.List;

/**
 * @author OEM
 * 
 */
public abstract class AbstractXulComponent implements XulComponent {
  private static final long serialVersionUID = -3629792827245143824L;

  private static final Log logger = LogFactory.getLog( AbstractXulComponent.class );

  private Object managedObject;

  protected Element element;

  protected int flex = 0;

  protected String id;

  protected boolean flexLayout = false;

  protected int width;

  protected int height;

  public boolean initialized = false;

  protected String tooltip;

  protected String bgcolor = null;

  protected int padding = -1;

  protected int spacing = -1;

  protected String onblur;

  protected String insertbefore, insertafter;

  protected boolean removeElement;

  protected int position = -1;

  protected boolean visible = true;

  protected Align alignment;

  protected String context, popup, menu;

  protected String ondrag;

  protected String drageffect;

  protected String ondrop;

  protected BindingProvider bindingProvider;
  private String dropVetoMethod;

  public AbstractXulComponent( Element element ) {
    this.element = element;

  }

  public AbstractXulComponent( String name ) {
    try {
      this.element = DocumentFactory.createElement( name, this );
    } catch ( XulException e ) {
      logger.error( String.format( "error creating XulElement (%s)", name ), e );
      throw new IllegalArgumentException( String.format( "error creating XulElement (%s)", name ), e );
    }
  }

  public Object getManagedObject() {
    return managedObject;
  }

  public void setManagedObject( Object managed ) {
    managedObject = managed;
  }

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.setAttribute( "ID", id );
    this.id = id;
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex( int flex ) {
    this.flex = flex;
  }

  public void layout() {
    initialized = true;
  }

  protected void invoke( String method ) {
    invoke( method, null );
  }

  protected void invoke( String method, Object[] args ) {
    Document doc = getDocument();
    if ( doc == null ) {
      logger.error( "Error invoking event: document is null" );
      return;
    }
    XulRoot window = (XulRoot) doc.getRootElement();
    XulDomContainer con = window.getXulDomContainer();

    try {
      if ( args == null ) {
        args = new Object[] {};
      }
      con.invoke( method, args );
    } catch ( XulException e ) {
      logger.error( "Error calling oncommand event", e );
    }
  }

  // passthrough DOM methods below
  // TODO: extract methods below into abstract class
  public String getName() {
    return element.getName();
  }

  public void addChild( Element ele ) {
    this.element.addChild( ele );
  }

  public void addChildAt( Element element, int idx ) {
    this.element.addChildAt( element, idx );
  }

  public void removeChild( Element ele ) {
    this.element.removeChild( ele );

  }

  public List<XulComponent> getChildNodes() {
    return this.element.getChildNodes();
  }

  public Document getDocument() {
    return this.element.getDocument();
  }

  public XulComponent getElementById( String elementId ) {
    return this.element.getElementById( elementId );
  }

  public XulComponent getElementByXPath( String path ) {
    return this.element.getElementByXPath( path );
  }

  public Object getElementObject() {
    return this.element.getElementObject();
  }

  public List<XulComponent> getElementsByTagName( String tagName ) {
    return this.element.getElementsByTagName( tagName );
  }

  public XulComponent getFirstChild() {
    return this.element.getFirstChild();
  }

  public Namespace getNamespace() {
    return this.element.getNamespace();
  }

  public XulComponent getParent() {
    return this.element.getParent();
  }

  public String getText() {
    return this.element.getText();
  }

  public void setAttribute( Attribute attribute ) {
    this.element.setAttribute( attribute );
  }

  public void setNamespace( String prefix, String uri ) {
    this.element.setNamespace( prefix, uri );

  }

  public List<Attribute> getAttributes() {
    return this.element.getAttributes();
  }

  public String getAttributeValue( String attributeName ) {
    return this.element.getAttributeValue( attributeName );
  }

  public void setAttributes( List<Attribute> attributes ) {
    this.element.setAttributes( attributes );
  }

  public void setAttribute( String name, String value ) {
    this.element.setAttribute( name, value );
  }

  public AbstractXulComponent getXulElement() {
    return this;
  }

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.element.replaceChild( oldElement, newElement );
  }

  public void setHeight( int height ) {
    this.height = height;
  }

  public int getHeight() {
    return this.height;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public int getWidth() {
    return this.width;
  }

  public void setOnblur( String method ) {
    onblur = method;
  }

  public String getOnblur() {
    return onblur;
  }

  public String getTooltiptext() {
    return this.tooltip;
  }

  public void setTooltiptext( String tooltip ) {
    this.tooltip = tooltip;
  }

  public void setBgcolor( String bgcolor ) {
    this.bgcolor = bgcolor;
  }

  public String getBgcolor() {
    return bgcolor;
  }

  public int getPadding() {
    return padding;
  }

  public void setPadding( int padding ) {
    this.padding = padding;
  }

  public int getSpacing() {
    return this.spacing;
  }

  public void setSpacing( int spacing ) {
    this.spacing = spacing;
  }

  public String getInsertafter() {
    return this.insertafter;
  }

  public String getInsertbefore() {
    return this.insertbefore;
  }

  public int getPosition() {
    return this.position;
  }

  public boolean getRemoveelement() {
    return this.removeElement;
  }

  public void setInsertafter( String id ) {
    this.insertafter = id;
  }

  public void setInsertbefore( String id ) {
    this.insertbefore = id;
  }

  public void setPosition( int pos ) {
    this.position = pos;
  }

  public void setRemoveelement( boolean flag ) {
    this.removeElement = flag;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public void setVisible( boolean visible ) {
    this.visible = visible;

    // recompute the layout after a visibility change ..
    if ( initialized ) {
      layout();
    }
  }

  public void onDomReady() {

  }

  public String getAlign() {
    return ( alignment != null ) ? alignment.toString() : null;
  }

  public void setAlign( String alignment ) {
    try {
      this.alignment = Align.valueOf( alignment.toUpperCase() );
    } catch ( Exception e ) {
      logger.error( e );
    }
  }

  public String getContext() {
    return context;
  }

  public String getPopup() {
    return popup;
  }

  public void setContext( String id ) {
    this.context = id;
  }

  public void setPopup( String id ) {
    this.popup = id;
  }

  public void setMenu( String id ) {
    this.menu = id;
  }

  public String getMenu() {
    return menu;
  }

  public void setOndrag( String ondrag ) {
    this.ondrag = ondrag;
  }

  public String getOndrag() {
    return ondrag;
  }

  public void setOndrop( String ondrop ) {
    this.ondrop = ondrop;
  }

  public String getOndrop() {
    return ondrop;
  }

  public void setDrageffect( String drageffect ) {
    this.drageffect = drageffect;
  }

  public String getDrageffect() {
    return drageffect;
  }

  public void setBindingProvider( BindingProvider bindingProvider ) {
    this.bindingProvider = bindingProvider;
  }

  public void setDropvetoer( String dropVetoMethod ) {
    this.dropVetoMethod = dropVetoMethod;
  }

  public String getDropvetoer() {
    return dropVetoMethod;
  }
}
