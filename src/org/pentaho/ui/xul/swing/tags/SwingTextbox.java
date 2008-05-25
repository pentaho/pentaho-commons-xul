/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.TextType;
import org.pentaho.ui.xul.dom.Element;

/**
 * @author nbaker
 *
 */
public class SwingTextbox extends SwingElement implements XulTextbox {
  private JTextField textField;

  private boolean multiline = false;

  private JTextArea textArea;

  boolean disabled = false;

  String value = "";

  JScrollPane scrollPane;

  private static final Log logger = LogFactory.getLog(SwingTextbox.class);

  private boolean readonly = false;

  private TextType type = TextType.NORMAL;

  public SwingTextbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("textbox");
    managedObject = null;
  }

  public String getValue() {
    if (managedObject != null) {
      return ((JTextComponent) managedObject).getText();
    } else {
      logger.error("Attempt to get Textbox's value before it's instantiated");
      return null;
    }
  }

  public void setValue(String text) {
    if (managedObject != null) {
      ((JTextComponent) managedObject).setText(text);
    }
    this.value = text;
  }

  public void layout() {
  }

  public int getMaxlength() {
    return 0;
  }

  public boolean isDisabled() {
    return this.disabled;
  }

  public void setDisabled(boolean dis) {
    this.disabled = dis;
    if (managedObject != null) {
      ((Component) managedObject).setEnabled(!dis);
    }
  }

  public void setMaxlength(int length) {
  }

  public boolean isMultiline() {
    return multiline;
  }

  public void setMultiline(boolean multi) {
    this.multiline = multi;
  }

  public boolean isReadonly() {
    return readonly;
  }

  public void setReadonly(boolean readOnly) {
    this.readonly = readOnly;
  }

  public String getType() {
    if (type == null) {
      return null;
    }

    return type.toString();
  }

  public void setType(String type) {
    if (type == null) {
      return;
    }
    setType(TextType.valueOf(type.toUpperCase()));
  }

  public void setType(TextType type) {
    this.type = type;
  }

  public void selectAll() {

    ((JTextComponent) managedObject).selectAll();

  }

  public void setFocus() {

  }

  public Object getTextControl() {
    return managedObject;
  }

  @Override
  public Object getManagedObject() {
    if (managedObject == null) {
      switch (this.type) {
        case PASSWORD:
          JPasswordField pass = new JPasswordField((value != null) ? value : "");
          pass.setPreferredSize(new Dimension(200, 20));
          pass.setEditable(!readonly);
          managedObject = pass;
          return pass;
        default: //regular text
          if (this.multiline) {
            textArea = new JTextArea((value != null) ? value : "");
            managedObject = textArea;
            scrollPane = new JScrollPane(textArea);
            textArea.setEditable(!readonly);
            this.scrollPane.setMinimumSize(new Dimension(this.width, this.height));
            this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            return scrollPane;
          } else {
            textField = new JTextField((value != null) ? value : "");
            textField.setPreferredSize(new Dimension(200, 20));
            textField.setEditable(!readonly);
            managedObject = textField;
            return textField;
          }
      }
    } else {
      return managedObject;
    }
  }

  public void setOninput(final String method) {
    ((JTextComponent) getManagedObject()).addKeyListener(new KeyAdapter() {

      public void keyReleased(KeyEvent e) {
        invoke(method);
      }
    });
  }
}
