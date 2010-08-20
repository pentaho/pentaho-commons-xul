package org.pentaho.ui.xul.gwt.util;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.dialogs.GlassPane;
import org.pentaho.gwt.widgets.client.dialogs.GlassPaneListener;
import org.pentaho.gwt.widgets.client.ui.Draggable;

/**
 * User: nbaker
 * Date: Aug 5, 2010
 */
public class XulDragController extends PickupDragController {

  private Widget proxy;
  private static XulDragController instance;

  public Widget getProxy() {
    return proxy;
  }

  public void setProxy(Widget proxy) {
    this.proxy = proxy;
  }

  private XulDragController(AbsolutePanel panel) {
    super(panel, false);
    setBehaviorDragProxy(true);
    setBehaviorDragStartSensitivity(5);
  }

  public static XulDragController getInstance(){
    if(instance == null){
      final AbsolutePanel panel = new AbsolutePanel();
      panel.setHeight("100%");
      panel.setWidth("100%");
      panel.getElement().getStyle().setProperty("position", "absolute");
      panel.getElement().getStyle().setProperty("top", "0px");
      panel.getElement().getStyle().setProperty("left", "0px");
      panel.getElement().getStyle().setProperty("display", "none");
      RootPanel.get().add(panel);
      instance = new XulDragController(panel);
      GlassPane.getInstance().addGlassPaneListener(new GlassPaneListener(){
        public void glassPaneShown() throws Exception {
          panel.setVisible(true);
        }

        public void glassPaneHidden() throws Exception {
          panel.setVisible(false);
        }
      });

    }
    return instance;
  }

  @Override
  protected void restoreSelectedWidgetsStyle() {
  }

  @Override
  protected void saveSelectedWidgetsLocationAndStyle() {
  }

  @Override
  protected void restoreSelectedWidgetsLocation() {
  }

  @Override
  public void dragEnd() {
    proxy.removeFromParent();
    proxy = null;
    super.dragEnd();
  }


  @Override
  protected Widget newDragProxy(DragContext context) {
    proxy = ((Draggable) context.draggable).makeProxy(context.draggable);
    return proxy;
  }


  @Override
  public void previewDragEnd() throws VetoDragException {
    super.previewDragEnd();
    
  }

  public Object getDragObject(){
    return ((Draggable) context.draggable).getDragObject();
  }

}
