package org.pentaho.ui.xul.test.swing;

import java.io.InputStream;

import javax.swing.JCheckBox;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.eclipse.swt.widgets.Button;
import org.junit.After;
import org.junit.Before;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.junit.Test;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

import static org.junit.Assert.*;

public class SwtCheckboxTest2 {

  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  XulCheckbox check;
  XulCheckbox check2;
  
  @Before
  public void setUp() throws Exception {
    
    container = new SwtXulLoader().loadXul("resource/documents/allTags.xul");

    runner = new SwtXulRunner();
    runner.addContainer(container);
    check = (XulCheckbox) container.getDocumentRoot().getElementById("checkbox");
    check2 = (XulCheckbox) container.getDocumentRoot().getElementById("checkbox2");
      
  }

  @After
  public void tearDown() throws Exception {
    try{
      runner.stop();
    } catch(Exception e){}
  }

  @Test
  public final void testLabel() {
    assertEquals("test", check.getLabel());
  }
  
  @Test
  public final void testCommand() {
    assertEquals("foo.test()", check.getCommand());
  }
  
  
  @Test
  public final void testIsChecked() {
    assertTrue(check.isChecked());
  }
  
  @Test
  public final void testSetChecked() {
    check2.setChecked(false);
    assertTrue(!check2.isChecked());
    check2.setChecked(true);
    assertTrue(check2.isChecked());
    
  }

  @Test
  public final void testIsDisabled() {
    assertTrue(!check.isDisabled());
  }

  @Test
  public final void testSetDisabled() {
    check.setDisabled(true);
    assertTrue(check.isDisabled());
    check.setDisabled(false);
    assertTrue(!check.isDisabled());
  }
  
  @Test
  public final void setManagedObject(){
    assertTrue(check.getManagedObject() instanceof Button);
  }
  
}
