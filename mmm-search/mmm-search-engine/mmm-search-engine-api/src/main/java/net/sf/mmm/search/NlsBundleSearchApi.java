/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search;

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
   * @see net.sf.mmm.search.indexer.base.SearchUpdateMissingIdException
   */
  public static final String ERR_UPDATE_MISSING_ID = "Can not update entry: neither UID nor URI is set!";

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

  /**
   * @see net.sf.mmm.search.indexer.base.SearchRemoveFailedException
   */
  public static final String ERR_REMOVE_FAILED = "Failed to remove entry with "
      + "value \"{value}\" for property \"{property}\"!";

  /**
   * @see net.sf.mmm.search.indexer.base.SearchAddFailedException
   */
  public static final String ERR_ADD_FAILED = "Failed to add entry \"{0}\"!";

  /**
   * @see net.sf.mmm.search.base.SearchParseException
   */
  public static final String ERR_PARSE = "Failed to parse the query \"{query}\"!";

}
