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
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtFileUpload  extends AbstractGwtXulContainer implements XulFileUpload {
  private String uploadSuccessMethod, uploadFailureMethod;
  public final static String ERROR = ".ERROR_"; //$NON-NLS-1$
  private FormPanel uploadForm = null;
  private FileUpload upload = null; 
  private VerticalPanel uploadPanel;
  private VerticalPanel mainPanel;
  private String action;
  private Map<String, String> parameters = null;
  private static final String ELEMENT_NAME = "pen:fileupload"; //$NON-NLS-1$
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME,
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtFileUpload();
      }
    });
  }

  public GwtFileUpload() {
    super(ELEMENT_NAME);
    setManagedObject(new VerticalPanel());
    this.parameters = new HashMap<String, String>();
  }

  private String buildActionUrl(String moduleBaseUrl, String anAction) {
    String url = moduleBaseUrl;
    while (anAction.indexOf("../") >= 0 &&  url.lastIndexOf("/") > -1) {
      url = url.substring(0, url.lastIndexOf("/"));
      anAction = anAction.substring(3);
    }
    url += "/" + anAction;
    return url;
  }
  
  @SuppressWarnings("deprecation")
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    mainPanel = new VerticalPanel();
    setManagedObject(mainPanel);

    super.init(srcEle, container);
    if (!StringUtils.isEmpty(srcEle.getAttribute("action"))) {
      setAction(buildActionUrl(GWT.getModuleBaseURL(), srcEle.getAttribute("action")));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("onuploadsuccess"))) {
      setOnUploadSuccess(srcEle.getAttribute("onuploadsuccess"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("onuploadfailure"))) {
      setOnUploadFailure(srcEle.getAttribute("onuploadfailure"));
    }
    
    uploadForm = new FormPanel();
    uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
    uploadForm.setMethod(FormPanel.METHOD_POST);
    uploadForm.setHeight(getHeight() + "px");
    uploadForm.setWidth(getWidth() + "px");
    // Create a panel to hold all of the form widgets.
    HorizontalPanel panel = new HorizontalPanel();
    uploadForm.setWidget(panel);
    uploadForm.setVisible(true);
    // Create a FileUpload widget.
    upload = new FileUpload();
    upload.setName("uploadFormElement"); //$NON-NLS-1$
    upload.setVisible(true);
    upload.setHeight(getHeight() + "px");
    upload.setWidth(getWidth() + "px");
    upload.addChangeHandler(new ChangeHandler(){
      public void onChange(ChangeEvent event) {
        setSelectedFile(upload.getFilename());
      }
    });
    uploadPanel = new VerticalPanel();
    uploadPanel.add(upload);
    panel.add(uploadPanel);
    mainPanel.add(uploadForm);
    if(getHeight() >= 0) {
      mainPanel.setHeight(getHeight() + "px");
    }
    if(getWidth() >= 0) {
      mainPanel.setWidth(getWidth() + "px");
    }

    uploadForm.addFormHandler(new FormHandler() {
      public void onSubmit(FormSubmitEvent event) {
        if(upload.getFilename() == null) {
          try {
            GwtFileUpload.this.getXulDomContainer().invoke(getOnUploadFailure(), new Object[] {new Throwable("No file has been selected. Please select the file to upload")});
            return;
          } catch (XulException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          
        }
      }

      public void onSubmitComplete(FormSubmitCompleteEvent event) {
        String results = event.getResults();
        try {
          if(results != null && results.indexOf(ERROR) >= 0) {
           if(results.indexOf(ERROR) + ERROR.length() < results.length()) {
             String result = results.replaceAll("\\<.*?>","");
             GwtFileUpload.this.getXulDomContainer().invoke(getOnUploadFailure(), new Object[] {new Throwable(result)});
           }
          } else {
            if(results != null) {
              String result = results.replaceAll("\\<.*?>","");
              GwtFileUpload.this.getXulDomContainer().invoke(getOnUploadSuccess(), new Object[] {result});
            } else {
              GwtFileUpload.this.getXulDomContainer().invoke(getOnUploadFailure(), new Object[] {new Throwable("Unable to find upload service or Upload service returned nothing")});
            }
          }
        } catch(XulException xule) {
          xule.printStackTrace(); 
        }
      }
    });

    uploadForm.setWidth("100%");

  }

  public String getAction() {
    return action;
  }
  
  private String processParameters() {
    //TODO the URL being returned should be encoded to UTF-8
    String fullUrl = null;
    try {
      try {
        StringBuffer buffer = new StringBuffer();
        Object[] keys = this.parameters.keySet().toArray();
        buffer.append(this.action);
        buffer.append("?"); //$NON-NLS-1$
        for(Object theKey : keys) {
          buffer.append(theKey);
          buffer.append("="); //$NON-NLS-1$
          buffer.append(this.parameters.get(theKey));
          buffer.append("&"); //$NON-NLS-1$
        }
        fullUrl = buffer.toString();
        fullUrl = fullUrl.substring(0, fullUrl.lastIndexOf("&"));
      } catch (Exception e) {
        GwtFileUpload.this.getXulDomContainer().invoke(getOnUploadFailure(), new Object[] {new Throwable(e)});
      }
    } catch(XulException xule) {
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


  public void setAction(String action) {
    this.action = action;
  }

  public void setOnUploadFailure(String method) {
   this.uploadFailureMethod = method;   
  }

  public void setOnUploadSuccess(String method) {
    this.uploadSuccessMethod = method;    
  }

  @Bindable
  public String getSeletedFile() {
    return upload.getFilename();
  }

  @Bindable
  public void setSelectedFile(String name) {
    if(name == null || name.length() <=0) {
      uploadPanel.remove(upload);
      upload = new FileUpload();
      upload.setName("uploadFormElement"); //$NON-NLS-1$
      upload.setVisible(true);
      upload.setHeight(getHeight() + "px"); //$NON-NLS-1$
      upload.setWidth(getWidth() + "px"); //$NON-NLS-1$
      uploadPanel.add(upload);
    }
    firePropertyChange("selectedFile", null, name); //$NON-NLS-1$
  }
  public void submit() {
    uploadForm.setAction(processParameters());
    uploadForm.submit();
  }
  
  public void addParameter(String name, String value) {
    this.parameters.put(name, value);
  }
}
