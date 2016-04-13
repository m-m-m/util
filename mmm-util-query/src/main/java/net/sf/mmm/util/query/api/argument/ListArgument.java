/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import java.util.List;

import net.sf.mmm.util.query.api.expression.Expression;

/**
 * Extends {@link Argument} to build an {@link Expression} of {@link List} values.
 *
 * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ListArgument<E> extends CollectionArgument<List<E>, E> {

}
