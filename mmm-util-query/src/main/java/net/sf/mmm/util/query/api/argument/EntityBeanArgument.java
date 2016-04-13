/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.query.api.expression.Expression;

/**
 * Extends {@link Argument} to build an {@link Expression} for {@link EntityBean} value(s).
 *
 * @param <V> the generic type of the value to check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface EntityBeanArgument<V extends EntityBean<?>> extends Argument<V> {

}
