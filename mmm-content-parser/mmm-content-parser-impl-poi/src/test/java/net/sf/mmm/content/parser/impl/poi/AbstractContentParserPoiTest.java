/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import java.io.InputStream;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.component.api.IocContainer;
import net.sf.mmm.util.component.base.SpringConfigs;
import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the abstract test-case for sub-classes of {@link AbstractContentParserPoi}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractContentParserPoiTest {

  /**
   * @return the {@link IocContainer} for tests with dependency injection.
   */
  protected IocContainer getContainer() {

    return SpringContainerPool.getInstance(SpringConfigs.SPRING_XML_CONTENT_PARSER);
  }

  /**
   * This method parses the given resource.
   * 
   * @param parser is the {@link ContentParser} to test.
   * @param resourceName is the name of the resource to parse.
   * @param filesize is the expected size of the resource in bytes.
   * @return the {@link GenericContext} with the extracted metadata.
   * @throws Exception in case of an error.
   */
  protected GenericContext parse(ContentParser parser, String resourceName, long filesize) throws Exception {

    DataResource testResource = new ClasspathResource(AbstractContentParserPoiTest.class, resourceName, false);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  /**
   * @return the {@link ContentParser} to test.
   */
  protected abstract ContentParser getContentParser();

}
