package org.pentaho.ui.xul.swt;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class SwtXulLoader implements XulLoader {

  public XulRunner loadXul(Document xulDocument) throws IllegalArgumentException, XulException {

    SwtXulRunner runner = new SwtXulRunner();
    XulParser parser = new XulParser(runner);
    parser.parseDocument(xulDocument.getRootElement());
    return runner;
  }

}
