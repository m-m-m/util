/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement;

import net.sf.mmm.util.query.base.path.Alias;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface StatementFactory {

  /**
   * @param <E> the generic type of the queried object (typically a {@link net.sf.mmm.util.bean.api.EntityBean}).
   * @param alias the {@link Alias} for the queried object. See {@link Alias#ofBean(net.sf.mmm.util.bean.api.Bean)}.
   * @return the {@link SelectStatement}.
   */
  <E> SelectStatement<E, ?> selectFrom(Alias<E> alias);

}
