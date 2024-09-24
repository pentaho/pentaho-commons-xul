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

package org.pentaho.ui.xul.containers;

/**
 * This is a marker interface alerting the impl classes that a collection is bound (using XUL binding architecture)
 * . XUL collection handling code should check for this interface before attempting to modify any list of elements.
 * 
 * @author gmoran
 * 
 */
public interface XulManagedCollection {

}
