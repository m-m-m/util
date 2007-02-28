/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.MutableConfiguration;
import net.sf.mmm.configuration.base.iterator.ChildPatternIterator;
import net.sf.mmm.configuration.base.iterator.ChildTypeIterator;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface for the
 * {@link #getType() type} {@link Configuration.Type#ELEMENT element}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationElement extends AbstractConfigurationNode {

  /** the child attributes */
  private final Map<QName, AbstractConfiguration> children;

  /**
   * The constructor.
   * 
   * @param parentConfiguration
   *        is the {@link #getParent() parent} configuration.
   */
  public AbstractConfigurationElement(AbstractConfiguration parentConfiguration) {

    super(parentConfiguration);
    this.children = new HashMap<QName, AbstractConfiguration>();
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getType()
   */
  public Type getType() {

    return Type.ELEMENT;
  }

  /**
   * This method creates a new empty and unattached
   * {@link Configuration.Type#ATTRIBUTE attribute} as
   * {@link #getChildren(Type) child} of this configuration.
   * 
   * @param name
   *        is the {@link #getName() name} of the
   *        {@link Configuration.Type#ATTRIBUTE attribute} to create (including
   *        the {@link Configuration#NAME_PREFIX_ATTRIBUTE prefix}
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the attribute.
   * @return the new attribute.
   */
  protected abstract AbstractConfiguration createChildAttribute(String name, String namespaceUri);

  /**
   * This method creates a new empty {@link Configuration.Type#ELEMENT element}
   * as child of this configuration.
   * 
   * @param name
   *        is the {@link #getName() name} of the
   *        {@link Configuration.Type#ATTRIBUTE attribute} to create.
   * @param namespaceUri
   *        is the {@link #getNamespaceUri() namespace} of the element.
   * @return the new element.
   */
  protected abstract AbstractConfiguration createChildElement(String name, String namespaceUri);

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespaceUri)
      throws ConfigurationException {

    if (name.length() == 0) {
      throw new ConfigurationException("Child name must not be empty!");
    }
    QName qName = new QName(name, namespaceUri);
    AbstractConfiguration child = this.children.get(qName);
    if ((child == null) && (namespaceUri != null) && (namespaceUri.equals(getNamespaceUri()))
        && name.charAt(0) == NAME_PREFIX_ATTRIBUTE) {
      qName = new QName(name, NAMESPACE_URI_NONE);
      child = this.children.get(qName);
    }
    return child;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(java.lang.String,
   *      java.lang.String)
   */
  @Override
  AbstractConfiguration doCreateChild(String name, String namespace) throws ConfigurationException {

    QName qName = new QName(name, namespace);
    AbstractConfiguration child = this.children.get(qName);
    if (name.charAt(0) == Configuration.NAME_PREFIX_ATTRIBUTE) {
      // does attribute already exist?
      if (child == null) {
        child = createChildAttribute(name, namespace);
        this.children.put(qName, child);
      }
      return child;
    } else {
      AbstractConfiguration newChild = createChildElement(name, namespace);
      if (child == null) {
        this.children.put(qName, newChild);
      } else {
        child.addSibling(newChild);
      }
      return newChild;
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.Configuration.Type)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return new ChildTypeIterator(this.children.values().iterator(), childType);
  }

  /**
   * This method is called if a {@link #getChild(String, String) child} has been
   * {@link #addChild(AbstractConfiguration) added} that has the
   * {@link #getNamespaceUri() namespace}
   * {@link ConfigurationDocument#NAMESPACE_URI_CONFIGURATION}.
   * 
   * @param child
   *        is the child to handle.
   */
  protected void handleInternalChild(AbstractConfiguration child) {

    String name = child.getName();
    if (ConfigurationDocument.NAME_CONTEXT.equals(name)) {
      Collection<? extends MutableConfiguration> envChildIt = child
          .getDescendants(ConfigurationDocument.NAME_CONTEXT_VARIABLE);
      for (Configuration variable : envChildIt) {
        String variableName = variable.getDescendant(
            ConfigurationDocument.NAME_CONTEXT_VARIABLE_NAME).getValue().getString();
        String variableValue = variable.getValue().getString();
        getOwnerDocument().getMutableContext().setObject(variableName, variableValue);
      }
    } else if (ConfigurationDocument.NAME_INCLUDE.equals(name)) {
      // getOwnerDocument().isIncludeEnabled();
      // TODO:
      // TODO: allow to disable includes per document for security reasons
      List<AbstractConfiguration> includeList = new ArrayList<AbstractConfiguration>();
      ConfigurationUtil.resolveInclude(child, includeList);
      for (AbstractConfiguration includeChild : includeList) {
        if (includeChild.getType() == Type.ELEMENT) {
          addChild(includeChild);
        } else {
          throw new ConfigurationException("Including attributes is NOT allowed!");
        }
      }
    } else if (ConfigurationDocument.NAME_EXCLUDE.equals(name)) {
      // nothing to do...
    } else {
      // TODO
      // throw new ConfigurationException("Illegal child '" + name +
      // "'!");
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected void addChild(AbstractConfiguration child) {

    String namespace = child.getNamespaceUri();
    QName qName = new QName(child.getName(), namespace);
    if (ConfigurationDocument.NAMESPACE_URI_CONFIGURATION.equals(namespace)) {
      handleInternalChild(child);
    }
    AbstractConfiguration existingChild = this.children.get(qName);
    if (existingChild == null) {
      this.children.put(qName, child);
    } else {
      switch (child.getType()) {
        case ATTRIBUTE:
          throw new IllegalArgumentException("Duplicate Attribute: " + qName + " in " + this);
        case ELEMENT:
          existingChild.addSibling(child);
          break;
        default :
          throw new IllegalArgumentException("unknown type for " + child);
      }
    }
    setModified();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected boolean removeChild(AbstractConfiguration child) {

    if (!isEditable()) {
      throw new ConfigurationException("not editable");
    }
    boolean modified;
    QName qName = new QName(child.getName(), child.getNamespaceUri());
    switch (child.getType()) {
      case ATTRIBUTE:
        AbstractConfiguration old = this.children.remove(qName);
        modified = (old != null);
        break;
      case ELEMENT:
        AbstractConfiguration head = this.children.get(qName);
        AbstractConfiguration following = head.getNextSibling();
        modified = head.removeSibling(child);
        if (child == head) {
          // remove first
          // if there is only one element in the sibling list, remove it
          if (following == child) {
            this.children.remove(qName);
          } else {
            this.children.put(qName, following);
          }
          modified = true;
        }
        break;
      default :
        throw new IllegalArgumentException("unknown type for " + child);
    }
    if (modified) {      
      setModified();
    }
    return modified;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.util.regex.Pattern,
   *      java.lang.String)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Pattern namePattern, String namespaceUri) {

    return new ChildPatternIterator(this.children.values().iterator(), namePattern, namespaceUri);
  }

}
