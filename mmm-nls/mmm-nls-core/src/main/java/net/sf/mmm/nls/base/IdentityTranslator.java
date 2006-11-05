/* $Id$ */
package net.sf.mmm.nls.base;

import net.sf.mmm.nls.api.StringTranslator;

/**
 * This is an implementation of the StringTranslatorIF interface that simply
 * returns the given string untranslated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class IdentityTranslator implements StringTranslator {

    /** the singleton instance */
    public static final StringTranslator INSTANCE = new IdentityTranslator();

    /**
     * The constructor.
     */
    private IdentityTranslator() {

        super();
    }

    /**
     * @see net.sf.mmm.nls.api.StringTranslator#translate(java.lang.String)
     */
    public String translate(String message) {

        return message;
    }

}