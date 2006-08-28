/* $Id: ComponentInstanceContainerIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.api;

/**
 * This is the interface of a container for a single
 * {@link #getInstance() instance} of the component for a specific
 * {@link ComponentProviderIF provider}.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptorIF#getSpecification() specification}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentInstanceContainerIF<S> {

    /**
     * This method gets the component instance.
     * 
     * @return the component instance.
     */
    S getInstance();

    /**
     * This method gets the
     * {@link ComponentManagerIF#requestComponent(Class, String) instance-ID} of
     * this component {@link #getInstance() instance}. The default is
     * <code>null</code>.
     * 
     * @return the instance-ID.
     */
    String getInstanceId();

    /**
     * This method gets the string representation of this object. It should
     * contain the {@link Object#toString() string representations} of the
     * {@link #getInstance() instance} itself, the
     * {@link #getInstanceId() instance-ID} and the
     * {@link ComponentDescriptorIF#getSpecification() specification} of the
     * component.
     * 
     * @see java.lang.Object#toString()
     */
    String toString();

}
