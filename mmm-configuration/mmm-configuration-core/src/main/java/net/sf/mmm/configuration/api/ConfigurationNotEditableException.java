/* $ Id: $ */
package net.sf.mmm.configuration.api;

import net.sf.mmm.configuration.NlsResourceBundle;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF configuration-node}
 * was edited without being {@link MutableConfigurationIF#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationNotEditableException extends ConfigurationException {

    /** uid for serialization */
    private static final long serialVersionUID = 8537295080260761963L;

    /**
     * The constructor.
     * 
     * @param node
     *        is the node that is NOT
     *        {@link MutableConfigurationIF#isEditable() editable} .
     */
    public ConfigurationNotEditableException(MutableConfigurationIF node) {

        super(NlsResourceBundle.ERR_NODE_NOT_EDITABLE, node);
    }

    /**
     * The constructor.
     * 
     * @param node
     *        is the node that is NOT
     *        {@link MutableConfigurationIF#isEditable() editable} .
     * @param nested
     *        is the throwable that caused this exception.
     */
    public ConfigurationNotEditableException(MutableConfigurationIF node, Throwable nested) {

        super(nested, NlsResourceBundle.ERR_NODE_NOT_EDITABLE, node);
    }

    /**
     * This method gets the node that was edited without being
     * {@link MutableConfigurationIF#isEditable() editable}.
     * 
     * @return the associated configuration node.
     */
    public MutableConfigurationIF getConfiguration() {

        return (MutableConfigurationIF) getNlsMessage().getArgument(0);
    }

}
