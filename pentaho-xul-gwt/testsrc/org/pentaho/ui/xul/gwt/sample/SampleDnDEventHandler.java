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

/**
 *
 */

package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.gwt.binding.GwtBinding;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.AbstractModelList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;

/**
 * @author OEM
 */
public class SampleDnDEventHandler extends AbstractXulEventHandler {

  @Bindable
  public void init() {
    AbstractModelList people = new AbstractModelList();
    people.add( new Person( "Bob" ) );
    people.add( new Person( "James" ) );
    people.add( new Person( "Tom" ) );
    people.add( new Person( "Harry" ) );

    XulListbox listbox = (XulListbox) document.getElementById( "list" );
    final XulTree tree = (XulTree) document.getElementById( "tree" );

    GwtBinding bind = new GwtBinding( people, "children", listbox, "elements" );
    getXulDomContainer().addBinding( bind );

    try {
      bind.fireSourceChanged();
    } catch ( XulException e ) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch ( InvocationTargetException e ) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    final Person p = new Person( "Rick" );
    Person mom = new Person( "Ted" );
    mom.add( new Person( "Larry" ) );
    p.add( mom );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );
    p.add( new Person( "Morris" ) );


    p.addPropertyChangeListener( "children", new PropertyChangeListener() {
      public void propertyChange( PropertyChangeEvent evt ) {
        tree.setElements( p );
      }
    } );

    tree.setElements( p );

  }

  public String getName() {
    return "handler";
  }

}
