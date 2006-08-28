/* $Id: IdentityTranslator.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.nls.base;

import net.sf.mmm.nls.api.StringTranslatorIF;

/**
 * This is an implementation of the StringTranslatorIF interface that simply
 * returns the given string untranslated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class IdentityTranslator implements StringTranslatorIF {

    /** the singleton instance */
    public static final StringTranslatorIF INSTANCE = new IdentityTranslator();

    /**
     * The constructor.
     */
    private IdentityTranslator() {

        super();
    }

    /**
     * @see net.sf.mmm.nls.api.StringTranslatorIF#translate(java.lang.String)
     * {@inheritDoc}
     */
    public String translate(String message) {

        return message;
    }

}