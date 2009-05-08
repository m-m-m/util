/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

import java.util.Map;

/**
 * This is the interface for a context for custom-data associated with an
 * {@link TransactionAdapter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionContext {

  /**
   * This method gets the {@link Map} that allows to associate custom-data with
   * a transaction.<br>
   * <b>ATTENTION:</b><br>
   * Please only use specific keys such as {@link Class#getName() qualified
   * class-names} of your custom code.
   * 
   * @return the {@link Map} for custom-data.
   */
  Map<String, Object> getMap();

  /**
   * This method determines if the {@link #getMap() map} is
   * {@link Map#isEmpty() empty}. The only difference to {@link #getMap()}.
   * {@link Map#isEmpty() isEmpty()} this method allows to avoid creating a map
   * in case of lazy allocation in {@link #getMap()} just to determine that it
   * is empty.
   * 
   * @return <code>true</code> if the {@link #getMap() map} is empty,
   *         <code>false</code> otherwise.
   */
  boolean isEmpty();

}
