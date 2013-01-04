package org.pentaho.ui.xul.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class DocumentTranslator
{
  private List<ResourceBundle> resourceBundleList = new ArrayList<ResourceBundle>();
  private final Pattern pattern;

  public DocumentTranslator(ResourceBundle bundle)
  {
    this(new ArrayList<ResourceBundle>());
    if (bundle == null)
    {
      throw new NullPointerException();
    }
    resourceBundleList.add(bundle);
  }

  public DocumentTranslator(final List<ResourceBundle> resourceBundleList)
  {
    if (resourceBundleList == null)
    {
      throw new NullPointerException();
    }
    this.resourceBundleList = resourceBundleList;
    this.pattern = Pattern.compile("\\$\\{([^\\}]*)\\}"); //$NON-NLS-1$
  }

  public void process(Document document)
  {
    process(document.getRootElement());
  }

  private String translate(String input)
  {
    Matcher m = pattern.matcher(input);
    StringBuffer sb = new StringBuffer();
    while (m.find())
    {
      String group = m.group(1);
      String translatedValue = getTranslatedValue(group);
      m.appendReplacement(sb, translatedValue);
    }
    m.appendTail(sb);

    return sb.toString();
  }

  private String getTranslatedValue(String group)
  {
    for (int j = 0; j < resourceBundleList.size(); j++)
    {
      try
      {
        final ResourceBundle bundle = resourceBundleList.get(j);
        if (bundle.containsKey(group))
        {
          return bundle.getString(group);
        }
      }
      catch (MissingResourceException mre)
      {
        // ignore
      }

    }
    return group;
  }

  public void process(Element element)
  {
    for (int i = 0, size = element.attributeCount(); i < size; i += 1)
    {
      Attribute attribute = element.attribute(i);
      attribute.setValue(translate(attribute.getValue()));
    }

    for (int i = 0, size = element.nodeCount(); i < size; i++)
    {
      Node node = element.node(i);
      if (node instanceof Element)
      {
        process((Element) node);
      }
    }
  }

}
