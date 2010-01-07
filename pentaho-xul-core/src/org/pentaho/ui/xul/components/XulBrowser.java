package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulBrowser extends XulComponent {
  void setSrc(String src);
  String getSrc();
  String getData();
  void execute(String data);
  void forward();
  void back();
  void home();
  void reload();
  void stop();
  void setShowtoolbar(boolean flag);
  boolean getShowtoolbar();
}
