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
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * General class to handle GWT Image operations mostly related to WCAG 2.1 requirements.
 */
public class ImageUtil {

  /**
   * WCAG 2.1 default value for attribute 'alt' when image doesn't have alternative text
   */
  protected static final String DEFAULT_IMAGE_ALT_WCAG_TEXT = "";

  public static final String ATTRIBUTE_PEN_IMAGE_ALT_TEXT = "pen:imagealttext";

  public static final String ATTRIBUTE_TOOLTIP_TEXT = "tooltiptext";

  /**
   * Retrieves image's alternative text based on attributes.
   * Attributes order of precedence:
   * <ol>
   *   <li>argument: {@code srcEle}'s attribute {@link #ATTRIBUTE_PEN_IMAGE_ALT_TEXT}</li>
   *   <li>argument: {@code srcEle}'s attribute {@link #ATTRIBUTE_TOOLTIP_TEXT}</li>
   *   <li>default value: {@link #DEFAULT_IMAGE_ALT_WCAG_TEXT} </li>
   * </ol>
   * @param  srcEle
   * @return first non-null text based on order of precedence, else {@link #DEFAULT_IMAGE_ALT_WCAG_TEXT}
   */
  public String getAltText( Element srcEle ) {
    return getAltText( srcEle, DEFAULT_IMAGE_ALT_WCAG_TEXT );
  }

  /**
   * Retrieves image's alternative text based on attributes.
   * Attributes order of precedence:
   * <ol>
   *   {@link AbstractGwtXulComponent#init(Element, XulDomContainer)}</li>
   *   <li>argument: {@code srcEle}'s attribute {@link #ATTRIBUTE_PEN_IMAGE_ALT_TEXT}</li>
   *   <li>argument: {@code srcEle}'s attribute {@link #ATTRIBUTE_TOOLTIP_TEXT}</li>
   *   <li>default value: <code>defaultAltText</code></li>
   * </ol>
   * @param srcEle
   * @param defaultAltText
   *    * @return first non-null text based on order of precedence, else <code>defaultAltText</code>
   */
  public String getAltText( Element srcEle, String defaultAltText ) {
    List<String> texts = new ArrayList<>();
    if ( srcEle != null ) {
      // access from xul tag directly either "pen:imagealttext"
      texts.add( srcEle.getAttribute( ATTRIBUTE_PEN_IMAGE_ALT_TEXT ) );
      // fall back on "tooltiptext" to mirror hover over text
      texts.add( srcEle.getAttribute( ATTRIBUTE_TOOLTIP_TEXT ) );
    }

    return texts.stream()
      .filter( t -> !StringUtils.isEmpty( t ) )
      .findFirst()
      .orElse( defaultAltText );
  }

  /**
   * Setting miscellaneous attributes of image after it's image instantiation.
   * @param image
   * @return
   */
  public Image setImageDefaults( Image image ) {
    return setDefaultImageAltTextIfNotSet( image );
  }

  /**
   * Set image's alternative text to {@link #DEFAULT_IMAGE_ALT_WCAG_TEXT} if originally undefined.
   * @param image
   * @return
   */
  Image setDefaultImageAltTextIfNotSet( Image image ) {
    if ( image != null && StringUtils.isEmpty( image.getAltText() ) ) {
      image.setAltText( DEFAULT_IMAGE_ALT_WCAG_TEXT );
    }
    return image;
  }
}
