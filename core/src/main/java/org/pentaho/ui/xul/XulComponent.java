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

/**
 * 
 */

package org.pentaho.ui.xul;

import org.pentaho.ui.xul.binding.BindingProvider;
import org.pentaho.ui.xul.dom.Element;

import java.beans.PropertyChangeListener;

/**
 * The base interface for any XUL widget.
 * 
 * @author nbaker
 * 
 */
public interface XulComponent extends Element, XulEventSource {

  /**
   * The manageObject is the rendering control or container that corresponds to this XUL component.
   * 
   * @return the impl control that represents this XUL component under the covers.
   */
  public Object getManagedObject();

  public void setManagedObject( Object managed );

  /**
   * The name is the tag name that this component corresponds to in XUL XML.
   * 
   * @return the XUL tag name.
   */
  public String getName();

  /**
   * Every element in XUL can have a unique id
   * 
   * @param id
   *          sets the component's id
   */
  public void setId( String id );

  /**
   * 
   * @return the id for this component.
   */
  public String getId();

  /**
   * From the XUL specification: http://www.xulplanet.com/references/elemref/ref_XULElement.html#attr_flex
   * Indicates the flexibility of the element, which indicates how an element's container distributes remaining
   * empty space among its children. Flexible elements grow and shrink to fit their given space. Elements with
   * larger flex values will be made larger than elements with lower flex values, at the ratio determined by the
   * two elements. The actual value is not relevant unless there are other flexible elements within the same
   * container. Once the default sizes of elements in a box are calculated, the remaining space in the box is
   * divided among the flexible elements, according to their flex ratios.
   * 
   * @return the flex value for this component
   */
  public int getFlex();

  /**
   * This field makes sense only relative to the values of its siblings. NOTE that if only one sibling has a flex
   * value, then that sibling gets ALL the extra space in the container, no matter what the flex value is.
   * 
   * @param flex
   */
  public void setFlex( int flex );

  /**
   * Sets the method that will be invoked when this component loses focus. Also hooks up any listeners for this
   * event.
   * 
   * @param method
   *          the method to execute when the focus is lost.
   */
  public void setOnblur( String method );

  /**
   * Gets the method that will be invoked when this component loses focus. Also hooks up any listeners for this
   * event.
   */
  public String getOnblur();

  /**
   * Set the width of this control
   * 
   */
  public void setWidth( int width );

  /**
   * Returns the width of the component
   * 
   * @return the component's width
   */
  public int getWidth();

  /**
   * Set the height of the component
   * 
   */
  public void setHeight( int height );

  /**
   * Returns the height of the component
   * 
   * @return the component's height
   */
  public int getHeight();

  public void addPropertyChangeListener( PropertyChangeListener listener );

  public void removePropertyChangeListener( PropertyChangeListener listener );

  /**
   * Sets the enablement state of the component
   * 
   * @param disabled
   *          sets this components enabled state
   * 
   */
  public void setDisabled( boolean disabled );

  /**
   * XUL's attribute is "disabled", thus this acts exactly the opposite of SWT/Swing/AWT. If the property is not
   * available, then the control is enabled.
   * 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled();

  public void setTooltiptext( String tooltip );

  public String getTooltiptext();

  public void setBgcolor( String bgcolor );

  public String getBgcolor();

  public void setPadding( int padding );

  public int getPadding();

  public void setSpacing( int spacing );

  public int getSpacing();

  public void adoptAttributes( XulComponent component );

  public String getInsertbefore();

  public void setInsertbefore( String id );

  public String getInsertafter();

  public void setInsertafter( String id );

  public int getPosition();

  public void setPosition( int pos );

  public boolean getRemoveelement();

  public void setRemoveelement( boolean flag );

  public boolean isVisible();

  public void setVisible( boolean visible );

  /**
   * Called by the parser when the document is fully parsed. Some implementations require knowledge of parents
   * above the document, or only behave properly when an unbroken chain to the root is in place.
   */
  public void onDomReady();

  /**
   * Specifies the alignment of children when the size of the container is greater than the size of it's children.
   * 
   * @param align
   *          one of [start, center, end].
   */
  public void setAlign( String align );

  /**
   * Returns the alignment of children.
   * 
   * @return String specifying the alignment [start, center, end].
   */
  public String getAlign();

  /**
   * Sets the ID of the popup menu to show on context action (right-click, etc)
   * 
   * @param id
   */
  public void setContext( String id );

  public String getContext();

  /**
   * Sets the ID of the popup menu to show when control is right-clicked. (drop-downs)
   * 
   * @param id
   */
  public void setPopup( String id );

  public String getPopup();

  /**
   * Sets the ID of the menu to show when the control is clicked
   * 
   * @param id
   */
  public void setMenu( String id );

  public String getMenu();

  /**
   * Sets the ondrag event handler, also notifies the element that it should be draggable
   * 
   * @param ondrag
   *          the controller method to call.
   */
  public void setOndrag( String ondrag );

  public String getOndrag();

  /**
   * Sets the drageffect value for dnd (move and copy currently supported) When specified, this should use the pen:
   * syntax
   * 
   * @param drageffect
   *          move or copy
   */
  public void setDrageffect( String drageffect );

  public String getDrageffect();

  /**
   * Sets the ondrop event handler, also notifies the element that it should accept drop events.
   * 
   * @param ondrop
   *          the controller method to call.
   */
  public void setOndrop( String ondrop );

  public String getOndrop();

  public void setDropvetoer( String dropVetoMethod );

  public String getDropvetoer();

  public void setBindingProvider( BindingProvider bindingProvider );
}
