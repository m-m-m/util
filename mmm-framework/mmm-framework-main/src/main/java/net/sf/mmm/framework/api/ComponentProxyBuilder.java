/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This is the interface used to build a proxy to hide the
 * {@link ExtendedComponentDescriptor#getImplementation() implementation} of a
 * component behind the
 * {@link ComponentDescriptor#getSpecification() specification}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentProxyBuilder {

    /**
     * This method creates a proxy delegating on the given
     * <code>componentInstance</code>. The
     * {@link ExtendedComponentDescriptor descriptor} holds the
     * {@link ComponentDescriptor#getSpecification() specification class}
     * corresponding to the templated type <code>S</code>.<br>
     * The returned proxy should NOT implement anything more specific that
     * <code>S</code>, especially it should NOT be possible to cast it to
     * <code>I</code> (except for I=S) or retrieve the original
     * <code>componentInstance</code>. Anyways a dummy implementation may
     * simply return the untouched <code>componentInstance</code> itself.
     * 
     * @param <S>
     *        is the component
     *        {@link ComponentDescriptor#getSpecification() specification}
     * @param <I>
     *        is the component
     *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
     * @param descriptor
     *        is the component descriptor explaining the component.
     * @param componentInstance
     *        is the instance to build a proxy on. It is a an instance of the
     *        {@link ExtendedComponentDescriptor#getImplementation() component implementation}
     *        specified by the given <code>descriptor</code>.
     * @return a proxy delegating on the given <code>componentInstance</code>.
     */
    public <S, I extends S> S createProxy(ExtendedComponentDescriptor<S, I> descriptor,
            I componentInstance);

}
