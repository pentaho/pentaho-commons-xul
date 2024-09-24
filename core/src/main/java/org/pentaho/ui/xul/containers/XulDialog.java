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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.containers;

public interface XulDialog extends XulRoot {

  public void setButtons( String buttons );

  public String getButtons();

  public void setButtonlabelcancel( String label );

  public String getButtonlabelcancel();

  public void setButtonlabelaccept( String label );

  public String getButtonlabelaccept();

  public void setButtonlabelextra1( String label );

  public String getButtonlabelextra1();

  public void setButtonlabelextra2( String label );

  public String getButtonlabelextra2();

  public void setOndialogaccept( String command );

  public String getOndialogaccept();

  public void setOndialogcancel( String command );

  public String getOndialogcancel();

  public void setOndialogextra1( String command );

  public String getOndialogextra1();

  public void setOndialogextra2( String command );

  public String getOndialogextra2();

  public void setButtonalign( String align );

  public String getButtonalign();

  public void show();

  public void hide();

  public boolean isHidden();

  public void setVisible( boolean visible );

  public Boolean getResizable();

  public void setResizable( Boolean resizable );

  public void setModal( Boolean modal );

  public void setPack( boolean pack );

  public boolean isPack();

  public void center();

  default void doAutoFocus() {
    // NOOP
  };
}
