/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import java.util.List;

/**
 * This interface gives read access to the {@link #getOptions() options} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of a single value out of the {@link #getOptions() options}.
 */
public abstract interface AttributeReadOptions<VALUE> {

  /**
   * This method gets the <em>options</em> of this object. The options are the available values to choose a
   * selection from. The {@link List} shall not contain duplicate entries. It may contain the value
   * <code>null</code>.
   * 
   * @return the {@link List} with all available options.
   */
  List<VALUE> getOptions();

}
