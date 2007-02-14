/* $Id$ */
package net.sf.mmm.configuration.impl.format.properties;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationAttribute;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface to
 * adapt an single
 * {@link java.util.Properties#getProperty(java.lang.String) property}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesAttribute extends AbstractConfigurationAttribute {

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
   *        is the {@link #getParent() parent} configuration.
   * @param document
   *        is the {@link #getOwnerDocument() owner-document}.
   * @param propertyKey
   *        is the {@link java.util.Properties#getProperty(String) property-key}.
   * @param propertyName
   *        is the {@link #getName() name}.
   * @param propertyNamespace
   *        is the {@link #getNamespaceUri() NameSpace-URI}.
   */
  public PropertiesAttribute(AbstractConfiguration parentConfiguration, PropertiesDocument document,
      String propertyKey, String propertyName, String propertyNamespace) {

    super(parentConfiguration);
    this.doc = document;
    this.key = propertyKey;
    this.name = propertyName;
    this.namespace = propertyNamespace;
  }
  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getOwnerDocument()
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    return this.doc;
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#getPlainString()
   */
  @Override
  protected String getPlainString() {

    return this.doc.getProperties().getProperty(this.key);
  }

  /**
   * @see net.sf.mmm.configuration.base.BasicConfiguration#setPlainString(java.lang.String)
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
   */
  public String getNamespaceUri() {

    return this.namespace;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   */
  @Override
  protected void doRemove() throws ConfigurationException {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    // TODO Auto-generated method stub
    return null;
  }

}
