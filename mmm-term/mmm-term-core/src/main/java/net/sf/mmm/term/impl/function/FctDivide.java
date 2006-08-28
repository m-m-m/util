/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.NlsResourceBundle;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that performs the division
 * of the arguments. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctDivide {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "/";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "div";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

    /**
     * The constructor.
     */
    private FctDivide() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the division of both arguments.
     * @throws CalculationException
     *         if the second argument is <code>0</code>.
     */
    public static Double div(Number argument1, Number argument2) throws CalculationException {

        double quotient = argument2.doubleValue();
        if (quotient == 0) {
            throw new CalculationException(NlsResourceBundle.ERR_FCT_DIV_ZERO);
        }
        return Double.valueOf(argument1.doubleValue() / quotient);
    }

}