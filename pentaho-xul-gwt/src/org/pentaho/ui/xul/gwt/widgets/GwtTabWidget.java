package org.pentaho.ui.xul.gwt.widgets;

import org.pentaho.gwt.widgets.client.utils.ElementUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtTabWidget extends HorizontalPanel {

  private static final int TAB_TEXT_LENGTH = 12;

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
    panel.setStyleName("tabWidget"); //$NON-NLS-1$
    leftCap.setStyleName("tabWidgetCap"); //$NON-NLS-1$
    Image leftCapImage = new Image(GWT.getModuleBaseURL() + "/image/tableft.gif");
    leftCap.setSpacing(0);
    leftCapImage.setWidth("5px"); //$NON-NLS-1$
    leftCap.add(leftCapImage);

    setLabelText(text);
    setLabelTooltip(tooltip);
    textLabel.setWordWrap(false);

    tabPanel.addTabListener(new TabListener() {

      public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
        return true;
      }

      public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
        ElementUtils.blur(getElement().getParentElement());
        if (tabIndex == tabPanel.getWidgetIndex(tabContent)) {
          panel.setStyleName("tabWidget-selected"); //$NON-NLS-1$
          leftCap.setStyleName("tabWidgetCap-selected"); //$NON-NLS-1$
        } else {
          panel.setStyleName("tabWidget"); //$NON-NLS-1$
          leftCap.setStyleName("tabWidgetCap"); //$NON-NLS-1$
        }
      }

    });

    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.add(textLabel);
    DOM.setStyleAttribute(textLabel.getElement(), "margin", "4px 5px 5px 5px"); //$NON-NLS-1$ //$NON-NLS-2$
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

}
