/* $Id$ */
package net.sf.mmm.term.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.term.NlsResourceBundle;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.FunctionException;
import net.sf.mmm.term.api.FunctionIF;
import net.sf.mmm.term.api.IllegalArgumentCountException;
import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.api.OperatorPriority;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.util.BasicUtil;
import net.sf.mmm.util.reflect.Arguments;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.Signature;
import net.sf.mmm.value.api.ValueException;

/**
 * This is an implementation of the {@link net.sf.mmm.term.api.FunctionIF}
 * interface that allows to combine multiple partial implementations to one
 * logical function. Each partial implementation implements one or more public,
 * static method(s) with the {@link #getName() name} of this logical function.<br>
 * This mechanism allows some sort of multi-inheritance (at runtime). If you add
 * your own custom value type, you can also extend the functional logic for this
 * type without modifying existing function implementations.<br>
 * E.g. the logical function add (+) may be implemented as generic function.
 * There may be a partial implementation called <code>FctAddNumeric</code>
 * with a public static method <code>Double add(Double,Double)</code> and
 * another partial implementation called <code>FctAddString</code> with a
 * public static method <code>String add(String,Object)</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericFunction extends BasicFunction {

    /** the name of the field containing the {@link FunctionIF#getName() name} */
    private static final String FIELD_NAME_NAME = "NAME";

    /**
     * the name of the field containing the
     * {@link FunctionIF#getOperatorSymbol() symbol}
     */
    private static final String FIELD_NAME_SYMBOL = "SYMBOL";

    /**
     * the name of the field containing the
     * {@link FunctionIF#getOperatorPriority() "operator priority"}
     */
    private static final String FIELD_NAME_PRIORITY = "PRIORITY";

    /** the signature for a non-arg method */
    private static final Object[] NO_ARGUMENTS = new Object[0];

    /** the name of the function */
    private final String name;

    /** the operator symbol */
    private final String symbol;

    /** the priority of the function as operator */
    private final OperatorPriority priority;

    /** the registered function implementations */
    private final Map<Signature, Method> signature2method;

    /** the minimum allowed argument count */
    private int minArgCount;

    /** the maximum allowed argument count */
    private int maxArgCount;

    /**
     * The constructor.
     * 
     * @param functionName
     *        is the name of the function.
     */
    public GenericFunction(String functionName) {

        this(functionName, null);
    }

    /**
     * The constructor.
     * 
     * @param functionName
     *        is the name of the function.
     * @param operatorSymbol
     *        is the symbol of the function as operator.
     */
    public GenericFunction(String functionName, String operatorSymbol) {

        this(functionName, operatorSymbol, OperatorPriority.MEDIUM);
    }

    /**
     * The constructor.
     * 
     * @param functionName
     *        is the name of the function.
     * @param operatorSymbol
     *        is the symbol of the function as operator.
     * @param operatorPriority
     *        is the priority of the function as operator.
     */
    public GenericFunction(String functionName, String operatorSymbol,
            OperatorPriority operatorPriority) {

        super();
        this.signature2method = new Hashtable<Signature, Method>();
        // initial settings are inconsistent!
        this.minArgCount = 2;
        this.maxArgCount = 0;
        this.name = functionName;
        this.symbol = operatorSymbol;
        this.priority = operatorPriority;
    }

    /**
     * The constructor.
     * 
     * @param partialFunction
     *        is the initial implementation to
     *        {@link #addImplementation(Class) add}. It must declare the fields
     *        {@link #FIELD_NAME_NAME}, {@link #FIELD_NAME_SYMBOL} and
     *        {@link #FIELD_NAME_PRIORITY}.
     * @throws FunctionException
     *         if the given <code>partialFunction</code> is illegal.
     */
    public GenericFunction(Class<?> partialFunction) throws FunctionException {

        super();
        this.signature2method = new Hashtable<Signature, Method>();
        // initial settings are inconsistent!
        this.minArgCount = 2;
        this.maxArgCount = 0;
        this.name = getStaticField(partialFunction, FIELD_NAME_NAME, String.class);
        this.symbol = getStaticField(partialFunction, FIELD_NAME_SYMBOL, String.class);
        OperatorPriority p = null;
        if (this.symbol != null) {
            p = getStaticField(partialFunction, FIELD_NAME_PRIORITY, OperatorPriority.class);
        }
        this.priority = p;
        addImplementation(partialFunction);
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getName()
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getOperatorSymbol()
     * {@inheritDoc}
     */
    public String getOperatorSymbol() {

        return this.symbol;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getOperatorPriority()
     * {@inheritDoc}
     */
    public OperatorPriority getOperatorPriority() {

        return this.priority;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMinimumArgumentCount()
     * {@inheritDoc}
     */
    public int getMinimumArgumentCount() {

        return this.minArgCount;
    }

    /**
     * @see net.sf.mmm.term.api.FunctionIF#getMaximumArgumentCount()
     * {@inheritDoc}
     */
    public int getMaximumArgumentCount() {

        return this.maxArgCount;
    }

    /**
     * This method sets the minimum argument count of this function. It is
     * usally not neccessary to set the argument count range because this is
     * done automatically by the
     * {@link GenericFunction#addImplementation(Class)} method. Anyways after
     * you have added all function implementations you can override the minimum
     * argument count using this method.
     * 
     * @see net.sf.mmm.term.api.FunctionIF#getMinimumArgumentCount()
     * 
     * @param minimumArgumentCount
     *        is the new minimum argument count value.
     */
    public void setMinimumArgumentCount(int minimumArgumentCount) {

        this.minArgCount = minimumArgumentCount;
    }

    /**
     * This method sets the maximum argument count of this function. It is
     * usally not neccessary to set the argument count range because this is
     * done automatically by the
     * {@link GenericFunction#addImplementation(Class)} method. Anyways after
     * you have added all function implementations you can override the maximum
     * argument count using this method.
     * 
     * @see net.sf.mmm.term.api.FunctionIF#getMinimumArgumentCount()
     * 
     * @param maximumArgumentCount
     *        is the new maximum argument count value.
     */
    public void setMaximumArgumentCount(int maximumArgumentCount) {

        this.maxArgCount = maximumArgumentCount;
    }

    /**
     * This method checks if the given method is a valid partial implementation
     * for this generic function. This means the following points:
     * <ul>
     * <li>the {@link Method#getName() "method name"} is
     * {@link String#equals(java.lang.Object) equal} to the
     * {@link #getName() name} of this function.</li>
     * <li>the method is {@link Modifier#isPublic(int) public}</li>
     * <li>the method is {@link Modifier#isStatic(int) static}</li>
     * <li>the methods {@link Method#getReturnType() return-type} is NOT
     * {@link Void}.</li>
     * <li>the method {@link Method#getExceptionTypes() throws} an
     * {@link Exception} that is
     * {@link Class#isAssignableFrom(java.lang.Class) assignable} to
     * {@link CalculationException} or {@link RuntimeException}.</li>
     * </ul>
     * 
     * @param method
     *        is the method to check.
     * @param partialFunction
     *        is the class containing the method.
     * @param signature
     *        is the signature of the given method.
     * @throws FunctionException
     *         if the given method applies to the points above except the last
     *         one.
     */
    protected void validateMethod(Method method, Class partialFunction, Signature signature)
            throws FunctionException {

        // check if the method is public and static...
        if (Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())) {
            // check if the method returns something...
            if (method.getReturnType() != Void.class) {
                // verify exception types...
                Class[] exceptionTypes = method.getExceptionTypes();
                for (int j = 0; j < exceptionTypes.length; j++) {
                    Class excType = exceptionTypes[j];
                    if (!CalculationException.class.isAssignableFrom(excType)) {
                        if (!RuntimeException.class.isAssignableFrom(excType)) {
                            throw new FunctionException(
                                    NlsResourceBundle.ERR_FCT_ILLEGAL_EXCEPTION_TYPE,
                                    partialFunction, this, signature, excType);
                        }
                    }
                }
                return;
            }
        }
        throw new FunctionException(NlsResourceBundle.ERR_FCT_ILLEGAL_METHOD, partialFunction,
                this, signature);
    }

    /**
     * This method gets the {@link Field#get(java.lang.Object) value} of a
     * {@link Modifier#isStatic(int) static} {@link Field field}.
     * 
     * @param <T>
     *        the templated type the requested field is assigned to.
     * @param partialFunction
     *        is the class containing the requested field.
     * @param fieldName
     *        is the {@link Field#getName() name} of the requested field.
     * @param fieldType
     *        is the type the requested field is assigned to. Therefore the
     *        field declaration (!) must be assignable to this type.
     * @return the value of the field with the given type.
     * @throws FunctionException
     *         if the requested field does NOT exist, is NOT accessable or has
     *         the wrong type.
     */
    protected <T> T getStaticField(Class<?> partialFunction, String fieldName, Class<T> fieldType)
            throws FunctionException {

        try {
            return ReflectionUtil.getStaticField(partialFunction, fieldName, fieldType, true);
        } catch (Throwable e) {
            throw new FunctionException(e, NlsResourceBundle.ERR_FCT_FIELD, partialFunction, this,
                    fieldName, fieldType);
        }
    }

    /**
     * This method validates the given partial implementation.
     * 
     * @param partialFunction
     *        is a partial implemenetation of this function. See
     *        {@link #addImplementation(Class)}.
     * @throws FunctionException
     *         if the given implementation is illegal.
     */
    protected void validateClass(Class<?> partialFunction) throws FunctionException {

        // check modifiers
        int classModifiers = partialFunction.getModifiers();
        if (Modifier.isAbstract(classModifiers) || Modifier.isInterface(classModifiers)
                || !Modifier.isPublic(classModifiers)) {
            throw new FunctionException(NlsResourceBundle.ERR_FCT_ILLEGAL_CLASS, partialFunction,
                    this);
        }
        // check if operator is valid...
        String operator = getStaticField(partialFunction, FIELD_NAME_SYMBOL, String.class);
        if (!BasicUtil.equals(this.symbol, operator)) {
            throw new FunctionException(NlsResourceBundle.ERR_FCT_WRONG_SYMBOL, partialFunction,
                    this, operator);
        }
    }

    /**
     * This method adds an implementation of the according function. Multiple
     * function implementations can be added sequentially and are automatically
     * merged to this generic function. <br>
     * See typdescription of this class for more details.
     * 
     * @param partialFunction
     *        is a partial implemenetation of this function. It is a class that
     *        has one or more static methods with the name of this function.
     *        Please follow the naming convention and use the "Fct" followed by
     *        the capitalized name of this function as prefix for the classname
     *        of your partial implementation.
     * @throws FunctionException
     *         if the implementation is illegal.
     */
    public void addImplementation(Class partialFunction) throws FunctionException {

        validateClass(partialFunction);
        int maxSignatureCount = -1;
        Method[] methodList = partialFunction.getMethods();
        for (int i = 0; i < methodList.length; i++) {
            Method method = methodList[i];
            // check the name of the method
            if (method.getName().equals(this.name)) {
                Signature signature = new Signature(method);
                validateMethod(method, partialFunction, signature);
                // check for duplicate signature
                if (this.signature2method.containsKey(signature)) {
                    throw new FunctionException(NlsResourceBundle.ERR_FCT_DUPLICATE_SIGNATURE,
                            partialFunction, this, signature);
                }
                this.signature2method.put(signature, method);
                int signatureLength = signature.getTypeCount();
                if (signatureLength < this.minArgCount) {
                    this.minArgCount = signatureLength;
                }
                if (signatureLength > maxSignatureCount) {
                    maxSignatureCount = signatureLength;
                }
            }
        }
        if (maxSignatureCount == -1) {
            // no method found!
            throw new FunctionException(NlsResourceBundle.ERR_FCT_EMPTY, partialFunction, this);
        }
        if (maxSignatureCount > this.maxArgCount) {
            if (maxSignatureCount > 1) {
                this.maxArgCount = Integer.MAX_VALUE;
            } else {
                this.maxArgCount = maxSignatureCount;
            }
        }
    }

    /**
     * This method finds the method implementing the function for the given
     * signature.
     * 
     * @param signature
     *        is the signature the method is called with.
     * @return the method for the given signature.
     * @throws CalculationException
     */
    protected Method getMethod(Signature signature) throws CalculationException {

        Method result = this.signature2method.get(signature);
        if (result == null) {
            Signature applicableSignature = null;
            Iterator<Signature> signatureList = this.signature2method.keySet().iterator();
            while (signatureList.hasNext()) {
                Signature currentSignature = signatureList.next();
                if (currentSignature.isApplicable(signature)) {
                    // there might be multiple matches, we need to find the most
                    // specific match (e.g. [Integer] preferred to [Number]
                    // preferred to [Object] if argument type is Integer).
                    if ((applicableSignature == null)
                            || (applicableSignature.isApplicable(currentSignature))) {
                        applicableSignature = currentSignature;
                    }
                }
            }
            if (applicableSignature != null) {
                result = this.signature2method.get(applicableSignature);
                // lets cache this for the next time...
                this.signature2method.put(signature, result);
            }
        }
        if (result == null) {
            int signatureLength = signature.getTypeCount();
            if ((signatureLength == 1) || (signatureLength == 2)) {
                throw new IllegalArgumentTypeException(this, signature);
            } else {
                // this is an internal error!
                throw new IllegalArgumentCountException(this, signatureLength);
            }
        }
        return result;
    }

    /**
     * This method performs the actual calculation by calling the given method
     * with the given arguments.
     * 
     * @see FunctionIF#calculate(net.sf.mmm.environment.api.EnvironmentIF,
     *      net.sf.mmm.term.api.TermIF[])
     * 
     * @param method
     *        is the method implementing the function call for the given
     *        arguments.
     * @param arguments
     *        are the arguments given to this function.
     * @return the result of
     * @throws CalculationException
     *         if the calculation fails.
     */
    protected Object doCalculate(Method method, Object[] arguments) throws CalculationException {

        try {
            return method.invoke(null, arguments);
        } catch (IllegalArgumentException e) {
            throw new CalculationException("Internal Errror!", e);
        } catch (IllegalAccessException e) {
            throw new CalculationException("Internal Errror!", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof CalculationException) {
                throw (CalculationException) cause;
            } else {
                throw new IllegalArgumentTypeException(this, new Arguments(arguments), e);
            }
        }
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate()
     * {@inheritDoc}
     */
    @Override
    public Object calculate() throws ValueException {

        Method method = getMethod(Signature.VOID);
        return doCalculate(method, NO_ARGUMENTS);
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(Object)
     * {@inheritDoc}
     */
    @Override
    public Object calculate(Object argument) throws ValueException {

        Method method = getMethod(new Signature(argument.getClass()));
        return doCalculate(method, new Object[] {argument});
    }

    /**
     * @see net.sf.mmm.term.base.BasicFunction#calculate(Object, Object)
     * {@inheritDoc}
     */
    @Override
    public Object calculate(Object argument1, Object argument2) throws ValueException {

        Method method = getMethod(new Signature(argument1.getClass(), argument2.getClass()));
        return doCalculate(method, new Object[] {argument1, argument2});
    }

}
