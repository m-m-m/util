/* $Id$ */
package net.sf.mmm.term.base;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This class represents a variable {@link net.sf.mmm.term.api.TermIF term}.
 * The variable simply contains a variable-name and
 * {@link net.sf.mmm.term.api.TermIF#evaluate(ContextIF) evaluates} to the
 * {@link net.sf.mmm.context.api.ContextIF#getValue(String) "environment value"}
 * with that name.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Variable extends AbstractVariable {

    /** uid for serialization */
    private static final long serialVersionUID = -8438644468276039165L;

    /** the name of the variable */
    private final String variableName;

    /**
     * The constructor.
     * 
     * @param name
     *        is the name of the variable.
     */
    public Variable(String name) {

        super();
        this.variableName = name;
    }

    /**
     * This method gets the name of the variable. Parameters are ignored.
     * 
     * @see net.sf.mmm.term.base.AbstractVariable#getVariableName(ContextIF)
     */
    public String getVariableName(ContextIF environment) {

        return this.variableName;
    }

    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    public String toString() {

        return VARIABLE_START + this.variableName + VARIABLE_END;
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     * {@inheritDoc}
     */
    public void toXml(XmlWriterIF xmlWriter) throws XmlException {

        xmlWriter.writeStartElement(XML_TAG_VARIABLE);
        xmlWriter.writeAttribute(XML_ATR_VARIABLE_NAME, this.variableName);
        xmlWriter.writeEndElement(XML_TAG_VARIABLE);
    }

}
