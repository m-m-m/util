/* $Id$ */
package net.sf.mmm.configuration.impl.format.properties;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationAttribute;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;


/**
 * TODO This type ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesAttribute extends AbstractConfigurationAttribute {

    /**
     * The constructor.
     *
     * @param parentConfiguration
     */
    public PropertiesAttribute(AbstractConfiguration parentConfiguration) {

        super(parentConfiguration);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see net.sf.mmm.configuration.base.BasicConfiguration#getPlainString()
     * {@inheritDoc}
     */
    @Override
    protected String getPlainString() {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.configuration.base.BasicConfiguration#setPlainString(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void setPlainString(String newValue) {

    // TODO Auto-generated method stub

    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
     * {@inheritDoc}
     */
    @Override
    protected AbstractConfigurationDocument getOwnerDocument() {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
     * {@inheritDoc}
     */
    @Override
    protected void doRemove() throws ConfigurationException {

    // TODO Auto-generated method stub

    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
     * {@inheritDoc}
     */
    @Override
    protected AbstractConfiguration doDisable() throws ConfigurationException {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getName()
     * {@inheritDoc}
     */
    public String getName() {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri()
     * {@inheritDoc}
     */
    public String getNamespaceUri() {

        // TODO Auto-generated method stub
        return null;
    }

}
