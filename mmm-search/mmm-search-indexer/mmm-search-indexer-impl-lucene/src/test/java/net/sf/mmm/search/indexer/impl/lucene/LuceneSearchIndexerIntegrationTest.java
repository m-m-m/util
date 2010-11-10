/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import net.sf.mmm.search.indexer.impl.SearchIndexerMain;
import net.sf.mmm.test.TestResourceHelper;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link LuceneSearchIndexer} via
 * {@link SearchIndexerMain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchIndexerIntegrationTest {

  /**
   * Tests the initial indexing, etc.
   */
  @Test
  public void testIndexer() {

    SearchIndexerMain main = new SearchIndexerMain();
    int exitCode = main.run(new String[] { "--config",
        TestResourceHelper.getTestPath(LuceneSearchIndexerIntegrationTest.class, ".xml") });
    Assert.assertEquals(0, exitCode);
    // TODO: create search engine and test results...
  }

}
