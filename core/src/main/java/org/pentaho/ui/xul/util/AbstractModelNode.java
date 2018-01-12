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

package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.XulEventSourceAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Base class for UI model objects that provides {@see java.util.List} implementations and XulEventSource support
 * 
 * @param <T>
 *          type of children
 */
public abstract class AbstractModelNode<T> extends XulEventSourceAdapter implements List<T>, Iterable<T>, Serializable {

  protected List<T> children = new ArrayList<T>();
  protected AbstractModelNode parent;

  // set-like behavior checking additions by comparison
  protected boolean isUniqueList;

  public AbstractModelNode() {
  }

  public AbstractModelNode( AbstractModelNode parent, List<T> children ) {
    this( children );
    setParent( parent );
  }

  public AbstractModelNode( List<T> children ) {
    this.children = new ArrayList<T>( children );
    for ( T t : this.children ) {
      onAdd( t );
    }
  }

  public AbstractModelNode getParent() {
    return parent;
  }

  public void setParent( AbstractModelNode parent ) {
    this.parent = parent;
  }

  private List<T> getChildren() {
    // return Collections.unmodifiableList(children);

    return this;
  }

  protected void fireCollectionChanged() {
    firePropertyChange( "children", null, this.getChildren() );
  }

  public boolean add( T child ) {
    if ( this.isUniqueList ) {
      T t = checkForUnique( child );
      if ( t != null ) {
        remove( t );
      }
    }
    boolean retVal = this.children.add( child );
    if ( child instanceof AbstractModelNode ) {
      ( (AbstractModelNode) child ).setParent( this );
    }
    onAdd( child );

    fireCollectionChanged();
    return retVal;
  }

  public T remove( int idx ) {
    T t = children.remove( idx );
    onRemove( t );
    fireCollectionChanged();
    return t;
  }

  public boolean remove( Object child ) {
    if ( !this.children.contains( child ) ) {
      throw new IllegalArgumentException( "Child does not exist in collection" );
    }
    boolean retVal = this.children.remove( child );
    if ( retVal ) {
      onRemove( (T) child );
    }
    fireCollectionChanged();
    return retVal;
  }

  public T removeModel( int pos ) {
    if ( pos > this.children.size() ) {
      throw new IllegalArgumentException( "Specified position (" + pos + ") is greater than collection length" );
    }
    T retVal = this.children.remove( pos );
    onRemove( retVal );
    fireCollectionChanged();
    return retVal;
  }

  public Iterator<T> iterator() {
    return this.children.iterator();
  }

  public void clear() {
    for ( T t : this.children ) {
      onRemove( t );
    }
    this.children.clear();
    fireCollectionChanged();
  }

  public void moveChildUp( T column ) {
    if ( !this.children.contains( column ) ) {
      throw new IllegalArgumentException( "child does not exist in collection" );
    }

    int pos = this.children.indexOf( column );
    moveChildUp( pos );
  }

  public void moveChildUp( int position ) {
    if ( position - 1 < 0 ) {
      throw new IllegalArgumentException( "Specified position (" + position
          + ") is greater than child collection length" );
    }
    // If already at Beginning do nothing
    if ( position == 0 ) {
      return;
    }
    T child = this.children.remove( position );
    this.children.add( position - 1, child );
    fireCollectionChanged();
  }

  public void moveChildDown( T column ) {
    if ( !this.children.contains( column ) ) {
      throw new IllegalArgumentException( "child does not exist in collection" );
    }

    int pos = this.children.indexOf( column );
    moveChildDown( pos );
  }

  public void moveChildDown( int position ) {
    if ( position < 0 || position + 1 >= this.children.size() ) {
      throw new IllegalArgumentException( "Specified position (" + position
          + ") is greater than child collection length" );
    }

    T child = this.children.remove( position );
    this.children.add( position + 1, child );
    fireCollectionChanged();
  }

  public List<T> asList() {
    // UnmodifiableList not serializable
    // return Collections.unmodifiableList(this.children);

    return this.children;
  }

  public boolean addAll( Collection<? extends T> c ) {
    if ( this.isUniqueList ) {
      Iterator<? extends T> iter = c.iterator();
      while ( iter.hasNext() ) {
        T t = checkForUnique( iter.next() );
        if ( t != null ) {
          remove( t );
        }
      }
    }
    boolean retVal = this.children.addAll( c );
    if ( retVal ) {
      for ( T t : c ) {
        onAdd( t );
      }
      fireCollectionChanged();
    }
    return retVal;
  }

  public boolean contains( Object o ) {
    return this.children.contains( o );
  }

  public boolean containsAll( Collection<?> c ) {
    boolean retval = true;
    for ( Object t : c ) {
      if ( this.children.contains( t ) == false ) {
        retval = false;
        break;
      }
    }
    return retval;
  }

  public boolean isEmpty() {
    return this.children.isEmpty();
  }

  public boolean removeAll( Collection<?> c ) {
    boolean retVal = this.children.removeAll( c );
    for ( Object t : c ) {
      onRemove( (T) t );
    }

    fireCollectionChanged();
    return retVal;
  }

  public boolean retainAll( Collection<?> c ) {
    boolean retVal = this.children.retainAll( c );

    fireCollectionChanged();
    return retVal;
  }

  public int size() {
    return this.children.size();
  }

  public Object[] toArray() {
    return this.children.toArray();
  }

  public <T> T[] toArray( T[] a ) {
    return this.children.toArray( a );
  }

  public void add( int index, T element ) {
    if ( this.isUniqueList ) {
      T t = checkForUnique( element );
      if ( t != null ) {
        remove( t );
      }
    }
    children.add( index, element );
    onAdd( element );
    fireCollectionChanged();
  }

  public boolean addAll( int index, Collection<? extends T> c ) {

    if ( this.isUniqueList ) {
      Iterator<? extends T> iter = c.iterator();
      while ( iter.hasNext() ) {
        T t = checkForUnique( iter.next() );
        if ( t != null ) {
          remove( t );
        }
      }
    }

    boolean retVal = children.addAll( index, c );
    if ( retVal ) {
      for ( T t : c ) {
        onAdd( t );
      }
    }
    fireCollectionChanged();
    return retVal;
  }

  public T get( int index ) {
    return children.get( index );
  }

  public int indexOf( Object o ) {
    return children.indexOf( o );
  }

  public int lastIndexOf( Object o ) {
    return children.lastIndexOf( o );
  }

  public ListIterator<T> listIterator() {
    return children.listIterator();
  }

  public ListIterator<T> listIterator( int index ) {
    return children.listIterator( index );
  }

  public T set( int index, T element ) {
    T result = children.set( index, element );
    fireCollectionChanged();
    return result;
  }

  public List<T> subList( int fromIndex, int toIndex ) {
    // children.subList() does not compile in GWT, re-implemented here
    List<T> newList = new ArrayList<T>();
    for ( int i = fromIndex; i < children.size() && i < toIndex; i++ ) {
      newList.add( children.get( i ) );
    }
    return newList;
  }

  public void onAdd( T child ) {

  }

  public void onRemove( T child ) {

  }

  public boolean isUniqueList() {
    return isUniqueList;
  }

  public void setUniqueList( boolean uniqueList ) {
    isUniqueList = uniqueList;
  }

  protected T checkForUnique( T newChild ) {
    for ( T t : this ) {
      if ( compareChildren( t, newChild ) ) {
        return t;
      }
    }
    return null;
  }

  protected boolean compareChildren( T child, T newChild ) {
    return child.equals( newChild );
  }
}
