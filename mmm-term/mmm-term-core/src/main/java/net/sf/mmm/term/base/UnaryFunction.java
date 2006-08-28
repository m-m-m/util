/* $Id: UnaryFunction.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.base;

import net.sf.mmm.term.api.OperatorPriority;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of a unary
 * {@link net.sf.mmm.term.api.FunctionIF function}. This means that the
 * function takes exactly <code>1</code> argument.
 * 
 * @see net.sf.mmm.term.api.FunctionIF#calculate(net.sf.mmm.environment.api.EnvironmentIF,
 *      net.sf.mmm.term.api.TermIF[])
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UnaryFunction extends BasicFunction {

    /**
     * The constructor.
     * 
     */
    public UnaryFunction() {

        super();
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getOperatorPriority()
     * {@inheritDoc}
     */
    @Override
    public OperatorPriority getOperatorPriority() {

        return OperatorPriority.MAXIMUM;
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

        return 1;
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(Object)
     * {@inheritDoc}
     */
    @Override
    public abstract Object calculate(Object argument) throws ValueException;

}
