package org.pentaho.ui.xul.gwt.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;

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

  private String typeName;

  private String packageName;

  private String className;
  
  private TreeLogger logger;
  
  private List<JClassType> implementingTypes;
  
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

    this.logger = logger;
    typeOracle = context.getTypeOracle();
    
    
    try {

      //find XulEventSource implementors
      implementingTypes = new ArrayList<JClassType>();
      JClassType eventSourceType = typeOracle.getType("org.pentaho.ui.xul.XulEventSource");

      for(JClassType type :typeOracle.getTypes()){
        if(type.isAssignableTo(eventSourceType)){
          implementingTypes.add(type);
        }
      }
      
      // get classType and save instance variables 
      JClassType classType = typeOracle.getType(typeName);
      packageName = classType.getPackage().getName();
      className = classType.getSimpleSourceName() + "Impl";
      // Generate class source code 
      generateClass(logger, context);

      
    } catch (Exception e) {
      // record to logger that Map generation threw an exception 
      logger.log(TreeLogger.ERROR, "Error generating BindingContext!!!", e);

    }

    // return the fully qualifed name of the class generated 
    return packageName + "." + className;
        
  }

  
  private String getTypeName(JType type){
    String sType = "";  

    if(type.isArray() != null){
      sType = type.isArray().getQualifiedSourceName();
      if(sType.contains("extends")){
        sType = sType.substring(sType.indexOf("extends")+7).trim();
      }
      if(sType.contains("[]") == false){
        sType+="[]";
      }

      System.out.println("array: "+sType);
    } else if(type.isGenericType() != null || type.getQualifiedSourceName().contains("extends")){
      if(type.isGenericType() != null && type.getQualifiedSourceName().contains("extends")){
        sType = type.isGenericType().getQualifiedSourceName();
      } else {
        sType = "Object";
      }
    } else if(type.isPrimitive() != null){
      JPrimitiveType primative = type.isPrimitive();
      sType = primative.getQualifiedBoxedSourceName();
    } else {
      sType = type.getQualifiedSourceName();
    }
    return sType;
  }
  
  private void generateClass(TreeLogger logger, GeneratorContext context) { 

    // get print writer that receives the source code 
    PrintWriter printWriter = null; 
    printWriter = context.tryCreate(logger, packageName, className); 
    // print writer if null, source code has ALREADY been generated, return
        if (printWriter == null) return; 

        // init composer, set class properties, create source writer 
    ClassSourceFileComposerFactory composer = null; 
    composer = new ClassSourceFileComposerFactory(packageName, className); 
    composer.addImplementedInterface("org.pentaho.ui.xul.gwt.binding.TypeController");
    composer.addImport("org.pentaho.ui.xul.gwt.binding.*");
    composer.addImport("java.util.Map");
    composer.addImport("java.util.HashMap");
    composer.addImport("org.pentaho.ui.xul.XulException");
    
    
    SourceWriter sourceWriter = null; 
    sourceWriter = composer.createSourceWriter(context, printWriter); 

    // generator constructor source code 
    generateConstructor(sourceWriter);
    
    
    writeMethods(sourceWriter);
    
    // close generated class 
    sourceWriter.outdent(); 
    sourceWriter.println("}"); 

    // commit generated class 
    context.commit(logger, printWriter); 

  }
  
  private void generateConstructor(SourceWriter sourceWriter) { 

    sourceWriter.println("public Map<String, GwtBindingMethod> wrappedTypes = new HashMap<String, GwtBindingMethod>();");

    // start constructor source generation 
    sourceWriter.println("public " + className + "() { "); 
    sourceWriter.indent(); 
    sourceWriter.println("super();");
    sourceWriter.println("populateMap0();"); 
    

    sourceWriter.outdent(); 
    sourceWriter.println("}"); 
    //write out the map of wrapped classes
    populateMapWtihWrappers(sourceWriter);


  }
  
  private void writeMethods(SourceWriter sourceWriter){
    sourceWriter.println("public GwtBindingMethod findGetMethod(Object obj, String propertyName){");
    sourceWriter.indent();
    

    sourceWriter.println("GwtBindingMethod retVal = wrappedTypes.get((obj.getClass().getName()+\"_get\"+propertyName).toLowerCase());");

    sourceWriter.println("if(retVal == null){");
    sourceWriter.indent();
    sourceWriter.println("retVal = wrappedTypes.get((obj.getClass().getName()+\"_is\"+propertyName).toLowerCase());");
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("return retVal;");

    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public GwtBindingMethod findSetMethod(Object obj, String propertyName){");
    sourceWriter.indent();
    
    sourceWriter.println("return wrappedTypes.get((obj.getClass().getName()+\"_set\"+propertyName).toLowerCase());");
    sourceWriter.outdent();
    sourceWriter.println("}");
  }
  

  private void populateMapWtihWrappers(SourceWriter sourceWriter){
    int methodNum = 0;
    int methodCount = 0;
    int totalClassCount = 0;
    sourceWriter.println("public void populateMap"+methodNum+"(){ "); 
    sourceWriter.indent();
    sourceWriter.println("GwtBindingMethod otherM;");
    for(JClassType type : implementingTypes){
      System.out.println("-------------------------");
      String keyRoot = generateTypeKey(type);

      System.out.println("generating type: "+type.getQualifiedSourceName());
      if(type.isAbstract()){
        System.out.println("abstract");
        continue;
      }
      try{
        
        JClassType loopType = type;
        JClassType eventSourceType = typeOracle.getType("org.pentaho.ui.xul.XulEventSource");
        while(loopType.getSuperclass() != null && loopType.getSimpleSourceName().equals("Object") == false && loopType.isAssignableTo(eventSourceType)){

          String superName = generateTypeKey(loopType);

          System.out.println("    generating inner type: "+superName);
          totalClassCount++;
          for(JMethod m : loopType.getMethods()){
            if(!m.isPublic()){
              continue;
            }
            methodCount++;
            if(methodCount > 100){
              methodNum++;
              sourceWriter.println("populateMap"+methodNum+"();");
              sourceWriter.outdent();
              sourceWriter.println("};");
              sourceWriter.println("public void populateMap"+methodNum+"(){ "); 
              sourceWriter.println("GwtBindingMethod otherM;");
              sourceWriter.indent();
              methodCount = 0;
            }
            //System.out.println("        generating inner type method: "+m.getName());
            
            String methodName = m.getName();
            sourceWriter.println("otherM = wrappedTypes.get(\""+superName+"_"+methodName+"\".toLowerCase());");
            sourceWriter.println("if(otherM != null){");
              sourceWriter.println("wrappedTypes.put((\""+keyRoot+"_"+methodName+"\").toLowerCase(), otherM);");
            sourceWriter.println("}");

            sourceWriter.println("if(otherM == null){");
            sourceWriter.indent();
            sourceWriter.println("GwtBindingMethod newMethod = new GwtBindingMethod(){");
            
  
            sourceWriter.println("public Object invoke(Object obj, Object[] args) throws XulException { ");
            sourceWriter.indent();
            sourceWriter.println("try{");
            sourceWriter.println(loopType.getQualifiedSourceName()+" target = ("+loopType.getQualifiedSourceName()+") obj;");
            
            JParameter[] params = m.getParameters();
            String argList = "";
            int pos = 0;
            for(JParameter param : params){
              if(pos > 0){
                argList += ", ";
              }
              argList += "("+getTypeName(param.getType())+") args["+pos+"]";
              pos++;
            }
            
            if(isVoidReturn(m.getReturnType())){
              sourceWriter.println("target."+methodName+"("+argList+");");
              sourceWriter.println("return null;");
            } else {
              sourceWriter.println("return "+boxReturnType(m)+" target."+methodName+"("+argList+");");
            }
  
            sourceWriter.println("}catch(Exception e){ throw new XulException(\"error with "+type.getQualifiedSourceName()+"\"+e.getMessage());}");
            sourceWriter.println("}");
  
            sourceWriter.outdent();
            sourceWriter.println("};");
            sourceWriter.println("wrappedTypes.put((\""+keyRoot+"_"+methodName+"\").toLowerCase(), newMethod);");

            sourceWriter.println("wrappedTypes.put((\""+superName+"_"+methodName+"\").toLowerCase(), newMethod);");
            sourceWriter.outdent();
            sourceWriter.println("}");
            
          }
          loopType = loopType.getSuperclass();
        }
      } catch (Exception e) {

        // record to logger that Map generation threw an exception 
        logger.log(TreeLogger.ERROR, "PropertyMap ERROR!!!", e);

      }
     
    }
    
    sourceWriter.outdent();
    sourceWriter.println("}");
    System.out.println("Done generating wrappers: "+totalClassCount+" classes created in the map");
  }

  private String generateTypeKey(JClassType type){
    return type.getQualifiedSourceName();
  }
      
  private boolean isVoidReturn(JType type){
    return type.isPrimitive() != null && type.isPrimitive() == JPrimitiveType.VOID;
  }

  private String boxReturnType(JMethod method){
    if(method.getReturnType().isPrimitive() != null){
      JPrimitiveType primative = method.getReturnType().isPrimitive();
      return "("+primative.getQualifiedBoxedSourceName()+") ";
    }
    return "";
  }

  private String boxPrimative(JType type){
     
    //Check for Generic Types type.isGenericType() doesn't seem to be working
    if(type.isGenericType() != null || type.getQualifiedSourceName().contains("extends")){
      return "java.lang.Object";
    } else if(type.isPrimitive() != null){
      JPrimitiveType primative = type.isPrimitive();
      return primative.getQualifiedBoxedSourceName();
    } else {
      return type.getQualifiedSourceName();
    }
  }
  
}

  