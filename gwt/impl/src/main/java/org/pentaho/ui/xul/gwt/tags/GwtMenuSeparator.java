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
