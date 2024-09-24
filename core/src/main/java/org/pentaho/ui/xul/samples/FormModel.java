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

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulEventSourceAdapter;

public class FormModel extends XulEventSourceAdapter {

  private String firstName = "";
  private String lastName = "";
  private boolean disabled = true;
  private boolean enabled = true;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName( String firstName ) {
    String previous = this.firstName;
    this.firstName = firstName;
    firePropertyChange( "firstName", previous, firstName );
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName( String lastName ) {
    String previous = this.lastName;
    this.lastName = lastName;
    firePropertyChange( "lastName", previous, lastName );
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    boolean previous = this.disabled;
    this.disabled = disabled;
    firePropertyChange( "disabled", previous, disabled );
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled( boolean enabled ) {
    boolean previous = this.enabled;
    this.enabled = enabled;
    firePropertyChange( "enabled", previous, enabled );
  }

}
