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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtFileDialog extends AbstractGwtXulContainer implements XulFileDialog{
  static FormPanel uploadForm = null;
  static FileUpload upload = null; 
  private Panel parentObject = null;
  private XulComponent parent;
  private SEL_TYPE selectionType = SEL_TYPE.SINGLE;
  private VIEW_TYPE viewType = VIEW_TYPE.FILES_DIRECTORIES;
  static {
    uploadForm = new FormPanel();
    uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
    uploadForm.setMethod(FormPanel.METHOD_POST);
    // Create a panel to hold all of the form widgets.
    VerticalPanel panel = new VerticalPanel();
    uploadForm.setWidget(panel);
    uploadForm.setVisible(false);
    // Create a TextBox, giving it a name so that it will be submitted.
    final TextBox tb = new TextBox();
    tb.setName("textBoxFormElement");
    panel.add(tb);
    // Create a FileUpload widget.
    upload = new FileUpload();
    upload.setName("uploadFormElement");
    panel.add(upload);
  }
  public static void register() {
    GwtXulParser.registerHandler("filedialog", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtFileDialog();
      }
    });
  }
  
  public GwtFileDialog() {
    super("filedialog");
    this.managedObject = uploadForm;
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if(container != null) {
      this.parent = container.getDocumentRoot().getRootElement();
      container.getDocumentRoot().getRootElement().addChild(this);
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

  public void setModalParent(Object parent) {
    parentObject = (Panel) parent;    
  }

  public void setSelectionMode(SEL_TYPE type) {
    this.selectionType = type;
  }

  public void setViewType(VIEW_TYPE type) {
    this.viewType = type;     
  }

  @Override
  public RETURN_CODE showOpenDialog() {
    doClick(GwtFileDialog.upload.getElement());    
    return null;
  }

  @Override
  public RETURN_CODE showOpenDialog(Object f) {
    doClick(GwtFileDialog.upload.getElement());
    return null;
  }
  
  public native void doClick(com.google.gwt.user.client.Element element)/*-{
  element.click();
  }-*/;

  @Override
  public RETURN_CODE showSaveDialog() {

    return null;
  }

  @Override
  public RETURN_CODE showSaveDialog(Object f) {
    // TODO Auto-generated method stub
    return null;
  }
  

}
