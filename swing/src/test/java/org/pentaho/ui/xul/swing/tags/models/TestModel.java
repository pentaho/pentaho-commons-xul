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
