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
/*jadclipse*/// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
package org.pentaho.ui.xul.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class ScrollablePanel extends JPanel {
  public ScrollablePanel() {
  }

  public ScrollablePanel( Component c ) {
    super( new BorderLayout() );
    add( c, BorderLayout.CENTER );
  }

  public ScrollablePanel( boolean isDoubleBuffered ) {
    super( isDoubleBuffered );
  }

  public ScrollablePanel( LayoutManager layout, boolean isDoubleBuffered ) {
    super( layout, isDoubleBuffered );
  }

  public ScrollablePanel( LayoutManager layout ) {
    super( layout );
  }

  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension( 1, 1 );
  }

  public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction ) {
    return 2;
  }

  public boolean getScrollableTracksViewportHeight() {
    return true;
  }

  public boolean getScrollableTracksViewportWidth() {
    return true;
  }

  public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction ) {
    return 2;
  }
}
