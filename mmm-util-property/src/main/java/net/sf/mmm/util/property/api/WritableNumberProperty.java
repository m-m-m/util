/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.value.WritableNumberValue;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Number}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableNumberProperty
    extends ReadableNumberProperty, WritableProperty<Number>, WritableNumberValue {

  // nothing to add...

}
