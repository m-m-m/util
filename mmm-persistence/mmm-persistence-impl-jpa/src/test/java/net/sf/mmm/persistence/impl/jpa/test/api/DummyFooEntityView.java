/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the read only interface for the {@link DummyFooEntity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface DummyFooEntityView extends GenericEntity<Integer> {

  /** The name of the property {@link #getBar() bar}. */
  String PROPERTY_BAR = "bar";

  /** The name of the property {@link #getNumber() number}. */
  String PROPERTY_NUMBER = "number";

  /** The typed property for {@link #getNumber() number}. */
  TypedProperty<Integer> TYPED_PROPERTY_NUMBER = new TypedProperty<Integer>(PROPERTY_NUMBER);

  /**
   * @return the {@link DummyBarEntity} or <code>null</code>.
   */
  DummyBarEntityView getBar();

  /**
   * @return the number.
   */
  int getNumber();

}
