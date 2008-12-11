package org.pentaho.ui.xul.swt.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SwtMenuList<T> extends AbstractSwtXulContainer implements XulMenuList<T> {

  private Combo combobox;

  private XulDomContainer xulDomContainer;

  private static final Log logger = LogFactory.getLog(SwtMenuList.class);

  private boolean loaded = false;

  private String binding;

  private T previousSelectedItem = null;
  
  private SwtMenupopup popup;
  
  private SwtMenuitem selectedItem = null;

  public SwtMenuList(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menulist");

    this.xulDomContainer = domContainer;

    children = new ArrayList<XulComponent>();

    combobox = new Combo((Composite)parent.getManagedObject(), SWT.READ_ONLY);
    
    managedObject = combobox;

    combobox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(SelectionEvent e) {
        SwtMenuList.this.changeSupport.firePropertyChange("selectedItem", 
            previousSelectedItem, combobox.getItem(combobox.getSelectionIndex())
        );
        SwtMenuList.this.changeSupport.firePropertyChange("selectedIndex", null
            , combobox.getSelectionIndex());
        
        previousSelectedItem = (T) combobox.getItem(combobox.getSelectionIndex());
      
      }

    });

  }
  
  

  @Override
  public void addChild(Element ele) {
    super.addChild(ele);
    if(ele instanceof XulMenupopup){
      popup = (SwtMenupopup) ele;
    }
  }


  public void layout() {
    combobox.removeAll();
    selectedItem = null; //clear selection
    
    for (XulComponent item : popup.getChildNodes()) {
      SwtMenuitem mItem = (SwtMenuitem) item;
      if(mItem.isSelected()){
        this.selectedItem = mItem;
      }
      combobox.add(mItem.getLabel());
    }
    if(selectedItem != null){
      combobox.select(combobox.indexOf(selectedItem.toString()));
    }
    loaded = true;
  }

  /*
   * Swaps out the managed list.  Effectively replaces the SwingMenupopup child component.
   * (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulMenuList#replaceAll(java.util.List)
   */
  @Deprecated
  public void replaceAllItems(Collection<T> tees) {
    setElements(tees);
  }

  public T getSelectedItem() {
    int idx = combobox.getSelectionIndex();
    return (idx > -1 && idx < this.combobox.getItemCount())? (T) this.combobox.getItem(idx) : (T) null;
  }

  public void setSelectedItem(T t) {
    this.combobox.select(combobox.indexOf(t.toString()));
  }

  public void setOncommand(final String command) {
    combobox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter(){

      public void widgetSelected(SelectionEvent e) {

        /*
         * This actionlistener is fired at parse time when elements are added.
         * We'll ignore that call by checking a variable set to true post parse time
         */
        if (!loaded) {
          return;
        }
        Document doc = getDocument();
        XulRoot window = (XulRoot) doc.getRootElement();
        final XulDomContainer con = window.getXulDomContainer();

        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            try {
              con.invoke(command, new Object[] {});
            } catch (XulException e) {
              logger.error("Error calling oncommand event", e);
            }
          }
        });
        
      }
    });
  }

  public Collection<T> getElements() {
    return (Collection) popup.getChildNodes();
  }

  public String getBinding() {
    return binding;
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  private String extractLabel(T t) {
    String attribute = getBinding();
    if (StringUtils.isEmpty(attribute)) {
      return t.toString();
    } else {
      String getter = "get" + (String.valueOf(attribute.charAt(0)).toUpperCase()) + attribute.substring(1);
      try {
        return new Expression(t, getter, null).getValue().toString();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void setElements(Collection<T> tees) {
    for (XulComponent menuItem : popup.getChildNodes()) {
      popup.removeChild(menuItem);
    }

    for (T t : tees) {
      try{
        XulMenuitem item = (XulMenuitem) xulDomContainer.getDocumentRoot().createElement("menuitem");

      String attribute = getBinding();
      if (StringUtils.isEmpty(attribute)) {
        item.setLabel(extractLabel(t));
      }

      popup.addChild(item);
      } catch(XulException e){
        logger.error("Unable to create new menulist menuitem: ", e);
      }
    }

    layout();
  }
  public int getSelectedIndex() {
    return this.combobox.getSelectionIndex();
  }

  public void setSelectedIndex(int idx) {
    this.combobox.select(idx);  
  }
}
