/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javafx.beans.value.WritableNumberValue;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Number}.
 *
 * @param <N> is the generic type of the internal numeric {@link #getValue() value} representation.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface WritableNumberProperty<N extends Number>
    extends ReadableNumberProperty<N>, WritableProperty<Number>, WritableNumberValue {

  // nothing to add...

}
