/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * Descriptor of an {@link NlsArgument} from an {@link NlsBundle} {@link java.lang.reflect.Method}.
 *
 * @see NlsBundleHelper
 *
 * @author hohwille
 * @since 7.3.0
 */
public class NlsArgumentDescriptor {

  /** An empty {@link NlsArgumentDescriptor} array. */
  public static final NlsArgumentDescriptor[] EMPTY_ARRAY = new NlsArgumentDescriptor[0];

  private final String name;

  private final Class<?> type;

  private final int index;

  /**
   * The constructor.
   *
   * @param name - see {@link #getKey()}.
   * @param type - see {@link #getType()}.
   * @param index - see {@link #getIndex()}.
   */
  public NlsArgumentDescriptor(String name, Class<?> type, int index) {
    super();
    this.name = name;
    this.type = type;
    this.index = index;
  }

  /**
   * @see net.sf.mmm.util.nls.api.NlsArgument#getKey()
   * @return the {@link NlsMessage#getArgument(String) key} or name of the parameter.
   */
  public String getKey() {

    return this.name;
  }

  /**
   * @return the {@link NlsMessage#getArgument(int) index} of the parameter.
   */
  @SuppressWarnings("javadoc")
  public int getIndex() {

    return this.index;
  }

  /**
   * @return the {@link Class} reflecting the type of the parameter.
   */
  public Class<?> getType() {

    return this.type;
  }

  @Override
  public String toString() {

    return this.name;
  }

}
