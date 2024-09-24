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

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScale;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingScale extends SwingElement implements XulScale {

  private int min = 0;
  private int max = 100;
  private int value = 0;
  private int increment = 1;
  private int pageIncrement = 1;
  private JSlider slider;

  private String direction;

  public SwingScale( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "scale" );
  }

  public String getDir() {
    return direction;
  }

  public int getMax() {
    return max;
  }

  public int getMin() {
    return min;
  }

  public int getPageincrement() {
    return pageIncrement;
  }

  public int getValue() {
    return value;
  }

  public void setDir( String direction ) {
    this.direction = direction;
  }

  public void setMax( int max ) {
    this.max = max;
  }

  public void setMin( int min ) {
    this.min = min;
  }

  public void setPageincrement( int increment ) {
    this.pageIncrement = increment;
  }

  public int getInc() {
    return this.increment;
  }

  public void setInc( int increment ) {
    this.increment = increment;

  }

  public void setValue( int value ) {
    int prevVal = this.value;
    this.value = value;
    if ( slider != null ) {
      slider.setValue( value );
    }
    this.changeSupport.firePropertyChange( "value", prevVal, value );
  }

  public void layout() {
    int orient = ( orientation == Orient.VERTICAL ) ? JSlider.VERTICAL : JSlider.HORIZONTAL;
    slider = new JSlider( orient, this.min, this.max, Math.max( min, this.value ) );
    setManagedObject( slider );

    slider.setMajorTickSpacing( this.pageIncrement );
    // slider.setExtent(this.pageIncrement);

    slider.setSnapToTicks( false );
    slider.setPaintTicks( true );

    slider.addChangeListener( new ChangeListener() {

      public void stateChanged( ChangeEvent arg0 ) {
        setValue( SwingScale.this.slider.getValue() );
      }

    } );

  }

}
