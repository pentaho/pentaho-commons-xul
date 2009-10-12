package org.pentaho.ui.xul.swing.tags;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSeparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingSeparator extends SwingElement implements XulSeparator{
  
  public SwingSeparator(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("separator");
    
    Orient parentOrientation = ((XulContainer) parent).getOrientation();
    JSeparator j = new JSeparator( 
        (parentOrientation == Orient.VERTICAL)
        ? SwingConstants.HORIZONTAL 
        : SwingConstants.VERTICAL
    );
    setManagedObject(j);
  }
}

  