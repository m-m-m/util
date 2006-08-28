/* $Id: FctAdd.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This is the abstract base class of a partial implementation of the add
 * function that builds the sum of its arguments. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class FctAdd {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "+";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "add";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.MEDIUM;

    /**
     * The constructor.
     */
    protected FctAdd() {

        super();
    }

}
