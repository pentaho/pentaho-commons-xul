package org.pentaho.ui.xul.gwt.tags;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtRadioGroup extends GwtVbox implements XulRadioGroup, PropertyChangeListener {

  private static final String NAME = "radiogroup"; //$NON-NLS-1$
  
  private List<GwtRadio> radios;
  private String value;
  
  
  public GwtRadioGroup() {
    super(NAME); 
    this.radios = new ArrayList<GwtRadio>();
    GwtRadio.currentGroup = this;
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer aContainer) {
    super.init(srcEle, aContainer);
    GwtRadio.currentGroup = this;
  }

  public void registerRadio(GwtRadio aRadio) {
    aRadio.addPropertyChangeListener("checked", this); //$NON-NLS-1$
    this.radios.add(aRadio);
  }

  @Bindable
  public String getValue() {
    return value;
  }

  @Bindable
  public void setValue(String value) {
    String prev = this.value;
    this.value = value;
    if (prev == null || !prev.equals(value)) {      
      for (GwtRadio radio : this.radios) {
        if (radio.getValue().equals(value)) {
          radio.setChecked(true);
        } else {
          radio.setChecked(false);
        }
      }
      firePropertyChange("value", prev, value); //$NON-NLS-1$
    }
  }

  
  public static void register() {
    GwtXulParser.registerHandler(NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadioGroup();
      }
    });
  }

  public void replaceChild(Element oldElement, Element newElement) {
    resetContainer();
    super.replaceChild(oldElement, newElement);
  }

  public void propertyChange(PropertyChangeEvent evt) {
    Boolean checked = (Boolean)evt.getNewValue();
    // only care about the radio becoming selected, not the one being unselected
    if (checked) {
      XulRadio radio = (XulRadio)evt.getSource();
      // let listeners know that the selected radio has changed
      setValue(radio.getValue());
    }
  }
  
}
