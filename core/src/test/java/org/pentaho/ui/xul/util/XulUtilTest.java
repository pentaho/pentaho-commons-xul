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
 * Copyright (c) 2019 Hitachi Vantara..  All rights reserved.
 */
package org.pentaho.ui.xul.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Element;


import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith( MockitoJUnitRunner.class )
public class XulUtilTest {
  @Mock Element element;

  @Test
  public void overflowAutoMeansScroll() {
    Mockito.when( element.getAttributes() )
      .thenReturn( asList( new Attribute( "n", "1" ), new Attribute( "style", "overflow: auto ; width: 235px" ) ) );
    assertTrue( XulUtil.isAutoScroll( element ) );
  }

  @Test
  public void noOverflowMeansNoScroll() {
    Mockito.when( element.getAttributes() )
      .thenReturn( asList( new Attribute( "n", "1" ), new Attribute( "style", "width: 235px" ) ) );
    assertFalse( XulUtil.isAutoScroll( element ) );
  }

  @Test
  public void noStyleMeansNoScroll() {
    Mockito.when( element.getAttributes() ).thenReturn( singletonList( new Attribute( "n", "1" ) ) );
    assertFalse( XulUtil.isAutoScroll( element ) );
  }
}
