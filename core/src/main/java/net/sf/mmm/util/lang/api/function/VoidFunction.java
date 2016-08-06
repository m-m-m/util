/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.function;

/**
 * This is an implementation of {@link Function} with the return type {@link Void} that can therefore only return
 * {@code null}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
@SuppressWarnings({ "deprecation", "javadoc" })
public final class VoidFunction implements Function<Object, Void> {

  /** The singleton instance. */
  public static final VoidFunction INSTANCE = new VoidFunction();

  /**
   * The constructor.
   */
  private VoidFunction() {

    super();
  }

  @Override
  public Void apply(Object input) {

    return null;
  }

  /**
   * @param <T> is the generic type of the input argument.
   * @return the instance
   */
  @SuppressWarnings("unchecked")
  public static <T> Function<T, Void> getInstance() {

    return (Function<T, Void>) INSTANCE;
  }

}
