/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.query.api.argument.EntityBeanArgument;
import net.sf.mmm.util.query.api.expression.Expression;

/**
 * This is the {@link #isConstant() constant} implementation of {@link EntityBeanArgument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstantEntityBeanArgument<V extends EntityBean> extends ConstantArgument<V>
    implements AbstractEntityBeanArgument<V> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantEntityBeanArgument(V value) {
    super(value);
  }

}
