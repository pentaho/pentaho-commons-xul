/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.TextType;

/**
 * @author nbaker
 *
 */
public class SwingTextbox extends SwingElement implements XulTextbox  {
  private JTextField textField;
  private boolean multiline = false;
  private JTextArea textArea;
  boolean disabled = false;
  private String value = "";
  private JScrollPane scrollPane;
	private static final Log logger = LogFactory.getLog(SwingTextbox.class);
  private boolean readonly = false;
  private TextType type = TextType.NORMAL;
  private JTextComponent textComp = null;
  private String onInput;
  private int min = -1;
  private int max = -1;
  private int maxlength = -1;
  
  public SwingTextbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("textbox");
    managedObject = null;
  }

  public String getValue(){
  	if(managedObject != null){
  		return textComp.getText();
  	} else {
  		logger.error("Attempt to get Textbox's value before it's instantiated");
  		return null;
  	}
  }

  public void setValue(String text){
	String oldVal = this.value;
  	if(managedObject != null){
  	  textComp.setText(text);
  	}
    this.value = text;
    this.changeSupport.firePropertyChange("value", oldVal, text);
  }
  
  public void layout(){
  }

  public int getMaxlength() {
    return maxlength;
  }

  public boolean isDisabled() {
    return this.disabled;
  }

  public void setDisabled(boolean dis) {
  	this.disabled = dis;
  	if(managedObject != null){
  	  textComp.setEnabled(!dis);
  	}
  }

  public void setMaxlength(int length) {
    maxlength = length;
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
    if (type == null){
      return null;
    }
    
    return type.toString();
  }

  public void setType(String type) {
    if (type == null){
      return;
    }
    setType(TextType.valueOf(type.toUpperCase()));
  }
  
  public void setType(TextType type) {
    this.type = type;
  }

  public void selectAll() {
    textComp.selectAll();
  }

  public void setFocus() {
    
  }

  public Object getTextControl() {
    return managedObject;
  }

	@Override
	public Object getManagedObject() {
		if(managedObject == null){
	    switch(this.type){
	      case PASSWORD:
	        JPasswordField pass = new JPasswordField((value != null) ? value : "");
	        pass.setPreferredSize(new Dimension(150,20));
	        pass.setMinimumSize(new Dimension(pass.getPreferredSize().width,pass.getPreferredSize().height));
	        pass.setEditable(!readonly);
	        textComp = pass;
	        managedObject = pass;
	        break;
	      case NUMERIC:
	      default: //regular text
    			if(this.multiline){
    				textArea = new JTextArea((value != null) ? value : "");
    				scrollPane = new JScrollPane(textArea);
    				textComp = textArea;
    				managedObject = scrollPane;
    				textArea.setEditable(!readonly);
    				this.scrollPane.setMinimumSize(new Dimension(this.width, this.height));
    		    //this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    			} else {
    				textField = new JTextField((value != null) ? value : "");
    				textField.setPreferredSize(new Dimension(150,textField.getPreferredSize().height));
            textField.setMinimumSize(new Dimension(textField.getPreferredSize().width,textField.getPreferredSize().height));
    				textField.setEditable(!readonly);
    				managedObject = textField;
    				textComp = textField;
    			}
	      
	        //constrin Numeric only here
	        if(this.type == TextType.NUMERIC){
	          textComp.setDocument(new NumericDocument(min, max));
	        }
	        break;
  		}

	    textComp.addKeyListener(new KeyListener(){

	      public void keyPressed(KeyEvent e) {}
	      public void keyReleased(KeyEvent e) {
	        SwingTextbox.this.changeSupport.firePropertyChange("value", "", SwingTextbox.this.getValue());
	      }

	      public void keyTyped(KeyEvent e) {}
	      
	    });
//	    Why do we need this here if we setup oninput in the setOninput
//	     textComp.addKeyListener(new KeyAdapter() {
//	       
//	       public void keyReleased(KeyEvent e) {
//	         invoke(onInput);
//	       }
//	     });
	  }
		
		return managedObject;
	  
	}
	
	public void setOninput(final String method) {
	  if(textComp != null){
  	  textComp.addKeyListener(new KeyAdapter() {
  
        public void keyReleased(KeyEvent e) {
          invoke(method);
        }
      });
	  } else { //Not instantiated, save for later
	    onInput = method;
	  }
  }

  public String getMin() {
    return ""+min;
  }

  public void setMin(String min) {
    this.min = Integer.parseInt(min);  
  }

  public String getMax() {
    return ""+max;
  }

  public void setMax(String max) {
    this.max = Integer.parseInt(max);  
  }
  
  @SuppressWarnings("serial")
  private class NumericDocument extends PlainDocument{
    private int min;
    private int max;
    
    public NumericDocument(int min, int max){
      super();
      this.min = min;
      this.max = max;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      if(str == null){
        return;
      }
      if(!validString(str)){
        logger.error("Textbox input not a valid number: "+str);
        return;
      }
      
      //if not special chars, evaluate number for range
      if(str.charAt(str.length()-1) != '.' && str.charAt(str.length()-1) != '-'){  

        if(max > -1 && Double.parseDouble(super.getText(0, super.getLength()) + str) > max){
          logger.error(
              String.format("Textbox Greater [%f]less than max: %d",
                  Float.parseFloat(super.getText(0, super.getLength()) + str),
                  max
              )
          );return;
        }
        
      }
      
      //everything checks out insert string
      super.insertString(offs, str, a); 
    
    }
    
    private boolean validString(String str){
      return StringUtils.isNumeric(str.replace(".","").replace("-","")) || str.equals("-") || str.equals(".");
    }
  }
  
  
}
