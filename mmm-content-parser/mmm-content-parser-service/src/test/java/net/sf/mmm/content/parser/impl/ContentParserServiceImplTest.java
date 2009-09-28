/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserService;

/**
 * This is the test-case for {@link ContentParserServiceImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserServiceImplTest {

  protected ContentParserService getContentParserService() {

    ContentParserServiceImpl service = new ContentParserServiceImplHardwired();
    service.initialize();
    return service;
  }

  @Test
  public void testParsersPresent() {

    ContentParserService service = getContentParserService();
    Assert.assertNotNull(service.getGenericParser());
    String[] extensions = new String[] { "txt", "html", "pdf", "doc", "xls", "java", "ppt", "htm",
        "odt", "py", "xml" };
    for (String ext : extensions) {
      ContentParser parser = service.getParser(ext);
      Assert.assertNotNull("Missisng parser for key: " + ext, parser);
    }
  }

}
