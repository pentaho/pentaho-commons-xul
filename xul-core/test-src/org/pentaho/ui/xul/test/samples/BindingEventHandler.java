package org.pentaho.ui.xul.test.samples;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class BindingEventHandler extends AbstractXulEventHandler {

  public FormModel model;
  public ProductModel productModel;
  
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
  
  public void addProduct(){
    String name = ((XulTextbox)document.getElementById("productName")).getValue();
    String descr = ((XulTextbox)document.getElementById("productDescr")).getValue();
    
    productModel.addProduct(new Product(name, descr));
  }
  
  public Binding removeMeBinding;
  public void removeBinding(){
    getXulDomContainer().removeBinding(removeMeBinding);
  }
  
  
  public void onLoad(){
    model = new FormModel();
    productModel = new ProductModel();
    
    //Approach 1: Bind Labels
    XulLabel firstNameLbl = (XulLabel) document.getElementById("firstName");
    XulLabel lastNameLbl = (XulLabel) document.getElementById("lastName");
    
    Binding binding = new Binding(model, "firstName", firstNameLbl, "value");
    getXulDomContainer().addBinding(binding);
    removeMeBinding = binding;

    binding = new Binding(model, "lastName", lastNameLbl, "value");
    getXulDomContainer().addBinding(binding);
    
    //Approach 2: First Name Textbox
    binding = new Binding(document, model, "firstName", "firstNameInput", "value");
    getXulDomContainer().addBinding(binding);
    
    //Approach 3: Last Name Textbox
    getXulDomContainer().createBinding(model, "lastName", "lastNameInput", "value");

    
    //Component to component
    getXulDomContainer().createBinding("echoTextbox", "value", "echoLabel", "value");
    
    
    //Boolean bind
    getXulDomContainer().createBinding(model, "disabled", "disabledButton", "disabled");
    

    //Inverse Boolean bind
    getXulDomContainer().createBinding("checkbox", "!checked", "inversedDisabledButton", "disabled");
    

    //Tree bind
    getXulDomContainer().createBinding(productModel, "products", "productTable", "elements");
    
    
    
    //Conversions
    binding = new Binding(document, "degreesField", "value", "radiansField", "value");
    binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
    binding.setConversion(new BindingConvertor<String, String>(){
      @Override
      public String sourceToTarget(String value) {
        float degrees = Float.parseFloat(value);
        return String.format("%.2f", degrees * (Math.PI / 180)); 
      }

      @Override
      public String targetToSource(String value) {
        float radians = Float.parseFloat(value);
        return String.format("%.2f", radians * (180/Math.PI));
      }
    });
    getXulDomContainer().addBinding(binding);
    
    
    //Conversion text to selectedIndex
    binding = getXulDomContainer().createBinding("nthItem", "value", "itemsList", "selectedIndex");
    binding.setConversion(new BindingConvertor<String, Integer>(){
      @Override
      public Integer sourceToTarget(String value) {
        return Integer.parseInt(value);
      }

      @Override
      public String targetToSource(Integer value) {
        return value.toString();
      }
    });
    
  }
}

  