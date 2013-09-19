package org.pentaho.ui.xul.test.swt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class SwtPromptTest {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  
  @Before
  public void setUp() throws Exception {
    
    //load anything... doesn't matter for this test.
    container = new SwtXulLoader().loadXul("resource/documents/treeTest.xul");

    runner = new SwtXulRunner();
    runner.addContainer(container);
    Document document = container.getDocumentRoot();


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
  public void promptTest()throws XulException{

    XulPromptBox prompt = (XulPromptBox) container.getDocumentRoot().createElement("promptbox");
    prompt.setTitle("Test Title");
    prompt.setButtons(new String[]{"Accept", "Cancel"});
    prompt.setMessage("Enter your data here:");
    prompt.setValue("my default value");
    prompt.addDialogCallback(new XulDialogCallback<String>(){
      public void onClose(XulComponent component, Status status, String value) {
          System.out.println("Component: " + component.getName());
          System.out.println("Status: " + status.name());
          System.out.println("Value: " + value);
      }
      
      public void onError(XulComponent component, Throwable err) {
        System.out.println(err.getMessage());
      }      
    });
    
    //prompt.open();

  }
  
}
