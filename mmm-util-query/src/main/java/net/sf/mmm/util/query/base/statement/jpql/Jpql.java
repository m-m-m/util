/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.jpql;

import javax.persistence.Entity;

import net.sf.mmm.util.query.base.path.Alias;

/**
 * Helper class with {@link JpqlDialect JPQL} specific functions.
 *
 * @author hohwille
 * @since 8.0.0
 */
public final class Jpql {

  private Jpql() {
  }

  /**
   * @param <E> the generic type of the {@link Entity} {@link Class}.
   * @param entityClass the {@link Entity} {@link Class}.
   * @return the corresponding {@link Alias}.
   */
  public static <E> Alias<E> alias(Class<E> entityClass) {

    String entityName = entityClass.getSimpleName();
    Entity entity = entityClass.getAnnotation(Entity.class);
    if (entity != null) {
      String name = entity.name();
      if (!name.isEmpty()) {
        entityName = name;
      }
    }
    return new Alias<>(entityName, null, entityClass);
  }

}
