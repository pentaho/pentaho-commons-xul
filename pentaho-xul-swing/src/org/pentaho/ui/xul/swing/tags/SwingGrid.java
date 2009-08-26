package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwingGrid extends AbstractSwingContainer implements XulGrid {
  private JPanel grid = new JPanel();
  private static final Log logger = LogFactory.getLog(SwingGrid.class);

  public SwingGrid(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("grid");

    grid.setLayout(new GridBagLayout());
    grid.setOpaque(false);
		this.managedObject = grid;
    resetContainer();
	}

  @Override
  public void resetContainer() {
    grid.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = 0;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = 1;
    gc.insets = new Insets(2, 2, 2, 2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;
  }

  @Override
  public void layout() {

    if(this.getChildNodes().size() < 2){
      logger.warn("Grid does not contain Column and Row children");
      return;
    }

    XulComponent columns = this.getChildNodes().get(0);
    XulComponent rows = this.getChildNodes().get(1);

    int colCount = 0;
    int colFlexTotal = 0;
    boolean columnFlexLayout = false;
    for(XulComponent col : columns.getChildNodes()){
      if(col.getFlex() > 0){
        columnFlexLayout = true;
        colFlexTotal += col.getFlex();
      }
      colCount++;
    }


    for(XulComponent row : rows.getChildNodes()){
      gc.gridx = 0;

      for(XulComponent xulComp : row.getChildNodes()){
        Component comp =  (Component) xulComp.getManagedObject();
        int flex = columns.getChildNodes().get(gc.gridx).getFlex();
        if (colFlexTotal > 0) {
          gc.weightx = (flex / colFlexTotal);
        }
        grid.add(comp, gc);
        gc.gridx++;
      }

      gc.gridy++;
    }   
    
    // Add in an extra row at the bottom to push others up
    gc.gridy++;
    gc.weighty = 1;
    gc.fill = gc.REMAINDER;
    grid.add(Box.createGlue(), gc);
    
    this.initialized = true;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulGrid#update()
   */
  public void update() {
    resetContainer();
    layout();
  }
}
