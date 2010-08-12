/**
 * 
 */
package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.gwt.binding.GwtBinding;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.AbstractModelList;
import org.pentaho.ui.xul.util.AbstractModelNode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OEM
 *
 */
public class SampleDnDEventHandler extends AbstractXulEventHandler {
 
  @Bindable
  public void init(){
    AbstractModelList people = new AbstractModelList();
    people.add(new Person("Bob"));
    people.add(new Person("James"));
    people.add(new Person("Tom"));
    people.add(new Person("Harry"));

    XulListbox listbox = (XulListbox) document.getElementById("list");
    final XulTree tree = (XulTree) document.getElementById("tree");

    GwtBinding bind = new GwtBinding(people,"children", listbox,"elements");
    getXulDomContainer().addBinding(bind);

    try {
      bind.fireSourceChanged();
    } catch (XulException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvocationTargetException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    final Person p = new Person("Rick");
    Person mom = new Person("Ted");
    mom.add(new Person("Larry"));
    p.add(mom);
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));
    p.add(new Person("Morris"));


    p.addPropertyChangeListener("children", new PropertyChangeListener(){
      public void propertyChange(PropertyChangeEvent evt) {
        tree.setElements(p);
      }
    });

    tree.setElements(p);

  }

  public String getName(){
    return "handler";
  }

}
