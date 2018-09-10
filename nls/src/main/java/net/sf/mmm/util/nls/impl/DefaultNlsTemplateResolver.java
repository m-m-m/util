/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Provider;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsBundleFactory;
import net.sf.mmm.util.nls.base.AbstractNlsBundleFactory.NlsBundleDescriptor;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.nls.base.NlsTemplateImplWithMessage;

/**
 * This is the default implementation of the {@link NlsTemplateResolver}. It locates all
 * {@link ResourceBundle}s declared in the {@link NlsTemplateResolver#CLASSPATH_NLS_BUNDLE
 * bundle-declaration-files}.
 *
 * @see AbstractResourceBundleNlsTemplateResolver
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DefaultNlsTemplateResolver extends AbstractResourceBundleNlsTemplateResolver {

  private Map<String, NlsTemplate> i18nMsg2TemplateMap;

  private NlsResourceBundleLocator resourceBundleLocator;

  private NlsBundleFactory bundleFactory;

  /**
   * The constructor.
   */
  public DefaultNlsTemplateResolver() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceBundleLocator == null) {
      NlsResourceBundleLocatorImpl impl = new NlsResourceBundleLocatorImpl();
      impl.initialize();
      this.resourceBundleLocator = impl;
    }
    if (this.bundleFactory == null) {
      this.bundleFactory = NlsAccess.getBundleFactory();
    }
  }

  private Map<String, NlsTemplate> getI18nMsg2TemplateMap() {

    if (this.i18nMsg2TemplateMap == null) {
      synchronized (this) {
        if (this.i18nMsg2TemplateMap == null) {
          Map<String, NlsTemplate> map = new HashMap<>();
          initTemplates(map);
          this.i18nMsg2TemplateMap = map;
        }
      }
    }
    return this.i18nMsg2TemplateMap;
  }

  /**
   * This method initializes the {@link NlsTemplate}s for reverse lookup.
   *
   * @param map the {@link Map} where to {@link Map#put(Object, Object) register} the {@link NlsTemplate}s by
   *        their {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() i18n message}.
   */
  protected void initTemplates(Map<String, NlsTemplate> map) {

    initTemplatesForNlsBundles(map);
    initTemplatesForResourceBundles(map);
  }

  /**
   * This method initializes the {@link NlsTemplate}s for reverse lookup for {@link ResourceBundle}s.
   *
   * @param map the {@link Map} where to {@link Map#put(Object, Object) register} the {@link NlsTemplate}s by
   *        their {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() i18n message}.
   */
  protected void initTemplatesForResourceBundles(Map<String, NlsTemplate> map) {

    List<ResourceBundle> bundles = this.resourceBundleLocator.findBundles();
    for (ResourceBundle resourceBundle : bundles) {
      String name = resourceBundle.getBaseBundleName();
      if (name == null) {
        name = resourceBundle.getClass().getName();
      }
      Enumeration<String> keyEnum = resourceBundle.getKeys();
      while (keyEnum.hasMoreElements()) {
        String key = keyEnum.nextElement();
        String message = resourceBundle.getString(key);
        NlsTemplateImplWithMessage template = new NlsTemplateImplWithMessage(name, key, message);
        map.put(message, template);
      }
    }
  }

  /**
   * This method initializes the {@link NlsTemplate}s for reverse lookup for {@link NlsBundle}s.
   *
   * @param map the {@link Map} where to {@link Map#put(Object, Object) register} the {@link NlsTemplate}s by
   *        their {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() i18n message}.
   */
  protected void initTemplatesForNlsBundles(Map<String, NlsTemplate> map) {

    if (this.bundleFactory instanceof AbstractNlsBundleFactory) {
      Collection<? extends NlsBundleDescriptor> bundleDescriptors = ((AbstractNlsBundleFactory) this.bundleFactory).getNlsBundleDescriptors();
      for (NlsBundleDescriptor descriptor : bundleDescriptors) {
        for (Provider<NlsTemplate> container : descriptor.getTemplateContainers()) {
          NlsTemplate template = container.get();
          if (template instanceof NlsTemplateImplWithMessage) {
            String message = template.translate(AbstractNlsMessage.LOCALE_ROOT);
            map.put(message, template);
          }
        }
      }
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    NlsAccess.setTemplateResolver(this);
  }

  /**
   * This method gets the {@link NlsResourceBundleLocator}.
   *
   * @return the {@link NlsResourceBundleLocator}.
   */
  public NlsResourceBundleLocator getResourceBundleFinder() {

    return this.resourceBundleLocator;
  }

  /**
   * @param resourceBundleLocator the {@link NlsResourceBundleLocator} to {@link Inject}
   */
  @Inject
  public void setResourceBundleLocator(NlsResourceBundleLocator resourceBundleLocator) {

    getInitializationState().requireNotInitilized();
    this.resourceBundleLocator = resourceBundleLocator;
  }

  /**
   * @param bundleFactory the {@link NlsBundleFactory} to {@link Inject}.
   */
  @Inject
  public void setBundleFactory(NlsBundleFactory bundleFactory) {

    this.bundleFactory = bundleFactory;
  }

  @Override
  public NlsTemplate resolveTemplate(String internationalizedMessage) {

    return getI18nMsg2TemplateMap().get(internationalizedMessage);
  }

}
