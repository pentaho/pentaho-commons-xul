package org.pentaho.ui.xul.test.swing;

import org.dom4j.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

import static org.junit.Assert.*;

public class SwingComponenetTest {
  XulDomContainer container;
  XulButton button;
  
  @Before
  public void setUp() throws Exception {
    container = new SwingXulLoader().loadXul("resource/documents/componentTest.xul");
    button = (XulButton) container.getDocumentRoot().getElementById("testButton");
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testGetSetOnBlur() {
    assertEquals("foo.bar()", button.getOnblur());
    button.setOnblur("baz.bang()");
    assertEquals("baz.bang()", button.getOnblur());
  }

  @Test
  public final void testGetSetPadding() {
    assertEquals(5, button.getPadding());
    button.setPadding(6);
    assertEquals(6, button.getPadding());
  }

  @Test
  public final void testGetSetBgcolor() {
    assertEquals("#fcfcfc", button.getBgcolor());
    button.setBgcolor("#cccccc");
    assertEquals("#cccccc", button.getBgcolor());
  }
  
  
  
}

  