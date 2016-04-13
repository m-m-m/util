/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Collection;
import java.util.Set;

import net.sf.mmm.util.query.api.argument.SetArgument;

/**
 * The abstract base implementation of {@link SetArgument}.
 *
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractSetArgument<E> extends AbstractCollectionArgument<Set<E>, E>, SetArgument<E> {

}
