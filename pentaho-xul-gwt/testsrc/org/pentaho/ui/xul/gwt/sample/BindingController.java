package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.gwt.binding.GwtBinding;
import org.pentaho.ui.xul.gwt.binding.GwtBindingFactory;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import com.google.gwt.user.client.Window;

public class BindingController extends AbstractXulEventHandler{

  private String firstName;
  private BindingModel model = new BindingModel();
  private XulTextbox textBox;
  BindingFactory bindingFactory;
  
  
  @Override
  public String getName() {
    return "controller";
  }

  public void init(){
    bindingFactory = new GwtBindingFactory(document);
    
    textBox = (XulTextbox) document.getElementById("nameTextbox");

    XulLabel lbl = (XulLabel) document.getElementById("testLabel");
    
    bindingFactory.createBinding(model, "firstName", lbl, "value");
    
  }
  
  public void click(){
    model.setFirstName(textBox.getValue());
  }


  public void setFirstName(String name) {
  
    this.firstName = name;
    this.firePropertyChange("firstName", null, this.firstName);
  }
  
  public String getFirstName(){
    return this.firstName;
  }
  
  
  
}

  