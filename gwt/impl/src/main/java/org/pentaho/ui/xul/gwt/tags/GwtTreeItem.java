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

import java.beans.PropertyChangeListener;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtTreeItem extends AbstractGwtXulContainer implements XulTreeItem {
  private boolean expanded;
  private String image;
  private Object obj;
  private String command;
  private String jscommand;
  private String classname;
  private String alttext;

  public static void register() {
    GwtXulParser.registerHandler( "treeitem", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeItem();
      }
    } );
  }

  public GwtTreeItem() {
    super( "treeitem" );
  }

  @Override
  public void layout() {
    super.layout();
  }

  @Override
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setCommand( srcEle.getAttribute( "command" ) );
    setJsCommand( srcEle.getAttribute( "js-command" ) );
  }

  public void setCommand( final String command ) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public String getJsCommand() {
    return jscommand;
  }

  public void setJsCommand( String jscommand ) {
    this.jscommand = jscommand;
  }

  public XulTreeRow getRow() {
    List list = getElementsByTagName( "treerow" );
    if ( list.size() > 0 ) {
      return (XulTreeRow) list.get( 0 );
    }
    return null;
  }

  public XulTree getTree() {
    XulTree tree = null;
    XulComponent parent = getParent();
    while ( parent != null ) {
      if ( parent instanceof XulTree ) {
        tree = (XulTree) parent;
        break;
      }
      parent = parent.getParent();
    }
    return tree;
  }

  public boolean isContainer() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void remove() {
    // TODO Auto-generated method stub

  }

  public void setContainer( boolean isContainer ) {
    // TODO Auto-generated method stub

  }

  public void setEmpty( boolean empty ) {
    // TODO Auto-generated method stub

  }

  public void setRow( XulTreeRow row ) {
    if ( getRow() != null ) {
      this.removeChild( getRow() );
    }
    this.addChild( row );
  }

  @Bindable
  public String getImage() {
    return image;
  }

  @Bindable
  public void setImage( String src ) {
    String oldSrc = this.image;
    this.image = src;
    firePropertyChange( "image", oldSrc, src );
  }

  @Bindable
  public boolean isExpanded() {
    return expanded;
  }

  @Bindable
  public void setExpanded( boolean expanded ) {
    this.expanded = expanded;
    XulTree tree = getTree();
    if ( tree != null ) {
      tree.setTreeItemExpanded( this, expanded );
    }

    changeSupport.firePropertyChange( "expanded", null, expanded );
  }

  public Object getBoundObject() {
    return obj;
  }

  public void setBoundObject( Object obj ) {
    this.obj = obj;
  }

  // TODO: migrate into XulComponent
  @Deprecated
  public void addPropertyChangeListener( String prop, PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( prop, listener );
  }

  @Bindable
  @Override
  public String getClassname() {
    return this.classname;
  }

  @Bindable
  @Override
  public void setClassname( String classname ) {
    String oldClassname = this.classname;
    this.classname = classname;
    firePropertyChange( "classname", oldClassname, classname ); //$NON-NLS-1$
  }

  @Bindable
  @Override
  public String  getAltText() {
    return this.alttext;
  }

  @Bindable
  @Override
  public void setAltText( String altText ) {
    String oldAltText = this.alttext;
    this.alttext = altText;
    firePropertyChange( "altText", oldAltText, altText ); //$NON-NLS-1$
  }
}
