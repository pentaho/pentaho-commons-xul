package org.pentaho.ui.xul.binding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BindingConvertor<V, R> {
  public enum Direction{FORWARD, BACK};
  
  public abstract R sourceToTarget(V value);

  public abstract V targetToSource(R value);

  public static BindingConvertor<Integer, String> integer2String() {
    BindingConvertor<Integer, String> bc = new BindingConvertor<Integer, String>() {
      @Override
      public String sourceToTarget(Integer value) {
        if (value != null) {
          return value.toString();
        } else {
          return ""; //$NON-NLS-1$
        }
      }
    
      @Override
      public Integer targetToSource(String value) {
        if (value != null) {
          try {
            return Integer.valueOf(value);
          } catch (NumberFormatException e) {            
            return new Integer(0);
          }
        }
        return new Integer(0);
      }
    };
    return bc;
  }
  
  public static BindingConvertor<Long, String> long2String() {
    BindingConvertor<Long, String> bc = new BindingConvertor<Long, String>() {
      @Override
      public String sourceToTarget(Long value) {
        if (value != null) {
          return value.toString();
        } else {
          return ""; //$NON-NLS-1$
        }
      }
    
      @Override
      public Long targetToSource(String value) {
        if (value != null) {
          try {            
            return Long.valueOf(value); 
          } catch (NumberFormatException e) {
            return new Long(0);
          }
        }
        return new Long(0);
      }
    };
    return bc;
  }
  
  public static BindingConvertor<Date, String> date2String() {
    return date2String(new SimpleDateFormat("MM-dd-yyyy")); //$NON-NLS-1$
  }
  
  public static BindingConvertor<Date, String> date2String(final DateFormat format) {
    BindingConvertor<Date, String> bc = new BindingConvertor<Date, String>() {
      @Override
      public String sourceToTarget(Date value) {
        if (value == null) {
          return "";
        }
        return format.format(value);
      }
    
      @Override
      public Date targetToSource(String value) {
        try {
          return format.parse(value);
        } catch (ParseException e) {
          return null;
        }
      }
    };
    return bc;
  }
  
  public static BindingConvertor<String, String> string2String() {
    BindingConvertor<String, String> bc = new BindingConvertor<String, String>() {

      @Override
      public String sourceToTarget(String value) {
        return value;
      }

      @Override
      public String targetToSource(String value) {
        return value; 
      }
      
    };
    return bc;
    
  }
  
}
