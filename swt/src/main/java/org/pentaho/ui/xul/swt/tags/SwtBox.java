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


package org.pentaho.ui.xul.swt.tags;

import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class SwtBox extends AbstractSwtXulContainer implements XulBox {
  private static final long serialVersionUID = 582736100041411600L;

  protected Composite box;
  protected String background, bgcolor, style;
  protected XulDomContainer container;
  private static Log logger = LogFactory.getLog( SwtBox.class );

  public SwtBox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    this( self, parent, tagName, container, Orient.HORIZONTAL );
  }

  public SwtBox( Element self, XulComponent parent, String tagName, XulDomContainer container,
                 Orient orient ) {
    super( tagName );
    style = self != null ? self.getAttributeValue( "style" ) : "";
    box = createNewComposite( (Composite) parent.getManagedObject() );
    box.setBackgroundMode( SWT.INHERIT_DEFAULT );
    setOrient( orient.toString() );
    this.container = container;
    setManagedObject( box );
  }

  protected Composite createNewComposite( Composite parent ) {
    int overflowProperty = style != null ? Style.getOverflowProperty( style ) : SWT.NONE;
    if ( overflowProperty == SWT.NONE ) {
      return new Composite( parent, SWT.NONE );
    } else {
      ScrolledComposite scrolledComposite = new ScrolledComposite( parent, overflowProperty );
      scrolledComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
      Composite composite = new Composite( scrolledComposite, SWT.NONE );
      composite.setLayout( new GridLayout() );
      scrolledComposite.setContent( composite );
      scrolledComposite.setExpandHorizontal( true );
      scrolledComposite.setExpandVertical( true );
      return composite;
    }
  }

  public String getBackground() {
    return background;
  }

  public void setBackground( String background ) {

    this.background = background;
    Image backgroundImg = SwtXulUtil.getCachedImage( background, container, box.getDisplay() );

    if ( backgroundImg != null ) {
      box.setBackgroundMode( SWT.INHERIT_DEFAULT );
      box.setBackgroundImage( backgroundImg );
    }

  }

  public String getBgcolor() {
    return bgcolor;
  }

  @Override public void layout() {
    super.layout();
    Composite composite = (Composite) getManagedObject();
    if ( composite.getParent() instanceof  ScrolledComposite ) {
      ( (ScrolledComposite) composite.getParent() ).setMinSize( composite.computeSize( SWT.DEFAULT, SWT.DEFAULT ) );
    }
  }

  public void setBgcolor( String bgcolor ) {
    this.bgcolor = bgcolor;
    Color c = Color.decode( bgcolor );
    box.setBackground( new org.eclipse.swt.graphics.Color( box.getDisplay(), c.getRed(), c.getGreen(), c.getBlue() ) );
    box.setBackgroundMode( SWT.INHERIT_DEFAULT );
  }

}
