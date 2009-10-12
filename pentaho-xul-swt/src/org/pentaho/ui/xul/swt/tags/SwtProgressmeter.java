package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtProgressmeter extends SwtElement implements XulProgressmeter {

  protected ProgressBar progressmeter;

  private boolean indeterminate;

  private XulComponent parent;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialValue;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMaximum;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMinimum;

  public SwtProgressmeter(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.parent = parent;
    // SWT progress bar not created until mode is known (in layout method)
  }

  protected ProgressBar createNewProgressmeter(Composite parent) {
    return new ProgressBar(parent, isIndeterminate() ? SWT.INDETERMINATE : SWT.NONE);
  }

  public int getMaximum() {
    return progressmeter.getMaximum();
  }

  public int getMinimum() {
    return progressmeter.getMinimum();
  }

  public int getValue() {
    return progressmeter.getSelection();
  }

  public boolean isIndeterminate() {
    return indeterminate;
  }

  public void setIndeterminate(boolean indeterminate) {
    // does nothing since this value set at construction time; (read-only property)
  }

  public void setMaximum(int value) {
    if (null != progressmeter) {
      progressmeter.setMaximum(value);
    } else {
      initialMaximum = value;
    }
  }

  public void setMinimum(int value) {
    if (null != progressmeter) {
      progressmeter.setMinimum(value);
    } else {
      initialMinimum = value;
    }
  }

  public void setMode(String mode) {
    indeterminate = MODE_INDETERMINATE.equals(mode);
  }

  public void setValue(int value) {
    if (null != progressmeter) {
      progressmeter.setSelection(value);
    } else {
      initialValue = value;
    }
  }

  @Override
  public void layout() {
    progressmeter = createNewProgressmeter((Composite) parent.getManagedObject());
    progressmeter.setSelection(initialValue);
    progressmeter.setMinimum(initialMinimum);
    progressmeter.setMaximum(initialMaximum);
    setManagedObject(progressmeter);
  }

}
