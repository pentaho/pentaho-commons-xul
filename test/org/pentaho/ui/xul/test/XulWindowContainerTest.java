package org.pentaho.ui.xul.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.test.samples.CloseHandler;
import org.pentaho.ui.xul.test.swing.ProgressHandler;

public class XulWindowContainerTest {
  XulDomContainer container;
  XulButton button;
  XulRunner runner;
  
  @Before
  public void setUp() throws Exception {
    container = new SwingXulLoader().loadXul("resource/documents/windowContainerTest.xul");
    runner = new SwingXulRunner();
    runner.addContainer(container);
    runner.initialize();
    runner.start();
  }

  @After
  public void tearDown() throws Exception {
    runner.stop();
  }
  
  @Test
  public final void testFrag() throws Exception {
    container.getDocumentRoot().loadFragment("all-box", "resource/documents/fragmenttest.xul");
    assertNotNull(container.getDocumentRoot().getElementById("database-options-box"));
  }

  @Test
  public final void testFrag2() throws Exception {
    XulDomContainer container2 = container.loadFragment("resource/documents/fragmenttest.xul");
    assertNotNull(container2.getDocumentRoot().getElementById("database-options-box"));
  }

  @Test
  public final void testClose() throws Exception {
    CloseHandler handler = (CloseHandler) container.getEventHandler("closeHandler");
    container.close();
    assertTrue(handler.closed1);
    assertTrue(handler.closed2);
    assertTrue(handler.closed3);
  }
  
  
  
}

  