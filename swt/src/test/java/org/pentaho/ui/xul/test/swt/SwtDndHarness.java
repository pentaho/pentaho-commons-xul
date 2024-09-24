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

package org.pentaho.ui.xul.test.swt;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.binding.DefaultBindingFactory;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.swt.SwtXulLoader;
import org.pentaho.ui.xul.swt.SwtXulRunner;

public class SwtDndHarness {

  public static class ListItem extends XulEventSourceAdapter implements Serializable {

    public String label = null;

    public ListItem( String label ) {
      this.label = label;
    }

    public void setLabel( String label ) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  public static class TreeNode extends XulEventSourceAdapter implements XulEventSource, Serializable {
    public List<TreeNode> list = new ArrayList<TreeNode>();
    public String name;
    public boolean expanded;

    public TreeNode( String name ) {
      this.name = name;
    }

    public List<TreeNode> getChildren() {
      return list;
    }

    public void setChildren( List<TreeNode> list ) {
      throw new UnsupportedOperationException( "Who is calling this?" );
    }

    public String getName() {
      return name;
    }

    public void setName( String name ) {
      this.name = name;
    }

    public String toString() {
      return name;
    }

    public boolean isExpanded() {
      return expanded;
    }

    public void setExpanded( boolean expanded ) {
      this.expanded = expanded;
    }
  }

  public static class DndHandler extends AbstractXulEventHandler {
    private XulListbox listbox1 = null;
    private XulTree mytree = null;
    List<TreeNode> root = new ArrayList<TreeNode>();
    List<ListItem> items = new ArrayList<ListItem>();

    public String getName() {
      return "dndHandler";
    }

    public void setItems( List<ListItem> items ) {
      this.items = items;
    }

    public List<ListItem> getItems() {
      return items;
    }

    public List<TreeNode> getRoot() {
      return root;
    }

    public void setRoot( List<TreeNode> root ) {
      throw new UnsupportedOperationException( "who?" );
    }

    public void onDrag( DropEvent event ) {
      List<Object> objs = event.getDataTransfer().getData();
      for ( Object obj : objs ) {
        if ( obj instanceof TreeNode ) {
          TreeNode node = (TreeNode) obj;
          if ( node.getName().equalsIgnoreCase( "root" ) ) {
            event.setAccepted( false );
            return;
          }
        }
      }
    }

    public void onTreeDrop( DropEvent event ) {
      List<Object> objs = event.getDataTransfer().getData();
      List<Object> newobjs = new ArrayList<Object>();
      for ( Object obj : objs ) {
        if ( obj instanceof TreeNode ) {
          TreeNode node = (TreeNode) obj;
          newobjs.add( node );
          if ( node.getName().equalsIgnoreCase( "root" ) ) {
            event.setAccepted( false );
            return;
          }
        } else if ( obj instanceof ListItem ) {
          TreeNode node = new TreeNode( "TREE: " + ( (ListItem) obj ).getLabel() );
          newobjs.add( node );
        } else {
          System.out.println( "CLASS NOT SUPPORTED: " + obj.getClass() );
          event.setAccepted( false );
          return;
        }
      }
      event.getDataTransfer().setData( newobjs );
    }

    public void initialize() {
      listbox1 = (XulListbox) document.getElementById( "listbox" );
      mytree = (XulTree) document.getElementById( "myTree" );

      TreeNode r = new TreeNode( "Root" );
      TreeNode c1 = new TreeNode( "Child 1" );
      TreeNode c2 = new TreeNode( "Child 2" );
      r.list.add( c1 );
      r.list.add( c2 );
      root.add( r );

      items.add( new ListItem( "Test 1" ) );
      items.add( new ListItem( "Test 2" ) );
      items.add( new ListItem( "Test 3" ) );

      BindingFactory bf = new DefaultBindingFactory();
      bf.setDocument( document );

      Binding tb = bf.createBinding( this, "root", mytree, "elements" );
      Binding lb = bf.createBinding( this, "items", listbox1, "elements" );
      try {
        tb.fireSourceChanged();
        lb.fireSourceChanged();

      } catch ( Exception e ) {
        e.printStackTrace();
      }

      listbox1.setRows( 5 );
    }
  }

  public static void main( String[] args ) {
    try {

      InputStream in = SwtXulRunner.class.getClassLoader().getResourceAsStream( "documents/dnd.xul" );

      if ( in == null ) {
        System.out.println( "Input is null" );
        System.exit( 123 );
      }

      SAXReader rdr = new SAXReader();
      final Document doc = rdr.read( in );

      XulDomContainer container = new SwtXulLoader().loadXul( doc );

      container.addEventHandler( new DndHandler() );

      XulRunner runner = new SwtXulRunner();
      runner.addContainer( container );

      runner.initialize();
      runner.start();

    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace( System.out );
    }
  }

}
