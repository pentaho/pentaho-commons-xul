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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtWindow extends AbstractGwtXulContainer implements XulWindow {

  static final String ELEMENT_NAME = "window"; //$NON-NLS-1$

  protected String onclose, onunload, title;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtWindow();
      }
    } );
  }

  // DialogBox frame;

  protected com.google.gwt.user.client.ui.Panel frame;

  private int width;

  private int height;

  private String onload;

  public GwtWindow() {
    super( ELEMENT_NAME );
    // managedObject = box = new DialogBox();
    this.orientation = Orient.VERTICAL;
    updateOrientation();
  }

  private void updateOrientation() {
    if ( getOrientation() == Orient.HORIZONTAL ) {
      frame = new HorizontalPanel();
    } else {
      frame = new VerticalPanel();
    }
    container = frame;
    setManagedObject( container );
  }

  public void setOrient( String orient ) {
    super.setOrient( orient );
    updateOrientation();
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( srcEle.hasAttribute( "title" ) && srcEle.getAttribute( "title" ).trim().length() > 0 ) {
      setTitle( srcEle.getAttribute( "title" ) );
    }
    if ( srcEle.hasAttribute( "height" ) && srcEle.getAttribute( "height" ).trim().length() > 0 ) {
      try {
        setHeight( Integer.parseInt( srcEle.getAttribute( "height" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    if ( srcEle.hasAttribute( "width" ) && srcEle.getAttribute( "width" ).trim().length() > 0 ) {
      try {
        setWidth( Integer.parseInt( srcEle.getAttribute( "width" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    if ( srcEle.hasAttribute( "onload" ) && srcEle.getAttribute( "onload" ).trim().length() > 0 ) {
      setOnload( srcEle.getAttribute( "onload" ) );
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#setTitle(java.lang.String)
   */
  public void setTitle( String title ) {
    // frame.setTitle(title);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#getHeight()
   */
  public int getHeight() {
    // TODO Auto-generated method stub
    return height;
  }

  public void setHeight( int height ) {
    frame.setHeight( height + "px" );
    this.height = height;
    // frame.setSize(new Dimension(this.width, this.height));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#getWidth()
   */
  public int getWidth() {
    // TODO Auto-generated method stub
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
    frame.setWidth( width + "px" );
    // frame.setSize(new Dimension(this.width, this.height));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulWindow#invoke(java.lang.String, java.lang.Object[])
   */
  public void invoke( String method, Object[] args ) {

    // TODO: need a way to dynamically call into methods

    try {
      if ( method.indexOf( '.' ) == -1 ) {
        throw new IllegalArgumentException( "method call does not follow the pattern [EventHandlerID].methodName()" );
      }

      method = method.replaceAll( "\\(\\)", "" );
      String[] pair = method.split( "\\." );
      String eventID = pair[0];
      String methodName = pair[1];

      // XulEventHandler eventHandler = this.xulDomContainer.getEventHandlerProxy(eventID);
      // Method m = eventHandler.getClass().getMethod(methodName, new Class[0]);
      // EventHandlerProxy eventHandler = ((GwtXulDomContainer)xulDomContainer).getEventHandlerProxy(eventID);
      // if (eventHandler != null) {
      // eventHandler.invoke(methodName, args);
      // } else {
      // System.out.println("No event handler found for " + method);
      // }
      // m.invoke(eventHandler, args);
      //
    } catch ( Exception e ) {
      System.out.println( "Error invoking method: " + method );
      e.printStackTrace( System.out );
    }
  }

  // public void setRootDocument(Document document) {
  // this.rootDocument = document;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulWindow#getOnload(java.lang.String)
   */
  public String getOnload() {
    return onload;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulWindow#setOnload(java.lang.String)
   */
  public void setOnload( String onload ) {
    this.onload = onload;

  }

  @Override
  public void layout() {

    // wait to materialize view until now
    // if (getOrientation() == Orient.HORIZONTAL) {
    // container = new HorizontalPanel();
    // } else {
    // container = new VerticalPanel();
    // }
    // container.setHeight("100%");
    // container.setWidth("100%");

    frame.clear();
    // frame.setWidget(container);

    super.layout();
  }

  public void close() {
    frame.setVisible( false );
  }

  public void open() {
    frame.setVisible( true );
  }

  public void copy() throws XulException {

    // TODO Auto-generated method stub

  }

  public void copy( String content ) throws XulException {

    // TODO Auto-generated method stub

  }

  public void cut() throws XulException {

    // TODO Auto-generated method stub

  }

  public boolean isClosed() {
    return false;
  }

  public void paste() throws XulException {

    // TODO Auto-generated method stub

  }

  public void setAppicon( String icon ) {

    // TODO Auto-generated method stub

  }

  public String getOnclose() {
    return onclose;
  }

  public String getOnunload() {
    return onunload;
  }

  public Object getRootObject() {
    return this.frame;
  }

  public String getTitle() {
    return this.title;
  }

  public void invokeLater( Runnable runnable ) {

    // TODO Auto-generated method stub

  }

  public void setOnclose( String onclose ) {

    // TODO Auto-generated method stub

  }

  public void setOnunload( String onunload ) {

    // TODO Auto-generated method stub

  }

  public void addComponentAt( XulComponent component, int idx ) {

    // TODO Auto-generated method stub

  }

  public void removeComponent( XulComponent component ) {

    // TODO Auto-generated method stub

  }

}
