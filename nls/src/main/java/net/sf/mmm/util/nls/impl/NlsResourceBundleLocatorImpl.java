/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.NlsBundleHelper;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the implementation of {@link NlsResourceBundleLocator}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class NlsResourceBundleLocatorImpl extends AbstractComponent implements NlsResourceBundleLocator {

  private static final Logger LOG = LoggerFactory.getLogger(NlsResourceBundleLocatorImpl.class);

  private List<ResourceBundle> nlsBundles;

  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public NlsResourceBundleLocatorImpl() {

    super();
  }

  /**
   * This method gets the {@link List} of {@link ResourceBundle}s.
   *
   * @see #findBundles()
   *
   * @return the {@link ResourceBundle}s.
   */
  protected List<ResourceBundle> getNlsBundles() {

    return this.nlsBundles;
  }

  /**
   * @param nlsBundles is the nlsBundles to set
   */
  public void setNlsBundles(List<ResourceBundle> nlsBundles) {

    getInitializationState().requireNotInitilized();
    this.nlsBundles = nlsBundles;
  }

  /**
   * This method gets the {@link ReflectionUtil}.
   *
   * @return the {@link ReflectionUtil}.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
  }

  @Override
  public List<ResourceBundle> findBundles() {

    if (this.nlsBundles == null) {
      synchronized (this) {
        if (this.nlsBundles == null) {
          if (this.nlsBundles == null) {
            this.nlsBundles = Collections.unmodifiableList(loadNlsBundles());
          }
        }
      }
    }
    return this.nlsBundles;
  }

  private List<ResourceBundle> loadNlsBundles() {

    List<ResourceBundle> bundles = new ArrayList<>();
    ClassLoader ccl = Thread.currentThread().getContextClassLoader();
    Enumeration<URL> resourceUrlEnumeration;
    try {
      resourceUrlEnumeration = ccl.getResources(NlsTemplateResolver.CLASSPATH_NLS_BUNDLE);
    } catch (IOException e) {
      throw new IllegalStateException("Error loading from classpath.", e);
    }
    while (resourceUrlEnumeration.hasMoreElements()) {
      URL url = resourceUrlEnumeration.nextElement();
      LOG.trace("Loading {}", url);
      try (InputStream inStream = url.openStream(); InputStreamReader isr = new InputStreamReader(inStream); BufferedReader reader = new BufferedReader(isr)) {
        boolean noEntryInBundleResource = true;
        String line = reader.readLine();
        while (line != null) {
          line = line.trim();
          if (line.length() > 0) {
            ResourceBundle bundleInstance = loadNlsBundle(LOG, url, line);
            if (bundleInstance != null) {
              bundles.add(bundleInstance);
              noEntryInBundleResource = false;
            }
          }
          line = reader.readLine();
        }
        if (noEntryInBundleResource) {
          LOG.error("Illegal bundle declaration {}: no entry!", url);
        }
      } catch (IOException e) {
        throw new IllegalStateException("Error writing to Appendable.", e);
      }
    }
    return bundles;
  }

  private ResourceBundle loadNlsBundle(Logger logger, URL url, String line) {

    if (logger.isTraceEnabled()) {
      logger.trace("Loading resource bundle " + line);
    }
    try {
      ResourceBundle bundleInstance = NlsBundleHelper.getInstance().getResourceBundle(line, AbstractNlsMessage.LOCALE_ROOT);
      return bundleInstance;
    } catch (Exception e) {
      logger.error("Illegal bundle declaration {}: Class '{}' is invalid!", url, line, e);
    }
    return null;
  }

}
