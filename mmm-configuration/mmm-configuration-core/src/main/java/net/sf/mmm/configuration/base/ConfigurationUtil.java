/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.List;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.ConfigurationFactory;
import net.sf.mmm.configuration.impl.access.file.FileAccessFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccessFactory;
import net.sf.mmm.configuration.impl.access.url.UrlAccessFactory;
import net.sf.mmm.configuration.impl.format.properties.PropertiesFactory;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.context.impl.MutableContextImpl;

/**
 * This class is a collection of utility methods to resolve
 * {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_INCLUDE included}
 * {@link net.sf.mmm.configuration.api.Configuration configurations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ConfigurationUtil {

  /**
   * The forbidden constructor.
   */
  private ConfigurationUtil() {

    super();
  }

  /**
   * This method creates a new and
   * {@link #initializeContext(MutableContext) initialized}
   * {@link MutableContext environment}.
   * 
   * @return a default environment.
   */
  public static MutableContext createDefaultContext() {

    MutableContext env = new MutableContextImpl();
    initializeContext(env);
    return env;
  }

  /**
   * 
   * @param context
   * @param type
   * @param factoryClass
   */
  public static void setAccessFactory(MutableContext context, String type,
      Class<? extends ConfigurationAccessFactory> factoryClass) {

    String key = ConfigurationAccessFactory.CONTEXT_VARIABLE_PREFIX + type
        + ConfigurationAccessFactory.CONTEXT_VARIABLE_SUFFIX_FACTORY;
    context.setObject(key, factoryClass.getName());
  }

  /**
   * 
   * @param context
   * @param type
   * @param factoryClass
   */
  public static void setFormatFactory(MutableContext context, String type,
      Class<? extends ConfigurationFactory> factoryClass) {

    String key = ConfigurationFactory.CONTEXT_VARIABLE_PREFIX + type
        + ConfigurationFactory.CONTEXT_VARIABLE_SUFFIX_FACTORY;
    context.setObject(key, factoryClass.getName());
  }

  /**
   * This method initializes the given {@link MutableContext environment} with
   * the defaults for the root {@link ConfigurationDocument document}.
   * 
   * @param context
   *        is the environment to modify.
   */
  public static void initializeContext(MutableContext context) {

    // ### access ###
    // default
    context.setObject(ConfigurationAccessFactory.CONTEXT_VARIABLE_DEFAULT,
        ResourceAccessFactory.CONTEXT_DEFAULT_NAME);

    // resource
    setAccessFactory(context, ResourceAccessFactory.CONTEXT_DEFAULT_NAME,
        ResourceAccessFactory.class);
    // file
    setAccessFactory(context, FileAccessFactory.CONTEXT_DEFAULT_NAME, FileAccessFactory.class);
    // url
    setAccessFactory(context, UrlAccessFactory.CONTEXT_DEFAULT_NAME, UrlAccessFactory.class);

    // ### formats ###
    // default
    context.setObject(ConfigurationFactory.CONTEXT_VARIABLE_DEFAULT,
        XmlFactory.CONTEXT_DEFAULT_NAME);
    // xml
    setFormatFactory(context, XmlFactory.CONTEXT_DEFAULT_NAME, XmlFactory.class);
    // properties
    setFormatFactory(context, PropertiesFactory.CONTEXT_DEFAULT_NAME, PropertiesFactory.class);
    // flat properties
    String type = "flat-" + PropertiesFactory.CONTEXT_DEFAULT_NAME;
    setFormatFactory(context, type, PropertiesFactory.class);
    String key = ConfigurationFactory.CONTEXT_VARIABLE_PREFIX + type
        + PropertiesFactory.CONTEXT_VARIABLE_SUFFIX_FLAT;
    context.setObject(key, Boolean.TRUE.toString());
    key = ConfigurationFactory.CONTEXT_VARIABLE_PREFIX + PropertiesFactory.CONTEXT_DEFAULT_NAME
        + PropertiesFactory.CONTEXT_VARIABLE_SUFFIX_FLAT;
    context.setObject(key, XmlFactory.class.getName());

  }

  /**
   * This method gets the factory used to interpret the format of the
   * configuration to include.
   * 
   * @param includeConfiguration
   *        is the {@link ConfigurationDocument#NAME_INCLUDE include} element.
   * @param context
   *        is the {@link ConfigurationDocument#getContext() document-context}.
   * @return the configuration factory.
   */
  public static ConfigurationFactory getDocumentFactory(Configuration includeConfiguration,
      Context context) {

    String format = includeConfiguration.getDescendant(ConfigurationDocument.NAME_INCLUDE_FORMAT)
        .getValue().getString(null);
    if (format == null) {
      format = context.getValue(ConfigurationFactory.CONTEXT_VARIABLE_DEFAULT).getString();
    }
    String varPrefix = ConfigurationFactory.CONTEXT_VARIABLE_PREFIX + format;
    String varFactory = varPrefix + ConfigurationFactory.CONTEXT_VARIABLE_SUFFIX_FACTORY;
    ConfigurationFactory factory = context.getValue(varFactory).getJavaClassInstance(
        ConfigurationFactory.class);
    factory.configure(varPrefix, context, includeConfiguration);
    return factory;
  }

  /**
   * This method gets the factory used to access the configuration to include.
   * 
   * @param includeConfiguration
   *        is the {@link ConfigurationDocument#NAME_INCLUDE include} element.
   * @param context
   *        is the {@link ConfigurationDocument#getContext() document-context}.
   * @return the access factory.
   */
  public static ConfigurationAccessFactory getAccessFactory(Configuration includeConfiguration,
      Context context) {

    String access = includeConfiguration.getDescendant(ConfigurationDocument.NAME_INCLUDE_ACCESS)
        .getValue().getString(null);
    if (access == null) {
      access = context.getValue(ConfigurationAccessFactory.CONTEXT_VARIABLE_DEFAULT).getString();
    }
    String varPrefix = ConfigurationAccessFactory.CONTEXT_VARIABLE_PREFIX + access;
    String varFactory = varPrefix + ConfigurationAccessFactory.CONTEXT_VARIABLE_SUFFIX_FACTORY;
    ConfigurationAccessFactory factory = context.getValue(varFactory).getJavaClassInstance(
        ConfigurationAccessFactory.class);
    factory.configure(varPrefix, context, includeConfiguration);
    return factory;
  }

  /**
   * This method resolves the included configurations for the given
   * <code>includeElement</code>.
   * 
   * @param includeElement
   *        is the {@link ConfigurationDocument#NAME_INCLUDE include} element to
   *        resolve.
   * @param targetList
   *        is the list where to add the included child nodes.
   */
  public static void resolveInclude(AbstractConfiguration includeElement,
      List<AbstractConfiguration> targetList) {

    assert ConfigurationDocument.NAME_INCLUDE.equals(includeElement.getName());
    assert ConfigurationDocument.NAMESPACE_URI_CONFIGURATION.equals(includeElement
        .getNamespaceUri());

    Context context = includeElement.getOwnerDocument().getContext();
    ConfigurationFactory documentFactory = getDocumentFactory(includeElement, context);
    ConfigurationAccessFactory accessFactory = getAccessFactory(includeElement, context);
    ConfigurationAccess[] accessors = accessFactory.getAccessors();
    String descendantPath = includeElement.getDescendant(
        ConfigurationDocument.NAME_INCLUDE_DESCENDANTS).getValue().getString(null);
    for (int i = 0; i < accessors.length; i++) {
      AbstractConfigurationDocument document = documentFactory.create(accessors[i], includeElement);
      // TODO: lazy loading!
      AbstractConfiguration result = document.getConfiguration();
      if (descendantPath == null) {
        targetList.add(result);
      } else {
        for (AbstractConfiguration descendant : result.getDescendants(descendantPath)) {
          targetList.add(new EntryPointConfiguration(includeElement, descendant));
        }
      }
    }
  }
}
