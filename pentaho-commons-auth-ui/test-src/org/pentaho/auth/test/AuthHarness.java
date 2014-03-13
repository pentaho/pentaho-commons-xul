/*
 * !
 *  * This program is free software; you can redistribute it and/or modify it under the
 *  * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 *  * Foundation.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public License along with this
 *  * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  * or from the Free Software Foundation, Inc.,
 *  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *  *
 *  * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  * See the GNU Lesser General Public License for more details.
 *  *
 *  * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 *
 */

package org.pentaho.auth.test;

import java.io.PrintStream;
import org.pentaho.auth.controller.AuthProviderController;
import org.pentaho.auth.model.NoAuthAuthProvider;
import org.pentaho.ui.xul.swt.SwtBindingFactory;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class AuthHarness
{
  public static void main(String[] args)
  {
    try
    {
      AuthProviderController controller = new AuthProviderController(new SwtXulLoader(), new SwtBindingFactory(), new SwtXulRunner());
      controller.setNewOverlay(new NoAuthAuthProvider());
      controller.open();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }
}
