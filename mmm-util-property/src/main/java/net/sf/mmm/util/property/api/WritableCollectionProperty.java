/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Collection;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Collection}.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained in the collection}.
 * @param <VALUE> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableCollectionProperty<E, VALUE extends Collection<E>>
    extends ReadableCollectionProperty<E, VALUE>, WritableProperty<VALUE> {

  // nothing to add...

}
