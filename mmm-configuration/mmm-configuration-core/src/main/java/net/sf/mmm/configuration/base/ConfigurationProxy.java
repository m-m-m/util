/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
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
   * {@inheritDoc}
   */
  public MutableGenericValue getValue() {

    return getDelegate().getValue();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return getDelegate().getName();
  }

  /**
   * {@inheritDoc}
   */
  public String getNamespaceUri() {

    return getDelegate().getNamespaceUri();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return getDelegate().isEditable();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAddDefaults() {

    return getDelegate().isAddDefaults();
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return getDelegate().getType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getParent() {

    return getDelegate().getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    return getDelegate().getOwnerDocument();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getDescendant(String path, String namespaceUri) {

    return getDelegate().getDescendant(path, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    return getDelegate().getDescendants(path, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration doCreateChild(String name, String namespace)
      throws ConfigurationException {

    return getDelegate().doCreateChild(name, namespace);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespace)
      throws ConfigurationException {

    return getDelegate().getChild(name, namespace);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return getDelegate().getChildren(name, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespaceUri) {

    return getDelegate().getChildren(namePattern, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return getDelegate().getChildren(childType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addChild(AbstractConfiguration child, QName qName) {

    getDelegate().addChild(child, qName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doRemove() throws ConfigurationException {

    getDelegate().doRemove();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean removeChild(AbstractConfiguration child) {

    return getDelegate().removeChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    return getDelegate().doDisable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disable() throws ConfigurationException {

    getDelegate().disable();
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(ConfigurationChangeListener listener) {

    getDelegate().addListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeListener(ConfigurationChangeListener listener) {

    return getDelegate().removeListener(listener);
  }

}
