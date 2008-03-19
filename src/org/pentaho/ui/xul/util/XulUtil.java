package org.pentaho.ui.xul.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;


public final class XulUtil {
	
	public static Map <String, String> AttributesToMap(List <Attribute> attMap){
		
		Map <String, String> map = new HashMap <String, String>();
		for (int i = 0; i < attMap.size(); i++) {
			Attribute node = attMap.get(i);
			map.put(node.getName(), node.getValue());
		}
		return map;
		
	}
	

  public static List<org.pentaho.ui.xul.dom.Attribute> convertAttributes(List <Attribute> inList){
    List<org.pentaho.ui.xul.dom.Attribute>  outList = new ArrayList<org.pentaho.ui.xul.dom.Attribute>();
    
    for(Attribute attr : inList){
      outList.add(new org.pentaho.ui.xul.dom.Attribute(attr.getName(), attr.getValue()));
    }
    return outList;
  }

}
