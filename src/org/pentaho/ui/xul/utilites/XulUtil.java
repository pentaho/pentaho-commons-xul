package org.pentaho.ui.xul.utilites;

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

}
