package org.pentaho.ui.xul.swing.tags;

import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLine;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingLine extends SwingElement implements XulLine{
  
  public SwingLine(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("line");
    
    Orient parentOrientation = ((XulContainer) parent).getOrientation();
    this.managedObject = new JSeparator( 
        (parentOrientation == Orient.VERTICAL)
        ? SwingConstants.HORIZONTAL 
        : SwingConstants.VERTICAL
    );
  }
}

  