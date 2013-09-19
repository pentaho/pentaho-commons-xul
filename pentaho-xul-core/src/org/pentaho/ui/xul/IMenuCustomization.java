package org.pentaho.ui.xul;

public interface IMenuCustomization {

	public enum CustomizationType { INSERT_BEFORE, INSERT_AFTER, FIRST_CHILD, LAST_CHILD, REPLACE, DELETE };
	
	public enum ItemType { MENU_ITEM, SUBMENU };

	public String getLabel();

	public void setLabel(String label);

	public String getAnchorId();

	public void setAnchorId(String anchorId);

	public String getId();

	public void setId(String id);

	public String getCommand();

	public void setCommand(String command);

	public CustomizationType getCustomizationType();

	public void setCustomizationType( CustomizationType customizationType);

	public ItemType getItemType();

	public void setItemType(ItemType itemType);

}
