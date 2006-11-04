/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.value.api.GenericValue;

/**
 * This class represents the {@link net.sf.mmm.term.api.FunctionIF function}
 * <b>else</b> that returns the second argument if the first agrument is
 * <code>null</code> and the first argument in all other cases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionElse extends BasicFunction {

    /** the {@link #getName() name} of this function */
    public static final String NAME = "else";

    /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
    public static final String SYMBOL = ":";

    /**
     * The constructor.
     */
    public FunctionElse() {

        super();
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getName()
     * 
     */
    public String getName() {

        return NAME;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getOperatorSymbol()
     * 
     */
    public String getOperatorSymbol() {

        return SYMBOL;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMinimumArgumentCount()
     * 
     */
    public int getMinimumArgumentCount() {

        return 2;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMaximumArgumentCount()
     * 
     */
    public int getMaximumArgumentCount() {

        return Integer.MAX_VALUE;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(GenericValue,
     *      GenericValue)
     * 
     */
    public Object calculate(Object argument1, Object argument2)
            throws CalculationException {

        if (argument1 == null) {
            return argument2;
        } else {
            return argument1;
        }
    }

}