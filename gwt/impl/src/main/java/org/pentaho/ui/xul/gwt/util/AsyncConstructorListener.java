/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.gwt.util;

/**
 * Implementors of this interface can be passed into constructors for classes that make asyncronous calls in their
 * constructors. Once this listener is notified, the object is properly initialized and subsequent calls to the
 * object can be made.
 * 
 * @author mlowery
 */
public interface AsyncConstructorListener<T> {
  void asyncConstructorDone( T source );
}
