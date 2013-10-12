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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwtWindow extends AbstractSwtXulContainer implements XulWindow {
  private static final long serialVersionUID = 6711745093238802441L;

  Shell shell;

  private int width;

  private int height;

  private String onload;
  private String onclose;
  private String onunload;

  private XulDomContainer xulDomContainer;

  private String title;

  private static final Log logger = LogFactory.getLog( SwtWindow.class );

  public SwtWindow( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );

    Shell possibleParent = null;

    orient = Orient.VERTICAL;

    if ( container.getOuterContext() != null && container.getOuterContext() instanceof Shell
        && ( self != null && self.getAttributeValue( "proxyoutercontext" ) != null ) ) {
      shell = (Shell) container.getOuterContext();
    } else {

      // First, check to see if an outer context was passed before parser started...
      if ( container.getOuterContext() != null && container.getOuterContext() instanceof Shell ) {
        possibleParent = (Shell) container.getOuterContext();
      }

      // If not, then try to use the API's parent parameter...
      if ( ( possibleParent == null ) && ( parent != null ) ) {
        possibleParent = (Shell) parent.getManagedObject();
      }

      // Otherwise, you're on your own...
      shell = ( possibleParent != null ) ? new Shell( possibleParent, SWT.SHELL_TRIM ) : new Shell( SWT.SHELL_TRIM );
    }

    shell.setLayout( new GridLayout() );
    shell.setBackgroundMode( SWT.INHERIT_DEFAULT );
    setManagedObject( shell );
    xulDomContainer = container;

  }

  public int getHeight() {
    return height;
  }

  public void setHeight( int height ) {
    this.height = height;
    shell.setSize( width, height );
  }

  public int getWidth() {
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
    shell.setSize( width, height );
  }

  public void setTitle( String title ) {
    this.title = title;
    Display.setAppName( title );
    shell.setText( title );

  }

  public String getTitle() {
    // TODO Auto-generated method stub
    return title;
  }

  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  public String getOnload() {
    return onload;
  }

  public void setOnload( final String method ) {
    this.onload = method;
    shell.addListener( XulRoot.EVENT_ON_LOAD, new Listener() {
      public void handleEvent( Event e ) {
        // invoke(method);
      }
    } );

  }

  /**
   * @deprecated This will be replaced by an agnostic listener pattern in the next version of Xul
   * @param event
   */
  @Deprecated
  public void notifyListeners( int event ) {
    if ( !shell.isDisposed() ) {
      shell.notifyListeners( event, new Event() );
    }
  }

  public void open() {
    shell.open();

    while ( !shell.isDisposed() ) {
      if ( !shell.getDisplay().readAndDispatch() ) {
        shell.getDisplay().sleep();
      }
    }
  }

  public void close() {
    shell.dispose();
  }

  public boolean isClosed() {
    return shell.isDisposed();
  }

  public void copy() throws XulException {
    throw new UnsupportedOperationException();
  }

  public void cut() throws XulException {
    throw new UnsupportedOperationException();
  }

  public void paste() throws XulException {
    throw new UnsupportedOperationException();
  }

  public void copy( String content ) throws XulException {
    throw new UnsupportedOperationException();
  }

  public Object getRootObject() {
    return shell;
  }

  public String getOnclose() {
    return onclose;
  }

  public String getOnunload() {
    return onunload;
  }

  public void setOnclose( String onclose ) {
    this.onclose = onclose;
  }

  public void setOnunload( String onunload ) {
    this.onunload = onunload;
  }

  public void invokeLater( Runnable runnable ) {
    shell.getDisplay().asyncExec( runnable );
  }

  public void setAppicon( String icon ) {

    // TODO Auto-generated method stub

  }

}
