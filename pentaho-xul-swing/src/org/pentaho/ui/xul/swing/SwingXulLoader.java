/**
 *
 */
package org.pentaho.ui.xul.swing;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.AbstractXulLoader;
import org.pentaho.ui.xul.impl.XulElementFactory;
import org.pentaho.ui.xul.swing.tags.SwingButton;
import org.pentaho.ui.xul.swing.tags.SwingCaption;
import org.pentaho.ui.xul.swing.tags.SwingCheckbox;
import org.pentaho.ui.xul.swing.tags.SwingColumn;
import org.pentaho.ui.xul.swing.tags.SwingColumns;
import org.pentaho.ui.xul.swing.tags.SwingDeck;
import org.pentaho.ui.xul.swing.tags.SwingDialog;
import org.pentaho.ui.xul.swing.tags.SwingDialogheader;
import org.pentaho.ui.xul.swing.tags.SwingFileDialog;
import org.pentaho.ui.xul.swing.tags.SwingGrid;
import org.pentaho.ui.xul.swing.tags.SwingGroupbox;
import org.pentaho.ui.xul.swing.tags.SwingHbox;
import org.pentaho.ui.xul.swing.tags.SwingImage;
import org.pentaho.ui.xul.swing.tags.SwingLabel;
import org.pentaho.ui.xul.swing.tags.SwingListbox;
import org.pentaho.ui.xul.swing.tags.SwingListitem;
import org.pentaho.ui.xul.swing.tags.SwingMenu;
import org.pentaho.ui.xul.swing.tags.SwingMenuList;
import org.pentaho.ui.xul.swing.tags.SwingMenubar;
import org.pentaho.ui.xul.swing.tags.SwingMenuitem;
import org.pentaho.ui.xul.swing.tags.SwingMenupopup;
import org.pentaho.ui.xul.swing.tags.SwingMenuseparator;
import org.pentaho.ui.xul.swing.tags.SwingMessageBox;
import org.pentaho.ui.xul.swing.tags.SwingOverlay;
import org.pentaho.ui.xul.swing.tags.SwingProgressmeter;
import org.pentaho.ui.xul.swing.tags.SwingRadio;
import org.pentaho.ui.xul.swing.tags.SwingRadioGroup;
import org.pentaho.ui.xul.swing.tags.SwingRow;
import org.pentaho.ui.xul.swing.tags.SwingRows;
import org.pentaho.ui.xul.swing.tags.SwingScale;
import org.pentaho.ui.xul.swing.tags.SwingScript;
import org.pentaho.ui.xul.swing.tags.SwingSeparator;
import org.pentaho.ui.xul.swing.tags.SwingSpacer;
import org.pentaho.ui.xul.swing.tags.SwingSplitter;
import org.pentaho.ui.xul.swing.tags.SwingStatusbar;
import org.pentaho.ui.xul.swing.tags.SwingStatusbarpanel;
import org.pentaho.ui.xul.swing.tags.SwingTab;
import org.pentaho.ui.xul.swing.tags.SwingTabbox;
import org.pentaho.ui.xul.swing.tags.SwingTabpanel;
import org.pentaho.ui.xul.swing.tags.SwingTabpanels;
import org.pentaho.ui.xul.swing.tags.SwingTabs;
import org.pentaho.ui.xul.swing.tags.SwingTextbox;
import org.pentaho.ui.xul.swing.tags.SwingTree;
import org.pentaho.ui.xul.swing.tags.SwingTreeCell;
import org.pentaho.ui.xul.swing.tags.SwingTreeChildren;
import org.pentaho.ui.xul.swing.tags.SwingTreeCol;
import org.pentaho.ui.xul.swing.tags.SwingTreeCols;
import org.pentaho.ui.xul.swing.tags.SwingTreeItem;
import org.pentaho.ui.xul.swing.tags.SwingTreeRow;
import org.pentaho.ui.xul.swing.tags.SwingVbox;
import org.pentaho.ui.xul.swing.tags.SwingWindow;

/**
 * @author nbaker
 */
