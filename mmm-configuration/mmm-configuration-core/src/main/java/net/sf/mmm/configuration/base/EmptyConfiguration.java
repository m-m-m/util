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
  private static final EmptyConfiguration EMPTY_ELEMENT = new EmptyConfiguration(Type.ELEMENT);

  /** the empty configuration attribute */
  private static final EmptyConfiguration EMPTY_ATTRIBUTE = new EmptyConfiguration(Type.ATTRIBUTE);

  /** @see #getName() */
  private static final String EMPTY_ELEMENT_NAME = "EMPTY";

  /** @see #getName() */
  private static final String EMPTY_ATTRIBUTE_NAME = "@EMPTY";

  /** the type */
  private final Type type;

  /**
   * The constructor.
   * 
   * @param configurationType is the type of the empty configuration.
   */
  private EmptyConfiguration(Type configurationType) {

    super();
    this.type = configurationType;
  }

  /**
   * This method gets the singleton instance of the empty configuration for the
   * given type.
   * 
   * @param configurationType is the type of the requested empty configuration.
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
   * {@inheritDoc}
   */
  public MutableGenericValue getValue() {

    return EmptyValue.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    if (this.type == Type.ATTRIBUTE) {
      return EMPTY_ATTRIBUTE_NAME;
    } else {
      return EMPTY_ELEMENT_NAME;
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getNamespaceUri() {

    return NAMESPACE_URI_NONE;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration doCreateChild(String name, String namespace)
      throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespace)
      throws ConfigurationException {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespace) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doRemove() throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChild(AbstractConfiguration child) {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(AbstractConfiguration child, QName qName) {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSibling(AbstractConfiguration element) {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSibling(AbstractConfiguration element) {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration doDisable() throws ConfigurationException {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disable() throws ConfigurationException {

  }

  /**
   * {@inheritDoc}
   */
  public void addListener(ConfigurationChangeListener listener) {

  }

  /**
   * {@inheritDoc}
   */
  public boolean removeListener(ConfigurationChangeListener listener) {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration getParent() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfigurationDocument getOwnerDocument() {

    return null;
  }

}
