/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.binding;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingExceptionHandler;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.dom.Document;

public class GwtBindingFactory implements BindingFactory {

  private Document document;

  private Binding.Type type = Binding.Type.BI_DIRECTIONAL;

  private BindingExceptionHandler exceptionHandler;

  public GwtBindingFactory( Document document ) {
    this.document = document;
  }

  public void setDocument( Document document ) {
    this.document = document;
  }

  public void setBindingType( Binding.Type type ) {
    this.type = type;
  }

  private Binding applyBinding( Binding b, BindingConvertor... converters ) {
    b.setBindingType( type );
    b.setExceptionHandler( exceptionHandler );
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
        new GwtBinding( document.getElementById( sourceId ), sourceAttr, document.getElementById( targetId ),
            targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( Object source, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters ) {
    constraintsCheck();
    Binding b = new GwtBinding( source, sourceAttr, document.getElementById( targetId ), targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( String sourceId, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters ) {
    constraintsCheck();
    Binding b = new GwtBinding( document.getElementById( sourceId ), sourceAttr, target, targetAttr );
    return applyBinding( b, converters );
  }

  public Binding createBinding( Object source, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters ) {
    Binding b = new GwtBinding( source, sourceAttr, target, targetAttr );
    return applyBinding( b, converters );
  }

  public void setExceptionHandler( BindingExceptionHandler handler ) {
    this.exceptionHandler = handler;

  }

}
