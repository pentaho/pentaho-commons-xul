package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.AbstractModelNode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: nbaker
 * Date: Aug 6, 2010
 */
public class Person extends AbstractModelNode<Person> {
  private String name;
  private PropertyChangeListener listener = new PropertyChangeListener(){
    public void propertyChange(PropertyChangeEvent evt) {
      fireCollectionChanged();
    }
  };

  public Person(String name) {
    this.name = name;
  }

  @Bindable
  public String getName() {
    return name;
  }

  @Bindable
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void onAdd(Person child) {
    child.addPropertyChangeListener("children", listener);
  }

  @Override
  public void onRemove(Person child) {
    child.removePropertyChangeListener(listener);
  }
}
  