package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulBrowser;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtBrowser  extends SwtElement implements XulBrowser {

  private Browser browser;
  private Composite mainPanel;
  private boolean showToolbar = true;
  private Text location;
  private ToolBar toolbar;
  private String src = "about:blank";
  private String home;
  private Composite toolbarPanel;

  public SwtBrowser(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    
    Composite parentComposite = (Composite) parent.getManagedObject();
    
    mainPanel = new Composite(parentComposite, SWT.BORDER);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    
    mainPanel.setLayout(layout);
    

    setManagedObject(mainPanel);
  }

  public void execute(String data) {
    browser.execute(data);
  }

  public String getData() {
    return ""+browser.getData();
  }

  public String getSrc() {
    return browser.getUrl();
  }

  public void setSrc(String src) {
    if(browser != null){
      browser.setUrl(src);
      location.setText(src);
    }
    this.src = src;
    if(home == null){
      home = src;
    }
  }

  public void back() {
    browser.back();
  }

  public void forward() {
    browser.forward(); 
  }

  public void home() {
    browser.setUrl(home);
  }
  
  public void reload(){
    browser.refresh();
  }
  
  public void stop(){
    browser.stop();
  }

  @Override
  public void layout() {

    toolbarPanel = new Composite(mainPanel, SWT.BORDER);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    toolbarPanel.setLayoutData(data);

    GridLayout layout = new GridLayout(3, false);
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    toolbarPanel.setLayout(layout);
    
    toolbar = new ToolBar(toolbarPanel, SWT.FLAT | SWT.HORIZONTAL);
    ToolItem item = new ToolItem(toolbar, SWT.PUSH);
    item.setText("<");
    item.addSelectionListener(new SelectionListener(){
      public void widgetDefaultSelected(SelectionEvent se) {}
      public void widgetSelected(SelectionEvent se) {
        back();
      }
    });
    data = new GridData();
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = false;
    toolbar.setLayoutData(data);
    
    item = new ToolItem(toolbar, SWT.PUSH);
    item.setText(">");
    item.addSelectionListener(new SelectionListener(){
      public void widgetDefaultSelected(SelectionEvent se) {}
      public void widgetSelected(SelectionEvent se) {
        forward();
      }
    });

    item = new ToolItem(toolbar, SWT.PUSH);
    item.setText("R");
    item.addSelectionListener(new SelectionListener(){
      public void widgetDefaultSelected(SelectionEvent se) {}
      public void widgetSelected(SelectionEvent se) {
        reload();
      }
    });
    
    location = new Text(toolbarPanel, SWT.BORDER);
    location.setText(src);
    location.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent arg0) {
        if(arg0.character == SWT.CR){
          setSrc(location.getText());
        }
      }
    });
    
    data = new GridData();
    data.horizontalSpan = 2;
    data.grabExcessHorizontalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    location.setLayoutData(data);
    
    
    browser = createBrowser(mainPanel);
    browser.setUrl(src);
    
    data = new GridData();
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    data.verticalAlignment = SWT.FILL;
    data.horizontalAlignment = SWT.FILL;
    browser.setLayoutData(data);
    
    browser.addLocationListener(new LocationListener(){
      public void changed(LocationEvent arg0) {
        SwtBrowser.this.src = arg0.location;
        location.setText(arg0.location);
      }
      public void changing(LocationEvent arg0) {}
    });
    
    setShowtoolbar(getShowtoolbar());
    mainPanel.layout(true);
  }
  
  protected Browser createBrowser(Composite parent){
    return new Browser(parent, SWT.None);
  }

  public boolean getShowtoolbar() {
    return showToolbar;
  }

  public void setShowtoolbar(boolean flag) {
    this.showToolbar = flag;
    if(toolbarPanel != null){
      toolbarPanel.setVisible(flag);
      ((GridData) toolbarPanel.getLayoutData()).exclude = !showToolbar;
      mainPanel.layout(true);
    }
  }
  
  public Browser getBrowser(){
    return browser;
  }
}
