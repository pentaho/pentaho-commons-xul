package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;

public class GwtMenuitem extends AbstractGwtXulComponent implements XulMenuitem {

  static final String ELEMENT_NAME = "menuitem"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuitem();
      }
    });
  }

  private String image;
  private String jscommand;
  private String command;
  private MenuItem menuitem;
  private boolean isSelected;

  public GwtMenuitem() {
    super(ELEMENT_NAME);
    menuitem = new MenuItem("blank", (Command) null);
    setManagedObject(menuitem);
  }

  public GwtMenuitem(XulMenupopup popup) {
    this();

  }

  @Override
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    this.setLabel(srcEle.getAttribute("label"));
    this.setCommand(srcEle.getAttribute("command"));
    this.setJscommand(srcEle.getAttribute("js-command"));
  }

  public String getAcceltext() {
    return null;
  }

  public String getAccesskey() {
    return null;
  }

  public boolean isDisabled() {
    return false;
  }

  public String getLabel() {
    return menuitem.getText();
  }

  public void setAcceltext(String accel) {
    throw new RuntimeException("Not implemented");
  }

  public void setAccesskey(String accessKey) {
    throw new RuntimeException("Not implemented");
  }

  public void setDisabled(boolean disabled) {
    throw new RuntimeException("Not implemented");
  }

  public void setDisabled(String disabled) {
    throw new RuntimeException("Not implemented");
  }

  public void setLabel(String label) {
    menuitem.setText(label);
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCommand() {
    return this.command;
  }

  public void setCommand(final String command) {
    this.command = command;
    if (command != null) {
      menuitem.setCommand(new Command() {
        public void execute() {
          invoke(command);
        }
      });
    }
  }

  public String getJscommand() {
    return jscommand;
  }

  public void setJscommand(String jscommand) {
    this.jscommand = jscommand;
    if (jscommand != null) {
      menuitem.setCommand(new Command() {
        public void execute() {
          executeJS(GwtMenuitem.this.jscommand);
        }
      });
    }
  }

  private native void executeJS(String js)
  /*-{
    try{
      $wnd.eval(js);
    } catch (e){
      alert("Javascript Error: " + e.message+"\n\n"+js);
    }
  }-*/;

  @Override
  public String toString() {
    return getLabel();
  }

}
