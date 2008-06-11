package org.pentaho.ui.xul.test.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwingHarness {

  public static void main(String[] args){
    try{
      
      XulDomContainer container = new SwingXulLoader().loadXul("resource/documents/bindings.xul");

      XulRunner runner = new SwingXulRunner();
      runner.addContainer(container);
      
      runner.initialize();
      runner.start();
      
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }
  
}

  