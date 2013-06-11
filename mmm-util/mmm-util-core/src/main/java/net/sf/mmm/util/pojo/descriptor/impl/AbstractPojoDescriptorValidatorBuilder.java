/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorValidatorBuilder;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ComposedValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the abstract base implementation of {@link PojoDescriptorValidatorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractPojoDescriptorValidatorBuilder extends AbstractLoggableComponent implements
    PojoDescriptorValidatorBuilder {

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorValidatorBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
  }

  /**
   * @return the {@link ReflectionUtil} instance to use.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildValidator(AbstractPojoDescriptor<?> descriptor) {

    Collection<? extends AbstractPojoPropertyDescriptor> propertyDescriptors = descriptor.getPropertyDescriptors();
    for (AbstractPojoPropertyDescriptor propertyDescriptor : propertyDescriptors) {
      buildValidator(propertyDescriptor);
    }
  }

  /**
   * Like {@link #buildValidator(AbstractPojoDescriptor)} but for {@link AbstractPojoPropertyDescriptor}.
   * 
   * @param propertyDescriptor is the {@link AbstractPojoPropertyDescriptor}.
   */
  protected void buildValidator(AbstractPojoPropertyDescriptor propertyDescriptor) {

    // TODO: actually to be compliant we need to ensure that we consider both fields and getters
    // this might only consider one of these...
    PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
    propertyDescriptor.setValidator(createValidator(getter));
  }

  /**
   * Creates the {@link ValueValidator} for the given <code>getter</code>.
   * 
   * @param getter is the {@link PojoPropertyAccessorNonArg} representing the GET-Accessor (getter method of
   *        field access). May be <code>null</code>.
   * @return the {@link ValueValidator} for the given <code>getter</code>.
   */
  protected ValueValidator<?> createValidator(PojoPropertyAccessorNonArg getter) {

    ValidatorCollector validatorCollector = new ValidatorCollector();
    if (getter != null) {
      AccessibleObject accessibleObject = getter.getAccessibleObject();
      if (accessibleObject instanceof Method) {
        createValidatorsForMethod(validatorCollector, (Method) accessibleObject);
      } else {
        createValidatorsForAccessible(validatorCollector, accessibleObject);
      }
    }
    return validatorCollector.createValidator();
  }

  /**
   * Creates and adds the {@link ValueValidator}s for the given {@link Method}.
   * 
   * @param validatorCollector is the {@link ValidatorCollector}.
   * @param method is the {@link Method} to scan.
   */
  protected void createValidatorsForMethod(ValidatorCollector validatorCollector, Method method) {

    Method method2scan = method;
    while (method2scan != null) {
      createValidatorsForAccessible(validatorCollector, method2scan);
      // TODO hohwille what is the sepc. saying here? inherit all or only if none definied?
      method2scan = getReflectionUtil().getParentMethod(method2scan);
    }
  }

  /**
   * Creates and adds the {@link ValueValidator}s for the given {@link AccessibleObject}.
   * 
   * @param validatorCollector is the {@link ValidatorCollector}.
   * @param accessibleObject is the {@link AccessibleObject} to scan.
   */
  protected void createValidatorsForAccessible(ValidatorCollector validatorCollector, AccessibleObject accessibleObject) {

    Annotation[] annotations = accessibleObject.getAnnotations();
    for (Annotation annotation : annotations) {
      ValueValidator<?> validator = createValidator(annotation);
      if (validator != null) {
        validatorCollector.register(annotation, validator);
      }
    }
  }

  /**
   * Creates the {@link ValueValidator} for the given <code>annotation</code>.
   * 
   * @param annotation is the {@link Annotation} potentially representing a validation constraint.
   * @return the {@link ValueValidator} for the given <code>annotation</code> or <code>null</code> if no
   *         constraint.
   */
  protected abstract ValueValidator<?> createValidator(Annotation annotation);

  /**
   * This inner class represents a container to collect the individual {@link ValueValidator}s to
   * {@link #createValidator() compose}.
   */
  protected class ValidatorCollector {

    /** @see #register(Annotation, ValueValidator) */
    private Map<Class<? extends Annotation>, ValueValidator<?>> annotationType2validatorMap;

    /**
     * The constructor.
     */
    public ValidatorCollector() {

      super();
    }

    /**
     * Registers the given {@link ValueValidator} for the given {@link Annotation}.
     * 
     * @param annotation is the {@link Annotation}.
     * @param validator is the {@link ValueValidator}.
     */
    public void register(Annotation annotation, ValueValidator<?> validator) {

      if (this.annotationType2validatorMap == null) {
        this.annotationType2validatorMap = new HashMap<Class<? extends Annotation>, ValueValidator<?>>();
      }
      Class<? extends Annotation> annotationClass = annotation.getClass();
      ValueValidator<?> existing = this.annotationType2validatorMap.get(annotationClass);
      if (existing != null) {
        getLogger().debug("Validation annotation " + annotation + " ignored hence overridden by " + existing);
      } else {
        this.annotationType2validatorMap.put(annotationClass, validator);
      }
    }

    /**
     * @return the {@link ValueValidator} for the {@link #register(Annotation, ValueValidator) registered}
     *         validators.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ValueValidator<?> createValidator() {

      if (this.annotationType2validatorMap == null) {
        return ValidatorNone.getInstance();
      } else {
        Collection<ValueValidator<?>> values = this.annotationType2validatorMap.values();
        if (this.annotationType2validatorMap.size() == 1) {
          return values.iterator().next();
        } else {
          return new ComposedValidator(values.toArray(new ValueValidator[values.size()]));
        }
      }
    }
  }

}
