/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.collection.base.NodeCycle;
import net.sf.mmm.util.collection.base.NodeCycleException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.lang.api.StringUtil;

/**
 * This class implements {@link EnumDefinition} for a java {@link Enum}.<br/>
 * <b>ATTENTION:</b><br/>
 * Please only use this class to build an {@link net.sf.mmm.util.lang.api.EnumProvider}. From out-side use
 * {@link net.sf.mmm.util.lang.api.EnumProvider#getEnumDefinition(Class)} instead.
 *
 * @param <TYPE> is the generic type of the {@link #getEnumType() enum type}.
 * @param <CATEGORY> is the generic type of the {@link #getCategory() category} or {@link Void} for none.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class EnumEnumDefinition<TYPE extends Enum<TYPE>, CATEGORY> extends AbstractEnumDefinition<TYPE, CATEGORY> {

  /** UID for serialization. */
  private static final long serialVersionUID = -5796877491769409263L;

  /** @see #getValue() */
  private String value;

  /** @see #toString() */
  private String title;

  /** @see #getCategory() */
  private EnumDefinition<CATEGORY, ?> category;

  /** @see #getEnumType() */
  private Class<TYPE> enumType;

  /** @see #getEnumValues() */
  private List<TYPE> enumValues;

  /** @see EnumDefaultFormatter */
  private StringUtil stringUtil;

  /**
   * The constructor. A potential {@link #getCategory() category} is automatically detected.
   *
   * @param enumType - see {@link #getEnumType()}.
   * @param stringUtil is the {@link StringUtil} instance to use.
   */
  public EnumEnumDefinition(Class<TYPE> enumType, StringUtil stringUtil) {

    this(enumType, new NodeCycle<Class<?>>(enumType), null, stringUtil);
  }

  /**
   * The constructor.
   *
   * @param enumType - see {@link #getEnumType()}.
   * @param category is the explicit {@link #getCategory() category} (if not realized by an {@link Enum} or
   *        auto-detection not desired for other reasons).
   * @param stringUtil is the {@link StringUtil} instance to use.
   */
  public EnumEnumDefinition(Class<TYPE> enumType, EnumDefinition<CATEGORY, ?> category, StringUtil stringUtil) {

    this(enumType, new NodeCycle<Class<?>>(enumType), category, stringUtil);
  }

  /**
   * The constructor.
   *
   * @param enumType - see {@link #getEnumType()}.
   * @param cycle is a {@link NodeCycle} instance to detect cyclic {@link #getCategory() categories}.
   * @param category is the {@link #getCategory() category} or <code>null</code> for auto-detect / none.
   * @param stringUtil is the {@link StringUtil} instance to use.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private EnumEnumDefinition(Class<TYPE> enumType, NodeCycle<Class<?>> cycle, EnumDefinition<CATEGORY, ?> category,
      StringUtil stringUtil) {

    super();
    this.enumType = enumType;
    this.value = getKey(enumType);
    this.stringUtil = stringUtil;
    if (SimpleDatatype.class.isAssignableFrom(enumType)) {
      setFormatter(new EnumTypeFormatter());
    } else {
      setFormatter(new EnumDefaultFormatter());
    }
    Class<?> categoryType = null;
    Boolean hasCategory = null;
    if (category != null) {
      // hasCategory = Boolean.TRUE;
      categoryType = category.getEnumType();
      if (categoryType == null) {
        throw new NlsNullPointerException("category.enumType");
      }
    }
    TYPE[] enumConstants = enumType.getEnumConstants();
    this.enumValues = new ArrayList<TYPE>(enumConstants.length);
    for (TYPE constant : enumConstants) {
      if (hasCategory == null) {
        hasCategory = Boolean.valueOf(constant instanceof EnumTypeWithCategory);
        if (!hasCategory.booleanValue() && (category != null)) {
          throw new NlsIllegalArgumentException(category, "category for " + enumType);
        }
      } else {
        assert ((constant instanceof EnumTypeWithCategory) == hasCategory.booleanValue());
      }
      if (hasCategory.booleanValue() && (categoryType == null)) {
        EnumTypeWithCategory<?, ?> e = (EnumTypeWithCategory<?, ?>) constant;
        Object categoryValue = e.getCategory();
        if (categoryValue != null) {
          categoryType = categoryValue.getClass();
        }
      }
      this.enumValues.add(constant);
    }
    this.enumValues = Collections.unmodifiableList(this.enumValues);
    if (category != null) {
      this.category = category;
    } else if (categoryType == null) {
      this.category = null;
    } else if (categoryType.isEnum()) {
      List<Class<?>> inverseCycle = cycle.getInverseCycle();
      if (inverseCycle.contains(categoryType)) {
        throw new NodeCycleException(cycle, EnumTypeWithCategory.class);
      }
      this.category = new EnumEnumDefinition(categoryType, cycle, null, this.stringUtil);
    } else {
      throw new ObjectMismatchException(categoryType, Enum.class, enumType);
    }
  }

  /**
   * This method is called from the constructor to set the {@link #getValue() value}. By default it returns
   * the {@link Class#getName() qualified name} of {@link #getEnumType()}. Override to change (e.g. to
   * {@link Class#getSimpleName() simple name}).
   *
   * @param type is the {@link #getEnumType()}.
   * @return the {@link #getValue()} to use.
   */
  @Override
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
  public String toString() {

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
   * @return the {@link List} of the {@link Class#getEnumConstants() enum values}.
   */
  @Override
  public List<TYPE> getEnumValues() {

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

  /**
   * The default {@link net.sf.mmm.util.lang.api.Formatter} for {@link Enum}s that implement
   * {@link SimpleDatatype}.
   */
  protected class EnumTypeFormatter extends AbstractFormatter<TYPE> {

    /**
     * The constructor.
     */
    public EnumTypeFormatter() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFormat(TYPE enumValue, Appendable buffer) throws IOException {

      SimpleDatatype<?> datatype = (SimpleDatatype<?>) enumValue;
      Object result = datatype.getValue();
      if (result == null) {
        buffer.append(formatNull());
      } else {
        buffer.append(result.toString());
      }
    }
  }

  /**
   * The default {@link net.sf.mmm.util.lang.api.Formatter} for {@link Enum}s.
   */
  protected class EnumDefaultFormatter extends AbstractFormatter<TYPE> {

    /**
     * The constructor.
     */
    public EnumDefaultFormatter() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFormat(TYPE enumValue, Appendable buffer) throws IOException {

      String result = EnumEnumDefinition.this.stringUtil.toCamlCase(enumValue.name());
      buffer.append(result);
    }
  }

}
