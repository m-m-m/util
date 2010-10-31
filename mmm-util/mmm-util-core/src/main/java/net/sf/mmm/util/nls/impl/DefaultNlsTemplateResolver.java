/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.base.AbstractResourceBundle;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver}. It locates all
 * {@link AbstractResourceBundle NLS-bundles} declared in
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver#CLASSPATH_NLS_BUNDLE
 * bundle-declaration-files}.
 * 
 * @see AbstractResourceBundleNlsTemplateResolver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Named
@Singleton
public class DefaultNlsTemplateResolver extends AbstractResourceBundleNlsTemplateResolver {

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #resolveTemplate(String) */
  private AbstractResourceBundle[] nlsBundles;

  /**
   * The constructor.
   */
  public DefaultNlsTemplateResolver() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.nlsBundles == null) {
      List<AbstractResourceBundle> bundleList = new ArrayList<AbstractResourceBundle>();
      if (this.reflectionUtil == null) {
        this.reflectionUtil = ReflectionUtilImpl.getInstance();
      }
      Set<DataResource> bundleResources = this.reflectionUtil.findResources(CLASSPATH_NLS_BUNDLE);
      for (DataResource dataResource : bundleResources) {
        InputStream inStream = dataResource.openStream();
        try {
          InputStreamReader isr = new InputStreamReader(inStream);
          BufferedReader reader = new BufferedReader(isr);
          boolean noEntryInBundleResource = true;
          String line = reader.readLine();
          while (line != null) {
            line = line.trim();
            if (line.length() > 0) {
              noEntryInBundleResource = false;
              try {
                Class<?> bundleClass = Class.forName(line);
                // if
                // (AbstractResourceBundle.class.isAssignableFrom(bundleClass))
                // {
                AbstractResourceBundle bundleInstance = (AbstractResourceBundle) bundleClass
                    .newInstance();
                bundleList.add(bundleInstance);
                // }
              } catch (Exception e) {
                getLogger().warn(
                    "Illegal file " + CLASSPATH_NLS_BUNDLE + ": Class '" + line + "' is invalid!",
                    e);
              }
            }
            line = reader.readLine();
          }
          if (noEntryInBundleResource) {
            getLogger().warn("Illegal file " + CLASSPATH_NLS_BUNDLE + ": no entry!");
          }
        } catch (IOException e) {
          throw new RuntimeIoException(e, IoMode.READ);
        } finally {
          try {
            inStream.close();
          } catch (IOException e) {
            getLogger().warn("Ignoring IOException while closing InputStream!", e);
          }
        }
      }
      this.nlsBundles = bundleList.toArray(new AbstractResourceBundle[bundleList.size()]);
    }
  }

  /**
   * @return the reflectionUtil
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
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return resolveTemplate(internationalizedMessage, this.nlsBundles);
  }

}
