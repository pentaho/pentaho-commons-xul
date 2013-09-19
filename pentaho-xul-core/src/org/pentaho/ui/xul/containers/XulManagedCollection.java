package org.pentaho.ui.xul.containers;

/**
 * This is a marker interface alerting the impl classes that a 
 * collection is bound (using XUL binding architecture) . XUL collection handling 
 * code should check for this interface before attempting to modify any list of elements.
 * 
 * @author gmoran
 *
 */
public interface XulManagedCollection {

}
