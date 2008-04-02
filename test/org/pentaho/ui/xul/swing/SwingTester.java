package org.pentaho.ui.xul.swing;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
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

      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read(in);
      
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

  