/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulFileDialog extends XulComponent {
  public enum RETURN_CODE {
    OK, CANCEL, ERROR
  }

  public enum SEL_TYPE {
    SINGLE, MULTIPLE
  }

  public enum VIEW_TYPE {
    FILES_DIRECTORIES, DIRECTORIES
  }

  public RETURN_CODE showOpenDialog();

  public RETURN_CODE showOpenDialog( Object f );

  public RETURN_CODE showSaveDialog();

  public RETURN_CODE showSaveDialog( Object f );

  public Object getFile();

  public Object[] getFiles();

  public void setSelectionMode( SEL_TYPE type );

  public SEL_TYPE getSelectionMode();

  public void setViewType( VIEW_TYPE type );

  public VIEW_TYPE getViewType();

  public void setModalParent( Object parent );
}
