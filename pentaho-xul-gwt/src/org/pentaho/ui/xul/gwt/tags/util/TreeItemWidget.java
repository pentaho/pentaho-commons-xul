package org.pentaho.ui.xul.gwt.tags.util;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * User: nbaker
 * Date: Jul 13, 2010
 */
public class TreeItemWidget extends FlexTable {
  Label label = new Label();
  public TreeItemWidget(){
    this.setStylePrimaryName("tree-item-custom-widget");
    this.setWidget(0,1,label);
    this.getCellFormatter().setWidth(0,1,"100%");
  }

  public void setLabel(String label){
    this.label.setText(label);
  }
  
  public void setImage( Image img){
    this.setWidget(0,0,img);
  }
}
