/* $Id$ */
package net.sf.mmm.configuration.impl.format.properties;

import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.AbstractConfigurationElement;
import net.sf.mmm.configuration.base.EmptyDummyConfiguration;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface to
 * adapt an single
 * {@link java.util.Properties#getProperty(java.lang.String) property}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesElement extends AbstractConfigurationElement {

  /** the document */
  private final PropertiesDocument doc;

  /** the properties key */
  private final String key;

  /** the {@link #getName() name} */
  private final String name;

  /** the {@link #getNamespaceUri() namespace} */
  private final String namespace;

  /**
   * The constructor.
   * 
   * @param parentConfiguration
   * @param document
   * @param propertyKey
   * @param propertyName
   * @param propertyNamespace
   */
  public PropertiesElement(AbstractConfiguration parentConfiguration, PropertiesDocument document,
      String propertyKey, String propertyName, String propertyNamespace) {

    super(parentConfiguration);
    this.doc = document;
    this.key = propertyKey;
    this.name = propertyName;
    this.namespace = propertyNamespace;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfigurationElement#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   *      
   */
  @Override
  protected void addChild(AbstractConfiguration child) {

    super.addChild(child);
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfigurationElement#createChildAttribute(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  protected AbstractConfiguration createChildAttribute(String childName, String namespaceUri) {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfigurationElement#createChildElement(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  protected AbstractConfiguration createChildElement(String childName, String namespaceUri) {

    String subName;
    if (this.doc.isFlat()) {
      if (this.key.length() == 0) {
        subName = childName;
      } else {
        // TODO
        return EmptyDummyConfiguration.getInstance(Type.ELEMENT);
      }
    } else {
      subName = this.key + this.doc.getSeparator() + childName;
    }
    return new PropertiesElement(this, this.doc, subName, childName, namespaceUri);
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getOwnerDocument()
   *      
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    return this.doc;
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getPlainString()
   *      
   */
  @Override
  protected String getPlainString() {

    return this.doc.getProperties().getProperty(this.key);
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#setPlainString(java.lang.String)
   *      
   */
  @Override
  protected void setPlainString(String newValue) {

    this.doc.getProperties().setProperty(this.key, newValue);
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getName() 
   */
  public String getName() {

    return this.name;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getNamespaceUri()
   *      
   */
  public String getNamespaceUri() {

    return this.namespace;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   *      
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    // TODO
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   *      
   */
  @Override
  protected void doRemove() throws ConfigurationException {

    this.doc.getProperties().remove(this.key);
    Iterator<AbstractConfiguration> children = getChildren((Type) null);
    while (children.hasNext()) {
      AbstractConfiguration child = children.next();
      if (child instanceof PropertiesElement) {
        PropertiesElement pe = (PropertiesElement) child;
        pe.doRemove();
      }
    }
  }

}
