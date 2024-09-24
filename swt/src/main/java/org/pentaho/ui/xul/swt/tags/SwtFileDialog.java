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

package org.pentaho.ui.xul.swt.tags;

import java.awt.Component;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtFileDialog extends SwtElement implements XulFileDialog {

  FileDialog fc;
  private SEL_TYPE selectionType = SEL_TYPE.SINGLE;
  private VIEW_TYPE viewType = VIEW_TYPE.FILES_DIRECTORIES;
  private String[] selectedFiles;
  private Component parentObject = null;
  private XulComponent parent;
  private XulDomContainer domContainer;
  private String selectedFile;
  private File fileHint;

  public SwtFileDialog( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "filedialog" );
    this.parent = domContainer.getDocumentRoot().getRootElement();
    domContainer.getDocumentRoot().getRootElement().addChild( this );
    this.domContainer = domContainer;

  }

  public File getFile() {
    return new File( fc.getFilterPath() + File.separator + fc.getFileName() );
  }

  public File[] getFiles() {
    String[] selected = fc.getFileNames();
    File[] retVal = new File[selected.length];
    int i = 0;
    for ( String s : selected ) {
      retVal[i++] = new File( fc.getFilterPath() + s );
    }
    return retVal;
  }

  public SEL_TYPE getSelectionMode() {
    return selectionType;
  }

  public VIEW_TYPE getViewType() {
    return viewType;
  }

  public void setSelectionMode( SEL_TYPE type ) {
    this.selectionType = type;
  }

  public void setViewType( VIEW_TYPE type ) {
    this.viewType = type;
  }

  public RETURN_CODE showOpenDialog() {
    return showOpen();
  }

  public RETURN_CODE showOpenDialog( Object f ) {
    if ( f != null ) {
      fileHint = (File) f;
    }
    return showOpen();
  }

  public RETURN_CODE showSaveDialog() {

    return showSave();
  }

  public RETURN_CODE showSaveDialog( Object f ) {
    if ( f != null ) {
      fileHint = (File) f;
    }
    return showSave();
  }

  private RETURN_CODE showOpen() {
    fc =
        new FileDialog( (Shell) ( (XulRoot) domContainer.getDocumentRoot().getRootElement() ).getRootObject(), SWT.OPEN );
    if ( fileHint != null ) {
      fc.setFileName( fileHint.getAbsolutePath() );
    }
    String retVal = fc.open();

    if ( retVal != null ) {
      if ( this.selectionType == SEL_TYPE.SINGLE ) {
        selectedFile = fc.getFileName();
      } else {
        selectedFiles = fc.getFileNames();
      }
      return RETURN_CODE.OK;
    } else {
      return RETURN_CODE.CANCEL;
    }

  }

  private RETURN_CODE showSave() {

    fc = new FileDialog( (Shell) domContainer.getDocumentRoot().getRootElement().getManagedObject(), SWT.SAVE );
    if ( fileHint != null ) {
      fc.setFileName( fileHint.getAbsolutePath() );
    }
    String retVal = fc.open();

    if ( retVal != null ) {
      if ( this.selectionType == SEL_TYPE.SINGLE ) {
        selectedFile = fc.getFileName();
      } else {
        selectedFiles = fc.getFileNames();
      }
      return RETURN_CODE.OK;
    } else {
      return RETURN_CODE.CANCEL;
    }
  }

  public void setModalParent( Object parent ) {
  }

}
