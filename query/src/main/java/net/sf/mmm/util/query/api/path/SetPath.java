/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import java.util.List;
import java.util.Set;

import net.sf.mmm.util.query.api.argument.SetArgument;

/**
 * This is the interface for a {@link Path} that is a {@link SetArgument}.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface SetPath<E> extends Path<Set<E>>, SetArgument<E> {

}
