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


package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.MenuBarUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.FrameCover;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;

public class GwtMenubar extends AbstractGwtXulContainer implements XulMenubar {

  private String menubarName;
  private MenuBar menubar;
  private boolean loaded;
  private boolean vertical = true;
  private boolean scrollable = false;
  private boolean autoClose = false;
  private final int closeDelay = 100;
  private FrameCover frameCover = null;

  // dynamic values
  private boolean mouseOver = false;

  protected class MenuBar extends com.google.gwt.user.client.ui.MenuBar {
    private GwtMenubar gwtMenubar;

    public MenuBar( boolean vertical ) {
      super( vertical );
    }

    @Override
    protected MenuItem getSelectedItem() {
      return super.getSelectedItem();
    }

    @Override
    public void focus() {
      if ( gwtMenubar != null && ( gwtMenubar.getParent() instanceof GwtMenubar ) ) {
        super.closeAllChildren( false );
      }
      super.focus();
    }

    public void setGwtMenubar( GwtMenubar gwtMenubar ) {
      this.gwtMenubar = gwtMenubar;
    }

    public GwtMenubar getGwtMenubar() {
      return gwtMenubar;
    }

    protected native boolean hasChildPopupMenuShowing() /*-{
      var popup = this.@com.google.gwt.user.client.ui.MenuBar::popup;
      return popup != null && popup.@com.google.gwt.user.client.ui.PopupPanel::isShowing()();
    }-*/;
  }

  public static void register() {
    GwtXulParser.registerHandler( "menubar", new GwtXulHandler() {
      @Override
      public Element newInstance() {
        return new GwtMenubar();
      }
    } );
  }

  public GwtMenubar() {
    super( "menubar" );
  }

  public GwtMenubar( String tagName ) {
    super( tagName );
  }

  @Override
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    setHorizontal( "vertical".equalsIgnoreCase( srcEle.getAttribute( "layout" ) ) );
    setScrollable( "true".equalsIgnoreCase( srcEle.getAttribute( "scroll" ) ));
    frameCover = new FrameCover();

    menubar = new MenuBar( vertical ) {
      @Override
      public void onBrowserEvent( Event event ) {
        super.onBrowserEvent( event );
        switch ( DOM.eventGetType( event ) ) {
          case Event.ONCLICK:
            if ( scrollable ){
              doPopupScrollLogic();
            }
            break;
          case Event.ONKEYDOWN:
            if ( event.getKeyCode() == KeyCodes.KEY_TAB ) {
              GwtMenubar rootMenu = getRootMenu();
              if ( event.getShiftKey() ) {
                ElementUtils.tabPrevious( rootMenu.menubar.getElement() );
              } else {
                ElementUtils.tabNext( rootMenu.menubar.getElement() );
              }
              event.preventDefault();
            }
            break;
        }
        maybeCover();
      }
    };

    menubar.setGwtMenubar( this );

    frameCover.addClickHandler( new ClickHandler() {
      @Override
      public void onClick( ClickEvent event ) {
        closeAllChildren( false );
      }
    } );

    menubar.addCloseHandler( new CloseHandler<PopupPanel>() {
      @Override
      public void onClose( CloseEvent<PopupPanel> event ) {
        maybeUncover();
      }
    } );

    if ( autoClose ) {
      menubar.addDomHandler( new MouseOutHandler() {
        @Override
        public void onMouseOut( MouseOutEvent event ) {
          if ( isMouseOver() ) {
            setMouseOver( false );
            Scheduler.get().scheduleFixedDelay( new RepeatingCommand() {
              @Override
              public boolean execute() {
                hide();
                return false;
              }
            }, closeDelay );
          }
        }
      }, MouseOutEvent.getType() );

      menubar.addDomHandler( new MouseOverHandler() {
        @Override
        public void onMouseOver( MouseOverEvent event ) {
          setMouseOver( true );
        }
      }, MouseOverEvent.getType() );
    }

    setLabel( srcEle.getAttribute( "label" ) );
    setManagedObject( menubar );

