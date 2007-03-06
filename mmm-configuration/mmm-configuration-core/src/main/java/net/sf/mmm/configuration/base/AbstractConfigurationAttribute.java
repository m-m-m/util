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
   * @param parentConfiguration
   *        is the parent configuration.
   */
  public AbstractConfigurationAttribute(AbstractConfiguration parentConfiguration) {

    super(parentConfiguration);
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getType()
   */
  public Type getType() {

    return Type.ATTRIBUTE;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String)
   */
  public AbstractConfiguration getChild(String name, String namespace)
      throws IllegalArgumentException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.Configuration.Type)
   */
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
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespaceUri) {
  
    return EmptyConfigurationIterator.getInstance();
  }
  
  /**
   * @see AbstractConfiguration#doCreateChild(String, String)
   */
  @Override
  AbstractConfiguration doCreateChild(String name, String namespace) throws ConfigurationException {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration, QName)
   */
  @Override
  protected void addChild(AbstractConfiguration child, QName qname) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected boolean removeChild(AbstractConfiguration child) {

    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have children!");
  }
 
  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addSibling(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  public void addSibling(AbstractConfiguration element) {
  
    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have siblings!");
  }
  
  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeSibling(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  public boolean removeSibling(AbstractConfiguration element) {
  
    // TODO: NLS
    throw new ConfigurationException("Attribute cannot have siblings!");
  }
  
}
