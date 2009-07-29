package org.pentaho.ui.xul.gwt.util;
/**
 * Implementors of this interface can be passed into constructors for classes that make asyncronous calls in their 
 * constructors. Once this listener is notified, the object is properly initialized and subsequent calls to the object
 * can be made.
 * 
 * @author mlowery
 */
public interface AsyncConstructorListener {
  void asyncConstructorDone();
}
