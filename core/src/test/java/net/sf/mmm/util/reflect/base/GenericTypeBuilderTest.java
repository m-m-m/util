/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.List;

import javax.inject.Provider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * Test case for {@link GenericTypeBuilder}.
 *
 * @author hohwille
 */
public class GenericTypeBuilderTest extends Assertions {

  /**
   * Test of {@link GenericTypeBuilder} using {@link GenericTypeBuilder#with(GenericTypeVariable, GenericType)} and
   * {@link GenericTypeBuilder#build()}.
   */
  @Test
  public void testBuilder() {

    // given
    SimpleGenericTypeImpl<String> stringType = new SimpleGenericTypeImpl<>(String.class);
    // when
    GenericType<List<Provider<String>>> listType = typeOfListOfProviderOf(stringType);
    // then
    assertThat(listType.getRetrievalClass()).isEqualTo(List.class);
    GenericType<?> componentType = listType.getComponentType();
    assertThat(componentType.getRetrievalClass()).isEqualTo(Provider.class);
    GenericType<?> typeArgument = componentType.getTypeArgument(0);
    assertThat(typeArgument).isEqualTo(stringType);
    assertThat(componentType).isEqualTo(listType.getTypeArgument(0));
  }

  private static <T> GenericType<List<Provider<T>>> typeOfListOfProviderOf(GenericType<T> type) {

    return new GenericTypeBuilder<List<Provider<T>>>() { // anonymous
    }.with(new GenericTypeVariable<T>() { // anonymous
    }, type).build();
  }

}
