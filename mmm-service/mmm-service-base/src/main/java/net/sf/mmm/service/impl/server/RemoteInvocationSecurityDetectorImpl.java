/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import javax.inject.Named;

import net.sf.mmm.service.api.Login;
import net.sf.mmm.service.base.server.RemoteInvocationSecurityDetector;
import net.sf.mmm.util.component.base.AbstractComponent;

/**
 * This is the default implementation of {@link RemoteInvocationSecurityDetector}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(RemoteInvocationSecurityDetector.CDI_NAME)
public class RemoteInvocationSecurityDetectorImpl extends AbstractComponent implements RemoteInvocationSecurityDetector {

  /**
   * The constructor.
   */
  public RemoteInvocationSecurityDetectorImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSecured(AnnotatedElement operation) {

    for (Annotation annotation : operation.getAnnotations()) {
      Boolean secured = isSecured(annotation);
      if (secured != null) {
        return secured.booleanValue();
      }
    }
    return isSecuredByDefault();
  }

  /**
   * This method gets the default value for {@link #isSecured(AnnotatedElement)} if no annotation or other
   * hint was found. This implementation returns <code>true</code>. Override to change.
   * 
   * @return the default value for {@link #isSecured(AnnotatedElement)}.
   */
  protected boolean isSecuredByDefault() {

    return true;
  }

  /**
   * @param annotation is the {@link Annotation} to check.
   * @return {@link Boolean#TRUE true} if secured, {@link Boolean#FALSE false} if unsecured and
   *         <code>null</code> if the given <code>annotation</code> is not known to be related to security.
   */
  protected Boolean isSecured(Annotation annotation) {

    Class<? extends Annotation> annotationType = annotation.annotationType();
    String simpleName = annotationType.getSimpleName();
    if ("RolesAllowed".equals(simpleName)) {
      // JSR 250 annotation to check permissions by roles
      return Boolean.TRUE;
    } else if ("PermitAll".equals(simpleName)) {
      // JSR 250 annotation to express no permissions required
      return Boolean.FALSE;
    } else if ("PreAuthorize".equals(simpleName)) {
      // spring security annotation to check permissions (by expression)
      return Boolean.TRUE;
    } else if ("Secured".equals(simpleName)) {
      // spring security annotation to check permissions (by roles)
      return Boolean.TRUE;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLogin(AnnotatedElement operation) {

    if (isLoginByName(operation)) {
      return true;
    }
    Login login = operation.getAnnotation(Login.class);
    if (login != null) {
      return true;
    }
    return false;
  }

  /**
   * Checks for {@link #isLogin(AnnotatedElement)} by naming convention.
   * 
   * @see #LOGIN_METHOD_NAME
   * @see #LOGIN_COMMAND_NAME
   * 
   * @param operation - see {@link #isLogin(AnnotatedElement)}.
   * @return <code>true</code> if the given <code>operation</code> is a login operation due to naming
   *         convention, <code>false</code> otherwise.
   */
  protected boolean isLoginByName(AnnotatedElement operation) {

    if (operation instanceof Method) {
      Method method = (Method) operation;
      if (LOGIN_METHOD_NAME.equals(method.getName())) {
        return true;
      }
    } else if (operation instanceof Class) {
      Class<?> commandClass = (Class<?>) operation;
      if (LOGIN_COMMAND_NAME.equals(commandClass.getName())) {
        return true;
      }
    }
    return false;
  }
}
