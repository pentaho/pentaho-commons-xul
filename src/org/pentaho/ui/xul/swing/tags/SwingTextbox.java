/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 *
 */
public class SwingTextbox extends SwingElement implements XulTextbox  {
  private JTextField textField;
  private boolean multiline = false;
  private JTextArea textArea;
  boolean disabled = false;
  String value = "";
  JScrollPane scrollPane;
  
  public SwingTextbox(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("textbox");
    managedObject = null;
  }

  public String getValue(){
    return textField.getText();
  }

  public void setValue(String text){
  	if(managedObject != null){
  		((JTextComponent) managedObject).setText(text);
  	}
  	this.value = text;
  }
  
  public void layout(){
  }

  public int getMaxlength() {
    return 0;
  }

  public boolean isDisabled() {
    return this.disabled;
  }

  public void setDisabled(boolean dis) {
  	this.disabled = dis;
  	if(managedObject != null){
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
    return false;
  }

  public void setReadonly(boolean readOnly) {
    
  }

  public String getType() {
    return null;
  }

  public void setType(String type) {
    
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
		if(managedObject == null){
			if(this.multiline){
				textArea = new JTextArea();
				managedObject = textArea;
				scrollPane = new JScrollPane(textArea);
				this.scrollPane.setMinimumSize(new Dimension(this.width, this.height));
		    this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			} else {
				textField = new JTextField();
				managedObject = textField;
			}
		}
		return (this.multiline)? scrollPane : textField;
    
	}

}
