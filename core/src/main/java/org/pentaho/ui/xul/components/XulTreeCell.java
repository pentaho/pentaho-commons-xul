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


package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulTreeRow;

public interface XulTreeCell extends XulComponent {

  public void setEditable( boolean edit );

  public boolean isEditable();

  public void setLabel( String label );

  public String getLabel();

  public void setSrc( String srcUrl );

  public String getSrc();

  public void setValue( Object value );

  public Object getValue();

  public void setSelectedIndex( int index );

  public int getSelectedIndex();

  public void setTreeRowParent( XulTreeRow row );
}
