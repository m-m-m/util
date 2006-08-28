/* $Id: FctAnd.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that performs the logical
 * (boolean) or the bitwise (integer) AND operation. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctAnd {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "&";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "and";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

    /**
     * The constructor.
     */
    private FctAnd() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the binary AND of both arguments.
     */
    public static Integer and(Integer argument1, Integer argument2) {

        return Integer.valueOf(argument1.intValue() & argument2.intValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the logical AND of both arguments (<code>true</code> if both
     *         arguments are <code>true</code>).
     */
    public static Boolean and(Boolean argument1, Boolean argument2) {

        return Boolean.valueOf(argument1.booleanValue() && argument2.booleanValue());
    }

}