/* $Id$ */
package net.sf.mmm.framework.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * An {@link IocException} is thrown if anything goes wrong with
 * {@link ComponentProviderIF component} {@link IocContainerIF management}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class IocException extends NlsRuntimeException {

    /**
     * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
     * {@inheritDoc}
     */
    public IocException(String newInternaitionalizedMessage, Object... newArguments) {

        super(newInternaitionalizedMessage, newArguments);
    }

    /**
     * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public IocException(Throwable newNested, String newInternaitionalizedMessage,
            Object... newArguments) {

        super(newNested, newInternaitionalizedMessage, newArguments);
    }

}
