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

package org.pentaho.ui.xul.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swt.tags.SwtDialog;
import org.pentaho.ui.xul.swt.tags.SwtWindow;

public class SwtXulRunner implements XulRunner {

  private Composite rootFrame;
  private List<XulDomContainer> containers;

  public SwtXulRunner() {
    containers = new ArrayList<XulDomContainer>();
  }

  public XulComponent getElementById( String id ) {
    return null;
  }

  public XulComponent getElementsById( String id ) {
    return null;
  }

  public void initialize() throws XulException {

    // TODO Should initialize return a status? Reply: Just let them catch the Exception

    XulComponent rootEle = containers.get( 0 ).getDocumentRoot().getRootElement();

    if ( rootEle instanceof SwtWindow ) {
      rootFrame = (Composite) rootEle.getManagedObject();
    } else if ( rootEle instanceof SwtDialog ) {
      rootFrame = ( (SwtDialog) rootEle ).getShell();
    } else {
      throw new XulException( "Unexpected root element: " + rootEle.getManagedObject().toString() );
    }

    // call the onLoads
    rootFrame.getDisplay().syncExec( new Runnable() {

      public void run() {
        containers.get( 0 ).initialize();
      }

    } );

  }

  public void start() throws XulException {
    ( (Shell) rootFrame ).open();
    while ( !rootFrame.isDisposed() ) {
      // process the next event, wait when none available
      if ( !rootFrame.getDisplay().readAndDispatch() ) {
        rootFrame.getDisplay().sleep();
      }
    }
  }

  public void stop() throws XulException {
    if ( ( (Shell) rootFrame ).isDisposed() ) {
      return;
    }
    ( (Shell) rootFrame ).dispose();
  }

  public void addContainer( XulDomContainer xulDomContainer ) {
    this.containers.add( xulDomContainer );
  }

  public List<XulDomContainer> getXulDomContainers() {
    return containers;
  }

  public void debug() throws XulException {

    // TODO Auto-generated method stub

  }

}
