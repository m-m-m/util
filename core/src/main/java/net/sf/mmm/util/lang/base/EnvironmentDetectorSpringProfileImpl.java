/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.core.env.Environment;

import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This is the default implementation of {@link net.sf.mmm.util.lang.api.EnvironmentDetector}. It is based on spring
 * profiles.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class EnvironmentDetectorSpringProfileImpl extends AbstractEnvironmentDetector {

  /** The {@link Environment} used to determine if we are running in production. */
  @Inject
  private Environment environment;

  /** @see #getEnvironmentType() */
  private String environmentType;

  /**
   * The constructor.
   */
  public EnvironmentDetectorSpringProfileImpl() {

    super();
  }

  @Override
  public String getEnvironmentType() {

    if (this.environmentType == null) {
      // Spring profiles do not work properly via
      // AbstractApplicationContext.getEnvironment().setActiveProfiles(...)
      // not even with lazy-init as lazy-init is violated as soon as a bean is requesting this bean without
      // being itself configured via lazy-init. To avoid all the problems and trouble we use our own lazy
      // initialization mechanism.
      String result = getEnvironmentTypeSynchronized();
      logEnvironmentStatus();
      return result;
    }
    return this.environmentType;
  }

  /**
   * @return the {@link #getEnvironmentType() environment type} with lazy initialization.
   */
  protected synchronized String getEnvironmentTypeSynchronized() {

    if (this.environmentType == null) {
      this.environmentType = detectEnvironmentType(this.environment.getActiveProfiles());
    }
    return this.environmentType;
  }

  /**
   * @return the {@link Environment}.
   */
  public Environment getEnvironment() {

    return this.environment;
  }

  /**
   * @param environment is the {@link Environment} to {@link Inject}.
   */
  @Inject
  public void setEnvironment(Environment environment) {

    getInitializationState().requireNotInitilized();
    this.environment = environment;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.environment == null) {
      throw new ResourceMissingException("environment");
    }
  }

  @Override
  protected void logEnvironmentStatus() {

    super.logEnvironmentStatus();
    if (isDevelopmentEnvironment()) {
      getLogger().warn("ATTENTION: You are currently running in DEVELOPMENT mode.");
      getLogger().warn(
          "Ensure to manage your environments properly via spring profiles (e.g. by setting the system property 'spring.profiles.active'");
    }
  }

  /**
   * @param names are the names of actual environments or configuration profiles.
   * @return the detected environment.
   */
  protected String detectEnvironmentType(String[] names) {

    String detectedEnvironmentType = null;
    for (String name : names) {
      String newType = detectEnvironmentType(name);
      if (newType != null) {
        if (detectedEnvironmentType == null) {
          detectedEnvironmentType = newType;
          getLogger().info("Profile '{}' triggered environment type '{}'.", name, newType);
        } else if (!detectedEnvironmentType.equals(newType)) {
          throw new IllegalStateException("Environment type is " + detectedEnvironmentType + " but profile " + name
              + " triggered different environment type " + newType);
        }
      }
    }
    if (detectedEnvironmentType == null) {
      // ATTENTION: this fallback is causing development to be default. You actually have to get active for
      // production.
      detectedEnvironmentType = ENVIRONMENT_TYPE_DEVELOPMENT;
    }
    return detectedEnvironmentType;
  }

  /**
   * @param name is the name of an actual environment or a configuration profile.
   * @return the {@link #getEnvironmentType() environment type} that has been detected by naming convention, or
   *         {@code null} if nothing could be detected.
   */
  protected String detectEnvironmentType(String name) {

    String lowercaseName = name.toLowerCase(Locale.US);
    if (lowercaseName.contains("pre-live") || lowercaseName.contains("pre-prod")
        || lowercaseName.contains("prelive") || lowercaseName.contains("preprod")) {
      return ENVIRONMENT_TYPE_PRE_PRODUCTION;
    } else if (lowercaseName.contains("prod") || lowercaseName.contains("live")) {
      return ENVIRONMENT_TYPE_PRODUCTION;
    } else if (lowercaseName.contains("dev")) {
      return ENVIRONMENT_TYPE_DEVELOPMENT;
    } else if (lowercaseName.contains("accept")) {
      return ENVIRONMENT_TYPE_ACCEPTANCE;
    } else if (lowercaseName.contains("test")) {
      return ENVIRONMENT_TYPE_TEST;
    } else if (lowercaseName.contains("staging") || lowercaseName.contains("stage")) {
      return ENVIRONMENT_TYPE_STAGING;
    }
    return null;
  }

}
