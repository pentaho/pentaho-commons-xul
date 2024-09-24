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

package org.pentaho.ui.xul.html.tags.transmenu;

import java.beans.PropertyChangeListener;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.html.IHtmlElement;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.util.Orient;

public abstract class HtmlElement extends AbstractXulComponent implements IHtmlElement {

  protected Orient orientation;

  protected Orient orient = Orient.HORIZONTAL;

  public HtmlElement(String tagName) {
    super(tagName);
  }

  public abstract void getHtml(StringBuilder sb);

  public abstract void getScript(Map<String, String> properties, StringBuilder sb);

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    // TODO Auto-generated method stub

  }

  public boolean isDisabled() {
    // TODO Auto-generated method stub
    return false;
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    // TODO Auto-generated method stub

  }

  public void setDisabled(boolean disabled) {
    // TODO Auto-generated method stub

  }

  public void setOrient(String orientation) {
    this.orientation = Orient.valueOf(orientation.toUpperCase());
  }

  public String getOrient() {
    return orientation.toString();
  }

  public Orient getOrientation() {
    return orientation;
  }

  public void adoptAttributes(XulComponent component) {

    // TODO Auto-generated method stub 

  }

}
