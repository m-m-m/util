/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

/**
 * This is the interface for a query that is the {@link net.sf.mmm.util.query.api.statement.SelectStatement#query()
 * result} of a {@link net.sf.mmm.util.query.api.statement.SelectStatement}.<br/>
 * Besides {@link #execute() executing} the {@link Query} you can also reuse it as a sub-query in more complex
 * {@link net.sf.mmm.util.query.api.statement.Statement}s.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface Query<E> extends Command {

  /**
   * Executes the {@link Query} {@link Command} and returns the result.
   *
   * @return the result of the {@link Query} execution.
   */
  E execute();

}
