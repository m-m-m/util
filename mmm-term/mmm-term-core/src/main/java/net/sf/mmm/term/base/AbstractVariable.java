/* $Id: AbstractVariable.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.base;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of a the
 * {@link net.sf.mmm.term.api.TermIF} interface that represents a variable.<br>
 * A variable
 * {@link net.sf.mmm.term.api.TermIF#evaluate(ContextIF) evaluates} to an
 * {@link net.sf.mmm.context.api.ContextIF#getObject(String) "environment value"}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractVariable extends AbstractTerm {

    /**
     * The constructor.
     */
    public AbstractVariable() {

        super();
    }

    /**
     * @see net.sf.mmm.term.api.TermIF#evaluate(net.sf.mmm.context.api.ContextIF)
     * {@inheritDoc}
     */
    public final Object evaluate(ContextIF environment) throws ValueException {

        return environment.getObject(getVariableName(environment));
    }

    /**
     * This method gets the name of the variable.
     * 
     * @return the name of the variable.
     * @param environment
     *        is the environment given to evaluate this variable.
     * @throws ValueException
     *         if the variable name is determined dynamically via a calculation
     *         that failed.
     */
    public abstract String getVariableName(ContextIF environment) throws ValueException;

}
