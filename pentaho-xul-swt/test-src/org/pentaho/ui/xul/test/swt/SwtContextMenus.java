package org.pentaho.ui.xul.test.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.dom4j.Document;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtContextMenus {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulMenupopup popup;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/context_menu.xul");

    runner = new SwtXulRunner();
    runner.addContainer(container);
    popup = (XulMenupopup) container.getDocumentRoot().getElementById("contextMenu");

    // Un-comment the following to test the GUI manually
    //runner.initialize();
    //runner.start();
  }
  
  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }
  
  @Test
  public void testGetSelectedIndex() throws Exception{
    assertTrue(popup != null);
  }
  
  
}
