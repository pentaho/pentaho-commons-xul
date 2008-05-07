package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

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
	public boolean isHidden();
	public void setVisible(boolean visible);
	
	public void setOnload(String onload);
	public String getOnload();
	
	
}
