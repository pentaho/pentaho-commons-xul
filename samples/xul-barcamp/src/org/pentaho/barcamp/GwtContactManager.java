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
