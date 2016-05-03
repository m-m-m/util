/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.EnvironmentDetector;

/**
 * This is the abstract base implementation of {@link EnvironmentDetector}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractEnvironmentDetector extends AbstractLoggableComponent implements EnvironmentDetector {

  /**
   * The constructor.
   */
  public AbstractEnvironmentDetector() {

    super();
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return ENVIRONMENT_TYPE_DEVELOPMENT.equals(getEnvironmentType());
  }

  @Override
  public boolean isProductionEnvironment() {

    return ENVIRONMENT_TYPE_PRODUCTION.equals(getEnvironmentType());
  }

  @Override
  public boolean isEnvironmentCloseToProduction() {

    String environmentType = getEnvironmentType();
    if (ENVIRONMENT_TYPE_PRODUCTION.equals(environmentType) || ENVIRONMENT_TYPE_PRE_PRODUCTION.equals(environmentType)
        || ENVIRONMENT_TYPE_STAGING.equals(environmentType) || ENVIRONMENT_TYPE_ACCEPTANCE.equals(environmentType)) {
      return true;
    }
    return false;
  }

  /**
   * Logs the current environment status information.
   */
  protected void logEnvironmentStatus() {

    if (getLogger().isInfoEnabled()) {
      getLogger().info("You are running in {} mode", getEnvironmentType());
    }
  }

}
