package org.pentaho.ui.xul.tests;

import static org.junit.Assert.*;

import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExceptionsTest {

  @Test
  public final void testXulException1() throws Exception{
  	XulException e = new XulException("This is an error");
  	assertNotNull(e);
  }
  
  @Test
  public final void testXulException2() throws Exception{
  	XulException e = new XulException();
  	assertNotNull(e);
  }

  @Test
  public final void testXulException3() throws Exception{
  	XulException e = new XulException("This is an error", new Exception("foo"));
  	assertNotNull(e);
  }

  @Test
  public final void testXulException4() throws Exception{
  	XulException e = new XulException(new Exception("foo"));
  	assertNotNull(e);
  }
  
  @Test
  public final void testXulDomException1() throws Exception{
  	XulDomException e = new XulDomException("This is an error");
  	assertNotNull(e);
  }
  
  @Test
  public final void testXulDomException2() throws Exception{
  	XulDomException e = new XulDomException();
  	assertNotNull(e);
  }

  @Test
  public final void testXulDomException3() throws Exception{
  	XulDomException e = new XulDomException("This is an error", new Exception("foo"));
  	assertNotNull(e);
  }

  @Test
  public final void testXulDomException4() throws Exception{
  	XulDomException e = new XulDomException(new Exception("foo"));
  	assertNotNull(e);
  }
  
}
	