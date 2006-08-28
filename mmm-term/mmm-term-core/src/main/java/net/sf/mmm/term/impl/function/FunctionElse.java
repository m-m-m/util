/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.value.api.GenericValueIF;

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
     * {@inheritDoc}
     */
    public String getName() {

        return NAME;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getOperatorSymbol()
     * {@inheritDoc}
     */
    public String getOperatorSymbol() {

        return SYMBOL;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMinimumArgumentCount()
     * {@inheritDoc}
     */
    public int getMinimumArgumentCount() {

        return 2;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMaximumArgumentCount()
     * {@inheritDoc}
     */
    public int getMaximumArgumentCount() {

        return Integer.MAX_VALUE;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(GenericValueIF,
     *      GenericValueIF)
     * {@inheritDoc}
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