/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a {@link ValueConverter} that is composed out of
 * individual {@link ValueConverter}s. The idea of this interface is the idiom
 * <em>separations of concerns</em>. Therefore you can write an individual
 * {@link ValueConverter} for each type of value you want to deal with.<br>
 * The idea is to combine various individual {@link ValueConverter}s to one
 * generic converter following the composition-pattern. Therefore this generic
 * converter needs to choose the individual {@link ValueConverter} that is
 * appropriate for a specific
 * {@link #convert(Object, Object, net.sf.mmm.util.reflect.api.GenericType)
 * conversion}.<br>
 * <br>
 * The meaning of <em>appropriate</em> here can depend on the implementation.
 * However it needs to guarantee that the {@link #getSourceType() source-type}
 * of the chosen {@link ValueConverter} {@link Class#isAssignableFrom(Class) is
 * assignable from} the actual {@link Object#getClass() type} of the object to
 * convert. Additionally the {@link ValueConverter#getTargetType() target-type}
 * of the chosen {@link ValueConverter} needs to be
 * {@link Class#isAssignableFrom(Class) assignable from} the actual type to
 * convert to or vice versa. The following table illustrates this with some
 * examples:<br>
 * <table border="1">
 * <tr>
 * <th>sourceType</th>
 * <th>targetType</th>
 * <th>{@link ValueConverter}</th>
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
 * </table>
 * <br>
 * However the chosen converter may
 * {@link #convert(Object, Object, net.sf.mmm.util.reflect.api.GenericType)
 * return} <code>null</code> to indicate that conversion is NOT possible. This
 * {@link ComposedValueConverter} should therefore try all applicable converters
 * starting from most to least specific until conversion succeeds. If all
 * applicable converters fail it will also fail and return <code>null</code>.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@ComponentSpecification
public interface ComposedValueConverter extends ValueConverter<Object, Object>,
    GenericValueConverter<Object> {

}
