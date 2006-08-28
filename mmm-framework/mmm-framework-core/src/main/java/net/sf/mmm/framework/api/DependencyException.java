/* $Id$ */
package net.sf.mmm.framework.api;

/**
 * A {@link DependencyException} is thrown if a dependency problem occured. This
 * means that a dependency was requested that could NOT be provided. This can
 * have one of the following reasons:
 * <ul>
 * <li>the dependency is NOT available</li>
 * <li>the dependency caused a cycle</li>
 * <li>the dependend component could not be set up</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class DependencyException extends IocException {

    /** UID for serialization */
    private static final long serialVersionUID = 592958136566865602L;

    /**
     * The constructor.
     * 
     * @param newInternaitionalizedMessage
     * @param newArguments
     */
    public DependencyException(String newInternaitionalizedMessage, Object... newArguments) {

        super(newInternaitionalizedMessage, newArguments);
    }

    /**
     * The constructor.
     * 
     * @param newNested
     * @param newInternaitionalizedMessage
     * @param newArguments
     */
    public DependencyException(Throwable newNested, String newInternaitionalizedMessage,
            Object... newArguments) {

        super(newNested, newInternaitionalizedMessage, newArguments);
    }

}
