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


package org.pentaho.ui.xul.swing.tags.dom;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.ui.xul.dom.Namespace;

public class NamespaceTest {
  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public final void testNamespace() {
    Namespace attr = new Namespace( "www.foo.xml", "pre" );

    assertNotNull( attr );
    assertTrue( attr.getURI().equals( "www.foo.xml" ) );
    assertTrue( attr.getPrefix().equals( "pre" ) );
  }
}
