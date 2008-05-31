package org.pentaho.ui.xul.swt;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulRunner;

public class SwtHarness {

  public static void main(String[] args){
    try{
      
      InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream("resource/documents/sampleXul.xul");

      if(in == null){
        System.out.println("Input is null");
        System.exit(123);
      }

      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read(in);
      
      XulDomContainer container = new SwtXulLoader().loadXul(doc);

      XulRunner runner = new SwtXulRunner();
      runner.addContainer(container);
      
      runner.initialize();
      runner.start();
      
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
    }
  }
  
}

  