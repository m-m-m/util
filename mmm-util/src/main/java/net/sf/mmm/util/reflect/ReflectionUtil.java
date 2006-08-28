/* $Id$ */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class is a collection of utility functions for dealing with
 * {@link java.lang.reflect reflection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionUtil {

    /** an empty class array */
    public static final Class[] NO_PARAMETERS = new Class[0];

    /** an empty object array */
    public static final Object[] NO_ARGUMENTS = new Object[0];

    /**
     * Forbidden constructor.
     */
    private ReflectionUtil() {

        super();
    }

    /**
     * This method gets the {@link Object#getClass() classes} of the given
     * objects.
     * 
     * @param objects
     *        is an array containing the objects for that the classes are
     *        requested.
     * @return an array of the same length as the given array. At each position
     *         the returned array contains the {@link Object#getClass() class}
     *         of the object from the given array at the same position or
     *         <code>null</code>, if that object is <code>null</code>.
     */
    public static Class[] getClasses(Object[] objects) {

        Class[] result = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                result[i] = null;
            } else {
                result[i] = objects[i].getClass();
            }
        }
        return result;

    }

    /**
     * This method gets the {@link Field#get(java.lang.Object) value} of a
     * {@link Modifier#isStatic(int) static} {@link Field field}.
     * 
     * @param <T>
     *        the templated type the requested field is assigned to.
     * @param type
     *        is the class or interface containing the requested field.
     * @param fieldName
     *        is the {@link Field#getName() name} of the requested field.
     * @param fieldType
     *        is the type the requested field is assigned to. Therefore the
     *        field declaration (!) must be assignable to this type.
     * @param mustBeFinal -
     *        if <code>true</code>, an {@link IllegalArgumentException} is
     *        thrown if the specified static field exists but is NOT
     *        {@link Modifier#isFinal(int) final}, <code>false</code>
     *        otherwise.
     * @return the value of the field with the given type.
     * @throws NoSuchFieldException
     *         if the given <code>type</code> has no field with the given
     *         <code>fieldName</code>.
     * @throws IllegalAccessException
     *         if you do not have permission to read the field (e.g. field is
     *         private).
     */
    @SuppressWarnings("unchecked")
    public static <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType,
            boolean mustBeFinal) throws NoSuchFieldException, IllegalAccessException {

        Field field = type.getField(fieldName);
        int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)) {
            throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
                    + "') is not static!");
        }
        if (mustBeFinal && !Modifier.isFinal(modifiers)) {
            throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
                    + "') is not final!");
        }
        if (!fieldType.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("Field '" + fieldName + "' (in type '" + type
                    + "') has type '" + field.getType() + "' but requested type was '" + fieldType
                    + "'!");
        }
        return (T) field.get(null);
    }

    /**
     * This method gets the parent method of the given <code>method</code>.
     * The parent method is the method overriden (is the sense of
     * {@link Override}) by the given <code>method</code> or directly
     * inherited from an {@link Class#getInterfaces() interface}.
     * 
     * @param method
     *        is the method.
     * @return the parent method or <code>null</code> if no such method
     *         exists.
     * @throws SecurityException
     *         if access has been denied by the {@link SecurityManager}.
     */
    public static Method getParentMethod(Method method) throws SecurityException {

        return getParentMethod(method.getDeclaringClass(), method.getName(), method
                .getParameterTypes());
    }

    /**
     * This method gets the method
     * {@link Class#getMethod(String, Class[]) identified} by
     * <code>methodName</code> and <code>parameterTypes</code> that is NOT
     * {@link Method#getDeclaringClass() declared} but inherited by the given
     * <code>declaringClass</code>.
     * 
     * @see #getParentMethod(Class, String, Class[])
     * 
     * @param inheritingClass
     *        is the class inherting the requested method.
     * @param methodName
     *        is the {@link Method#getName() name} of the requested method.
     * @param parameterTypes
     *        is the {@link Method#getParameterTypes() signature} of the
     *        requested method.
     * @return the inherited method or <code>null</code> if no such method
     *         exists.
     * @throws SecurityException
     *         if access has been denied by the {@link SecurityManager}.
     */
    public static Method getParentMethod(Class inheritingClass, String methodName,
            Class[] parameterTypes) throws SecurityException {

        Class parentClass = inheritingClass.getSuperclass();
        if (parentClass != null) {
            try {
                return parentClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // method does NOT exist...
            }
        }
        Class[] interfaces = inheritingClass.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            try {
                return interfaces[i].getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // method does NOT exist...
            }
        }
        // search indirectly from interfaces...
        for (int i = 0; i < interfaces.length; i++) {
            Method result = getParentMethod(interfaces[i], methodName, parameterTypes);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
