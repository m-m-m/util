/* $Id$ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.base.descriptor.SimpleComponentDescriptor;

/**
 * This is the abstract base implementation of the {@link ComponentProviderIF}
 * interface.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentProvider<S> implements ComponentProviderIF<S> {

    /** @see #getDescriptor() */
    private final ComponentDescriptorIF<S> descriptor;

    /**
     * The constructor.
     * 
     * @param specification
     *        is the
     *        {@link ComponentDescriptorIF#getSpecification() specification} of
     *        the component.
     */
    public AbstractComponentProvider(Class<S> specification) {

        super();
        this.descriptor = new SimpleComponentDescriptor<S>(specification);
    }

    /**
     * The constructor.
     * 
     * @param componentDescriptor
     *        is the {@link #getDescriptor() descriptor} of the component.
     */
    public AbstractComponentProvider(ComponentDescriptorIF<S> componentDescriptor) {

        super();
        this.descriptor = componentDescriptor;
    }
    
    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#getDescriptor()
     * 
     */
    public final ComponentDescriptorIF<S> getDescriptor() {

        return this.descriptor;
    }
    
}
