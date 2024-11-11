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


package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * A Xul messagebox wrapper. This widget is not rendered from XUL XML, but instead allows us to code in keeping
 * with the agnostic nature of the framework regarding underlying UI implementation.
 * 
 * Rather than try to build message boxes from standard XUL XML definitions, it seemed much more efficient at this
 * time to use the rich messagebox utilities already build into each UI technology.
 * 
 * @author gmoran
 * 
 */
public interface XulMessageBox extends XulComponent {

  /**
   * Sets the title of the messagebox dialog.
   * 
   * @param title
   *          The dialog title to display.
   */
  public void setTitle( String title );

  /**
   * 
   * @return The messagebox dialog title.
   */
  public String getTitle();

  /**
   * Sets the message for display in this messagebox.
   * 
   * @param message
   *          The message for display.
   */
  public void setMessage( String message );

  /**
   * 
   * @return The messagebox's message.
   */
  public String getMessage();

  /**
   * Sets the buttons to show on the messagebox dialog. Values for this array will be implementation specific. If
   * buttons is set to null, the default (OK) will be shown.
   * 
   * @param buttons
   *          An array of objects representing buttons - implementation specific values.
   */
  public void setButtons( Object[] buttons );

  /**
   * 
   * @return The array of values representing the buttons to show on this messagebox.
   */
  public Object[] getButtons();

  /**
   * Sets a custom icon on the messagebox dialog. Values for this parameter are implementation specific. If icon is
   * set to null, no icon is displayed. If this value is not set at all, the underlying implementation's version of
   * an information icon is displayed by default.
   * 
   * @param icon
   *          The icon to display on the messagebox.
   */
  public void setIcon( Object icon );

  /**
   * 
   * @return the messagebox's icon.
   */
  public Object getIcon();

  /**
   * Creates and opens the messagebox for viewing. Return values are implementation specific.
   * 
   * @return the return value for the dialog.
   */
  public int open();

  /**
   * Sets whether or not to display the message as a simple label or in a scrollable container.
   * 
   * @param scroll
   *          boolean flag
   */
  public void setScrollable( boolean scroll );

  public int getHeight();

  public void setHeight( int height );

  public int getWidth();

  public void setWidth( int width );

  public void setModalParent( Object parent );

  public void setAcceptLabel( String label );

}
