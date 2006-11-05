/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that performs the logical
 * (boolean) or the bitwise (integer) OR operation. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctOr {

    /**
     * the suggested
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "|";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "or";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.MEDIUM;

    /**
     * The constructor.
     */
    private FctOr() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the bitwise OR of both arguments.
     */
    public static Integer or(Integer argument1, Integer argument2) {

        return Integer.valueOf(argument1.intValue() | argument2.intValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the logical OR of both arguments (<code>true</code> if one of
     *         the arguments is <code>true</code>).
     */
    public static Boolean or(Boolean argument1, Boolean argument2) {

        return Boolean.valueOf(argument1.booleanValue() || argument2.booleanValue());
    }

}