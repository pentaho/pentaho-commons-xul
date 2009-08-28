package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.ColumnType;

public class GwtTreeCol extends AbstractGwtXulComponent implements XulTreeCol {

  private String binding;
  private String columnTypeBinding;
  private String disabledBinding;
  private boolean editable;
  private ColumnType type = ColumnType.TEXT;
  private String colType;


  public static void register() {
    GwtXulParser.registerHandler("treecol", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCol();
      }
    });
  }
  
  public GwtTreeCol() {
    super("treecol");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setBinding(srcEle.getAttribute("pen:binding"));
    setChildrenbinding(srcEle.getAttribute("pen:childrenbinding"));
    setType(srcEle.getAttribute("type"));
    setCombobinding(srcEle.getAttribute("pen:combobinding"));
    setColumntypebinding(srcEle.getAttribute("pen:columntypebinding"));
    setDisabledbinding(srcEle.getAttribute("pen:disabledbinding"));
    setEditable("true".equalsIgnoreCase(srcEle.getAttribute("editable"))); //$NON-NLS-1$ //$NON-NLS-2$
  }
  
  public void autoSize() {
    // TODO Auto-generated method stub
    
  }

  public String getBinding() {
    return binding;
  }

  public String getCustomeditor() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getLabel() {
    return getAttributeValue("label");
  }

  public String getSortDirection() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getType() {
    return colType;
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isFixed() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHidden() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isPrimary() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isSortActive() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  public void setCustomeditor(String customClass) {
    // TODO Auto-generated method stub
    
  }

  public void setEditable(boolean edit) {
    this.editable = edit;
  }

  public void setFixed(boolean fixed) {
    // TODO Auto-generated method stub
    
  }

  public void setHidden(boolean hide) {
    // TODO Auto-generated method stub
    
  }

  public void setLabel(String label) {
    setAttribute("label", label);
  }

  public void setPrimary(boolean primo) {
    // TODO Auto-generated method stub
    
  }

  public void setSortActive(boolean sort) {
    // TODO Auto-generated method stub
    
  }

  public void setSortDirection(String dir) {
    // TODO Auto-generated method stub
    
  }

  public void setSrc(String srcUrl) {
    // TODO Auto-generated method stub
    
  }

  public void setType(String type) {
    this.colType = type;
    try{
      this.type = ColumnType.valueOf(type.toUpperCase());
    } catch(Exception e){
      this.type = ColumnType.CUSTOM;
    }
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

  public List<InlineBindingExpression> getBindingExpressions() {
    List<InlineBindingExpression> exps = new ArrayList<InlineBindingExpression>();
    if(binding == null){
      return exps;
    }
    for (String bindingExpText : binding.split(",")) {
      exps.add(new InlineBindingExpression(bindingExpText.trim()));
    }
    return exps;
  }

  public ColumnType getColumnType() {
    return type;
  }

  private String childrenBinding, comboBinding;
  public String getChildrenbinding() {
    return childrenBinding;
  }

  public String getCombobinding() {
    return comboBinding;  
  }

  public void setChildrenbinding(String childProperty) {
    this.childrenBinding = childProperty;  
  }

  public void setCombobinding(String property) {
    this.comboBinding = property;
  }
  

  public void setColumntypebinding(String propertyName){
    this.columnTypeBinding = propertyName;
  }

  public String getColumntypebinding(){
    return this.columnTypeBinding;
  }

  public String getDisabledbinding() {
    return disabledBinding;
  }

  public void setDisabledbinding(String property) {
    this.disabledBinding = property;
  }
}
