/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.collection.base.NodeCycle;
import net.sf.mmm.util.collection.base.NodeCycleException;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumType;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

/**
 * This class implements {@link EnumDefinition} for a java {@link Enum}.
 * 
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category} or {@link Void} for none.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class EnumEnumDefinition<TYPE extends Enum<?>, CATEGORY> implements EnumDefinition<TYPE, CATEGORY> {

  /** UID for serialization. */
  private static final long serialVersionUID = -5796877491769409263L;

  /** @see #getValue() */
  private String value;

  /** @see #getTitle() */
  private String title;

  /** @see #getCategory() */
  private EnumDefinition<CATEGORY, ?> category;

  /** @see #getEnumType() */
  private Class<TYPE> enumType;

  /** @see #getEnumValues() */
  private List<EnumType<?>> enumValues;

  /**
   * The constructor.
   * 
   * @param enumType - see {@link #getEnumType()}.
   */
  public EnumEnumDefinition(Class<TYPE> enumType) {

    this(enumType, new NodeCycle<Class<?>>(enumType));
  }

  /**
   * The constructor.
   * 
   * @param enumType - see {@link #getEnumType()}.
   * @param cycle is a {@link NodeCycle} instance to detect cyclic {@link #getCategory() categories}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private EnumEnumDefinition(Class<TYPE> enumType, NodeCycle<Class<?>> cycle) {

    super();
    this.enumType = enumType;
    this.value = getKey(enumType);
    Class<?> categoryType = null;
    boolean hasCategory = false;
    Boolean instanceofEnumType = null;
    TYPE[] enumConstants = enumType.getEnumConstants();
    this.enumValues = new ArrayList<EnumType<?>>(enumConstants.length);
    for (TYPE constant : enumConstants) {
      if (instanceofEnumType == null) {
        instanceofEnumType = Boolean.valueOf(constant instanceof EnumType);
        if (instanceofEnumType.booleanValue()) {
          hasCategory = (constant instanceof EnumTypeWithCategory);
        }
      }
      if (instanceofEnumType.booleanValue()) {
        if (hasCategory && categoryType == null) {
          EnumTypeWithCategory<?, ?> e = (EnumTypeWithCategory<?, ?>) constant;
          EnumType<?> categoryValue = e.getCategory();
          if (categoryValue != null) {
            categoryType = categoryValue.getClass();
          }
          // why do I need this downcast? Eclipse compiler bug?
          this.enumValues.add((EnumType<?>) e);
        } else {
          this.enumValues.add((EnumType<?>) constant);
        }
      } else {
        this.enumValues.add(new EnumEnumType(constant));
      }
    }
    if (categoryType == null) {
      this.category = null;
    } else {
      if (categoryType.isEnum()) {
        List<Class<?>> inverseCycle = cycle.getInverseCycle();
        if (inverseCycle.contains(categoryType)) {
          throw new NodeCycleException(cycle, EnumTypeWithCategory.class);
        }
        this.category = new EnumEnumDefinition(categoryType, cycle);
      } else {
        throw new ObjectMismatchException(categoryType, Enum.class, enumType);
      }
    }
  }

  /**
   * This method is called from the constructor to set the {@link #getValue() value}. By default it returns
   * {@link Class#getName()}. Override to change (e.g. to {@link Class#getSimpleName()}).
   * 
   * @param type is the {@link #getEnumType()}.
   * @return the {@link #getValue()} to use.
   */
  protected String getKey(Class<TYPE> type) {

    return type.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EnumDefinition<CATEGORY, ?> getCategory() {

    return this.category;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<TYPE> getEnumType() {

    return this.enumType;
  }

  /**
   * @return the {@link List} of the {@link Class#getEnumConstants() enum values} as {@link EnumType}s.
   */
  public List<? extends EnumType<?>> getEnumValues() {

    return this.enumValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMutable() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCachable() {

    return true;
  }

}
