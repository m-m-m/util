/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import net.sf.mmm.util.component.AbstractLoggable;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfigurationImpl;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyAdd;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyGetIndexed;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyGetMapped;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyGetSize;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyRemove;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxySetIndexed;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxySetMapped;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.text.EnglishSingularizer;
import net.sf.mmm.util.text.Singularizer;

/**
 * This is an implementation of the {@link PojoDescriptorEnhancer} interface
 * that scans all
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getPropertyDescriptors() PropertyDescriptor}s
 * for accessing {@link java.util.Collection}s, {@link Map}s or arrays. For
 * such {@link PojoPropertyDescriptor}s additional
 * {@link PojoPropertyAccessor accessors} that are NOT already present are
 * created and added.<br>
 * <br>
 * In a first step the {@link PojoPropertyDescriptor#getName() name} of the
 * {@link PojoPropertyDescriptor} is
 * {@link Singularizer#transform(String) singularized}. If a different singular
 * form is determined, the {@link PojoPropertyAccessor accessors} of the
 * singular-named {@link PojoPropertyDescriptor} are copied to the original
 * {@link PojoPropertyDescriptor} if such accessors do NOT already exist.<br>
 * In a second step {@link PojoPropertyAccessor accessors} that are (still) NOT
 * present in the original {@link PojoPropertyDescriptor} are created and added
 * as virtual delegates on the container-typed getter (and according setter for
 * array-resizing if available). This will happen for the following modes:
 * <ul>
 * <li>{@link PojoPropertyAccessorOneArgMode#ADD add}</li>
 * <li>{@link PojoPropertyAccessorOneArgMode#REMOVE remove}</li>
 * <li>{@link PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed-getter}</li>
 * <li>{@link PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed-setter}</li>
 * <li>{@link PojoPropertyAccessorOneArgMode#GET_MAPPED mapped-getter}</li>
 * <li>{@link PojoPropertyAccessorTwoArgMode#SET_MAPPED mapped-setter}</li>
 * </ul>
 * 
 * For example the method
 * 
 * <pre>List&lt;Foo&gt; getChildren()</pre>
 * 
 * is available via the {@link PojoPropertyDescriptor}
 * {@link PojoPropertyDescriptor#getName() named} <code>children</code>.
 * Further the method
 * 
 * <pre>void addChild(Foo child)</pre>
 * 
 * is available via the {@link PojoPropertyDescriptor}
 * {@link PojoPropertyDescriptor#getName() named} <code>child</code>. This
 * enhancer makes the <code>addChild</code>-method also available via
 * <code>children</code> which is the plural form of <code>child</code>.
 * Further it will add the virtual {@link PojoPropertyAccessor accessors} as
 * described above so it behaves as if the following method would also be
 * present:
 * 
 * <pre>
 * Foo getChild(int index) {
 *   return getChildren().get(index);
 * }
 * 
 * void setChild(int index, Foo child) {
 *   getChildren().set(index, child);
 * }
 * 
 * void removeChild(Foo child) {
 *   getChildren().remove(child);
 * }
 * 
 * int getChildCount() {
 *   return getChildren().size();
 * }
 * </pre>
 * 
 * This makes it easier for generic access and allows ultimate flexibility since
 * the explicit methods of a POJO always overrule the virtual accessors.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultPojoDescriptorEnhancer extends AbstractLoggable implements
    PojoDescriptorEnhancer {

  /** The singularizer */
  private final Singularizer singularizer;

  /** @see #DefaultPojoDescriptorEnhancer(Singularizer, boolean, boolean) */
  private final boolean addSingularAccessors;

  /** @see #DefaultPojoDescriptorEnhancer(Singularizer, boolean, boolean) */
  private final boolean addVirtualAccessors;

  /** @see #getConfiguration() */
  private PojoDescriptorConfiguration configuration;

  /**
   * The constructor.
   */
  public DefaultPojoDescriptorEnhancer() {

    this(EnglishSingularizer.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param singularizer the singularizer to use.
   */
  public DefaultPojoDescriptorEnhancer(Singularizer singularizer) {

    this(singularizer, true, true);
  }

  /**
   * The constructor.
   * 
   * @param singularizer the singularizer to use.
   * 
   * @param addSingularAccessors - if <code>true</code> each
   *        {@link PojoPropertyAccessorNonArgMode#GET getter} that points to an
   *        array, {@link java.util.Collection} or {@link Map} is scanned. If it
   *        has a plural form for that the
   *        {@link #getSingularizer() singular form can be determined}, the
   *        {@link PojoPropertyAccessor}s of the singular
   *        {@link PojoPropertyDescriptor} are copied to the plural
   *        {@link PojoPropertyDescriptor} as long as no such
   *        {@link PojoPropertyAccessor} already exists.
   * @param addVirtualAccessors - if <code>true</code> for each
   *        {@link PojoPropertyAccessorNonArgMode#GET getter} that points to an
   *        array, {@link java.util.Collection} or {@link Map} according virtual
   *        {@link PojoPropertyAccessor}s are created and added as long as
   *        senseful and no such {@link PojoPropertyAccessor} already exists.
   */
  public DefaultPojoDescriptorEnhancer(Singularizer singularizer, boolean addSingularAccessors,
      boolean addVirtualAccessors) {

    super();
    this.singularizer = singularizer;
    this.addSingularAccessors = addSingularAccessors;
    this.addVirtualAccessors = addVirtualAccessors;
  }

  /**
   * @return the singularizer
   */
  protected Singularizer getSingularizer() {

    return this.singularizer;
  }

  /**
   * This method gets the configuration required for this descriptor.
   * 
   * @return the configuration.
   */
  protected PojoDescriptorConfiguration getConfiguration() {

    return this.configuration;
  }

  /**
   * This method sets the {@link #getConfiguration() configuration}.
   * 
   * @param configuration is the configuration to set.
   */
  @Resource
  public void setConfiguration(PojoDescriptorConfiguration configuration) {

    this.configuration = configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.configuration == null) {
      PojoDescriptorConfigurationImpl config = new PojoDescriptorConfigurationImpl();
      config.initialize();
      this.configuration = config;
    }
  }

  /**
   * This method adds the given <code>accessor</code> to the given
   * <code>propertyDescriptor</code>.
   * 
   * @param propertyDescriptor is the descriptor of the property where to add
   *        the given <code>accessor</code>.
   * @param accessor is the (virtual) accessor to add.
   */
  private void addVirtualAccessor(AbstractPojoPropertyDescriptor propertyDescriptor,
      PojoPropertyAccessor accessor) {

    if (getLogger().isDebugEnabled()) {
      getLogger().debug("adding virtual accessor: " + accessor);
    }
    propertyDescriptor.addAccessor(accessor);
  }

  /**
   * {@inheritDoc}
   */
  public void enhanceDescriptor(AbstractPojoDescriptor<?> descriptor) {

    ReflectionUtil reflectionUtil = getConfiguration().getReflectionUtil();
    for (AbstractPojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorNonArg getAccessor = propertyDescriptor
          .getAccessor(PojoPropertyAccessorNonArgMode.GET);
      if (getAccessor != null) {
        Type type = getAccessor.getReturnType();
        Class<?> typeClass = getAccessor.getReturnClass();
        Type componentType = reflectionUtil.getComponentType(type, true);
        boolean isMap = Map.class.isAssignableFrom(typeClass);
        if ((componentType != null) || isMap) {
          // getter type is container (map, array or collection)...
          String propertyName = getAccessor.getName();
          String singular = getSingularizer().transform(propertyName);
          if (this.addSingularAccessors && !propertyName.equals(singular)) {
            PojoPropertyDescriptor singularDescriptor = descriptor.getPropertyDescriptor(singular);
            if (singularDescriptor != null) {
              for (PojoPropertyAccessor singularAccessor : singularDescriptor.getAccessors()) {
                PojoPropertyAccessorMode<? extends PojoPropertyAccessor> mode = singularAccessor
                    .getMode();
                PojoPropertyAccessor pluralAccessor = propertyDescriptor.getAccessor(mode);
                if (pluralAccessor == null) {
                  Log logger = getLogger();
                  if (logger.isDebugEnabled()) {
                    logger.debug("copying accessor '" + singularAccessor + "' to '"
                        + propertyDescriptor + "'");
                  }
                  propertyDescriptor.addAccessor(singularAccessor);
                }
              }
            }
          }
          if (this.addVirtualAccessors) {
            // add size accessor
            if (propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET_SIZE) == null) {
              addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxyGetSize(
                  this.configuration, getAccessor));
            }
            if (isMap) {
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.GET_MAPPED) == null) {
                addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxyGetMapped(
                    this.configuration, getAccessor));
              }
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorTwoArgMode.SET_MAPPED) == null) {
                addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxySetMapped(
                    this.configuration, getAccessor));
              }
            } else {
              // array or collection
              PojoPropertyAccessorOneArg setAccessor = propertyDescriptor
                  .getAccessor(PojoPropertyAccessorOneArgMode.SET);
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.ADD) == null) {
                // we can NOT add 'add'-accessor for arrays without setter
                if (!typeClass.isArray() || (setAccessor != null)) {
                  addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxyAdd(
                      this.configuration, getAccessor, setAccessor));
                }
              }
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.REMOVE) == null) {
                // we can NOT add 'remove'-accessor for arrays without setter
                if (!typeClass.isArray() || (setAccessor != null)) {
                  addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxyRemove(
                      this.configuration, getAccessor, setAccessor));
                }
              }
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED) == null) {
                addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxyGetIndexed(
                    this.configuration, getAccessor));
              }
              if (propertyDescriptor.getAccessor(PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED) == null) {
                addVirtualAccessor(propertyDescriptor, new PojoPropertyAccessorProxySetIndexed(
                    this.configuration, getAccessor, setAccessor));
              }
            }
          }
        }
      }
    }
  }
}
