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
