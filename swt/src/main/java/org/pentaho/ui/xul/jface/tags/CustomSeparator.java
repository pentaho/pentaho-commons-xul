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



package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;

public class CustomSeparator extends Separator implements IContributionItem {

  private String id;

  public void setId( String id ) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
