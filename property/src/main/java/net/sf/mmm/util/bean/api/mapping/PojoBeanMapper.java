/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.mapping;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.entity.EntityBean;

/**
 * This is the interface used to map instances from a hierarchy of {@link net.sf.mmm.util.pojo.api.Pojo POJOs}
 * {@link #toBean(Object, Bean) to} amd {@link #fromBean(Bean, Class) from} a corresponding hierarchy of {@link Bean}s.
 * E.g. it will be used to map from {@code FooEntity} to {@code FooBean} or from {@code BarBean} to {@code BarEntity}.
 * To map from a fixed generic container object see {@link DocumentBeanMapper} instead.
 *
 * @param <P> the base type of the {@link net.sf.mmm.util.pojo.api.Pojo}s to convert (e.g. {@link Object} or
 *        {@link net.sf.mmm.util.bean.api.entity.Entity}).
 * @param <B> the base type of the {@link Bean} to map (e.g. {@link Bean} or {@link EntityBean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface PojoBeanMapper<P, B extends Bean> {

  /**
   * @param <T> the generic type of the {@link Bean} to map to.
   * @param pojo the {@link net.sf.mmm.util.pojo.api.Pojo} to map.
   * @param prototype the {@link Bean} {@link BeanFactory#createPrototype(Class) prototype}.
   * @return a {@link Bean}-{@link BeanFactory#create(Bean) instance} of the given
   *         {@link BeanFactory#createPrototype(Class) prototype} with the properties mapped from the given
   *         {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  <T extends B> T toBean(P pojo, T prototype);

  /**
   *
   * @param <T> the generic type of the {@link net.sf.mmm.util.pojo.api.Pojo} to map to.
   * @param bean the {@link Bean} to map.
   * @param type the {@link Class} reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} to map to.
   * @return the a {@link Class#newInstance() new instance} of the given {@link Class} with the properties mapped from
   *         the given {@link Bean}.
   */
  <T extends P> T fromBean(B bean, Class<T> type);

}
