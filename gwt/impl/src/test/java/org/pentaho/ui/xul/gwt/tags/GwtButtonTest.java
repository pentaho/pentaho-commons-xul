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
