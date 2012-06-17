/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import net.sf.mmm.search.indexer.api.state.SearchIndexerState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateHolder;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolderImpl;

/**
 * This is the implementation of {@link SearchIndexerStateHolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerStateHolderImpl extends
    JaxbBeanHolderImpl<SearchIndexerState, SearchIndexerStateBean> implements
    SearchIndexerStateHolder {

  /**
   * The constructor.
   * 
   * @param state is the {@link SearchIndexerStateBean} for {@link #getBean()}.
   * @param resource is the {@link DataResource}.
   * @param reader is the {@link SearchIndexerStateLoaderImpl} for
   *        {@link #refresh()}.
   */
  public SearchIndexerStateHolderImpl(SearchIndexerStateBean state, DataResource resource,
      SearchIndexerStateLoaderImpl reader) {

    super(state, resource, reader, true);
  }

}
