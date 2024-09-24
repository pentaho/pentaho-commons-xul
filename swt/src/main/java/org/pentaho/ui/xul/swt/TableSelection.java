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

package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;

/**
 * Values used for table and tree selection modes.
 * 
 * @author gmoran
 */
public enum TableSelection {

  SINGLE( SWT.SINGLE | SWT.FULL_SELECTION ), MULTIPLE( SWT.MULTI | SWT.FULL_SELECTION ),

  // TODO: No true equivalent, need to investigate if this behavior is do-able in SWT
  // XUL defines "cell" level selection as:
  // cell: Individual cells can be selected. (This means only a single cell at a time).
  CELL( SWT.SINGLE ), TEXT( SWT.SINGLE ), NONE( SWT.NONE );

  private final int equivalent;

  private TableSelection( int swtEquivalent ) {
    this.equivalent = swtEquivalent;
  }

  public int getSwtStyle() {
    return equivalent;
  }

}
