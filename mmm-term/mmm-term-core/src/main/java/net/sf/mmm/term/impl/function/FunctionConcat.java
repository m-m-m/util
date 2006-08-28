/* $Id: FunctionConcat.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.util.reflect.Arguments;
import net.sf.mmm.value.api.GenericValueIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This class represents the {@link net.sf.mmm.term.api.FunctionIF function}
 * <b>concat</b> that concats all given arguments.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionConcat extends BasicFunction {

    /** the {@link #getName() name} of this function */
    public static final String NAME = "concat";

    /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
    public static final String SYMBOL = null;

    /**
     * The constructor.
     */
    public FunctionConcat() {

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

        return 1;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMaximumArgumentCount()
     * {@inheritDoc}
     */
    public int getMaximumArgumentCount() {

        return Integer.MAX_VALUE;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(java.lang.Object)
     * {@inheritDoc}
     */
    @Override
    public Object calculate(Object argument) throws ValueException {

        return argument;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(GenericValueIF,
     *      GenericValueIF)
     * {@inheritDoc}
     */
    public String calculate(Object argument1, Object argument2) throws CalculationException {

        if ((argument1 != null) && (argument1.getClass() == String.class)) {
            return ((String) argument1) + argument2;
        }
        throw new IllegalArgumentTypeException(this, new Arguments(argument1, argument2));
    }

}