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


package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtProgressmeter extends SwtElement implements XulProgressmeter {

  protected ProgressBar progressmeter;

  private boolean indeterminate;

  protected XulComponent parent;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialValue;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMaximum;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMinimum;

  public SwtProgressmeter( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    this.parent = parent;
    // SWT progress bar not created until mode is known (in layout method)
  }

  protected ProgressBar createNewProgressmeter( Composite parent ) {
    ProgressBar progressmeter = new ProgressBar( parent, isIndeterminate() ? SWT.INDETERMINATE : SWT.NONE );

    progressmeter.setSelection( initialValue );
    progressmeter.setMinimum( initialMinimum );
    progressmeter.setMaximum( initialMaximum );
    return progressmeter;
  }

  public int getMaximum() {
    return progressmeter.getMaximum();
  }

  public int getMinimum() {
    return progressmeter.getMinimum();
  }

  public int getValue() {
    return progressmeter.getSelection();
  }

  public boolean isIndeterminate() {
    return indeterminate;
  }

  public void setIndeterminate( boolean indeterminate ) {
    this.indeterminate = indeterminate;
  }

  public void setMaximum( int value ) {
    if ( null != progressmeter ) {
      progressmeter.setMaximum( value );
    } else {
      initialMaximum = value;
    }
  }

  public void setMinimum( int value ) {
    if ( null != progressmeter ) {
      progressmeter.setMinimum( value );
    } else {
      initialMinimum = value;
    }
  }

  public void setMode( String mode ) {
    indeterminate = MODE_INDETERMINATE.equals( mode );
  }

  public void setValue( final int value ) {
    Display.getDefault().syncExec( new Runnable() {
      public void run() {
        if ( null != progressmeter ) {
          progressmeter.setSelection( value );
        } else {
          initialValue = value;
        }
      }
    } );

  }

  @Override
  public void layout() {
    progressmeter = createNewProgressmeter( (Composite) parent.getManagedObject() );
    setManagedObject( progressmeter );
  }

}
