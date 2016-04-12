/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.BooleanArgument;
import net.sf.mmm.util.query.api.argument.ComparableArgument;
import net.sf.mmm.util.query.api.expression.Expression;

/**
 * The abstract base implementation of {@link BooleanArgument}.
 *
 * @param <V> the generic type of the value to check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractComparableArgument<V extends Comparable<?>>
    extends AbstractArgument<V>, ComparableArgument<V> {

}
