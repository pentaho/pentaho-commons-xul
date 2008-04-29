package org.pentaho.ui.xul.containers;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

public interface XulDialog extends XulContainer{

	public void setButtons(String buttons);
	public String getButtons();
	
	public void setButtonlabelcancel(String label);
	public String getButtonlabelcancel();
	
	public void setButtonlabelaccept(String label);
	public String getButtonlabelaccept();
	
	public void setOndialogaccept(String command);
	public String getOndialogaccept();

	public void setOndialogcancel(String command);
	public String getOndialogcancel();
	
	public void setTitle(String title);
	public String getTitle();
	
	public void setHeight(int height);
	public void setWidth(int width);
	
	public int getHeight();
	public int getWidth();
	
	public void setButtonalign(String align);
	public String getButtonalign();

	public void show();
	public void hide();
	public void setVisible(boolean visible);
	
	
}
