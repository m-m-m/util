/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.mapping;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.EntityBean;

/**
 * This is the interface used to map a fixed type of <em>document</em> (generic container object) {@link #toBean(Object)
 * to} and {@link #fromBean(Bean) from} instances of {@link Bean}.
 *
 * @param <D> the type of the document (generic container object) to map (e.g. a document from a CMS or NoSQL-database).
 * @param <B> the base type of the {@link Bean} to map (e.g. {@link Bean} or {@link EntityBean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface DocumentBeanMapper<D, B extends Bean> {

  /**
   * @param <T> the generic type of the {@link Bean} to map to.
   * @param document the generic container object to map.
   * @return a corresponding {@link Bean}-{@link BeanFactory#create(Bean) instance} with the properties mapped from the
   *         given container object.
   */
  <T extends B> T toBean(D document);

  /**
   * @param bean the {@link Bean} to map.
   * @return the a {@link Class#newInstance() new instance} of the document (generic container object) with the
   *         properties mapped from the given {@link Bean}.
   */
  D fromBean(B bean);

}
