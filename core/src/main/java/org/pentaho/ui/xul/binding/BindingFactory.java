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

package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.dom.Document;

public interface BindingFactory {

  void setDocument( Document document );

  void setBindingType( Binding.Type type );

  Binding createBinding( String sourceId, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters );

  Binding createBinding( Object source, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters );

  Binding createBinding( String sourceId, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters );

  Binding createBinding( Object source, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters );

  void setExceptionHandler( BindingExceptionHandler handler );

}
