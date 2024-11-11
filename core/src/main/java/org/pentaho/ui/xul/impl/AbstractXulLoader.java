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


package org.pentaho.ui.xul.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulSettingsManager;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.dom4j.DocumentDom4J;
import org.pentaho.ui.xul.dom.dom4j.ElementDom4J;
import org.pentaho.ui.xul.util.ResourceBundleTranslator;
import org.pentaho.ui.xul.util.XmlParserFactoryProducer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public abstract class AbstractXulLoader implements XulLoader {

  protected XulParser parser;

  protected String rootDir = "/";

  protected Object outerContext = null;

  protected static final Log logger = LogFactory.getLog( AbstractXulLoader.class );

  private ResourceBundle mainBundle = null;

  private List<Object> resourceBundleList = new ArrayList<Object>();

  private List<ClassLoader> classloaders = new ArrayList<ClassLoader>();

  private HashMap<String, ResourceBundle> cachedResourceBundles;

  private XulSettingsManager settings;
  private Locale locale;

  private List<String> includedSources = new ArrayList<String>();

  private List<String> resourceBundles = new ArrayList<String>();
  private URLClassLoader localDirClassLoader;

  public AbstractXulLoader() throws XulException {

    classloaders.add( this.getClass().getClassLoader() );
    DocumentFactory.registerDOMClass( DocumentDom4J.class );
    DocumentFactory.registerElementClass( ElementDom4J.class );

    cachedResourceBundles = new HashMap<String, ResourceBundle>();
    try {
      parser = new XulParser();
    } catch ( Exception e ) {
      throw new XulException( "Error getting XulParser Instance, probably a DOM Factory problem: "
        + e.getMessage(), e );
    }

    locale = Locale.getDefault();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulDomContainer loadXul( Object xulDocument ) throws IllegalArgumentException, XulException {
    Document document = (Document) xulDocument;
    try {
      xulDocument = preProcess( document );

      String processedDoc = performIncludeTranslations( document.asXML() );
      String localOutput =
          ( mainBundle != null ) ? ResourceBundleTranslator.translate( processedDoc, mainBundle ) : processedDoc;

      SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
      final Document doc = rdr.read( new StringReader( localOutput ) );
      XulDomContainer container = new XulWindowContainer( this );
      container.setOuterContext( outerContext );
      container.setSettingsManager( settings );
      container.setResourceBundles( this.resourceBundleList );
      parser.setContainer( container );
      parser.setClassLoaders( classloaders );
      parser.parseDocument( doc.getRootElement() );

      for ( ClassLoader l : classloaders ) {
        container.registerClassLoader( l );
      }
      return container;

    } catch ( Exception e ) {
      throw new XulException( e );
    }
  }

  public void setRootDir( String loc ) {
    if ( !rootDir.equals( "/" ) ) { // lets only set this once
      return;
    }
    if ( loc.lastIndexOf( "/" ) > 0 && loc.indexOf( ".xul" ) > -1 ) { // exists and not first char
      rootDir = loc.substring( 0, loc.lastIndexOf( "/" ) + 1 );
    } else {
      rootDir = loc;
      if ( !( loc.lastIndexOf( '/' ) == loc.length() ) ) { // no trailing slash, add it
        rootDir = rootDir + "/";
      }
    }
  }

  public void registerResourceBundle( final ResourceBundle bundle ) {
    if ( bundle == null ) {
      throw new NullPointerException();
    }
    this.resourceBundleList.add( bundle );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment( Object xulDocument ) throws IllegalArgumentException, XulException {
    Document document = (Document) xulDocument;
    XulDomContainer container = new XulFragmentContainer( this );
    container.setResourceBundles( this.resourceBundleList );
    container.setOuterContext( outerContext );
    container.setSettingsManager( settings );

    parser.reset();
    parser.setClassLoaders( classloaders );
    parser.setContainer( container );
    parser.parseDocument( document.getRootElement() );

    return container;
  }

  public XulComponent createElement( String elementName ) throws XulException {
    return parser.getElement( elementName );
  }

  private ResourceBundle loadResourceBundle( final String resStr ) {
    ResourceBundle res = cachedResourceBundles.get( resStr );
    if ( res == null ) {
      for ( final ClassLoader cl : classloaders ) {
        try {
          res = ResourceBundle.getBundle( resStr, locale, cl );
          if ( res != null ) {
            break;
          }
        } catch ( MissingResourceException e ) {
          // ignored ..
        }
      }

      if ( res == null ) {
        try {
          final URLClassLoader cls = getLocalDirClassLoader();
          res = ResourceBundle.getBundle( resStr, locale, cls );
        } catch ( MalformedURLException ex ) {
          return null;
        } catch ( MissingResourceException ex ) {
          return null;
        }
      }
      cachedResourceBundles.put( resStr, res );
    }
    return res;
  }

  public XulDomContainer loadXul( String resource ) throws IllegalArgumentException, XulException {

    setRootDir( resource );

    final String resStr = resource.replace( ".xul", "" );
    final ResourceBundle res = loadResourceBundle( resStr );
    if ( res == null ) {
      final Document doc = findDocument( resource );
      return loadXul( doc );
    } else {
      return loadXul( resource, res );
    }
  }

  public XulDomContainer loadXul( String resource, Object bundle ) throws XulException {

    final Document doc = findDocument( resource );

    setRootDir( resource );
    mainBundle = (ResourceBundle) bundle;

    final String resStr = resource.replace( ".xul", "" );
    final ResourceBundle res = loadResourceBundle( resStr );
    if ( res != null ) {
      resourceBundleList.add( res );
    } else {
      return loadXul( doc );
    }

    resourceBundleList.add( mainBundle );
    return this.loadXul( doc );

  }

  public XulDomContainer loadXulFragment( String resource ) throws IllegalArgumentException, XulException {

    setRootDir( resource );

    final String resStr = resource.replace( ".xul", "" );
    final ResourceBundle res = loadResourceBundle( resStr );
    if ( res == null ) {
      final Document doc = findDocument( resource );
      return loadXulFragment( doc );
    }

    return loadXulFragment( resource, res );
  }

  public XulDomContainer loadXulFragment( String resource, Object bundle ) throws XulException {

    if ( bundle instanceof ResourceBundle == false ) {
      throw new XulException( "Need a resource-bundle as bundle" );
    }

    try {

      final InputStream in = getResourceAsStream( resource );
      if ( in == null ) {
        throw new XulException( "Given resource does not yield a valid document" );
      }
      try {
        resourceBundleList.add( bundle );
        final String localOutput = ResourceBundleTranslator.translate( in, (ResourceBundle) bundle );
        final SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
        final Document doc = rdr.read( new StringReader( localOutput ) );

        setRootDir( resource );

        return this.loadXulFragment( doc );
      } finally {
        in.close();
      }
    } catch ( DocumentException e ) {
      throw new XulException( "Error parsing Xul Document", e );
    } catch ( IOException e ) {
      throw new XulException( "Error loading Xul Document into Freemarker", e );
    }
  }

  public String performIncludeTranslations( final String input ) throws XulException {
    for ( final String includeSrc : includedSources ) {
      final String resStr = includeSrc.replace( ".xul", "" );
      final ResourceBundle res = loadResourceBundle( resStr );
      if ( res != null ) {
        resourceBundleList.add( res );
      } else {
        try {
          final URLClassLoader cls = getLocalDirClassLoader();
          resourceBundleList.add( ResourceBundle.getBundle( resStr, locale, cls ) );
        } catch ( MalformedURLException ex ) {
          // intentionally empty
        } catch ( MissingResourceException ex ) {
          // intentionally empty
        }

      }
    }

    for ( final String resource : resourceBundles ) {
      logger.debug( "Processing Resource Bundle: " + resource );
      final ResourceBundle res = loadResourceBundle( resource );
      if ( res != null ) {
        resourceBundleList.add( res );
      } else {
        try {
          final URLClassLoader cls = getLocalDirClassLoader();
          resourceBundleList.add( ResourceBundle.getBundle( resource, locale, cls ) );
        } catch ( MalformedURLException ex ) {
          // intentionally empty
        } catch ( MissingResourceException ex ) {
          // intentionally empty
        }
      }
    }

    String output = input;
    for ( final Object bundle : resourceBundleList ) {
      try {
        output = ResourceBundleTranslator.translate( output, (ResourceBundle) bundle );
      } catch ( IOException e ) {
        e.printStackTrace();
      }
    }
    return output;
  }

  private URLClassLoader getLocalDirClassLoader() throws MalformedURLException {
    if ( localDirClassLoader == null ) {
      final URL url = new File( "." ).toURI().toURL();
      localDirClassLoader = URLClassLoader.newInstance( new URL[] { url } );
    }
    return localDirClassLoader;
  }

  public void register( String tagName, String className ) {
    parser.registerHandler( tagName, className );
  }

  public String getRootDir() {
    return this.rootDir;
  }

  public Document preProcess( Document srcDoc ) throws XulException {

    XPath xpath = new DefaultXPath( "//pen:include" );

    HashMap uris = new HashMap();
    uris.put( "xul", "http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul" );
    uris.put( "pen", "http://www.pentaho.org/2008/xul" );

    xpath.setNamespaceURIs( uris );

    for ( Node node : xpath.selectNodes( srcDoc ) ) {
      if ( node.getNodeType() != Node.ELEMENT_NODE ) {
        continue;
      }

      Element element = (Element) node;
      String src = this.getRootDir() + element.attributeValue( "src" );

      String resourceBundle = element.attributeValue( "resource" );
      if ( resourceBundle != null ) {
        resourceBundles.add( resourceBundle );
      } else {
        resourceBundles.add( src.replace( ".xul", "" ) );
      }

      InputStream in = null;
      try {
        in = getResourceAsStream( src );

        if ( in != null ) {
          logger.debug( "Adding include src: " + src );
          includedSources.add( src );
        } else {
          // try fully qualified name
          src = element.attributeValue( "src" );
          in = getResourceAsStream( src );
          if ( in != null ) {
            includedSources.add( src );
            logger.debug( "Adding include src: " + src );
          } else {
            File f = new File( this.getRootDir() + src );
            if ( f.exists() ) {
              try {
                in = new FileInputStream( f );
                includedSources.add( src );
              } catch ( FileNotFoundException e ) {
                e.printStackTrace();
              }
            }
          }

        }

        final Document doc = getDocFromInputStream( in );

        Element root = doc.getRootElement();
        String ignoreRoot = element.attributeValue( "ignoreroot" );
        if ( root.getName().equals( "overlay" ) ) {
          processOverlay( root, element.getDocument().getRootElement() );
        } else if ( ignoreRoot == null || ignoreRoot.equalsIgnoreCase( "false" ) ) {
          logger.debug( "Including entire file: " + src );
          List contentOfParent = element.getParent().content();
          int index = contentOfParent.indexOf( element );
          contentOfParent.set( index, root );

          if ( root.getName().equals( "dialog" ) ) {
            String newOnload = root.attributeValue( "onload" );
            if ( newOnload != null ) {
              String existingOnload = srcDoc.getRootElement().attributeValue( "onload" );
              String finalOnload = "";
              if ( existingOnload != null ) {
                finalOnload = existingOnload + ", ";
              }
              finalOnload += newOnload;
              srcDoc.getRootElement().setAttributeValue( "onload", finalOnload );
            }
          }

          // process any overlay children
          List<Element> overlays = element.elements();
          for ( Element overlay : overlays ) {
            logger.debug( "Processing overlay within include" );

            this.processOverlay( overlay.attributeValue( "src" ), srcDoc );
          }
        } else {
          logger.debug( "Including children: " + src );
          List contentOfParent = element.getParent().content();
          int index = contentOfParent.indexOf( element );
          contentOfParent.remove( index );
          List children = root.elements();
          for ( int i = children.size() - 1; i >= 0; i-- ) {
            Element child = (Element) children.get( i );
            contentOfParent.add( index, child );

            if ( child.getName().equals( "dialog" ) ) {
              String newOnload = child.attributeValue( "onload" );
              if ( newOnload != null ) {
                String existingOnload = srcDoc.getRootElement().attributeValue( "onload" );
                String finalOnload = "";
                if ( existingOnload != null ) {
                  finalOnload = existingOnload + ", ";
                }
                finalOnload += newOnload;
                srcDoc.getRootElement().setAttributeValue( "onload", finalOnload );
              }
            }
          }

          // process any overlay children
          List<Element> overlays = element.elements();
          for ( Element overlay : overlays ) {
            logger.debug( "Processing overlay within include" );

            this.processOverlay( overlay.attributeValue( "src" ), srcDoc );
          }
        }
      } finally {
        try {
          if ( in != null ) {
            in.close();
          }
        } catch ( IOException ignored ) {
          //ignored
        }
      }
    }

    return srcDoc;
  }

  protected Document getDocFromInputStream( InputStream in ) throws XulException {
    try {

      BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
      StringBuffer buf = new StringBuffer();
      String line;
      while ( ( line = reader.readLine() ) != null ) {
        buf.append( line );
      }
      in.close();

      String upperedIdDoc = this.upperCaseIDAttrs( buf.toString() );
      SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
      return rdr.read( new StringReader( upperedIdDoc ) );
    } catch ( Exception e ) {
      e.printStackTrace();
      return null;
    }
  }

  protected Document findDocument( String src ) throws XulException {
    Document doc = getDocFromClasspath( src );
    if ( doc == null ) {
      doc = getDocFromFile( src );
    }
    if ( doc == null ) {
      throw new XulException( "Can not locate Xul document [" + src + "]" );
    }
    return doc;
  }

  protected Document getDocFromClasspath( String src ) throws XulException {
    InputStream in = getResourceAsStream( this.getRootDir() + src );
    if ( in != null ) {
      Document doc = getDocFromInputStream( in );
      return doc;
    } else {
      // try fully qualified name
      in = getResourceAsStream( src );
      if ( in != null ) {
        return getDocFromInputStream( in );
      } else {
        return null;
        // throw new XulException("Can no locate Xul document [" + src + "]");
      }
    }
  }

  protected Document getDocFromFile( String src ) throws XulException {

    File f = new File( this.getRootDir() + src );
    if ( f.exists() ) {

      Document doc;
      try {
        doc = getDocFromInputStream( new FileInputStream( f ) );
        return doc;
      } catch ( FileNotFoundException ignored ) {
        //ignored
      }
    } else {
      // try fully qualified name
      f = new File( src );
      if ( f.exists() ) {

        Document doc;
        try {
          doc = getDocFromInputStream( new FileInputStream( f ) );
          return doc;
        } catch ( FileNotFoundException ignored ) {
          //ignored
        }
      } else {
        return null;
        // throw new XulException("Can no locate Xul document [" + src + "]");
      }
    }
    return null;
  }

  protected void processOverlay( String overlaySrc, Document doc ) {
    try {
      final Document overlayDoc = findDocument( overlaySrc );
      processOverlay( overlayDoc.getRootElement(), doc.getRootElement() );
    } catch ( Exception e ) {
      logger.error( "Could not load include overlay document: " + overlaySrc, e );
    }
  }

  protected void processOverlay( Element overlayEle, Element srcEle ) {
    for ( Object child : overlayEle.elements() ) {
      Element overlay = (Element) child;
      String overlayId = overlay.attributeValue( "ID" );
      logger.debug( "Processing overlay\nID: " + overlayId );
      Element sourceElement = srcEle.getDocument().elementByID( overlayId );
      if ( sourceElement == null ) {
        logger.error( "Could not find corresponding element in src doc with id: " + overlayId );
        continue;
      }
      logger.debug( "Found match in source doc:" );

      String removeElement = overlay.attributeValue( "removeelement" );
      if ( removeElement != null && removeElement.equalsIgnoreCase( "true" ) ) {
        sourceElement.getParent().remove( sourceElement );
      } else {

        List attribs = overlay.attributes();

        // merge in attributes
        for ( Object o : attribs ) {
          Attribute atr = (Attribute) o;
          sourceElement.addAttribute( atr.getName(), atr.getValue() );
        }

        Document targetDocument = srcEle.getDocument();

        // lets start out by just including everything
        for ( Object overlayChild : overlay.elements() ) {
          Element pluckedElement = (Element) overlay.content().remove( overlay.content().indexOf( overlayChild ) );

          if ( pluckedElement.getName().equals( "dialog" ) ) {
            String newOnload = pluckedElement.attributeValue( "onload" );
            if ( newOnload != null ) {
              String existingOnload = targetDocument.getRootElement().attributeValue( "onload" );
              String finalOnload = "";
              if ( existingOnload != null ) {
                finalOnload = existingOnload + ", ";
              }
              finalOnload += newOnload;
              targetDocument.getRootElement().setAttributeValue( "onload", finalOnload );
            }
          }

          String insertBefore = pluckedElement.attributeValue( "insertbefore" );
          String insertAfter = pluckedElement.attributeValue( "insertafter" );
          String position = pluckedElement.attributeValue( "position" );

          // determine position to place it
          int positionToInsert = -1;
          if ( insertBefore != null ) {
            Element insertBeforeTarget = sourceElement.elementByID( insertBefore );
            positionToInsert = sourceElement.elements().indexOf( insertBeforeTarget );

          } else if ( insertAfter != null ) {
            Element insertAfterTarget = sourceElement.elementByID( insertAfter );
            positionToInsert = sourceElement.elements().indexOf( insertAfterTarget );
            if ( positionToInsert != -1 ) {
              positionToInsert++; // we want to be after that point;
            }
          } else if ( position != null ) {
            int pos = Integer.parseInt( position );
            positionToInsert = ( pos <= sourceElement.elements().size() ) ? pos : -1;
          }
          if ( positionToInsert == -1 ) {
            // default to last
            positionToInsert = sourceElement.elements().size();
          }
          if ( positionToInsert > sourceElement.elements().size() ) {
            sourceElement.elements().add( pluckedElement );
          } else {
            sourceElement.elements().add( positionToInsert, pluckedElement );
          }
          logger.debug( "processed overlay child: " + ( (Element) overlayChild ).getName() + " : "
              + pluckedElement.getName() );
        }
      }
    }
  }

  private InputStream getInputStreamForSrc( String src ) {
    InputStream in = getResourceAsStream( this.getRootDir() + src );
    if ( in == null ) {
      // try fully qualified name
      in = getResourceAsStream( src );
      if ( in == null ) {
        File f = new File( src );
        if ( f.exists() ) {
          try {
            in = new FileInputStream( f );
          } catch ( FileNotFoundException e ) {
            logger.error( e );
          }
        } else {
          logger.error( "Cant find overlay source" );
        }
      }
    }
    return in;
  }

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException {

    String baseName = overlaySrc.replace( ".xul", "" );
    ResourceBundle res = loadResourceBundle( baseName );
    if ( res == null ) {
      baseName = ( this.getRootDir() + overlaySrc ).replace( ".xul", "" );
      res = loadResourceBundle( baseName );
      if ( res == null ) {
        logger.debug( "could not find resource bundle, defaulting to main" );
        res = mainBundle;
      } else {
        resourceBundleList.add( res );
      }
    } else {
      resourceBundleList.add( res );
    }
    this.processOverlay( overlaySrc, targetDocument, container, res );
  }

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container, Object resourceBundle ) throws XulException {

    InputStream in = getInputStreamForSrc( overlaySrc );
    Document doc = null;

    ResourceBundle res = (ResourceBundle) resourceBundle;

    String runningTranslatedOutput = getDocFromInputStream( in ).asXML(); // TODO IOUtils this
    if ( resourceBundle != null ) {
      try {
        runningTranslatedOutput = ResourceBundleTranslator.translate( runningTranslatedOutput, res );

      } catch ( IOException e ) {
        logger.error( "Error loading resource bundle for overlay: " + overlaySrc, e );
      }
    }

    // check for top-level message bundle and apply it
    if ( this.mainBundle != null ) {
      try {

        runningTranslatedOutput = ResourceBundleTranslator.translate( runningTranslatedOutput, this.mainBundle );
        try {
          SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
          String upperedIdDoc = this.upperCaseIDAttrs( runningTranslatedOutput.toString() );
          doc = rdr.read( new StringReader( upperedIdDoc ) );
        } catch ( DocumentException e ) {
          logger.error( "Error loading XML while applying top level message bundle to overlay file:", e );
        }
      } catch ( IOException e ) {
        logger.error( "Error loading Resource Bundle File to apply to overlay: ", e );
      }
    } else {
      try {
        SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
        String upperedIdDoc = this.upperCaseIDAttrs( runningTranslatedOutput.toString() );
        doc = rdr.read( new StringReader( upperedIdDoc ) );
      } catch ( DocumentException e ) {
        logger.error( "Error loading XML while applying top level message bundle to overlay file:", e );
      }
    }

    Element overlayRoot = doc.getRootElement();

    for ( Object child : overlayRoot.elements() ) {
      Element overlay = (Element) child;
      String overlayId = overlay.attributeValue( "ID" );
      String removeElement = overlay.attributeValue( "removeelement" );

      org.pentaho.ui.xul.dom.Element sourceElement = targetDocument.getElementById( overlayId );
      if ( sourceElement == null ) {
        logger.warn( "Cannot overlay element with id [" + overlayId + "] "
          + "as it does not exist in the target document." );
        continue;
      }

      if ( removeElement != null && removeElement.equals( "true" ) ) {
        sourceElement.getParent().removeChild( sourceElement );
      }

      parser.setClassLoaders( classloaders );
      for ( Object childToParse : overlay.elements() ) {
        Element childElement = (Element) childToParse;

        logger.debug( "Processing overlay on element with id: " + overlayId );
        parser.reset();
        parser.setContainer( container );
        XulComponent c = parser.parse( childElement, (XulContainer) sourceElement );

        String insertBefore = childElement.attributeValue( "insertbefore" );
        String insertAfter = childElement.attributeValue( "insertafter" );
        String position = childElement.attributeValue( "position" );

        XulContainer sourceContainer = ( (XulContainer) sourceElement );

        // determine position to place it
        int positionToInsert = -1;
        if ( insertBefore != null ) {
          org.pentaho.ui.xul.dom.Element insertBeforeTarget = targetDocument.getElementById( insertBefore );
          positionToInsert = sourceContainer.getChildNodes().indexOf( insertBeforeTarget );
        } else if ( insertAfter != null ) {
          org.pentaho.ui.xul.dom.Element insertAfterTarget = targetDocument.getElementById( insertAfter );
          positionToInsert = sourceContainer.getChildNodes().indexOf( insertAfterTarget );
          if ( positionToInsert != -1 ) {
            positionToInsert += 1;
          }
        } else if ( position != null ) {
          int pos = Integer.parseInt( position );
          positionToInsert = ( pos <= sourceContainer.getChildNodes().size() ) ? pos : -1;
        }
        if ( positionToInsert == -1 || positionToInsert == sourceContainer.getChildNodes().size() ) {
          // default to previous behavior
          sourceContainer.addChild( c );
        } else {
          sourceContainer.addChildAt( c, positionToInsert );
        }
        notifyOverlayDomReady( c );

        logger.debug( "added child: " + c );
      }

      List attribs = overlay.attributes();

      // merge in attributes
      for ( Object o : attribs ) {
        Attribute atr = (Attribute) o;
        try {
          BeanUtils.setProperty( sourceElement, atr.getName(), atr.getValue() );
          sourceElement.setAttribute( atr.getName(), atr.getValue() );
        } catch ( InvocationTargetException e ) {
          logger.error( e );
        } catch ( IllegalAccessException e ) {
          logger.error( e );
        }
      }

    }

  }

  protected void notifyOverlayDomReady( XulComponent comp ) {
    comp.onDomReady();
    for ( XulComponent ele : comp.getChildNodes() ) {
      notifyOverlayDomReady( ele );
    }
  }

  public void removeOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException {

    final Document doc = findDocument( overlaySrc );

    Element overlayRoot = doc.getRootElement();

    for ( Object child : overlayRoot.elements() ) {
      Element overlay = (Element) child;

      for ( Object childToParse : overlay.elements() ) {
        String childId = ( (Element) childToParse ).attributeValue( "ID" );

        org.pentaho.ui.xul.dom.Element prevOverlayedEle = targetDocument.getElementById( childId );
        if ( prevOverlayedEle == null ) {
          logger.debug( "Source Element from target document is null: " + childId );
          continue;
        }

        prevOverlayedEle.getParent().removeChild( prevOverlayedEle );

      }

    }
  }

  private String upperCaseIDAttrs( String src ) {

    String result = src.replace( " id=", " ID=" );
    return result;

  }

  public void setOuterContext( Object context ) {
    outerContext = context;
  }

  public boolean isRegistered( String elementName ) {
    return this.parser.handlers.containsKey( elementName );
  }

  public void registerClassLoader( Object loader ) {
    if ( classloaders.contains( loader ) == false ) {
      classloaders.add( (ClassLoader) loader );
    }

  }

  @Override
  public void deRegisterClassLoader( Object loader ) {
    if ( classloaders.contains( loader ) ) {
      classloaders.remove( loader );
    }
  }

  public InputStream getResourceAsStream( String name ) {
    for ( ClassLoader loader : classloaders ) {
      InputStream str = loader.getResourceAsStream( name );
      if ( str != null ) {
        return str;
      }
    }
    return null;
  }

  public void setSettingsManager( XulSettingsManager settings ) {
    this.settings = settings;
  }

  public XulSettingsManager getSettingsManager() {
    return settings;
  }
}
