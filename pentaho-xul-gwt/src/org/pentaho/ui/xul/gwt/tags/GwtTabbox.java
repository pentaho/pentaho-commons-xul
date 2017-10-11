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

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.widgets.GwtTabWidget;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

public class GwtTabbox extends AbstractGwtXulContainer implements XulTabbox {

  static final String ELEMENT_NAME = "tabbox"; //$NON-NLS-1$

  private TabPanel tabPanel;

  private GwtTabPanels panels;

  private GwtTabs tabs;

  private int selectedIndex;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabbox();
      }
    } );
  }

  public GwtTabbox() {
    this( Orient.HORIZONTAL );
  }

  public GwtTabbox( Orient orient ) {
    super( ELEMENT_NAME );
    tabPanel = new TabPanel();
    setManagedObject( tabPanel );
  }

  public void addChild( Element ele ) {
    super.addChild( ele );

    if ( ele instanceof GwtTabs ) {
      this.tabs = (GwtTabs) ele;
    } else if ( ele instanceof GwtTabPanels ) {
      this.panels = (GwtTabPanels) ele;
    }

  }

  @Bindable
  public int getSelectedIndex() {
    return selectedIndex;
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

  @Bindable
  public void setSelectedIndex( int index ) {
    int previousValue = selectedIndex;
    selectedIndex = index;
    if ( tabPanel.getWidgetCount() > 0 ) { // component instantiated
      tabPanel.selectTab( index );
      firePropertyChange( "selectedIndex", previousValue, index );
    }
  }

  public void layout() {
    for ( int i = 0; i < tabPanel.getWidgetCount(); i++ ) {
      tabPanel.remove( i );
    }

    for ( int i = 0; i < tabs.getChildNodes().size(); i++ ) {
      XulTabpanel panel = this.panels.getTabpanelByIndex( i );
      if ( panel == null ) {
        // no panel for tab
        continue;
      }
      String tooltipText = tabs.getTabByIndex( i ).getTooltiptext();
      if ( StringUtils.isEmpty( tooltipText ) ) {
        tooltipText = "";
      }
      GwtTabWidget widget = new GwtTabWidget( tabs.getTabByIndex( i ).getLabel(), tooltipText,
              tabPanel, ( (Widget) panel.getManagedObject() ) );
      Widget panelWidget = (Widget) panel.getManagedObject();
      panelWidget.setStylePrimaryName( "pentaho-tabPanel" );
      tabPanel.add( panelWidget, widget );
    }
    setSelectedIndex( selectedIndex );
    initialized = true;
    tabPanel.getTabBar().setStylePrimaryName( "pentaho-tabBar" );
    tabPanel.addBeforeSelectionHandler( new BeforeSelectionHandler<Integer>() {

      public void onBeforeSelection( BeforeSelectionEvent<Integer> event ) {
        if ( event != null && event.getItem() >= 0 ) {
          try {
            final String onBeforeSelectMethod = ( (GwtTab) tabs.getTabByIndex( event.getItem() ) ).getOnBeforeSelect();
            if ( StringUtils.isEmpty( onBeforeSelectMethod ) == false ) {
              Object returnValue =
                  GwtTabbox.this.getXulDomContainer().invoke( onBeforeSelectMethod, new Object[] { event.getItem() } );
              if ( returnValue != null && returnValue instanceof Boolean ) {
                Boolean value = (Boolean) returnValue;
                if ( !value ) {
                  event.cancel();
                }
              }
            }
          } catch ( XulException e ) {
            e.printStackTrace();
          }
        }
      }

    } );

    tabPanel.addSelectionHandler( new SelectionHandler<Integer>() {
      public void onSelection( final SelectionEvent<Integer> event ) {
        if ( event != null && event.getSelectedItem() >= 0 ) {
          try {
            final String onClickMethod = tabs.getTabByIndex( event.getSelectedItem() ).getOnclick();
            if ( onClickMethod != null ) {
              GwtTabbox.this.getXulDomContainer().invoke( onClickMethod, new Object[] {} );
            }

          } catch ( XulException e ) {
            e.printStackTrace();
          }
        }
      }

    } );
  }

  public void setTabDisabledAt( boolean flag, int pos ) {

  }

  public void removeTab( int idx ) {
    if ( tabPanel.getWidgetCount() > idx ) {
      tabPanel.remove( idx );
    }
  }

  public void removeTabpanel( int idx ) {
    if ( tabPanel.getWidgetCount() > idx ) {
      tabPanel.remove( idx );
    }
  }

  public void addTab( int idx ) {
  }

  public void addTabpanel( int idx ) {
  }

  public void setClosable( boolean flag ) {
  }

  public boolean isClosable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setOnclose( String command ) {
    throw new RuntimeException( "not implemented" );
  }
}
