package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class BindingEventHandler extends AbstractXulEventHandler {

  FormModel model;
  
  //Button click action
  public void test(){
    model.setFirstName("Oliver");
    model.setLastName("Twist");
  }

  //Button click action
  public void test2(){
    ((XulTextbox) document.getElementById("firstNameInput")).setValue("James");
    ((XulTextbox) document.getElementById("lastNameInput")).setValue("Kirk");
  }
  
  public void toggleBoolean(){
    model.setDisabled(!model.isDisabled());
  }
  
  public void onLoad(){
    model = new FormModel();
    
    //Approach 1: Bind Labels
    XulLabel firstNameLbl = (XulLabel) document.getElementById("firstName");
    XulLabel lastNameLbl = (XulLabel) document.getElementById("lastName");
    
    Binding binding = new Binding(model, "firstName", firstNameLbl, "value");
    this.getXulDomContainer().addBinding(binding);

    binding = new Binding(model, "lastName", lastNameLbl, "value");
    this.getXulDomContainer().addBinding(binding);
    
    //Approach 2: First Name Textbox
    new Binding(getXulDomContainer(), model, "firstName", "firstNameInput", "value");
    
    //Approach 3: Last Name Textbox
    getXulDomContainer().createBinding(model, "lastName", "lastNameInput", "value");

    
    //Component to component
    getXulDomContainer().createBinding("echoTextbox", "value", "echoLabel", "value");
    
    
    //Boolean bind
    getXulDomContainer().createBinding(model, "disabled", "disabledButton", "disabled");
  }
}

  