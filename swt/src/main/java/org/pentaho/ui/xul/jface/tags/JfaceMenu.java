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

package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;

@Deprecated // XXX: hope this isn't used anywhere :/ nothing really seems to do anything
public class JfaceMenu extends AbstractSwtXulContainer implements XulMenu {

//  private IMenuManager parentMenu;

  private String accel = null;

  private XulComponent parent;

  private PentahoMenuManager menu;

  public JfaceMenu( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menu" );

    orient = Orient.VERTICAL;

//    parentMenu = (MenuManager) parent.getManagedObject();
    this.parent = parent;
  }

  @Override
  public void setId( String id ) {
    this.id = id;
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }

//  private void addMenuChildren( MenuManager menuParent, List<XulComponent> children ) {
//
//    // System.out.println( "JfaceMenu adding children" );
//    for ( Element comp : children ) {
//
//      // TODO
//      /*
//       * for (XulComponent compInner : ((SwtMenupopup) comp).getChildNodes()) { if(compInner instanceof XulMenu){
//       * MenuItem item = new MenuItem(menuParent, SWT.CASCADE); Menu flyout = new Menu(shell, SWT.DROP_DOWN);
//       * item.setMenu(flyout); addMenuChildren(flyout, compInner.getChildNodes()); } else {
//       * 
//       * 
//       * 
//       * } }
//       */
//    }
//  }

  public void layout() {
    initialized = true;
  }

  //
  // @Override
  // public void addComponent(XulComponent c) {
  // super.addComponent(c);
  // if (initialized) {
  // resetContainer();
  // layout();
  // }
  // }

  public String getAcceltext() {
    return accel;
  }

  public String getAccesskey() {
    return null;
  }

  public boolean isDisabled() {
    return !menu.isEnabled();
  }

  public String getLabel() {
    return menu.getMenuText();
  }

  public void setAcceltext( String accel ) {
    this.accel = accel;
  }

  public void setAccesskey( String accessKey ) {
    if ( menu != null ) {
      int mask = 0;
      if ( accessKey.indexOf( "ctrl" ) > -1 ) {
        mask += SWT.CTRL;
      }
      if ( accessKey.indexOf( "shift" ) > -1 ) {
        mask += SWT.SHIFT;
      }
      if ( accessKey.indexOf( "alt" ) > -1 ) {
        mask += SWT.ALT;
      }
      String remainder =
          accessKey.replaceAll( "ctrl", "" ).replaceAll( "shift", "" ).replaceAll( "alt", "" ).replaceAll( "-", "" )
              .trim();
      if ( remainder.length() == 1 ) {
        mask += remainder.charAt( 0 );
      }
      // TODO
      // header.setAccelerator(mask);
    }
  }

  public void setDisabled( boolean disabled ) {
    super.setDisabled( disabled );
    menu.setEnabled( !disabled );
    /*
     * if(menu != null && menu.getMenu() != null){ menu.getMenu().setEnabled(!disabled);
     * menu.getMenu().getParentItem().setEnabled(!disabled); }
     */
  }

  public void setLabel( String label ) {
    // if(menubar != null){
    // TODO
    menu = new PentahoMenuManager( label, id );

    setManagedObject( menu );
    // menubar.add(header);

    // System.out.println( "JfaceMenu created menu: "+label );

    // }
  }

  @Override
  public void addChild( Element c ) {
    addChildAt( c, -1 );
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    if ( pos != -1 ) {
      super.addChildAt( c, pos );
    } else {
      super.addChild( c );
    }
    if ( c instanceof JfaceMenupopup ) {
      /*
       * JfaceMenupopup popup = (JfaceMenupopup) c; System.out.println("JfaceMenu adding popup "+popup.getId());
       * popup.setMenu(menu); IMenuManager menu = (IMenuManager) popup.getManagedObject(); if( pos == -1 ) {
       * header.add(menu); } else { header.insert(pos, menu); }
       */
    }
  }

  protected MenuManager getJfaceMenu() {
    return menu;
  }

  @Override
  public void setVisible( boolean visible ) {
    if ( menu != null ) {
      // System.out.println("JFaceMenu setting visible for "+menu.getId()+" "+getLabel()+" ("+menu.getClass().getSimpleName()+") to "+visible);
      menu.setVisible( visible );
      // } else {
      // System.out.println("JFaceMenu setting visible for null header");
    }
  }

  @Override
  public void removeChild( Element e ) {
    // System.out.println( "JfaceMenu.removeChild: "+e.getClass().getSimpleName() );
    if ( e instanceof XulMenuitem ) {
      // System.out.println( "removing menu item: "+((XulMenuitem)e).getLabel() );
    }
    if ( e instanceof XulMenupopup ) {
      // System.out.println( "removing menu popup: "+((XulMenupopup)e).getId() );
    }
    if ( e instanceof XulMenu ) {
      // System.out.println( "removing menu: "+((XulMenu)e).getId() );
    }
  }

}
