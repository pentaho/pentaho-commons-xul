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


package org.pentaho.ui.xul.binding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public abstract class BindingConvertor<V, R> {
  private static final long serialVersionUID = 1L;

  public enum Direction {
    FORWARD, BACK
  };

  public abstract R sourceToTarget( V value );

  public abstract V targetToSource( R value );

  private static BindingConvertor<Integer, String> integer2String = new Integer2String();
  private static BindingConvertor<String, Integer> string2Integer = new String2Integer();
  private static BindingConvertor<Long, String> long2String = new Long2String();
  private static BindingConvertor<String, String> string2String = new String2String();
  private static BindingConvertor<Boolean, String> boolean2String = new Boolean2String();
  private static BindingConvertor<Double, String> double2String = new Double2String();
  private static BindingConvertor<String, Double> string2Double = new String2Double();
  private static BindingConvertor<Integer, Boolean> integer2Boolean = new Integer2Boolean();
  private static BindingConvertor<Object, Boolean> object2Boolean = new Object2Boolean();

  private static BindingConvertor<Collection, Object[]> collection2ObjectArray = new Collection2ObjectArray();

  public static BindingConvertor<Collection, Object[]> collection2ObjectArray() {
    return collection2ObjectArray;
  }

  public static BindingConvertor<Integer, String> integer2String() {
    return integer2String;
  }

  public static BindingConvertor<Integer, Boolean> integer2Boolean() {
    return integer2Boolean;
  }

  public static BindingConvertor<String, Integer> string2Integer() {
    return string2Integer;
  }

  public static BindingConvertor<Long, String> long2String() {
    return long2String;
  }

  public static BindingConvertor<Date, String> date2String() {
    return date2String( new SimpleDateFormat( "MM-dd-yyyy" ) ); //$NON-NLS-1$
  }

  public static BindingConvertor<Date, String> date2String( final DateFormat format ) {
    return new Date2String( format );
  }

  public static BindingConvertor<String, String> string2String() {
    return string2String;
  }

  public static BindingConvertor<Boolean, String> boolean2String() {
    return boolean2String;
  }

  public static BindingConvertor<Double, String> double2String() {
    return double2String;
  }

  public static BindingConvertor<String, Double> string2Double() {
    return string2Double;
  }

  /**
   * Converts an Object into a Boolean, false if the object is null, true otherwise
   * 
   * @return
   */
  public static BindingConvertor<Object, Boolean> object2Boolean() {
    return object2Boolean;
  }

  public static BindingConvertor<String, String> truncatedString( int length ) {
    return new TruncatedStringBindingConvertor( length );
  }

  static class Collection2ObjectArray extends BindingConvertor<Collection, Object[]> {
    @Override
    public Object[] sourceToTarget( Collection value ) {
      return value.toArray();
    }

    @Override
    public Collection targetToSource( Object[] value ) {
      return Arrays.asList( value );
    }
  }

  /*
   * Canned BindingConverter Implementations here
   */
  static class Integer2String extends BindingConvertor<Integer, String> {
    public String sourceToTarget( Integer value ) {
      if ( value != null ) {
        return value.toString();
      } else {
        return ""; //$NON-NLS-1$
      }
    }

    public Integer targetToSource( String value ) {
      if ( value != null ) {
        try {
          return Integer.valueOf( value );
        } catch ( NumberFormatException e ) {
          return new Integer( 0 );
        }
      }
      return new Integer( 0 );
    }
  }

  static class String2Integer extends BindingConvertor<String, Integer> {
    public Integer sourceToTarget( String value ) {
      if ( value != null ) {
        try {
          return Integer.valueOf( value );
        } catch ( NumberFormatException e ) {
          return new Integer( 0 );
        }
      }
      return new Integer( 0 );
    }

    public String targetToSource( Integer value ) {
      if ( value != null ) {
        return value.toString();
      } else {
        return ""; //$NON-NLS-1$
      }
    }
  }

  static class Long2String extends BindingConvertor<Long, String> {
    public String sourceToTarget( Long value ) {
      if ( value != null ) {
        return value.toString();
      } else {
        return ""; //$NON-NLS-1$
      }
    }

    public Long targetToSource( String value ) {
      if ( value != null ) {
        try {
          return Long.valueOf( value );
        } catch ( NumberFormatException e ) {
          return new Long( 0 );
        }
      }
      return new Long( 0 );
    }
  }

  static class Date2String extends BindingConvertor<Date, String> {
    DateFormat format = null;

    public Date2String() {
      format = new SimpleDateFormat( "MM-dd-yyyy" ); //$NON-NLS-1$
    }

    public Date2String( DateFormat format ) {
      this.format = format;
    }

    public String sourceToTarget( Date value ) {
      if ( value == null ) {
        return ""; //$NON-NLS-1$
      }
      return format.format( value );
    }

    public Date targetToSource( String value ) {
      try {
        return format.parse( value );
      } catch ( Exception e ) {
        return null;
      }
    }
  }

  static class String2String extends BindingConvertor<String, String> {
    public String sourceToTarget( String value ) {
      return value;
    }

    public String targetToSource( String value ) {
      return value;
    }
  }

  static class Boolean2String extends BindingConvertor<Boolean, String> {
    public String sourceToTarget( Boolean value ) {
      return value.toString();
    }

    public Boolean targetToSource( String value ) {
      return Boolean.parseBoolean( value );
    }

  }

  static class String2Double extends BindingConvertor<String, Double> {

    public Double sourceToTarget( String toDouble ) {
      try {
        return Double.valueOf( toDouble );
      } catch ( Exception e ) {
        return new Double( 0 );
      }
    }

    public String targetToSource( Double toString ) {
      if ( toString != null ) {
        return toString.toString();
      } else {
        return "";
      }
    }
  }

  static class Double2String extends BindingConvertor<Double, String> {

    public String sourceToTarget( Double toString ) {
      if ( toString != null ) {
        return toString.toString();
      } else {
        return "";
      }
    }

    public Double targetToSource( String toDouble ) {
      try {
        return Double.valueOf( toDouble );
      } catch ( Exception e ) {
        return new Double( 0 );
      }
    }
  }

  static class Integer2Boolean extends BindingConvertor<Integer, Boolean> {
    public Boolean sourceToTarget( Integer value ) {
      if ( value == null ) {
        return false;
      }
      return value > 0;
    }

    public Integer targetToSource( Boolean value ) {
      if ( value == null ) {
        return 0;
      }
      return value ? 1 : 0;
    }

  }

  static class Object2Boolean extends BindingConvertor<Object, Boolean> {
    public Boolean sourceToTarget( Object value ) {
      return value != null;
    }

    public Object targetToSource( Boolean value ) {
      // can't logically construct this binding
      return null;
    }

  }

  static class TruncatedStringBindingConvertor extends BindingConvertor<String, String> {
    private int length = 100;

    public TruncatedStringBindingConvertor( int length ) {
      this.length = length;
    }

    public String sourceToTarget( String value ) {
      if ( value.length() > length ) {
        return value.substring( 0, length ) + "...";
      } else {
        return value;
      }
    }

    public String targetToSource( String value ) {
      return value;
    }
  }

}
