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

package org.pentaho.ui.xul.gwt;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.BindingProvider;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

/**
 * @author OEM
 * 
 */
public abstract class AbstractGwtXulComponent extends GwtDomElement implements XulComponent, XulEventSource {

  protected XulDomContainer xulDomContainer;
  protected Panel container;
  protected Orient orientation;
  private Object managedObject;
  protected int flex = 0;
  protected String id;
  protected boolean flexLayout = false;
  protected String insertbefore, insertafter;
  protected int position = -1;
  protected boolean initialized;
  protected String bgcolor, onblur, tooltiptext;
  protected int height, padding;
  protected int width;
  protected boolean disabled, removeElement;
  protected boolean visible = true;
  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport( this );
  protected String alignment;
  protected String context, popup, menu;
  protected String ondrag;
  protected String drageffect;
  protected String ondrop;
  protected BindingProvider bindingProvider;
  private String dropVetoMethod;

  public AbstractGwtXulComponent( String name ) {
    super( name );
  }

  // public AbstractGwtXulComponent(String tagName, Object managedObject) {
  // super(tagName);
  // this.managedObject = managedObject;
  // children = new ArrayList<XulComponent>();
  // }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    if ( srcEle.hasAttribute( "id" ) ) {
      setId( srcEle.getAttribute( "id" ) );
    }

    if ( srcEle.hasAttribute( "orient" ) && srcEle.getAttribute( "orient" ).trim().length() > 0 ) {
      // TODO: setOrient should live in an interface somewhere???
      setOrient( srcEle.getAttribute( "orient" ) );
    }
    if ( srcEle.hasAttribute( "tooltiptext" ) && srcEle.getAttribute( "tooltiptext" ).trim().length() > 0 ) {
      // TODO: setOrient should live in an interface somewhere???
      setTooltiptext( srcEle.getAttribute( "tooltiptext" ) );
    }

    if ( srcEle.hasAttribute( "flex" ) && srcEle.getAttribute( "flex" ).trim().length() > 0 ) {
      try {
        setFlex( Integer.parseInt( srcEle.getAttribute( "flex" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( hasAttribute( srcEle, "width" ) ) {
      try {
        setWidth( Integer.parseInt( srcEle.getAttribute( "width" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    if ( hasAttribute( srcEle, "height" ) ) {
      try {
        setHeight( Integer.parseInt( srcEle.getAttribute( "height" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    if ( hasAttribute( srcEle, "spacing" ) ) {
      try {
        setSpacing( Integer.parseInt( srcEle.getAttribute( "spacing" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( hasAttribute( srcEle, "padding" ) ) {
      try {
        setPadding( Integer.parseInt( srcEle.getAttribute( "padding" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( hasAttribute( srcEle, "visible" ) ) {
      try {
        setVisible( srcEle.getAttribute( "visible" ).equalsIgnoreCase( "true" ) ? true : false ); //$NON-NLS-1$ //$NON-NLS-2$
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( hasAttribute( srcEle, "position" ) ) {
      try {
        setPosition( Integer.parseInt( srcEle.getAttribute( "position" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    if ( srcEle.hasAttribute( "insertbefore" ) && srcEle.getAttribute( "insertbefore" ).trim().length() > 0 ) {
      setInsertbefore( srcEle.getAttribute( "insertbefore" ) );
    }

    if ( srcEle.hasAttribute( "insertafter" ) && srcEle.getAttribute( "insertafter" ).trim().length() > 0 ) {
      setInsertafter( srcEle.getAttribute( "insertafter" ) );
    }
    if ( srcEle.hasAttribute( "removeelement" ) && srcEle.getAttribute( "removeelement" ).trim().length() > 0 ) {
      setRemoveelement( "true".equals( srcEle.getAttribute( "removeelement" ) ) );
    }

    NamedNodeMap attrs = srcEle.getAttributes();
    for ( int i = 0; i < attrs.getLength(); i++ ) {
      Node n = attrs.item( i );
      if ( n != null ) {
        this.setAttribute( n.getNodeName(), n.getNodeValue() );
      }
    }
  }

  private boolean hasAttribute( com.google.gwt.xml.client.Element ele, String attr ) {
    return ( ele.hasAttribute( attr ) && ele.getAttribute( attr ).trim().length() > 0 );
  }

  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  public void layout() {
    if ( this instanceof XulContainer == false ) {
      // Core version of parser doesn't call layout unless the node is a container...
      return;
    }

    setVisible( isVisible() );
    if ( this.container != null ) {
      this.container.clear();
    }

    Object w = getManagedObject();

    double totalFlex = 0.0;

    for ( XulComponent comp : this.getChildNodes() ) {

      if ( comp.getManagedObject() == null ) {
        continue;
      }
      if ( comp.getFlex() > 0 && comp.isVisible() ) {
        flexLayout = true;
        totalFlex += comp.getFlex();
      }
    }

    // if(flexLayout)
    // gc.fill = GridBagConstraints.BOTH;

    List<XulComponent> nodes = this.getChildNodes();

    XulContainer thisContainer = (XulContainer) this;

    Align alignment =
        ( StringUtils.isEmpty( thisContainer.getAlign() ) == false ) ? Align.valueOf( thisContainer.getAlign()
            .toUpperCase() ) : null;

    // TODO: this is a different behavior than we implemented in Swing.
    if ( !flexLayout && StringUtils.isEmpty( thisContainer.getAlign() ) == false ) {
      SimplePanel fillerPanel = new SimplePanel();
      switch ( alignment ) {
        case END:
          container.add( fillerPanel );
          if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
            ( (VerticalPanel) container ).setCellHeight( fillerPanel, "100%" );
          } else {
            ( (HorizontalPanel) container ).setCellWidth( fillerPanel, "100%" );
          }
          break;
        case CENTER:
          container.add( fillerPanel );

          if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
            ( (VerticalPanel) container ).setCellHeight( fillerPanel, "50%" );
          } else {
            ( (HorizontalPanel) container ).setCellWidth( fillerPanel, "50%" );
          }
          break;
      }
    }

    for ( int i = 0; i < children.size(); i++ ) {
      XulComponent comp = nodes.get( i );

      Object wrappedWidget = comp.getManagedObject();
      if ( wrappedWidget == null || !( wrappedWidget instanceof Widget ) ) {
        continue;
      }
      Widget component = (Widget) wrappedWidget;
      component.getElement().setId( comp.getId() );

      if ( component == null ) {
        continue;
      }
      SimplePanel componentWrapper = new SimplePanel();
      componentWrapper.add( component );
      container.add( componentWrapper );

      if ( this.getOrientation() == Orient.VERTICAL ) { // VBox
        container.setWidth( "100%" );

      } else { // HBox
        container.setHeight( "100%" );
      }

      if ( flexLayout ) {

        int componentFlex = comp.getFlex();
        if ( componentFlex > 0 ) {

          String percentage = Math.round( ( componentFlex / totalFlex ) * 100 ) + "%";
          if ( this.getOrientation() == Orient.VERTICAL ) { // VBox
            ( (VerticalPanel) container ).setCellHeight( componentWrapper, percentage );
            ( (VerticalPanel) container ).setCellWidth( componentWrapper, "100%" );
            component.setWidth( "100%" );

            if ( comp.getFlex() > 0 ) {
              componentWrapper.setHeight( "100%" );
              component.setHeight( "100%" );
            }
          } else { // HBox
            ( (HorizontalPanel) container ).setCellWidth( componentWrapper, percentage );
            ( (HorizontalPanel) container ).setCellHeight( componentWrapper, "100%" );
            component.setHeight( "100%" );

            if ( comp.getFlex() > 0 ) {
              componentWrapper.setWidth( "100%" );
              component.setHeight( "100%" );
            }
          }
        }
      }

      Style wrapperStyle = componentWrapper.getElement().getStyle();
      Style style = component.getElement().getStyle();
      // By default 100%, respect hard-coded width
      if ( this.getOrientation() == Orient.VERTICAL ) { // VBox
        if ( comp.getWidth() > 0 ) {
          style.setProperty( "width", comp.getWidth() + "px" );
        } else {
          wrapperStyle.setProperty( "width", "100%" );
        }
        if ( comp.getHeight() > 0 ) {
          style.setProperty( "height", comp.getHeight() + "px" );
        } else if ( comp.getFlex() > 0 ) {
          wrapperStyle.setProperty( "height", "100%" );
        }
      } else { // HBox
        if ( comp.getHeight() > 0 ) {
          style.setProperty( "height", comp.getHeight() + "px" );
        } else {
          wrapperStyle.setProperty( "height", "100%" );
        }
        if ( comp.getWidth() > 0 ) {
          style.setProperty( "width", comp.getWidth() + "px" );
        } else if ( comp.getFlex() > 0 ) {
          wrapperStyle.setProperty( "width", "100%" );
        }
      }
    }

    // TODO: this is a different behavior than we implemented in Swing.
    if ( !flexLayout && container != null ) {
      SimplePanel fillerPanel = new SimplePanel();
      if ( alignment == null ) {
        alignment = Align.START;
      }
      switch ( alignment ) {
        case START:
          if ( this.getOrientation() != Orient.VERTICAL ) {
            ( (HorizontalPanel) container ).setCellWidth( fillerPanel, "100%" );
            container.add( fillerPanel );
          }
          break;
        case CENTER:
          container.add( fillerPanel );

          if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
            ( (VerticalPanel) container ).setCellHeight( fillerPanel, "50%" );
          } else {
            ( (HorizontalPanel) container ).setCellWidth( fillerPanel, "50%" );
          }
          break;
        case END:
          break;
      }
    }
    initialized = true;
  }

  private native void printInnerHTML( Widget w )/*-{
                                                if(w){
                                                alert(w);
                                                }
                                                }-*/;

  public Orient getOrientation() {
    return this.orientation;
  }

  public void setOrient( String orientation ) {
    this.orientation = Orient.valueOf( orientation.toUpperCase() );
  }

  public String getOrient() {
    return orientation.toString();
  }

  public Object getManagedObject() {
    if ( managedObject == null ) {
      return managedObject;
    }

    if ( ( !StringUtils.isEmpty( getId() ) && ( managedObject instanceof UIObject ) ) ) {
      UIObject u = (UIObject) managedObject;
      if ( u.getElement() != null ) {
        ( (UIObject) managedObject ).getElement().setId( this.getId() );
      }
    }
    return managedObject;
  }

  public void setManagedObject( Object managed ) {
    managedObject = managed;
  }

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.setAttribute( "id", id );
    this.id = id;
    if ( managedObject != null && managedObject instanceof UIObject ) {
      ( (UIObject) managedObject ).getElement().setId( id );
    }
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex( int flex ) {
    this.flex = flex;
  }

  public void addComponent( XulComponent c ) {
    throw new UnsupportedOperationException( "addComponent not supported" );
  }

  public String getBgcolor() {
    return this.bgcolor;
  }

  public int getHeight() {
    return height;
  }

  public String getOnblur() {
    return onblur;
  }

  public int getPadding() {
    return padding;
  }

  public String getTooltiptext() {
    return tooltiptext;
  }

  public int getWidth() {
    return width;
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  public void setBgcolor( String bgcolor ) {
    this.bgcolor = bgcolor;
  }

  @Bindable
  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
  }

  public void setHeight( int height ) {
    this.height = height;
  }

  public void setOnblur( String method ) {
    this.onblur = method;
  }

  public void setPadding( int padding ) {
    this.padding = padding;
  }

  public void setTooltiptext( String tooltip ) {
    this.tooltiptext = tooltip;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public String getInsertbefore() {

    return insertbefore;
  }

  public void setInsertbefore( String insertbefore ) {

    this.insertbefore = insertbefore;
  }

  public String getInsertafter() {

    return insertafter;
  }

  public void setInsertafter( String insertafter ) {

    this.insertafter = insertafter;
  }

  public int getPosition() {

    return position;
  }

  public void setPosition( int position ) {

    this.position = position;
  }

  public boolean getRemoveelement() {
    return removeElement;
  }

  public void setRemoveelement( boolean flag ) {
    this.removeElement = flag;
  }

  public void addPropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( listener );
  }

  public void addPropertyChangeListener( String propertyName, PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( propertyName, listener );
  }

  public void removePropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.removePropertyChangeListener( listener );
  }

  protected void firePropertyChange( String attr, Object previousVal, Object newVal ) {
    if ( previousVal == null && newVal == null ) {
      return;
    }
    changeSupport.firePropertyChange( attr, previousVal, newVal );
  }

  @Bindable
  public boolean isVisible() {
    return this.visible;
  }

  @Bindable
  public void setVisible( boolean visible ) {
    this.visible = visible;

    if ( this.container != null ) {
      ( (Widget) this.container ).getElement().getStyle().setProperty( "display", ( this.visible ) ? "" : "none" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
  }

  public void onDomReady() {

  }

  public void resetContainer() {
  }

  protected void invoke( String method ) {
    invoke( method, null );
  }

  protected void invoke( String method, Object[] args ) {
    Document doc = getDocument();
    XulRoot window = (XulRoot) doc.getRootElement();
    XulDomContainer con = window.getXulDomContainer();

    try {
      if ( args == null ) {
        args = new Object[] {};
      }
      con.invoke( method, args );
    } catch ( XulException e ) {
      Window.alert( "Error calling oncommand event" + e.getMessage() );
    }
  }

  protected native void executeJS( String js )
  /*-{
    try{
      $wnd.eval(js);
    } catch (e){
      alert("Javascript Error: " + e.message+"\n\n"+js);
    }
  }-*/;

  public enum Property {
    TOOLTIP, FLEX, WIDTH, HEIGHT, VISIBLE, POSITION
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {
        case TOOLTIP:
          setTooltiptext( value );
          break;
        case FLEX:
          setFlex( Integer.valueOf( value ) );
          break;
        case WIDTH:
          setWidth( Integer.valueOf( value ) );
          break;
        case HEIGHT:
          setHeight( Integer.valueOf( value ) );
          break;
        case VISIBLE:
          setVisible( "true".equals( value ) );
          break;
        case POSITION:
          setPosition( Integer.valueOf( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in enum for: " + name + " in class" + getClass().getName() );
    }

  }

  @Override
  public void setAttribute( Attribute attribute ) {
    super.setAttribute( attribute );
    setAttribute( attribute.getName(), attribute.getValue() );
  }

  public void setAlign( String align ) {
    this.alignment = align;
  }

  public String getAlign() {
    return alignment;
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
    return this.menu;
  }

  public int getSpacing() {
    return 0;
  }

  public void setSpacing( int spacing ) {

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

  public void setBindingProvider( BindingProvider provider ) {
    this.bindingProvider = provider;
  }

  public void setDropvetoer( String dropVetoMethod ) {
    this.dropVetoMethod = dropVetoMethod;
  }

  public String getDropvetoer() {
    return dropVetoMethod;
  }

  public void adoptAttributes( XulComponent xulComponent ) {
    // To change body of implemented methods use File | Settings | File Templates.
  }
}
