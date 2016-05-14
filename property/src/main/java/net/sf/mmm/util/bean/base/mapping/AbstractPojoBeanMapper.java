/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.mapping;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.bean.api.mapping.DocumentBeanMapper;
import net.sf.mmm.util.bean.api.mapping.PojoBeanMapper;

/**
 * This is the abstract base implementation of {@link DocumentBeanMapper}.
 *
 * @param <D> the type of the document (generic container object) to map (e.g. a document from a CMS or NoSQL-database).
 * @param <B> the base type of the {@link Bean} to convert (e.g. {@link Bean} or {@link EntityBean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractPojoBeanMapper<D, B extends Bean> extends AbstractBeanMapper<D, B>
    implements PojoBeanMapper<D, B> {

  /**
   * The constructor.
   */
  public AbstractPojoBeanMapper() {
    super();
  }

}
