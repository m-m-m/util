/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.Instant;

import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Instant}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface WritableInstantProperty extends ReadableInstantProperty, WritableProperty<Instant> {

}
