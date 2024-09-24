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

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.dialogs.DialogBox;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GenericDialog;
import org.pentaho.ui.xul.util.Orient;

import java.util.ArrayList;
import java.util.List;

public class GwtDialog extends GenericDialog implements XulDialog {

  public static void register() {
    GwtXulParser.registerHandler( "dialog", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtDialog();
      }
    } );
  }

  // region Enums
  public enum Property {
    RESPONSIVE, SIZINGMODE, WIDTHCATEGORY, MINIMUMHEIGHTCATEGORY;
  }

  public enum SizingMode {
    SIZETOCONTENT, FILLVIEWPORTWIDTH, FILLVIEWPORT, FULLVIEWPORT;

    DialogBox.DialogSizingMode getGwtValue() {
      switch ( this ) {
        case SIZETOCONTENT: return DialogBox.DialogSizingMode.SIZE_TO_CONTENT;
        case FILLVIEWPORTWIDTH: return DialogBox.DialogSizingMode.FILL_VIEWPORT_WIDTH;
        case FILLVIEWPORT: return DialogBox.DialogSizingMode.FILL_VIEWPORT;
        case FULLVIEWPORT: return DialogBox.DialogSizingMode.FULL_VIEWPORT;
      }

      throw new IllegalArgumentException( "Undefined sizing mode value" );
    }
  }

  public enum WidthCategory {
    MINIMUM, TEXT, EXTRASMALL, SMALL, MEDIUM, LARGE, EXTRALARGE, MAXIMUM;

    DialogBox.DialogWidthCategory getGwtValue() {
      switch ( this ) {
        case MINIMUM: return DialogBox.DialogWidthCategory.MINIMUM;
        case TEXT: return DialogBox.DialogWidthCategory.TEXT;
        case EXTRASMALL: return DialogBox.DialogWidthCategory.EXTRA_SMALL;
        case SMALL: return DialogBox.DialogWidthCategory.SMALL;
        case MEDIUM: return DialogBox.DialogWidthCategory.MEDIUM;
        case LARGE: return DialogBox.DialogWidthCategory.LARGE;
        case EXTRALARGE: return DialogBox.DialogWidthCategory.EXTRA_LARGE;
        case MAXIMUM: return DialogBox.DialogWidthCategory.MAXIMUM;
      }

      throw new IllegalArgumentException( "Undefined width category value" );
    }
  }

  public enum MinimumHeightCategory {
    CONTENT, MINIMUM;

    DialogBox.DialogMinimumHeightCategory getGwtValue() {
      switch ( this ) {
        case CONTENT: return DialogBox.DialogMinimumHeightCategory.CONTENT;
        case MINIMUM: return DialogBox.DialogMinimumHeightCategory.MINIMUM;
      }

      throw new IllegalArgumentException( "Undefined minimum height category value" );
    }
  }
  // endregion

  /**
   * Standard GWT <code>dialog</code> tag button identifiers in order of
   * most to least impactful.
   *
   * @see #getFocusButtonWidgets()
   */
  private static final String[] BUTTON_VALUES_AUTO_FOCUS = new String[] { "cancel", "accept", "extra1", "extra2" };

  private XulDomContainer xulContainer;
  private String bgColor = null;
  private List<XulButton> dialogButtons = new ArrayList<XulButton>();
  private boolean pack;
  private boolean responsive;
  private SizingMode sizingMode;
  private WidthCategory widthCategory;
  private MinimumHeightCategory minimumHeightCategory;

  public GwtDialog() {
    super( "dialog" );

    setManagedObject( null );
    this.orientation = Orient.VERTICAL;
    this.setResponsive( false );
    this.setSizingMode( (SizingMode) null );
    this.setWidthCategory( (WidthCategory) null );
    this.setMinimumHeightCategory( (MinimumHeightCategory) null );
  }

  // we don't add ourselves to the main screen
  private boolean preventLayout = false;

  // Called by the GwtXulParser, when processing a dialog tag:
  // 1. Creates the element by calling above's GwtXulHandler#newInstance().
  // 2. Calls #init(...) on the new element.
  // 3. Processes each child element and adds it via #addChild( . ).
  // 4. Calls #layout().
  public void layout() {
    if ( preventLayout ) {
      return;
    } else {
      preventLayout = true;
    }
    String buttons = getButtons();
    if ( buttons != null && buttons.trim().length() > 0 ) {

      String[] buttonStr = buttons.split( "," );
      for ( String button : buttonStr ) {
        try {
          XulButton buttonObj = createXulButton( button.trim() );
          this.addChild( buttonObj );
          dialogButtons.add( buttonObj );
        } catch ( XulException e ) {
          System.out.println( "Error creating button: " + e.getMessage() );
          e.printStackTrace();
        }
      }
    }

    preventLayout = false;
  }

  private XulButton createXulButton( String buttonValue ) throws XulException {
    XulButton buttonObj = (XulButton) this.xulContainer.getDocumentRoot().createElement( "button" );

    buttonObj.setLabel( getAttributeValue( "buttonlabel" + buttonValue ) );
    buttonObj.setTooltiptext( getAttributeValue( "buttonlabel" + buttonValue + "tooltiptext" ) );
    buttonObj.setOnclick( getAttributeValue( "ondialog" + buttonValue ) );
    buttonObj.setId( buildButtonId( buttonValue ) );
    buttonObj.setAlign( getAttributeValue( "pen:" + buttonValue + "buttonalign" ) );

    return buttonObj;
  }

  private String buildButtonId( String buttonValue ) {
    return this.getId() + "_" + buttonValue;
  }

  @Override
  public Panel getButtonPanel() {
    if ( dialogButtons.isEmpty() ) {
      return new SimplePanel();
    }
    // Check for individual button alignment. If the main align properties is set on the Dialog box then all the
    // individual alignment for the button will be ignored
    boolean ignoreIndividualButtonAlign = false;
    String buttonalign = getButtonalign();
    if ( !StringUtils.isEmpty( buttonalign ) ) {
      ignoreIndividualButtonAlign = true;
    }

    HorizontalPanel buttonPanel = new HorizontalFlexPanel();
    HorizontalPanel leftButtonPanel = new HorizontalFlexPanel();
    HorizontalPanel centerButtonPanel = new HorizontalFlexPanel();
    HorizontalPanel rightButtonPanel = new HorizontalFlexPanel();
    rightButtonPanel.addStyleName( "buttonTable" );
    centerButtonPanel.addStyleName( "buttonTable" );
    leftButtonPanel.addStyleName( "buttonTable" );

    // keep track of the number in the left and right button cells. If they're not the same, add shims to fix the
    // center
    // balance

    for ( XulButton btn : dialogButtons ) {
      this.removeChild( btn );
      Widget widget = (Widget) btn.getManagedObject();
      if ( !ignoreIndividualButtonAlign ) {
        String align = btn.getAlign();
        if ( !StringUtils.isEmpty( align ) ) {
          if ( "center".equals( align ) ) { //$NON-NLS-1$
            centerButtonPanel.add( widget );
          } else if ( "left".equals( align ) ) { //$NON-NLS-1$
            leftButtonPanel.add( widget );
          } else {
            // default to right
            rightButtonPanel.add( widget );
          }
        } else {
          rightButtonPanel.add( widget );
        }
      } else {
        if ( "center".equals( buttonalign ) ) { //$NON-NLS-1$
          centerButtonPanel.add( widget );
        } else if ( "left".equals( buttonalign ) ) { //$NON-NLS-1$
          leftButtonPanel.add( widget );
        } else {
          // default to right
          rightButtonPanel.add( widget );
        }
      }
    }

    buttonPanel.add( leftButtonPanel );
    buttonPanel.setCellHorizontalAlignment( leftButtonPanel, HorizontalPanel.ALIGN_LEFT );
    buttonPanel.setCellWidth( leftButtonPanel, "33%" );
    buttonPanel.add( centerButtonPanel );
    buttonPanel.setCellHorizontalAlignment( centerButtonPanel, HorizontalPanel.ALIGN_CENTER );
    buttonPanel.setCellWidth( centerButtonPanel, "33%" );
    buttonPanel.add( rightButtonPanel );
    buttonPanel.setCellHorizontalAlignment( rightButtonPanel, HorizontalPanel.ALIGN_RIGHT );
    buttonPanel.setCellWidth( leftButtonPanel, "33%" );
    buttonPanel.setWidth( "100%" );

    return buttonPanel;
  }

  @Override
  public Panel getDialogContents() {
    VerticalPanel contentPanel = GwtVbox.createManagedPanel( 0 );
    container = contentPanel;
    return contentPanel;
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    this.xulContainer = container;
    setButtons( srcEle.getAttribute( "buttons" ) );
    setOndialogaccept( srcEle.getAttribute( "ondialogaccept" ) );
    setOndialogcancel( srcEle.getAttribute( "ondialogcancel" ) );
    setOndialogextra1( srcEle.getAttribute( "ondialogextra1" ) );
    setOndialogextra2( srcEle.getAttribute( "ondialogextra2" ) );

    setButtonlabelaccept( srcEle.getAttribute( "buttonlabelaccept" ) );
    setButtonlabelcancel( srcEle.getAttribute( "buttonlabelcancel" ) );
    setButtonlabelextra1( srcEle.getAttribute( "buttonlabelextra1" ) );
    setButtonlabelextra2( srcEle.getAttribute( "buttonlabelextra2" ) );

    setButtonlabelacceptTooltiptext( srcEle.getAttribute( "pen:buttonlabelaccepttooltiptext" ) );
    setButtonlabelcancelTooltiptext( srcEle.getAttribute( "pen:buttonlabelcanceltooltiptext" ) );
    setButtonlabelextra1Tooltiptext( srcEle.getAttribute( "pen:buttonlabelextra1tooltiptext" ) );
    setButtonlabelextra2Tooltiptext( srcEle.getAttribute( "pen:buttonlabelextra2tooltiptext" ) );

    setButtonalign( srcEle.getAttribute( "buttonalign" ) );

    setAcceptbuttonalign( srcEle.getAttribute( "pen:acceptbuttonalign" ) );
    setCancelbuttonalign( srcEle.getAttribute( "pen:cancelbuttonalign" ) );
    setExtra1buttonalign( srcEle.getAttribute( "pen:extra1buttonalign" ) );
    setExtra2buttonalign( srcEle.getAttribute( "pen:extra2buttonalign" ) );

    setTitle( srcEle.getAttribute( "title" ) );
    setOnclose( srcEle.getAttribute( "onclose" ) );
    setOnload( srcEle.getAttribute( "onload" ) );
    setOnunload( srcEle.getAttribute( "onunload" ) );
  }

  public String getButtonalign() {
    return getAttributeValue( "buttonalign" );
  }

  public String getButtonlabelaccept() {
    return getAttributeValue( "buttonlabelaccept" );
  }

  public String getButtonlabelcancel() {
    return getAttributeValue( "buttonlabelcancel" );
  }

  public String getButtonlabelextra1() {
    return getAttributeValue( "buttonlabelextra1" );
  }

  public String getButtonlabelextra2() {
    return getAttributeValue( "buttonlabelextra2" );
  }

  public String getAcceptbuttonalign() {
    return getAttributeValue( "pen:acceptbuttonalign" );
  }

  public String getCancelbuttonalign() {
    return getAttributeValue( "pen:cancelbuttonalign" );
  }

  public String getExtra1buttonalign() {
    return getAttributeValue( "pen:extra1buttonalign" );
  }

  public String getExtra2buttonalign() {
    return getAttributeValue( "pen:extra2buttonalign" );
  }

  public String getButtonlabelacceptTooltipText() {
    return getAttributeValue( "buttonlabelaccepttooltiptext" );
  }

  public String getButtonlabelcancelTooltipText() {
    return getAttributeValue( "buttonlabelcanceltooltiptext" );
  }

  public String getButtonlabelextra1TooltipText() {
    return getAttributeValue( "buttonlabelextra1tooltiptext" );
  }

  public String getButtonlabelextra2TooltipText() {
    return getAttributeValue( "buttonlabelextra2tooltiptext" );
  }

  public String getButtons() {
    return getAttributeValue( "buttons" );
  }

  public String getOndialogaccept() {
    return getAttributeValue( "ondialogaccept" );
  }

  public String getOndialogcancel() {
    return getAttributeValue( "ondialogcancel" );
  }

  public String getOndialogextra1() {
    return getAttributeValue( "ondialogextra1" );
  }

  public String getOndialogextra2() {
    return getAttributeValue( "ondialogextra2" );
  }

  public void setButtonalign( String align ) {
    this.setAttribute( "buttonalign", align );
  }

  public void setAcceptbuttonalign( String align ) {
    this.setAttribute( "pen:acceptbuttonalign", align );
  }

  public void setCancelbuttonalign( String align ) {
    this.setAttribute( "pen:cancelbuttonalign", align );
  }

  public void setExtra1buttonalign( String align ) {
    this.setAttribute( "pen:extra1buttonalign", align );
  }

  public void setExtra2buttonalign( String align ) {
    this.setAttribute( "pen:extra2buttonalign", align );
  }

  public void setButtonlabelaccept( String label ) {
    this.setAttribute( "buttonlabelaccept", label );
  }

  public void setButtonlabelacceptTooltiptext( String tooltip ) {
    this.setAttribute( "buttonlabelaccepttooltiptext", tooltip );
  }

  public void setButtonlabelcancel( String label ) {
    this.setAttribute( "buttonlabelcancel", label );
  }

  public void setButtonlabelcancelTooltiptext( String tooltip ) {
    this.setAttribute( "buttonlabelcanceltooltiptext", tooltip );
  }

  public void setButtonlabelextra1( String label ) {
    this.setAttribute( "buttonlabelextra1", label );
  }

  public void setButtonlabelextra1Tooltiptext( String tooltip ) {
    this.setAttribute( "buttonlabelextra1tooltiptext", tooltip );
  }

  public void setButtonlabelextra2( String label ) {
    this.setAttribute( "buttonlabelextra2", label );
  }

  public void setButtonlabelextra2Tooltiptext( String tooltip ) {
    this.setAttribute( "buttonlabelextra2tooltiptext", tooltip );
  }

  public void setButtons( String buttons ) {
    this.setAttribute( "buttons", buttons );
  }

  public void setOndialogaccept( String command ) {
    this.setAttribute( "ondialogaccept", command );
  }

  public void setOndialogcancel( String command ) {
    this.setAttribute( "ondialogcancel", command );
  }

  public void setOndialogextra1( String command ) {
    this.setAttribute( "ondialogextra1", command );
  }

  public void setOndialogextra2( String command ) {
    this.setAttribute( "ondialogextra2", command );
  }

  public void setResponsive( boolean responsive ) {
    this.responsive = responsive;
  }

  public void setSizingMode( String sizingMode ) {
    setSizingMode( StringUtils.isEmpty( sizingMode ) ? null : SizingMode.valueOf( sizingMode.toUpperCase() ) );
  }

  public void setSizingMode( SizingMode sizingMode ) {
    this.sizingMode = sizingMode != null ? sizingMode : SizingMode.SIZETOCONTENT;
  }

  public void setWidthCategory( String widthCategory ) {
    setWidthCategory( StringUtils.isEmpty( widthCategory ) ? null : WidthCategory.valueOf( widthCategory.toUpperCase() ) );
  }

  public void setWidthCategory( WidthCategory widthCategory ) {
    this.widthCategory = widthCategory != null ? widthCategory : WidthCategory.MAXIMUM;
  }

  public void setMinimumHeightCategory( String minimumHeightCategory ) {
    setMinimumHeightCategory( StringUtils.isEmpty( minimumHeightCategory ) ? null : MinimumHeightCategory.valueOf( minimumHeightCategory.toUpperCase() ) );
  }

  public void setMinimumHeightCategory( MinimumHeightCategory minimumHeightCategory ) {
    this.minimumHeightCategory = minimumHeightCategory != null ? minimumHeightCategory : MinimumHeightCategory.MINIMUM;
  }

  public void setVisible( boolean visible ) {
    if ( isVisible() == visible ) {
      return;
    }
    super.setVisible( visible );

    if ( visible ) {
      show();
    } else {
      hide();
    }
  }

  @Override
  public void setAttribute( String name, String value ) {
    Property prop;
    try {
      prop = Property.valueOf( getAttributeEnumName( name ) );
    } catch ( IllegalArgumentException e ) {
      // Property is not known by the base class. No need to log.
      prop = null;
    }

    if ( prop != null ) {
      try {
        switch ( prop ) {
          case RESPONSIVE:
            setResponsive( Boolean.parseBoolean( value ) );
            break;
          case SIZINGMODE:
            setSizingMode( value );
            break;
          case WIDTHCATEGORY:
            setWidthCategory( value );
            break;
          case MINIMUMHEIGHTCATEGORY:
            setMinimumHeightCategory( value );
            break;
        }
      } catch ( IllegalArgumentException e ) {
        System.out.println(
                "Error pre-processing property '" + name + "' with value '"
                        + value + "' in class " + getClass().getName() );
        return;
      }
    }

    super.setAttribute( name, value );
  }

  @Override
  protected void prepareManagedDialog() {

    super.prepareManagedDialog();

    dialog.setResponsive( responsive );
    dialog.setSizingMode( sizingMode.getGwtValue() );
    dialog.setWidthCategory( widthCategory.getGwtValue() );
    dialog.setMinimumHeightCategory( minimumHeightCategory.getGwtValue() );
  }

  public void show() {

    super.show();

    // Remove child XUL buttons before super.layout(),
    // so that corresponding managed objects are not added to `container`,
    // as this is set to be the dialog's body element.
    for ( XulButton btn : dialogButtons ) {
      this.removeChild( btn );
    }

    super.layout();

    if ( dialogButtons.isEmpty() ) {
      this.dialog.addStyleName( "pentaho-dialog-buttonless" );
    } else {
      this.dialog.removeStyleName( "pentaho-dialog-buttonless" );

      // Add child XUL buttons back.
      for ( XulButton btn : dialogButtons ) {
        this.addChild( btn );
      }
    }

    // call into the method that sets the hover style on button elements for IE,
    // since hover pseudo-classes don't work when not in quirksmode
    ElementUtils.setupButtonHoverEffect();

    // Also calls doAutoFocus().
    this.dialog.setFocusButtons( this.getFocusButtonWidgets() );
    this.dialog.getElement().setId( getId() );
    this.dialog.center();
  }

  /**
   * Gets the focus buttons' GWT widgets.
   * <p>
   *   Returns the widgets of the defined buttons, in order of most to least impactful:
   *   <code>cancel</code>, <code>accept</code>, <code>extra1</code> and <code>extra2</code>.
   * </p>
   * <p>
   *   The <i>extra</i> buttons have no predefined semantics and are thus considered in numeric order.
   * </p>
   *
   * @return The focus buttons widgets.
   * @see org.pentaho.gwt.widgets.client.dialogs.DialogBox#setFocusButtons(List)
   */
  private List<Focusable> getFocusButtonWidgets() {
    ArrayList<Focusable> focusButtons = new ArrayList<>();
    for ( String buttonValue : BUTTON_VALUES_AUTO_FOCUS ) {
      XulButton button = findButtonByValue( buttonValue );
      if ( button != null ) {
        Object buttonWidget = button.getManagedObject();
        if ( buttonWidget instanceof Focusable ) {
          focusButtons.add( (Focusable) buttonWidget );
        }
      }
    }

    return focusButtons;
  }

  private XulButton findButtonByValue( String buttonValue ) {
    final String buttonId = buildButtonId( buttonValue );

    for ( XulButton button : dialogButtons ) {
      if ( buttonId.equals( button.getId() ) ) {
        return button;
      }
    }

    return null;
  }

  public String getOnclose() {
    return getAttributeValue( "onclose" );
  }

  public String getOnload() {
    return getAttributeValue( "onload" );
  }

  public String getOnunload() {
    return getAttributeValue( "onunload" );
  }

  public Object getRootObject() {
    return dialog;
  }

  public String getTitle() {
    return getAttributeValue( "title" );
  }

  public void invokeLater( Runnable runnable ) {
    // TODO Auto-generated method stub

  }

  public void setOnclose( String onclose ) {
    this.setAttribute( "onclose", onclose );
  }

  public void setOnload( String onload ) {
    this.setAttribute( "onload", onload );
  }

  public void setOnunload( String onunload ) {
    this.setAttribute( "onunload", onunload );
  }

  public void adoptAttributes( XulComponent component ) {

  }

  @Override
  public void onDomReady() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulDialog#getResizable()
   */
  public Boolean getResizable() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulDialog#setResizable(java.lang.Boolean)
   */
  public void setResizable( Boolean resizable ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setBgcolor( String bgcolor ) {
    this.bgColor = bgcolor;
    if ( container != null ) {
      container.getElement().getStyle().setProperty( "backgroundColor", bgcolor );
    }
  }

  public void setModal( Boolean modal ) {
    throw new RuntimeException( "Not Yet Implemented" );
  }

  public String getBgcolor() {
    return this.bgColor;
  }

  public void setAppicon( String icon ) {
    // TODO Auto-generated method stub
  }

  public boolean isPack() {
    return pack;
  }

  public void setPack( boolean pack ) {
    this.pack = pack;
  }

  @Override
  public void center() {
    if ( dialog != null ) {
      dialog.center();
    }
  }

  @Override
  public void doAutoFocus() {
    if ( dialog != null ) {
      dialog.doAutoFocus();
    }
  }
}
