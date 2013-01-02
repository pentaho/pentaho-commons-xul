package org.pentaho.ui.xul.impl;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class UpperCaseIDWorker
{
  public UpperCaseIDWorker()
  {
  }

  public void process(Document document)
  {
    process(document.getRootElement());
  }

  public void process(Element element)
  {
    Attribute id = element.attribute("id");
    if (id != null)
    {
      String value = id.getValue();
      element.remove(id);
      element.addAttribute("ID", value);
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
