package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulFileUpload;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtFileUpload  extends AbstractGwtXulContainer implements XulFileUpload{
  private String uploadSuccessMethod, uploadFailureMethod;
  public final static String ERROR = ".ERROR_";  
  private FormPanel uploadForm = null;
  private FileUpload upload = null; 
  private VerticalPanel uploadPanel;
  private VerticalPanel mainPanel;
  private String action;
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
  }

  @SuppressWarnings("deprecation")
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    mainPanel = new VerticalPanel();
    setManagedObject(mainPanel);

    super.init(srcEle, container);
    if (!StringUtils.isEmpty(srcEle.getAttribute("action"))) {
      setAction(srcEle.getAttribute("action"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("onuploadsuccess"))) {
      setOnUploadSuccess(srcEle.getAttribute("onuploadsuccess"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("onuploadfailure"))) {
      setOnUploadFailure(srcEle.getAttribute("onuploadfailure"));
    }
    
    uploadForm = new FormPanel();
    uploadForm.setAction(getAction());
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
    uploadForm.submit();
  }
}
