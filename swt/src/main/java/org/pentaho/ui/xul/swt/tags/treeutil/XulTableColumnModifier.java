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

package org.pentaho.ui.xul.swt.tags.treeutil;

import java.util.Vector;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.TableItem;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;

public class XulTableColumnModifier implements ICellModifier {

  XulTree tree;

  public XulTableColumnModifier( XulTree tree ) {
    this.tree = tree;
  }

  public boolean canModify( Object arg0, String property ) {
    return tree.getColumns().getColumn( Integer.parseInt( property ) ).isEditable();
  }

  public Object getValue( Object arg0, String property ) {

    final int colIdx = Integer.parseInt( property );
    XulTreeCell cell = ( (XulTreeItem) arg0 ).getRow().getCell( colIdx );
    if ( cell == null ) {
      return "";
    }

    switch ( tree.getColumns().getColumn( colIdx ).getColumnType() ) {
      case CHECKBOX:
        return cell.getValue();
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        Vector vals = (Vector) cell.getValue();
        String[] items = new String[vals.size()];
        for ( int i = 0; i < vals.size(); i++ ) {
          items[i] = "" + vals.get( i );
        }
        ( (ComboBoxCellEditor) ( (TableViewer) tree.getManagedObject() ).getCellEditors()[colIdx] ).setItems( items );
        return cell.getSelectedIndex();
      case TEXT:
      case PASSWORD:
        ( (TextCellEditor) ( (TableViewer) tree.getManagedObject() ).getCellEditors()[colIdx] ).getControl()
            .setEnabled( !cell.isDisabled() );

        return cell.getLabel() != null ? cell.getLabel() : "";
      default:
        return cell.getValue();
    }

  }

  public void modify( Object element, String property, Object value ) {

    final int colIdx = Integer.parseInt( property );
    XulTreeCell cell = ( (XulTreeItem) ( (TableItem) element ).getData() ).getRow().getCell( colIdx );
    if ( cell == null ) {
      return;
    }

    switch ( tree.getColumns().getColumn( colIdx ).getColumnType() ) {
      case CHECKBOX:
        cell.setValue( (Boolean) value );
        break;
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        if ( value instanceof String ) {
          cell.setLabel( (String) value );
        } else if ( ( (Integer) value ) > -1 ) {
          cell.setSelectedIndex( (Integer) value );
        }
        break;
      default:
        cell.setLabel( (String) value );
    }
    ( (TableViewer) tree.getManagedObject() ).refresh();
  }

}
