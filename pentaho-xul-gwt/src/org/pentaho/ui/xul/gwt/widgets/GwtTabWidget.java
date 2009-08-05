package org.pentaho.ui.xul.gwt.widgets;

import org.pentaho.gwt.widgets.client.utils.ElementUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtTabWidget extends HorizontalPanel implements MouseListener {
  private TabPanel tabPanel;
  private Widget tabContent;
  private Label textLabel = new Label();
  private HorizontalPanel panel = new HorizontalPanel();
  private HorizontalPanel leftCap = new HorizontalPanel();
  private String fullText;

  public GwtTabWidget(String text, String tooltip, final TabPanel tabPanel, final Widget tabContent) {
    this.tabPanel = tabPanel;
    this.tabContent = tabContent;
    this.fullText = text;
    setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.setStyleName("xulTabWidget"); //$NON-NLS-1$
    leftCap.setStyleName("xulTabWidgetCap"); //$NON-NLS-1$
    setLabelText(text);
    setLabelTooltip(tooltip);
    textLabel.setStyleName("xulTabWidgetLabel");
    textLabel.setWordWrap(false);
    textLabel.addMouseListener(this);
    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {

      public void onSelection(SelectionEvent<Integer> event) {
        int tabIndex = event.getSelectedItem();
        ElementUtils.blur(getElement().getParentElement());
        if (tabIndex == tabPanel.getWidgetIndex(tabContent)) {
          panel.setStyleName("xulTabWidget-selected"); //$NON-NLS-1$
          leftCap.setStyleName("xulTabWidgetCap-selected"); //$NON-NLS-1$
        } else {
          panel.setStyleName("xulTabWidget"); //$NON-NLS-1$
          leftCap.setStyleName("xulTabWidgetCap"); //$NON-NLS-1$
        }
      }
    });

    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.add(textLabel);
//    DOM.setStyleAttribute(textLabel.getElement(), "margin", "4px 5px 5px 5px"); //$NON-NLS-1$ //$NON-NLS-2$
    DOM.setStyleAttribute(textLabel.getElement(), "paddingRight", "5px"); //$NON-NLS-1$ //$NON-NLS-2$
    add(leftCap);
    add(panel);
    sinkEvents(Event.ONDBLCLICK | Event.ONMOUSEUP);
  }

  public String getText() {
    return fullText;
  }

  public void setLabelText(String text) {
    String trimmedText = text.substring(0, Math.min(18, text.length()));
    if (!trimmedText.equals(text)) {
      trimmedText += ".."; //$NON-NLS-1$
    }
    textLabel.setText(trimmedText);
  }

  public void setLabelTooltip(String tooltip) {
    textLabel.setTitle(tooltip);
  }

  public void onMouseDown(Widget sender, int x, int y) {
  }
  
  public void onMouseEnter(Widget sender) {
    if (tabPanel.getTabBar().getSelectedTab() == tabPanel.getWidgetIndex(tabContent)) {
        // don't do anything
    } else {
      panel.setStyleName("xulTabWidget-hover"); //$NON-NLS-1$
      leftCap.setStyleName("xulTabWidgetCap-hover"); //$NON-NLS-1$
    }
  }

  public void onMouseLeave(Widget sender) {
   if (tabPanel.getTabBar().getSelectedTab() == tabPanel.getWidgetIndex(tabContent)) {
        // don't do anything
   } else {
      panel.setStyleName("xulTabWidget"); //$NON-NLS-1$
      leftCap.setStyleName("xulTabWidgetCap"); //$NON-NLS-1$
   }
  }

  public void onMouseMove(Widget sender, int x, int y) {
  }

  public void onMouseUp(Widget sender, int x, int y) {
  }


}
