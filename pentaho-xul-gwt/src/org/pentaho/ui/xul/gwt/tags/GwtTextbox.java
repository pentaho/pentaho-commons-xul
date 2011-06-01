package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtDomElement;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.TextType;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class GwtTextbox extends AbstractGwtXulComponent implements XulTextbox {
  
  static final String ELEMENT_NAME = "textbox"; //$NON-NLS-1$

  protected String max, min, oninput, onblur, oncommand;
  protected TextType type = TextType.NORMAL;
  protected boolean readonly;
  protected boolean multiline = false;
  private Integer rows;
  private Integer cols = -1;
  private Integer lineCharacterCount = 0;
  private String value;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTextbox();
      }
    });
  }
  
  private TextBoxBase textBox;
  
  public GwtTextbox() {
    super(ELEMENT_NAME);
    textBox = new TextBox();
    textBox.setStylePrimaryName("xul-textbox");
    
    // Firefox 2 and sometimes 3 fails to render cursors in Textboxes if they're contained in absolutely
    // positioned div's, such as when they're in dialogs. The workaround is to wrap the <input> in a div
    // with overflow: auto;
    setManagedObject(textBox);
      
    // textBox.setPreferredSize(new Dimension(150,18));
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setValue(srcEle.getAttribute("value"));
    if(StringUtils.isEmpty(srcEle.getAttribute("disabled")) == false){
      setDisabled("true".equals(srcEle.getAttribute("disabled")));
    }
    setMultiline("true".equals(srcEle.getAttribute("multiline")));
    setRows(getInt(srcEle.getAttribute("rows")));
    setCols(getInt(srcEle.getAttribute("cols")));
    setOnblur(srcEle.getAttribute("onblur"));
    setOncommand(srcEle.getAttribute("oncommand"));
  }
  
  public Integer getInt(String val) {
    if (val != null) {
      try {
        return Integer.parseInt(val);
      } catch (Exception e) {
      }
    }
    return null;
  }
  
  @Bindable
  public String getValue(){
    return value;
  }
  
  private FlowPanel scrollPanel = new FlowPanel() {
  { 
 	  setStylePrimaryName("textbox-scroll-panel");
	  this.sinkEvents(Event.ONMOUSEUP);
  }
	  @Override
	  public void onBrowserEvent(Event event) {
		  if(multiline) {
			  if(getChildren().size() > 0) {
				  Object child = getChildren().get(0);
				  if(child instanceof TextArea) {
					  ((TextArea) child).setFocus(true);
				  }
			  }
		  }
	  }
  };
  
  @Bindable
  public void setValue(String text){
      String prevVal = this.value;
      this.value = text;
      if(textBox.getText().equals(text) == false){
        textBox.setText(text);
      }
      this.firePropertyChange("value", prevVal, text);
      
      if(StringUtils.isEmpty(text) && multiline) {
    	  reinitializeMultilineTextArea();
      }
      if(multiline){
          ElementUtils.replaceScrollbars(scrollPanel.getElement());
      }
      
  }
  
  private void reinitializeMultilineTextArea() {
	  if(textBox instanceof TextArea) {
		  rows = 2;
		  ((TextArea)textBox).setVisibleLines(rows++);
	  }
  }
  
  // TODO: this double initialization is not good. Any values previously set will be lost in a second layout
  // move to local variables if this late binding is really needed and take advantage of the new onDomReady event 
  // to late bind instead of using layout.
  public void layout(){
	Object managedObject = null;
    String typeString = this.getAttributeValue("type");
    if(typeString != null && typeString.length() > 0) {
      setType(typeString);
    }
    switch(this.type) {
      case PASSWORD:
          textBox = new PasswordTextBox();
          managedObject = textBox;
          if(this.getHeight() > 0){
              textBox.setHeight(this.getHeight()+"px");
          }
        break;        
      case NUMERIC:
          if(this.getHeight() > 0){
              textBox.setHeight(this.getHeight()+"px");
          }
      default: //regular text  
        if (multiline) {
          managedObject = createStyledMultilineTextBox();
          ElementUtils.replaceScrollbars(scrollPanel.getElement());
          if(cols != null && cols > -1){
              //((TextArea)textBox).setCharacterWidth(cols);
              //((TextArea)textBox).setVisibleLines(rows);
          }
        } else {
          //managedObject = textBox = new TextBox();
          managedObject = textBox;
          if(this.getHeight() > 0){
              textBox.setHeight(this.getHeight()+"px");
          }
        }
        break;
    }
    setManagedObject(managedObject);
    
    if(this.getWidth() > 0){
      textBox.setWidth(this.getWidth()+"px");
    } else {
      textBox.setWidth("100%");
    }
    textBox.setText(getValue());
    textBox.setEnabled(! this.isDisabled() );
    setupListeners();
  }
  
  private SimplePanel createStyledMultilineTextBox() {
	  
	  /*
	   * This method places the TextArea inside an styled ScrollPanel.
	   * The TextArea is initially set to 2 rows in height and then increases its size vertically
	   * on each Enter key stroke to avoid displaying its native scrollbars and forcing
	   * the styled ScrollPanel to show its instead.
	   * 
	   * Also the size of the TextArea gets increased vertically when the number of characters in a row of text
	   * is equal to the predefined number of columns in the TextArea.	   * 
	   * */
	  
	  rows = 2;
	  
	  textBox = new TextArea();
      textBox.addKeyPressHandler(new KeyPressHandler() {
  		public void onKeyPress(KeyPressEvent event) {
  			if (event.getCharCode() == KeyCodes.KEY_ENTER) {
  				((TextArea)textBox).setVisibleLines(rows++);
  			}
  			lineCharacterCount ++;
  			if(lineCharacterCount == cols) {
  				((TextArea)textBox).setVisibleLines(rows++);
  				lineCharacterCount = 0;
  			}
  		}
  	  });

      scrollPanel.clear();
      scrollPanel.add(textBox);
	  
      SimplePanel simplePanel = new SimplePanel();
      simplePanel.setStyleName("multiline-simple-panel");
      simplePanel.setHeight(this.getHeight()+"px");
      simplePanel.add(scrollPanel);
      return simplePanel;
  }
  
  @SuppressWarnings("deprecation")
  private void setupListeners(){
    textBox.addKeyboardListener(new KeyboardListener(){

      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        if(onblur != null && !onblur.equalsIgnoreCase("")){ //$NON-NLS-1$
          if(keyCode == KeyboardListener.KEY_TAB){
            Event.getCurrentEvent().cancelBubble(true);
            Event.getCurrentEvent().preventDefault();
            
            try {
              GwtTextbox.this.getXulDomContainer().invoke(GwtTextbox.this.getOnblur(), new Object[] {});
            } catch (XulException e) {
              e.printStackTrace();
            }
          }
         }
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {}

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        setValue(textBox.getText());
        if(keyCode == KeyboardListener.KEY_ENTER){
          if(!GwtTextbox.this.multiline){
            try {
              GwtTextbox.this.getXulDomContainer().invoke(GwtTextbox.this.getOncommand(), new Object[] {});
            } catch (XulException e) {
              e.printStackTrace();
            }
          }
        }
      }
      
    });
    
    textBox.addBlurHandler(new BlurHandler(){
      public void onBlur(BlurEvent event) {
        if(onblur != null && !onblur.equalsIgnoreCase("")){ //$NON-NLS-1$
          try {
            GwtTextbox.this.getXulDomContainer().invoke(GwtTextbox.this.getOnblur(), new Object[] {});
          } catch (XulException e) {
            e.printStackTrace();
          }
        }
      }      
    });
    
  }

  public void setOncommand(String method){
    this.oncommand = method;
  }
  
  public String getOncommand() {
    return oncommand;
  }

  public int getMaxlength() {
    return 0;
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Bindable
  public void setDisabled(boolean dis) {
    this.disabled = dis;
    textBox.setEnabled(!dis);
  }

  public void setMaxlength(int length) {
  }

  public boolean isMultiline() {
    return false;
  }

  public void setMultiline(boolean multi) {
    this.multiline = multi;
  }

  public String getMax() {
    return min;  
  }

  public String getMin() {
    return min;  
  }

  public Object getTextControl() {
   return getManagedObject();   
  }

  public String getType() {
    if (type == null) {
      return null;
    }

    return type.toString(); 
  }

  public boolean isReadonly() {
    return readonly;  
  }

  public void selectAll() {
    // TODO Auto-generated method stub
  }

  public void setFocus() {
    this.textBox.setFocus(true);
  }

  public void setMax(String max) {
    this.max = max;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public void setOninput(String method) {
    this.oninput = method;
  }
  
  public String getOnblur(){
    return this.onblur;
  }
  
  public void setOnblur(String method) {
    this.onblur = method;
  }

  public void setReadonly(boolean readOnly) {
    this.readonly = readOnly;
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

  public void adoptAttributes(XulComponent component) {
    super.adoptAttributes(component);

    if(StringUtils.isEmpty(component.getAttributeValue("rows")) == false){
      setRows(getInt(component.getAttributeValue("rows")));
    }
    if(StringUtils.isEmpty(component.getAttributeValue("cols")) == false){
      setCols(getInt(component.getAttributeValue("cols")));
    }

    if(StringUtils.isEmpty(component.getAttributeValue("value")) == false){
      setValue(component.getAttributeValue("value"));
    }
    if(StringUtils.isEmpty(component.getAttributeValue("disabled")) == false){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
    layout();
    ((AbstractGwtXulComponent) this.getParent()).layout();
  }

  void setRows(Integer rows) {
    this.rows = rows;
  }

  Integer getRows() {
    return rows;
  }

  void setCols(Integer cols) {
    this.cols = cols;
  }

  Integer getCols() {
    return cols;
  }
  
  public void setCommand(String command) {
    throw new RuntimeException("command not implemented on textbox");
  }
}
