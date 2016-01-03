/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
import net.sf.mmm.util.validation.base.ValidatorPattern;

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
  }

  private void verifyBean(Bean bean) {

    BeanAccess access = bean.access();
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
    assertThat(access.getProperties()).hasSize(4).contains(bean.CountryCode(), bean.Name(), bean.Friend(),
        bean.Orientation());

    String name = "magicName";
    bean.Name().setValue(name);
    assertThat(bean.Name().get()).isEqualTo(name);

    // validation
    bean.CountryCode().set("DE");
    ValidationFailure failure = access.validate();
    assertThat(failure).isNull();
    bean.CountryCode().set("xyz");
    failure = access.validate();
    assertThat(failure).isNotNull();
    assertThat(failure.getCode()).isEqualTo(ValidatorPattern.CODE);
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
    String[] properties = new String[] { "CountryCode", "Name", "Friend", "Orientation" };
    int expectedSize = properties.length;
    assertThat(access.getProperties()).hasSize(expectedSize);
    Set<String> propertyNames = new HashSet<>();
    for (GenericProperty<?> property : access.getProperties()) {
      propertyNames.add(property.getName());
    }
    assertThat(propertyNames).hasSize(expectedSize).contains(properties);
  }

}