    // init AFTER we set the managed object and we get "id" set for us
    super.init( srcEle, container );
  }

  private void doPopupScrollLogic() {
    String height, maxHeight;
    int left, top;
    DecoratedPopupPanel popup = MenuBarUtils.getPopup( menubar );
    if ( getSelectedItem() != null && popup != null && popup.isVisible() ) {
      left = 0;
      top = popup.getAbsoluteTop();
      height = "calc( 100vh - " + top + "px )";
      maxHeight = MenuBarUtils.calculatePopupHeight( getSelectedItem().getSubMenu() ) + "px";
      popup.getWidget().getElement().getStyle().setProperty( "height", height );
      popup.getWidget().getElement().getStyle().setProperty( "maxHeight", maxHeight );
      popup.setPopupPosition( left, top );
    }
  }

  private void maybeCover() {
    if ( !frameCover.isCovered() && getRootMenu().hasChildPopupMenuShowing() ) {
      frameCover.cover();
    }
  }

  private void maybeUncover() {
    if ( frameCover.isCovered() && !getRootMenu().hasChildPopupMenuShowing() ) {
      frameCover.remove();
    }
  }

  private void hide() {
    XulComponent parentComponent = getParent();
    if ( parentComponent instanceof GwtMenubar ) {
      if ( !isMouseOver() ) {
        MenuItem child = getSelectedItem();
        if ( child != null ) {
          MenuBar subMenu = (MenuBar) child.getSubMenu();
          if ( subMenu != null ) {
            if ( subMenu.getGwtMenubar().isMouseOver() ) {
              return;
            }
          }
        }
        GwtMenubar parentMenu = ( (GwtMenubar) parentComponent );
        if ( !parentMenu.isMouseOver() ) {
          parentMenu.closeAllChildren( false );
          parentMenu.hide();
        }
      }
    }
  }

  private GwtMenubar getRootMenu() {
    XulComponent parent = this.getParent();
    return parent instanceof GwtMenubar ? ( (GwtMenubar) parent ).getRootMenu() : this;
  }

  private boolean hasChildPopupMenuShowing() {
    return menubar.hasChildPopupMenuShowing();
  }

  protected void closeAllChildren( boolean focus ) {
    menubar.closeAllChildren( focus );
  }

  protected MenuItem getSelectedItem() {
    return menubar.getSelectedItem();
  }

  public void setLabel( String label ) {
    menubar.setTitle( label );
  }

  public String getLabel() {
    return menubar.getTitle();
  }

  public boolean isHorizontal() {
    return vertical;
  }

  public void setHorizontal( boolean horizontal ) {
    vertical = horizontal;
  }

  private void setScrollable( boolean scrollable ) {
    this.scrollable = scrollable;
  }

  public String getMenubarName() {
    return menubarName;
  }

  public void setMenubarName( String name ) {
    menubarName = name;
  }

  protected boolean isMouseOver() {
    return mouseOver;
  }

  protected void setMouseOver( boolean mouseOver ) {
    this.mouseOver = mouseOver;
  }

  public boolean isAutoClose() {
    return autoClose;
  }

  public void setAutoClose( boolean autoClose ) {
    this.autoClose = autoClose;
  }

  @Override
  @Bindable
  public void setVisible( boolean visible ) {
    this.visible = visible;
    menubar.getElement().getStyle().setProperty( "display", ( this.visible ) ? "" : "none" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    if ( getParent() instanceof GwtMenubar ) {
      ( (MenuBar) getParent().getManagedObject() ).clearItems();
      ( (GwtMenubar) getParent() ).layout();
    }
  }

  @Override
  public void layout() {
    menubar.clearItems();
    for ( XulComponent c : getChildNodes() ) {
      add( c );
    }
    if ( !loaded ) {
      loaded = true;
    }
  }

  private void add( XulComponent c ) {
    if ( c instanceof XulMenuitem ) {
      MenuItem item = (MenuItem) c.getManagedObject();
      menubar.addItem( item );
    } else if ( c instanceof XulMenuseparator ) {
      menubar.addSeparator();
    } else if ( c instanceof XulMenubar ) {
      if ( c.isVisible() ) {
        MenuBar bar = (MenuBar) c.getManagedObject();
        MenuItem submenu = new MenuItem( bar.getTitle(), bar );
        submenu.getElement().setId( bar.getElement().getId() );
        menubar.addItem( submenu );
      }
    }
  }

  @Override
  public void addChild( Element element ) {
    super.addChild( element );
    if ( loaded == true ) {
      menubar.clearItems();
      layout();
    }
  }

  @Override
  public void addChildAt( Element element, int idx ) {
    super.addChildAt( element, idx );
    if ( loaded == true ) {
      menubar.clearItems();
      layout();
    }
  }

  @Override
  public void removeChild( Element element ) {
    super.removeChild( element );
    XulComponent child = (XulComponent) element;
    if ( child instanceof XulMenuitem ) {
      menubar.removeItem( (MenuItem) child.getManagedObject() );
    }
  }
}
