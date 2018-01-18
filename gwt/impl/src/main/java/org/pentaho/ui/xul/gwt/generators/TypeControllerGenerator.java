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

package org.pentaho.ui.xul.gwt.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class TypeControllerGenerator extends Generator {

  private TypeOracle typeOracle;

  private String packageName;

  private String className;

  private TreeLogger logger;

  private List<JClassType> implementingTypes;

  // Keep track of generated method classes. Used to optimize code for inherited methods
  private List<String> generatedMethods = new ArrayList<String>();

  @Override
  public String generate( TreeLogger logger, GeneratorContext context, String typeName )
    throws UnableToCompleteException {

    this.logger = logger;
    typeOracle = context.getTypeOracle();

    try {

      // find XulEventSource implementors
      implementingTypes = new ArrayList<JClassType>();
      JClassType eventSourceType = typeOracle.getType( "org.pentaho.ui.xul.XulEventSource" );

      for ( JClassType type : typeOracle.getTypes() ) {
        if ( type.isAssignableTo( eventSourceType ) ) {
          implementingTypes.add( type );
        }
      }

      // get classType and save instance variables
      JClassType classType = typeOracle.getType( typeName );
      packageName = classType.getPackage().getName();
      className = classType.getSimpleSourceName() + "Impl";
      // Generate class source code
      generateClass( logger, context );

    } catch ( Exception e ) {
      // record to logger that Map generation threw an exception
      logger.log( TreeLogger.ERROR, "Error generating BindingContext!!!", e );

    }

    // return the fully qualifed name of the class generated
    return packageName + "." + className;

  }

  private String getTypeName( JType type ) {
    String sType = "";

    if ( type.isArray() != null ) {
      sType = type.isArray().getQualifiedSourceName();
      if ( sType.contains( "extends" ) ) {
        sType = sType.substring( sType.indexOf( "extends" ) + 7 ).trim();
      }
      if ( sType.contains( "[]" ) == false ) {
        sType += "[]";
      }

    } else if ( type.isGenericType() != null || type.getQualifiedSourceName().contains( "extends" ) ) {
      if ( type.isGenericType() != null && type.getQualifiedSourceName().contains( "extends" ) ) {
        sType = type.isGenericType().getQualifiedSourceName();
      } else {
        sType = "Object";
      }
    } else if ( type.isPrimitive() != null ) {
      JPrimitiveType primative = type.isPrimitive();
      sType = primative.getQualifiedBoxedSourceName();
    } else {
      sType = type.getQualifiedSourceName();
    }
    return sType;
  }

  private void generateClass( TreeLogger logger, GeneratorContext context ) {

    // get print writer that receives the source code
    PrintWriter printWriter = null;
    printWriter = context.tryCreate( logger, packageName, className );
    // print writer if null, source code has ALREADY been generated, return
    if ( printWriter == null ) {
      return;
    }

    // init composer, set class properties, create source writer
    ClassSourceFileComposerFactory composer = null;
    composer = new ClassSourceFileComposerFactory( packageName, className );
    composer.addImplementedInterface( "org.pentaho.ui.xul.gwt.binding.TypeController" );
    composer.addImport( "org.pentaho.ui.xul.gwt.binding.*" );
    composer.addImport( "java.util.Map" );
    composer.addImport( "java.util.HashMap" );
    composer.addImport( "org.pentaho.ui.xul.XulException" );

    SourceWriter sourceWriter = null;
    sourceWriter = composer.createSourceWriter( context, printWriter );

    // generator constructor source code
    generateConstructor( sourceWriter );

    writeMethods( sourceWriter );

    // close generated class
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    // commit generated class
    context.commit( logger, printWriter );

  }

  private void generateConstructor( SourceWriter sourceWriter ) {

    sourceWriter
        .println( "public Map<String, GwtBindingMethod> wrappedTypes = new HashMap<String, GwtBindingMethod>();" );

    // start constructor source generation
    sourceWriter.println( "public " + className + "() { " );
    sourceWriter.indent();
    sourceWriter.println( "super();" );

    sourceWriter.outdent();
    sourceWriter.println( "}" );

  }

  private void writeMethods( SourceWriter sourceWriter ) {
    sourceWriter.println( "public GwtBindingMethod findGetMethod(Object obj, String propertyName){" );
    sourceWriter.indent();

    sourceWriter
        .println( "GwtBindingMethod retVal = findMethod(obj,\"get\"+propertyName.substring(0,1).toUpperCase()"
          + "+propertyName.substring(1));" );

    sourceWriter.println( "if(retVal == null){" );
    sourceWriter.indent();
    sourceWriter
        .println( "retVal = findMethod(obj,\"is\"+propertyName.substring(0,1).toUpperCase()+propertyName."
          + "substring(1));" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "return retVal;" );

    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public GwtBindingMethod findSetMethod(Object obj, String propertyName){" );
    sourceWriter.indent();

    sourceWriter
        .println( "return findMethod(obj,\"set\"+propertyName.substring(0,1).toUpperCase()+propertyName"
          + ".substring(1));" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public GwtBindingMethod findMethod(Object obj, String propertyName){" );
    sourceWriter.indent();

    sourceWriter.println( "return findOrCreateMethod(obj.getClass().getName(), propertyName);" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    createFindMethod( sourceWriter );
  }

  private void createFindMethod( SourceWriter sourceWriter ) {

    // We create more than one findMethod as the body of one method would be too large. This is the int that we
    // increment to add to the name
    // i.e. findMethod0()
    int methodNum = 0;

    // This int keeps track of how many methods are generated. When it gets to 200 we create a new findMethodX()
    // and chain it to the previous.
    int methodCount = 0;

    sourceWriter.println( "private GwtBindingMethod findOrCreateMethod(String obj, String methodName){ " );
    sourceWriter.indent();

    sourceWriter.println( "GwtBindingMethod newMethod;" );

    // dummy first condition, rest are "else if". Keeps us from having conditional logic.
    sourceWriter.println( "if(false){ }" );

    for ( JClassType type : implementingTypes ) {

      // close last method, chain it to a new one.
      if ( methodCount > 200 ) {
        sourceWriter.println( "return findOrCreateMethod" + ( methodNum ) + "(obj, methodName);" );
        sourceWriter.println( "}" );

        sourceWriter.println( "private GwtBindingMethod findOrCreateMethod" + ( methodNum++ )
            + "(String obj, String methodName){ " );
        sourceWriter.println( "GwtBindingMethod newMethod;" );
        // dummy first condition, rest are "else if". Keeps us from having conditional logic.
        sourceWriter.println( "if(false){ }" );

        methodCount = 0;
      }

      String keyRoot = generateTypeKey( type );

      // if(type.isAbstract()){
      // System.out.println("abstract");
      // continue;
      // }

      // determine if there are any methods that are bindable before writing out conditional for class

      JClassType loopType = type;
      boolean hasBindableMethods = false;
      JClassType eventSourceType = null;
      try {
        eventSourceType = typeOracle.getType( "org.pentaho.ui.xul.XulEventSource" );
      } catch ( NotFoundException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      //CHECKSTYLE IGNORE Indentation FOR NEXT 1 LINES
      outer: while ( loopType.getSuperclass() != null && loopType.getSimpleSourceName().equals( "Object" ) == false
          && loopType.isAssignableTo( eventSourceType ) ) {

        for ( JMethod m : loopType.getMethods() ) {
          if ( m.isPublic() && m.getAnnotation( Bindable.class ) != null ) {
            hasBindableMethods = true;
            break outer;
          }
        }
        loopType = loopType.getSuperclass();
      }
      if ( hasBindableMethods == false ) {
        continue;
      }
      sourceWriter.println( "else if(obj.equals(\"" + type.getQualifiedSourceName() + "\")){ " );
      try {

        loopType = type;
        sourceWriter.indent();

        // Loop over class heirarchy and generate methods for every object that is declared a XulEventSource
        while ( loopType.getSuperclass() != null && loopType.getSimpleSourceName().equals( "Object" ) == false
            && loopType.isAssignableTo( eventSourceType ) ) {
          String superName = generateTypeKey( loopType );

          boolean first = true;
          for ( JMethod m : loopType.getMethods() ) {
            methodCount++;
            if ( !m.isPublic() || m.getAnnotation( Bindable.class ) == null ) {
              continue;
            }

            sourceWriter.println( ( first ? "" : "else " ) + "if(methodName.equals(\"" + m.getName() + "\")){ " );
            if ( first ) {
              first = false;
            }
            sourceWriter.indent();

            String methodName = m.getName();

            // check to see if we've already processed this classes' method. Point to that class instead.
            if ( generatedMethods.contains( ( superName + "_" + methodName ).toLowerCase() ) && type != loopType ) {

              sourceWriter.println( "return findOrCreateMethod(\"" + superName + "\", methodName);" );

            } else {
              // See if it's already been created and cached. If so, return that.
              String keyName = ( keyRoot + "_" + methodName ).toLowerCase();

              sourceWriter.println( "GwtBindingMethod found = wrappedTypes.get(\"" + keyName + "\");" );
              sourceWriter.println( "if(found != null){" );
              sourceWriter.indent();
              sourceWriter.println( "return found;" );
              sourceWriter.outdent();
              sourceWriter.println( "} else {" );
              sourceWriter.indent();

              // Not cached, create a new instance and put it in the cache.
              sourceWriter.println( "newMethod = new GwtBindingMethod(){" );

              sourceWriter.println( "public Object invoke(Object obj, Object[] args) throws XulException { " );
              sourceWriter.indent();
              sourceWriter.println( "try{" );
              sourceWriter.println( loopType.getQualifiedSourceName() + " target = ("
                  + loopType.getQualifiedSourceName() + ") obj;" );

              JParameter[] params = m.getParameters();
              String argList = "";
              int pos = 0;
              for ( JParameter param : params ) {
                if ( pos > 0 ) {
                  argList += ", ";
                }
                argList += "(" + getTypeName( param.getType() ) + ") args[" + pos + "]";
                pos++;
              }

              if ( isVoidReturn( m.getReturnType() ) ) {
                sourceWriter.println( "target." + methodName + "(" + argList + ");" );
                sourceWriter.println( "return null;" );
              } else {
                sourceWriter.println( "return " + boxReturnType( m ) + " target." + methodName + "(" + argList + ");" );
              }

              sourceWriter.println( "}catch(Exception e){ e.printStackTrace(); throw new XulException(\"error with "
                  + type.getQualifiedSourceName() + "\"+e.getMessage());}" );
              sourceWriter.println( "}" );

              sourceWriter.outdent();
              sourceWriter.println( "};" );

              // Add it to the HashMap cache as type and decendant type if available.
              sourceWriter.println( "wrappedTypes.put((\"" + keyName + "\"), newMethod);" );
              if ( keyRoot.equals( superName ) == false ) {
                sourceWriter.println( "wrappedTypes.put((\"" + keyName + "\"), newMethod);" );
              }
              generatedMethods.add( ( keyRoot + "_" + methodName ).toLowerCase() );
              generatedMethods.add( ( superName + "_" + methodName ).toLowerCase() );

              sourceWriter.println( "return newMethod;" );

              sourceWriter.outdent();
              sourceWriter.println( "}" );
            }
            sourceWriter.outdent();

            sourceWriter.println( "}" );

          }

          // go up a level in the heirarchy and check again.
          loopType = loopType.getSuperclass();
        }
        sourceWriter.outdent();
        sourceWriter.println( "}" );

      } catch ( Exception e ) {

        // record to logger that Map generation threw an exception
        logger.log( TreeLogger.ERROR, "PropertyMap ERROR!!!", e );

      }

    }

    sourceWriter.outdent();

    // This is the end of the line, if not found return null.
    sourceWriter.println( "return null;" );
    sourceWriter.println( "}" );
  }

  private String generateTypeKey( JClassType type ) {
    return type.getQualifiedSourceName();
  }

  private boolean isVoidReturn( JType type ) {
    return type.isPrimitive() != null && type.isPrimitive() == JPrimitiveType.VOID;
  }

  private String boxReturnType( JMethod method ) {
    if ( method.getReturnType().isPrimitive() != null ) {
      JPrimitiveType primative = method.getReturnType().isPrimitive();
      return "(" + primative.getQualifiedBoxedSourceName() + ") ";
    }
    return "";
  }

}
