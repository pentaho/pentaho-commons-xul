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

package org.pentaho.barcamp;

import org.pentaho.barcamp.controllers.MainController;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.BindingException;
import org.pentaho.ui.xul.binding.BindingExceptionHandler;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.binding.DefaultBindingFactory;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingContactManager {

  public SwingContactManager() {
    try {
      // Load the document
      XulDomContainer container = new SwingXulLoader().loadXul("org/pentaho/barcamp/xul/contactManager.xul");


      // Binding Factories are used to make the creation of many bindings less verbose. We generally give one to all
      // of our controllers. There are specific implementations for Swing and SWT that ensure events targeting UI
      // components happen in the event thread.
      BindingFactory bf = new DefaultBindingFactory();
      bf.setDocument(container.getDocumentRoot());

      // Any exceptions that happen in the event thread need to be managed
      bf.setExceptionHandler(new BindingExceptionHandler(){
        public void handleException(BindingException t) {
          t.printStackTrace();
        }
      });

      // Create our main Controller
      MainController controller = new MainController();
      controller.setBindingFactory(bf);
      container.addEventHandler(controller);

      // Start it up!
      final XulRunner runner = new SwingXulRunner();
      runner.addContainer(container);

      // Initialize calls any onload methods specified on the top level element (dialog or window)
      runner.initialize();

      // Start will display the window or dialog if you want, we mostly never call this.
      runner.start();


    } catch (XulException e) {
      System.out.println("error loading Xul application");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new SwingContactManager();
  }
}
