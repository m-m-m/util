/* $Id$ */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.FunctionIF;
import net.sf.mmm.term.api.TermIF;
import net.sf.mmm.term.base.AbstractTerm;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.xml.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This class represents an expression as a simple combination of a function and
 * some arguments. Each argument can be an expression again so any term of
 * functions, constants and variables can be build.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Expression extends AbstractTerm {

    /** uid for serialization */
    private static final long serialVersionUID = -7391477832020859397L;

    /** the function of this expression */
    private final FunctionIF function;

    /** the arguments to the function */
    private final TermIF[] arguments;

    /**
     * The constructor.
     * 
     * @param theFunction
     *        is the function for this expression.
     * @param theArguments
     *        are the arguments applied to the function.
     * @throws ValueException
     *         if the given terms are invalid arguments for the given function.
     */
    public Expression(FunctionIF theFunction, TermIF... theArguments) throws ValueException {

        super();
        this.function = theFunction;
        this.arguments = theArguments;
        this.function.validateArgumentCount(this.arguments.length);
    }

    /**
     * @return the function of this expression.
     */
    public FunctionIF getFunction() {

        return this.function;
    }

    /**
     * @see net.sf.mmm.term.api.TermIF#evaluate(net.sf.mmm.context.api.ContextIF)
     * 
     */
    public Object evaluate(ContextIF environment) throws CalculationException,
            ValueException {

        return this.function.calculate(environment, this.arguments);
    }

    /**
     * @see java.lang.Object#toString()
     * 
     */
    public String toString() {

        StringBuffer result = new StringBuffer();
        String symbol = this.function.getOperatorSymbol();
        if (symbol == null) {
            result.append(this.function.getName());
            result.append(ARGUMENT_START);
            if (this.arguments.length > 0) {
                result.append(this.arguments[0]);
                for (int i = 1; i < this.arguments.length; i++) {
                    result.append(ARGUMENT_SEPARATOR);
                    result.append(this.arguments[i]);
                }
            }
            result.append(ARGUMENT_END);
        } else {
            result.append(EXPRESSION_START);
            if (this.arguments.length < 2) {
                result.append(symbol);
                if (this.arguments.length > 0) {
                    result.append(this.arguments[0]);
                }
            } else {
                result.append(this.arguments[0]);
                for (int i = 1; i < this.arguments.length; i++) {
                    result.append(symbol);
                    result.append(this.arguments[i]);
                }
            }
            result.append(EXPRESSION_END);
        }
        return result.toString();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     * 
     */
    public void toXml(XmlWriterIF serializer) throws XmlException {

        serializer.writeStartElement(XML_TAG_EXPRESSION);
        serializer.writeAttribute(XML_ATR_EXPRESSION_FKTNAME, getFunction().getName());
        for (int i = 0; i < this.arguments.length; i++) {
            this.arguments[i].toXml(serializer);
        }
        serializer.writeEndElement(XML_TAG_EXPRESSION);
    }

}