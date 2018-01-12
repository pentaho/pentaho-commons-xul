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
}
