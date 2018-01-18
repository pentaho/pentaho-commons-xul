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

import com.google.gwt.user.client.ui.SimplePanel;
import org.pentaho.gwt.widgets.client.toolbar.Toolbar;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarGroup;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.components.XulToolbarspacer;
import org.pentaho.ui.xul.components.XulToolbarspring;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.containers.XulToolbarset;
import org.pentaho.ui.xul.containers.XulToolbar.ToolbarMode;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.Panel;

public class GwtToolbar extends AbstractGwtXulContainer implements XulToolbar {
  private String toolbarName;
  private ToolbarMode mode = ToolbarMode.ICONS;
  private Toolbar toolbar = new Toolbar();
  private boolean loaded;

  public static void register() {
    GwtXulParser.registerHandler( "toolbar", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbar();
      }
    } );
  }

  public GwtToolbar() {
    super( "toolbar" );
    setManagedObject( toolbar );
    this.container = toolbar;
  }

  public String getToolbarName() {
    return toolbarName;
  }

  public void setToolbarName( String name ) {
    this.toolbarName = name;
  }

  public void setMode( String mode ) {
    this.mode = ToolbarMode.valueOf( mode.toUpperCase() );
  }

  public String getMode() {
    return mode.toString();
  }

  @Override
  public void layout() {
    toolbar.removeAll();
    boolean flexLayout = false;
    for ( XulComponent c : this.getChildNodes() ) {
      if ( c.getFlex() > 0 ) {
        flexLayout = true;
      }
      add( c );
    }
    if ( !flexLayout ) {
      SimplePanel spacer = new SimplePanel();
      spacer.getElement().setAttribute( "flex", "1" );
      toolbar.add( spacer );
    }
    if ( !loaded ) {
      loaded = true;
    }
  }

  private void add( XulComponent c ) {
    if ( c instanceof XulToolbarset ) {
      ToolbarGroup group = (ToolbarGroup) c.getManagedObject();
      toolbar.add( group );
    } else if ( c instanceof XulToolbarbutton ) {
      toolbar.add( (ToolbarButton) c.getManagedObject() );
    } else if ( c instanceof XulToolbarseparator ) {
      toolbar.add( Toolbar.SEPARATOR );
    } else if ( c instanceof XulToolbarspring ) {
      toolbar.add( Toolbar.GLUE );
    } else if ( c instanceof XulToolbarspacer ) {
      Panel p = (Panel) c.getManagedObject();
      toolbar.add( p );
    }
  }

  @Override
  public void addChild( Element element ) {
    super.addChild( element );
    if ( loaded == true ) {
      // toolbar.removeAll();
      this.layout();
    }
  }

  @Override
  public void addChildAt( Element element, int idx ) {
    super.addChildAt( element, idx );
    if ( loaded == true ) {
      // toolbar.removeAll();
      this.layout();
    }
  }

  @Override
  public void removeChild( Element element ) {
    super.removeChild( element );
    XulComponent child = (XulComponent) element;
    if ( child instanceof XulToolbarbutton ) {
      toolbar.remove( ( (ToolbarButton) child.getManagedObject() ).getPushButton() );
    }

  }

}
