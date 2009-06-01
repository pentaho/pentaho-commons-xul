package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.dialogs.MessageDialogBox;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtMessageBox extends AbstractGwtXulComponent implements XulMessageBox {

  private static final String OK = "OK"; //$NON-NLS-1$

  private String title;

  private String message;

  private Object[] defaultButtons = new Object[] { OK };

  private Object[] buttons = defaultButtons;

  static final String ELEMENT_NAME = "messagebox"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMessageBox();
      }
    });
  }

  public GwtMessageBox() {
    super(ELEMENT_NAME);
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
    final boolean IS_HTML = false;
    final boolean AUTO_HIDE = false;
    final boolean MODAL = true;
    new MessageDialogBox(title, message, IS_HTML, AUTO_HIDE, MODAL).show();
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

  public void setTitle(final String title) {
    this.title = title;
  }

}
