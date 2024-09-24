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

package org.pentaho.ui.xul.swing.tags;

import javax.swing.JProgressBar;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author mlowery
 */
public class SwingProgressmeter extends SwingElement implements XulProgressmeter {

  private JProgressBar progressmeter;

  public SwingProgressmeter( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "progressmeter" ); //$NON-NLS-1$
    progressmeter = new JProgressBar();
    setManagedObject( progressmeter );
  }

  public int getMaximum() {
    return progressmeter.getMaximum();
  }

  public int getMinimum() {
    return progressmeter.getMinimum();
  }

  public int getValue() {
    return progressmeter.getValue();
  }

  public boolean isIndeterminate() {
    return progressmeter.isIndeterminate();
  }

  public void setIndeterminate( boolean indeterminate ) {
    progressmeter.setIndeterminate( indeterminate );
  }

  public void setMaximum( int value ) {
    progressmeter.setMaximum( value );
  }

  public void setMinimum( int value ) {
    progressmeter.setMinimum( value );
  }

  public void setValue( int value ) {
    progressmeter.setValue( value );
  }

  public void setMode( String mode ) {
    progressmeter.setIndeterminate( MODE_INDETERMINATE.equals( mode ) );
  }

}
