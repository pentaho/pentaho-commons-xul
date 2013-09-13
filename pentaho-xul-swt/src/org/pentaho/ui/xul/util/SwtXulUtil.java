package org.pentaho.ui.xul.util;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableColumnLabelProvider;

public class SwtXulUtil {

  private static Log logger = LogFactory.getLog(SwtXulUtil.class);

  public static Image getCachedImage(String src, XulDomContainer container, Display display){

    Image img = null;
    
    if (src == null){
      return null;
    }
    
    if (JFaceResources.getImageRegistry().getDescriptor(src) != null){
      img =  JFaceResources.getImageRegistry().get(src);
    }
    
    if (img == null){
      InputStream in = null;
      try{
        in = XulUtil.loadResourceAsStream(src, container);
        if (in != null){
          img = new Image(display, in);
          JFaceResources.getImageRegistry().put(src, img);
        }
      } catch (Exception e){
        logger.error(e);
      } finally{
        try{
          in.close();
        } catch(Exception ignored){}
      }
    }
    
    return img;
  }
    
}
