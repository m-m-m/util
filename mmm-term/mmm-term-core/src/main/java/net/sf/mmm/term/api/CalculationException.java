/* $Id$ */
package net.sf.mmm.term.api;

import net.sf.mmm.value.api.ValueException;

/**
 * This is the exception thrown if the
 * {@link net.sf.mmm.term.api.TermIF#evaluate(net.sf.mmm.environment.api.EnvironmentIF) evaluation}
 * of a {@link net.sf.mmm.term.api.TermIF term} failes because of an invalid
 * calculation. This can have various reasons such as zero divide or
 * incompatible types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CalculationException extends ValueException {

    /** uid for serialization */
    private static final long serialVersionUID = 8736558379218781121L;

    /**
     * @see ValueException#ValueException(String, Object[])
     * {@inheritDoc}
     */
    public CalculationException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see ValueException#ValueException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public CalculationException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}
