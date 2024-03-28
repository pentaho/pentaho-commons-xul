/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * O
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2023-2024 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.gwt.widgets.client.buttons.ImageButton;
import org.pentaho.ui.xul.gwt.tags.util.ImageUtil;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( GwtMockitoTestRunner.class )
public class GwtButtonTest extends TestCase {

  @Test
  public void testSetAttribute_IMAGEALTTEXT() {
    // SETUP
    String attributeKey = "imagealttext";
    String altText1 = "SOME ALT TEST TEXT 1";

    ImageButton mockImageButton = mock( ImageButton.class );
    GwtButton testInstance = new GwtButton( null, null, mockImageButton, null );

    // EXECUTE
    testInstance.setAttribute( attributeKey, altText1 );

    // VERIFY
    verify( mockImageButton, times( 1 ) ).setAltText( eq( altText1 ) );
  }

  @Test
  public void testInitSetImageAltText() {
    // SETUP
    ImageButton mockImageButton = mock( ImageButton.class );
    ImageUtil mockImageUtil = mock( ImageUtil.class );
    Element mockElement = mock( Element.class );
    String altText = "test alternative image text";
    when( mockImageUtil.getAltText( eq( mockElement ) ) ).thenReturn( altText );
    GwtButton testInstance = new GwtButton( null, null, mockImageButton, mockImageUtil );

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockImageButton, times( 1 ) ).setAltText( eq( altText ) );
  }

  @Test
  public void testSetImageAltText() {
    // Scenario 1: null image

    // SETUP
    GwtButton testInstance1 = new GwtButton( null, null, null, null );

    // EXECUTE & VERIFY
    // no exception is thrown here
    testInstance1.setImageAltText( "someText" );

    // Scenario 2: non-null imagebutton

    // SETUP
    String altText = "SOME TEST ALTERNATIVE IMAGE TEXT";
    ImageButton mockImageButton = mock( ImageButton.class );
    when( mockImageButton.getAltText() ).thenReturn( altText );
    GwtButton testInstance2 = new GwtButton( null, null, mockImageButton, null );

    // EXECUTE
    testInstance2.setImageAltText( altText );

    // VERIFY
    verify( mockImageButton, times( 1 ) ).setAltText( eq( altText ) );
  }

  @Test
  public void testGetImageAltText() {
    // Scenario 1: null imagebutton

    // SETUP
    GwtButton testInstance1 = new GwtButton( null, null, null, null );

    // EXECUTE & VERIFY
    assertNull( testInstance1.getImageAltText() );

    // Scenario 2: non-null imagebutton

    // SETUP
    String altText = "SOME TEST ALTERNATIVE IMAGE TEXT";
    ImageButton mockImageButton = mock( ImageButton.class );
    when( mockImageButton.getAltText() ).thenReturn( altText );
    GwtButton testInstance2 = new GwtButton( null, null, mockImageButton, null );

    // EXECUTE
    assertEquals( altText, testInstance2.getImageAltText() );
  }
}
