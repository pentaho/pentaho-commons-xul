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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulEditpanel;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class SwtEditpanel extends AbstractSwtXulContainer implements XulEditpanel {

  private Composite topForm;
  private CLabel lbl;
  private Label btn;
  private Composite body;
  private Composite mainComposite;
  private XulToolbar toolbar;
  private boolean collapsed;
  private Composite parentComposite;
  private int hiddenflex, hiddenWidth;
  private Composite toolbarPanel, buttonPanel, titlePanel;
  private Image rightImg, leftImg;
  private XulDomContainer domContainer;
  private TYPE type;

  public SwtEditpanel( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    setOrient( Orient.VERTICAL.toString() );
    this.domContainer = container;

    parentComposite = (Composite) parent.getManagedObject();
    mainComposite = new Composite( parentComposite, SWT.BORDER );
    GridData data = new GridData();
    data.verticalAlignment = SWT.FILL;
    data.grabExcessVerticalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;

    GridLayout layout = new GridLayout();
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    mainComposite.setLayout( layout );

    topForm = new Composite( mainComposite, SWT.NONE );
    // topForm.addPaintListener(new PaintListener(){
    // public void paintControl(PaintEvent arg0) {
    // GC gc = arg0.gc;
    // gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
    // gc.drawLine(0, topForm.getBounds().height-1, topForm.getBounds().width, topForm.getBounds().height-1);
    // }
    // });
    topForm.setBackground( Display.getCurrent().getSystemColor( SWT.COLOR_DARK_GRAY ) );

    layout = new GridLayout( 2, false );
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    topForm.setLayout( layout );

    GridData gData = new GridData();
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = false;
    gData.horizontalAlignment = SWT.FILL;
    gData.verticalAlignment = SWT.FILL;
    topForm.setLayoutData( gData );

    titlePanel = new Composite( topForm, SWT.NONE );
    titlePanel.setLayout( new FillLayout() );
    gData = new GridData();
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.horizontalAlignment = SWT.FILL;
    gData.verticalAlignment = SWT.FILL;
    titlePanel.setLayoutData( gData );

    buttonPanel = new Composite( topForm, SWT.NONE );
    buttonPanel.setLayout( new FillLayout() );
    gData = new GridData();
    gData.grabExcessHorizontalSpace = false;
    buttonPanel.setLayoutData( gData );

    toolbarPanel = new Composite( mainComposite, SWT.NONE );
    layout = new GridLayout();
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    toolbarPanel.setLayout( layout );

    gData = new GridData();
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalAlignment = SWT.FILL;
    gData.horizontalIndent = 0;
    gData.verticalIndent = 0;
    toolbarPanel.setLayoutData( gData );

    body = new Composite( mainComposite, SWT.None );

    data = new GridData();
    data.verticalAlignment = SWT.FILL;
    data.grabExcessVerticalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.verticalSpan = 10;
    data.horizontalIndent = 3;
    data.verticalIndent = 3;
    body.setLayoutData( data );

    data = new GridData();
    data.grabExcessVerticalSpace = false;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.verticalSpan = 0;
    topForm.setLayoutData( data );

    setManagedObject( mainComposite );
    setPadding( 0 );
    setSpacing( 0 );

  }

  protected Composite createNewComposite( Composite parent ) {
    return new Composite( parent, SWT.NONE );
  }

  @Override
  public void layout() {
    boolean toolbarFound = false;
    for ( XulComponent comp : getChildNodes() ) {
      if ( comp instanceof XulToolbar ) {
        toolbar = (XulToolbar) comp;
        toolbarFound = true;

        ( (Control) comp.getManagedObject() ).setParent( toolbarPanel );
        toolbarPanel.layout( true );

      } else if ( comp instanceof XulCaption ) {
        if ( lbl == null ) {
          lbl = new CLabel( titlePanel, SWT.None );
          lbl.setForeground( Display.getCurrent().getSystemColor( SWT.COLOR_WHITE ) );
          // Color[] inactiveGradient = new
          // Color[]{Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT),
          // Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND)
          // , null};
          // int[] gradPercent = new int[] {25,100};
          // //lbl.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
          // lbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
          //
          // lbl.setBackground(inactiveGradient, gradPercent);
          // lbl.addPaintListener(new PaintListener(){
          // public void paintControl(PaintEvent arg0) {
          // GC gc = arg0.gc;
          // gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
          // gc.drawLine(0, topForm.getBounds().height-1, topForm.getBounds().width, topForm.getBounds().height-1);
          // }
          // });
        }
        lbl.setText( ( (XulCaption) comp ).getLabel() );

      } else if ( comp.getManagedObject() instanceof Control ) {
        ( (Control) comp.getManagedObject() ).setParent( body );
      } else if ( comp.getManagedObject() instanceof Viewer ) {
        ( (Viewer) comp.getManagedObject() ).getControl().setParent( body );
      }

    }
    if ( toolbarFound == false ) {

      Object data = toolbarPanel.getLayoutData();
      if ( data instanceof GridData ) {
        ( (GridData) data ).exclude = true;
      }
      toolbarPanel.setLayoutData( data );
      toolbarPanel.setVisible( false );
      toolbarPanel.getParent().layout( true );
    }
    if ( type != null ) {
      btn = new Label( buttonPanel, SWT.NONE );

      if ( type == TYPE.COLLAPSIBLE && btn == null ) {

        this.rightImg =
            SwtXulUtil.getCachedImage( "org/pentaho/ui/xul/swt/tags/images/16x16_right.png", domContainer, buttonPanel
                .getDisplay() );
        this.leftImg =
            SwtXulUtil.getCachedImage( "org/pentaho/ui/xul/swt/tags/images/16x16_left.png", domContainer, buttonPanel
                .getDisplay() );

        btn.setImage( rightImg );
        btn.addMouseListener( new MouseAdapter() {
          @Override
          public void mouseUp( MouseEvent arg0 ) {
            collapse( !collapsed );
          }
        } );

      } else {

        Image rightImg =
            SwtXulUtil.getCachedImage( "org/pentaho/ui/xul/swt/tags/images/close.png", domContainer, buttonPanel
                .getDisplay() );

        btn.setImage( rightImg );
        btn.addMouseListener( new MouseAdapter() {
          @Override
          public void mouseUp( MouseEvent arg0 ) {
            close();
          }
        } );

      }
    } else {
      buttonPanel.dispose();
      ( (GridData) titlePanel.getLayoutData() ).horizontalSpan = 2;
      topForm.layout( true );
      topForm.pack();
    }
    setManagedObject( body );
    super.layout();
    setManagedObject( mainComposite );
  }

  public void close() {
    setVisible( false );
  }

  @Override
  public void setVisible( boolean visible ) {
    boolean prevVal = isVisible();
    super.setVisible( visible );
    changeSupport.firePropertyChange( "visible", prevVal, visible );
  }

  public boolean isCollapsed() {
    return collapsed;
  }

  public void collapse( boolean collapse ) {
    this.collapsed = collapse;
    body.setVisible( !collapsed );
    ( (GridData) body.getLayoutData() ).exclude = collapsed;
    if ( lbl != null ) {
      titlePanel.setVisible( !collapsed );
      ( (GridData) titlePanel.getLayoutData() ).exclude = collapsed;
    }
    // toolbarPanel.setVisible(!collapsed);

    if ( collapsed ) {
      btn.setImage( leftImg );
      hiddenflex = getFlex();
      hiddenWidth = getWidth();
      setFlex( 0 );
      setWidth( 0 );
    } else {
      btn.setImage( rightImg );
      setFlex( hiddenflex );
      setWidth( hiddenWidth );
    }
    ( (SwtElement) getParent() ).layout();
  }

  public void setCaption( String caption ) {
    if ( lbl != null ) {
      lbl.setText( caption );
    }
  }

  public String getType() {
    return type.toString();
  }

  public void setType( String type ) {
    this.type = TYPE.valueOf( type.toUpperCase() );
  }

  public void open() {
    setVisible( true );
  }

}
