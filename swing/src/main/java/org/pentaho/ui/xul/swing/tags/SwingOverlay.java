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


/**
 *
 */

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

/**
 * @author nbaker
 */
public class SwingOverlay extends AbstractSwingContainer implements XulScript {
  private String id;
  private String src;

  public SwingOverlay( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
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
