/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorBuilderObject;
import net.sf.mmm.util.validation.base.ValidatorMandatory;
import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.value.api.NumberRange;

/**
 * Test-case for {@link BeanValidationProcessor}.
 */
@SuppressWarnings({ "boxing", "rawtypes", "javadoc" })
public class BeanValidationProcessorTest extends Assertions {

  protected BeanValidationProcessor getBeanValidationProcessor() {

    BeanValidationProcessorImpl processor = new BeanValidationProcessorImpl();
    processor.initialize();
    return processor;
  }

  protected ObjectValidatorBuilder<Object, Void, ?> createRegistry() {

    return new ValidatorBuilderObject<>(null);
  }

  @Test
  public void testNotNullAndSize() throws Exception {

    // given
    BeanValidationProcessor processor = getBeanValidationProcessor();
    Method method = SampleBean.class.getMethod("getLong", ReflectionUtilLimited.NO_PARAMETERS);
    ObjectValidatorBuilder<Object, Void, ?> validatorRegistry = createRegistry();
    // when
    processor.processConstraints(method, validatorRegistry, new SimpleGenericTypeImpl<>(Long.class));
    AbstractValidator<Object> validator = validatorRegistry.build();
    // then
    assertThat(validator).isNotNull();
    assertThat(validator.isMandatory()).isTrue();
    assertThat(validator).isInstanceOf(ComposedValidator.class);
    ComposedValidator composedValidator = (ComposedValidator) validator;
    assertThat(composedValidator.getValidatorCount()).isEqualTo(2);
    assertThat(composedValidator.getValidator(0)).isSameAs(ValidatorMandatory.getInstance());
    AbstractValidator sizeValidator = composedValidator.getValidator(1);
    assertThat(sizeValidator).isInstanceOf(ValidatorRange.class);
    ValidatorRange rangeValidator = (ValidatorRange) sizeValidator;
    assertThat(rangeValidator.getRange()).isEqualTo(new NumberRange(-5, 5));
  }

  @Test
  public void testDecimalMinMax() throws Exception {

    // given
    BeanValidationProcessor processor = getBeanValidationProcessor();
    Method method = SampleBean.class.getMethod("getBigDecimal", ReflectionUtilLimited.NO_PARAMETERS);
    ObjectValidatorBuilder<Object, Void, ?> validatorRegistry = createRegistry();
    // when
    processor.processConstraints(method, validatorRegistry, new SimpleGenericTypeImpl<>(Long.class));
    AbstractValidator<Object> validator = validatorRegistry.build();
    // then
    assertThat(validator).isNotNull();
    assertThat(validator.isMandatory()).isFalse();
    assertThat(validator).isInstanceOf(ValidatorRange.class);
    ValidatorRange rangeValidator = (ValidatorRange) validator;
    assertThat(rangeValidator.getRange()).isEqualTo(new NumberRange(new BigDecimal("-1.5e+18"), new BigDecimal("1e+18")));
  }

  public interface SampleBean {

    @NotNull
    @Size(min = -5, max = 5)
    Long getLong();

    @DecimalMin(value = "-1.5e+18")
    @DecimalMax(value = "1e+18")
    BigDecimal getBigDecimal();
  }

}
