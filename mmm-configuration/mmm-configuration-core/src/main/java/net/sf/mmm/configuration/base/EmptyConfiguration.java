/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.configuration.base.iterator.EmptyConfigurationIterator;
import net.sf.mmm.util.event.EventListener;
import net.sf.mmm.value.api.MutableGenericValue;
import net.sf.mmm.value.base.EmptyValue;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface that is
 * always empty.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyConfiguration extends AbstractConfiguration {

  /** the empty configuration element */
  private static final EmptyConfiguration EMPTY_ELEMENT = new EmptyConfiguration(
      Type.ELEMENT);

  /** the empty configuration attribute */
  private static final EmptyConfiguration EMPTY_ATTRIBUTE = new EmptyConfiguration(
      Type.ATTRIBUTE);

  /** @see #getName() */
  private static final String EMPTY_ELEMENT_NAME = "EMPTY";

  /** @see #getName() */
  private static final String EMPTY_ATTRIBUTE_NAME = "@EMPTY";

  /** the type */
  private final Type type;

  /**
   * The constructor.
   * 
   * @param configurationType
   *        is the type of the empty configuration.
   */
  private EmptyConfiguration(Type configurationType) {

    super();
    this.type = configurationType;
  }

  /**
   * This method gets the singleton instance of the empty configuration for the
   * given type.
   * 
   * @param configurationType
   *        is the type of the requested empty configuration.
   * @return the requested configuration.
   */
  public static final AbstractConfiguration getInstance(Type configurationType) {

    switch (configurationType) {
      case ELEMENT:
        return EMPTY_ELEMENT;
      case ATTRIBUTE:
        return EMPTY_ATTRIBUTE;
      default :
        throw new IllegalArgumentException("" + configurationType);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#getValue()
   */
  public MutableGenericValue getValue() {

    return EmptyValue.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getName() 
   */
  public String getName() {

    if (this.type == Type.ATTRIBUTE) {
      return EMPTY_ATTRIBUTE_NAME;      
    } else {
      return EMPTY_ELEMENT_NAME;
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getNamespaceUri()
   */
  public String getNamespaceUri() {

    return NAMESPACE_URI_NONE;
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#isEditable()
   */
  public boolean isEditable() {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#isAddDefaults()
   */
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getType() 
   */
  public Type getType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getDescendants(java.lang.String,
   *      java.lang.String) 
   */
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    return Collections.emptyList();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfiguration doCreateChild(String name, String namespace) throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespace)
      throws ConfigurationException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.Configuration.Type)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.util.regex.Pattern, java.lang.String)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespace) {
  
    return EmptyConfigurationIterator.getInstance();
  }
  
  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   */
  @Override
  public void doRemove() throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  public boolean removeChild(AbstractConfiguration child) {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration, QName)
   */
  @Override
  public void addChild(AbstractConfiguration child, QName qName) {

    throw new IllegalStateException();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addSibling(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  public void addSibling(AbstractConfiguration element) {
  
    throw new IllegalStateException();
  }
  
  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeSibling(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  public boolean removeSibling(AbstractConfiguration element) {
  
    throw new IllegalStateException();
  }
  
  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   */
  @Override
  public AbstractConfiguration doDisable() throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#disable()
   */
  public void disable() throws ConfigurationException {

  }

  /**
   * @see net.sf.mmm.util.event.EventSource#addListener(EventListener)
   */
  public void addListener(ConfigurationChangeListener listener) {

  }

  /**
   * @see net.sf.mmm.util.event.EventSource#removeListener(EventListener)
   */
  public void removeListener(ConfigurationChangeListener listener) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getParent()
   */
  @Override
  public AbstractConfiguration getParent() {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
   */
  @Override
  public AbstractConfigurationDocument getOwnerDocument() {

    return null;
  }

}
