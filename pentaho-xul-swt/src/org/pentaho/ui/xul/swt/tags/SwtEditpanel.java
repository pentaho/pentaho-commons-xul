package org.pentaho.ui.xul.swt.tags;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulEditpanel;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtEditpanel extends AbstractSwtXulContainer implements XulEditpanel{

  private ViewForm topForm;
  private CLabel lbl;
  private ToolItem btn;
  private boolean collapsible;
  private Composite body;
  private Composite mainComposite;
  private XulToolbar toolbar;
  private boolean collapsed;
  private Composite parentComposite;
  private int hiddenflex;
  private String cachedLabel;
  
  public SwtEditpanel(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    setOrient(Orient.VERTICAL.toString());
    
    parentComposite = (Composite) parent.getManagedObject();
    mainComposite = new Composite(parentComposite, SWT.BORDER);
    GridData data = new GridData();
    data.verticalAlignment = SWT.FILL;
    data.grabExcessVerticalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
//    mainComposite.setLayoutData(data);
    
    GridLayout layout = new GridLayout();
    mainComposite.setLayout(layout);
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    
    topForm = new ViewForm(mainComposite, SWT.NONE);
    topForm.addPaintListener(new PaintListener(){

      public void paintControl(PaintEvent arg0) {
        GC gc = arg0.gc;
        gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
        gc.drawLine(0, topForm.getBounds().height-1, topForm.getBounds().width, topForm.getBounds().height-1);
      }
      
    });
    body = new Composite(mainComposite, SWT.None);
    
    data = new GridData();
    data.verticalAlignment = SWT.FILL;
    data.grabExcessVerticalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.verticalSpan = 10;
    body.setLayoutData(data);
    
    data = new GridData();
    data.grabExcessVerticalSpace = false;
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    data.verticalSpan = 0;
    topForm.setLayoutData(data);

    setManagedObject(mainComposite);
    setPadding(0);
    setSpacing(0);
    
  }
  protected Composite createNewComposite(Composite parent) {
    return new Composite(parent, SWT.NONE);
  }

  
  public void setCollapsible(boolean collapsible) {
    this.collapsible = collapsible;
  }

  @Override
  public void layout() {
    for(XulComponent comp : getChildNodes()){
      if(comp instanceof XulToolbar){
        toolbar = (XulToolbar) comp;
        ((Control) comp.getManagedObject()).setParent(topForm);
        topForm.setTopCenter((Control) comp.getManagedObject());
        
      } else if(comp instanceof XulCaption){
        if(lbl == null){
          lbl = new CLabel(topForm, SWT.None);
          Color[] inactiveGradient = new Color[]{Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT),
              Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND)
              , null};
          int[] gradPercent = new int[] {25,100};            
          //lbl.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
          lbl.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));

          lbl.setBackground(inactiveGradient, gradPercent);
          lbl.addPaintListener(new PaintListener(){

            public void paintControl(PaintEvent arg0) {
              GC gc = arg0.gc;
              gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
              gc.drawLine(0, topForm.getBounds().height-1, topForm.getBounds().width, topForm.getBounds().height-1);
            }
            
          });
        }
        lbl.setText(((XulCaption) comp).getLabel());
        topForm.setTopLeft(lbl);
        
      } else {
        ((Control) comp.getManagedObject()).setParent(body);
      }
      
    }
    if(isCollapsible() && btn == null){
      final ToolBar bar = new ToolBar(topForm, SWT.FLAT | SWT.HORIZONTAL);
      btn = new ToolItem(bar, SWT.PUSH);
      btn.setText("_");
      btn.addSelectionListener(new SelectionListener(){
        public void widgetDefaultSelected(SelectionEvent se) {}
        public void widgetSelected(SelectionEvent se) {
          collapse();
        }
      });
      bar.addPaintListener(new PaintListener(){

        public void paintControl(PaintEvent arg0) {
          GC gc = arg0.gc;
          gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
          gc.drawLine(0, bar.getBounds().height-1, topForm.getBounds().width, bar.getBounds().height-1);
        }
        
      });
 
      topForm.setTopRight(bar);
    }
    setManagedObject(body);
    super.layout();
    setManagedObject(mainComposite);
  }
  
  public void collapse(){
    body.setVisible(collapsed);
    //((GridData) body.getLayoutData()).exclude = !collapsed;
    if(lbl != null){
      if(! collapsed){
        cachedLabel = lbl.getText();
        lbl.setText(lbl.getText().substring(0,6)+"...");
      } else {
        lbl.setText(cachedLabel);
      }
    }
    if(toolbar != null){  
      toolbar.setVisible(collapsed);
    }
    collapsed = !collapsed;
    if(collapsed){
      hiddenflex = getFlex();
      setFlex(0);
    } else {
      setFlex(hiddenflex);
    }
    ((SwtElement) getParent()).layout();
  }
  
  public boolean isCollapsible() {
    return this.collapsible;
  }
  
  public void setCaption(String caption) {
    if(lbl != null){
      lbl.setText(caption);
    }
  }
  
  
  
}
