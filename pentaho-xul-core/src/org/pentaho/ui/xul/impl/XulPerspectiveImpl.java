package org.pentaho.ui.xul.impl;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.XulPerspective;
import org.pentaho.ui.xul.dom.Document;

public class XulPerspectiveImpl implements XulPerspective{

  private List<XulOverlay> overlays = new ArrayList<XulOverlay>();

  private List<XulEventHandler> eventHandlers = new ArrayList<XulEventHandler>();
  private String name, id;
  
  public String getDisplayName() {
    return name;
  }
  
  public void setDisplayName(String name){
    this.name = name;
  }

  public List<XulEventHandler> getEventHandlers() {
    return eventHandlers;
  }

  public String getID() {
    return id;
  }
  
  public void setID(String id){
    this.id = id;
  }

  public List<XulOverlay> getOverlays() {
    return overlays;
  }

  public void addOverlay(XulOverlay overlay){
    this.overlays.add(overlay);
  }
  
  public void addEventHandler(XulEventHandler handler){
    this.eventHandlers.add(handler);
  }

  public void onLoad(Document doc) {
    
  }

  public void onUnload() {
    
  }
  
}
