package org.pentaho.ui.xul.gwt.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

public class TypeControllerGenerator extends Generator {

  private TypeOracle typeOracle;

  private String packageName;

  private String className;
  
  private TreeLogger logger;
  
  private List<JClassType> implementingTypes;
  
  // Keep track of generated method classes. Used to optimize code for inherited methods
  private List<String> generatedMethods = new ArrayList<String>();
  
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
    
    sourceWriter.outdent(); 
    sourceWriter.println("}"); 

  }
  
  private void writeMethods(SourceWriter sourceWriter){
    sourceWriter.println("public GwtBindingMethod findGetMethod(Object obj, String propertyName){");
    sourceWriter.indent();
    

    sourceWriter.println("GwtBindingMethod retVal = findMethod(obj.getClass().getName(),\"get\"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1));");

    sourceWriter.println("if(retVal == null){");
    sourceWriter.indent();
    sourceWriter.println("retVal = findMethod(obj.getClass().getName(),\"is\"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1));");
    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("return retVal;");

    sourceWriter.outdent();
    sourceWriter.println("}");

    sourceWriter.println("public GwtBindingMethod findSetMethod(Object obj, String propertyName){");
    sourceWriter.indent();
    
    sourceWriter.println("return findMethod(obj.getClass().getName(),\"set\"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1));");
    sourceWriter.outdent();
    sourceWriter.println("}");
    
    sourceWriter.println("public GwtBindingMethod findMethod(Object obj, String propertyName){");
    sourceWriter.indent();
    
    sourceWriter.println("return findMethod(obj.getClass().getName(), propertyName);");
    sourceWriter.outdent();
    sourceWriter.println("}");
    
    createFindMethod(sourceWriter);
  }
  
  private void createFindMethod(SourceWriter sourceWriter){
    
    // We create more than one findMethod as the body of one method would be too large. This is the int that we increment to add to the name
    // i.e. findMethod0()  
    int methodNum = 0;
    
    // This int keeps track of how many methods are generated. When it gets to 200 we create a new findMethodX() and chain it to the previous.
    int methodCount = 0;
    
    sourceWriter.println("private GwtBindingMethod findMethod(String obj, String methodName){ "); 
    sourceWriter.indent();

    sourceWriter.println("GwtBindingMethod newMethod;");
    

    // dummy first condition, rest are "else if". Keeps us from having conditional logic.
    sourceWriter.println("if(obj.equals( \"dummyClass\")){ }");
    
    for(JClassType type : implementingTypes){
    
      // close last method, chain it to a new one.
      if(methodCount > 200){
        sourceWriter.println("return findMethod"+(methodNum) +"(obj, methodName);"); 
        sourceWriter.println("}"); 
        
        sourceWriter.println("private GwtBindingMethod findMethod"+(methodNum++)+"(String obj, String methodName){ "); 
        sourceWriter.println("GwtBindingMethod newMethod;");
        // dummy first condition, rest are "else if". Keeps us from having conditional logic.
        sourceWriter.println("if(obj.equals( \"dummyClass\")){ }");
        
        methodCount = 0;
      }
      
      String keyRoot = generateTypeKey(type);
      
//      if(type.isAbstract()){
//        System.out.println("abstract");
//        continue;
//      }
      
      sourceWriter.println("else if(obj.equals(\""+type.getQualifiedSourceName()+"\")){ ");
      try{
        
        JClassType loopType = type;
        JClassType eventSourceType = typeOracle.getType("org.pentaho.ui.xul.XulEventSource");
        sourceWriter.indent();
      
        // Loop over class heirarchy and generate methods for every object that is declared a XulEventSource
        while(loopType.getSuperclass() != null && loopType.getSimpleSourceName().equals("Object") == false && loopType.isAssignableTo(eventSourceType)){
          String superName = generateTypeKey(loopType);

          // dummy first condition, rest are "else if". Keeps us from having conditional logic.
          sourceWriter.println("if(methodName.equals(\"abcdefg\")){} ");
          
          for(JMethod m : loopType.getMethods()){
            methodCount++;
            if(!m.isPublic()){
              continue;
            }
            
            sourceWriter.println("else if(methodName.equals(\""+m.getName()+"\")){ ");
            sourceWriter.indent();
            
            String methodName = m.getName();
            
            // check to see if we've already processed this classes' method. Point to that class instead.
            if(generatedMethods.contains((superName+"_"+methodName).toLowerCase()) && type != loopType){
              
              sourceWriter.println("return findMethod(\""+superName+"\", methodName);");
              
            } else {
              // See if it's already been created and cached. If so, return that.
              sourceWriter.println("if(wrappedTypes.get((\""+keyRoot+"_"+methodName+"\").toLowerCase()) != null){");
              sourceWriter.indent();
              sourceWriter.println("return wrappedTypes.get((\""+keyRoot+"_"+methodName+"\").toLowerCase());");
              sourceWriter.outdent();
              sourceWriter.println("} else {");
              sourceWriter.indent();
              
              // Not cached, create a new instance and put it in the cache.
              sourceWriter.println("newMethod = new GwtBindingMethod(){");
              
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
    
              sourceWriter.println("}catch(Exception e){ e.printStackTrace(); throw new XulException(\"error with "+type.getQualifiedSourceName()+"\"+e.getMessage());}");
              sourceWriter.println("}");
    
              sourceWriter.outdent();
              sourceWriter.println("};");
              
              // Add it to the HashMap cache as type and decendant type if available.
              
              sourceWriter.println("wrappedTypes.put((\""+keyRoot+"_"+methodName+"\").toLowerCase(), newMethod);");
              sourceWriter.println("wrappedTypes.put((\""+superName+"_"+methodName+"\").toLowerCase(), newMethod);");
              generatedMethods.add((keyRoot+"_"+methodName).toLowerCase());
              generatedMethods.add((superName+"_"+methodName).toLowerCase());

              sourceWriter.println("return newMethod;");

              sourceWriter.outdent();
              sourceWriter.println("}");
            }
            sourceWriter.outdent();
            
            sourceWriter.println("}");
            
          }
          
          // go up a level in the heirarchy and check again.
          loopType = loopType.getSuperclass();
        }          
        sourceWriter.outdent();
        sourceWriter.println("}");
        
        
      } catch (Exception e) {

        // record to logger that Map generation threw an exception 
        logger.log(TreeLogger.ERROR, "PropertyMap ERROR!!!", e);

      }
     
    }
    
    sourceWriter.outdent();
    
    // This is the end of the line, if not found return null.
    sourceWriter.println("return null;");
    sourceWriter.println("}");
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

  
}
