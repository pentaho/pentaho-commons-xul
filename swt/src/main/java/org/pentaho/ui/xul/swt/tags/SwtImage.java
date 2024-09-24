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

import java.awt.image.BufferedImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.SwtSwingConversion;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class SwtImage extends SwtElement implements XulImage {

  private XulDomContainer domContainer;
  private XulComponent parent;
  private Label label;
  private String src;
  private static Log logger = LogFactory.getLog( SwtImage.class );

  public SwtImage( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    this.domContainer = container;
    this.parent = parent;
    label = new Label( ( (Composite) parent.getManagedObject() ), SWT.NONE );

    setManagedObject( label );
  }

  public String getSrc() {
    return src;
  }

  public void refresh() {

  }

  public void setSrc( String src ) {
    this.src = src;
    Image img = SwtXulUtil.getCachedImage( src, domContainer, ( (Composite) parent.getManagedObject() ).getDisplay() );
    if ( img != null ) {
      label.setImage( img );
    }
  }

  public void setSrc( Object img ) {
    if ( img instanceof String ) {
      setSrc( (String) img );
    } else if ( img instanceof BufferedImage ) {
      label.setImage( new Image( ( (Composite) parent.getManagedObject() ).getDisplay(), SwtSwingConversion
          .convertToSWT( (BufferedImage) img ) ) );
    }
  }

  @Override
  public void setVisible( boolean visible ) {
    label.setVisible( visible );
    label.getParent().layout( true );
  }

}