public class SwingXulLoader extends AbstractXulLoader implements XulLoader
{
  public SwingXulLoader() throws XulException
  {
    super();

    //attach Renderers
    parser.registerHandler("WINDOW", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingWindow(e, c, parent, tag);
      }
    });
    parser.registerHandler("BUTTON", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingButton(e, c, parent, tag);
      }
    });
    parser.registerHandler("VBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingVbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("HBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingHbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("LABEL", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingLabel(e, c, parent, tag);
      }
    });
    parser.registerHandler("TEXTBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTextbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("SCRIPT", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingScript(e, c, parent, tag);
      }
    });
    parser.registerHandler("SPACER", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingSpacer(e, c, parent, tag);
      }
    });
    parser.registerHandler("CHECKBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingCheckbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("RADIO", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingRadio(e, c, parent, tag);
      }
    });
    parser.registerHandler("RADIOGROUP", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingRadioGroup(e, c, parent, tag);
      }
    });
    parser.registerHandler("GROUPBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingGroupbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("CAPTION", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingCaption(e, c, parent, tag);
      }
    });
    parser.registerHandler("LISTBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingListbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("LISTITEM", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingListitem(e, c, parent, tag);
      }
    });
    parser.registerHandler("MESSAGEBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMessageBox(e, c, parent, tag);
      }
    });
    parser.registerHandler("DECK", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingDeck(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENUBAR", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenubar(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENU", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenu(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENUPOPUP", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenupopup(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENUITEM", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenuitem(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENULIST", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenuList(e, c, parent, tag);
      }
    });
    parser.registerHandler("MENUSEPARATOR", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingMenuseparator(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREE", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTree(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREECOLS", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeCols(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREECOL", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeCol(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREECHILDREN", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeChildren(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREEITEM", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeItem(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREEROW", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeRow(e, c, parent, tag);
      }
    });
    parser.registerHandler("TREECELL", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTreeCell(e, c, parent, tag);
      }
    });
    parser.registerHandler("SPLITTER", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingSplitter(e, c, parent, tag);
      }
    });
    parser.registerHandler("OVERLAY", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingOverlay(e, c, parent, tag);
      }
    });

    parser.registerHandler("TABBOX", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTabbox(e, c, parent, tag);
      }
    });
    parser.registerHandler("TABS", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTabs(e, c, parent, tag);
      }
    });
    parser.registerHandler("TAB", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTab(e, c, parent, tag);
      }
    });
    parser.registerHandler("TABPANELS", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTabpanels(e, c, parent, tag);
      }
    });
    parser.registerHandler("TABPANEL", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingTabpanel(e, c, parent, tag);
      }
    });
    parser.registerHandler("DIALOG", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingDialog(e, c, parent, tag);
      }
    });
    parser.registerHandler("DIALOGHEADER", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingDialogheader(e, c, parent, tag);
      }
    });
    parser.registerHandler("PROGRESSMETER", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingProgressmeter(e, c, parent, tag);
      }
    });
    parser.registerHandler("FILEDIALOG", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingFileDialog(e, c, parent, tag);
      }
    });
    parser.registerHandler("STATUSBAR", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingStatusbar(e, c, parent, tag);
      }
    });
    parser.registerHandler("STATUSBARPANEL", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingStatusbarpanel(e, c, parent, tag);
      }
    });
    parser.registerHandler("IMAGE", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingImage(e, c, parent, tag);
      }
    });
    parser.registerHandler("SEPARATOR", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingSeparator(e, c, parent, tag);
      }
    });


    parser.registerHandler("GRID", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingGrid(e, c, parent, tag);
      }
    });
    parser.registerHandler("COLUMNS", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingColumns(e, c, parent, tag);
      }
    });
    parser.registerHandler("COLUMN", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingColumn(e, c, parent, tag);
      }
    });
    parser.registerHandler("ROWS", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingRows(e, c, parent, tag);
      }
    });
    parser.registerHandler("ROW", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingRow(e, c, parent, tag);
      }
    });

    parser.registerHandler("SCALE", new XulElementFactory()
    {
      public XulComponent create(final Element e,
                                 final XulComponent c,
                                 final XulDomContainer parent,
                                 final String tag) throws XulException
      {
        return new SwingScale(e, c, parent, tag);
      }
    });


  }


  public XulLoader getNewInstance() throws XulException
  {
    return new SwingXulLoader();
  }

  public XulDomContainer loadXul(Document xulDocument) throws IllegalArgumentException, XulException
  {
    return loadXul(xulDocument, outerContext);
  }

  public XulDomContainer loadXul(Document xulDocument,
                                 Object outerContext) throws IllegalArgumentException, XulException
  {
    setOuterContext(outerContext);
    XulDomContainer domC = super.loadXul(xulDocument);
    return domC;
  }

}
