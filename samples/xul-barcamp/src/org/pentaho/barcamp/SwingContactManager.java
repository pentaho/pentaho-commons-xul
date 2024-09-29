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
