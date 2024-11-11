/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.barcamp;

import org.pentaho.barcamp.controllers.MainController;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.gwt.GwtXulDomContainer;
import org.pentaho.ui.xul.gwt.GwtXulRunner;
import org.pentaho.ui.xul.gwt.binding.GwtBindingFactory;
import org.pentaho.ui.xul.gwt.util.AsyncXulLoader;
import org.pentaho.ui.xul.gwt.util.EventHandlerWrapper;
import org.pentaho.ui.xul.gwt.util.IXulLoaderCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtContactManager implements  EntryPoint {

  public GwtContactManager() {

  }

  public void onModuleLoad() {
    
    
    AsyncXulLoader.loadXulFromUrl("contactManager.xul", "contactManager", new IXulLoaderCallback(){

      public void overlayLoaded() {}

      public void overlayRemoved() {}

      public void xulLoaded(GwtXulRunner runner) {

        try {

          XulDomContainer container = runner.getXulDomContainers().get(0);
          GwtBindingFactory bf = new GwtBindingFactory(container.getDocumentRoot());

          MainController controller = new MainController();
          controller.setBindingFactory(bf);

          container.addEventHandler(controller);
          
          runner.initialize();
          runner.start();

          Widget root = (Widget) ((XulComponent) container.getDocumentRoot().getRootElement()).getManagedObject();

          RootPanel.get().add(root);
        } catch (XulException e) {
          e.printStackTrace();
        }
      }
      
    });
    
  }

}
