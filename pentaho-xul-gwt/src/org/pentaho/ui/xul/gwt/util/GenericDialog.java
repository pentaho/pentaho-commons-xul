package org.pentaho.ui.xul.gwt.util;

import org.pentaho.gwt.widgets.client.dialogs.GlassPane;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.tags.GwtDialog;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class GenericDialog extends AbstractGwtXulContainer{

  private SimplePanel glasspane = new SimplePanel();
  protected DialogBox dialog;
  private VerticalPanel contents = new VerticalPanel();
  private String title = "";

  public static final int CANCEL = 0;
  public static final int ACCEPT = 1;
  public static final int EXTRA1 = 2;
  public static final int EXTRA2 = 3;
  
  public GenericDialog(String tagName){
    super(tagName);
    
    glasspane.setStyleName("glasspane");
    Style glassPaneStyle = glasspane.getElement().getStyle();
    glassPaneStyle.setProperty("width", "100%");
    glassPaneStyle.setProperty("height", "100%");
    glassPaneStyle.setProperty("display", "block");
    
    
    dialog = new DialogBox(){
      @Override
      public void hide() {
        // User may press the "ESC" key, invoking this code
        super.hide();
        RootPanel.get().remove(glasspane);
        GlassPane.getInstance().hide();
      }
    };
    
  }
  
  public void hide(){
    dialog.hide();
  }
  
  public void show(){
    dialog.setText(title);

    contents.clear();
    Panel p = getDialogContents();
    p.setSize("100%", "100%");
    contents.add(p);
    contents.setCellHeight(p, "100%");

    p = getButtonPanel();
    p.setWidth("100%");
    

    HorizontalPanel buttonPanelWrapper = new HorizontalPanel();
    buttonPanelWrapper.setStyleName("dialog-button-panel");//$NON-NLS-1$
    buttonPanelWrapper.add(p);
    buttonPanelWrapper.setWidth("100%");//$NON-NLS-1$
    buttonPanelWrapper.setCellWidth(p, "100%");
    contents.add(buttonPanelWrapper);
    
    contents.setSpacing(3);
    contents.setSize("100%", "100%");

    
    dialog.setWidget(contents);
    
    dialog.setWidth(getWidth()+"px");
    dialog.setHeight(getHeight()+"px");
    dialog.center();
    dialog.show();

    // Show glasspane element
    RootPanel.get().add(glasspane);

    // Notify GlassPane listeners
    GlassPane.getInstance().show();
    
    glasspane.getElement().getStyle().setProperty("zIndex",  ""+(GwtDialog.dialogPos));//$NON-NLS-1$
    dialog.getElement().getStyle().setProperty("zIndex",  ""+(++GwtDialog.dialogPos));//$NON-NLS-1$
    
  }
  
  public Panel getDialogContents(){
    return null;
  }
  
  public Panel getButtonPanel(){
    return null;
  }
  
  public void setTitle(final String title) {
    this.title = title;
  }
  
  public boolean isHidden(){
    return dialog == null || !dialog.isVisible();
  }
  
}
