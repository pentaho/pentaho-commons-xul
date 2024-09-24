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

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulFileUpload extends XulComponent {

  public String getAction();

  public void setAction( String action );

  public void setOnUploadSuccess( String method );

  public void setOnUploadFailure( String method );

  public String getOnUploadSuccess();

  public String getOnUploadFailure();

  public String getSeletedFile();

  public void setSelectedFile( String name );

  public void submit();

  public void addParameter( String name, String value );
}
