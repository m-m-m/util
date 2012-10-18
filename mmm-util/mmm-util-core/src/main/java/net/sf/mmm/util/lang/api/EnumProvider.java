/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Iterator;
import java.util.List;

import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the interface for a generic provider of {@link EnumType enumerations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@ComponentSpecification
public interface EnumProvider extends Iterable<EnumDefinition<?, ?>> {

  /**
   * The same as {@link #getEnumDefinitions()}.
   * 
   * {@inheritDoc}
   */
  @Override
  Iterator<EnumDefinition<?, ?>> iterator();

  /**
   * This method gets an (unmodifiable) {@link Iterator} with all existing {@link EnumDefinition}s. In most
   * cases this is a static list, however it may also be dynamic.
   * 
   * @see #iterator()
   * 
   * @return the {@link Iterator} with all existing {@link EnumDefinition}s.
   */
  Iterator<EnumDefinition<?, ?>> getEnumDefinitions();

  /**
   * This method gets the {@link EnumDefinition} with the given <code>key</code>.
   * 
   * @param key is the {@link EnumDefinition#getValue() key} of the requested definition.
   * @return the requested {@link EnumDefinition}.
   * @throws ObjectNotFoundException if no such {@link EnumDefinition} exists.
   */
  EnumDefinition<?, ?> getEnumDefinition(String key) throws ObjectNotFoundException;

  /**
   * This method determines if the given {@link EnumDefinition} is available, so it is ensured that the
   * {@link #getEnumValues(EnumDefinition) enum values} can be retrieved.
   * 
   * @param enumDefinition is the {@link EnumDefinition} to check.
   * @return <code>true</code> if the {@link #getEnumValues(EnumDefinition) enum values} for the given
   *         {@link EnumDefinition} are available (already loaded / in cache), <code>false</code> otherwise.
   */
  boolean isAvailable(EnumDefinition<?, ?> enumDefinition);

  /**
   * This method triggers that the given {@link EnumDefinition}s are {@link #isAvailable(EnumDefinition)
   * available}.
   * 
   * @param enumDefinitions are the {@link EnumDefinition}s of the required enumerations.
   */
  void require(EnumDefinition<?, ?>... enumDefinitions);

  /**
   * This method triggers that the given {@link EnumDefinition}s are {@link #isAvailable(EnumDefinition)
   * available}. In advanced to {@link #require(EnumDefinition...)} it allows to specify a callback that gets
   * invoked after the required {@link EnumDefinition}s are {@link #isAvailable(EnumDefinition) available}.
   * This is useful in asynchronous environments (e.g. in client applications that have to receive the values
   * (asynchronously) from the server).
   * 
   * @param callback is a {@link Runnable} that will be {@link Runnable#run() called} after all given
   *        {@link EnumDefinition}s are {@link #isAvailable(EnumDefinition) available}.
   * @param enumDefinitions are the {@link EnumDefinition}s of the required enumerations.
   */
  void require(Runnable callback, EnumDefinition<?, ?>... enumDefinitions);

  /**
   * This method gets the {@link List} of {@link EnumType}-instances that represent the values of the given
   * {@link EnumDefinition}. In case the {@link EnumDefinition#getEnumType() enum type} implements
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated} the
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadDeprecated#isDeprecated() deprecated} enum values
   * will be excluded from the returned {@link List}.
   * 
   * @param enumDefinition is the {@link EnumDefinition} for which the values are requested.
   * @return the {@link List} of {@link EnumType}s.
   */
  List<? extends EnumType<?>> getEnumValues(EnumDefinition<?, ?> enumDefinition);

  /**
   * This method gets the {@link List} of {@link #getEnumValues(EnumDefinition) enum values} filtered by the
   * given {@link EnumTypeWithCategory#getCategory() categories}. So only these
   * {@link #getEnumValues(EnumDefinition) enum values} are returned that have a
   * {@link EnumTypeWithCategory#getCategory() category}
   * 
   * @param <CATEGORY> is the generic type of the {@link EnumDefinition#getCategory() category}
   *        {@link EnumDefinition#getEnumType() type}.
   * @param enumDefinition is the {@link EnumDefinition} for which the values are requested.
   * @param categories are the {@link EnumDefinition#getCategory() categories}.
   * @return the {@link List} of matching {@link EnumType}s.
   */
  <CATEGORY extends EnumType<?>> List<? extends EnumTypeWithCategory<?, CATEGORY>> getEnumValues(
      EnumDefinition<?, CATEGORY> enumDefinition, CATEGORY... categories);

  /**
   * This method clears the (potentially) cached {@link #getEnumValues(EnumDefinition) enum values} of the
   * given {@link EnumDefinition}. If the enum is {@link EnumDefinition#isMutable() dynamic} it is NOT
   * {@link #isAvailable(EnumDefinition) available} (anymore) after this operation.
   * 
   * @param enumDefinition is the {@link EnumDefinition} to clear.
   */
  void clear(EnumDefinition<?, ?> enumDefinition);

}
