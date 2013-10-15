/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.resource.api.DataResource;

import org.slf4j.Logger;

/**
 * This is the implementation of {@link NlsResourceBundleLocator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Singleton
@Named
public class NlsResourceBundleLocatorImpl extends AbstractLoggableComponent implements NlsResourceBundleLocator {

  /** @see #findBundles() */
  private List<ResourceBundle> nlsBundles;

  /** @see #getReflectionUtil() */
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    Logger logger = getLogger();
    if (this.nlsBundles == null) {
      this.nlsBundles = new ArrayList<ResourceBundle>();
      if (this.reflectionUtil == null) {
        this.reflectionUtil = ReflectionUtilImpl.getInstance();
      }
      Set<DataResource> bundleResources = this.reflectionUtil.findResources(NlsTemplateResolver.CLASSPATH_NLS_BUNDLE);
      for (DataResource dataResource : bundleResources) {
        if (logger.isTraceEnabled()) {
          logger.trace("Loading " + dataResource.getUri());
        }
        try (InputStream inStream = dataResource.openStream()) {
          try (InputStreamReader isr = new InputStreamReader(inStream)) {
            try (BufferedReader reader = new BufferedReader(isr)) {
              boolean noEntryInBundleResource = true;
              String line = reader.readLine();
              while (line != null) {
                line = line.trim();
                if (line.length() > 0) {
                  if (logger.isTraceEnabled()) {
                    logger.trace("Loading resource bundle " + line);
                  }
                  noEntryInBundleResource = false;
                  try {
                    ResourceBundle bundleInstance = ResourceBundle.getBundle(line, AbstractNlsMessage.LOCALE_ROOT);
                    this.nlsBundles.add(bundleInstance);
                  } catch (Exception e) {
                    logger.error("Illegal bundle declaration " + dataResource.getUri() + ": Class '" + line
                        + "' is invalid!", e);
                  }
                }
                line = reader.readLine();
              }
              if (noEntryInBundleResource) {
                logger.error("Illegal bundle declaration " + dataResource.getUri() + ": no entry!");
              }
            }
          }
        } catch (IOException e) {
          throw new RuntimeIoException(e, IoMode.READ);
        }
      }
      this.nlsBundles = Collections.unmodifiableList(this.nlsBundles);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ResourceBundle> findBundles() {

    return this.nlsBundles;
  }

}
