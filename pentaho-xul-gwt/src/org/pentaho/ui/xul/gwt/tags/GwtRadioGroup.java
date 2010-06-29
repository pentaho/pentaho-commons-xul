package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtRadioGroup extends GwtVbox implements XulRadioGroup {

  private static final String NAME = "radiogroup"; //$NON-NLS-1$
  
  private List<GwtRadio> radios;

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
    this.radios.add(aRadio);
  }

  public String getValue() {
    String value = null;
    for (GwtRadio radio : this.radios) {
      if (radio.isSelected()) {
        value = radio.getValue();
        break;
      }
    }
    return value;
  }

  public static void register() {
    GwtXulParser.registerHandler(NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadioGroup();
      }
    });
  }

  public void fireValueChanged() {
  }

  @Override
  public void replaceChild(Element oldElement, Element newElement) {
    resetContainer();
    super.replaceChild(oldElement, newElement);
  }

}
