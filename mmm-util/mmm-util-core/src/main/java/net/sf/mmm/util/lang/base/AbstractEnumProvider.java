/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.EnumDefinition;
import net.sf.mmm.util.lang.api.EnumProvider;
import net.sf.mmm.util.lang.api.EnumType;
import net.sf.mmm.util.lang.api.EnumTypeWithCategory;
import net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link EnumProvider}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEnumProvider extends AbstractLoggableComponent implements EnumProvider {

  /** @see #getEnumDefinition(String) */
  private final Map<String, EnumContainer> enumDefinitionMap;

  /**
   * The constructor.
   */
  public AbstractEnumProvider() {

    super();
    this.enumDefinitionMap = new HashMap<String, EnumContainer>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Iterator<EnumDefinition<?, ?>> iterator() {

    return getEnumDefinitions();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<EnumDefinition<?, ?>> getEnumDefinitions() {

    return new EnumDefinitionIterator(this.enumDefinitionMap.values().iterator());
  }

  /**
   * This method registers the given <code>enumClass</code> as {@link EnumDefinition}.
   * 
   * @param <ENUM> is the generic type of the {@link Enum}.
   * 
   * @param enumClass is the {@link Enum} to register.
   */
  protected <ENUM extends Enum<?>> void registerEnum(Class<ENUM> enumClass) {

    registerEnumDefinition(new EnumEnumDefinition<ENUM, Object>(enumClass));
  }

  /**
   * This method registers the given {@link EnumDefinition}.
   * 
   * @param enumDefinition is the {@link EnumDefinition} to register.
   */
  protected void registerEnumDefinition(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = new EnumContainer(enumDefinition);
    String key = enumDefinition.getValue();
    EnumContainer old = this.enumDefinitionMap.put(key, container);
    if (old != null) {
      throw new DuplicateObjectException(enumDefinition, key, old.enumDefinition);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EnumDefinition<?, ?> getEnumDefinition(String key) throws ObjectNotFoundException {

    EnumContainer container = this.enumDefinitionMap.get(key);
    if (container == null) {
      throw new ObjectNotFoundException(EnumDefinition.class, key);
    }
    return container.enumDefinition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAvailable(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumDefinitionMap.get(enumDefinition.getValue());
    if (container != null) {
      return (container.allValues != null);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends EnumType<?>> getEnumValues(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumDefinitionMap.get(enumDefinition.getValue());
    NlsNullPointerException.checkNotNull(EnumContainer.class, container);
    if (container.activeValues == null) {
      loadEnumValues(container);
    }
    return container.activeValues;
  }

  /**
   * @param container is the {@link EnumContainer} for which the {@link #getEnumValues(EnumDefinition)} should
   *        be loaded.
   */
  protected abstract void loadEnumValues(EnumContainer container);

  /**
   * {@inheritDoc}
   */
  @Override
  public <CATEGORY extends EnumType<?>> List<? extends EnumTypeWithCategory<?, CATEGORY>> getEnumValues(
      EnumDefinition<?, CATEGORY> enumDefinition, CATEGORY... categories) {

    assert (enumDefinition.getCategory() != null);
    List<? extends EnumType<?>> allEnumValues = getEnumValues(enumDefinition);
    List<EnumTypeWithCategory<?, CATEGORY>> result = new LinkedList<EnumTypeWithCategory<?, CATEGORY>>();

    Set<CATEGORY> categorySet = new HashSet<CATEGORY>();
    for (CATEGORY currentCategory : categories) {
      categorySet.add(currentCategory);
    }

    for (EnumType<?> value : allEnumValues) {
      @SuppressWarnings("unchecked")
      EnumTypeWithCategory<?, CATEGORY> enumValue = (EnumTypeWithCategory<?, CATEGORY>) value;
      CATEGORY category = enumValue.getCategory();
      if (categorySet.contains(category)) {
        result.add(enumValue);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear(EnumDefinition<?, ?> enumDefinition) {

    NlsNullPointerException.checkNotNull(EnumDefinition.class, enumDefinition);
    EnumContainer container = this.enumDefinitionMap.get(enumDefinition.getValue());
    NlsNullPointerException.checkNotNull(EnumContainer.class, container);
    if (container.enumDefinition.isMutable()) {
      container.setAllValues(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void require(EnumDefinition<?, ?>... enumDefinitions) {

    require(null, enumDefinitions);
  }

  /**
   * This inner class is a container for an {@link EnumDefinition} with its {@link #getValues() values}.
   */
  protected static class EnumContainer {

    /** @see #getEnumDefinition() */
    private final EnumDefinition<?, ?> enumDefinition;

    /** @see #setAllValues(List) */
    private volatile List<? extends EnumType<?>> allValues;

    /** @see #getValues() */
    private volatile List<? extends EnumType<?>> activeValues;

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
    public void setAllValues(List<? extends EnumType<?>> values) {

      this.allValues = values;
      if (values != null) {
        List<EnumType<?>> nonDeprecatedValues = new ArrayList<EnumType<?>>(this.allValues.size());
        Boolean implementsDeprecated = null;
        for (EnumType<?> value : this.allValues) {
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
      } else {
        this.activeValues = null;
      }
    }

    /**
     * @return the {@link EnumProvider#getEnumValues(EnumDefinition) enum values} or <code>null</code> if NOT
     *         the {@link EnumProvider#isAvailable(EnumDefinition) available}.
     */
    public List<? extends EnumType<?>> getValues() {

      return this.activeValues;
    }
  }

  /**
   * @see AbstractEnumProvider#getEnumDefinitions()
   */
  private static class EnumDefinitionIterator extends AbstractIterator<EnumDefinition<?, ?>> {

    /** @see #findNext() */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected EnumDefinition<?, ?> findNext() {

      if (this.containerIterator.hasNext()) {
        return this.containerIterator.next().enumDefinition;
      }
      return null;
    }
  }

}
