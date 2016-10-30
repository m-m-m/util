/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.query.api.argument.ComparableArgument;

/**
 * This is the interface for a {@link Path} that is a {@link ComparableArgument}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface ComparablePath<V extends Comparable<?>> extends Path<V>, ComparableArgument<V> {

}
