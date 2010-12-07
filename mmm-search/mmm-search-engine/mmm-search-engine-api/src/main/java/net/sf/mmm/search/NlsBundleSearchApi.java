/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleSearchApi extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleSearchApi() {

    super();
  }

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdInvalidException
   */
  public static final String ERR_ID_INVALID = "Invalid entry ID \"{id}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdMissingException
   */
  public static final String ERR_ENTRY_ID_MISSING = "Entry for ID \"{id}\" does NOT exist!";

  /**
   * @see net.sf.mmm.search.base.SearchPropertyValueInvalidException
   */
  public static final String ERR_PROPERTY_VALUE_INVALID = "The value \"{value}\" "
      + "is invalid for property \"{property}\"!";

  /** @see net.sf.mmm.search.engine.base.SearchQueryParseException */
  public static final String ERR_QUERY_PARSE = "Failed to parse the query!";

  /** @see net.sf.mmm.search.engine.base.SearchQueryParseException */
  public static final String ERR_QUERY_PARSE_WITH_QUERY = "Failed to parse the query \"{query}\"!";

  /** Label for query in UI. */
  public static final String INF_QUERY = "query";

  /** Button text for search in UI. */
  public static final String INF_SEARCH = "search";

  /** Label for advanced search options in UI. */
  public static final String INF_ADVANCED = "advanced";

  /** Label for any source/filetype option in UI. */
  public static final String INF_ANY = "Any";

  /** Label for creator in UI. */
  public static final String INF_LAST_REFRESH = "Last refresh";

  /** NLS term: {@value} . */
  public static final String INF_FIRST = "first";

  /** NLS term: {@value} . */
  public static final String INF_LAST = "last";

  /** NLS term: {@value} . */
  public static final String INF_PREVIOUS = "previous";

  /** NLS term: {@value} . */
  public static final String INF_NEXT = "next";

  /** NLS term: {@value} . */
  public static final String INF_TOP = "top";

  /** NLS term: {@value} . */
  public static final String INF_BOTTOM = "bottom";

  /** NLS term: {@value} . */
  public static final String INF_FIELD = "field";

  /** NLS term: {@value} . */
  public static final String INF_VALUE = "value";

  /** NLS term: {@value} . */
  public static final String INF_TEXT = SearchEntry.FIELD_TEXT;

  /** NLS term: {@value} . */
  public static final String INF_FILETYPE = SearchEntry.FIELD_TYPE;

  /** NLS term: {@value} . */
  public static final String INF_SOURCE = SearchEntry.FIELD_SOURCE;

  /** NLS term: {@value} . */
  public static final String INF_CREATOR = SearchEntry.FIELD_CREATOR;

  /** NLS term: {@value} . */
  public static final String INF_TITLE = SearchEntry.FIELD_TITLE;

  /** NLS term: {@value} . */
  public static final String INF_TYPE = SearchEntry.FIELD_TYPE;

  /** NLS term: {@value} . */
  public static final String INF_URI = SearchEntry.FIELD_URI;

  /** NLS term: {@value} . */
  public static final String INF_LANGUAGE = SearchEntry.FIELD_LANGUAGE;

  /** NLS term: {@value} . */
  public static final String INF_ID = SearchEntry.FIELD_ID;

  /** NLS term: {@value} . */
  public static final String INF_KEYWORDS = SearchEntry.FIELD_KEYWORDS;

  /** NLS term: {@value} . */
  public static final String INF_SIZE = SearchEntry.FIELD_SIZE;

  /** NLS term: {@value} . */
  public static final String INF_CUSTOM_ID = SearchEntry.FIELD_CUSTOM_ID;

  /** Message for hits of search-page in UI. */
  public static final String MSG_RESULT_RAGE = "Result <strong>{min}</strong> "
      + "to <strong>{max}</strong> of <strong>{size}</strong> for your search "
      + "for \"<strong>{query}</strong>\"";

  /** Message for no hits in UI. */
  public static final String MSG_NO_HITS = "Your search for <strong>{query}</strong> produced no hit.";

  /** Message with hints in case of no hits in UI. */
  public static final String MSG_NO_HITS_HINT = "No hits where found for your query.<br/><br/>Suggestions:"
      + "<ul><li>Ensure that all terms are spelled correctly.</li><li>Try other search terms.</li></ul>";

  /** Message if an error has occurred. */
  public static final String MSG_ERROR = "An error has occurred.";

  /** Markup text to return back to search. */
  public static final String MSG_BACK_TO_SEARCH = "Back to the <a href=\"{uri}\">search</a>.";

  /** Markup text for the details of a hit. */
  public static final String MSG_HIT_DETAILS = "Details for <a href=\"{uri}\"><strong>{title}</strong></a>.";

  /** Markup text for the details of a hit. */
  public static final String MSG_REFRESH_SUCCESS = "The search-index has been refreshed successfully.";

  /** Markup text for the details of a hit. */
  public static final String MSG_REFRESH_NO_CHANGE = "No changes since the last refresh.";

  /** Markup text for the details of a hit. */
  public static final String MSG_REFRESH = "Refresh of configuration and search-index.";

  /** Markup text for the details of a hit. */
  public static final String MSG_ERROR_NO_RESULT = "The parameter for the search-result "
      + "is missing. Maybe this page was not called from the <a href=\"{uri}\">search-page</a>.";

}
