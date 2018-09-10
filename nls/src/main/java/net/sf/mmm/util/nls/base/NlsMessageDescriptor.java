/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.lang.reflect.Method;

/**
 * This is the descriptor of an {@link net.sf.mmm.util.nls.api.NlsMessage} from an
 * {@link net.sf.mmm.util.nls.api.NlsBundle} {@link Method}.
 *
 * @author hohwille
 * @since 7.3.0
 */
public class NlsMessageDescriptor {

  private final Method method;

  private final String key;

  private final String message;

  private NlsArgumentDescriptor[] arguments;

  /**
   * The constructor.
   *
   * @param method the {@link net.sf.mmm.util.nls.api.NlsBundle} {@link Method}.
   * @param bundleHelper the {@link NlsBundleHelper} instance.
   */
  NlsMessageDescriptor(Method method) {
    super();
    this.method = method;
    this.message = NlsBundleHelper.INSTANCE.getMessage(method);
    this.key = NlsBundleHelper.INSTANCE.getKey(method);
  }

  /**
   * The constructor.
   *
   * @param key - see {@link #getKey()}.
   * @param message - see {@link #getMessage()}.
   * @param arguments - see {@link #getArgument(int)}.
   */
  public NlsMessageDescriptor(String key, String message, NlsArgumentDescriptor[] arguments) {
    super();
    this.method = null;
    this.key = key;
    this.message = message;
    this.arguments = arguments;
  }

  /**
   * @return the method
   */
  public Method getMethod() {

    return this.method;
  }

  /**
   * @return the key
   */
  public String getKey() {

    return this.key;
  }

  /**
   * @return the message
   */
  public String getMessage() {

    return this.message;
  }

  private NlsArgumentDescriptor[] getArguments() {

    if (this.arguments == null) {
      if (this.method == null) {
        this.arguments = NlsArgumentDescriptor.EMPTY_ARRAY;
      } else {
        this.arguments = NlsBundleHelper.INSTANCE.getArguments(this.method);
      }
    }
    return this.arguments;
  }

  /**
   * @param index the {@link NlsArgumentDescriptor#getIndex() index} of the requested {@link NlsArgumentDescriptor} in
   *        the range from {@code 0} to <code>{@link #getArgumentCount()} - 1</code>.
   * @return the {@link NlsArgumentDescriptor} for the given {@code index}.
   */
  public NlsArgumentDescriptor getArgument(int index) {

    return getArguments()[index];
  }

  /**
   * @return the number of {@link #getArgument(int) arguments}.
   */
  public int getArgumentCount() {

    return getArguments().length;
  }

  /**
   * @param argumentKey the {@link NlsArgumentDescriptor#getKey() key} of the requested {@link NlsArgumentDescriptor}.
   * @return the {@link NlsArgumentDescriptor} for the given {@code key} or {@code null} if not found.
   */
  public NlsArgumentDescriptor getArgument(String argumentKey) {

    for (NlsArgumentDescriptor arg : getArguments()) {
      if (arg.getKey().equals(argumentKey)) {
        return arg;
      }
    }
    return null;
  }

}
