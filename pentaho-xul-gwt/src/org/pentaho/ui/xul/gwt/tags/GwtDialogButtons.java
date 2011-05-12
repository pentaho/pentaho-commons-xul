package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.HorizontalPanel;
import org.pentaho.ui.xul.containers.XulDialogButtons;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

/**
 * User: nbaker
 * Date: 5/4/11
 */
public class GwtDialogButtons extends AbstractGwtXulContainer implements XulDialogButtons {

  static final String ELEMENT_NAME = "dialogbuttons"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME,
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtDialogButtons();
          }
        });
  }


  public GwtDialogButtons() {
    super(ELEMENT_NAME);
    this.orientation = Orient.HORIZONTAL;
    HorizontalPanel hp;
    container = hp = new HorizontalPanel();
    setManagedObject(container);
    hp.setSpacing(GwtUIConst.PANEL_SPACING);    // IE_6_FIX, move to CSS
    hp.setStyleName("xul-dialog-buttons");

  }

}
