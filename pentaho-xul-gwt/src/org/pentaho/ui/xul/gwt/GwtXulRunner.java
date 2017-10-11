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

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.gwt.tags.GwtDialog;
import org.pentaho.ui.xul.gwt.tags.GwtWindow;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author OEM
 * 
 */
public class GwtXulRunner { // implements XulRunner {

  private Panel rootFrame;
  // private JFrame rootFrame;
  private List<XulDomContainer> containers;

  public GwtXulRunner() {
    containers = new ArrayList<XulDomContainer>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulRunner#initialize()
   */
  public void initialize() throws XulException {
    // get first Element, should be a JFrame and show it.
    XulComponent c = containers.get( 0 ).getDocumentRoot().getRootElement();
    if ( c instanceof XulWindow == false && c instanceof XulDialog == false ) {
      return;
    }
    XulRoot rootEle = (XulRoot) containers.get( 0 ).getDocumentRoot().getRootElement();
    String onLoad = rootEle.getOnload();
    if ( onLoad != null ) {
      String[] onloads = onLoad.split( "," );
      for ( String ol : onloads ) {
        containers.get( 0 ).invoke( ol.trim(), new Object[] {} );
      }
    }

    if ( rootEle instanceof GwtWindow ) {
      rootFrame = (Panel) ( (GwtWindow) rootEle ).getRootObject();
    //CHECKSTYLE IGNORE EmptyBlock FOR NEXT 3 LINES
    } else if ( rootEle instanceof GwtDialog ) {
      // do nothing
    } else {
      throw new XulException( "Root element not a Frame" );
    }
  }

  public Panel getRootPanel() {
    return rootFrame;
  }

  // /* (non-Javadoc)
  // * @see org.pentaho.ui.xul.XulRunner#remoteCall(org.pentaho.ui.xul.XulServiceCall)
  // */
  // public Document remoteCall(XulServiceCall serviceUrl) {
  // // TODO Auto-generated method stub
  // return null;
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulRunner#start()
   */
  public void start() {
    // rootFrame.pack();
    System.out.println( "Showing Dialog..." );
    // rootFrame.show();
    // rootFrame.setVisible(true);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulRunner#stop()
   */
  public void stop() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulRunner#addContainer(org.pentaho.ui.xul.XulWindowContainer)
   */
  public void addContainer( XulDomContainer xulDomContainer ) {
    this.containers.add( xulDomContainer );

  }

  public List<XulDomContainer> getXulDomContainers() {
    return containers;
  }

  // public static void main(String[] args) {
  // try{
  //
  // //InputStream in =
  // SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/sampleXul.xml");
  // InputStream in =
  // GwtXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/samples/datasource.xul");
  //
  // if(in == null){
  // System.out.println("Input is null");
  // System.exit(123);
  // }
  //
  // Document doc = CleanXmlHelper.getDocFromStream(in);
  //
  // XulDomContainer container = new GwtXulLoader().loadXul(doc);
  //
  // XulRunner runner = new GwtXulRunner();
  // runner.addContainer(container);
  //
  // runner.initialize();
  // runner.start();
  //
  // } catch(Exception e){
  // System.out.println(e.getMessage());
  // e.printStackTrace(System.out);
  // }
  // }

}
