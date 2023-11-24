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
 * Copyright (c) 2002-2023 Hitachi Vantara. All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.mantle.client.csrf.CsrfUtil;
import org.pentaho.mantle.client.csrf.JsCsrfToken;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulFileUpload;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.tags.util.LabelWidget;
import org.pentaho.ui.xul.stereotype.Bindable;

import java.util.HashMap;
import java.util.Map;

public class GwtFileUpload extends AbstractGwtXulContainer implements XulFileUpload {
  /**
   * The name of the CSRF token field to use when CSRF protection is disabled.
   * <p>
   * An arbitrary name, yet different from the name it can have when CSRF protection enabled.
   * This avoids not having to dynamically adding and removing the field from the form depending
   * on whether CSRF protection is enabled or not.
   * <p>
   * When CSRF protection is enabled,
   * the actual name of the field is set before each submit.
   */
  private static final String DISABLED_CSRF_TOKEN_PARAMETER = "csrf_token_disabled";

  private String uploadSuccessMethod, uploadFailureMethod;
  public static final String ERROR = ".ERROR_";
  private FormPanel uploadForm = null;
  private Panel formPanel;
  private FileUpload upload = null;
  private VerticalPanel mainPanel;
  private GwtLabel uploadTextBox;
  private String action;
  private Map<String, String> parameters = null;
  private static final String ELEMENT_NAME = "pen:fileupload";

  /**
   * The CSRF token field/parameter.
   * Its name and value are set to the expected values before each submit,
   * to match the obtained {@link JsCsrfToken}.
   * <p>
   * The Tomcat's context must have the `allowCasualMultipartParsing` attribute set
   * so that the `CsrfGateFilter` is able to transparently read this parameter
   * in a multi-part encoding form, as is the case of `form`.
   */
  private Hidden csrfTokenParameter;

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
    // GWT compilation requires explicit generic parameters. Don't remove...
    this.parameters = new HashMap<String, String>();
  }

  private String buildActionUrl( String moduleBaseUrl, String anAction ) {
    String url = moduleBaseUrl;
    while ( anAction.contains( "../" ) && url.lastIndexOf( "/" ) > -1 ) {
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

    // Create a panel to hold all form widgets.
    formPanel = new HTMLPanel( "" );
    formPanel.addStyleName( "gwt_FileUpload_formPanel" );
    uploadForm.setWidget( formPanel );
    uploadForm.setVisible( true );

    HorizontalPanel uploadPanel = new HorizontalFlexPanel();
    // Create a FileUpload widget.
    upload = createFileUpload();

    String uploadButtonImage = srcEle.getAttribute( "image" );
    String uploadButtonDisabledImage = srcEle.getAttribute( "disabledimage" );

    csrfTokenParameter = new Hidden( DISABLED_CSRF_TOKEN_PARAMETER );

    uploadTextBox = new GwtLabel();
    uploadTextBox.setId( "gwt_FileUpload_uploadTextBox" );

    GwtButton uploadButton = new GwtButton();
    uploadButton.setId( "gwt_FileUpload_uploadButton" );
    uploadButton.setHeight( 22 );
    uploadButton.addClickHandler( event -> {
      event.preventDefault();
      ElementUtils.click( upload.getElement() );
    } );

    final LabelWidget label = new LabelWidget( "uploadFormElement" );
    label.setStyleName( "gwt_file_upload_label" );
    label.addStyleName( "flex-row" );
    // If "image" attribute has been defined in the fileupload control do not display the file textfield AND do not
    // set the button label.
    if ( StringUtils.isEmpty( uploadButtonImage ) ) {
      uploadButton.setLabel( "..." );
      final Widget labelWidget = (Widget) uploadTextBox.getManagedObject();
      labelWidget.addStyleName( "flex-row" );
      label.add( labelWidget );
      uploadTextBox.layout();
      labelWidget.setHeight( getHeight() + "px" );
      labelWidget.setWidth( ( getWidth() - 55 ) + "px" );
      DOM.setStyleAttribute( labelWidget.getElement(), "lineHeight", getHeight() + "px" );
    } else {
      uploadButton.setImage( uploadButtonImage );
      uploadButton.setDisabledImage( uploadButtonDisabledImage );
    }

    uploadButton.layout();

    uploadPanel.add( label );
    uploadPanel.add( (Widget) uploadButton.getManagedObject() );

    formPanel.add( uploadPanel );
    formPanel.add( upload );
    formPanel.add( csrfTokenParameter );

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

  private FileUpload createFileUpload() {
    final FileUpload upload = new FileUpload();

    upload.setStylePrimaryName( "gwt-StyledFileUpload" );
    upload.setName( "uploadFormElement" );
    upload.getElement().setId( "uploadFormElement" );
    upload.setVisible( true );
    upload.setHeight( getHeight() + "px" );
    upload.setWidth( getWidth() + "px" );
    upload.addChangeHandler( new ChangeHandler() {
      public void onChange( ChangeEvent event ) {
        setSelectedFile( upload.getFilename() );
      }
    } );

    return upload;
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
        buffer.append( "?" );
        for ( Object theKey : keys ) {
          buffer.append( theKey );
          buffer.append( "=" );
          buffer.append( this.parameters.get( theKey ) );
          buffer.append( "&" );
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

  /**
   * Obtains a CSRF token for the form's current URL and
   * fills it in the form's token parameter hidden field.
   */
  private void setupCsrfToken() {
    JsCsrfToken token = CsrfUtil.getCsrfTokenSync( uploadForm.getAction() );
    if ( token != null ) {
      csrfTokenParameter.setName( token.getParameter() );
      csrfTokenParameter.setValue( token.getToken() );
    } else {
      // Reset the field.
      csrfTokenParameter.setName( DISABLED_CSRF_TOKEN_PARAMETER );
      csrfTokenParameter.setValue( "" );
    }
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
      formPanel.remove( upload );
      upload = createFileUpload();
      formPanel.add( upload );
    }

    uploadTextBox.setValue( name );
    firePropertyChange( "selectedFile", null, name );
  }

  public void submit() {
    uploadForm.setAction( processParameters() );
    setupCsrfToken();
    uploadForm.submit();
  }

  public void addParameter( String name, String value ) {
    this.parameters.put( name, value );
  }
}
