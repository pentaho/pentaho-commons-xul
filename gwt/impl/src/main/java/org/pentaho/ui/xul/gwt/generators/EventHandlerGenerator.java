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

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

@Deprecated
public class EventHandlerGenerator extends Generator {

  private String typeName;

  private String packageName;

  private String className;

  private TypeOracle typeOracle;

  private TreeLogger logger;

  private String handlerClassName;

  @Override
  public String generate( TreeLogger logger, GeneratorContext context, String typeName )
    throws UnableToCompleteException {
    this.typeName = typeName;
    this.logger = logger;
    typeOracle = context.getTypeOracle();

    try {
      // get classType and save instance variables
      JClassType classType = typeOracle.getType( typeName );
      packageName = classType.getPackage().getName();
      handlerClassName = classType.getQualifiedSourceName();
      className = classType.getSimpleSourceName() + "Wrapper";
      // Generate class source code
      generateClass( logger, context );

    } catch ( Exception e ) {

      // record to logger that Map generation threw an exception
      logger.log( TreeLogger.ERROR, "PropertyMap ERROR!!!", e );

    }

    // return the fully qualifed name of the class generated
    return packageName + "." + className;
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
    composer.addImplementedInterface( "org.pentaho.ui.xul.gwt.util.EventHandlerWrapper" );
    composer.addImport( "org.pentaho.ui.xul.impl.XulEventHandler" );

    SourceWriter sourceWriter = null;
    sourceWriter = composer.createSourceWriter( context, printWriter );

    // generator constructor source code
    generateConstructor( sourceWriter );

    generateMethods( sourceWriter );

    // close generated class
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    // commit generated class
    context.commit( logger, printWriter );

  }

  private void generateMethods( SourceWriter sourceWriter ) {

    sourceWriter.println( "public void execute(String method, Object[] args) { " );
    sourceWriter.indent();

    try {
      JClassType classType = typeOracle.getType( typeName );

      do {
        for ( JMethod m : classType.getMethods() ) {
          String methodName = m.getName();

          if ( !m.isPublic() ) {
            continue;
          }

          sourceWriter.println( "if(method.equals(\"" + methodName + "\")){" );
          sourceWriter.indent();

          boolean firstParam = true;
          // method call
          sourceWriter.print( "handler." + methodName + "(" );
          int argPos = 0;
          for ( JParameter param : m.getParameters() ) {
            if ( !firstParam ) {
              sourceWriter.print( ", " );
            } else {
              firstParam = false;
            }
            sourceWriter.print( "(" + boxPrimative( param.getType() ) + ") args[" + argPos + "]" );
            argPos++;
          }
          sourceWriter.print( ");" );
          // end method call

          sourceWriter.println( "return;" );
          sourceWriter.outdent();
          sourceWriter.println( "}" );
        }
      } while ( ( classType = classType.getSuperclass() ).getSimpleSourceName().equals( "Object" ) == false );

    } catch ( Exception e ) {

      // record to logger that Map generation threw an exception
      logger.log( TreeLogger.ERROR, "PropertyMap ERROR!!!", e );

    }
    sourceWriter.println( "System.err.println(\"ERROR: method '\" + method + \"' not annotated with EventMethod.\");" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( handlerClassName + " handler;" );

    sourceWriter.println( "public void setHandler(XulEventHandler handler) { " );
    sourceWriter.indent();
    sourceWriter.println( "this.handler = (" + handlerClassName + ") handler;" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public XulEventHandler getHandler() { " );
    sourceWriter.indent();
    sourceWriter.println( "return this.handler;" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public String getName() { " );
    sourceWriter.indent();
    sourceWriter.println( "return this.handler.getName();" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public Object getData() { " );
    sourceWriter.indent();
    sourceWriter.println( "return null;" );
    sourceWriter.outdent();
    sourceWriter.println( "}" );

    sourceWriter.println( "public void setData(Object o) { " );
    sourceWriter.println( "}" );

  }

  private void generateConstructor( SourceWriter sourceWriter ) {

    // start constructor source generation
    sourceWriter.println( "public " + className + "() { " );
    sourceWriter.indent();
    sourceWriter.println( "super();" );

    sourceWriter.outdent();
    sourceWriter.println( "}" );

  }

  private String boxPrimative( JType type ) {
    if ( type.isPrimitive() != null ) {
      JPrimitiveType primative = type.isPrimitive();
      return primative.getQualifiedBoxedSourceName();
    } else {
      return type.getQualifiedSourceName();
    }
  }
}
