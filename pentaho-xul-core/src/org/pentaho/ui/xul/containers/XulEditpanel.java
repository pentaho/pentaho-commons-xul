package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * Edit panels are similar to the docked views in Eclipse. They have a title via a caption child, can
 * be collapsed/expanded and contain a toolbar.
 * <p>
 * Edit Panels are intended to have only one panel child
 * 
 * @author nbaker
 *
 */

public interface XulEditpanel extends XulContainer, XulCaptionedPanel {
  void setCollapsible(boolean collapsible);
  boolean isCollapsible();
}
