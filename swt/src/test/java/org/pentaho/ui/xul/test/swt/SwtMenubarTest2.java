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

package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.XulEventHandler;
import org.pentaho.ui.xul.jface.tags.ApplicationWindowLocal;
import org.pentaho.ui.xul.swt.SwtXulLoader;

public class SwtMenubarTest2 implements XulEventHandler {

  static Document doc = null;
  static XulDomContainer container;
  static XulMenupopup list;
  static final TestApplicationWindow window = new TestApplicationWindow();

  public static void main( String[] args ) throws IllegalArgumentException, XulException {

    ApplicationWindowLocal.setApplicationWindow( window );
    container = new SwtXulLoader().loadXul( "documents/menutest.xul" );
    doc = container.getDocumentRoot();
    new SwtMenubarTest2();
    window.run();
  }

  public SwtMenubarTest2() {

    container.addEventHandler( this );

    list = (XulMenupopup) doc.getElementById( "edit-popup" );

  }

  @BeforeClass
  public static void setUp() throws Exception {

    ApplicationWindowLocal.setApplicationWindow( window );

    container = new SwtXulLoader().loadXul( "resource/documents/menutest.xul" );
    doc = container.getDocumentRoot();

    new Thread( new Runnable() {
      public void run() {
        window.run();
      }
    } );

  }

  @AfterClass
  public static void shutdown() {
    window.close();
  }

  @Test
  public void testGetElements() throws Exception {
    assertEquals( 5, list.getChildNodes().size() );
  }

  @Override
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "mainController";
  }

  @Override
  public XulDomContainer getXulDomContainer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setData( Object arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setName( String arg0 ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setXulDomContainer( XulDomContainer arg0 ) {
    // TODO Auto-generated method stub

  }

  public void toggleVisible() {

    XulMenu menu = (XulMenu) doc.getElementById( "test-menu" );

    XulComponent parent = menu.getParent();
    while ( !( parent instanceof XulMenubar ) ) {
      parent = parent.getParent();
    }
    MenuManager manager = (MenuManager) parent.getManagedObject();

    for ( IContributionItem item : manager.getItems() ) {
      item.setVisible( false );
    }

    manager.updateAll( true );

  }

  public void cut() {
    System.out.println( "Cut called" );
  }

  public void copy() {
    System.out.println( "Copy called" );
  }

  public void close() {
    System.out.println( "Close called" );
    window.close();
  }

  public void refresh() {
    System.out.println( "Refresh called" );
  }

}
