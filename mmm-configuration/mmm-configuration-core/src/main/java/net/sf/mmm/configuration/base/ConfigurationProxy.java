/* $Id: ConfigurationProxy.java 205 2006-08-10 19:04:59Z hohwille $ */
package net.sf.mmm.configuration.base;

import java.util.Collection;
import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListenerIF;
import net.sf.mmm.event.EventListenerIF;
import net.sf.mmm.value.api.MutableGenericValueIF;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.configuration.api.ConfigurationIF} interface that delegates
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
     * @see net.sf.mmm.configuration.api.MutableConfigurationIF#getValue()
     * {@inheritDoc}
     */
    public MutableGenericValueIF getValue() {

        return getDelegate().getValue();
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getName()
     * {@inheritDoc}
     */
    public String getName() {

        return getDelegate().getName();
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri()
     * {@inheritDoc}
     */
    public String getNamespaceUri() {

        return getDelegate().getNamespaceUri();
    }

    /**
     * @see net.sf.mmm.configuration.api.MutableConfigurationIF#isEditable()
     * {@inheritDoc}
     */
    public boolean isEditable() {

        return getDelegate().isEditable();
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#isAddDefaults()
     * {@inheritDoc}
     */
    public boolean isAddDefaults() {

        return getDelegate().isAddDefaults();
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getType()
     * {@inheritDoc}
     */
    public Type getType() {

        return getDelegate().getType();
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getParent()
     * {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration getParent() {

        return getDelegate().getParent();
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
     * {@inheritDoc}
     */
    @Override
    protected AbstractConfigurationDocument getOwnerDocument() {
    
        return getDelegate().getOwnerDocument();
    }
    
    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public AbstractConfiguration getDescendant(String path, String namespaceUri) {

        return getDelegate().getDescendant(path, namespaceUri);
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getDescendants(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

        return getDelegate().getDescendants(path, namespaceUri);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(String,
     *      String)
     * {@inheritDoc}
     */
    @Override
    public AbstractConfiguration doCreateChild(String name, String namespace)
            throws ConfigurationException {

        return getDelegate().doCreateChild(name, namespace);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    @Override
    public AbstractConfiguration getChild(String name, String namespace)
            throws ConfigurationException {

        return getDelegate().getChild(name, namespace);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    @Override
    public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

        return getDelegate().getChildren(name, namespaceUri);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.ConfigurationIF.Type)
     * {@inheritDoc}
     */
    @Override
    public Iterator<AbstractConfiguration> getChildren(Type childType) {

        return getDelegate().getChildren(childType);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     * {@inheritDoc}
     */
    @Override
    protected void addChild(AbstractConfiguration child) {
    
        getDelegate().addChild(child);
    }
    
    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
     * {@inheritDoc}
     */
    @Override
    protected void doRemove() throws ConfigurationException {

        getDelegate().doRemove();
    }
    
    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     * {@inheritDoc}
     */
    @Override
    protected void removeChild(AbstractConfiguration child) {

        getDelegate().removeChild(child);
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
     * {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration doDisable() throws ConfigurationException {
    
        return getDelegate().doDisable();
    }

    /**
     * @see net.sf.mmm.configuration.api.MutableConfigurationIF#disable()
     * {@inheritDoc}
     */
    public void disable() throws ConfigurationException {

        getDelegate().disable();
    }

    /**
     * @see net.sf.mmm.event.EventSourceIF#addListener(EventListenerIF)
     * {@inheritDoc}
     */
    public void addListener(ConfigurationChangeListenerIF listener) {

        getDelegate().addListener(listener);
    }

    /**
     * @see net.sf.mmm.event.EventSourceIF#removeListener(EventListenerIF)
     * {@inheritDoc}
     */
    public void removeListener(ConfigurationChangeListenerIF listener) {

        getDelegate().removeListener(listener);
    }

}
