/* $Id: FctAbsolute.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a unary function that determines the absolute
 * value of the given argument. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctAbsolute {

    /**
     * the suggested
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = null;

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "abs";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.MEDIUM;

    /**
     * The constructor.
     */
    private FctAbsolute() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the mathematically absolute value.
     */
    public static Integer abs(Integer argument) {

        return Integer.valueOf(Math.abs(argument.intValue()));
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the mathematically absolute value.
     */
    public static Long abs(Long argument) {

        return Long.valueOf(Math.abs(argument.longValue()));
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the mathematically absolute value.
     */
    public static Float abs(Float argument) {

        return Float.valueOf(Math.abs(argument.floatValue()));
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the mathematically absolute value.
     */
    public static Double abs(Double argument) {

        return Double.valueOf(Math.abs(argument.doubleValue()));
    }

}