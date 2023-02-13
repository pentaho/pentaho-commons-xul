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
 * Copyright (c) 2023 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.xml.client.Element;
import junit.framework.TestCase;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.pentaho.ui.xul.gwt.tags.GwtToolbarbutton.DEFAULT_IMAGE_ALT_WCAG_TEXT;

public class GwtToolbarbuttonTest extends TestCase {

  public static final String DEFAULT_ALT_TEXT = DEFAULT_IMAGE_ALT_WCAG_TEXT;

  public static final String ATTR_IMAGE_ALT_TEXT = "imagealttext";

  public static final String PEN_ATTR_IMAGE_ALT_TEXT = "pen:" +  ATTR_IMAGE_ALT_TEXT;

  public static final String ATTR_TOOL_TIP_TEXT = "tooltiptext";

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

  /**
   * In absence of being able to test {@link GwtToolbarbutton#init}, created this test.
   * GwtToolbarbutton#init has logic in parent class that might override alternative image text
   */
  public void testInitSetImageAltText_OrderOfPrecedence_Default() {
    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( mockToolbarButton );
    Element mockElement = mock( Element.class );

    // simulating basic attributes
    // not using certain keywords to prevent UnsupportedOperationException: ERROR: GWT.create()
    initSetAttribute( testInstance, "_id_", "testButton" );
    initSetAttribute( testInstance, "_images_", "test_image.png" );
    // NOTE: no attributes relating to "imagealttext" or "tooltip"

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( DEFAULT_ALT_TEXT ) );
  }

  /**
   * In absence of being able to test {@link GwtToolbarbutton#init}, created this test.
   * GwtToolbarbutton#init has logic in parent class that might override alternative image text
   */
  public void testInitSetImageAltText_OrderOfPrecedence_ToolTipText() {
    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( mockToolbarButton );
    Element mockElement = mock( Element.class );
    String testToolTipText = "TEST TOOL TIP TEXT";
    when( mockElement.getAttribute( eq( ATTR_TOOL_TIP_TEXT ) ) ).thenReturn( testToolTipText );

    // simulating basic attributes
    // not using certain keywords to prevent UnsupportedOperationException: ERROR: GWT.create()
    initSetAttribute( testInstance, "_id_", "testButton" );
    initSetAttribute( testInstance, "_images_", "test_image.png" );
    // NOTE: no attributes relating to "imagealttext"

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( testToolTipText ) );
  }

  /**
   * In absence of being able to test {@link GwtToolbarbutton#init}, created this test.
   * GwtToolbarbutton#init has logic in parent class that might override alternative image text
   */
  public void testInitSetImageAltText_OrderOfPrecedence_PenImageAltText() {
    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( mockToolbarButton );
    Element mockElement = mock( Element.class );
    String testToolTipText = "TEST TOOL TIP TEXT";
    String testPenAltImageText = "PEN: TEST SOME ALTERNATIVE IMAGE TEXT";
    when( mockElement.getAttribute( eq( ATTR_TOOL_TIP_TEXT ) ) ).thenReturn( testToolTipText );
    when( mockElement.getAttribute( eq( PEN_ATTR_IMAGE_ALT_TEXT ) ) ).thenReturn( testPenAltImageText );

    // simulating basic attributes
    // not using certain keywords to prevent UnsupportedOperationException: ERROR: GWT.create()
    initSetAttribute( testInstance, "_id_", "testButton" );
    initSetAttribute( testInstance, "_images_", "test_image.png" );
    // NOTE: no attributes relating to "imagealttext"

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( testPenAltImageText ) );
  }

  /**
   * In absence of being able to test {@link GwtToolbarbutton#init}, created this test.
   * GwtToolbarbutton#init has logic in parent class that might override alternative image text
   */
  public void testInitSetImageAltText_OrderOfPrecedence_AttrImageAltText() {
    // SETUP
    ToolbarButton mockToolbarButton = mock( ToolbarButton.class );
    GwtToolbarbutton testInstance = new GwtToolbarbutton( mockToolbarButton );
    Element mockElement = mock( Element.class );
    String testToolTipText = "TEST TOOL TIP TEXT";
    String testPenAltImageText = "PEN: TEST SOME ALTERNATIVE IMAGE TEXT";
    String testAttrAltImageText = "ATTR: TEST SOME ALTERNATIVE IMAGE TEXT";
    when( mockElement.getAttribute( eq( ATTR_TOOL_TIP_TEXT ) ) ).thenReturn( testToolTipText );
    when( mockElement.getAttribute( eq( PEN_ATTR_IMAGE_ALT_TEXT ) ) ).thenReturn( testPenAltImageText );

    // simulating basic attributes
    // not using certain keywords to prevent UnsupportedOperationException: ERROR: GWT.create()
    initSetAttribute( testInstance, "_id_", "testButton" );
    initSetAttribute( testInstance, "_images_", "test_image.png" );
    initSetAttribute( testInstance, ATTR_IMAGE_ALT_TEXT, testAttrAltImageText );

    //binding on #setAltImageText gets called
    verify( mockToolbarButton, times( 1 ) ).setImageAltText( eq( testAttrAltImageText ) );

    // EXECUTE
    testInstance.initSetImageAltText( mockElement );

    // VERIFY
    verify( mockToolbarButton, times( 2 ) ).setImageAltText( eq( testAttrAltImageText ) );
  }

  void initSetAttribute( GwtToolbarbutton gwtToolbarbutton, String key, String value ) {
    /**
     *  gwtToolbarbutton#setAttribute directly calls #setFn to be called
     *  ie #setAltImageText for gwtToolbarbutton.setAttribute("imagealttext", someValue)
     *  this simulates how super#init processes attributes
     */
    gwtToolbarbutton.setAttribute( key, value );
  }

}
