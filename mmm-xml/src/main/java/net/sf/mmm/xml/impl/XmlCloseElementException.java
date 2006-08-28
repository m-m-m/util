/* $Id$ */
package net.sf.mmm.xml.impl;

import net.sf.mmm.xml.NlsResourceBundle;
import net.sf.mmm.xml.api.XmlException;

/**
 * This is the exception thrown if the
 * {@link net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(String, String) closing}
 * of an element failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlCloseElementException extends XmlException {

    /** uid for serialization */
    private static final long serialVersionUID = 5035282653290930026L;

    /**
     * The constructor.
     * 
     * @param openTagName
     *        is the (qualified) tagname used to
     *        {@link net.sf.mmm.xml.api.XmlWriterIF#writeStartElement(String, String, String) open}
     *        the element.
     * @param closeTagName
     *        is the (qualified) tagname used to
     *        {@link net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(String, String) close}
     *        the element that differes.
     */
    public XmlCloseElementException(String openTagName, String closeTagName) {

        super(NlsResourceBundle.ERR_CLOSE_TAGNAME, openTagName, closeTagName);
    }

}
