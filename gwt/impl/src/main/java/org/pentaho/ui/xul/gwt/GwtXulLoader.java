/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

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
import org.pentaho.ui.xul.gwt.tags.GwtBox;
import org.pentaho.ui.xul.gwt.tags.GwtButton;
import org.pentaho.ui.xul.gwt.tags.GwtCaption;
import org.pentaho.ui.xul.gwt.tags.GwtCheckbox;
import org.pentaho.ui.xul.gwt.tags.GwtColumn;
import org.pentaho.ui.xul.gwt.tags.GwtColumns;
import org.pentaho.ui.xul.gwt.tags.GwtConfirmBox;
import org.pentaho.ui.xul.gwt.tags.GwtDeck;
import org.pentaho.ui.xul.gwt.tags.GwtDialog;
import org.pentaho.ui.xul.gwt.tags.GwtEditPanel;
import org.pentaho.ui.xul.gwt.tags.GwtExpandPanel;
import org.pentaho.ui.xul.gwt.tags.GwtFileDialog;
import org.pentaho.ui.xul.gwt.tags.GwtFileUpload;
import org.pentaho.ui.xul.gwt.tags.GwtGrid;
import org.pentaho.ui.xul.gwt.tags.GwtGroupBox;
import org.pentaho.ui.xul.gwt.tags.GwtHbox;
import org.pentaho.ui.xul.gwt.tags.GwtImage;
import org.pentaho.ui.xul.gwt.tags.GwtLabel;
import org.pentaho.ui.xul.gwt.tags.GwtListbox;
import org.pentaho.ui.xul.gwt.tags.GwtListitem;
import org.pentaho.ui.xul.gwt.tags.GwtMenuList;
import org.pentaho.ui.xul.gwt.tags.GwtMenubar;
import org.pentaho.ui.xul.gwt.tags.GwtMenubarSeparator;
import org.pentaho.ui.xul.gwt.tags.GwtMenuitem;
import org.pentaho.ui.xul.gwt.tags.GwtMenupopup;
import org.pentaho.ui.xul.gwt.tags.GwtMenuSeparator;
import org.pentaho.ui.xul.gwt.tags.GwtMessageBox;
import org.pentaho.ui.xul.gwt.tags.GwtOverlay;
import org.pentaho.ui.xul.gwt.tags.GwtPromptBox;
import org.pentaho.ui.xul.gwt.tags.GwtRadio;
import org.pentaho.ui.xul.gwt.tags.GwtRadioGroup;
import org.pentaho.ui.xul.gwt.tags.GwtRow;
import org.pentaho.ui.xul.gwt.tags.GwtRows;
import org.pentaho.ui.xul.gwt.tags.GwtScript;
import org.pentaho.ui.xul.gwt.tags.GwtScrollbox;
import org.pentaho.ui.xul.gwt.tags.GwtSpacer;
import org.pentaho.ui.xul.gwt.tags.GwtTab;
import org.pentaho.ui.xul.gwt.tags.GwtTabPanel;
import org.pentaho.ui.xul.gwt.tags.GwtTabPanels;
import org.pentaho.ui.xul.gwt.tags.GwtTabbox;
import org.pentaho.ui.xul.gwt.tags.GwtTabs;
import org.pentaho.ui.xul.gwt.tags.GwtTextbox;
import org.pentaho.ui.xul.gwt.tags.GwtToolbar;
import org.pentaho.ui.xul.gwt.tags.GwtToolbarbutton;
import org.pentaho.ui.xul.gwt.tags.GwtToolbarseparator;
import org.pentaho.ui.xul.gwt.tags.GwtToolbarset;
import org.pentaho.ui.xul.gwt.tags.GwtToolbarspacer;
import org.pentaho.ui.xul.gwt.tags.GwtTree;
import org.pentaho.ui.xul.gwt.tags.GwtTreeCell;
import org.pentaho.ui.xul.gwt.tags.GwtTreeChildren;
import org.pentaho.ui.xul.gwt.tags.GwtTreeCol;
import org.pentaho.ui.xul.gwt.tags.GwtTreeCols;
import org.pentaho.ui.xul.gwt.tags.GwtTreeItem;
import org.pentaho.ui.xul.gwt.tags.GwtTreeRow;
import org.pentaho.ui.xul.gwt.tags.GwtVbox;
import org.pentaho.ui.xul.gwt.tags.GwtWindow;
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
  public static final XulLoaderServiceAsync SERVICE = (XulLoaderServiceAsync) GWT.create( XulLoaderService.class );
  static {
    ServiceDefTarget endpoint = (ServiceDefTarget) SERVICE;
    String moduleRelativeURL = GWT.getModuleBaseURL() + "XulLoaderService"; //$NON-NLS-1$
    endpoint.setServiceEntryPoint( moduleRelativeURL );
  }

  public GwtXulLoader() throws XulException {

    try {
      parser = new GwtXulParser();
    } catch ( Exception e ) {
      throw new XulException( "Error getting XulParser Instance, probably a DOM Factory problem: "
        + e.getMessage(), e );
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
    GwtMenuSeparator.register();
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

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public GwtXulDomContainer loadXul( Object xulDocument ) throws IllegalArgumentException, XulException {

    Document document = (Document) xulDocument;
    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager( settings );
    container.setLoader( this );

    // We don't want to re-use the old parser as this will cause issues.
    GwtXulParser anotherParser = new GwtXulParser();
    anotherParser.setContainer( container );
    anotherParser.parseDocument( document.getDocumentElement() );

    return container;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public GwtXulDomContainer loadXul( Object xulDocument, Object bundle ) throws IllegalArgumentException, XulException {
    Document document = (Document) xulDocument;
    String translated = ResourceBundleTranslator.translate( xulDocument.toString(), (ResourceBundle) bundle );
    document = XMLParser.parse( translated );

    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager( settings );
    container.setLoader( this );
    List<Object> resourceBundles = new ArrayList<Object>();
    resourceBundles.add( bundle );
    container.setResourceBundles( resourceBundles );
    parser.setContainer( container );
    parser.parseDocument( document.getDocumentElement() );

    return container;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.XulLoader#loadXulFragment(org.dom4j.Document)
   */
  public XulDomContainer loadXulFragment( Object xulDocument ) throws IllegalArgumentException, XulException {
    Document document = (Document) xulDocument;
    GwtXulDomContainer container = new GwtXulDomContainer();
    container.setSettingsManager( settings );
    container.setLoader( this );
    parser.setContainer( container );
    parser.parseDocument( document.getDocumentElement() );

    return container;
  }

  public XulComponent createElement( String elementName ) throws XulException {
    return parser.getElement( elementName, null );
  }

  public GwtXulLoader getNewInstance() throws XulException {
    return null;
  }

  public String getRootDir() {
    return "";
  }

  public boolean isRegistered( String elementName ) {
    return GwtXulParser.isRegistered( elementName );
  }

  public void bundleLoaded( String bundleName ) {

  }

  public XulDomContainer loadXul( String resource ) throws IllegalArgumentException, XulException {
    throw new RuntimeException( "not yet implemented" );
  }

  public XulDomContainer loadXul( String resource, Object bundle ) throws XulException {
    throw new RuntimeException( "not yet implemented" );
  }

  public XulDomContainer loadXulFragment( String resource ) throws IllegalArgumentException, XulException {
    throw new RuntimeException( "not yet implemented" );
  }

  public XulDomContainer loadXulFragment( String resource, Object bundle ) throws XulException {
    throw new RuntimeException( "not yet implemented" );
  }

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException {
    throw new RuntimeException( "not yet implemented" );

  }

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container, Object resourceBundle ) throws XulException {
    throw new RuntimeException( "not yet implemented" );

  }

  public void register( String tagName, String className ) {
    throw new RuntimeException( "not yet implemented" );

  }

  public void removeOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException {
    // TODO Auto-generated method stub

  }

  public void setOuterContext( Object context ) {
    throw new RuntimeException( "not yet implemented" );

  }

  public void setRootDir( String str ) {
    throw new RuntimeException( "not yet implemented" );

  }

  public Object getResourceAsStream( String name ) {
    // TODO Auto-generated method stub
    return null;
  }

  public void registerClassLoader( Object loader ) {
    // TODO Auto-generated method stub

  }

  public void deRegisterClassLoader( Object loader ) {
    // TODO Auto-generated method stub

  }
  public XulSettingsManager getSettingsManager() {
    return settings;
  }

  public void setSettingsManager( XulSettingsManager settings ) {
    this.settings = settings;

  }

}
