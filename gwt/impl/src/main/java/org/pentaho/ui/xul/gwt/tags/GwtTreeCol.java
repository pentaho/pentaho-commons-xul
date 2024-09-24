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

package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.ColumnType;

public class GwtTreeCol extends AbstractGwtXulComponent implements XulTreeCol {

  private String binding;
  private String columnTypeBinding;
  private String disabledBinding;
  private boolean editable;
  private ColumnType type = ColumnType.TEXT;
  private String colType;
  private String tooltipBinding;
  private String expandBinding;
  private String imageBinding;
  private boolean sortActive = false;
  private String sortDirection = null;
  private String classnameBinding;
  private String alttextBinding;

  public static void register() {
    GwtXulParser.registerHandler( "treecol", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCol();
      }
    } );
  }

  public GwtTreeCol() {
    super( "treecol" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setLabel( srcEle.getAttribute( "label" ) );
    setBinding( srcEle.getAttribute( "pen:binding" ) );
    setChildrenbinding( srcEle.getAttribute( "pen:childrenbinding" ) );
    setType( srcEle.getAttribute( "type" ) );
    setCombobinding( srcEle.getAttribute( "pen:combobinding" ) );
    setColumntypebinding( srcEle.getAttribute( "pen:columntypebinding" ) );
    setDisabledbinding( srcEle.getAttribute( "pen:disabledbinding" ) );
    setEditable( "true".equalsIgnoreCase( srcEle.getAttribute( "editable" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
    if ( StringUtils.isEmpty( srcEle.getAttribute( "pen:expandedbinding" ) ) == false ) {
      setExpandedbinding( srcEle.getAttribute( "pen:expandedbinding" ) ); //$NON-NLS-1$ 
    }
    if ( StringUtils.isEmpty( srcEle.getAttribute( "pen:imagebinding" ) ) == false ) {
      setImagebinding( srcEle.getAttribute( "pen:imagebinding" ) ); //$NON-NLS-1$ 
    }
    setSortActive( "true".equalsIgnoreCase( srcEle.getAttribute( "sortActive" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$)
    setSortDirection( srcEle.getAttribute( "sortDirection" ) ); //$NON-NLS-1$

    // Classname
    String classname = srcEle.getAttribute( "pen:classnamebinding" ); //$NON-NLS-1$
    if ( !StringUtils.isEmpty( classname ) ) {
      setClassnameBinding( classname );
    }

    if ( !StringUtils.isEmpty( srcEle.getAttribute( "pen:alttextbinding" ) ) ) {
      setAlttextBinding( srcEle.getAttribute( "pen:alttextbinding" ) ); //$NON-NLS-1$
    }
  }

  public void autoSize() {
    // TODO Auto-generated method stub

  }

  public String getBinding() {
    return binding;
  }

  public String getCustomeditor() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getLabel() {
    return getAttributeValue( "label" );
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getType() {
    return colType;
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isFixed() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHidden() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isPrimary() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSortActive() {
    // TODO Auto-generated method stub
    return sortActive;
  }

  public void setBinding( String binding ) {
    this.binding = binding;
  }

  public void setCustomeditor( String customClass ) {
    // TODO Auto-generated method stub

  }

  public void setEditable( boolean edit ) {
    this.editable = edit;
  }

  public void setFixed( boolean fixed ) {
    // TODO Auto-generated method stub

  }

  public void setHidden( boolean hide ) {
    // TODO Auto-generated method stub

  }

  public void setLabel( String label ) {
    setAttribute( "label", label );
  }

  public void setPrimary( boolean primo ) {
    // TODO Auto-generated method stub

  }

  public void setSortActive( boolean sort ) {
    this.sortActive = sort;
  }

  public void setSrc( String srcUrl ) {
    // TODO Auto-generated method stub

  }

  public void setType( String type ) {
    this.colType = type;
    try {
      this.type = ColumnType.valueOf( type.toUpperCase() );
    } catch ( Exception e ) {
      this.type = ColumnType.CUSTOM;
    }
  }

  public List<InlineBindingExpression> getBindingExpressions() {
    List<InlineBindingExpression> exps = new ArrayList<InlineBindingExpression>();
    if ( binding == null ) {
      return exps;
    }
    for ( String bindingExpText : binding.split( "," ) ) {
      exps.add( new InlineBindingExpression( bindingExpText.trim() ) );
    }
    return exps;
  }

  public ColumnType getColumnType() {
    return type;
  }

  private String childrenBinding, comboBinding;

  public String getChildrenbinding() {
    return childrenBinding;
  }

  public String getCombobinding() {
    return comboBinding;
  }

  public void setChildrenbinding( String childProperty ) {
    this.childrenBinding = childProperty;
  }

  public void setCombobinding( String property ) {
    this.comboBinding = property;
  }

  public void setColumntypebinding( String propertyName ) {
    this.columnTypeBinding = propertyName;
  }

  public String getColumntypebinding() {
    return this.columnTypeBinding;
  }

  public String getDisabledbinding() {
    return disabledBinding;
  }

  public void setDisabledbinding( String property ) {
    this.disabledBinding = property;
  }

  public String getImagebinding() {
    return imageBinding;
  }

  public void setImagebinding( String img ) {
    this.imageBinding = img;
  }

  public String getExpandedbinding() {
    return expandBinding;
  }

  public void setExpandedbinding( String bind ) {
    this.expandBinding = bind;
  }

  public String getTooltipbinding() {
    return tooltipBinding;
  }

  public void setTooltipbinding( String bind ) {
    this.tooltipBinding = bind;
  }

  public String getComparatorbinding() {
    return null;
  }

  public void setComparatorbinding( String comp ) {

  }

  public void setSortDirection( String dir ) {
    sortDirection = dir;
  }

  public String getSortDirection() {
    return sortDirection;
  }

  @Override
  public String getClassnameBinding() {
    return this.classnameBinding;
  }

  @Override
  public void setClassnameBinding( String classnameBinding ) {
    this.classnameBinding = classnameBinding;
  }

  @Override
  public String getAlttextBinding() {
    return alttextBinding;
  }

  @Override
  public void setAlttextBinding( String alttext ) {
    this.alttextBinding = alttext;
  }

}

