/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulBrowser;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtBrowser extends SwtElement implements XulBrowser {

  private static final String EMPTY_URL = "about:blank";

  private Browser browser;
  private Composite mainPanel;
  private boolean showToolbar = true;
  private Text location;
  private ToolBar toolbar;
  private String src = EMPTY_URL;
  private String home;
  private Composite toolbarPanel;

  public SwtBrowser( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );

    Composite parentComposite = (Composite) parent.getManagedObject();

    mainPanel = new Composite( parentComposite, SWT.BORDER );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;

    mainPanel.setLayout( layout );

    setManagedObject( mainPanel );
  }

  public void execute( String data ) {
    browser.execute( data );
  }

  public String getData() {
    return "" + browser.getData();
  }

  public String getSrc() {
    return browser.getUrl();
  }

  public void setSrc( String src ) {
    if ( StringUtils.isEmpty( src ) ) {
      src = EMPTY_URL;
    }
    if ( browser != null && !browser.isDisposed() ) {
      browser.setUrl( src );
      location.setText( src );
    }
    this.src = src;
    if ( home == null ) {
      home = src;
    }
  }

  public void back() {
    browser.back();
  }

  public void forward() {
    browser.forward();
  }

  public void home() {
    browser.setUrl( home );
  }

  public void reload() {
    browser.refresh();
  }

  public void stop() {
    browser.stop();
  }

  @Override
  public void layout() {

    toolbarPanel = new Composite( mainPanel, SWT.BORDER );
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    toolbarPanel.setLayoutData( data );

    GridLayout layout = new GridLayout( 3, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    toolbarPanel.setLayout( layout );

    toolbar = new ToolBar( toolbarPanel, SWT.FLAT | SWT.HORIZONTAL );
    ToolItem item = new ToolItem( toolbar, SWT.PUSH );
    item.setText( "<" );
    item.addSelectionListener( new SelectionListener() {
      public void widgetDefaultSelected( SelectionEvent se ) {
      }

      public void widgetSelected( SelectionEvent se ) {
        back();
      }
    } );
    data = new GridData();
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = false;
    toolbar.setLayoutData( data );

    item = new ToolItem( toolbar, SWT.PUSH );
    item.setText( ">" );
    item.addSelectionListener( new SelectionListener() {
      public void widgetDefaultSelected( SelectionEvent se ) {
      }

      public void widgetSelected( SelectionEvent se ) {
        forward();
      }
    } );

    item = new ToolItem( toolbar, SWT.PUSH );
    item.setText( "R" );
    item.addSelectionListener( new SelectionListener() {
      public void widgetDefaultSelected( SelectionEvent se ) {
      }

      public void widgetSelected( SelectionEvent se ) {
        reload();
      }
    } );

    location = new Text( toolbarPanel, SWT.BORDER );
    location.setText( src );
    location.addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( KeyEvent arg0 ) {
        if ( arg0.character == SWT.CR ) {
          setSrc( location.getText() );
        }
      }
    } );

    data = new GridData();
    data.horizontalSpan = 2;
    data.grabExcessHorizontalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    location.setLayoutData( data );

    browser = createBrowser( mainPanel );
    hookupBrowserListeners( browser, false );

    browser.addCloseWindowListener( new CloseWindowListener() {
      public void close( WindowEvent event ) {
        Browser browser = (Browser) event.widget;
        Shell shell = browser.getShell();
        shell.close();
      }
    } );

    browser.setUrl( src );

    data = new GridData();
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    data.verticalAlignment = SWT.FILL;
    data.horizontalAlignment = SWT.FILL;
    browser.setLayoutData( data );

    browser.addLocationListener( new LocationListener() {
      public void changed( LocationEvent arg0 ) {
        SwtBrowser.this.src = arg0.location;
        location.setText( arg0.location );
      }

      public void changing( LocationEvent arg0 ) {
      }
    } );

    setShowtoolbar( getShowtoolbar() );
    mainPanel.layout( true );
  }

  private void hookupBrowserListeners( final Browser browser, final boolean visibilityListeners ) {
    browser.addOpenWindowListener( new OpenWindowListener() {
      public void open( WindowEvent event ) {
        Shell shell = new Shell( browser.getShell() );
        shell.setText( "" );
        shell.setLayout( new FillLayout() );
        Browser newBrowser = new Browser( shell, SWT.NONE );
        hookupBrowserListeners( newBrowser, true );
        event.browser = newBrowser;
      }
    } );
    if ( !visibilityListeners ) {
      return;
    }
    browser.addVisibilityWindowListener( new VisibilityWindowListener() {
      public void hide( WindowEvent event ) {
        Browser b = (Browser) event.widget;
        Shell shell = b.getShell();
        shell.setVisible( false );
      }

      public void show( WindowEvent event ) {
        Browser b = (Browser) event.widget;
        final Shell shell = b.getShell();
        if ( event.location != null ) {
          shell.setLocation( event.location );
        }
        if ( event.size != null ) {
          Point size = event.size;
          shell.setSize( shell.computeSize( size.x, size.y ) );
        }
        if ( !"webkit".equals( b.getBrowserType() ) || event.addressBar ) { // for some reason we're getting
                                                                            // double-events for safari window.open
                                                                            // clicks. The second is the one we want and
                                                                            // it has this property
          shell.open();
        }
      }
    } );
  }

  protected Browser createBrowser( Composite parent ) {
    // Force *nix (like CentOS) to use Mozilla
    Browser browser = new Browser( parent, ( isCentOS() ? SWT.MOZILLA : SWT.NONE ) );
    browser.setUrl( EMPTY_URL );
    return browser;
  }

  public boolean getShowtoolbar() {
    return showToolbar;
  }

  public void setShowtoolbar( boolean flag ) {
    this.showToolbar = flag;
    if ( toolbarPanel != null ) {
      toolbarPanel.setVisible( flag );
      ( (GridData) toolbarPanel.getLayoutData() ).exclude = !showToolbar;
      mainPanel.layout( true );
    }
  }

  public Browser getBrowser() {
    return browser;
  }

  private static boolean isCentOS() {
    String os = System.getProperty( "os.name" ).toLowerCase();
    String osVersion = System.getProperty( "os.version" ).toLowerCase();

    // linux or unix
    return ( os.indexOf( "nux" ) >= 0 && osVersion.contains( "centos" ) );

  }
}
