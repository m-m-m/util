/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.util.function.Consumer;

/**
 * This interface gives read access to the {@link #getDefaultFailureCallback() defaultFailureCallback}
 * property of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadDefaultFailureCallback {

  /**
   * This method gets the default callback to {@link Consumer#accept(Object) handle} failures that occurred on
   * service invocations.
   * 
   * @return the default failure callback or <code>null</code> if no default is configured.
   */
  Consumer<Throwable> getDefaultFailureCallback();

}
