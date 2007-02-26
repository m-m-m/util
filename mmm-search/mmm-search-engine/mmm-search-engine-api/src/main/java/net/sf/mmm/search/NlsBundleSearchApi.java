/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleSearchApi extends AbstractResourceBundle {

  /**
   * The constructor
   */
  public NlsBundleSearchApi() {

    super();
  }

  /**
   * @see net.sf.mmm.search.base.SearchUpdateMissingIdException
   */
  public static final String ERR_UPDATE_MISSING_ID = "Can not update entry: neither UID nor URI is set!";

  /**
   * @see net.sf.mmm.search.base.SearchIdInvalidException
   */
  public static final String ERR_ID_INVALID = "Invalid entry ID \"{0}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdMissingException
   */
  public static final String ERR_ENTRY_ID_MISSING = "Entry for ID \"{0}\" does NOT exist!";

  /**
   * @see net.sf.mmm.search.base.SearchIdInvalidException
   */
  public static final String ERR_SEARCH_IO = "Search engine failed with I/O error!";

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdMissingException
   */
  public static final String ERR_PROPERTY_VALUE_INVALID = "The value \"{0}\" is invalid for property \"{1}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdMissingException
   */
  public static final String ERR_REMOVE_FAILED = "Failed to remove entry with value \"{0}\" for property \"{1}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchEntryIdMissingException
   */
  public static final String ERR_ADD_FAILED = "Failed to add entry \"{0}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchParseException
   */
  public static final String ERR_PARSE = "Failed to parse the query \"{0}\"!";

}
