package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.buttons.RoundedButton;
import org.pentaho.gwt.widgets.client.dialogs.MessageDialogBox;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GenericDialog;
import org.pentaho.ui.xul.util.XulDialogCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtMessageBox extends GenericDialog implements XulMessageBox {

  private static final String OK = "OK"; //$NON-NLS-1$

  private String title;

  private String message;

  private Object[] defaultButtons = new Object[] { OK };

  private Object[] buttons = defaultButtons;

  static final String ELEMENT_NAME = "messagebox"; //$NON-NLS-1$
  
  private String acceptLabel = "OK";
  
  private RoundedButton acceptBtn = new RoundedButton();

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMessageBox();
      }
    });
  }

  public GwtMessageBox() {
    super(ELEMENT_NAME);
    //setup default width and height in case user does not specify
    setHeight(125);
    setWidth(250);
    
    acceptBtn.addClickListener(new ClickListener(){
      public void onClick(Widget sender) {
        hide();
      }
    });
  }
  
  protected GwtMessageBox(String elementName){
    super(elementName);
  }

  @Override
  public Panel getButtonPanel() {
    HorizontalPanel hp = new HorizontalPanel();
    acceptBtn.setText(this.acceptLabel);
    hp.add(acceptBtn);
    hp.setCellWidth(acceptBtn, "100%");
    hp.setCellHorizontalAlignment(acceptBtn, hp.ALIGN_CENTER);
    return hp;
  }

  @Override
  public Panel getDialogContents() {

    VerticalPanel vp = new VerticalPanel();
    Label lbl = new Label(message);
    vp.add(lbl);
    vp.setCellHorizontalAlignment(lbl, vp.ALIGN_CENTER);
    vp.setCellVerticalAlignment(lbl, vp.ALIGN_MIDDLE);
    vp.setSize("200px", "125px");

    return vp;
  }
  
  public Object[] getButtons() {
    return buttons;
  }

  public Object getIcon() {
    return null;
  }

  public String getMessage() {
    return message;
  }

  public String getTitle() {
    return title;
  }

  public int open() {
    show();
    return 0;
  }

  public void setButtons(Object[] buttons) {
    // Can't have null buttons - accept default
    this.buttons = (buttons == null) ? defaultButtons : buttons;
  }

  public void setIcon(Object icon) {
    // not implemented
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public void setModalParent(final Object parent) {
    // not implemented
  }

  public void setScrollable(final boolean scroll) {
    // not implemented
  }
  
  public void setAcceptLabel(String lbl){
    this.acceptLabel = lbl;
  }

}
