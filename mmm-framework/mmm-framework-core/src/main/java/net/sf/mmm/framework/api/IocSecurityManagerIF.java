/* $Id: IocSecurityManagerIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.api;

/**
 * This is the interface of a manager that
 * {@link #checkPermission(Class, Class, String) checks} if a
 * {@link ComponentDescriptorIF#getSpecification() source component} is allowed to
 * access a {@link ComponentDescriptorIF#getSpecification() target component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface IocSecurityManagerIF {

    /**
     * This method determines if a component identified by the
     * {@link ComponentDescriptorIF#getSpecification() specification}
     * <code>source</code> is allowed to
     * {@link ComponentManagerIF#requestComponent(Class) access} a component
     * identified by <code>target</code>.
     * 
     * @param source
     *        is the
     *        {@link ComponentDescriptorIF#getSpecification() specification} of
     *        the
     *        {@link ComponentManagerIF#requestComponent(Class, String) requesting}
     *        component.
     * @param target
     *        is the
     *        {@link ComponentDescriptorIF#getSpecification() specification} of
     *        the
     *        {@link ComponentManagerIF#requestComponent(Class, String) requested}
     *        component.
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) requested instance-ID}.
     * @return <code>true</code> if <code>source</code> is allowed to access
     *         <code>target</code> with the given <code>instanceId</code>.
     */
    boolean checkPermission(Class source, Class target, String instanceId);

}
