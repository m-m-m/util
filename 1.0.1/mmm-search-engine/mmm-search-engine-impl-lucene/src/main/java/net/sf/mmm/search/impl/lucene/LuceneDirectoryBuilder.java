/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import net.sf.mmm.search.api.config.SearchIndexConfiguration;

import org.apache.lucene.store.Directory;

/**
 * This is the interface for {@link #createDirectory(SearchIndexConfiguration)}
 * creating} a {@link Directory} for a lucene search index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface LuceneDirectoryBuilder {

  /**
   * This method creates a {@link Directory} for the given
   * <code>configuration</code>.
   * 
   * @param configuration is the {@link SearchIndexConfiguration}.
   * @return the according {@link Directory}.
   */
  Directory createDirectory(SearchIndexConfiguration configuration);

  /**
   * This method creates a {@link Directory} for the given
   * <code>directory</code>.
   * 
   * @param directory is the path pointing to a directory for the index.
   * @return the according {@link Directory}.
   */
  Directory createDirectory(String directory);

}
