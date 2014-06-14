/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link SearchIndexer}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchIndexer extends AbstractLoggableObject implements SearchIndexer {

  /**
   * The constructor.
   */
  public AbstractSearchIndexer() {

    super();
  }

  /**
   * @see #add(MutableSearchEntry)
   * 
   * @param entry is the {@link MutableSearchEntry} to add.
   * @param id is the {@link MutableSearchEntry#getId() ID} for the
   *        <code>entry</code>.
   */
  protected abstract void add(MutableSearchEntry entry, Long id);

  /**
   * {@inheritDoc}
   */
  public int update(MutableSearchEntry entry) throws SearchException {

    NlsNullPointerException.checkNotNull(MutableSearchEntry.class, entry);
    int removeCount;
    Long id = null;
    Object cid = entry.getCustomId();
    String uri = null;
    String source = null;
    if (cid == null) {
      // attention: uri will only be unique in combination with source
      source = entry.getSource();
      uri = entry.getUri();
      if (uri == null) {
        throw new SearchUpdateMissingIdException();
      }
    }
    SearchResultPage page = search(id, cid, uri, source);
    removeCount = page.getPageHitCount();
    if (removeCount == 1) {
      id = page.getPageHit(0).getId();
    }
    if (removeCount > 0) {
      remove(page);
    }
    if (id == null) {
      add(entry);
    } else {
      add(entry, id);
    }
    return removeCount;
  }

  /**
   * {@inheritDoc}
   */
  public boolean remove(SearchHit hit) throws SearchException {

    NlsNullPointerException.checkNotNull(SearchHit.class, hit);
    return removeById(hit.getId());
  }

  /**
   * {@inheritDoc}
   */
  public int removeByCustumId(String uid) throws SearchException {

    return remove(SearchEntry.FIELD_CUSTOM_ID, uid);
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeById(Long id) throws SearchException {

    NlsNullPointerException.checkNotNull("id", id);
    int count = remove(SearchEntry.FIELD_ID, id);
    if (count > 1) {
      getLogger().warn("Removed " + count + " entries with ID: " + id);
    }
    return (count > 0);
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUri(String uri, String sourceId) throws SearchException {

    int result;
    if (sourceId == null) {
      result = remove(SearchEntry.FIELD_URI, uri);
    } else {
      SearchResultPage page = search(null, null, uri, sourceId);
      result = remove(page);
    }
    if (result > 1) {
      getLogger().warn(
          "Removed " + result + " entries for URI: " + uri + " (source=" + sourceId + ")");
    }
    return result;
  }

  /**
   * This method removes all {@link SearchResultPage#getPageHit(int) hits} of
   * the given {@link SearchResultPage}.
   * 
   * @param page is the {@link SearchResultPage} to delete.
   * @return the value of {@link SearchResultPage#getPageCount()}.
   */
  protected int remove(SearchResultPage page) {

    NlsNullPointerException.checkNotNull(SearchResultPage.class, page);
    int pageCount = page.getPageCount();
    for (int i = 0; i < pageCount; i++) {
      remove(page.getPageHit(i));
    }
    return pageCount;
  }

  /**
   * This method searches the {@link MutableSearchEntry entries} with the
   * specified properties. At least one of the properties has to be set (not
   * <code>null</code>).
   * 
   * @param id is the {@link MutableSearchEntry#getId() ID} or <code>null</code>
   *        if any ID should match.
   * @param cid is the {@link MutableSearchEntry#getCustomId() custom-ID} or
   *        <code>null</code> if any custom-ID should match.
   * @param uri is the {@link MutableSearchEntry#getUri() URI} or
   *        <code>null</code> if any URI should match.
   * @param source is the {@link MutableSearchEntry#getSource() source} or
   *        <code>null</code> if any source should match.
   * @return the {@link SearchResultPage} for the specified query.
   */
  protected SearchResultPage search(Long id, Object cid, String uri, String source) {

    SearchQueryBuilder queryBuilder = getSearchEngine().getQueryBuilder();
    ComplexSearchQuery query = queryBuilder.createComplexQuery();
    if (id != null) {
      query.addRequiredQuery(queryBuilder.createWordQuery(SearchEntry.FIELD_ID, id.toString()));
    }
    if (cid != null) {
      query.addRequiredQuery(queryBuilder.createWordQuery(SearchEntry.FIELD_CUSTOM_ID,
          cid.toString()));
    }
    if (uri != null) {
      query.addRequiredQuery(queryBuilder.createWordQuery(SearchEntry.FIELD_URI, uri));
    }
    if (source != null) {
      query.addRequiredQuery(queryBuilder.createWordQuery(SearchEntry.FIELD_SOURCE, source));
    }
    return getSearchEngine().search(query, 10);
  }

}
