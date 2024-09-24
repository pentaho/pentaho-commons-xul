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

package org.pentaho.ui.xul.swing.tags.models;

import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.swing.tags.models.TestModel.TestDocumentField;
import org.pentaho.ui.xul.util.AbstractModelList;

public class TestModel extends AbstractModelList<TestDocumentField> {
  private static final long serialVersionUID = 199023145675985806L;

  public TestModel() {
    super();
  }

  public class TestDocumentField extends XulEventSourceAdapter {
    private boolean selected = true;
    private String parameter = "";
    private String defaulted = "default";
    private String value = "";
    private String description = "";

    public TestDocumentField() {
      super();
    }

    public TestDocumentField( boolean selected, String parameter, String defaulted, String value, String description ) {
      super();
      this.selected = selected;
      this.parameter = parameter;
      this.defaulted = defaulted;
      this.value = value;
      this.description = description;
    }

    public boolean isSelected() {
      return selected;
    }

    public void setSelected( boolean selected ) {
      this.selected = selected;
    }

    public String getParameter() {
      return parameter;
    }

    public void setParameter( String parameter ) {
      this.parameter = parameter;
    }

    public String getDefaulted() {
      return defaulted;
    }

    public void setDefaulted( String defaulted ) {
      this.defaulted = defaulted;
    }

    public String getValue() {
      return value;
    }

    public void setValue( String value ) {
      this.value = value;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription( String description ) {
      this.description = description;
    }

  }

}
