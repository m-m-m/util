/* $Id$ */
package net.sf.mmm.framework.api;

/**
 * This is the interface used to build a proxy to hide the
 * {@link ExtendedComponentDescriptorIF#getImplementation() implementation} of a
 * component behind the
 * {@link ComponentDescriptorIF#getSpecification() specification}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentProxyBuilderIF {

    /**
     * This method creates a proxy delegating on the given
     * <code>componentInstance</code>. The
     * {@link ExtendedComponentDescriptorIF descriptor} holds the
     * {@link ComponentDescriptorIF#getSpecification() specification class}
     * corresponding to the templated type <code>S</code>.<br>
     * The returned proxy should NOT implement anything more specific that
     * <code>S</code>, especially it should NOT be possible to cast it to
     * <code>I</code> (except for I=S) or retrieve the original
     * <code>componentInstance</code>. Anyways a dummy implementation may
     * simply return the untouched <code>componentInstance</code> itself.
     * 
     * @param <S>
     *        is the component
     *        {@link ComponentDescriptorIF#getSpecification() specification}
     * @param <I>
     *        is the component
     *        {@link ExtendedComponentDescriptorIF#getImplementation() implementation}
     * @param descriptor
     *        is the component descriptor explaining the component.
     * @param componentInstance
     *        is the instance to build a proxy on. It is a an instance of the
     *        {@link ExtendedComponentDescriptorIF#getImplementation() component implementation}
     *        specified by the given <code>descriptor</code>.
     * @return a proxy delegating on the given <code>componentInstance</code>.
     */
    public <S, I extends S> S createProxy(ExtendedComponentDescriptorIF<S, I> descriptor,
            I componentInstance);

}
