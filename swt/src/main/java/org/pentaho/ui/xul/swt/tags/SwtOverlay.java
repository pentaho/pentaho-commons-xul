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


/**
 *
 */

package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

/**
 * @author nbaker
 */
public class SwtOverlay extends AbstractSwtXulContainer implements XulScript {
  private static final Log logger = LogFactory.getLog( SwtOverlay.class );
  private String id;
  private String src;

  public SwtOverlay( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    Composite c = new Composite( (Composite) ( (XulComponent) parent ).getManagedObject(), SWT.NONE );
    setManagedObject( c );

  }

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc( String className ) {
    this.src = className;
  }

  public void layout() {
  }

}
