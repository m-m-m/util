/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import java.util.List;

import net.sf.mmm.util.query.api.argument.ListArgument;

/**
 * This is the interface for a {@link Path} that is a {@link ListArgument}.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface ListPath<E> extends Path<List<E>>, ListArgument<E> {

}
