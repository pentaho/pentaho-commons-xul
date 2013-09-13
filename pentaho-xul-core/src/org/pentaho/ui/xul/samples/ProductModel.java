package org.pentaho.ui.xul.samples;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulEventSourceAdapter;

public class ProductModel extends XulEventSourceAdapter{
  private List<Product> products = new ArrayList<Product>();
  
  public void addProduct(Product p){
    products.add(p);
    this.firePropertyChange("products", null, products);
  }
  
  public List<Product> getProducts(){
    return products;
  }
  
  public void setProducts(List<Product> products){
    
  }
}

  