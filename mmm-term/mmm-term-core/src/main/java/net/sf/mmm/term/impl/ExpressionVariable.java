/* $Id$ */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.term.CoreNlsResourceBundle;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.TermIF;
import net.sf.mmm.term.base.AbstractVariable;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.xml.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This class represents a variable as term. The name of the variable is given
 * as expression.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ExpressionVariable extends AbstractVariable {

    /** uid for serialization */
    private static final long serialVersionUID = -6989674048385225206L;

    /**
     * the term that must resolve to a string that will be interpreted as name
     * of the variable.
     */
    private TermIF term;

    /**
     * The constructor.
     * 
     * @param variableNameTerm
     *        is an expression that must resolve to the name of the variable (as
     *        string value).
     * @throws CalculationException
     *         if the given term has a return type can not match {@link String}.
     *         It might be confusing that the caused exception is called this
     *         way but however.
     */
    public ExpressionVariable(TermIF variableNameTerm) throws CalculationException {

        super();
        this.term = variableNameTerm;
    }

    /**
     * @see net.sf.mmm.term.base.AbstractVariable#getVariableName(net.sf.mmm.context.api.ContextIF)
     * 
     */
    public String getVariableName(ContextIF environment) throws ValueException {

        Object expressionResult = this.term.evaluate(environment);
        if (expressionResult == null) {
            throw new CalculationException(CoreNlsResourceBundle.ERR_EXPR_VAR_NULL, this);
        }
        return expressionResult.toString();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     * 
     */
    public void toXml(XmlWriterIF serializer) throws XmlException {

        serializer.writeStartElement(XML_TAG_VARIABLE);
        this.term.toXml(serializer);
        serializer.writeEndElement(XML_TAG_VARIABLE);
    }

    /**
     * @see java.lang.Object#toString()
     * 
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(VARIABLE_START);
        sb.append(EXPRESSION_START);
        sb.append(this.term.toString());
        sb.append(EXPRESSION_END);
        sb.append(VARIABLE_END);
        return sb.toString();
    }

}