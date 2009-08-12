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
  private static int dialogPos = 1100;
  
  public GenericDialog(String tagName){
    super(tagName);
    
    glasspane.setStyleName("glasspane");
    Style glassPaneStyle = glasspane.getElement().getStyle();
    glassPaneStyle.setProperty("width", "100%");
    glassPaneStyle.setProperty("height", "100%");
    glassPaneStyle.setProperty("display", "block");
    
  }
  
  private void createDialog(){
    dialog = new DialogBox(){
      @Override
      public void hide() {
        // User may press the "ESC" key, invoking this code
        super.hide();
        RootPanel.get().remove(glasspane);
        GlassPane.getInstance().hide();
      }
    };
    dialog.add(contents);
    
  }
  
  public void hide(){
    dialog.hide();
  }
  
  public void show(){
    // Instantiation if delayed to prevent errors with the underlying GWT's not being able to calculate available size
    // in the case that the GWT app has been loaded into an iframe that's not visible.
    if(dialog == null){
      createDialog();
    }
    dialog.setText(title);

    contents.clear();

    // implement the buttons
    VerticalPanel panel = new VerticalPanel();
    

    Panel p = getDialogContents();
    p.setSize("100%", "100%");

    p.setStyleName("dialog-content");//$NON-NLS-1$    

    SimplePanel sp = new SimplePanel();
    //sp.getElement().getStyle().setProperty("padding", "5px");
    sp.getElement().setClassName("gwt-Dialog");
    sp.add(p);
    panel.add(sp);
    panel.setCellWidth(sp, "100%");//$NON-NLS-1$
    panel.setCellHeight(sp, "100%");//$NON-NLS-1$
    panel.setStyleName("dialog");//$NON-NLS-1$
    panel.setWidth("100%");   //$NON-NLS-1$
    panel.setHeight("100%");  //$NON-NLS-1$
    contents.add(panel);
//    contents.setCellHeight(panel, "100%");
    
    if(getBgcolor() != null) {
      p.getElement().getStyle().setProperty("backgroundColor", getBgcolor());
    }
    
    
    p = this.getButtonPanel();
    HorizontalPanel buttonPanelWrapper = new HorizontalPanel();
    buttonPanelWrapper.setStyleName("dialog-button-panel");//$NON-NLS-1$
    buttonPanelWrapper.add(p);
    buttonPanelWrapper.setWidth("100%");//$NON-NLS-1$
    buttonPanelWrapper.setCellWidth(p, "100%");
    contents.add(buttonPanelWrapper);
    
    contents.setSpacing(1);

    contents.setWidth("100%");//$NON-NLS-1$
    contents.setHeight("100%");//$NON-NLS-1$
    

    dialog.setWidth(getWidth()+"px");//$NON-NLS-1$
    dialog.setHeight(getHeight()+"px");//$NON-NLS-1$
    dialog.center();
    dialog.show();

    // Show glasspane element
    RootPanel.get().add(glasspane);

    // Notify GlassPane listeners
    GlassPane.getInstance().show();
    
    glasspane.getElement().getStyle().setProperty("zIndex",  ""+(GenericDialog.dialogPos));//$NON-NLS-1$
    dialog.getElement().getStyle().setProperty("zIndex",  ""+(++GenericDialog.dialogPos));//$NON-NLS-1$
    
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
