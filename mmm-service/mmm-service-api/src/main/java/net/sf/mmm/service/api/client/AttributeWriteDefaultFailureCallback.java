/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This interface gives read and write access to the {@link #getDefaultFailureCallback()
 * defaultFailureCallback} property of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteDefaultFailureCallback extends AttributeReadDefaultFailureCallback {

  /**
   * This method sets the {@link #getDefaultFailureCallback() default failure callback}.
   *
   * @param failureCallback is the new value of {@link #getDefaultFailureCallback()}.
   */
  void setDefaultFailureCallback(Consumer<Throwable> failureCallback);

}
