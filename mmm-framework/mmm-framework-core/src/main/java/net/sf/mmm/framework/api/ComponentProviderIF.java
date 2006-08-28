package net.sf.mmm.framework.api;

import java.io.InputStream;

/**
 * This is the interface for a provider of a
 * {@link ComponentDescriptorIF component}.
 * 
 * @see net.sf.mmm.framework.api.IocContainerIF
 * 
 * @param <S>
 *        is the {@link ComponentDescriptorIF#getSpecification() specification}
 *        of the provided component. The generic type in this interface is
 *        mainly used to make the specification more clear.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ComponentProviderIF<S> {

    /**
     * This method gets the descriptor of the provided component.
     * 
     * @return the component descriptor.
     */
    ComponentDescriptorIF<S> getDescriptor();

    /**
     * This method requests a new instance of the
     * {@link ComponentDescriptorIF component} provided by this implementation.
     * It depends on the implementation if a new instance is created for the
     * request or an existing instance is reused.
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the requested component. The default is <code>null</code>.
     * @param sourceDescriptor
     *        is the {@link ComponentDescriptorIF descriptor} of the component
     *        causing this request.
     * @param sourceInstanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the component causing this request.
     * @param componentManager
     *        is the component manager that can be used to
     *        {@link ComponentManagerIF#requestComponent(Class, String) request}
     *        required (dependent) components.
     * @return a container with the instance of the component.
     * @throws ComponentException
     *         if the request fails.
     */
    ComponentInstanceContainerIF<S> request(String instanceId,
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException;

    /**
     * This method is called if a
     * {@link ComponentInstanceContainerIF#getInstance() component instance} is
     * NOT needed anymore by the requestor. This method eithter
     * {@link #dispose(ComponentInstanceContainerIF, ComponentManagerIF) disposes}
     * the given
     * {@link ComponentInstanceContainerIF#getInstance() component instance} and
     * returns <code>true</code> or it decides to reuse the
     * {@link ComponentInstanceContainerIF#getInstance() component instance}
     * (e.g. shared singleton) and returns <code>false</code> without touching
     * it.<br>
     * 
     * @see #dispose(ComponentInstanceContainerIF, ComponentManagerIF)
     * 
     * @param instanceContainer
     *        is the container with the
     *        {@link ComponentInstanceContainerIF#getInstance() component instance}
     *        NOT needed any longer by the requestor.
     * @param componentManager
     *        is the component manager that can be used to
     *        {@link ComponentManagerIF#requestComponent(Class, String) request}
     *        required components.
     * @return <code>true</code> if the component is NOT needed anymore and
     *         has been shut-down, <code>false</code> otherwise (if the
     *         component is shared or pooled and will still be used).
     */
    boolean release(ComponentInstanceContainerIF<S> instanceContainer,
            ComponentManagerIF componentManager);

    /**
     * This method disposes the given
     * {@link ComponentInstanceContainerIF#getInstance() component instance}.
     * This method is only called explicitly if the
     * {@link IocContainerIF container} that created the component is stopped
     * but the component is shared and therefore NOT
     * {@link #release(ComponentInstanceContainerIF, ComponentManagerIF) released}.
     * It is suggested to use this method internally by the
     * {@link #release(ComponentInstanceContainerIF, ComponentManagerIF) release method}
     * if the component is really released.<br>
     * If a component is disposed (shut-down), all resources (e.g.
     * {@link Thread threads}, {@link Process processes},
     * {@link InputStream streams}, memory, etc.) allocated for the
     * {@link ComponentInstanceContainerIF#getInstance() componant instance}
     * should be released. All dependend components should NOT be touched by
     * this method. They will be released automatically be the container.<br>
     * 
     * @param instanceContainer
     * @param componentManager
     */
    void dispose(ComponentInstanceContainerIF<S> instanceContainer,
            ComponentManagerIF componentManager);
    
}
