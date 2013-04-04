/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a function with a generic method {@link #apply(Object)}.<br/>
 * It is the result of the overdue closures still missing in java.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <IN> is the generic type of the argument passed to {@link #apply(Object)}. May be a "tuple" or array
 *        if more than one argument is needed.
 * @param <OUT> is the generic return type of {@link #apply(Object)}.
 * @deprecated will be replaced by {@link java.util.function.Function}.
 */
@Deprecated
public interface Function<IN, OUT> {

  /**
   * This is a generic method. The meaning is unspecified here and has to be defined whenever this interface
   * is used as callback argument (due to the lack of closures in java).
   * 
   * @param argument is the argument. May only be <code>null</code> if explicitly specified on usage.
   * @return the result of the function. May only be <code>null</code> if explicitly specified on usage.
   */
  OUT apply(IN argument);

}
