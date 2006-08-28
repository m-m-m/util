/* $Id: FctGreater.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import java.util.Date;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that determines if the
 * first argument is greater than the second. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctGreater {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = ">";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "greater";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

    /**
     * The constructor.
     */
    private FctGreater() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return <code>true</code> if the first argument is greater than the
     *         second.
     */
    public static Boolean greater(Number argument1, Number argument2) {

        return Boolean.valueOf(argument1.doubleValue() > argument2.doubleValue());
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return <code>true</code> if the first argument is after the second.
     */
    public static Boolean greater(Date argument1, Date argument2) {

        return Boolean.valueOf(argument1.after(argument2));
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return <code>true</code> if the first argument is lexicographically
     *         after the second.
     */
    public static Boolean greater(String argument1, String argument2) {

        int compare = argument1.compareToIgnoreCase(argument2);
        return Boolean.valueOf(compare > 0);
    }

}