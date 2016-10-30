/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

/**
 * This is the abstract interface of a {@link Command} that can be executed against a database as {@link #getSql() SQL}.
 * It is either a {@link Query} or a {@link net.sf.mmm.util.query.api.statement.Statement}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract interface Command {

  /**
   * @return the current SQL {@link String} of this {@link Command}. In case of a
   *         {@link net.sf.mmm.util.query.api.statement.Statement} it may only be an SQL fragment as the final operation
   *         such as {@link net.sf.mmm.util.query.api.statement.SelectStatement#fetch()} will complete the statement.
   */
  String getSql();

}
