/* $Id: ComponentDescriptorIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.api;

/**
 * This is the interface for the descriptor of a component. It is referenced in
 * javadocs to identify the abstract conecpt of a component.
 * <p>
 * Definition: <em>A component is a self-contained, reuseable, replaceable,
 * composeable and dynamically bound piece of software following the "separation
 * of concerns" idiom.
 * </em>
 * </p>
 * Examples for components are search-engine, persistence-layer,
 * billing-service, connection-pool, uri-formatter, etc. The following list
 * gives examples for what is NOT a component: atomic types (such as String,
 * Integer, ...), entity types, simple data sets (JAVA Beans), etc. <br>
 * <br>
 * In the context of this framework a component is defined by a
 * {@link ComponentProviderIF provider} in combination with a
 * {@link ComponentDescriptorIF descriptor}. There are no technical
 * restrictions so anything can be a component. It is a matter of your
 * architectural design what is a component.<br>
 * A component has a {@link #getSpecification() specification}. This is all the
 * user of the component knows. He does NOT need to care how and when the
 * component is created, how it is configured and set-up, and when it is
 * shut-down. All of this is managed for you by the
 * {@link IocContainerIF IoC-Container} and the implementation the
 * {@link ComponentProviderIF provider}.<br>
 * Typically you do NOT implement {@link ComponentDescriptorIF descriptors} and
 * {@link ComponentProviderIF providers} (or deal at all with the framework
 * API). There are generic implementations of this interface that allow you to
 * configure your component using
 * {@link java.lang.annotation.Annotation annotations} or XML descriptors.<br>
 * The implementation of this interface must guarantee that all attributes ({@link #getSpecification() specification},
 * {@link #getName() name}, etc.) are final (do NOT change after the descriptor
 * is published.
 * 
 * @see ComponentProviderIF
 * 
 * @param <S>
 *        is the {@link #getSpecification() specification} of the provided
 *        component. The generic type in this interface is mainly used to make
 *        the specification more clear.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentDescriptorIF<S> {

    /**
     * This method gets the specification of the component. It should be a well
     * documented JAVA interface defining the API of the component. The
     * specification is the view on the component for the user.
     * 
     * @return the specification of the component.
     */
    Class<S> getSpecification();

    /**
     * This method gets the name of the component. This can be any string
     * following the conventions for
     * {@link Class#getCanonicalName() class-names}. It may be the
     * {@link Class#getCanonicalName() canonical name} of the
     * {@link #getSpecification() specification} or the implementation. The name
     * should be quite short but also identify the component and give an idea of
     * its meaning. The combination of {@link #getDomain() domain} and
     * {@link #getName() name} should be technically unique.<br>
     * The {@link IocContainerIF IoC-container} will only display the name but
     * it might be interpreted by a {@link ComponentProviderIF provider} for one
     * of your dependencies.
     * 
     * @return the name of the component.
     */
    String getName();

    /**
     * This method gets the category of the component. This can be any string
     * following the conventions for
     * {@link Class#getName() qualified class-names}. The category may be used
     * to create the components logger.<br>
     * The category is NOT evaluated by the {@link IocContainerIF IoC-container}
     * but might be used by a {@link ComponentProviderIF provider} for one of
     * your dependencies.
     * 
     * @return the category of the component.
     */
    String getCategory();

    /**
     * This method gets the domain of the component. This can be any string
     * following the conventions for {@link Class#getName() class-names} or
     * {@link Package#getName() package-names}. It may be the common prefix of
     * your {@link Package packages} such as <code>net.sf.mmm</code> or
     * <code>org.apache.maven</code>. Instead of using technical domains you
     * can also use logical domains such as <code>Billing</code>, </code>Accounting</code>,
     * <code>Delivery</code>, etc.<br>
     * The domain is NOT evaluated by the {@link IocContainerIF IoC-container}
     * but might be used by a {@link ComponentProviderIF provider} for one of
     * your dependencies.
     * 
     * @return the domain of the component.
     */
    String getDomain();

}
