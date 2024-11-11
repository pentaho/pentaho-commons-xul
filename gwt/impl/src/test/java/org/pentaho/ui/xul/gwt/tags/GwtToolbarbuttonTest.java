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

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.ui.xul.gwt.tags.util.ImageUtil;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( GwtMockitoTestRunner.class )
public class GwtToolbarbuttonTest extends TestCase {

  @Test
  public void testSetAttribute_IMAGEALTTEXT() {
    // SETUP
    String attributeKey = "imagealttext";
    String altText1 = "SOME ALT TEST TEXT 1";
    String altText2 = "another alternative test text 2";

    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( mockToolbarButton );

    // EXECUTE
    testInstance.setAttribute( attributeKey, altText1 );
    testInstance.setAttribute( "pen:" + attributeKey, altText2 ); // testing removal of "pen:"

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( altText1 ) );
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( altText2 ) );
  }

  @Test
  public void testGetImageAltText() {
    // Scenario 1: null toolbarbutton

    // SETUP
    GwtToolbarbutton testInstance1 = new GwtToolbarbutton( null );

    // EXECUTE & VERIFY
    assertNull( testInstance1.getImageAltText() );

    // Scenario 2: non-null toolbarbutton

    // SETUP
    String altText = "SOME TEST ALTERNATIVE IMAGE TEXT";
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    when( mockToolbarButton.getImageAltText() ).thenReturn( altText );
    GwtToolbarbutton testInstance2 = new GwtToolbarbutton( mockToolbarButton );

    // EXECUTE
    assertEquals( altText, testInstance2.getImageAltText() );
  }

  @Test
  public void testSetImageAltText() {
    // Scenario 1: null toolbarbutton

    // SETUP
    GwtToolbarbutton testInstance1 = new GwtToolbarbutton( null );

    // EXECUTE & VERIFY
    // no exception is thrown here
    testInstance1.setImageAltText( "someText" );

    // Scenario 2: non-null toolbarbutton

    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance2 = new GwtToolbarbutton( mockToolbarButton );
    String altText = "SOME TEST ALTERNATIVE IMAGE TEXT";

    // EXECUTE
    testInstance2.setImageAltText( altText );

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( altText ) );
  }

  @Test
  public void testInitSetImageAltText() {
    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    ImageUtil mockImageUtil = mock( ImageUtil.class );
    Element mockElement = mock( Element.class );
    String altText = "test alternative image text";
    when( mockImageUtil.getAltText( eq( mockElement ) ) )
      .thenReturn( altText );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( "testToolbarbutton", mockToolbarButton, mockImageUtil );

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( altText ) );
  }
}
