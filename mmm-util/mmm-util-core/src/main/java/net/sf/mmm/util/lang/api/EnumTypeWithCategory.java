/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for an {@link EnumType} that has a {@link #getCategory() category}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface EnumTypeWithCategory<V, CATEGORY extends EnumType<?>> extends Datatype<V> {

  /**
   * This method gets the {@link EnumType} of the category of this enum instance. The category typically
   * expresses a <em>belongs to</em> relationship.<br/>
   * E.g. a <em>state</em> could be an {@link EnumType} that has another {@link EnumType} called
   * <em>country</em> as category. Further, a <code>city</code> could be another {@link EnumType} that has
   * <code>state</code> as category.
   * 
   * @see EnumDefinition#getCategory()
   * 
   * @return the {@link #getValue() ID} of the {@link EnumType} that represents the
   *         {@link EnumDefinition#getCategory() category} of this instance.
   */
  CATEGORY getCategory();

}
