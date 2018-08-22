/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.List;

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

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;

public class GwtDialog extends GenericDialog implements XulDialog {

  public static void register() {
    GwtXulParser.registerHandler( "dialog", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtDialog();
      }
    } );
  }

  private XulDomContainer xulContainer;
  private String bgColor = null;
  private SimplePanel glasspane = new SimplePanel();
  private List<XulButton> dialogButtons = new ArrayList<XulButton>();
  private boolean pack;

  public GwtDialog() {
    super( "dialog" );

    setManagedObject( null );
    this.orientation = Orient.VERTICAL;
  }

  // we don't add ourselves to the main screen
  private boolean preventLayout = false;

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
        final String buttonVal = button.trim();

        try {
          XulButton buttonObj = (XulButton) this.xulContainer.getDocumentRoot().createElement( "button" );
          buttonObj.setLabel( getAttributeValue( "buttonlabel" + buttonVal ) );
          buttonObj.setTooltiptext( getAttributeValue( "buttonlabel" + buttonVal + "tooltiptext" ) );
          buttonObj.setOnclick( getAttributeValue( "ondialog" + buttonVal ) );
          buttonObj.setId( this.getId() + "_" + button );
          buttonObj.setAlign( getAttributeValue( "pen:" + buttonVal + "buttonalign" ) );
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

    // HorizontalPanel buttonPanel = new HorizontalPanel();
    HorizontalPanel buttonPanel = new HorizontalPanel();
    HorizontalPanel leftButtonPanel = new HorizontalPanel();
    HorizontalPanel centerButtonPanel = new HorizontalPanel();
    HorizontalPanel rightButtonPanel = new HorizontalPanel();
    rightButtonPanel.setStylePrimaryName( "buttonTable" );
    centerButtonPanel.setStylePrimaryName( "buttonTable" );
    leftButtonPanel.setStylePrimaryName( "buttonTable" );

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

    VerticalPanel contentPanel = new VerticalPanel();
    contentPanel.setSpacing( 0 );
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

  public void show() {

    super.show();

    for ( XulButton btn : dialogButtons ) {
      this.removeChild( btn );
    }

    super.layout();
    if ( dialogButtons.isEmpty() ) {
      this.dialog.addStyleName( "pentaho-dialog-buttonless" );
    } else {
      this.dialog.removeStyleName( "pentaho-dialog-buttonless" );
    }
    for ( XulButton btn : dialogButtons ) {
      this.addChild( btn );
    }

    // call into the method that sets the hover style on button elements for IE,
    // since hover pseudo-classes don't work when not in quirksmode
    ElementUtils.setupButtonHoverEffect();

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
}
