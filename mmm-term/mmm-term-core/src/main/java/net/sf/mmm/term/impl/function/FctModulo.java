/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.NlsResourceBundle;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that calculates the modulo
 * (division rest) of the arguments. <br>
 * E.g. <code>5%2</code> results to <code>1</code>.
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctModulo {

    /**
     * the suggested
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "%";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "mod";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

    /**
     * The constructor.
     */
    private FctModulo() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the modulo of both arguments.
     * @throws CalculationException if the second argument is zero.
     */
    public static Integer mod(Integer argument1, Integer argument2) throws CalculationException {

        if (argument2.intValue() == 0) {
            throw new CalculationException(NlsResourceBundle.ERR_FCT_DIV_ZERO);            
        }
        return new Integer(argument1.intValue() % argument2.intValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the modulo of both arguments.
     * @throws CalculationException if the second argument is zero.
     */
    public static Long mod(Long argument1, Integer argument2) throws CalculationException {

        if (argument2.longValue() == 0) {
            throw new CalculationException(NlsResourceBundle.ERR_FCT_DIV_ZERO);            
        }
        return new Long(argument1.longValue() % argument2.longValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the modulo of both arguments.
     * @throws CalculationException if the second argument is zero.
     */
    public static Long mod(Integer argument1, Long argument2) throws CalculationException {

        if (argument2.longValue() == 0) {
            throw new CalculationException(NlsResourceBundle.ERR_FCT_DIV_ZERO);            
        }
        return new Long(argument1.longValue() % argument2.longValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the modulo of both arguments.
     * @throws CalculationException if the second argument is zero.
     */
    public static Long mod(Long argument1, Long argument2) throws CalculationException {

        if (argument2.longValue() == 0) {
            throw new CalculationException(NlsResourceBundle.ERR_FCT_DIV_ZERO);            
        }
        return new Long(argument1.longValue() % argument2.longValue());
    }

}