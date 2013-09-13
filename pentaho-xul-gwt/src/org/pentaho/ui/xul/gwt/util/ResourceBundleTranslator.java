package org.pentaho.ui.xul.gwt.util;

import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;


public class ResourceBundleTranslator {
  
  public static String translate(String input, ResourceBundle bundle) {
    int pos = 0;
    while(input.indexOf("${", pos) > -1){
      int start = input.indexOf("${", pos)+2;
      int end = input.indexOf("}",start);
      
      String key = input.substring(start, end);
      String transval = bundle.getString(key);
      input = input.replace("${"+key+"}", transval);
      pos = start+1;
      
    } 
    
    return input;
    
  }
  
}
