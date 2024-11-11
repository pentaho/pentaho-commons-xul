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


package org.pentaho.ui.xul.gwt;

import java.util.HashMap;
import java.util.Map;

public class EventHandlerProxy {

  public static interface ProxyMethod {
    public void exec( Object[] args );
  }

  Map<String, ProxyMethod> methods = new HashMap<String, ProxyMethod>();

  public void addMethod( String method, ProxyMethod proxy ) {
    methods.put( method, proxy );
  }

  public void invoke( String method, Object[] args ) {
    methods.get( method ).exec( args );
  }
}
