package org.pentaho.ui.xul.gwt;


import java.util.ArrayList;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.i18n.IResourceBundleLoadCallback;
import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulSettingsManager;
import org.pentaho.ui.xul.gwt.service.XulLoaderService;
import org.pentaho.ui.xul.gwt.service.XulLoaderServiceAsync;
import org.pentaho.ui.xul.gwt.tags.*;
import org.pentaho.ui.xul.gwt.util.ResourceBundleTranslator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author nbaker
 *
 */
public class GwtXulLoader implements IResourceBundleLoadCallback, XulLoader {

  private GwtXulParser parser;
  private XulSettingsManager settings;
  public static final XulLoaderServiceAsync SERVICE = (XulLoaderServiceAsync) GWT.create(XulLoaderService.class);
  static{
    ServiceDefTarget endpoint = (ServiceDefTarget) SERVICE;
    String moduleRelativeURL = GWT.getModuleBaseURL() + "XulLoaderService"; //$NON-NLS-1$
    endpoint.setServiceEntryPoint(moduleRelativeURL);
  }
  public GwtXulLoader() throws XulException{
    
    try{
      parser = new GwtXulParser();
    } catch(Exception e){
      throw new XulException("Error getting XulParser Instance, probably a DOM Factory problem: "+e.getMessage(), e);
    }
     
    // attach registers
    GwtBox.register();
    GwtButton.register();
    GwtCaption.register();
    GwtCheckbox.register();
    GwtRadio.register();
    GwtDeck.register();
    GwtGroupBox.register();
    GwtRadioGroup.register();
    GwtHbox.register();
    GwtImage.register();
    GwtLabel.register();
    GwtListbox.register();
    GwtListitem.register();
    GwtScript.register();
    GwtSpacer.register();
    GwtTextbox.register();
    GwtVbox.register();
    GwtWindow.register();
    GwtToolbar.register();
    GwtToolbarseparator.register();
    GwtToolbarspacer.register();
    GwtToolbarset.register();
    GwtToolbarbutton.register();
    GwtOverlay.register();
    GwtTree.register();
    GwtTreeCols.register();
    GwtTreeCol.register();
    GwtTreeChildren.register();
    GwtTreeItem.register();
    GwtTreeRow.register();
    GwtTreeRow.register();
    GwtTreeCell.register();
    GwtDialog.register();
    GwtMenuList.register();
    GwtMenubar.register();
    GwtMenubarSeparator.register();
    GwtMenuitem.register();
    GwtMenupopup.register();
    GwtMessageBox.register();
    GwtPromptBox.register();
    GwtConfirmBox.register();
    GwtScrollbox.register();
    GwtColumn.register();
    GwtColumns.register();
    GwtRow.register();
    GwtRows.register();
    GwtGrid.register();
    GwtFileDialog.register();
    GwtFileUpload.register();
    GwtTab.register();
    GwtTabs.register();
    GwtTabbox.register();
    GwtTabPanels.register();
    GwtTabPanel.register();
    GwtExpandPanel.register();
    GwtEditPanel.register();
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public GwtXulDomContainer loadXul(Object xulDocument) throws IllegalArgumentException, XulException{

    Document document = (Document)xulDocument;
    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager(settings);
    container.setLoader(this);
    
    // We don't want to re-use the old parser as this will cause issues.
    GwtXulParser anotherParser = new GwtXulParser();
    anotherParser.setContainer(container);
    anotherParser.parseDocument(document.getDocumentElement());
   
    return container;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public GwtXulDomContainer loadXul(Object xulDocument, Object bundle) throws IllegalArgumentException, XulException{
    Document document = (Document)xulDocument;
    String translated = ResourceBundleTranslator.translate(xulDocument.toString(), (ResourceBundle)bundle);
    document = XMLParser.parse(translated);
    
    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager(settings);
    container.setLoader(this);
    List<Object> resourceBundles = new ArrayList<Object>();
    resourceBundles.add(bundle);
    container.setResourceBundles(resourceBundles);
    parser.setContainer(container);
    parser.parseDocument(document.getDocumentElement());
   
    return container;
  }


  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment(Object xulDocument) throws IllegalArgumentException, XulException {
    Document document = (Document)xulDocument;
    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager(settings);
    container.setLoader(this);
    parser.setContainer(container);
    parser.parseDocument(document.getDocumentElement());
    
    return container;
  }

  public XulComponent createElement(String elementName) throws XulException {
    return parser.getElement(elementName, null);
  }

  public GwtXulLoader getNewInstance() throws XulException {
    return null;
  }

  public String getRootDir() {
    return "";  
  }

  public boolean isRegistered(String elementName) {
    return GwtXulParser.isRegistered(elementName);
  }

  public void bundleLoaded(String bundleName) {
    
  }

  public XulDomContainer loadXul(String resource) throws IllegalArgumentException, XulException {
    throw new RuntimeException("not yet implemented");
  }

  public XulDomContainer loadXul(String resource, Object bundle) throws XulException {
    throw new RuntimeException("not yet implemented");
  }

  public XulDomContainer loadXulFragment(String resource) throws IllegalArgumentException, XulException {
    throw new RuntimeException("not yet implemented");
  }

  public XulDomContainer loadXulFragment(String resource, Object bundle) throws XulException {
    throw new RuntimeException("not yet implemented");
  }

  public void processOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container) throws XulException {
    throw new RuntimeException("not yet implemented");
    
  }
  
  

  public void processOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container, Object resourceBundle) throws XulException {
    throw new RuntimeException("not yet implemented");
      
  }

  public void register(String tagName, String className) {
    throw new RuntimeException("not yet implemented");
    
  }

  public void removeOverlay(String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument, XulDomContainer container)
      throws XulException {
    // TODO Auto-generated method stub
    
  }

  public void setOuterContext(Object context) {
    throw new RuntimeException("not yet implemented");
    
  }

  public void setRootDir(String str) {
    throw new RuntimeException("not yet implemented");
    
  }

  public Object getResourceAsStream(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  public void registerClassLoader(Object loader) {
    // TODO Auto-generated method stub
    
  }

  public XulSettingsManager getSettingsManager() {
    return settings;
  }

  public void setSettingsManager(XulSettingsManager settings) {
    this.settings = settings;
    
  }
  
  
  
  
}


