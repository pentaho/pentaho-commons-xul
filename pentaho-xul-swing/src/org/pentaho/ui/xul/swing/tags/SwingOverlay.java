/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Copyright 2009 Pentaho Corporation.  All rights reserved.
 */

/**
 *
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

/**
 * @author nbaker
 */
public class SwingOverlay extends AbstractSwingContainer implements XulScript {
  private String id;
  private String src;

  public SwingOverlay(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String className) {
    this.src = className;
  }

  public void layout() {
  }

}