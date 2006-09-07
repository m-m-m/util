/* $Id$ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of the
 * {@link ConfigurationAccessFactoryIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAccessFactory implements ConfigurationAccessFactoryIF {

    /** the file accessors */
    private AbstractConfigurationAccess[] accessors;

    /**
     * The constructor.
     */
    public AbstractConfigurationAccessFactory() {

        super();
        this.accessors = null;
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#configure(java.lang.String,
     *      net.sf.mmm.context.api.ContextIF,
     *      net.sf.mmm.configuration.api.ConfigurationIF) {@inheritDoc}
     */
    public final void configure(String prefix, ContextIF context, ConfigurationIF include)
            throws ConfigurationException, ValueException {

        String href = include.getDescendant(ConfigurationDocumentIF.NAME_INCLUDE_HREF).getValue()
                .getString();
        String parentKey = prefix + CONTEXT_VARIABLE_SUFFIX_PARENT;
        ConfigurationAccessIF parentAccess = (ConfigurationAccessIF) context.getValue(parentKey)
                .getObject(null);
        // TODO: only store root url access specific and relative href
        // globally???

        // TODO: base is 'http://foo.com/bar/test.xml'
        // 'foo/some.xml' -> 'http://foo.com/bar/foo/some.xml'
        // '/foo/some.xml' -> 'http://foo.com/foo/some.xml'
        // 'http://bar.com/foo/test.xml' -> 'http://bar.com/foo/test.xml'
        // same for 'C:\bar\text.xml' and '\foo\test.xml' -> 'C:\foo\test.xml'

        // This means there is a top-root 'http://foo.com' or 'C:\'
        // and additionally a pwd 'http://foo.com/bar' or 'C:\bar'
        this.accessors = configure(prefix, context, include, parentAccess, href);
        for (int i = 0; i < this.accessors.length; i++) {
            this.accessors[i].setContextPrefix(prefix);
        }
    }

    /**
     * @see #configure(java.lang.String, net.sf.mmm.context.api.ContextIF,
     *      net.sf.mmm.configuration.api.ConfigurationIF)
     * @see #getAccessors()
     * 
     * @param prefix
     *        is the prefix for the
     *        {@link ContextIF#getValue(String) "variables"}.
     * @param context
     *        is the context (potentially) containing the required configuration.
     * @param include
     *        is the
     *        {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE include}
     *        configuration.
     * @param parent
     *        is the paretn access the given <code>href</code> may be relative
     *        to. It should only be interpreted if the given <code>href</code>
     *        is relative and may potentially be <code>null</code> in other
     *        cases.
     * @param href
     *        is the value of the
     *        {@link ConfigurationDocumentIF#NAME_INCLUDE_HREF} attribute. It
     *        can be relative or absolute.
     * @return the {@link #getAccessors() accessors}.
     * @throws ConfigurationException
     */
    public abstract AbstractConfigurationAccess[] configure(String prefix, ContextIF context,
            ConfigurationIF include, ConfigurationAccessIF parent, String href)
            throws ConfigurationException;

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#getAccessors()
     *      {@inheritDoc}
     */
    public final ConfigurationAccessIF[] getAccessors() {

        return this.accessors;
    }

}