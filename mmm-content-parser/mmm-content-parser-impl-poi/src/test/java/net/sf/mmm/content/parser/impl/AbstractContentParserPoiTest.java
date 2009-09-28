/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.impl.AbstractContentParserPoi;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the abstract test-case for sub-classes of
 * {@link AbstractContentParserPoi}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractContentParserPoiTest {

  protected Properties parse(ContentParser parser, String resourceName, long filesize)
      throws Exception {

    DataResource testResource = new ClasspathResource(AbstractContentParserPoiTest.class,
        resourceName, false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  protected abstract ContentParser getContentParser();

}
