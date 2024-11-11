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


package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.menuitem.PentahoMenuSeparator;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtMenuSeparator extends AbstractGwtXulComponent implements
		XulMenuseparator {

	static final String ELEMENT_NAME = "menuseparator"; //$NON-NLS-1$

	public static void register() {
		GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
			public Element newInstance() {
				return new GwtMenuSeparator();
			}
		});
	}

	PentahoMenuSeparator separator;

	public GwtMenuSeparator() {
		super(ELEMENT_NAME);
		separator = new PentahoMenuSeparator();
		setManagedObject(separator);
	}

	@Override
	public String toString() {
		return "-- separator --";
	}

    @Bindable
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        separator.getElement().getStyle()
                .setProperty("display", (this.visible) ? "" : "none");
    }
}
