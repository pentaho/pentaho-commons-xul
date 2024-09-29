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
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.util.ColumnType;

import java.util.List;

public interface XulTreeCol extends XulComponent {

  public void setEditable( boolean edit );

  public boolean isEditable();

  public void setFixed( boolean fixed );

  public boolean isFixed();

  public void setHidden( boolean hide );

  public boolean isHidden();

  public void setLabel( String label );

  public String getLabel();

  public void setPrimary( boolean primo );

  public boolean isPrimary();

  public void setSortActive( boolean sort );

  public boolean isSortActive();

  public void setSortDirection( String dir );

  public String getSortDirection();

  public void setSrc( String srcUrl );

  public String getSrc();

  public void setType( String type );

  public String getType();

  public ColumnType getColumnType();

  public void setWidth( int width );

  public int getWidth();

  public void autoSize();

  public String getCustomeditor();

  public void setCustomeditor( String customClass );

  public void setBinding( String binding );

  public String getBinding();

  public void setChildrenbinding( String childProperty );

  public String getChildrenbinding();

  public String getCombobinding();

  public void setCombobinding( String property );

  public String getColumntypebinding();

  public void setColumntypebinding( String property );

  public List<InlineBindingExpression> getBindingExpressions();

  public String getDisabledbinding();

  public void setDisabledbinding( String property );

  public String getImagebinding();

  public void setImagebinding( String img );

  public String getComparatorbinding();

  public void setComparatorbinding( String comp );

  public String getExpandedbinding();

  public void setExpandedbinding( String bind );

  public String getTooltipbinding();

  public void setTooltipbinding( String bind );

  public String getClassnameBinding();

  public void setClassnameBinding( String classname );

  default String getAlttextBinding() {
    throw new UnsupportedOperationException( "getAltTextBinding() not implemented" );
  }

  default void setAlttextBinding( String altText ) {
    throw new UnsupportedOperationException( "setAltTextBinding( altText ) not implemented" );
  }
}
