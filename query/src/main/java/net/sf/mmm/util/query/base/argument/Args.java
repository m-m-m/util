/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.argument.BooleanArgument;
import net.sf.mmm.util.query.api.argument.CollectionArgument;
import net.sf.mmm.util.query.api.argument.ComparableArgument;
import net.sf.mmm.util.query.api.argument.EntityBeanArgument;
import net.sf.mmm.util.query.api.argument.ListArgument;
import net.sf.mmm.util.query.api.argument.MapArgument;
import net.sf.mmm.util.query.api.argument.NumberArgument;
import net.sf.mmm.util.query.api.argument.SetArgument;
import net.sf.mmm.util.query.api.argument.StringArgument;

/**
 * Static factory to create {@link Argument}s.
 *
 * @author hohwille
 * @since 8.0.0
 */
public final class Args {

  private Args() {
  }

  /**
   * @param <V> the generic type of the value.
   * @param value the value to use as parameter.
   * @return the generic {@link Argument#isConstant() constant} {@link Argument} for the given {@code value}.
   */
  public static <V> Argument<V> of(V value) {

    return new ConstantArgument<>(value);
  }

  /**
   * @param <V> the generic type of the value.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link ComparableArgument} for the given {@code value}.
   */
  public static <V extends Comparable<?>> ComparableArgument<V> of(V value) {

    return new ConstantComparableArgument<>(value);
  }

  /**
   * @param <V> the generic type of the value.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link NumberArgument} for the given {@code value}.
   */
  public static <V extends Number & Comparable<?>> NumberArgument<V> of(V value) {

    return new ConstantNumberArgument<>(value);
  }

  /**
   * @param <V> the generic type of the value.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link EntityBeanArgument} for the given {@code value}.
   */
  public static <V extends EntityBean> EntityBeanArgument<V> of(V value) {

    return new ConstantEntityBeanArgument<>(value);
  }

  /**
   * @param <V> the generic type of the value.
   * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link CollectionArgument} for the given {@code value}.
   */
  public static <V extends Collection<E>, E> CollectionArgument<V, E> of(V value) {

    return new ConstantCollectionArgument<>(value);
  }

  /**
   * @param <E> the generic type of the {@link List}-{@link List#get(int) elements}.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link ListArgument} for the given {@code value}.
   */
  public static <E> ListArgument<E> of(List<E> value) {

    return new ConstantListArgument<>(value);
  }

  /**
   * @param <E> the generic type of the {@link Set}-{@link Set#contains(Object) elements}.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link ListArgument} for the given {@code value}.
   */
  public static <E> SetArgument<E> of(Set<E> value) {

    return new ConstantSetArgument<>(value);
  }

  /**
   * @param <K> the generic type of the {@link Map}-{@link Map#keySet() keys}.
   * @param <V> the generic type of the {@link Map}-{@link Map#values() values}.
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link MapArgument} for the given {@code value}.
   */
  public static <K, V> MapArgument<K, V> of(Map<K, V> value) {

    return new ConstantMapArgument<>(value);
  }

  /**
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link BooleanArgument} for the given {@code value}.
   */
  public static BooleanArgument of(Boolean value) {

    return new ConstantBooleanArgument(value);
  }

  /**
   * @param value the value to use as parameter.
   * @return the {@link Argument#isConstant() constant} {@link StringArgument} for the given {@code value}.
   */
  public static StringArgument of(String value) {

    return new ConstantStringArgument(value);
  }

}
