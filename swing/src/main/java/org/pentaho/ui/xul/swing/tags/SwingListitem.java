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

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 * 
 */
public class SwingListitem extends SwingElement implements XulListitem {

  private String label;
  private Object value;

  public SwingListitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "listitem" );
  }

  public String getLabel() {
    return label;
  }

  public void setLabel( String label ) {
    this.label = label;
    setManagedObject( this.label );
  }

  public String toString() {
    return this.label;
  }

  public void layout() {
  }

  public Object getValue() {
    return value;
  }

  public boolean isSelected() {
    return false;
  }

  public void setSelected( boolean selected ) {
  }

  public void setValue( Object value ) {
    this.value = value;
  }

}
