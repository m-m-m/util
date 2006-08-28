/* $Id$ */
package net.sf.mmm.framework.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.base.ComponentInstanceContainer;
import net.sf.mmm.framework.base.provider.AbstractStaticSingletonComponentProvider;

/**
 * This is a provider for a {@link ThreadPoolExecutor thread-pool}
 * {@link Executor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ThreadPoolProvider extends AbstractStaticSingletonComponentProvider<Executor> {

    /** the singleton instance */
    private ThreadPoolExecutor threadPool;

    /** @see #getCorePoolSize() */
    private int corePoolSize;

    /** @see #getMaximumPoolSize() */
    private int maximumPoolSize;

    /**
     * The constructor.
     */
    public ThreadPoolProvider() {

        super(Executor.class);
        this.corePoolSize = 1;
        this.maximumPoolSize = Integer.MAX_VALUE;
    }

    /**
     * This method gets the core pool-size.
     * 
     * @see ThreadPoolExecutor#getCorePoolSize()
     * 
     * @return the corePoolSize.
     */
    public int getCorePoolSize() {

        return this.corePoolSize;
    }

    /**
     * This method sets the {@link #getCorePoolSize() core pool-size}.
     * 
     * @see ThreadPoolExecutor#setCorePoolSize(int)
     * 
     * @param newCorePoolSize
     *        is the corePoolSize to set.
     */
    public void setCorePoolSize(int newCorePoolSize) {

        this.corePoolSize = newCorePoolSize;
    }

    /**
     * This method gets the maximum pool-size.
     * 
     * @see ThreadPoolExecutor#getMaximumPoolSize()
     * 
     * @return the maximumPoolSize.
     */
    public int getMaximumPoolSize() {

        return this.maximumPoolSize;
    }

    /**
     * This method sets the {@link #getMaximumPoolSize() maximum pool-size}.
     * 
     * @see ThreadPoolExecutor#setMaximumPoolSize(int)
     * 
     * @param newMaximumPoolSize
     *        is the maximumPoolSize to set.
     */
    public void setMaximumPoolSize(int newMaximumPoolSize) {

        this.maximumPoolSize = newMaximumPoolSize;
    }

    /**
     * This method gets the current number of threads in the pool.
     * 
     * @see ThreadPoolExecutor#getPoolSize()
     * 
     * @return the current pool size.
     */
    public int getCurrentPoolSize() {

        return this.threadPool.getPoolSize();
    }

    /**
     * This method gets the number of threads in the pool that are currently
     * active.
     * 
     * @return the number of active threads in the pool.
     */
    public int getActiveThreadCount() {

        return this.threadPool.getActiveCount();
    }

    /**
     * This method gets the largest number of threads that have been in the pool
     * since it was created.
     * 
     * @see ThreadPoolExecutor#getLargestPoolSize()
     * 
     * @return the largest pool size since creation of the pool.
     */
    public int getLargestPoolSize() {

        return this.threadPool.getLargestPoolSize();
    }

    /**
     * @see net.sf.mmm.framework.base.provider.AbstractStaticSingletonComponentProvider#requestSingleton(net.sf.mmm.framework.api.ComponentDescriptorIF,
     *      java.lang.String, net.sf.mmm.framework.api.ComponentManagerIF)
     *      {@inheritDoc}
     */
    @Override
    protected ComponentInstanceContainerIF<Executor> requestSingleton(
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) {

        // TODO Auto-generated method stub
        return super.requestSingleton(sourceDescriptor, sourceInstanceId, componentManager);
    }

    /**
     * @see net.sf.mmm.framework.base.provider.AbstractDefaultInstanceComponentProvider#requestDefault(net.sf.mmm.framework.api.ComponentDescriptorIF,
     *      java.lang.String, net.sf.mmm.framework.api.ComponentManagerIF)
     *      {@inheritDoc}
     */
    @Override
    protected ComponentInstanceContainerIF<Executor> requestDefault(
            ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId,
            ComponentManagerIF componentManager) throws ComponentException {

        ThreadFactory threadFactory = componentManager.requestComponent(ThreadFactory.class);
        this.threadPool = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        return new ComponentInstanceContainer<Executor>(this.threadPool);
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#dispose(net.sf.mmm.framework.api.ComponentInstanceContainerIF,
     *      net.sf.mmm.framework.api.ComponentManagerIF) {@inheritDoc}
     */
    @Override
    public void dispose(ComponentInstanceContainerIF<Executor> instanceContainer,
            ComponentManagerIF componentManager) {

        this.threadPool.shutdown();
        super.dispose(instanceContainer, componentManager);
    }

}
