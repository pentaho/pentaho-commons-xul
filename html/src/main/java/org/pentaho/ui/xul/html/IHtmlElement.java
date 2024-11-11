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


package org.pentaho.ui.xul.html;

import java.util.Map;

public interface IHtmlElement {

	public void getHtml( StringBuilder sb );
	
	public void getScript( Map<String,String> properties, StringBuilder sb );
	
}
