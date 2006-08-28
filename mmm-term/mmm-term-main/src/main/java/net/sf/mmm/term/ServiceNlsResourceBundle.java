/* $Id$ */
package net.sf.mmm.term;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the term component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ServiceNlsResourceBundle extends AbstractResourceBundle {

    /**
     * The constructor.
     */
    public ServiceNlsResourceBundle() {

        super();
    }

    /**
     * exception message if function with the same name is already registered.
     * 
     * @see net.sf.mmm.term.base.AbstractFunctionService
     */
    public static final String ERR_FCT_NAME_USED = "Function \"{0}\" can not be registered: name is already in use!";

    /**
     * exception message if function with the same operator symbol is already
     * registered.
     * 
     * @see net.sf.mmm.term.base.AbstractFunctionService
     */
    public static final String ERR_FCT_SYMBOL_USED = "Function \"{0}\" can not be registered: operator symbol is already in use!";

    /**
     * exception message if function for name/symbol was not found.
     * 
     * @see net.sf.mmm.term.api.NoSuchFunctionException
     */
    public static final String ERR_FCT_NO_SUCH_NAME_OR_SYMBOL = "Function \"{0}\" can not be found!";

    /**
     * exception message for illegal function argument count.
     * 
     * @see net.sf.mmm.term.api.IllegalArgumentCountException
     */
    public static final String ERR_ILLEGAL_ARG_COUNT = "Illegal argument count \"{1}\" for function \"{0}\": must be in the rage of \"{2}\"-\"{3}\"!";

    /**
     * exception message for illegal unary function argument.
     * 
     * @see net.sf.mmm.term.api.IllegalArgumentTypeException
     */
    public static final String ERR_ILLEGAL_ARG_1 = "The function \"{0}\" is not applicable for the argument \"{1}\"!";

    /**
     * exception message for illegal binary function arguments.
     * 
     * @see net.sf.mmm.term.api.IllegalArgumentTypeException
     */
    public static final String ERR_ILLEGAL_ARG_2 = "The function \"{0}\" is not applicable for the arguments \"{1}\" and \"{2}\"!";

    /**
     * exception message for illegal cast (conversion of a value to another
     * type).
     * 
     * @see net.sf.mmm.term.impl.function.FctCast
     * @see net.sf.mmm.term.api.IllegalCastException
     */
    public static final String ERR_ILLEGAL_CAST = "Can not cast value \"{0}\" to \"{1}\"!";

    /**
     * exception message if string was multiplied with negative integer.
     * 
     * @see net.sf.mmm.term.impl.function.FctMultiply
     */
    public static final String ERR_FCT_MUL_STR_NEG = "Can not multiply string with negative number!";

    /**
     * exception message if string multiplication results in string longer than
     * maximum allowed limit.
     * 
     * @see net.sf.mmm.term.impl.function.FctMultiply
     */
    public static final String ERR_FCT_MUL_STR_MAX = "String multiply must not exceed result string longer than \"{0}\"!";

    /**
     * exception message on division by zero.
     * 
     * @see net.sf.mmm.term.impl.function.FctDivide
     */
    public static final String ERR_FCT_DIV_ZERO = "Division by zero!";

}