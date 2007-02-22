/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

/**
 * This interface extends the {@link MetaDataSet}interface with functionality
 * to modify the meta-data.
 * 
 * @see net.sf.mmm.content.value.api.MetaDataSet
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableMetaDataSet extends MetaDataSet {

  /**
   * This method (un)sets a meta-data entry.
   * 
   * @param key
   *        is the qualified key of the meta-data to set.
   * @param value
   *        is the meta-data value to set. A value of <code>null</code>
   *        unsets the meta-data entry.
   * @return the previous value of the meta-data for the given qualified key
   *         or <code>null</code> if no meta-data was defined for the key
   *         before.
   */
  String setMetaData(MetaDataKey key, String value);

  /**
   * This method unsets a meta-data entry.
   * 
   * @param key
   *        is the qualified key of the meta-data to unset.
   * @return the previous value of the meta-data for the given qualified key
   *         or <code>null</code> if no meta-data was defined for the key
   *         before.
   */
  String unsetMetaData(MetaDataKey key);

}
