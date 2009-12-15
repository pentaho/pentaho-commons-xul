package org.pentaho.ui.xul;

import java.util.List;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.XulEventHandler;

public interface XulPerspective {
  List<XulOverlay> getOverlays();
  List<XulEventHandler> getEventHandlers();
  String getID();
  String getDisplayName();
  void onLoad(Document doc);
  void onUnload();
  
}
