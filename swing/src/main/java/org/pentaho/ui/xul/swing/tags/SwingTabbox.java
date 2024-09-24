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

package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.JTabbedPane;

import org.apache.commons.lang.NotImplementedException;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingTabbox extends AbstractSwingContainer implements XulTabbox {

  private JTabbedPane tabpane;

  private SwingTabpanels panels;

  private SwingTabs tabs;

  private int selectedIndex;

  public SwingTabbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabbox" );
    tabpane = new JTabbedPane();
    setManagedObject( tabpane );
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );

    if ( ele instanceof SwingTabs ) {
      this.tabs = (SwingTabs) ele;
    } else if ( ele instanceof SwingTabpanels ) {
      this.panels = (SwingTabpanels) ele;
    }

  }

  public int getSelectedIndex() {
    return tabpane.getSelectedIndex();
  }

  public XulTabpanel getSelectedPanel() {
    return panels.getTabpanelByIndex( getSelectedIndex() );
  }

  public XulTabpanels getTabpanels() {
    return panels;
  }

  public XulTabs getTabs() {
    return tabs;
  }

  public void setSelectedIndex( int index ) {
    selectedIndex = index;
    if ( tabpane.getTabCount() > 0 ) { // component instantiated
      tabpane.setSelectedIndex( selectedIndex );
    }
  }

  @Override
  public void layout() {
    tabpane.removeAll();
    for ( int i = 0; i < tabs.getChildNodes().size(); i++ ) {
      XulTabpanel panel = this.panels.getTabpanelByIndex( i );
      if ( panel == null ) {
        // no panel for tab
        continue;
      }
      tabpane.add( (Component) panel.getManagedObject(), tabs.getTabByIndex( i ).getLabel() );
      tabpane.setEnabledAt( i, !tabs.getTabByIndex( i ).isDisabled() );
    }
    tabpane.setSelectedIndex( selectedIndex );
    initialized = true;
  }

  public void setTabDisabledAt( boolean flag, int pos ) {
    tabpane.setEnabledAt( pos, !flag );
  }

  public void removeTab( int idx ) {
    if ( tabpane.getTabCount() > idx ) {
      tabpane.remove( idx );
    }
  }

  public void removeTabpanel( int idx ) {
    if ( tabpane.getTabCount() > idx ) {
      tabpane.remove( idx );
    }
  }

  public void addTab( int idx ) {
    throw new NotImplementedException( "addTab(int) not implemented in Swing" );
  }

  public void addTabpanel( int idx ) {
    throw new NotImplementedException( "addTabpanel(int) not implemented in Swing" );
  }

  public void setClosable( boolean flag ) {
    throw new NotImplementedException( "not implemented in Swing" );
  }

  public boolean isClosable() {
    throw new NotImplementedException( "not implemented in Swing" );
  }

  public void setOnclose( String command ) {
    throw new NotImplementedException( "not implemented in Swing" );
  }

}
