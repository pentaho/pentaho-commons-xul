/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Copyright 2009 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.barcamp;

import org.pentaho.barcamp.controllers.MainController;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.binding.DefaultBindingFactory;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtContactManager {

  public SwtContactManager() {
    try {
      // Load the document
      XulDomContainer container = new SwtXulLoader().loadXul("org/pentaho/barcamp/xul/contactManager.xul");

      // Create a Binding Factory
      BindingFactory bf = new DefaultBindingFactory();
      bf.setDocument(container.getDocumentRoot());

      // Create our main Controller
      MainController controller = new MainController();
      controller.setBindingFactory(bf);
      container.addEventHandler(controller);

      // Start it up!
      final XulRunner runner = new SwtXulRunner();
      runner.addContainer(container);

      runner.initialize();
      runner.start();

    } catch (XulException e) {
      System.out.println("error loading Xul application");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new SwtContactManager();
  }
}
