/* $Id$ */
package net.sf.mmm.nls.api;

/**
 * This is the interface for an object with native language support. Such object
 * be can {@link #toNlsMessage() converted} to an
 * {@link NlsMessage i18n message} describing the object analog to its
 * {@link Object#toString() string representation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsObject {

    /**
     * This method is the equivalent to {@link Object#toString()} with native
     * language support.
     * 
     * @return an nls message representing this object.
     */
    NlsMessage toNlsMessage();

}
