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
