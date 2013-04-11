/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a {@link Function} that does not return a value. It is used to receive a value
 * from outside (e.g. asynchronously).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <IN> is the generic type of the argument passed to {@link #apply(Object)}. May be a composite object
 *        (e.g. java bean) if more than one argument is needed.
 * @deprecated will be removed and is replaced by {@link java.util.function.Consumer}.
 */
@Deprecated
public interface Callback<IN> extends Function<IN, Void> {

  /**
   * {@inheritDoc}
   * 
   * @return always <code>null</code>
   */
  @Override
  Void apply(IN argument);

}
