/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.mmm.test.ObjectHelper;
import net.sf.mmm.util.bean.api.AbstractBeanFactory;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.impl.example.CountryCodeProperty;
import net.sf.mmm.util.bean.impl.example.ExamplePojo;
import net.sf.mmm.util.bean.impl.example.ExamplePojoBean;
import net.sf.mmm.util.bean.impl.example.ExamplePropertyBean;
import net.sf.mmm.util.lang.api.Orientation;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.lang.WritableStringProperty;
import net.sf.mmm.util.property.api.math.WritableIntegerProperty;
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
 */
public abstract class AbstractBeanTest extends Assertions {

  protected BeanFactory getBeanFactory() {

    return BeanFactoryImpl.getInstance();
  }

  protected void verifyBean(Bean bean, BeanFactory beanFactory) {

    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(((BeanAccessBase<?>) access).getBean()).isSameAs(bean);
    String undefinedProperty = "UndefinedProperty";
    assertThat(access.getProperty(undefinedProperty)).isNull();
    PojoPropertyNotFoundException error = null;
    try {
      access.getRequiredProperty(undefinedProperty);
    } catch (PojoPropertyNotFoundException e) {
      error = e;
    }
    assertThat(error).isNotNull().hasMessageContaining(undefinedProperty);
    for (WritableProperty<?> property : access.getProperties()) {
      String name = property.getName();
      assertThat(property).isSameAs(access.getProperty(property.getName())).isSameAs(access.getRequiredProperty(name))
          .isSameAs(access.getOrCreateProperty(name, property.getType()));
    }

    // test equals
    Bean copy = beanFactory.copy(bean);
    assertThat(copy).isNotSameAs(bean);
    ObjectHelper.checkEqualsAndHashCode(bean, copy, true);
  }

  protected void verifyExamplePropertyBean(ExamplePropertyBean bean, AbstractBeanFactory beanFactory) {

    assertThat(bean).isNotNull();
    WritableStringProperty propertyName = bean.Name();
    assertThat(propertyName).isInstanceOf(StringProperty.class);
    BooleanProperty propertyFriend = bean.Friend();
    assertThat(propertyFriend).isInstanceOf(BooleanProperty.class);
    WritableProperty<Orientation> propertyOrientation = bean.Orientation();
    assertThat(propertyOrientation).isInstanceOf(GenericProperty.class);

    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(access.isReadOnly()).isFalse();
    assertThat(access.isPrototype()).isFalse();
    assertThat(access.isVirtual()).isFalse();
    CountryCodeProperty countryCode = bean.CountryCode();
    WritableIntegerProperty propertyAge = bean.Age();
    WritableProperty<?>[] properties = { countryCode, propertyName, propertyAge, propertyFriend, propertyOrientation };
    assertThat(access.getProperties()).contains(properties);

    assertThat(access.getPropertyNameForAlias("Alias")).isEqualTo("Name");
    assertThat(access.getRequiredProperty("Alias")).isSameAs(access.getRequiredProperty("Name"));

    // validation
    // invalid mandatory fields
    ValidationFailure failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ComposedValidator.CODE);
    ComposedValidationFailure composedFailure = (ComposedValidationFailure) failure;
    String[] mandatoryProperties;
    if (bean instanceof ExamplePojo) {
      mandatoryProperties = new String[] { "Name", "Age", "CountryCode", "Orientation" };
    } else {
      mandatoryProperties = new String[] { "Name", "Age", "CountryCode" };
    }
    assertThat(composedFailure.getFailureCount()).isEqualTo(mandatoryProperties.length);
    Set<String> failureSources = new HashSet<>();
    for (ValidationFailure subFailure : composedFailure) {
      assertThat(subFailure.getCode()).isSameAs(ValidatorMandatory.CODE);
      failureSources.add(subFailure.getSource());
    }
    assertThat(failureSources).containsOnly(mandatoryProperties);

    String name = "magicName";
    propertyName.setValue(name);
    assertThat(propertyName.getValue()).isEqualTo(name);

    propertyOrientation.setValue(Orientation.HORIZONTAL);
    assertThat(propertyOrientation.getValue()).isEqualTo(Orientation.HORIZONTAL);

    // valid
    countryCode.set("DE");
    propertyAge.set(5);
    failure = access.validate();
    assertThat(failure).isNull();

    // invalid country code
    countryCode.set("xyz");
    failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ValidatorPattern.CODE);

    // and also invalid age
    propertyAge.set(500);
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
      for (WritableProperty<?> property : access.getProperties()) {
        Object valueProperty = property.getValue();
        if (valueProperty == null) {
          assertThat(beanJsonMap.containsKey(property.getName())).isFalse();
        } else {
          Object valueJson = beanJsonMap.get(property.getName());
          if (valueProperty instanceof Enum) {
            assertThat(valueJson).isEqualTo(valueProperty.toString());
          } else {
            assertThat(valueJson).isEqualTo(valueProperty);
          }
        }
      }
    } catch (Exception e) {
      fail("Failed to parse bean.toString() as JSON: " + json, e);
    }

    // equals and hashCode
    ExamplePropertyBean copy = beanFactory.copy(bean);
    copy.Name().setValue("Heinz");
    ObjectHelper.checkEqualsAndHashCode(bean, copy, false);
  }

  protected void verifyExamplePojoBean(ExamplePojoBean bean) {

    assertThat(bean).isNotNull();
    assertThat(bean.getName()).isNull();
    assertThat(bean.isFriend()).isFalse();
    assertThat(bean.getOrientation()).isNull();
    BeanAccess access = bean.access();
    assertThat(access).isNotNull();
    assertThat(access.isReadOnly()).isFalse();
    assertThat(access.isPrototype()).isFalse();
    assertThat(access.isDynamic()).isFalse();
    String[] propertyNameArray = { "CountryCode", "Name", "Age", "Friend", "Orientation" };
    Set<String> propertyNames = access.getPropertyNames();
    assertThat(propertyNames).contains(propertyNameArray);
    Iterable<WritableProperty<?>> properties = access.getProperties();
    assertThat(properties).hasSize(propertyNames.size());
    Set<String> propertyNameSet = new HashSet<>();
    for (WritableProperty<?> property : properties) {
      propertyNameSet.add(property.getName());
    }
    assertThat(propertyNameSet).isEqualTo(propertyNames);
  }

}
