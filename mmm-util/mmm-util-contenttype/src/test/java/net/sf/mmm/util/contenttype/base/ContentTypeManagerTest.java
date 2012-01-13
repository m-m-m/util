/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.api.ContentTypeManager;
import net.sf.mmm.util.contenttype.impl.ContentTypeManagerImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentTypeManagerTest {

  /**
   * The constructor.
   */
  public ContentTypeManagerTest() {

    super();
  }

  protected ContentTypeManager getContentTypeManager() {

    ContentTypeManagerImpl manager = new ContentTypeManagerImpl();
    manager.initialize();
    return manager;
  }

  @Test
  public void test() {

    ContentTypeManager contentTypeManager = getContentTypeManager();
    ContentType rootType = contentTypeManager.getRootType();
    Assert.assertNotNull(rootType);
    Assert.assertEquals("DATA", rootType.getId());
    Assert.assertEquals("data", rootType.getTitle());
    Assert.assertEquals("*/*", rootType.getMimetype());
    Assert.assertNull(rootType.getParent());
    Assert.assertNull(rootType.getTechnicalParent());
    Assert.assertEquals(0, rootType.getExtensions().size());
    Assert.assertSame(rootType, contentTypeManager.getContentType("DATA"));

    ContentType graphicsType = contentTypeManager.getContentType("GRAPHICS");
    Assert.assertNotNull(graphicsType);
    Assert.assertEquals("GRAPHICS", graphicsType.getId());
    Assert.assertEquals("graphics", graphicsType.getTitle());
    Assert.assertTrue(graphicsType.isAbstract());
    Assert.assertNull(graphicsType.getMimetype());
    Assert.assertSame(rootType, graphicsType.getParent());

    ContentType jpegType = contentTypeManager.getContentType("JPG");
    Assert.assertNotNull(jpegType);
    Assert.assertEquals("JPG", jpegType.getId());
    Assert.assertEquals("jpeg", jpegType.getTitle());
    Assert.assertEquals("image/jpeg", jpegType.getMimetype());
    Assert.assertFalse(jpegType.isAbstract());
    Assert.assertSame(graphicsType, jpegType.getParent());

  }

}
