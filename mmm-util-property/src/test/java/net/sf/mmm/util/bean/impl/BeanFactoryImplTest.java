/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.example.ExampleBean;
import net.sf.mmm.util.bean.impl.example.ExamplePojoBean;
import net.sf.mmm.util.bean.impl.example.ExamplePropertyBean;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.BooleanPropertyImpl;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;
import net.sf.mmm.util.property.impl.StringPropertyImpl;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidatorRange;
import net.sf.mmm.util.validation.base.ComposedValidationFailure;
import net.sf.mmm.util.validation.base.ComposedValidator;
import net.sf.mmm.util.validation.base.ValidatorMandatory;
import net.sf.mmm.util.validation.base.text.ValidatorPattern;

/**
 * This is the test-case for {@link BeanFactoryImpl}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanFactoryImplTest extends Assertions {

  protected BeanFactory getBeanFactory() {

    return BeanFactoryImpl.getInstance();
  }

  @Test
  public void testExamplePropertyBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExamplePropertyBean bean = beanFactory.create(ExamplePropertyBean.class);
    verifyBean(bean);
    verifyExamplePropertyBean(bean);
  }

  @Test
  public void testExamplePojoBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExamplePojoBean bean = beanFactory.create(ExamplePojoBean.class);
    verifyBean(bean);
    verifyExamplePojoBean(bean);
  }

  @Test
  public void testExampleBean() {

    BeanFactory beanFactory = getBeanFactory();
    ExampleBean bean = beanFactory.create(ExampleBean.class);
    verifyBean(bean);
    verifyExamplePojoBean(bean);
    verifyExamplePropertyBean(bean);

    assertThat(bean.self()).isSameAs(bean);
    assertThat(bean.sayHi("Peter")).isEqualTo("Hi Peter");
  }

  private void verifyBean(Bean bean) {

    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(((BeanAccessBase) access).getBean()).isSameAs(bean);
    String undefinedProperty = "UndefinedProperty";
    assertThat(access.getProperty(undefinedProperty)).isNull();
    PojoPropertyNotFoundException error = null;
    try {
      access.getRequiredProperty(undefinedProperty);
    } catch (PojoPropertyNotFoundException e) {
      error = e;
    }
    assertThat(error).isNotNull().hasMessageContaining(undefinedProperty);
    for (GenericProperty<?> property : access.getProperties()) {
      String name = property.getName();
      assertThat(property).isSameAs(access.getProperty(property.getName()))
          .isSameAs(access.getRequiredProperty(name))
          .isSameAs(access.getOrCreateProperty(name, property.getType()));
    }
  }

  private void verifyExamplePropertyBean(ExamplePropertyBean bean) {

    assertThat(bean).isNotNull();
    assertThat(bean.Name()).isInstanceOf(StringPropertyImpl.class);
    assertThat(bean.Friend()).isInstanceOf(BooleanPropertyImpl.class);
    assertThat(bean.Orientation()).isInstanceOf(GenericPropertyImpl.class);

    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(access.isReadOnly()).isFalse();
    assertThat(access.isPrototype()).isFalse();
    assertThat(access.isDynamic()).isFalse();
    GenericProperty<?>[] properties = { bean.CountryCode(), bean.Name(), bean.Age(), bean.Friend(),
    bean.Orientation() };
    assertThat(access.getProperties()).hasSize(properties.length).contains(properties);

    String name = "magicName";
    bean.Name().setValue(name);
    assertThat(bean.Name().get()).isEqualTo(name);

    // validation
    // invalid mandatory fields
    ValidationFailure failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ComposedValidator.CODE);
    ComposedValidationFailure composedFailure = (ComposedValidationFailure) failure;
    assertThat(composedFailure.getFailureCount()).isEqualTo(2);
    for (ValidationFailure subFailure : composedFailure) {
      assertThat(subFailure.getCode()).isSameAs(ValidatorMandatory.CODE);
    }

    // valid
    bean.CountryCode().set("DE");
    bean.Age().set(5);
    failure = access.validate();
    assertThat(failure).isNull();

    // invalid country code
    bean.CountryCode().set("xyz");
    failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ValidatorPattern.CODE);

    // and also invalid age
    bean.Age().set(500);
    failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ComposedValidator.CODE);
    composedFailure = (ComposedValidationFailure) failure;
    Set<String> failureCodes = new HashSet<>();
    for (ValidationFailure subFailure : composedFailure) {
      failureCodes.add(subFailure.getCode());
    }
    String[] codes = { ValidatorPattern.CODE, AbstractValidatorRange.CODE };
    assertThat(failureCodes).hasSize(codes.length).contains(codes);

    // toString()
    ObjectMapper mapper = new ObjectMapper();
    Map<String, ?> beanJsonMap;
    String json = bean.toString();
    try {
      beanJsonMap = mapper.readValue(json, Map.class);
      for (GenericProperty<?> property : access.getProperties()) {
        Object valueProperty = property.getValue();
        if (valueProperty == null) {
          assertThat(beanJsonMap.containsKey(property.getName())).isFalse();
        } else {
          Object valueJson = beanJsonMap.get(property.getName());
          assertThat(valueJson).isEqualTo(valueProperty);
        }
      }
    } catch (Exception e) {
      fail("Failed to parse bean.toString() as JSON: " + json, e);
    }
  }

  private void verifyExamplePojoBean(ExamplePojoBean bean) {

    assertThat(bean).isNotNull();
    assertThat(bean.getName()).isNull();
    assertThat(bean.isFriend()).isFalse();
    assertThat(bean.getOrientation()).isNull();
    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(access.isReadOnly()).isFalse();
    assertThat(access.isPrototype()).isFalse();
    assertThat(access.isDynamic()).isFalse();
    String[] properties = { "CountryCode", "Name", "Age", "Friend", "Orientation" };
    int expectedSize = properties.length;
    assertThat(access.getProperties()).hasSize(expectedSize);
    Set<String> propertyNames = new HashSet<>();
    for (GenericProperty<?> property : access.getProperties()) {
      propertyNames.add(property.getName());
    }
    assertThat(propertyNames).hasSize(expectedSize).contains(properties);
  }

}
