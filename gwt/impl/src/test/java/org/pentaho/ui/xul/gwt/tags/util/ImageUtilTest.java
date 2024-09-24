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

package org.pentaho.ui.xul.gwt.tags.util;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( GwtMockitoTestRunner.class )
public class ImageUtilTest extends TestCase {

  @Test
  public void testGetAltText_defaultAltText() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    Element mockElement = mock( Element.class );
    String testDefaultAltText = "TEST DEFAULT ALTERNATIVE TEXT";

    // EXECUTE & VERIFY
    assertEquals( testDefaultAltText, testInstance.getAltText( mockElement, testDefaultAltText ) );
  }

  @Test
  public void testGetAltText_Null_Element() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    String testDefaultAltText = "TEST DEFAULT ALTERNATIVE TEXT";

    // EXECUTE & VERIFY
    assertEquals( testDefaultAltText, testInstance.getAltText( null, testDefaultAltText ) );
  }

  @Test
  public void testGetAltText_OrderOfPrecedence_3_Default() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    AbstractGwtXulComponent mockAGXC = mock( AbstractGwtXulComponent.class );
    Element mockElement = mock( Element.class );

    // NOTE: no attributes relating to "pen:imagealttext" or "tooltip"

    // EXECUTE & VERIFY
    assertEquals( ImageUtil.DEFAULT_IMAGE_ALT_WCAG_TEXT, testInstance.getAltText( mockElement ) );
  }

  @Test
  public void testGetAltText_OrderOfPrecedence_2_ToolTipText() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    AbstractGwtXulComponent mockAGXC = mock( AbstractGwtXulComponent.class );
    Element mockElement = mock( Element.class );
    String testToolTipText = "TEST TOOL TIP TEXT";

    // simulating basic attributes
    mockGetAttribute( mockElement, ImageUtil.ATTRIBUTE_TOOLTIP_TEXT, testToolTipText );
    // NOTE: no attributes relating to "imagealttext"

    // EXECUTE & VERIFY
    assertEquals( testToolTipText, testInstance.getAltText( mockElement ) );
  }

  @Test
  public void testGetAltText_OrderOfPrecedence_1_PenImageAltText() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    AbstractGwtXulComponent mockAGXC = mock( AbstractGwtXulComponent.class );
    Element mockElement = mock( Element.class );
    String testToolTipText = "TEST TOOL TIP TEXT";
    String testAltImageTextTag = "TAG - TEST SOME ALTERNATIVE IMAGE TEXT";
    String testPenAltImageTextTag = "TAG - PEN:TEST SOME ALTERNATIVE IMAGE TEXT";

    // simulating basic attributes
    mockGetAttribute( mockElement, ImageUtil.ATTRIBUTE_TOOLTIP_TEXT, testToolTipText );
    mockGetAttribute( mockElement, ImageUtil.ATTRIBUTE_PEN_IMAGE_ALT_TEXT, testPenAltImageTextTag );

    // EXECUTE & VERIFY
    assertEquals( testPenAltImageTextTag, testInstance.getAltText( mockElement ) );
  }

  @Test
  public void testSetImageDefaults() {
    // SETUP
    ImageUtil testInstance = new ImageUtil();
    String altText = "Test ALTERNATIVE IMAGE TEXT";

    // EXECUTE & VERIFY: Null
    assertNull( testInstance.setImageDefaults( null ) );

    // EXECUTE & VERIFY: alt text already set, don't override
    Image mockImage1 = mock( Image.class );
    when( mockImage1.getAltText() ).thenReturn( altText );
    assertEquals( mockImage1, testInstance.setImageDefaults(  mockImage1 ) );
    verify( mockImage1, never() ).setAltText( anyString() );

    // EXECUTE & VERIFY: alt text empty, override
    Image mockImage2 = mock( Image.class );
    when( mockImage2.getAltText() ).thenReturn( "" );
    assertEquals( mockImage2, testInstance.setImageDefaults(  mockImage2 ) );
    verify( mockImage2 ).setAltText( ImageUtil.DEFAULT_IMAGE_ALT_WCAG_TEXT  );
  }

  void mockGetAttribute( Element mockElement, String key, String value ) {
    when( mockElement.getAttribute( eq( key ) ) ).thenReturn( value );
  }
}
