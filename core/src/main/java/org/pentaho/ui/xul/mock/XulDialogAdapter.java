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


package org.pentaho.ui.xul.mock;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class XulDialogAdapter implements XulDialog {

  public String getButtonalign() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getButtonlabelaccept() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getButtonlabelcancel() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getButtonlabelextra1() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getButtonlabelextra2() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getButtons() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOndialogaccept() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOndialogcancel() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOndialogextra1() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOndialogextra2() {
    // TODO Auto-generated method stub
    return null;
  }

  public void hide() {
    // TODO Auto-generated method stub

  }

  public boolean isHidden() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setButtonalign( String align ) {
    // TODO Auto-generated method stub

  }

  public void setButtonlabelaccept( String label ) {
    // TODO Auto-generated method stub

  }

  public void setButtonlabelcancel( String label ) {
    // TODO Auto-generated method stub

  }

  public void setButtonlabelextra1( String label ) {
    // TODO Auto-generated method stub

  }

  public void setButtonlabelextra2( String label ) {
    // TODO Auto-generated method stub

  }

  public void setButtons( String buttons ) {
    // TODO Auto-generated method stub

  }

  public void setOndialogaccept( String command ) {
    // TODO Auto-generated method stub

  }

  public void setOndialogcancel( String command ) {
    // TODO Auto-generated method stub

  }

  public void setOndialogextra1( String command ) {
    // TODO Auto-generated method stub

  }

  public void setOndialogextra2( String command ) {
    // TODO Auto-generated method stub

  }

  public void setVisible( boolean visible ) {
    // TODO Auto-generated method stub

  }

  public void show() {
    // TODO Auto-generated method stub

  }

  public String getOnclose() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOnload() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOnunload() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getRootObject() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getTitle() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulDomContainer getXulDomContainer() {
    // TODO Auto-generated method stub
    return null;
  }

  public void invokeLater( Runnable runnable ) {
    // TODO Auto-generated method stub

  }

  public void setOnclose( String onclose ) {
    // TODO Auto-generated method stub

  }

  public void setOnload( String onload ) {
    // TODO Auto-generated method stub

  }

  public void setOnunload( String onunload ) {
    // TODO Auto-generated method stub

  }

  public void setTitle( String title ) {
    // TODO Auto-generated method stub

  }

  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    // TODO Auto-generated method stub

  }

  public void addComponent( XulComponent component ) {
    // TODO Auto-generated method stub

  }

  public void addComponentAt( XulComponent component, int idx ) {
    // TODO Auto-generated method stub

  }

  public Orient getOrientation() {
    // TODO Auto-generated method stub
    return null;
  }

  public void addPropertyChangeListener( PropertyChangeListener listener ) {
    // TODO Auto-generated method stub

  }

  public String getBgcolor() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getFlex() {
    // TODO Auto-generated method stub
    return 0;
  }

  public int getHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getManagedObject() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getPadding() {
    // TODO Auto-generated method stub
    return 0;
  }

  public String getTooltiptext() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean isDisabled() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removePropertyChangeListener( PropertyChangeListener listener ) {
    // TODO Auto-generated method stub

  }

  public void setBgcolor( String bgcolor ) {
    // TODO Auto-generated method stub

  }

  public void setDisabled( boolean disabled ) {
    // TODO Auto-generated method stub

  }

  public void setFlex( int flex ) {
    // TODO Auto-generated method stub

  }

  public void setHeight( int height ) {
    // TODO Auto-generated method stub

  }

  public void setId( String id ) {
    // TODO Auto-generated method stub

  }

  public void setOnblur( String method ) {
    // TODO Auto-generated method stub

  }

  public void setPadding( int padding ) {
    // TODO Auto-generated method stub

  }

  public void setTooltiptext( String tooltip ) {
    // TODO Auto-generated method stub

  }

  public void setWidth( int width ) {
    // TODO Auto-generated method stub

  }

  public void addChild( Element element ) {
    // TODO Auto-generated method stub

  }

  public void addChildAt( Element element, int idx ) {
    // TODO Auto-generated method stub

  }

  public String getAttributeValue( String attributeName ) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Attribute> getAttributes() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<XulComponent> getChildNodes() {
    // TODO Auto-generated method stub
    return null;
  }

  public Document getDocument() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getElementById( String id ) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getElementByXPath( String path ) {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getElementObject() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<XulComponent> getElementsByTagName( String tagName ) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getFirstChild() {
    // TODO Auto-generated method stub
    return null;
  }

  public Namespace getNamespace() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getText() {
    // TODO Auto-generated method stub
    return null;
  }

  public void removeChild( Element element ) {
    // TODO Auto-generated method stub

  }

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    // TODO Auto-generated method stub

  }

  public void setAttribute( Attribute attribute ) {
    // TODO Auto-generated method stub

  }

  public void setAttribute( String name, String value ) {
    // TODO Auto-generated method stub

  }

  public void setAttributes( List<Attribute> attribute ) {
    // TODO Auto-generated method stub

  }

  public void setNamespace( String prefix, String uri ) {
    // TODO Auto-generated method stub

  }

  public void removeComponent( XulComponent component ) {

  }

  public void adoptAttributes( XulComponent component ) {

    // TODO Auto-generated method stub

  }

  public String getInsertafter() {
    return null;
  }

  public String getInsertbefore() {
    return null;
  }

  public String getOnblur() {
    return null;
  }

  public int getPosition() {
    return 0;
  }

  public boolean getRemoveelement() {
    return false;
  }

  public boolean isVisible() {
    return true;
  }

  public void setInsertafter( String id ) {

    // TODO Auto-generated method stub

  }

  public void setInsertbefore( String id ) {

    // TODO Auto-generated method stub

  }

  public void setPosition( int pos ) {

    // TODO Auto-generated method stub

  }

  public void setRemoveelement( boolean flag ) {

    // TODO Auto-generated method stub

  }

  public String getAlign() {
    return Align.START.toString();
  }

  public void setAlign( String align ) {

    // TODO Auto-generated method stub

  }

  public void center() {

    // TODO Auto-generated method stub
  }
}
