/* $Id$ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.util.reflect.Arguments;
import net.sf.mmm.value.api.GenericValueIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This class implements the binary function <b>switch</b> that returns the
 * second argument if the first is <code>true</code>, else <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionSwitch extends BasicFunction {

    /** the {@link #getName() name} of this function */
    public static final String NAME = "switch";

    /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
    public static final String SYMBOL = "?";

    /**
     * The constructor.
     */
    public FunctionSwitch() {

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

        return 2;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(GenericValueIF,
     *      GenericValueIF)
     * 
     */
    public Object calculate(Object argument1, Object argument2)
            throws ValueException {
        
        try {
            Boolean test = (Boolean) argument1;
            if (test) {
                return argument2;
            } else {
                //TODO: create fake NULL value in order to work with else!
                return null;
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentTypeException(this, new Arguments(argument1, argument2), e);
        }
    }

}