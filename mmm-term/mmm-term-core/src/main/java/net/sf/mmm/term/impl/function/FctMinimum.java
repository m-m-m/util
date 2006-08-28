/* $Id$ */
package net.sf.mmm.term.impl.function;

import java.util.Date;

/**
 * This class partially implements a binary function that determines the minimum
 * of two given arguments. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctMinimum {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = null;

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "min";

    /**
     * The constructor.
     */
    private FctMinimum() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the minimum of both arguments.
     */
    public static Number min(Number argument1, Number argument2) {

        if (argument1.doubleValue() <= argument2.doubleValue()) {
            return argument1;
        } else {
            return argument2;
        }
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return the minimum of both arguments.
     */
    public static Date min(Date argument1, Date argument2) {

        if (argument1.before(argument2)) {
            return argument1;
        } else {
            return argument2;
        }
    }
    
}