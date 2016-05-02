/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.query.api.argument.NumberArgument;

/**
 * This is the interface for a {@link Path} that is a {@link NumberArgument}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface NumberPath<V extends Number & Comparable<?>> extends ComparablePath<V>, NumberArgument<V> {

}
