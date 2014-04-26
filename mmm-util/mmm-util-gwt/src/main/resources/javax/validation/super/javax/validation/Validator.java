package javax.validation;

import java.util.Set;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * Reduced variant for GWT.
 *
 * @author Joerg Hohwiller
 */
public interface Validator {

  <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups);

  <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups);

  <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups);

  BeanDescriptor getConstraintsForClass(Class<?> clazz);

  <T> T unwrap(Class<T> type);

  //ExecutableValidator forExecutables();
}
