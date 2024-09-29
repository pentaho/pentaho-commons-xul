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


/**
 * 
 */

package org.pentaho.ui.xul.dom;

/**
 * @author OEM
 * 
 */
public class Namespace {
  private String uri;
  private String prefix;

  public Namespace( String uri, String prefix ) {
    this.uri = uri;
    this.prefix = prefix;
  }

  public String getURI() {
    return uri;
  }

  public String getPrefix() {
    return prefix;
  }
}
