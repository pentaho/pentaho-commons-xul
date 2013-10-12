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

package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Point;
import java.io.File;

import javax.swing.JFileChooser;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingFileDialog extends SwingElement implements XulFileDialog {

  JFileChooser fc;
  private SEL_TYPE selectionType = SEL_TYPE.SINGLE;
  private VIEW_TYPE viewType = VIEW_TYPE.FILES_DIRECTORIES;
  private File selectedFile;
  private File[] selectedFiles;
  private Component parentObject = null;
  private XulComponent parent;

  public SwingFileDialog( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "filedialog" );
    this.parent = domContainer.getDocumentRoot().getRootElement();
    domContainer.getDocumentRoot().getRootElement().addChild( this );
  }

  public File getFile() {
    return fc.getSelectedFile();
  }

  public File[] getFiles() {
    return fc.getSelectedFiles();
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
    fc = new JFileChooser();
    return showOpen();
  }

  public RETURN_CODE showOpenDialog( Object f ) {
    fc = new JFileChooser( (File) f );
    return showOpen();
  }

  public RETURN_CODE showSaveDialog() {

    fc = new JFileChooser();
    return showSave();
  }

  public RETURN_CODE showSaveDialog( Object f ) {
    fc = new JFileChooser( (File) f );
    return showSave();
  }

  private RETURN_CODE showOpen() {
    Point loc = getParentObject().getLocation();
    switch ( this.getViewType() ) {
      case FILES_DIRECTORIES:
        fc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        break;
      case DIRECTORIES:
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        break;
      default:
        fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
    }

    int retVal = fc.showOpenDialog( getParentObject() );

    switch ( retVal ) {
      case JFileChooser.APPROVE_OPTION:
        if ( this.selectionType == SEL_TYPE.SINGLE ) {
          selectedFile = fc.getSelectedFile();
        } else {
          selectedFiles = fc.getSelectedFiles();
        }
        return RETURN_CODE.OK;
      case JFileChooser.CANCEL_OPTION:
      default:
        return RETURN_CODE.CANCEL;
    }
  }

  private RETURN_CODE showSave() {
    fc.setLocation( getParentObject().getLocation() );
    int retVal = fc.showSaveDialog( getParentObject() );
    switch ( retVal ) {
      case JFileChooser.APPROVE_OPTION:
        selectedFile = fc.getSelectedFile();
        return RETURN_CODE.OK;
      case JFileChooser.CANCEL_OPTION:
      default:
        return RETURN_CODE.CANCEL;
    }
  }

  public void setModalParent( Object parent ) {
    parentObject = (Component) parent;
  }

  private Component getParentObject() {
    if ( parentObject != null ) {
      return parentObject;
    } else {
      return (Component) this.parent.getManagedObject();
    }
  }
}
