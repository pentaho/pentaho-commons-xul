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
