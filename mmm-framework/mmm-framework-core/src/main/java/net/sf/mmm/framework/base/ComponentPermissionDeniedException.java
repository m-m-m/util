/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.api.IocSecurityManagerIF;

/**
 * A {@link ComponentPermissionDeniedException} is thrown if a
 * {@link ComponentProviderIF component} tries to
 * {@link ComponentManagerIF#requestComponent(Class, String) request} another
 * {@link ComponentProviderIF component} without having permission to do so by
 * the security manager.
 * 
 * @see IocSecurityManagerIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentPermissionDeniedException extends ComponentSecurityException {

    /** UID for serialization */
    private static final long serialVersionUID = -328513333974531814L;

    /**
     * The constructor.
     * 
     * @param source
     *        is the requestor specification.
     * @param target
     *        is the requested specifciation.
     * @param instanceId
     *        is the requested instance-ID.
     */
    public ComponentPermissionDeniedException(Class source, Class target, String instanceId) {

        super(NlsResourceBundle.ERR_COMPONENT_PERMISSION_DENIED, source, target, instanceId);
    }

}
