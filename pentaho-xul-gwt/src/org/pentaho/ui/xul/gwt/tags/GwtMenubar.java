package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class GwtMenubar extends AbstractGwtXulContainer implements XulMenubar {

  private String menubarName;
  private MenuBar menubar;
  private boolean loaded;
  private boolean vertical = true;

  public static void register() {
    GwtXulParser.registerHandler("menubar", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenubar();
      }
    });
  }

  public GwtMenubar() {
    super("menubar");
  }

  public GwtMenubar(String tagName) {
    super(tagName);
  }

  @Override
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    this.setHorizontal("vertical".equalsIgnoreCase(srcEle.getAttribute("layout")));
    menubar = new MenuBar(vertical);
    this.setLabel(srcEle.getAttribute("label"));
    setManagedObject(menubar);
    // init AFTER we set the managed object and we get "id" set for us
    super.init(srcEle, container);
  }

  public void setLabel(String label) {
    menubar.setTitle(label);
  }

  public String getLabel() {
    return menubar.getTitle();
  }

  public boolean isHorizontal() {
    return vertical;
  }

  public void setHorizontal(boolean horizontal) {
    this.vertical = horizontal;
  }

  public String getMenubarName() {
    return menubarName;
  }

  public void setMenubarName(String name) {
    this.menubarName = name;
  }

  @Bindable
  public void setVisible(boolean visible) {
    this.visible = visible;
    menubar.getElement().getStyle().setProperty("display", (this.visible) ? "" : "none"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    
    if (getParent() instanceof GwtMenubar) {
      ((MenuBar)getParent().getManagedObject()).clearItems();
      ((GwtMenubar)getParent()).layout();
    }
  }

  @Override
  public void layout() {
    menubar.clearItems();
    for (XulComponent c : this.getChildNodes()) {
      add(c);
    }
    if (!loaded) {
      loaded = true;
    }
  }

  private void add(XulComponent c) {
    if (c instanceof XulMenuitem) {
      MenuItem item = (MenuItem) c.getManagedObject();
      menubar.addItem(item);
    } else if (c instanceof XulMenuseparator) {
      menubar.addSeparator();
    } else if (c instanceof XulMenubar) {
      if (c.isVisible()) {
        MenuBar bar = (MenuBar) c.getManagedObject();
        MenuItem submenu = new MenuItem(bar.getTitle(), bar);
        submenu.getElement().setId(bar.getElement().getId());
        menubar.addItem(submenu);
      }
    }
  }

  @Override
  public void addChild(Element element) {
    super.addChild(element);
    if (loaded == true) {
      menubar.clearItems();
      this.layout();
    }
  }

  @Override
  public void addChildAt(Element element, int idx) {
    super.addChildAt(element, idx);
    if (loaded == true) {
      menubar.clearItems();
      this.layout();
    }
  }

  @Override
  public void removeChild(Element element) {
    super.removeChild(element);
    XulComponent child = (XulComponent) element;
    if (child instanceof XulMenuitem) {
      menubar.removeItem((MenuItem) child.getManagedObject());
    }

  }

}