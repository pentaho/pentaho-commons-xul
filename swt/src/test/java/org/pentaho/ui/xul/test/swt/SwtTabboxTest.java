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
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */

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
