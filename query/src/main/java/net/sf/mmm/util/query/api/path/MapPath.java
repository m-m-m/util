/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import java.util.Map;

import net.sf.mmm.util.query.api.argument.MapArgument;

/**
 * This is the interface for a {@link Path} that is a {@link MapArgument}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface MapPath<K, V> extends Path<Map<K, V>>, MapArgument<K, V> {

}
