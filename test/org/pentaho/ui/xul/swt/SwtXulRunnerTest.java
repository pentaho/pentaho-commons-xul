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
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwtXulRunnerTest {

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
    try{
      runner.stop();
    } catch(Exception e){}
  }

  @Test
  public final void testGetXulDomContainers() {
    assertNotNull("Runner's dom container collection is empty.", runner.getXulDomContainers());
    
  }
  @Test
  public final void testInitialize() {
    try{
      runner.initialize();
    } catch(XulException e){
      fail("XulException: "+e.getMessage());
    }
  }

  @Test
//  public final void testStart() {
//    try{
//      runner.initialize();
//      runner.start();
//      runner.stop();
//    } catch(XulException e){
//      fail("XulException: "+e.getMessage());
//    }
//  }
//
//  public final void testStop() {
//    try{
//      runner.initialize();
//      runner.start();
//      runner.stop();
//    } catch(XulException e){
//      fail("XulException: "+e.getMessage());
//    }
//  }

  public final void testNewLoaderInstance() {
    try{
      XulDomContainer cont = (XulDomContainer) runner.getXulDomContainers().get(0);
      assertNotNull(cont.getXulLoader().getNewInstance());
    } catch(XulException e){
      fail("XulException: "+e.getMessage());
    }
  }

}
