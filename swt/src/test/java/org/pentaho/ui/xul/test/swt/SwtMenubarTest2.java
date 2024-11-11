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
