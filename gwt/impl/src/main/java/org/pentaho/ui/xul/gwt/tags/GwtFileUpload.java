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

import java.util.HashMap;
import java.util.Map;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulFileUpload;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.tags.util.LabelWidget;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.DOM;

public class GwtFileUpload extends AbstractGwtXulContainer implements XulFileUpload {
  private String uploadSuccessMethod, uploadFailureMethod;
  public static final String ERROR = ".ERROR_"; //$NON-NLS-1$
  private FormPanel uploadForm = null;
  private FileUpload upload = null;
  private VerticalPanel uploadPanel;
  private VerticalPanel mainPanel;
  private HTMLPanel hiddenPanel;
  private GwtLabel uploadTextBox;
  private String action;
  private Map<String, String> parameters = null;
  private static final String ELEMENT_NAME = "pen:fileupload"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtFileUpload();
      }
    } );
  }

  public GwtFileUpload() {
    super( ELEMENT_NAME );
    setManagedObject( new VerticalPanel() );
    this.parameters = new HashMap<String, String>();
  }

  private String buildActionUrl( String moduleBaseUrl, String anAction ) {
    String url = moduleBaseUrl;
    while ( anAction.indexOf( "../" ) >= 0 && url.lastIndexOf( "/" ) > -1 ) {
      url = url.substring( 0, url.lastIndexOf( "/" ) );
      anAction = anAction.substring( 3 );
    }
    url += "/" + anAction;
    return url;
  }

  @SuppressWarnings( "deprecation" )
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    mainPanel = new VerticalPanel();
    setManagedObject( mainPanel );

    super.init( srcEle, container );
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "action" ) ) ) {
      setAction( buildActionUrl( GWT.getModuleBaseURL(), srcEle.getAttribute( "action" ) ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "onuploadsuccess" ) ) ) {
      setOnUploadSuccess( srcEle.getAttribute( "onuploadsuccess" ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "onuploadfailure" ) ) ) {
      setOnUploadFailure( srcEle.getAttribute( "onuploadfailure" ) );
    }

    uploadForm = new FormPanel();
    uploadForm.setEncoding( FormPanel.ENCODING_MULTIPART );
    uploadForm.setMethod( FormPanel.METHOD_POST );
    uploadForm.setHeight( getHeight() + "px" );
    uploadForm.setWidth( getWidth() + "px" );
    // Create a panel to hold all of the form widgets.
    HorizontalPanel panel = new HorizontalPanel();
    uploadForm.setWidget( panel );
    uploadForm.setVisible( true );
    // Create a FileUpload widget.
    upload = new FileUpload();
    upload.setStylePrimaryName( "gwt-StyledFileUpload" );
    upload.setName( "uploadFormElement" ); //$NON-NLS-1$
    upload.getElement().setId( "uploadFormElement" );
    upload.setVisible( true );
    upload.setHeight( getHeight() + "px" );
    upload.setWidth( getWidth() + "px" );
    upload.addChangeHandler( new ChangeHandler() {
      public void onChange( ChangeEvent event ) {
        setSelectedFile( upload.getFilename() );
      }
    } );
    uploadPanel = new VerticalPanel();

    // -- upload styling -- //
    String uploadButtonImage = srcEle.getAttribute( "image" );
    String uploadButtonDisabledImage = srcEle.getAttribute( "disabledimage" );

    hiddenPanel = new HTMLPanel( "<div id='hidden_div' class='gwt_file_upload_hidden_div'></div>" );
    uploadTextBox = new GwtLabel();
    uploadTextBox.setId( "gwt_FileUpload_uploadTextBox" );

    GwtButton uploadButton = new GwtButton();
    uploadButton.setId( "gwt_FileUpload_uploadButton" );
    uploadButton.setHeight( 22 );

    final LabelWidget label = new LabelWidget( "uploadFormElement" );
    label.setStyleName( "gwt_file_upload_label" );
    // If "image" attribute has been defined in the fileupload control do not display the file textfield AND do not
    // set the button label.
    if ( StringUtils.isEmpty( uploadButtonImage ) ) {
      uploadButton.setLabel( "..." );
      final Widget labelWidget = (Widget) uploadTextBox.getManagedObject();
      label.add( labelWidget );
      uploadTextBox.layout();
      labelWidget.setHeight( getHeight() + "px" );
      labelWidget.setWidth( ( getWidth() - 55 ) + "px" );
      DOM.setStyleAttribute( labelWidget.getElement(), "lineHeight", getHeight() + "px" );
    } else {
      uploadButton.setImage( uploadButtonImage );
      uploadButton.setDisabledImage( uploadButtonDisabledImage );
    }

    label.add( (Widget) uploadButton.getManagedObject() );
    uploadButton.layout();
    hiddenPanel.add( upload, "hidden_div" );
    hiddenPanel.add( label, "hidden_div" );
    // -- upload styling -- //

    uploadPanel.add( hiddenPanel );
    panel.add( uploadPanel );
    mainPanel.add( uploadForm );
    if ( getHeight() >= 0 ) {
      mainPanel.setHeight( getHeight() + "px" );
    }
    if ( getWidth() >= 0 ) {
      mainPanel.setWidth( getWidth() + "px" );
    }

    uploadForm.addFormHandler( new FormHandler() {
      public void onSubmit( FormSubmitEvent event ) {
        if ( upload.getFilename() == null ) {
          try {
            GwtFileUpload.this.getXulDomContainer().invoke( getOnUploadFailure(),
                new Object[] { new Throwable( "No file has been selected. Please select the file to upload" ) } );
            return;
          } catch ( XulException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }

        }
      }

      public void onSubmitComplete( FormSubmitCompleteEvent event ) {
        String results = event.getResults();
        try {
          if ( results != null && results.indexOf( ERROR ) >= 0 ) {
            if ( results.indexOf( ERROR ) + ERROR.length() < results.length() ) {
              String result = results.replaceAll( "\\<.*?>", "" );
              GwtFileUpload.this.getXulDomContainer().invoke( getOnUploadFailure(),
                  new Object[] { new Throwable( result ) } );
            }
          } else {
            if ( results != null ) {
              String result = results.replaceAll( "\\<.*?>", "" );
              GwtFileUpload.this.getXulDomContainer().invoke( getOnUploadSuccess(), new Object[] { result } );
            } else {
              GwtFileUpload.this.getXulDomContainer().invoke( getOnUploadFailure(),
                  new Object[] { new Throwable( "Unable to find upload service or "
                    + "Upload service returned nothing" ) } );
            }
          }
        } catch ( XulException xule ) {
          xule.printStackTrace();
        }
      }
    } );

    uploadForm.setWidth( "100%" );

  }

  public String getAction() {
    return action;
  }

  private String processParameters() {
    // TODO the URL being returned should be encoded to UTF-8
    String fullUrl = null;
    try {
      try {
        StringBuffer buffer = new StringBuffer();
        Object[] keys = this.parameters.keySet().toArray();
        buffer.append( this.action );
        buffer.append( "?" ); //$NON-NLS-1$
        for ( Object theKey : keys ) {
          buffer.append( theKey );
          buffer.append( "=" ); //$NON-NLS-1$
          buffer.append( this.parameters.get( theKey ) );
          buffer.append( "&" ); //$NON-NLS-1$
        }
        fullUrl = buffer.toString();
        fullUrl = fullUrl.substring( 0, fullUrl.lastIndexOf( "&" ) );
      } catch ( Exception e ) {
        GwtFileUpload.this.getXulDomContainer().invoke( getOnUploadFailure(), new Object[] { new Throwable( e ) } );
      }
    } catch ( XulException xule ) {
      xule.printStackTrace();
    }
    return fullUrl;
  }

  public String getOnUploadFailure() {
    return uploadFailureMethod;
  }

  public String getOnUploadSuccess() {
    return uploadSuccessMethod;
  }

  public void setAction( String action ) {
    this.action = action;
  }

  public void setOnUploadFailure( String method ) {
    this.uploadFailureMethod = method;
  }

  public void setOnUploadSuccess( String method ) {
    this.uploadSuccessMethod = method;
  }

  @Bindable
  public String getSeletedFile() {
    return upload.getFilename();
  }

  @Bindable
  public void setSelectedFile( String name ) {
    if ( name == null || name.length() <= 0 ) {
      hiddenPanel.remove( upload );
      upload = new FileUpload();
      upload.setStylePrimaryName( "gwt-StyledFileUpload" );
      upload.setName( "uploadFormElement" ); //$NON-NLS-1$
      upload.getElement().setId( "uploadFormElement" );
      upload.setVisible( true );
      upload.setHeight( getHeight() + "px" ); //$NON-NLS-1$
      upload.setWidth( getWidth() + "px" ); //$NON-NLS-1$
      upload.addChangeHandler( new ChangeHandler() {
        public void onChange( ChangeEvent event ) {
          setSelectedFile( upload.getFilename() );
        }
      } );
      hiddenPanel.add( upload, "hidden_div" );
    }
    uploadTextBox.setValue( name );
    firePropertyChange( "selectedFile", null, name ); //$NON-NLS-1$
  }

  public void submit() {
    uploadForm.setAction( processParameters() );
    uploadForm.submit();
  }

  public void addParameter( String name, String value ) {
    this.parameters.put( name, value );
  }
}
