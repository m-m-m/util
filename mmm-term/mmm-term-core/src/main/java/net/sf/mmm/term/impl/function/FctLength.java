/* $Id$ */
package net.sf.mmm.term.impl.function;

import java.util.List;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a unary function that determines the length
 * of {@link java.lang.String}and {@link java.util.List}values. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctLength {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "#";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "length";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.MAXIMUM;

    /**
     * The constructor.
     */
    private FctLength() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the length of the given string.
     */
    public static Integer length(String argument) {

        if (argument == null) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(argument.length());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument
     *        is the argument of the function.
     * @return the length of the given string.
     */
    public static Integer length(List argument) {

        if (argument == null) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(argument.size());
    }

}