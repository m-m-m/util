/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.search;

import net.sf.mmm.persistence.NlsBundlePersistence;
import net.sf.mmm.persistence.api.PersistenceException;

/**
 * This is the exception thrown if an operation (typically a query) in the
 * persistence exceeded a configured timeout.<br>
 * This typically means that the end-user should refine his query and rerun the
 * search.
 * 
 * @see PersistenceSearchQuery#getSearchTimeout()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TimeoutException extends PersistenceException {

  /** UID for serialization.. */
  private static final long serialVersionUID = 4019398692999534503L;

  /**
   * The constructor.
   */
  public TimeoutException() {

    super(NlsBundlePersistence.ERR_TIMEOUT);
  }

}
