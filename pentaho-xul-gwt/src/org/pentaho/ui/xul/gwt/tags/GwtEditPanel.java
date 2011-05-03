package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.*;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulEditpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

/**
 * User: nbaker
 * Date: 5/3/11
 */
public class GwtEditPanel extends AbstractGwtXulContainer implements XulEditpanel {

  static final String ELEMENT_NAME = "editpanel"; //$NON-NLS-1$
  private String type;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME,
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtEditPanel();
          }
        });
  }

  private FlowPanel outterPanel = new FlowPanel();
  private SimplePanel titlePanel = new SimplePanel();
  private Label title = new Label();
  private VerticalPanel contentPanel = new VerticalPanel();
  

  public GwtEditPanel() {
    super(ELEMENT_NAME);
    this.orientation = Orient.VERTICAL;
    setManagedObject(outterPanel);
    outterPanel.setStylePrimaryName("xul-editPanel");
    titlePanel.setStylePrimaryName("xul-editPanel-title");
    contentPanel.setStylePrimaryName("xul-editPanel-content");
    title.setStylePrimaryName("xul-editPanel-title-label");

    contentPanel.setHeight("100%");
    contentPanel.setSpacing(2);
    container = contentPanel;
    container.setWidth("100%");
    contentPanel.addStyleName("vbox");

    outterPanel.add(titlePanel);
    titlePanel.add(title);
    outterPanel.add(contentPanel);
  }


  @Override
  public void addChild(Element element) {
    if(element instanceof XulCaption){
        setCaption(((XulCaption) element).getLabel());
    }
    super.addChild(element);
  }

  public void setCaption(String caption){
    title.setText(caption);
  }

  public void resetContainer(){

    container.clear();
  }

  public String getType() {
    return type;
  }

  public void setType(String s) {
    this.type = s;
  }

  public void open() {
    // not implemented
  }
}
