/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DummyFooEntityView extends GenericEntity<Integer> {

  /**
   * @return the {@link DummyBarEntity} or <code>null</code>.
   */
  DummyBarEntityView getBar();

  /**
   * @return the number.
   */
  int getNumber();

}
