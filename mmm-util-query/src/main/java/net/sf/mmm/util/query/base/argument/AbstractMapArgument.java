/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Map;

import net.sf.mmm.util.query.api.argument.MapArgument;

/**
 * The abstract base implementation of {@link MapArgument}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractMapArgument<K, V> extends AbstractArgument<Map<K, V>>, MapArgument<K, V> {

}
