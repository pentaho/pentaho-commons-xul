/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.ui.xul.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;

public class GwtDomElement implements Element {
  protected List<Element> children = null;
  String name;
  Element parent;

  public GwtDomElement( String name ) {
    this.name = name;
    children = new ArrayList<Element>();
  }

  public String getText() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getName() {
    return name;
  }

  public Document getDocument() {
    if ( this instanceof Document ) {
      return (Document) this;
    }
    if ( parent == null ) {
      Document doc = new GwtDomDocument();
      doc.addChild( this );
      return doc;
    }
    if ( parent instanceof Document ) {
      return (Document) parent;
    } else {
      return parent.getDocument();
    }

  }

  public XulComponent getParent() {
    return (XulComponent) parent;
  }

  public XulComponent getFirstChild() {
    if ( children.size() > 0 ) {
      return (XulComponent) children.get( 0 );
    } else {
      return null;
    }
  }

  public List<XulComponent> getChildNodes() {
    ArrayList<XulComponent> list = new ArrayList<XulComponent>() {
      @Override
      public boolean remove( Object o ) {
        children.remove( o );
        return super.remove( o );
      }

      @Override
      public XulComponent remove( int index ) {
        children.remove( index );
        return super.remove( index );
      }

      @Override
      public void clear() {
        super.clear();
        children.clear();
      }
    };
    if ( this.children == null ) {
      return list;
    }
    for ( Element ele : this.children ) {
      list.add( (XulComponent) ele );

    }
    return list;
  }

  public void setNamespace( String prefix, String uri ) {
    throw new UnsupportedOperationException();

  }

  public Namespace getNamespace() {
    throw new UnsupportedOperationException();
  }

  public XulComponent getElementById( String id ) {
    if ( id == null ) {
      throw new IllegalArgumentException( "ID null" );
    }
    if ( id.equals( this.getAttributeValue( "id" ) ) ) {
      return (XulComponent) this;
    } else if ( children != null ) {
      for ( Element child : children ) {
        Element elem = child.getElementById( id );
        if ( elem != null ) {
          return (XulComponent) elem;
        }
      }
    }
    return null;

  }

  public XulComponent getElementByXPath( String path ) {
    // support for : "/window/vbox/groupbox/vbox/label"
    //CHECKSTYLE IGNORE EmptyBlock FOR NEXT 3 LINES
    if ( path.startsWith( "//" ) ) {
      // global search
    } else if ( path.startsWith( "/" ) ) {
      // root lookup
      String[] xpath = path.split( "/" );
      List<Element> currElements = new ArrayList<Element>();
      currElements.add( getDocument() );
      for ( int i = 1; i < xpath.length; i++ ) {
        List<Element> newCurrElements = new ArrayList<Element>();
        for ( Element elem : currElements ) {
          List<XulComponent> list = elem.getElementsByTagName( xpath[i] );
          newCurrElements.addAll( list );
        }
        currElements = newCurrElements;
      }
      if ( currElements.size() > 0 ) {
        return (XulComponent) currElements.get( 0 );
      }
    }
    return null;
  }

  public List<XulComponent> getElementsByTagName( String tagName ) {

    ArrayList<XulComponent> list = new ArrayList<XulComponent>();
    if ( children == null ) {
      return list;
    }
    for ( Element child : children ) {
      if ( child.getName().equals( tagName ) ) {
        list.add( (XulComponent) child );
      }
    }
    return list;

  }

  public void addChild( Element element ) {
    if ( children == null ) {
      children = new ArrayList<Element>();
    }

    Element dElementparent = element.getParent();
    if ( dElementparent != null ) {
      dElementparent.removeChild( element );
    }

    ( (GwtDomElement) element ).setParent( this );
    children.add( element );

  }

  public void setParent( Element parent ) {
    // this should be a GwtDomElement
    this.parent = parent;
  }

  public void removeChild( Element element ) {
    if ( children != null ) {
      children.remove( element );
    }
  }

  public Object getElementObject() {
    throw new UnsupportedOperationException();
  }

  public List<Attribute> getAttributes() {
    if ( attributes == null ) {
      attributes = new HashMap<String, Attribute>();
    }
    return new ArrayList<Attribute>( attributes.values() );
  }

  public void setAttributes( List<Attribute> attribute ) {
    throw new UnsupportedOperationException();
  }

  public void setAttribute( Attribute attribute ) {
    attributes.put( attribute.getName(), attribute );
  }

  Map<String, Attribute> attributes = null;

  public void setAttribute( String name, String value ) {
    if ( attributes == null ) {
      attributes = new HashMap<String, Attribute>();
    }
    attributes.put( name, new Attribute( name, value ) );

  }

  public String getAttributeValue( String attributeName ) {
    if ( attributes == null ) {
      attributes = new HashMap<String, Attribute>();
    }
    Attribute attrib = attributes.get( attributeName );
    if ( attrib != null ) {
      return attrib.getValue();
    }
    return null;
  }

  public void replaceChild( Element oldElement, Element newElement ) {
    int index = children.indexOf( oldElement );
    children.remove( index );
    children.add( index, newElement );
    ( (GwtDomElement) newElement ).setParent( (XulComponent) this );
  }

  public void addChildAt( Element element, int idx ) {

    if ( children == null ) {
      children = new ArrayList<Element>();
    }
    ( (GwtDomElement) element ).setParent( this );
    children.add( idx, element );

  }

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {

    // TODO Auto-generated method stub

  }

}
