package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.impl.AbstractXulEventHandler

class TestHandler extends AbstractXulEventHandler {

    def sayHello() {
        document.getElementById("label").setValue("Hi There")
    }

}