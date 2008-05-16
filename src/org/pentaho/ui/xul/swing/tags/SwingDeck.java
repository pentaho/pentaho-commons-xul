package org.pentaho.ui.xul.swing.tags;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingDeck extends SwingElement implements XulDeck {

  protected JPanel container;
  private CardLayout cardLayout;
  private int selectedChildIndex = 0; 
  private int numChildren = 0;
  private List<XulComponent> children = new ArrayList<XulComponent>();

  public SwingDeck(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
  }

  public SwingDeck(XulComponent parent, String tagName, Orient orient) {
    super(tagName);
    cardLayout = new CardLayout();
    container = new JPanel(cardLayout);
    setSelectedIndex(0);
    managedObject = container;
  }
  
  
  
  @Override
  public void addChild(Element ele) {
     super.addChild(ele);
     this.container.add((Component) ((XulComponent)ele).getManagedObject(), ""+numChildren); 
     numChildren++;
 
  }

  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex(int index) {
    cardLayout.show(container, ""+index);
  }

  public void layout() {
      
  }

}
