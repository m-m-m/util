/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import java.util.List;

/**
 * This interface gives read and write access to the selected {@link #getOptions() options} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of a single value out of the {@link #getOptions() options}.
 */
public abstract interface AttributeWriteOptions<VALUE> extends AttributeReadOptions<VALUE> {

  /**
   * This method sets the {@link #getOptions() options} of this object. The options will be presented in their
   * order specified by the given {@link List}.
   * 
   * @param options is the new {@link List} of available {@link #getOptions() options}.
   */
  void setOptions(List<VALUE> options);

}
