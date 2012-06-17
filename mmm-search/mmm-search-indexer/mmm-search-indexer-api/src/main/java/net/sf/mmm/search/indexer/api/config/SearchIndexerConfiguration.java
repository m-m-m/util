/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import java.util.Collection;

import net.sf.mmm.search.api.config.SearchConfiguration;

/**
 * This is the interface for the configuration of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer}.<br>
 * You will typically provide your configuration as XML. The base-implementation
 * comes with an according (un)marshaler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerConfiguration extends SearchConfiguration {

  /**
   * {@inheritDoc}
   */
  Collection<? extends SearchIndexerSource> getSources();

  /**
   * {@inheritDoc}
   */
  SearchIndexerSource getSource(String id);

  /**
   * If you are indexing {@link SearchIndexerDataLocation locations} that
   * contain textual content with different encodings you can enable automatic
   * unicode detection by omitting the
   * {@link SearchIndexerDataLocation#getEncoding()}. In this case if a file has
   * an unspecified encoding, unicode encodings will be detected automatically.
   * Otherwise this non unicode decoding is used as fallback. If not explicitly
   * set, {@link net.sf.mmm.util.io.api.EncodingUtil#ENCODING_ISO_8859_15} is
   * used.
   * 
   * @return the non-unicode encoding.
   */
  String getNonUtfEncoding();

}
