package org.pentaho.ui.xul.test;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ResourceBundle;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swing.SwingXulLoader;

import static org.junit.Assert.*;

public class XulLoaderTest {
  
  @Test
  public void testLoadXulFromInputStream() throws Exception{
    
    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream("resource/documents/includeTest.xul");
    Document doc = rdr.read(in);
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir("resource/documents");
    XulDomContainer container = loader.loadXul(doc);
    XulComponent testComponent = container.getDocumentRoot().getElementById("general-settings-box");
    assertNotNull(testComponent);
  }
  
  @Test
  public void testLoadXulFromString() throws Exception{
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul("resource/documents/includeTest.xul");
    XulComponent testComponent = container.getDocumentRoot().getElementById("general-settings-box");
    assertNotNull(testComponent);
  }
  
  @Test
  public void testLoadXulFragFromInputStream() throws Exception{
    
    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream("resource/documents/includeTest.xul");
    Document doc = rdr.read(in);
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir("resource/documents");
    XulDomContainer container = loader.loadXulFragment(doc);
    XulComponent testComponent = container.getDocumentRoot().getElementById("general-settings-box");
    assertNotNull(testComponent);
  }
  
  @Test
  public void testLoadXulFragFromFromString() throws Exception{
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXulFragment("resource/documents/includeTest.xul");
    XulComponent testComponent = container.getDocumentRoot().getElementById("general-settings-box");
    assertNotNull(testComponent);
  }
  
  
  @Test
  public void testLoadXulFromStringWithBundle() throws Exception{
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle("resource/documents/internationalization");
    XulDomContainer container = loader.loadXul("resource/documents/internationalization.xul", bundle);
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById("welcomeLabel");
    assertEquals("Welcome & Hello >< !!", welcome.getValue());
  }
  
  @Test
  public void testLoadXulFragFromFromStringWithBundle() throws Exception{
    XulLoader loader = new SwingXulLoader();
    ResourceBundle bundle = ResourceBundle.getBundle("resource/documents/internationalization");
    XulDomContainer container = loader.loadXulFragment("resource/documents/internationalization.xul", bundle);
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById("welcomeLabel");
    assertEquals("Welcome & Hello >< !!", welcome.getValue());
  }
  
  @Test
  public void testProcessOverlay() throws Exception{
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul("resource/documents/overlayTestProgramatically.xul");
    loader.processOverlay("overlay.xul", container.getDocumentRoot(), container);
    
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById("newLabel");
    assertNotNull(welcome);
  }
  
  @Test
  public void testRemoveOverlay() throws Exception{
    XulLoader loader = new SwingXulLoader();
    XulDomContainer container = loader.loadXul("resource/documents/overlayTestProgramatically.xul");
    loader.processOverlay("overlay.xul", container.getDocumentRoot(), container);
    
    XulLabel welcome = (XulLabel) container.getDocumentRoot().getElementById("newLabel");
    assertNotNull(welcome);
    
    loader.removeOverlay("overlay.xul", container.getDocumentRoot(), container);
    welcome = (XulLabel) container.getDocumentRoot().getElementById("newLabel");
    assertNull(welcome);
    
  }
  
  @Test
  public void testIncludeInternationalization() throws Exception{
    
    SAXReader rdr = new SAXReader();
    InputStream in = getClass().getClassLoader().getResourceAsStream("resource/documents/includeTest.xul");
    Document doc = rdr.read(in);
    XulLoader loader = new SwingXulLoader();
    loader.setRootDir("resource/documents");
    XulDomContainer container = loader.loadXul(doc);
    XulLabel testComponent = (XulLabel) container.getDocumentRoot().getElementById("nicknameLabel");
    assertEquals("Nickname", testComponent.getValue());
  }
  
}

  