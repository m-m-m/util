/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteFormatter;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.util.lang.api.Formatter;

/**
 * This is the interface for a {@link UiWidget widget} that allows to select from a given list of
 * {@link #getOptions() options}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getOptions() options}.
 */
public abstract interface UiWidgetOptionsBase<VALUE> extends UiWidget, AttributeWriteOptions<VALUE>,
    AttributeWriteFormatter<VALUE> {

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br/>
   * The {@link Formatter} has to be set before the {@link #getOptions() options} are
   * {@link #setOptions(java.util.List) set} in order to reflect this change.
   */
  @Override
  void setFormatter(Formatter<VALUE> formatter);

}
