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

package org.pentaho.ui.xul.swt.custom;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.swt.DialogButton;

public class BasicDialog extends TitleAreaDialog {

  private Composite dialogArea = null;
  private Composite contentArea = null;
  private Composite buttonArea = null;

  private int height = -999;
  private int width = -999;

  public BasicDialog( Shell shell, boolean resizable ) {
    super( shell );
    setResizable( resizable );
    create();
  }

  public void addShellListener( ShellAdapter adapter ) {
    getShell().addShellListener( adapter );
  }

  /**
   * For test purposes only
   * 
   * @param args
   *          params to main
   */
  public static void main( String[] args ) {
    new BasicDialog( new Shell(), true ).open();
  }

  @Override
  protected Control createContents( Composite parent ) {

    contentArea = (Composite) super.createContents( parent );
    // TODO This should be dependent on whether we want to set up the header or not...
    getTitleImageLabel().dispose();

    return contentArea;
  }

  public Control getMainDialogArea() {
    return dialogArea;
  }

  @Override
  protected Control createDialogArea( Composite parent ) {
    Control c = super.createDialogArea( parent );
    // c.setBackground(c.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
    dialogArea = (Composite) c;
    return dialogArea;
  }

  public Composite getMainArea() {
    return dialogArea;
  }

  @Override
  protected void createButtonsForButtonBar( Composite parent ) {
    buttonArea = parent;
    buttonArea.setLayout( new GridLayout() );

    Label spacer = new Label( buttonArea, SWT.None );

    spacer.setSize( new Point( 10, 10 ) );
    GridData data = new GridData();
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    spacer.setLayoutData( data );

  }

  public Button createButton( DialogButton dialogButton, boolean defaultButton ) {
    Button button = createButton( buttonArea, dialogButton.getId(), dialogButton.getLabel(), defaultButton );
    return button;
  }

  public Composite getButtonArea() {
    return buttonArea;
  }

  public void resizeBounds() {
    initializeBounds();
  }

  /**
   * The dialog does a fine job of sizing appropriately, but we must support fixed size as well.. Don't set the height
   * and width and the default initial size should be reasonable.
   */
  @Override
  protected Point getInitialSize() {

    if ( ( height > 0 ) && ( width > 0 ) ) {
      return new Point( width, height );
    }
    return super.getInitialSize();
  }

  /**
   * This is silly, but we need this method to try to preempt a user from setting a size too small to render the child
   * components well.
   * 
   * @return the height and width that the dialog deems reasonable, without taking into account the user specified
   *         height and width.
   */
  public Point getPreferredSize() {
    return super.getInitialSize();
  }

  public void setHeight( int height ) {
    this.height = height;
    this.resizeBounds();
  }

  public void setWidth( int width ) {
    this.width = width;
    this.resizeBounds();
  }

  @Override
  protected void buttonPressed( int buttonId ) {
    // Empty on purpose
  }

  protected void setResizable( boolean resize ) {
    // TODO: find a better way to do this conditional bitmask
    if ( resize ) {
      setShellStyle( SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL );
    } else {
      setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
    }
  }
}
