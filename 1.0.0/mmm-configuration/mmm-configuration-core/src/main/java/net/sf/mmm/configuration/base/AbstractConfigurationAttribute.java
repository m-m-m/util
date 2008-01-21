/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.iterator.EmptyConfigurationIterator;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.Configuration} interface for the
 * {@link #getType() type}
 * {@link net.sf.mmm.configuration.api.Configuration.Type#ATTRIBUTE attribute}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAttribute extends AbstractConfigurationNode {

  /**
   * The constructor.
   * 
   * @param parentConfiguration is the parent configuration.
   */
  public AbstractConfigurationAttribute(AbstractConfiguration parentConfiguration) {

    super(parentConfiguration);
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return Type.ATTRIBUTE;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration getChild(String name, String namespace)
      throws IllegalArgumentException {

    return null;
  }

  /**
   * {@inheritDoc}
   */
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
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespaceUri) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  AbstractConfiguration doCreateChild(String name, String namespace) throws ConfigurationException {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addChild(AbstractConfiguration child, QName qname) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean removeChild(AbstractConfiguration child) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSibling(AbstractConfiguration element) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have siblings!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSibling(AbstractConfiguration element) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have siblings!");
  }

}
