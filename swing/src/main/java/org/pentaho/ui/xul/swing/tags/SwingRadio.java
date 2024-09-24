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

package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swing.SwingRoot;

/**
 * @author aphillips
 * 
 */
public class SwingRadio extends SwingElement implements XulRadio {

  private String value;
  private String group;
  private JRadioButton radioButton;
  private static final Log logger = LogFactory.getLog( SwingRadio.class );

  public SwingRadio( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "radio" );
    radioButton = new JRadioButton();
    setManagedObject( radioButton );

    // wrong type of event to listen to.. this one fires even on mouse over. use itemlistener
    // radioButton.addChangeListener(new ChangeListener(){
    // public void stateChanged(ChangeEvent evt){
    // logger.debug("firing selected property change: isSelected="+SwingRadio.this.isSelected());
    // SwingRadio.this.changeSupport.firePropertyChange("selected", null, SwingRadio.this.isSelected());
    // }
    // });

    radioButton.addItemListener( new ItemListener() {

      public void itemStateChanged( ItemEvent e ) {
        boolean selected = ( e.getStateChange() == ItemEvent.SELECTED );
        logger.debug( "firing selected property change: isSelected=" + selected );
        SwingRadio.this.changeSupport.firePropertyChange( "selected", null, selected );
      }

    } );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulRadio#isSelected()
   */
  public boolean isSelected() {
    // TODO Auto-generated method stub
    return radioButton.isSelected();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulRadio#setSelected(boolean)
   */
  public void setSelected( boolean selected ) {
    radioButton.setSelected( selected );
  }

  public void layout() {
  }

  public void setLabel( String label ) {
    radioButton.setText( label );
  }

  public String getLabel() {
    return radioButton.getText();
  }

  public boolean isDisabled() {
    return !radioButton.isEnabled();
  }

  public void setDisabled( boolean dis ) {
    radioButton.setEnabled( !dis );
  }

  public void setCommand( final String method ) {
    radioButton.addChangeListener( new ChangeListener() {
      public void stateChanged( ChangeEvent evt ) {
        invoke( method );
      }
    } );
  }

  public String getValue() {
    return value;
  }

  public void setValue( String aValue ) {
    String previousVal = this.value;
    this.value = aValue;
    this.changeSupport.firePropertyChange( "value", previousVal, aValue );
  }

  public String getGroup() {
    return group;
  }

  public void setGroup( String group ) {
    this.group = group;
  }

  @Override
  public void onDomReady() {
    if ( this.group != null && getDocument() != null && getDocument().getRootElement() instanceof SwingRoot ) {
      ButtonGroup buttonGroup = ( (SwingRoot) getDocument().getRootElement() ).getButtonGroup( group );
      AbstractButton button = (AbstractButton) getManagedObject();
      buttonGroup.add( button );
      if ( buttonGroup.getButtonCount() == 1 ) {
        // first button in, TODO: remove once selected="true" attribute supported
        button.setSelected( true );
      }
    }
  }

}
