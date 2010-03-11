package org.pentaho.ui.xul.test.swt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.WaitBoxRunnable;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.components.XulWaitBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;
import org.pentaho.ui.xul.util.XulDialogCallback;

public class SwtWaitDialogTest {
  XulRunner runner = null;
  Document doc = null;
  XulDomContainer container;
  Document document;
  
  @Before
  public void setUp() throws Exception {
    
    //load anything... doesn't matter for this test.
    container = new SwtXulLoader().loadXul("resource/documents/buttonTester.xul");
    document = container.getDocumentRoot();
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
    XulWaitBox box;
    try {
      box = (XulWaitBox) document.createElement("waitbox");
      box.setIndeterminate(true);
      box.setMaximum(10);
      box.setCanCancel(true);
      box.setRunnable(new WaitBoxRunnable(box){
        boolean canceled = false;
        @Override
        public void run() {
          int steps = 0;
          while(steps <=10){
            if(canceled){
              break;
            }
            //this.waitBox.setValue(steps);
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              waitBox.stop();
            }
            steps++;
          }
          waitBox.stop();
        }

        @Override
        public void cancel() {
          canceled =true;
        }
        
        
      });
      //box.start();
    } catch (XulException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

  }
  
}
