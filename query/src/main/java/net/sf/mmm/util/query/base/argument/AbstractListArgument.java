/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Collection;
import java.util.List;

import net.sf.mmm.util.query.api.argument.CollectionArgument;
import net.sf.mmm.util.query.api.argument.ListArgument;

/**
 * The abstract base implementation of {@link CollectionArgument}.
 *
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract interface AbstractListArgument<E> extends AbstractCollectionArgument<List<E>, E>, ListArgument<E> {

}
