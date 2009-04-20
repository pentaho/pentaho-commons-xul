package org.pentaho.ui.xul.swing.tags;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScale;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingScale extends SwingElement implements XulScale{
  

  private int min = 0;
  private int max = 100;
  private int value = 0;
  private int increment = 1;
  
  private String direction;
  
  public SwingScale(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("scale");
  }
  

  @Override
  public String getDir() {
    return direction;
  }

  @Override
  public int getMax() {
    return max;
  }

  @Override
  public int getMin() {
    return min;
  }

  @Override
  public int getPageincrement() {
    return increment;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public void setDir(String direction) {
    this.direction = direction;
  }

  @Override
  public void setMax(int max) {
    this.max = max;
  }

  @Override
  public void setMin(int min) {
    this.min = min;
  }

  @Override
  public void setPageincrement(int increment) {
    this.increment = increment;
  }

  @Override
  public void setValue(int value) {
    int prevVal = this.value;
    this.value = value;
    this.changeSupport.firePropertyChange("value", prevVal, value);
  }


  @Override
  public void layout() {
    int orient = (orientation == Orient.VERTICAL)? JSlider.VERTICAL: JSlider.HORIZONTAL;
    final JSlider slider = new JSlider(orient, this.min, this.max, Math.max(min, this.value));
    this.managedObject = slider;
    
    slider.setMajorTickSpacing(this.increment);
    
    slider.setSnapToTicks(true);
    slider.setPaintTicks(true);
    
    slider.addChangeListener(new ChangeListener(){

      @Override
      public void stateChanged(ChangeEvent arg0) {
        setValue(slider.getValue());
      }
      
    });

  }
  
  

  
}
