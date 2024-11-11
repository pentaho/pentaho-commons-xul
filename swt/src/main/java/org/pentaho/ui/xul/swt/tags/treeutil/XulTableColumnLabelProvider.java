/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags.treeutil;

import java.util.Vector;

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

    XulTreeCell cell = ( (XulTreeItem) obj ).getRow().getCell( i );
    if ( cell == null ) {
      return "";
    }

    switch ( tree.getColumns().getColumn( i ).getColumnType() ) {
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
        return getPasswordString( cell.getLabel().length() );
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

  private Image makeImage( final Shell shell, boolean type ) {
    if ( SwtXulUtil.isRunningOnWebspoonMode() ) {
      String location = "org/pentaho/ui/xul/swt/tags/images/";
      location += type ? "checked.png" : "unchecked.png";
      return new Image( shell.getDisplay(), this.getClass().getClassLoader().getResourceAsStream( location ) );
    } else {
      Shell placeholder = new Shell( SWT.NO_TRIM );
      Button btn = new Button( placeholder, SWT.CHECK );
      btn.setSelection( type );
      btn.pack();
      Point bsize = btn.computeSize( SWT.DEFAULT, SWT.DEFAULT );
      btn.setLocation( 0, 0 );
      placeholder.setLocation( SWT.DEFAULT, SWT.DEFAULT );
      placeholder.setSize( bsize );
      placeholder.open();

      final GC gc = new GC( btn );
      final Image image = new Image( shell.getDisplay(), bsize.x, bsize.y );
      gc.copyArea( image, 0, 0 );
      gc.dispose();

      placeholder.close();

      return image;
    }
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
    if ( val == null || !( val instanceof Boolean ) ) {
      return false;
    }
    return (Boolean) c.getValue();
  }
}
