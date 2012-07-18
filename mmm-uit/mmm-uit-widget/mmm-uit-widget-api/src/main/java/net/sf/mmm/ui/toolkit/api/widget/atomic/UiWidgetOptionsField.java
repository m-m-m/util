/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteFormatter;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOptions;
import net.sf.mmm.util.lang.api.Formatter;

/**
 * This is the abstract interface for a {@link UiWidgetField field widget} that allows to select a single
 * value out of a list of predefined options.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetOptionsField<VALUE> extends UiWidgetField<VALUE>, AttributeWriteOptions<VALUE>,
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
