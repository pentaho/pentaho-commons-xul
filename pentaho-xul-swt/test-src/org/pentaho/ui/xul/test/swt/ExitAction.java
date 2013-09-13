package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.action.Action;

public class ExitAction extends Action
{
 JFaceExample parent;

 public ExitAction(JFaceExample parent)
 {
  this.parent = parent;
  setText("E&xit@Ctrl+W");
  setToolTipText("Exit the application");
 }

 public void run()
 {
  parent.stop(true);
 }
}