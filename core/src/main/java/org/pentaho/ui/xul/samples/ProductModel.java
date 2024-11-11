/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulEventSourceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductModel extends XulEventSourceAdapter {
  private List<Product> products = new ArrayList<Product>();

  public void addProduct( Product p ) {
    products.add( p );
    this.firePropertyChange( "products", null, products );
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts( List<Product> products ) {

  }
}
