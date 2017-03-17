/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.collection.base.ConcurrentHashMapFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.lang.api.BasicHelper;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated;

/**
 * This is the abstract base implementation of {@link EnumProvider}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEnumProvider extends AbstractLoggableComponent implements EnumProvider {

  private final Map<String, EnumContainer> enumContainerMap;

  private StringUtil stringUtil;

  /**
   * The constructor.
   */
  public AbstractEnumProvider() {

    this(ConcurrentHashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param mapFactory is the {@link MapFactory} used to create internal {@link Map}s for cache or registration.
   */
  public AbstractEnumProvider(MapFactory<?> mapFactory) {

    super();
    this.enumContainerMap = mapFactory.createGeneric();
  }

  /**
   * @return the {@link StringUtil} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the {@link StringUtil} to {@link Inject}.
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    this.stringUtil = stringUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    registerEnumDefinitions();
  }

  /**
   * Registers the enum definitions to support. Override this method to
   * {@link #registerEnumDefinition(AbstractEnumDefinition) add} custom {@link EnumDefinition}s or replace defaults.
   */
  protected void registerEnumDefinitions() {

    registerEnumDefinition(new BooleanEnumDefinition());
  }

  @Override
  public final Iterator<EnumDefinition<?, ?>> iterator() {

    return getEnumDefinitions();
  }

  @Override
  public Iterator<EnumDefinition<?, ?>> getEnumDefinitions() {

    return new EnumDefinitionIterator(this.enumContainerMap.values().iterator());
  }

  /**
   * This method registers the given {@code enumClass} as {@link EnumDefinition}.
   *
   * @param <ENUM> is the generic type of the {@link Enum}.
   *
   * @param enumClass is the {@link Enum} to register.
   */
  protected <ENUM extends Enum<ENUM>> void registerEnum(Class<ENUM> enumClass) {

    registerEnumDefinition(new EnumEnumDefinition<>(enumClass, this.stringUtil));
  }

  /**
   * This method registers the given {@link EnumDefinition}.
   *
   * @see #registerEnum(Class)
   *
   * @param enumDefinition is the {@link EnumDefinition} to register.
   */
  protected void registerEnumDefinition(AbstractEnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = new EnumContainer(enumDefinition);

    List<?> enumValues = enumDefinition.getEnumValues();
    if (enumValues != null) {
      container.setAllValues(enumValues);
    }

    String key = enumDefinition.getValue();
    EnumContainer old = this.enumContainerMap.get(key);
    if (old != null) {
      throw new DuplicateObjectException(enumDefinition, key, old.enumDefinition);
    }
    this.enumContainerMap.put(key, container);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <CATEGORY, ENUM extends EnumTypeWithCategory<?, CATEGORY>> EnumDefinition<ENUM, CATEGORY> getEnumDefinitionWithCategory(Class<? extends ENUM> enumType)
      throws ObjectNotFoundException {

    return (EnumDefinition<ENUM, CATEGORY>) getEnumDefinition(enumType);
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <TYPE> EnumDefinition<TYPE, ?> getEnumDefinition(Class<TYPE> enumType) throws ObjectNotFoundException {

    NlsNullPointerException.checkNotNull("enumType", enumType);
    String key = enumType.getName();
    EnumContainer container = this.enumContainerMap.get(key);
    if (isSupportEnumAutoRegistration() && (container == null) && enumType.isEnum()) {
      registerEnum((Class) enumType);
      container = this.enumContainerMap.get(key);
    }
    if (container == null) {
      throw new ObjectNotFoundException(EnumDefinition.class, key);
    }
    return (EnumDefinition<TYPE, ?>) container.enumDefinition;
  }

  /**
   * @return {@code true} to automatically {@link #registerEnum(Class)} requested {@link Enum} types (default),
   *         {@code false} otherwise (override to disable).
   */
  protected boolean isSupportEnumAutoRegistration() {

    return true;
  }

  /**
   * @param key is the {@link EnumDefinition#getValue() identifier} of the requested {@link EnumContainer}.
   * @return the requested {@link EnumContainer}.
   */
  protected EnumContainer getEnumContainer(String key) {

    EnumContainer container = this.enumContainerMap.get(key);
    if (container == null) {
      throw new ObjectNotFoundException(EnumDefinition.class, key);
    }
    return container;
  }

  @Override
  public EnumDefinition<?, ?> getEnumDefinition(String key) throws ObjectNotFoundException {

    EnumContainer container = getEnumContainer(key);
    return container.enumDefinition;
  }

  @Override
  public boolean isAvailable(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumContainerMap.get(enumDefinition.getValue());
    if (container != null) {
      return (container.allValues != null);
    }
    return false;
  }

  @Override
  public <ENUM> ENUM getEnumValue(Class<ENUM> enumType, String value, boolean required) throws IllegalCaseException {

    return getEnumValue(getEnumDefinition(enumType), value, required);
  }

  @Override
  public <ENUM> ENUM getEnumValue(EnumDefinition<ENUM, ?> enumDefinition, String value, boolean required) {

    if (value == null) {
      return null;
    }
    // for performance reasons it would be
    @SuppressWarnings("unchecked")
    List<ENUM> enumValues = (List<ENUM>) getEnumContainer(enumDefinition.getValue()).allValues;
    Formatter<ENUM> formatter = enumDefinition.getFormatter();
    for (ENUM enumVal : enumValues) {
      String string = formatter.format(enumVal);
      if (value.equals(string)) {
        return enumVal;
      }
    }
    boolean isEnum = enumDefinition.getEnumType().isEnum();
    ENUM result = null;
    String valueNormalized = normalizeStringRepresentation(value);
    for (ENUM enumVal : enumValues) {
      String string = formatter.format(enumVal);
      String stringNormalized = normalizeStringRepresentation(string);
      if (valueNormalized.equals(stringNormalized) || (isEnum && valueNormalized.equals(normalizeStringRepresentation(((Enum<?>) enumVal).name())))) {
        if (result == null) {
          result = enumVal;
        } else {
          getLogger().warn("Ambiguous constant '{}' for enum {}", value, enumDefinition.getEnumType());
          throw new IllegalCaseException(value);
        }
      }
    }
    if ((result == null) && required) {
      throw new IllegalCaseException(value);
    }
    return result;
  }

  /**
   * Normalizes the given {@link String}.
   *
   * @see StringUtil#toCamlCase(String)
   * @see EnumEnumDefinition.EnumDefaultFormatter
   *
   * @param value is the value to normalize.
   * @return the normalized value.
   */
  private String normalizeStringRepresentation(String value) {

    if (value == null) {
      return null;
    }
    return BasicHelper.toLowerCase(value.replaceAll("[-_ .]", ""));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <ENUM> List<ENUM> getEnumValues(EnumDefinition<ENUM, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumContainerMap.get(enumDefinition.getValue());
    NlsNullPointerException.checkNotNull(EnumContainer.class, container);
    if (container.activeValues == null) {
      loadEnumValues(container);
    }
    return (List<ENUM>) container.activeValues;
  }

  /**
   * @param container is the {@link EnumContainer} for which the {@link #getEnumValues(EnumDefinition)} should be
   *        loaded.
   */
  protected abstract void loadEnumValues(EnumContainer container);

  @Override
  @SuppressWarnings("unchecked")
  public <CATEGORY, ENUM extends EnumTypeWithCategory<?, CATEGORY>> List<ENUM> getEnumValues(EnumDefinition<ENUM, CATEGORY> enumDefinition,
      CATEGORY... categories) {

    assert (enumDefinition.getCategory() != null);
    List<?> allEnumValues = getEnumValues(enumDefinition);
    List<EnumTypeWithCategory<?, CATEGORY>> result = new LinkedList<>();

    Set<CATEGORY> categorySet = new HashSet<>();
    for (CATEGORY currentCategory : categories) {
      categorySet.add(currentCategory);
    }

    for (Object value : allEnumValues) {
      EnumTypeWithCategory<?, CATEGORY> enumValue = (EnumTypeWithCategory<?, CATEGORY>) value;
      CATEGORY category = enumValue.getCategory();
      if (categorySet.contains(category)) {
        result.add(enumValue);
      }
    }
    return (List<ENUM>) result;
  }

  @Override
  public void clear(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumContainerMap.get(enumDefinition.getValue());
    NlsNullPointerException.checkNotNull(EnumContainer.class, container);
    if (container.enumDefinition.isMutable()) {
      container.setAllValues(null);
    }
  }

  @Override
  public void require(EnumDefinition<?, ?>... enumDefinitions) {

    require(null, enumDefinitions);
  }

  /**
   * This inner class is a container for an {@link EnumDefinition} with its {@link #getValues() values}.
   */
  protected static class EnumContainer {

    private final EnumDefinition<?, ?> enumDefinition;

    private volatile List<?> allValues;

    private volatile List<?> activeValues;

    /**
     * The constructor.
     *
     * @param enumDefinition is the {@link EnumDefinition}.
     */
    public EnumContainer(EnumDefinition<?, ?> enumDefinition) {

      super();
      this.enumDefinition = enumDefinition;
    }

    /**
     * @return the {@link EnumDefinition}.
     */
    public EnumDefinition<?, ?> getEnumDefinition() {

      return this.enumDefinition;
    }

    /**
     * @param values the {@link EnumProvider#getEnumValues(EnumDefinition) enum values}.
     */
    public void setAllValues(List<?> values) {

      this.allValues = values;
      if (values == null) {
        this.activeValues = null;
      } else {
        List<Object> nonDeprecatedValues = new ArrayList<>(this.allValues.size());
        Boolean implementsDeprecated = null;
        for (Object value : this.allValues) {
          if (implementsDeprecated == null) {
            implementsDeprecated = Boolean.valueOf(value instanceof AttributeReadDeprecated);
          }
          boolean deprecated = false;
          if (implementsDeprecated.booleanValue()) {
            deprecated = ((AttributeReadDeprecated) value).isDeprecated();
          }
          if (!deprecated) {
            nonDeprecatedValues.add(value);
          }
        }
        this.activeValues = Collections.unmodifiableList(nonDeprecatedValues);
      }
    }

    /**
     * @return the {@link EnumProvider#getEnumValues(EnumDefinition) enum values} or {@code null} if NOT the
     *         {@link EnumProvider#isAvailable(EnumDefinition) available}.
     */
    public List<?> getValues() {

      return this.activeValues;
    }
  }

  /**
   * @see AbstractEnumProvider#getEnumDefinitions()
   */
  private static class EnumDefinitionIterator extends AbstractIterator<EnumDefinition<?, ?>> {

    private final Iterator<EnumContainer> containerIterator;

    /**
     * The constructor.
     *
     * @param containers is the {@link Iterator} of {@link EnumContainer}s to iterate.
     */
    public EnumDefinitionIterator(Iterator<EnumContainer> containers) {

      super();
      this.containerIterator = containers;
      findFirst();
    }

    @Override
    protected EnumDefinition<?, ?> findNext() {

      if (this.containerIterator.hasNext()) {
        return this.containerIterator.next().enumDefinition;
      }
      return null;
    }
  }

}
