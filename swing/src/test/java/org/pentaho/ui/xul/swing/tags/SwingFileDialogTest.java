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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swing.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.GraphicsEnvironment;
import java.io.File;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.components.XulFileDialog.SEL_TYPE;
import org.pentaho.ui.xul.components.XulFileDialog.VIEW_TYPE;
import org.pentaho.ui.xul.containers.XulMenu;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulLoader;

import javax.swing.SwingUtilities;

public class SwingFileDialogTest {

  static Document doc = null;
  static XulDomContainer container;
  static XulMenu menu;
  static SwingFileDialog dialog;

  @BeforeClass
  public static void setUp() throws Exception {

    // Do not run on headless environment
    Assume.assumeTrue( !GraphicsEnvironment.isHeadless() );

    container = new SwingXulLoader().loadXul( "documents/menutest.xul" );

    doc = container.getDocumentRoot();
    menu = (XulMenu) doc.getElementById( "file-menu" );

    dialog = (SwingFileDialog) doc.createElement( "filedialog" );
    dialog.setModalParent( container.getDocumentRoot().getFirstChild().getManagedObject() );

  }

  private void validateDialog( Thread msgThread ) throws InterruptedException {
    msgThread.start();
    final boolean[] valid = { false };
    SwingUtilities.invokeLater( new Thread( () -> {
      do {
        try {
          Thread.sleep( 100 );
        } catch ( InterruptedException ignored ) { }
      } while ( dialog.fc == null || !dialog.fc.isShowing() );
      valid[ 0 ] = dialog.fc.isValid();
      dialog.fc.cancelSelection();
    } ) );

    msgThread.join();
    assertTrue( valid[0] );
  }

  @Test
  public void showFileOpenTest() throws Exception {
    Thread msgThread = new Thread( dialog::showOpenDialog );
    validateDialog( msgThread );
  }

  @Test
  public void showFileOpenTest2() throws Exception {
    Thread msgThread = new Thread( () -> dialog.showOpenDialog( new File( "../" ) ) );
    validateDialog( msgThread );
  }

  @Test
  public void showFileSaveTest() throws Exception {
    Thread msgThread = new Thread( dialog::showSaveDialog );
    validateDialog( msgThread );
  }

  @Test
  public void showFileSaveTest2() throws Exception {
    Thread msgThread = new Thread( () -> dialog.showSaveDialog( new File( "../" ) ) );
    validateDialog( msgThread );
  }

  @Test
  public void testSelType() throws Exception {
    final XulFileDialog dialog = (XulFileDialog) doc.createElement( "filedialog" );
    dialog.setSelectionMode( SEL_TYPE.SINGLE );
    assertEquals( SEL_TYPE.SINGLE, dialog.getSelectionMode() );

    dialog.setSelectionMode( SEL_TYPE.MULTIPLE );
    assertEquals( SEL_TYPE.MULTIPLE, dialog.getSelectionMode() );
  }

  @Test
  public void testViewType() throws Exception {
    final XulFileDialog dialog = (XulFileDialog) doc.createElement( "filedialog" );
    dialog.setViewType( VIEW_TYPE.DIRECTORIES );
    assertEquals( VIEW_TYPE.DIRECTORIES, dialog.getViewType() );

    dialog.setViewType( VIEW_TYPE.FILES_DIRECTORIES );
    assertEquals( VIEW_TYPE.FILES_DIRECTORIES, dialog.getViewType() );
  }

}
