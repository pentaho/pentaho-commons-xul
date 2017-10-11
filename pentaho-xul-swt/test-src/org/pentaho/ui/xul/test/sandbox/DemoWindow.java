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
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.test.sandbox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DemoWindow {

  public static void main( String[] args ) {
    Shell window = new Shell();

    Table table = new Table( window, SWT.SINGLE | SWT.BORDER );
    window.setLayout( new GridLayout() );
    GridData data = new GridData();
    data.horizontalSpan = 1;
    data.verticalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = true;
    data.horizontalAlignment = SWT.FILL;
    data.verticalAlignment = SWT.FILL;
    data.heightHint = 100;

    table.setLayoutData( data );

    for ( int i = 0; i < 5; i++ ) {
      TableColumn column = new TableColumn( table, SWT.LEFT );
      if ( i != 0 ) {
        column.setText( "help" + String.valueOf( i ) );
      }
      column.pack();
    }
    for ( int i = 0; i < 40; i++ ) {
      TableItem item = new TableItem( table, SWT.NONE );

      TableEditor checkEditor = new TableEditor( table );

      table.setHeaderVisible( true );
      table.setLinesVisible( true );

      TraverseListener lsTraverse = new TraverseListener() {
        public void keyTraversed( TraverseEvent e ) {
          e.doit = false;
        }
      };
      table.addTraverseListener( lsTraverse );

      Listener textListener = new Listener() {
        public void handleEvent( Event e ) {
          switch ( e.type ) {
            case SWT.FocusOut:
              // item.setText(index, String.valueOf(((Button)e.widget).getSelection()));
              break;
            case SWT.Traverse:
              switch ( e.detail ) {
                case SWT.TRAVERSE_RETURN:
                  // item.setText(index, String.valueOf(((Button)e.widget).getSelection()));
                  // FALL THROUGH
                case SWT.TRAVERSE_ESCAPE:
                  e.doit = false;
              }
              break;
          }
        }
      };

      final Button check = new Button( table, SWT.CHECK );
      check.addListener( SWT.FocusOut, textListener );
      check.addListener( SWT.Traverse, textListener );
      check.addTraverseListener( new TraverseListener() {

        public void keyTraversed( TraverseEvent e ) {
          e.doit = false;
        }

      } );

      /*
       * final Text edit = new Text(table, SWT.NONE); edit.setText(item.getText()); edit.selectAll(); edit.setFocus();
       * edit.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { System.out.println("Character:" +
       * String.valueOf(e.character)); } }); edit.addTraverseListener(lsTraverse);
       */
      checkEditor.setEditor( check, item, 0 );
      checkEditor.grabHorizontal = true;
    }

    window.open();
    window.pack();

    while ( !window.isDisposed() ) {
      if ( !window.getDisplay().readAndDispatch() ) {
        window.getDisplay().sleep();
      }
    }

  }
}
