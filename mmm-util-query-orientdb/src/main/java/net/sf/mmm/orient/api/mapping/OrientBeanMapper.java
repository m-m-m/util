/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.api.mapping;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchemaProxy;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.orient.api.bean.OrientBean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.mapping.DocumentBeanMapper;

/**
 * This is the interface for mapping between {@link ODocument} and {@link OrientBean} and vice versa.
 *
 * @author hohwille
 * @since 1.0.0
 */
public interface OrientBeanMapper extends DocumentBeanMapper<ODocument, OrientBean> {

  /**
   * Synchronizes the {@link OSchemaProxy OrientDB schema} with the {@link OrientBean Java data model}.
   *
   * @see #syncClass(OClass)
   *
   * @param schema the {@link OSchemaProxy}.
   */
  void syncSchema(OSchemaProxy schema);

  /**
   * Synchronizes a {@link OClass OrientDB class} with the according {@link OrientBean}.
   *
   * @param oclass the {@link OClass} to synchronize.
   */
  void syncClass(OClass oclass);

  /**
   * @param oClass the {@link OClass OrientDB class} to map.
   * @return the corresponding {@link OrientBean} {@link BeanFactory#createPrototype(Class) prototype}.
   */
  OrientBean getBeanPrototype(OClass oClass);

  /**
   * @param <B> the generic type of the {@link OrientBean}.
   * @param type the {@link Class} reflecting the {@link OrientBean}-interface to get the prototype for.
   * @return the corresponding {@link OrientBean} {@link BeanFactory#createPrototype(Class) prototype}.
   */
  <B extends OrientBean> B getBeanPrototype(Class<B> type);

  /**
   * @param beanClass the {@link OrientBean}-{@link Class}.
   * @return the corresponding {@link OClass}.
   */
  OClass getOClass(Class<? extends OrientBean> beanClass);

  // /**
  // * @see #map2Bean(ODocument, OrientBean)
  // * @param <BEAN> the generic type of the {@link OrientBean} to map to.
  // * @param document the {@link ODocument OrientDB document} to map.
  // * @return the new mapped {@link OrientBean} populated with the data from the given {@code document}.
  // */
  // default <BEAN extends OrientBean> BEAN map2Bean(ODocument document) {
  //
  // return map2Bean(document, null);
  // }
  //
  // /**
  // * @param <BEAN> the generic type of the {@link OrientBean} to map to.
  // * @param document the {@link ODocument OrientDB document} to map.
  // * @param bean the existing {@link OrientBean} instance to update from the given {@code document} or {@code null} to
  // * create and return a new {@link OrientBean} instance.
  // * @return the mapped {@link OrientBean} populated with the data from the given {@code document}.
  // */
  // <BEAN extends OrientBean> BEAN map2Bean(ODocument document, BEAN bean);
  //
  // /**
  // * @see #map2Document(OrientBean, ODocument)
  // * @param bean the {@link OrientBean} to map.
  // * @return the new mapped {@link ODocument OrientDB document} populated with the data from the given {@code bean}.
  // */
  // default ODocument map2Document(OrientBean bean) {
  //
  // return map2Document(bean, null);
  // }
  //
  // /**
  // * @param bean the {@link OrientBean} to map.
  // * @param document the existing {@link ODocument OrientDB document} instance to update from the given {@code bean}
  // or
  // * {@code null} to create and return a new {@link ODocument} instance.
  // * @return the mapped {@link ODocument OrientDB document} populated with the data from the given {@code bean}.
  // */
  // ODocument map2Document(OrientBean bean, ODocument document);

}
