package org.pentaho.ui.xul.swt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;

public class XulRunnerTest {

  XulRunner runner = null;
  Document doc = null;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {

    InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/sampleXul.xul");
    assertNotNull("XUL input not found.", in);
    SAXReader rdr = new SAXReader();
    final Document doc = rdr.read(in);
    
    XulDomContainer container = new SwtXulLoader().loadXul(doc);

    runner = new SwtXulRunner();
    runner.addContainer(container);

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testGetDocumentRoot() {
    //assertNotNull("Runner's document is null.", runner.getDocumentRoot());
  }

  @Test
  public final void testGetElementById() {
    fail("Not yet implemented");
  }

  @Test
  public final void testGetElementsById() {
    fail("Not yet implemented");
  }

  @Test
  public final void testInitialize() {
    fail("Not yet implemented");
  }

  @Test
  public final void testRemoteCall() {
    fail("Not yet implemented");
  }

  @Test
  public final void testSetDocumentRoot() {
    fail("Not yet implemented");
  }

  @Test
  public final void testStart() {
    fail("Not yet implemented");
  }

  @Test
  public final void testStop() {
    fail("Not yet implemented");
  }

}
