/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.w3c.dom.Document;

import net.sf.mmm.util.xml.api.DomUtil;

/**
 * This is the test-case for {@link DomUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class DomUtilTest {

  protected DomUtil getDomUtil() {

    return DomUtilImpl.getInstance();
  }

  @Test
  public void testCreateDocument() {

    DomUtil domUtil = getDomUtil();
    Document document = domUtil.createDocument();
    assertNotNull(document);
    assertNull(document.getOwnerDocument());
  }

}
