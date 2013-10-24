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
* Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
*/

package org.pentaho.ui.xul.gwt.sample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.XMLParser;
import org.pentaho.gwt.widgets.client.utils.i18n.IResourceBundleLoadCallback;
import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;
import org.pentaho.ui.xul.gwt.GwtXulDomContainer;
import org.pentaho.ui.xul.gwt.GwtXulLoader;
import org.pentaho.ui.xul.gwt.GwtXulRunner;

public class ExampleXulApp implements EntryPoint, IResourceBundleLoadCallback {

  private ResourceBundle bundle;

  public void onModuleLoad() {
    try {
      bundle = new ResourceBundle( "", "toolbar", true, this );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public void displayXulDialog( String xul ) {
    try {

      GwtXulLoader loader = new GwtXulLoader();
      GwtXulRunner runner = new GwtXulRunner();

      com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse( xul );
      GwtXulDomContainer container;

      if ( bundle != null ) {
        container = loader.loadXul( gwtDoc, bundle );
      } else {
        container = loader.loadXul( gwtDoc );
      }

      //      EventHandlerWrapper wrapper = GWT.create(TestController.class);
      //      TestController instance = new TestController();
      //      wrapper.setHandler(instance);
      //
      //      container.addEventHandler(wrapper);
      container.addEventHandler( new SampleDnDEventHandler() );
      //      container.addEventHandler(new SampleEventHandler2());

      runner.addContainer( container );
      runner.initialize();
      RootPanel.get().add( runner.getRootPanel() );
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public void bundleLoaded( String bundleName ) {
    try {

      RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, "tree.xul" );

      try {
        Request response = builder.sendRequest( null, new RequestCallback() {
          public void onError( Request request, Throwable exception ) {
            // Code omitted for clarity
          }

          public void onResponseReceived( Request request, Response response ) {

            displayXulDialog( response.getText() );
            // Code omitted for clarity
          }
        } );
      } catch ( RequestException e ) {
        // Code omitted for clarity
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
