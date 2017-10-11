/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.overlay;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Element;

/**
 * User: nbaker Date: 10/25/11
 */
public class OverlayProfile {
  private List<IOverlayAction> actions = new ArrayList<IOverlayAction>();

  public OverlayProfile( Element root, Element rootElement ) {
    for ( Element overlayNode : root.getChildNodes() ) {
      String id = overlayNode.getAttributeValue( "id" );
      if ( id == null ) {
        // log.error("Element target node missing id attribute");
        continue;
      }
      Element targetNode = rootElement.getElementById( id );
      if ( targetNode == null ) {
        // log.error("Cannot remove element with the id of: \""+id+"\" as it does not exist in the document");
        continue;
      }

      // Remove
      if ( "true".equals( overlayNode.getAttributeValue( "removeelement" ) ) ) {
        actions.add( new RemoveOverlayAction( targetNode ) );
      } else {
        // we don't support both removing and adding to a node at the same time for obvious reasons
        for ( Element childNode : overlayNode.getChildNodes() ) {
          AddElementOverlayAction.Type insertType = AddElementOverlayAction.Type.LAST;
          Element relativeElement = null;
          if ( childNode.getAttributeValue( "pos" ) != null ) {
            String pos = childNode.getAttributeValue( "pos" );
            if ( "first".equals( pos ) ) {
              insertType = AddElementOverlayAction.Type.FIRST;
            } else if ( "last".equals( pos ) ) {
              insertType = AddElementOverlayAction.Type.LAST;
            } else {
              insertType = AddElementOverlayAction.Type.POSITION;
            }
          } else if ( childNode.getAttributeValue( "insertbefore" ) != null ) {
            relativeElement = rootElement.getElementById( childNode.getAttributeValue( "insertbefore" ) );
            insertType = AddElementOverlayAction.Type.BEFORE;
          } else if ( childNode.getAttributeValue( "insertafter" ) != null ) {
            relativeElement = rootElement.getElementById( childNode.getAttributeValue( "insertafter" ) );
            insertType = AddElementOverlayAction.Type.AFTER;
          }

          actions.add( new AddElementOverlayAction( childNode, targetNode, relativeElement, insertType ) );
        }
      }

      // Compute change property actions
      ChangePropertyGroup propGroup = new ChangePropertyGroup( targetNode );
      for ( Attribute attr : overlayNode.getAttributes() ) {
        String attrName = attr.getName();
        if ( attrName.equals( "id" ) || attrName.equals( "removeelement" ) ) {
          // ignore specials
          continue;
        }
        propGroup.addAction( new ChangePropertyAction( targetNode, attr.getName(), attr.getValue() ) );
      }
      actions.add( propGroup );

    }
  }

  public void perform() {
    for ( IOverlayAction action : actions ) {
      action.perform();
    }
  }

  public void remove() {
    for ( IOverlayAction action : actions ) {
      action.remove();
    }
  }
}
