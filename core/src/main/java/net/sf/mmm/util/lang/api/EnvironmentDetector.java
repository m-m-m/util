/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a detector of the environment. It can be used for dynamic decision of behavior that shall
 * differ between environments. The common approach is the <em>DTAP</em> setup with the environments
 * {@link #ENVIRONMENT_TYPE_DEVELOPMENT Development}, {@link #ENVIRONMENT_TYPE_TEST Test},
 * {@link #ENVIRONMENT_TYPE_ACCEPTANCE Acceptance}, and the {@link #ENVIRONMENT_TYPE_PRODUCTION Production}. However,
 * also other more fine-grained environment setups are supported. <br>
 * <b>ATTENTION:</b><br>
 * Unfortunately there is no Java standard for this. Therefore your configuration to setup the environments has to be
 * compliant to the implementation of this interface. Environments can be defined via spring profiles, via JEE
 * application server specific configurations, via custom {@link System#getProperties() system properties}, etc. The
 * default implementation is using spring profiles so you have to replace it if you choose a different setup.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@ComponentSpecification
public interface EnvironmentDetector {

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>development</em> environment.
   *
   * @see #isDevelopmentEnvironment()
   */
  String ENVIRONMENT_TYPE_DEVELOPMENT = "development";

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>test</em> environment.
   */
  String ENVIRONMENT_TYPE_TEST = "test";

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>acceptance</em> environment.
   */
  String ENVIRONMENT_TYPE_ACCEPTANCE = "acceptance";

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>production</em> environment.
   *
   * @see #isProductionEnvironment()
   */
  String ENVIRONMENT_TYPE_PRODUCTION = "production";

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>staging</em> environment.
   */
  String ENVIRONMENT_TYPE_STAGING = "staging";

  /**
   * The {@link #getEnvironmentType() environment type} for the <em>pre-production</em> environment.
   */
  String ENVIRONMENT_TYPE_PRE_PRODUCTION = "pre-production";

  /**
   * @return the environment type such as {@link #ENVIRONMENT_TYPE_DEVELOPMENT}, {@link #ENVIRONMENT_TYPE_TEST},
   *         {@link #ENVIRONMENT_TYPE_ACCEPTANCE}, {@link #ENVIRONMENT_TYPE_PRODUCTION}, or any other custom kind of
   *         environment.
   */
  String getEnvironmentType();

  /**
   * @return the result of {@link #getEnvironmentType()}{@code .equals(}{@link #ENVIRONMENT_TYPE_DEVELOPMENT}{@code )}.
   */
  boolean isDevelopmentEnvironment();

  /**
   * @return the result of {@link #getEnvironmentType()}{@code .equals(}{@link #ENVIRONMENT_TYPE_PRODUCTION}{@code )}.
   */
  boolean isProductionEnvironment();

  /**
   * @return {@code true} if the {@link #getEnvironmentType() environment type} is {@link #ENVIRONMENT_TYPE_PRODUCTION}
   *         or close to production such as {@link #ENVIRONMENT_TYPE_PRE_PRODUCTION},
   *         {@link #ENVIRONMENT_TYPE_ACCEPTANCE}, or {@link #ENVIRONMENT_TYPE_STAGING}.
   */
  boolean isEnvironmentCloseToProduction();

}
