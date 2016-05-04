/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.util.List;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface of a factory of {@link ComposedValueConverter} instances. In case
 * {@link ComposedValueConverter} is used for different purposes there might be contradicting requirements. To prevent
 * undesired side-effects from one purpose of usage to the next you can {@link #createConverter(boolean, List) create}
 * custom instances.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@ComponentSpecification
public interface ComposedValueConverterFactory {

  /**
   * @return the default instance of {@link ComposedValueConverter} as it would be directly {@link javax.inject.Inject
   *         injected}.
   */
  ComposedValueConverter getDefaultConverter();

  /**
   * Creates a new custom instance of {@link ComposedValueConverter} with the given configuration.
   *
   * @param addDefaultConverters - {@code true} if all {@link ValueConverter}s of the {@link #getDefaultConverter()
   *        default converter} (technically all those registered via {@link net.sf.mmm.util.component.api.Cdi}) should
   *        be added to the new requested {@link ComposedValueConverter}, {@code false} otherwise (if only the given
   *        converters should be added).
   * @param converterList is the {@link List} of the {@link ValueConverter}s to add (register as plugin) to the new
   *        requested {@link ComposedValueConverter}.
   * @return the new {@link ComposedValueConverter} instance with the specified configuration.
   */
  ComposedValueConverter createConverter(boolean addDefaultConverters, List<ValueConverter<?, ?>> converterList);

}
