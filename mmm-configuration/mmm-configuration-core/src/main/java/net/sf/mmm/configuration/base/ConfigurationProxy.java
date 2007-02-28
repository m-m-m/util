/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.value.api.MutableGenericValue;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.configuration.api.Configuration} interface that delegates
 * to another {@link #getDelegate() instance}. You can use it to wrap a
 * configuration node and change its behaviour by overriding specific method(s).<br>
 * There are {@link java.lang.reflect.Proxy proxies} in JAVA that could be used
 * instead but this this is done by intention and the API is stable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ConfigurationProxy extends AbstractConfiguration {

  /**
   * The constructor.
   */
  public ConfigurationProxy() {

    super();
  }

  /**
   * This method gets the configuration this proxy delegates to. The delegate
   * can be lazy loaded in the implementation of this method.
   * 
   * @return the configuration delegate.
   */
  protected abstract AbstractConfiguration getDelegate();

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#getValue()
   */
  public MutableGenericValue getValue() {

    return getDelegate().getValue();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getName()
   */
  public String getName() {

    return getDelegate().getName();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getNamespaceUri()
   */
  public String getNamespaceUri() {

    return getDelegate().getNamespaceUri();
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#isEditable()
   */
  public boolean isEditable() {

    return getDelegate().isEditable();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#isAddDefaults()
   */
  public boolean isAddDefaults() {

    return getDelegate().isAddDefaults();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getType()
   */
  public Type getType() {

    return getDelegate().getType();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getParent()
   */
  @Override
  public AbstractConfiguration getParent() {

    return getDelegate().getParent();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    return getDelegate().getOwnerDocument();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(java.lang.String,
   *      java.lang.String)
   */
  public AbstractConfiguration getDescendant(String path, String namespaceUri) {

    return getDelegate().getDescendant(path, namespaceUri);
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendants(java.lang.String,
   *      java.lang.String)
   */
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    return getDelegate().getDescendants(path, namespaceUri);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(String,
   *      String)
   */
  @Override
  public AbstractConfiguration doCreateChild(String name, String namespace)
      throws ConfigurationException {

    return getDelegate().doCreateChild(name, namespace);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespace)
      throws ConfigurationException {

    return getDelegate().getChild(name, namespace);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return getDelegate().getChildren(name, namespaceUri);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.util.regex.Pattern,
   *      java.lang.String)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespaceUri) {

    return getDelegate().getChildren(namePattern, namespaceUri);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.Configuration.Type)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return getDelegate().getChildren(childType);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected void addChild(AbstractConfiguration child) {

    getDelegate().addChild(child);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   */
  @Override
  protected void doRemove() throws ConfigurationException {

    getDelegate().doRemove();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected boolean removeChild(AbstractConfiguration child) {

    return getDelegate().removeChild(child);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    return getDelegate().doDisable();
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#disable()
   */
  public void disable() throws ConfigurationException {

    getDelegate().disable();
  }

  /**
   * @see net.sf.mmm.util.event.EventSource#addListener(EventListener)
   */
  public void addListener(ConfigurationChangeListener listener) {

    getDelegate().addListener(listener);
  }

  /**
   * @see net.sf.mmm.util.event.EventSource#removeListener(EventListener)
   */
  public void removeListener(ConfigurationChangeListener listener) {

    getDelegate().removeListener(listener);
  }

}
