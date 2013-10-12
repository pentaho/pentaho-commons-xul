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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags.treeutil;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.swt.tags.SwtTreeItem;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class XulTableColumnLabelProvider implements ITableLabelProvider {

  private static final String UNCHECKED = "UNCHECKED";
  private static final String CHECKED = "CHECKED";

  private XulTree tree;
  private XulDomContainer domContainer;
  private static Log logger = LogFactory.getLog( XulTableColumnLabelProvider.class );

  private int imageIndex = 0;

  public XulTableColumnLabelProvider( XulTree tree, XulDomContainer aDomContainer ) {
    this.tree = tree;
    this.domContainer = aDomContainer;
    if ( JFaceResources.getImageRegistry().getDescriptor( CHECKED ) == null ) {
      JFaceResources.getImageRegistry().put( UNCHECKED,
          makeImage( ( (TableViewer) tree.getManagedObject() ).getControl().getShell(), false ) );
      JFaceResources.getImageRegistry().put( CHECKED,
          makeImage( ( (TableViewer) tree.getManagedObject() ).getControl().getShell(), true ) );
    }
  }

  public String getColumnText( Object obj, int i ) {

    final int colIdx = i;
    XulTreeCell cell = ( (XulTreeItem) obj ).getRow().getCell( colIdx );
    if ( cell == null ) {
      return "";
    }

    switch ( tree.getColumns().getColumn( colIdx ).getColumnType() ) {
      case CHECKBOX:
        return cell.getLabel() != null ? cell.getLabel() : cell.getLabel();
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        Vector vec = (Vector) cell.getValue();
        if ( vec != null && vec.size() > cell.getSelectedIndex() ) {
          return "" + vec.get( cell.getSelectedIndex() );
        } else {
          return "";
        }
      case TEXT:
        return cell.getLabel() != null ? cell.getLabel() : "";
      case PASSWORD:
        return String.format( getPasswordString( cell.getLabel().length() ) );
      default:
        return cell.getLabel() != null ? cell.getLabel() : "";
    }

  }

  private String getPasswordString( int len ) {
    StringBuilder b = new StringBuilder();
    while ( len-- > 0 ) {
      b.append( '*' );
    }
    return b.toString();
  }

  public void addListener( ILabelProviderListener ilabelproviderlistener ) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty( Object obj, String s ) {
    return false;
  }

  public void removeListener( ILabelProviderListener ilabelproviderlistener ) {
  }

  public Image getColumnImage( Object row, int col ) {
    if ( tree.getColumns().getColumn( col ).getColumnType() == ColumnType.CHECKBOX ) {
      if ( isSelected( row, col ) ) {
        return JFaceResources.getImageRegistry().get( CHECKED );
      } else {
        return JFaceResources.getImageRegistry().get( UNCHECKED );
      }
    }

    if ( tree.getColumns().getColumn( col ).getImagebinding() != null ) {
      String src = ( (SwtTreeItem) row ).getImage();
      Display display = ( (TableViewer) tree.getManagedObject() ).getTable().getDisplay();
      return SwtXulUtil.getCachedImage( src, domContainer, display );
    }
    return null;
  }

  private Image makeImage( Shell shell, boolean type ) {
    Shell placeholder = new Shell( shell, SWT.NO_TRIM );
    Button btn = new Button( placeholder, SWT.CHECK );
    btn.setSelection( type );
    Point bsize = btn.computeSize( SWT.DEFAULT, SWT.DEFAULT );
    btn.setSize( bsize );
    placeholder.setSize( bsize );
    btn.setLocation( 0, 0 );
    placeholder.open();

    GC gc = new GC( btn );
    Image image = new Image( shell.getDisplay(), bsize.x, bsize.y );
    gc.copyArea( image, 0, 0 );
    gc.dispose();

    placeholder.close();

    return image;
  }

  private boolean isSelected( Object row, int col ) {
    XulTreeRow r = ( (XulTreeItem) row ).getRow();
    if ( r == null || r.getChildNodes().size() < col ) {
      return false;
    }
    XulTreeCell c = r.getCell( col );
    if ( c == null ) {
      return false;
    }
    Object val = c.getValue();
    if ( val == null || val instanceof Boolean == false ) {
      return false;
    }
    return (Boolean) c.getValue();
  }
}
