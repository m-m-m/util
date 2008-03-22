/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

/**
 * This is the interface for a generic {@link PojoPathConverter}. The idea of
 * the {@link PojoPathConverter} interface is the idiom
 * <em>separations of concerns</em>. Therefore you can write an individual
 * {@link PojoPathConverter} for each type of
 * {@link net.sf.mmm.util.reflect.pojo.api.Pojo} you want to deal with.<br>
 * The idea of this interface is to combine various individual
 * {@link PojoPathConverter}s to one generic converter following the
 * composition-pattern. Therefore this generic converter needs to choose the
 * individual {@link PojoPathConverter} that is appropriate for a specific
 * {@link #convert(Object, Class, java.lang.reflect.Type, PojoPath) conversion}.<br>
 * <br>
 * The meaning of <em>appropriate</em> here can depend on the implementation.
 * However it needs to guarantee that the
 * {@link PojoPathConverter#getSourceType() source-type} of the chosen
 * {@link PojoPathConverter}
 * {@link Class#isAssignableFrom(Class) is assignable from} the actual
 * {@link Object#getClass() type} of the object to convert. Additionally the
 * {@link PojoPathConverter#getTargetType() target-type} of the chosen
 * {@link PojoPathConverter} needs to be
 * {@link Class#isAssignableFrom(Class) assignable from} the actual type to
 * convert to or vice versa. The following table illustrates this with some
 * examples:<br>
 * <table border="1">
 * <tr>
 * <th>sourceType</th>
 * <th>targetType</th>
 * <th>{@link PojoPathConverter}</th>
 * <th>applicable</th>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Date</td>
 * <td>&lt;String,Date&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Date</td>
 * <td>&lt;Object,Date&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Date</td>
 * <td>&lt;String,Calendar&gt;</td>
 * <td>no</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Number</td>
 * <td>&lt;String,Integer&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Integer</td>
 * <td>&lt;String,Number&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Integer</td>
 * <td>&lt;String,Integer&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>Double</td>
 * <td>Integer</td>
 * <td>&lt;String,Integer&gt;</td>
 * <td>no</td>
 * </tr>
 * <tr>
 * <td>String</td>
 * <td>Double</td>
 * <td>&lt;String,Integer&gt;</td>
 * <td>no</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>*</td>
 * <td>&lt;Object,Object&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>X</td>
 * <td>*</td>
 * <td>&lt;? super X,Object&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>Y</td>
 * <td>&lt;Object,? super Y&gt;</td>
 * <td>yes</td>
 * </tr>
 * <tr>
 * <td>*</td>
 * <td>Y</td>
 * <td>&lt;Object,? extends Y&gt;</td>
 * <td>yes</td>
 * </tr>
 * </table> <br>
 * However the chosen converter may
 * {@link #convert(Object, Class, java.lang.reflect.Type, PojoPath) return}
 * <code>null</code> to indicate that conversion is NOT possible. This
 * {@link PojoPathGenericConverter} should therefore try all applicable
 * converters starting from most to least specific until conversion succeeds. If
 * all applicable converters fail it will also fail and return <code>null</code>.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathGenericConverter extends PojoPathConverter<Object, Object> {

}
