/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.nls.base.NlsMessageFactoryImpl;
import net.sf.mmm.util.nls.impl.DefaultNlsTemplateResolver;
import net.sf.mmm.util.nls.impl.NlsBundleFactoryImpl;

/**
 * This is a static accessor for central components supporting <em>native language support</em> (NLS). It is therefore
 * the entry point to create instances of {@link NlsBundle} and {@link net.sf.mmm.util.nls.api.NlsMessage}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsAccess {

  private static final Logger LOG = LoggerFactory.getLogger(NlsAccess.class);

  private static NlsMessageFactory factory;

  private static NlsTemplateResolver templateResolver;

  private static NlsBundleFactory bundleFactory;

  /**
   * The constructor.
   */
  private NlsAccess() {

    super();
  }

  /**
   * This method gets the {@link NlsMessageFactory} used to create instances of
   * {@link net.sf.mmm.util.nls.api.NlsMessage}.
   *
   * @return the factory instance.
   */
  public static NlsMessageFactory getFactory() {

    if (factory == null) {
      synchronized (NlsAccess.class) {
        if (factory == null) {
          NlsMessageFactoryImpl factoryImpl = new NlsMessageFactoryImpl();
          factoryImpl.initialize();
          factory = factoryImpl;
        }
      }
    }
    return factory;
  }

  /**
   * This method sets (overrides) the {@link NlsMessageFactory}. This allows to exchange the {@link NlsMessageFactory}
   * and thereby the implementation of {@link net.sf.mmm.util.nls.api.NlsMessage}. <br>
   * The desired behavior of a universal translator can depend from the situation where it is used. E.g. a client
   * application could use the {@link java.util.Locale#getDefault() "default locale"} to choose the destination
   * language. In a multi-user server application a {@link ThreadLocal} may be used to retrieve the appropriate
   * {@link java.util.Locale locale}. <br>
   * <b>WARNING:</b><br>
   * This is only a back-door for simple applications or test situations. Please try to avoid using this feature as well
   * as {@link net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage()} and solve this issue with IoC strategies (using
   * non-final static fields like here is evil). <br>
   * <b>ATTENTION:</b><br>
   * No synchronization is performed setting the factory instance. This assumes that an assignment is an atomic
   * operation in the JVM you are using. Additionally this method should only be invoked in the initialization phase of
   * your application.
   *
   * @param factory the factory-instance to use.
   */
  public static void setFactory(NlsMessageFactory factory) {

    if (NlsAccess.factory == null) {
      NlsAccess.factory = factory;
    } else if (NlsAccess.factory != factory) {
      LOG.warn("NlsTemplateResolver is already set to {} and will not be changed to {}.", NlsAccess.factory,
          factory);
    }
  }

  /**
   * This method gets the default {@link NlsTemplateResolver}.
   *
   * @return the default {@link NlsTemplateResolver}.
   * @since 2.0.0
   */
  public static NlsTemplateResolver getTemplateResolver() {

    if (templateResolver == null) {
      synchronized (NlsAccess.class) {
        if (templateResolver == null) {
          DefaultNlsTemplateResolver resolver = new DefaultNlsTemplateResolver();
          resolver.initialize();
        }
      }
    }
    return templateResolver;
  }

  /**
   * This method sets (overrides) the default {@link NlsTemplateResolver}. <b>WARNING:</b><br>
   * This is only a back-door for simple applications or test situations. Please try to avoid using this feature and
   * solve this issue with IoC strategies (using non-final static fields like here is evil). <br>
   * <b>ATTENTION:</b><br>
   * No synchronization is performed setting the instance. This assumes that an assignment is an atomic operation in the
   * JVM you are using. Additionally this method should only be invoked in the initialization phase of your application.
   *
   * @param templateResolver is the {@link NlsTemplateResolver} to use by default.
   * @since 2.0.0
   */
  public static void setTemplateResolver(NlsTemplateResolver templateResolver) {

    if (NlsAccess.templateResolver == null) {
      NlsAccess.templateResolver = templateResolver;
    } else if (NlsAccess.templateResolver != templateResolver) {
      LOG.warn("NlsTemplateResolver is already set to {} and will not be changed to {}.",
          NlsAccess.templateResolver, templateResolver);
    }
  }

  /**
   * This method gets the default {@link NlsBundleFactory}.
   *
   * @return the default {@link NlsBundleFactory}.
   * @since 3.0.0
   */
  public static NlsBundleFactory getBundleFactory() {

    if (bundleFactory == null) {
      synchronized (NlsAccess.class) {
        if (bundleFactory == null) {
          NlsBundleFactoryImpl impl = new NlsBundleFactoryImpl();
          impl.initialize();
        }
      }
    }
    return bundleFactory;
  }

  /**
   * This method sets (overrides) the default {@link NlsBundleFactory}. <br>
   * <b>WARNING:</b><br>
   * This is only a back-door for simple applications or test situations. Please try to avoid using this feature and
   * solve this issue with IoC strategies (using non-final static fields like here is evil). <br>
   * <b>ATTENTION:</b><br>
   * No synchronization is performed setting the instance. This assumes that an assignment is an atomic operation in the
   * JVM you are using. Additionally this method should only be invoked in the initialization phase of your application.
   *
   * @param bundleFactory is the {@link NlsBundleFactory} to use by default.
   * @since 3.0.0
   */
  public static void setBundleFactory(NlsBundleFactory bundleFactory) {

    if (NlsAccess.bundleFactory == null) {
      NlsAccess.bundleFactory = bundleFactory;
    } else if (NlsAccess.bundleFactory != bundleFactory) {
      LOG.warn("NlsBundleFactory is already set to {} and will not be changed to {}.", NlsAccess.bundleFactory,
          bundleFactory);
    }
  }

}
