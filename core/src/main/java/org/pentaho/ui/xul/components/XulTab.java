/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulTab extends XulComponent {

  public void setLabel( String label );

  public String getLabel();

  public void setOnclick( String onClick );

  public String getOnclick();

  public void setDisabled( boolean disabled );

  public boolean isDisabled();

}
