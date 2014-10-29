/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search;

import javax.inject.Named;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface NlsBundleSearchRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.search.engine.base.SearchQueryParseException
   *
   * @param query is the invalid query.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to parse the query \"{query}\"!")
  NlsMessage errorQueryParse(@Named("query") String query);

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Search")
  NlsMessage labelSearch();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Query")
  NlsMessage labelQuery();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("advanced")
  NlsMessage labelAdvanced();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Last refresh")
  NlsMessage labelLastRefresh();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Any")
  NlsMessage infoAny();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("first")
  NlsMessage infoFirst();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("last")
  NlsMessage infoLast();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("previous")
  NlsMessage infoPrevious();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("next")
  NlsMessage infoNext();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("field")
  NlsMessage infoField();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("value")
  NlsMessage infoValue();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_TEXT)
  NlsMessage infoText();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_TYPE)
  NlsMessage infoType();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_SOURCE)
  NlsMessage infoSource();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_CREATOR)
  NlsMessage infoCreator();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_TITLE)
  NlsMessage infoTitle();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_URI)
  NlsMessage infoUri();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_LANGUAGE)
  NlsMessage infoLanguage();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_ID)
  NlsMessage infoIdentifier();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_KEYWORDS)
  NlsMessage infoKeywords();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_SIZE)
  NlsMessage infoSize();

  /**
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage(SearchEntry.FIELD_CUSTOM_ID)
  NlsMessage infoCustomId();

  // TODO hohwille HTML templating should not happen via NLS (XSS, etc.)...

  /**
   * Message for hits of search-page.
   *
   * @param start the paging start index.
   * @param end the paging end index.
   * @param size the number of hits on the page.
   * @param query the escaped query.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Result <strong>{start}</strong> "
      + "to <strong>{end}</strong> of <strong>{size}</strong> for your search for \"<strong>{query}</strong>\"")
  NlsMessage msgResultRange(@Named("start") Number start, @Named("end") Number end, @Named("size") Number size,
      @Named("query") String query);

  /**
   * Message for query that produced no hit.
   *
   * @param query the escaped query.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Your search for <strong>{query}</strong> produced no hit.")
  NlsMessage msgNoHits(@Named("query") String query);

  /**
   * Message for query that produced no hit.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("No hits where found for your query. <br><br>Suggestions:"
      + "<ul><li>Ensure that all terms are spelled correctly.</li><li>Try other search terms.</li></ul>")
  NlsMessage msgNoHitsHint();

  /**
   * Message for query that produced no hit.
   *
   * @param uri is the URI/URL.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The parameter for the search-result is missing. Maybe this page was not called from the "
      + "<a href=\"{uri}\">search-page</a>.")
  NlsMessage msgErrorNoHit(@Named("uri") String uri);

  /**
   * Message if an error has occurred.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An error has occurred.")
  NlsMessage msgError();

  /**
   * Markup text to return back to search.
   *
   * @param uri is the URI/URL.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Back to the <a href=\"{uri}\">search</a>.")
  NlsMessage msgBackToSearch(@Named("uri") String uri);

  /**
   * Markup text for the details of a hit.
   *
   * @param uri is the URI/URL.
   * @param title is the label of the link.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Details for <a href=\"{uri}\"><strong>{title}</strong></a>.")
  NlsMessage msgHitDetails(@Named("uri") String uri, @Named("title") String title);

  /**
   * Message after successful refresh.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The search-index has been refreshed successfully.")
  NlsMessage msgRefreshSuccess();

  /**
   * Message if refresh caused no change.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("No changes since the last refresh.")
  NlsMessage msgRefreshNoChange();

  /**
   * Tooltip for refresh.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Refresh of configuration and search-index.")
  NlsMessage tooltipRefresh();

  /**
   * <code>net.sf.mmm.search.indexer.base.SearchUpdateMissingIdException</code>.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Can not update entry: neither UID nor URI is set!")
  NlsMessage errorUpdateMissingId();

  /**
   * <code>net.sf.mmm.search.indexer.base.SearchRemoveFailedException</code>.
   *
   * @param property is the name of the property to match for value.
   * @param value is the matching value for the entries to remove.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to remove entry with value \"{value}\" for property \"{property}\"!")
  NlsMessage errorRemoveFailed(@Named("property") String property, @Named("value") String value);

  /**
   * <code>net.sf.mmm.search.indexer.base.SearchAddFailedException</code>.
   *
   * @param entry is the entry that could not be added.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to add entry \"{entry}\"!")
  NlsMessage errorAddFailed(@Named("entry") Object entry);

  /**
   * <code>net.sf.mmm.search.indexer.base.SearchEntryIdImmutableException</code>.
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The ID of a search-entry is immutable and can not be modified!")
  NlsMessage errorIdImmutable();

  /** See net.sf.mmm.search.indexer.impl.SearchIndexerMain */
  String MSG_MAIN_OPTION_USAGE_SPRING_XML = "The optional XML based configuration used for the spring context given by "
      + "{operand}. By default annotation based config is used.";

  /** See net.sf.mmm.search.indexer.impl.SearchIndexerMain */
  String INF_INDEXER_MAIN_OPTION_NAME_SPRING_XML = "--spring-xml";

  /** See net.sf.mmm.search.indexer.impl.SearchIndexerMain */
  String MSG_MAIN_OPTION_USAGE_SPRING_PACKAGES = "The optional list of java packages where spring should look for "
      + "annotated beans. The default is {default}. This option is ignored, if "
      + INF_INDEXER_MAIN_OPTION_NAME_SPRING_XML + " is set.";

  /** See net.sf.mmm.search.indexer.base.AbstractSearchIndexerMain */
  String MSG_SEARCH_INDEXER_MAIN_MODE_USAGE_DEFAULT = "Perform search-indexing according to the configuration.";

}
