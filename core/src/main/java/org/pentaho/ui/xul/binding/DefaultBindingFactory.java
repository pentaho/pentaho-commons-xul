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

public class DefaultBindingFactory implements BindingFactory {

  private Document document;

  private Binding.Type type = Binding.Type.BI_DIRECTIONAL;

  private BindingExceptionHandler exceptionHandler;

  public void setDocument( Document document ) {
    this.document = document;
  }

  public void setBindingType( Binding.Type type ) {
    this.type = type;
  }

  private Binding applyBinding( Binding b, BindingConvertor... converters ) {
    b.setBindingType( type );
    b.setExceptionHandler( this.exceptionHandler );
    if ( converters != null && converters.length > 0 ) {
      b.setConversion( converters[0] );
    }
    b.initialize();
    document.addInitializedBinding( b );
    return b;
  }

  private void constraintsCheck() {
    if ( document == null ) {
      throw new IllegalArgumentException(
          "document is null.  Did you forget to set the document on the DefaultBindingFactory?" );
    }
  }

  public Binding createBinding( String sourceId, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters ) {
    constraintsCheck();
    Binding b =
        new DefaultBinding( document.getElementById( sourceId ), sourceAttr, document.getElementById( targetId ),
            targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( Object source, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters ) {
    constraintsCheck();
    Binding b = new DefaultBinding( source, sourceAttr, document.getElementById( targetId ), targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( String sourceId, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters ) {
    constraintsCheck();
    Binding b = new DefaultBinding( document.getElementById( sourceId ), sourceAttr, target, targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( Object source, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters ) {
    Binding b = new DefaultBinding( source, sourceAttr, target, targetAttr );
    return applyBinding( b, converters );
  }

  public void setExceptionHandler( BindingExceptionHandler handler ) {
    this.exceptionHandler = handler;
  }

}
