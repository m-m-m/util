/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import javax.inject.Inject;

import javafx.beans.property.Property;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a generic bean based on {@link net.sf.mmm.util.property.api.WritableProperty generic
 * properties}. Unlike plain old Java Beans this offers a lot of advanced features:
 * <ul>
 * <li>Simplicity - just create your interface, no need to write boiler-plate code for implementation.</li>
 * <li>Generic - fast, easy and reliable introspection via {@link BeanAccess#getProperties()}. No greedy introspection
 * via reflection.</li>
 * <li>Dynamic - supports combination of Java's strong typing with {@link BeanAccess#isDynamic() dynamic} beans. E.g. if
 * read data from Database, XML, or JSON you can still map "undefined" properties in your {@link Bean}. This way a
 * client can receive an object from a newer version of a database or (REST-)service with added properties that will be
 * kept in the object and send back when the {@link Bean} is written back.</li>
 * <li>Automatic - no need to implement methods such as {@link #equals(Object)}, {@link #hashCode()}, or
 * {@link #toString()}. Also general getters and setters are automatically implemented.</li>
 * <li>Flexibility - add any custom logic to your {@link Bean} interface via default methods including custom
 * implementations of {@link CustomEquals equals} and {@link CustomHashCode hashCode} if needed.</li>
 * <li>ReadOnly-Support - {@link BeanFactory#getReadOnlyBean(Bean) create} a {@link BeanAccess#isReadOnly() read-only}
 * view of your object to pass by reference without side-effects.</li>
 * <li>Powerful properties - {@link net.sf.mmm.util.property.api.WritableProperty} is based on JavaFx {@link Property}
 * supporting listeners and bindings but also additional features such as
 * {@link net.sf.mmm.util.property.api.WritableProperty#getType() generic type information}.</li>
 * <li>Validation - build-in {@link BeanAccess#validate() validation support}. This can be configured via
 * Bean-Validation standard (JSR303) or even more efficient and reusable via custom properties or in default methods for
 * the properties of your {@link Bean} interface.</li>
 * </ul>
 *
 * In order to create your own bean simple create a new interface extending this {@link Bean} interface. Have a look at
 * the following example:
 *
 * <pre>
 * public interface AddressBean extends {@link Bean} {
 *   {@link net.sf.mmm.util.property.api.WritableStringProperty} Street();
 *   {@link net.sf.mmm.util.property.api.WritableStringProperty} HouseNumber();
 *   {@link net.sf.mmm.util.property.api.WritableStringProperty} PostalCode();
 *   {@link net.sf.mmm.util.property.api.WritableStringProperty} City();
 *   {@link net.sf.mmm.util.property.api.WritableStringProperty} Country();
 *   // if you want you may also define regular getters and setters as well
 *   String getStreet();
 *   void setStreet(String street);
 * }
 * </pre>
 *
 * In order to create a new instance of {@code AddressBean} simply do this:
 *
 * <pre>
 * public void MyClass {
 *
 *   &#64;{@link Inject}
 *   private {@link BeanFactory} beanFactory;
 *
 *   public void doSomething() {
 *     AddressBean address = this.beanFactory.{@link BeanFactory#create(Class) create}(AddressBean.class);
 *     address.Street().setValue("Baker Street");
 *     address.HouseNumber().setValue("1a");
 *     address.PostalCode().setValue("6wn7ta");
 *     address.City().setValue("Rafferty");
 *     address.Country().setValue("Gerryland");
 *     for (Property{@literal <?>} property: address.{@link #access()}.{@link BeanAccess#getProperties() getProperties()}) {
 *       System.out.println("Property '" + property.{@link net.sf.mmm.util.property.api.WritableProperty#getName() getName()} + "' of type ' + property.{@link WritableProperty#getType() getType()} + ' has value '" + property.{@link WritableProperty#getValue() getValue()} + "'.");
 *     }
 *   }
 * }
 * </pre>
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface Bean {

  /**
   * @return the {@link BeanAccess} with all generic features and methods on a {@link Bean}. These are encapsulated in
   *         {@link BeanAccess} to avoid polluting this {@link Bean} interface itself with additional methods and for
   *         better compatibility on enhancements.
   */
  BeanAccess access();

}
