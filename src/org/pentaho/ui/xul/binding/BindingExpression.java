package org.pentaho.ui.xul.binding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BindingExpression {
  private static final String regEx = "([^=]*)=([^\\.]*)\\.(.*)";
  
  public String sourceAttr;
  public String target;
  public String targetAttr;
  
  private BindingExpression(){
    
  }
  public static BindingExpression parse(String str){
    if(!str.matches(regEx)){
      throw new IllegalArgumentException("Binding Expression invalid");
    }
    Pattern pat = Pattern.compile(regEx);

    Matcher m = pat.matcher(str);
    m.find();
 
    BindingExpression expr = new BindingExpression();
    expr.sourceAttr = m.group(1).trim();
    expr.target = m.group(2).trim();
    expr.targetAttr = m.group(3).trim();
    
    System.out.println(String.format("SourceAttr %s \nTarget: %s \nTargetAttr: %s",expr.sourceAttr, expr.target, expr.targetAttr));
       
    return expr;
  }
  
  public static void main(String[] args){
    BindingExpression.parse("foo = bar.enabled");
  }
}

  