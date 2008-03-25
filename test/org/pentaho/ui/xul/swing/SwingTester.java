package org.pentaho.ui.xul.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;

public class SwingTester {

  public static void main(String[] args){
    try{
      
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/buttonTester.xul");

      if(in == null){
        System.out.println("Input is null");
        System.exit(123);
      }
      
      Document doc = CleanXmlHelper.getDocFromStream(in);
      
      XulDomContainer container = new SwingXulLoader().loadXul(doc);

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

  