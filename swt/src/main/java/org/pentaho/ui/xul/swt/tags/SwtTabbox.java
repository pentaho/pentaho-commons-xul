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


package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTabbox extends AbstractSwtXulContainer implements XulTabbox {

  private CTabFolder tabFolder;

  private SwtTabpanels panels;

  private SwtTabs tabs;

  private int selectedIndex = -1;

  private boolean closable;

  private String onclose;

  private XulDomContainer domContainer;

  // used to prevent recursing.
  private boolean suppressRemoveEvents;

  public SwtTabbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabbox" );
    int style = SWT.MULTI;
    if ( self.getAttributeValue( "closable" ) != null && self.getAttributeValue( "closable" ).equals( "true" ) ) {
      style |= SWT.CLOSE;
    }
    this.domContainer = domContainer;

    tabFolder = new CTabFolder( (Composite) parent.getManagedObject(), style );
    tabFolder.setLayoutData( new GridData( GridData.FILL_BOTH ) );

    tabFolder.setSimple( false );
    tabFolder.setUnselectedImageVisible( true );
    tabFolder.setUnselectedCloseVisible( true );
    tabFolder.setBorderVisible( true );

    tabFolder.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent arg0 ) {
        int prevVal = selectedIndex;
        selectedIndex = tabFolder.getSelectionIndex();

        // expect to find exactly one tabs child node of this tabbox node
        XulTabs tabs = null;
        for ( XulComponent c : getChildNodes() ) {
          if ( c instanceof XulTabs ) {
            tabs = (XulTabs) c;
          }
        }

        XulTab tab = (XulTab) tabs.getChildNodes().get( selectedIndex );
        if ( StringUtils.isNotBlank( tab.getOnclick() ) ) {
          SwtTabbox.this.invoke( tab.getOnclick() );
        }

        SwtTabbox.this.changeSupport.firePropertyChange( "selectedIndex", prevVal, selectedIndex );
      }

    } );

    tabFolder.addCTabFolder2Listener( new CTabFolder2Listener() {

      public void close( CTabFolderEvent arg0 ) {
        if ( onclose != null ) {
          try {
            int pos = 0;
            for ( int i = 0; i < tabFolder.getItems().length; i++ ) {
              if ( tabFolder.getItems()[i] == arg0.item ) {
                pos = i;
                break;
              }
            }
            Boolean returnVal = (Boolean) SwtTabbox.this.domContainer.invoke( onclose, new Object[] { pos } );
            if ( returnVal == true ) {
              remove( pos );
            } else {
              arg0.doit = false;
            }
          } catch ( XulException e ) {
            e.printStackTrace();
          }
        } else {
          remove( tabFolder.getSelectionIndex() );
        }

      }

      public void maximize( CTabFolderEvent arg0 ) {
      }

      public void minimize( CTabFolderEvent arg0 ) {
      }

      public void restore( CTabFolderEvent arg0 ) {
      }

      public void showList( CTabFolderEvent arg0 ) {
      }
    } );

    // Set a small vertical gradient
    tabFolder.setSelectionBackground( new Color[] {
      tabFolder.getDisplay().getSystemColor( SWT.COLOR_WIDGET_NORMAL_SHADOW ),
      tabFolder.getDisplay().getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ), }, new int[] { 55, }, true );

    setManagedObject( tabFolder );
  }

  private void remove( int pos ) {
    suppressRemoveEvents = true;
    this.tabs.removeChild( this.tabs.getChildNodes().get( pos ) );
    this.panels.removeChild( this.panels.getChildNodes().get( pos ) );
    if ( tabs.getChildNodes().size() == 0 ) { // last one doesn't fire selection event. Manually do that here
      setSelectedIndex( -1 );
    }
    suppressRemoveEvents = false;
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );

    if ( ele instanceof SwtTabs ) {
      this.tabs = (SwtTabs) ele;
    } else if ( ele instanceof SwtTabpanels ) {
      this.panels = (SwtTabpanels) ele;
    }

  }

  public int getSelectedIndex() {
    return tabFolder.getSelectionIndex();
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
    int prevVal = selectedIndex;
    selectedIndex = index;
    SwtTabbox.this.changeSupport.firePropertyChange( "selectedIndex", prevVal, selectedIndex );
    if ( initialized ) { // component instantiated
      tabFolder.setSelection( selectedIndex );

      // Programatic set selectedIndex does not fire listener.
      int sel = tabFolder.getSelectionIndex();
    }
  }

  @Override
  public void layout() {

    CTabItem[] t = tabFolder.getItems();

    for ( int i = 0; i < t.length; i++ ) {
      if ( !t[i].isDisposed() ) {
        t[i].dispose();
      }
    }

    for ( int i = 0; i < tabs.getChildNodes().size(); i++ ) {
      int style = SWT.None;
      int tabIndex = 0;
      if ( isClosable() ) {
        style = SWT.Close;
      }
      SwtTab tab = (SwtTab) tabs.getChildNodes().get( i );
      if ( tab.isVisible() ) {
        CTabItem item = new CTabItem( tabFolder, style );
        item.setText( tabs.getTabByIndex( i ).getLabel() );
        // There could be a chance that the tabs have been added but tab panel are still
        // not there. In this case we need to break from this layout routine
        if ( panels.getChildNodes().size() <= i ) {
          break;
        }

        //Set Scrollable tab if overflow is found
        if ( ( (SwtTabpanel) panels.getTabpanelByIndex( i ) ).getStyle() != null ) {
          item.setControl( setScrollableTab( (SwtTabpanel) panels.getTabpanelByIndex( i ) ) );
        } else {
          item.setControl( (Control) panels.getTabpanelByIndex( i ).getManagedObject() );
        }

        tabFolder.getItem( tabIndex++ ).getControl().setEnabled( !tabs.getTabByIndex( i ).isDisabled() );
      }
    }
    initialized = true;
    tabFolder.layout( true, true );
    if ( selectedIndex < 0 && tabFolder.getItemCount() > 0 ) {
      selectedIndex = 0;
    }
    setSelectedIndex( selectedIndex );
  }

  public void setTabDisabledAt( boolean flag, int pos ) {
    tabFolder.getItem( pos ).getControl().setEnabled( !flag );
  }

  public void setTabVisibleAt( boolean flag, int pos ) {
    tabFolder.getItem( pos ).getControl().setVisible( flag );
  }

  public void updateTabState() {
    for ( int i = 0; i < tabs.getChildNodes().size(); i++ ) {
      tabFolder.getItem( i ).setText( "" + tabs.getTabByIndex( i ).getLabel() );
      Control control = tabFolder.getItem( i ).getControl();
      if ( control != null ) {
        tabFolder.getItem( i ).getControl().setEnabled( !tabs.getTabByIndex( i ).isDisabled() );
      }
    }
  }

  public void removeTab( int idx ) {
    if ( suppressRemoveEvents ) {
      return;
    }
    if ( tabFolder.getItemCount() >= idx ) {
      tabFolder.getItem( idx ).dispose();
    }
  }

  public void removeTabpanel( int idx ) {
    if ( suppressRemoveEvents ) {
      return;
    }
    if ( tabFolder.getItemCount() > idx ) {
      tabFolder.getItem( idx ).getControl().dispose();
    }
  }

  public void addTab( int idx ) {
    int style = SWT.None;
    if ( isClosable() ) {
      style = SWT.Close;
    }
    CTabItem item = new CTabItem( tabFolder, style );
    String lbl = tabs.getTabByIndex( idx ).getLabel();
    if ( lbl != null ) {
      item.setText( lbl );
    }

    // may have been added after panel
    // addTabpanel(idx);
    if ( selectedIndex < 0 ) {
      selectedIndex = 0;
    }
    setSelectedIndex( selectedIndex );
  }

  public void addTabpanel( int idx ) {

    // not sure if the tab has been added first, ignore if not
    if ( tabFolder.getItemCount() <= idx || panels.getChildNodes().size() <= idx ) {
      return;
    }
    CTabItem item = tabFolder.getItem( idx );
    Control control = (Control) panels.getTabpanelByIndex( idx ).getManagedObject();
    if ( control.getParent() != tabFolder ) {
      control.setParent( tabFolder );
    }
    item.setControl( control );
    item.getControl().setEnabled( !tabs.getTabByIndex( idx ).isDisabled() );

  }

  public void setClosable( boolean flag ) {
    this.closable = flag;

  }

  public boolean isClosable() {
    return this.closable;
  }

  public void setOnclose( String command ) {
    this.onclose = command;
  }

  private ScrolledComposite setScrollableTab( SwtTabpanel tabpanel ) {
    int overflow = Style.getOverflowProperty( tabpanel.getStyle() );
    ScrolledComposite scrolledComposite = new ScrolledComposite( tabFolder, overflow );
    scrolledComposite.setLayout( new GridLayout() );
    scrolledComposite.setLayoutData( new GridData() );
    Composite composite = (Composite) tabpanel.getFirstChild().getManagedObject();
    composite.setBackground( Display.getCurrent().getSystemColor( SWT.COLOR_WHITE ) );
    composite.setParent( scrolledComposite );
    scrolledComposite.setContent( composite );
    Rectangle bounds = composite.getBounds();
    scrolledComposite.setExpandHorizontal( true );
    scrolledComposite.setExpandVertical( true );
    scrolledComposite.setMinWidth( bounds.width );
    scrolledComposite.setMinHeight( bounds.height );
    return scrolledComposite;
  }
}
