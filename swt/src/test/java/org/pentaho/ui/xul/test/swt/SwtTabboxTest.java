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


package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

import org.eclipse.swt.SWT;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.tags.Style;
import org.pentaho.ui.xul.swt.tags.SwtTabpanel;

public class SwtTabboxTest {

  Document doc = null;
  XulDomContainer container;
  XulTabbox tabbox;

  @Before
  public void setUp() throws Exception {
    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwtXulLoader().loadXul( "documents/tabpanel.xul" );
    doc = container.getDocumentRoot();
    tabbox = (XulTabbox) doc.getElementById( "myTabList" );
  }

  @Test
  public void selectedIndexTest() {
    assertEquals( 2, tabbox.getSelectedIndex() );
  }

  @Test
  public void disableTabTest() {
    XulTab tab = (XulTab) doc.getElementById( "tab1" );
    tab.setDisabled( false );
    assertTrue( tab.isDisabled() == false );
  }

  @Test
  public void tabCountTest() {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    assertEquals( 4, tabs.getTabCount() );
  }

  @Test
  public void removeTabTest() {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    XulTab tab = (XulTab) doc.getElementById( "tab1" );
    tabs.removeChild( tab );
    assertEquals( 3, tabs.getTabCount() );
  }

  @Test
  public void getTabsTest() {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    assertEquals( tabs, tabbox.getTabs() );
  }

  @Test
  public void getTabPanelsTest() {
    XulTabpanels panels = (XulTabpanels) doc.getElementById( "tabpanels" );
    assertEquals( panels, tabbox.getTabpanels() );
  }

  @Test
  public void getSelectedPanelTest() {
    XulTabpanel panel = (XulTabpanel) doc.getElementById( "panel3" );
    assertEquals( panel, tabbox.getSelectedPanel() );
  }

  @Test
  public void removeTabpanelTest() {
    XulTabpanels panels = (XulTabpanels) doc.getElementById( "tabpanels" );
    XulTabpanel panel = (XulTabpanel) doc.getElementById( "panel3" );
    panels.removeChild( panel );
    assertEquals( 3, panels.getChildNodes().size() );
  }

  @Test
  public void getOverflowPropertyStyle() {
    XulTabpanel panel = (XulTabpanel) doc.getElementById( "panel1" );
    int overflow = Style.getOverflowProperty( ( (SwtTabpanel) panel ).getStyle() );
    assertEquals( overflow, SWT.V_SCROLL | SWT.H_SCROLL );
  }

  @Test
  public void getOverflowPropertyWithNullStyle() {
    int overflow = Style.getOverflowProperty( null );
    assertEquals( overflow, SWT.NONE );
  }
}
