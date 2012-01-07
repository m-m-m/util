/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.io.ByteArrayInputStream;

import net.sf.mmm.util.contenttype.api.ContentType;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentTypeLoaderTest {

  /**
   * The constructor.
   */
  public ContentTypeLoaderTest() {

    super();
  }

  protected ContentTypeLoader getLoader() {

    ContentTypeLoader loader = new ContentTypeLoader();
    loader.initialize();
    return loader;
  }

  @Test
  public void testLoader() {

    ByteArrayInputStream stream = new ByteArrayInputStream(
        "<?xml version='1.0' standalone='yes'?><content-type id='root' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'><children><content-type id='PNG' title='portable-network-graphics' defaultExtension='.png'></content-type></children></content-type>"
            .getBytes());
    ContentTypeBean rootType = getLoader().loadXml(stream, "test");
    Assert.assertNotNull(rootType);
    Assert.assertEquals("root", rootType.getId());
    Assert.assertEquals("root", rootType.getTitle());
    Assert.assertNull(rootType.getDefaultExtension());
    Assert.assertEquals(1, rootType.getChildren().size());
    ContentType pngType = rootType.getChildren().get(0);
    Assert.assertNotNull(pngType);
    Assert.assertEquals("PNG", pngType.getId());
    Assert.assertEquals("portable-network-graphics", pngType.getTitle());
    Assert.assertSame(rootType, pngType.getParent());

    // Assert.assertEquals(".png", pngType.getDefaultExtension());

  }

}
