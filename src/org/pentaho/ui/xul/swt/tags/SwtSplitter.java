package org.pentaho.ui.xul.swt.tags;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Sash;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtSplitter extends SwtElement implements XulSplitter{

  private Sash sash;
  public SwtSplitter(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("splitter");
    int dir = (((XulContainer) parent).getOrientation() == Orient.VERTICAL) ? SWT.HORIZONTAL : SWT.VERTICAL;
    sash = new Sash((Composite)parent.getManagedObject(), SWT.BORDER | dir);
    GridData gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.grabExcessVerticalSpace = true;
    sash.setLayoutData(gd);
    sash.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected(SelectionEvent event) {
        widgetSelected(event);
      }

      public void widgetSelected(SelectionEvent event) {
//        Control[] peers = sash.getParent().getChildren();
//        List<Control> peersList = Arrays.asList(peers);
//        int meIdx = peersList.indexOf(sash);
//        if(meIdx > 0 && meIdx < peersList.size()){
//          Control prev = peersList.get(meIdx);
//          GridData prevLayout = (GridData) prev.getLayoutData();
//          prevLayout.widthHint = prev.getSize().x - (event.x - prev.getSize().x);
//          sash.getParent().layout(); 
//          sash.getParent().redraw();
//        }
      }
    });

    
    this.managedObject = "empty";
  }
} 