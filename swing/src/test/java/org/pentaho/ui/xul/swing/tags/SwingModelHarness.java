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

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.swing.SwingBindingFactory;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swing.tags.models.TestModel;
import org.pentaho.ui.xul.swing.tags.models.TestModel.TestDocumentField;

/**
 * This harness demonstrates binding an AbstractModelList to a XUL table. Refer to the init() method for proper binding
 * of the model to the UI.
 * 
 * This harness also demonstrates enabling the context menu (insert, delete and keep only menus available in Swing
 * impl). Refer to the createNewRow() method for details.
 * 
 * @author gmoran
 * 
 */
public class SwingModelHarness extends AbstractXulEventHandler {

  BindingFactory bf = new SwingBindingFactory();
  TestModel model = new TestModel();
  Document document;

  public SwingModelHarness() {
    setName( "handler" );
    try {

      XulDomContainer container = new SwingXulLoader().loadXul( "documents/treecontextmenu.xul" );

      XulRunner runner = new SwingXulRunner();
      runner.addContainer( container );

      document = container.getDocumentRoot();
      container.addEventHandler( this );

      runner.initialize();
      runner.start();

      final TestDocumentField row1 = model.new TestDocumentField( true, "Nick", "bazz", "foo", "Testing" );
      final TestDocumentField row2 = model.new TestDocumentField( false, "Barb", "bazz", "bar", "Testing 2" );

      document.invokeLater( new Runnable() {
        public void run() {
          model.add( row1 );
          model.add( row2 );
        }
      } );

    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace( System.out );
    }
  }

  public static void main( String[] args ) {
    new SwingModelHarness();
  }

  public void init() {
    bf.setDocument( document );

    bf.setBindingType( Binding.Type.ONE_WAY );
    Binding fieldsBinding = bf.createBinding( model, "children", "testTable", "elements" );

    try {
      fieldsBinding.fireSourceChanged();
    } catch ( Exception e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * This method is bound by the "newitembinding" attribute of the XULTree definition.
   * 
   * example: <tree flex="1" newitembinding="createNewRow" ...
   * 
   * This will be invoked by the "insert row" UI interaction (currently a right-click context menu enabled by the
   * presence of the "newitembinding" attribute.
   * 
   */
  public void createNewRow() {
    TestDocumentField newRow = model.new TestDocumentField();
    model.add( newRow );
  }

}
