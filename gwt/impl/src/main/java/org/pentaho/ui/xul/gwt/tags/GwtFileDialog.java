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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtFileDialog extends AbstractGwtXulContainer implements XulFileDialog {
  private FormPanel uploadForm = null;
  private FileUpload upload = null;
  private Panel parentObject = null;
  private XulComponent parent;
  private SEL_TYPE selectionType = SEL_TYPE.SINGLE;
  private VIEW_TYPE viewType = VIEW_TYPE.FILES_DIRECTORIES;

  public static void register() {
    GwtXulParser.registerHandler( "filedialog", //$NON-NLS-1$
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtFileDialog();
          }
        } );
  }

  public GwtFileDialog() {
    super( "filedialog" );
    uploadForm = new FormPanel();
    uploadForm.setEncoding( FormPanel.ENCODING_MULTIPART );
    uploadForm.setMethod( FormPanel.METHOD_POST );
    // Create a panel to hold all of the form widgets.
    VerticalPanel panel = new VerticalPanel();
    uploadForm.setWidget( panel );
    uploadForm.setVisible( false );
    // Create a FileUpload widget.
    upload = new FileUpload();
    upload.setName( "uploadFormElement" ); //$NON-NLS-1$
    panel.add( upload );
    RootPanel.get().add( uploadForm );
    setManagedObject( uploadForm );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( container != null ) {
      this.parent = container.getDocumentRoot().getRootElement();
      container.getDocumentRoot().getRootElement().addChild( this );
    }
  }

  public Object getFile() {
    return upload.getFilename();
  }

  public Object[] getFiles() {
    return null;
  }

  public SEL_TYPE getSelectionMode() {
    return selectionType;
  }

  public VIEW_TYPE getViewType() {
    return viewType;
  }

  public void setModalParent( Object parent ) {
    parentObject = (Panel) parent;
  }

  public void setSelectionMode( SEL_TYPE type ) {
    this.selectionType = type;
  }

  public void setViewType( VIEW_TYPE type ) {
    this.viewType = type;
  }

  public RETURN_CODE showOpenDialog() {
    doClick( upload.getElement() );
    if ( upload.getFilename() != null ) {
      return RETURN_CODE.OK;
    } else {
      return RETURN_CODE.CANCEL;
    }
  }

  public RETURN_CODE showOpenDialog( Object f ) {
    doClick( upload.getElement() );
    if ( upload.getFilename() != null ) {
      return RETURN_CODE.OK;
    } else {
      return RETURN_CODE.CANCEL;
    }
  }

  public native void doClick( com.google.gwt.user.client.Element element )/*-{
                                                                          element.click();
                                                                          }-*/;

  public RETURN_CODE showSaveDialog() {

    return null;
  }

  public RETURN_CODE showSaveDialog( Object f ) {
    // TODO Auto-generated method stub
    return null;
  }

  public FormPanel getUploadForm() {
    return uploadForm;
  }
}
