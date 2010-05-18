package org.pentaho.ui.xul;

import java.io.IOException;

public interface XulSettingsManager {
  void storeSetting(String prop, String val);
  String getSetting(String prop);
  void save() throws IOException;
}
