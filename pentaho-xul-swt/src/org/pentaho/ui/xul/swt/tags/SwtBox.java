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

import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
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
  protected String background, bgcolor;
  protected XulDomContainer container;
  private static Log logger = LogFactory.getLog( SwtBox.class );

  public SwtBox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    this( parent, tagName, container, Orient.HORIZONTAL );
  }

  public SwtBox( XulComponent parent, String tagName, XulDomContainer container, Orient orient ) {
    super( tagName );
    box = createNewComposite( (Composite) parent.getManagedObject() );
    box.setBackgroundMode( SWT.INHERIT_DEFAULT );
    setOrient( orient.toString() );
    this.container = container;
    setManagedObject( box );
  }

  protected Composite createNewComposite( Composite parent ) {
    return new Composite( parent, SWT.NONE );
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

  public void setBgcolor( String bgcolor ) {
    this.bgcolor = bgcolor;
    Color c = Color.decode( bgcolor );
    box.setBackground( new org.eclipse.swt.graphics.Color( box.getDisplay(), c.getRed(), c.getGreen(), c.getBlue() ) );
    box.setBackgroundMode( SWT.INHERIT_DEFAULT );
  }

}
