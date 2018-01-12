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

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;

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
import org.pentaho.ui.xul.swing.SwingXulLoader;

public class SwingTabboxTest {

  Document doc = null;
  XulDomContainer container;
  XulTabbox tabbox;

  @Before
  public void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/tabpanel.xul" );
    doc = container.getDocumentRoot();
    tabbox = (XulTabbox) doc.getElementById( "myTabList" );

  }

  @Test
  public void selectedIndexTest() throws Exception {

    assertEquals( 2, tabbox.getSelectedIndex() );
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    XulTab tab = (XulTab) doc.getElementById( "tab1" );
  }

  @Test
  public void disableTabTest() throws Exception {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    XulTab tab = (XulTab) doc.getElementById( "tab1" );

    tab.setDisabled( false );
    assertTrue( tab.isDisabled() == false );

  }

  @Test
  public void tabCountTest() throws Exception {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );

    assertEquals( 4, tabs.getTabCount() );

  }

  @Test
  public void removeTabTest() throws Exception {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );
    XulTab tab = (XulTab) doc.getElementById( "tab1" );
    tabs.removeChild( tab );
    assertEquals( 3, tabs.getTabCount() );

  }

  @Test
  public void getTabsTest() throws Exception {
    XulTabs tabs = (XulTabs) doc.getElementById( "tabs" );

    assertEquals( tabs, tabbox.getTabs() );

  }

  @Test
  public void getTabPanelsTest() throws Exception {
    XulTabpanels panels = (XulTabpanels) doc.getElementById( "tabpanels" );

    assertEquals( panels, tabbox.getTabpanels() );

  }

  @Test
  public void getSelectedPanelTest() throws Exception {
    XulTabpanel panel = (XulTabpanel) doc.getElementById( "panel3" );
    assertEquals( panel, tabbox.getSelectedPanel() );
  }

  @Test
  public void removeTabpanelTest() throws Exception {
    XulTabpanels panels = (XulTabpanels) doc.getElementById( "tabpanels" );
    XulTabpanel panel = (XulTabpanel) doc.getElementById( "panel3" );
    panels.removeChild( panel );
    assertEquals( 3, panels.getChildNodes().size() );

  }

}
