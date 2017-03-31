/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base;

/**
 * This is the {@link Enum} for the available modes of a {@link net.sf.mmm.util.query.api.Query}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public enum QueryMode {

  /** {@link QueryImpl#getMode() Mode} for a normal query. */
  NORMAL,

  /** {@link QueryImpl#getMode() Mode} for a query where only the first result should be returned. */
  FIRST,

  /** {@link QueryImpl#getMode() Mode} for a query where only a single unique result should match and be returned. */
  UNIQUE

}
