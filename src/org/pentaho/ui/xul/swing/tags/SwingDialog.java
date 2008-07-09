package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingDialog extends SwingElement implements XulDialog {
  
  XulDomContainer domContainer = null;

  private JDialog dialog = null;

  private String buttonlabelaccept;

  private String buttonlabelcancel;

  private String buttonlabelextra1;

  private String buttonlabelextra2;

  private TreeMap<SwingDialog.BUTTONS, XulButton> buttons = new TreeMap<SwingDialog.BUTTONS, XulButton>();

  private String ondialogaccept;

  private String ondialogcancel;

  private String ondialogextra1;

  private String ondialogextra2;

  private String title = "Dialog";

  private String onload;
  private String onclose;
  private String onunload;

  private XulDialogheader header;

  private int height = 300;

  private int width = 450;
  
  private String btns;

  private BUTTON_ALIGN buttonAlignment;

  private enum BUTTONS {
    ACCEPT, CANCEL, HELP, EXTRA1, EXTRA2
  };

  private enum BUTTON_ALIGN {
    START, CENTER, END, LEFT, RIGHT, MIDDLE
  };
  
  private JFrame frame;

  private String ID;
  private XulComponent parent = null;
  public SwingDialog(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("dialog");
    ID = self.getAttributeValue("ID");
    
    this.parent = parent;
    this.domContainer = domContainer;
    
    this.orientation = Orient.VERTICAL;

    container = new JPanel(new GridBagLayout());
    managedObject = "empty";

    resetContainer();
  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(2, 2, 2, 2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }
  
  public JDialog getDialog(){
    if (dialog == null) {
      createDialog();
    }
    return dialog;
  }
  
  @Override
  public void addComponent(XulComponent component) {
    super.addComponent(component);
    if (initialized) {
      resetContainer();
      layout();
    }
  }

  public String getButtonlabelaccept() {
    return buttonlabelaccept;
  }

  public String getButtonlabelcancel() {
    return buttonlabelcancel;
  }

  public String getButtons() {
    return btns;
  }

  public String getOndialogaccept() {
    return ondialogaccept;
  }

  public String getOndialogcancel() {
    return ondialogcancel;
  }

  public String getTitle() {
    return title;
  }

  public void setButtonlabelaccept(String label) {
    this.buttonlabelaccept = label;
  }

  public void setButtonlabelcancel(String label) {
    this.buttonlabelcancel = label;
  }

  public void setButtons(String buttons) {
    btns = buttons;
    String[] tempButtons = buttons.split(",");

    for (int i = 0; i < tempButtons.length;  i++) {
      SwingButton btn = new SwingButton(null, this, this.domContainer, "button");
      addChild(btn);
      this.buttons.put(SwingDialog.BUTTONS.valueOf(tempButtons[i].trim().toUpperCase()), btn);
      btn.setId(ID+"_" + SwingDialog.BUTTONS.valueOf(tempButtons[i].trim().toUpperCase()).toString().toLowerCase());
    }
  }

  public void setOndialogaccept(String command) {
    this.ondialogaccept = command;

  }

  public void setOndialogcancel(String command) {
    this.ondialogcancel = command;

  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void show() {

    // we delay instantiation in order to setup a modal relationship.
    if (dialog == null) {
      createDialog();
      dialog.pack();
    }
    dialog.setLocationRelativeTo(frame);
    dialog.setVisible(true);
  }

  public void hide() {
    dialog.setVisible(false);
  }

  public void setVisible(boolean visible) {
    if (visible) {
      show();
    } else {
      hide();
    }
  }

  @Override
  public void layout() {
    super.layout();

    for (XulComponent comp : this.children) {
      if (comp instanceof XulDialogheader) {
        header = (XulDialogheader) comp;
      }
    }
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setHeight(int height) {
    this.height = height;
    if(dialog != null){
      dialog.setSize(dialog.getSize().width, height);
    }
  }

  public void setWidth(int width) {
    this.width = width;
    if(dialog != null){
      dialog.setSize(width, dialog.getSize().height);
    }
  }

  public String getButtonalign() {
    return this.buttonAlignment.toString().toLowerCase();
  }

  public void setButtonalign(String align) {
    this.buttonAlignment = SwingDialog.BUTTON_ALIGN.valueOf(align.toUpperCase());

  }

  public String getOnload() {
    return onload;
  }

  public void setOnload(String onload) {
    //check to see if this is a child of a window
    
    if(parent == null && !(parent instanceof XulRoot)){//dialog is root, no JFrame Parent
      this.onload = onload;
    } else {
      //add onLoad event to the XulWindow parent.
      XulRoot root = ((XulRoot) parent);
      String prevOnload = root.getOnload();
      root.setOnload(prevOnload+","+onload);
    }
    
    
    this.onload = onload;
  }

  private void createDialog() {
    Document doc = getDocument();
    Element rootElement = doc.getRootElement();
    XulWindow window = null;
    if(rootElement != this){ //dialog is root, no JFrame Parent
      window = (XulWindow) rootElement;
    }

    if(window != null){
      frame = (JFrame) window.getManagedObject();
      dialog = new JDialog(frame);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    } else {
      dialog = new JDialog();
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    dialog.setResizable(false);
    dialog.setLayout(new BorderLayout());
    
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setOpaque(true);
    int pad = (this.getPadding() > -1)? getPadding() : 3; 
    mainPanel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

    dialog.setTitle(title);
    dialog.setModal(true);
    dialog.add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(container, BorderLayout.CENTER);
    container.setOpaque(false);

    if (this.header != null) {

      JPanel headerPanel = new JPanel(new BorderLayout());
      headerPanel.setBackground(Color.decode("#5F86C0"));
      headerPanel.setOpaque(true);
      JPanel headerPanelInner = new JPanel(new BorderLayout());
      headerPanelInner.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
      headerPanelInner.setOpaque(false);

      headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.decode("#8FB1E5"), Color
          .decode("#446CA9")));

      JLabel title = new JLabel(this.header.getTitle());

      title.setForeground(Color.white);
      headerPanelInner.add(title, BorderLayout.WEST);

      JLabel desc = new JLabel(this.header.getDescription());
      desc.setForeground(Color.white);
      headerPanelInner.add(desc, BorderLayout.EAST);

      headerPanel.add(headerPanelInner, BorderLayout.CENTER);

      mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    Box buttonPanel = Box.createHorizontalBox();

    if (this.buttonAlignment == BUTTON_ALIGN.RIGHT || this.buttonAlignment == BUTTON_ALIGN.END
        || this.buttonAlignment == BUTTON_ALIGN.MIDDLE || this.buttonAlignment == BUTTON_ALIGN.CENTER) {
      buttonPanel.add(Box.createHorizontalGlue());
    }

    ArrayList<BUTTONS> buttonKeyList = new ArrayList<BUTTONS>(buttons.keySet());
    for (int i = 0; i < buttonKeyList.size(); i++) {
    //for (int i = buttonKeyList.size() - 1; i >= 0; i--) {
      buttonPanel.add(Box.createHorizontalStrut(5));
      buttonPanel.add((JButton) this.buttons.get(buttonKeyList.get(i)).getManagedObject());
      this.addChild(this.buttons.get(buttonKeyList.get(i)));
    }

    buttonPanel.add(Box.createHorizontalStrut(5));

    if (this.buttonAlignment == BUTTON_ALIGN.START || this.buttonAlignment == BUTTON_ALIGN.LEFT
        || this.buttonAlignment == BUTTON_ALIGN.MIDDLE || this.buttonAlignment == BUTTON_ALIGN.CENTER) {
      buttonPanel.add(Box.createHorizontalGlue());
    }

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    dialog.setSize(new Dimension(getWidth(), getHeight()));
    dialog.setPreferredSize(new Dimension(getWidth(), getHeight()));
    dialog.setMinimumSize(new Dimension(getWidth(), getHeight()));

    if (buttons.containsKey(SwingDialog.BUTTONS.ACCEPT)) {
      this.buttons.get(SwingDialog.BUTTONS.ACCEPT).setLabel(this.getButtonlabelaccept());
      this.buttons.get(SwingDialog.BUTTONS.ACCEPT).setOnclick(this.getOndialogaccept());
    }
    if (buttons.containsKey(SwingDialog.BUTTONS.CANCEL)) {
      this.buttons.get(SwingDialog.BUTTONS.CANCEL).setLabel(this.getButtonlabelcancel());
      this.buttons.get(SwingDialog.BUTTONS.CANCEL).setOnclick(this.getOndialogcancel());
    }

    // FIXME
    if (buttons.containsKey(SwingDialog.BUTTONS.EXTRA1)) {
      this.buttons.get(SwingDialog.BUTTONS.EXTRA1).setLabel(this.getButtonlabelextra1());
      this.buttons.get(SwingDialog.BUTTONS.EXTRA1).setOnclick(this.getOndialogextra1());
    }
    if (buttons.containsKey(SwingDialog.BUTTONS.EXTRA2)) {
      this.buttons.get(SwingDialog.BUTTONS.EXTRA2).setLabel(this.getButtonlabelextra2());
      this.buttons.get(SwingDialog.BUTTONS.EXTRA2).setOnclick(this.getOndialogextra2());
    }
    
    if(this.getBgcolor() != null){
      mainPanel.setBackground(Color.decode(this.getBgcolor()));
    }
    
  }

  public boolean isHidden() {
    return dialog == null || !dialog.isVisible();
  }

  public String getButtonlabelextra1() {
    return buttonlabelextra1;
  }

  public void setButtonlabelextra1(String buttonlabelextra1) {
    this.buttonlabelextra1 = buttonlabelextra1;
  }

  public String getButtonlabelextra2() {
    return buttonlabelextra2;
  }

  public void setButtonlabelextra2(String buttonlabelextra2) {
    this.buttonlabelextra2 = buttonlabelextra2;
  }

  public String getOndialogextra1() {
    return ondialogextra1;
  }

  public void setOndialogextra1(String ondialogextra1) {
    this.ondialogextra1 = ondialogextra1;
  }

  public String getOndialogextra2() {
    return ondialogextra2;
  }

  public void setOndialogextra2(String ondialogextra2) {
    this.ondialogextra2 = ondialogextra2;
  }

  public XulDomContainer getXulDomContainer() {
    return this.domContainer;
  }

  public void setXulDomContainer(XulDomContainer xulDomContainer) {
   this.domContainer = xulDomContainer;
  }

  public Object getRootObject() {
    return dialog;
  }

	public String getOnclose() {
		return onclose;
	}

	public String getOnunload() {
		return onunload;
	}

	public void setOnclose(String onclose) {
		this.onclose = onclose;
	}

	public void setOnunload(String onunload) {
		this.onunload = onunload;
	}

}
