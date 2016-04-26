/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.link;

import java.util.Objects;

import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.bean.api.link.Link;

/**
 * Abstract base implementation of {@link Link}.
 *
 * @param <ID> the generic type of the {@link #getId() unique ID}.
 * @param <E> the generic type of the {@link #getTarget() linked} {@link EntityBean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractLink<ID, E extends EntityBean<ID>> implements Link<ID, E> {

  /**
   * The constructor.
   */
  public AbstractLink() {
    super();
  }

  @Override
  public int hashCode() {

    ID id = getId();
    if (id == null) {
      return 0;
    }
    return ~id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    Object id = ((AbstractLink<?, ?>) obj).getId();
    if (!Objects.equals(getId(), id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {

    return getId().toString();
  }

}
