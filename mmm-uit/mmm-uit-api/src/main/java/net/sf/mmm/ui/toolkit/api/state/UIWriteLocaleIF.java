/* $Id: UIWriteLocaleIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

import java.util.Locale;

/**
 * This interface gives {@link UIReadLocaleIF#getLocale() read} and
 * {@link #setLocale(Locale) write} access to the {@link java.util.Locale} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteLocaleIF extends UIReadLocaleIF {

    /**
     * This method sets the locale to the given value.
     * 
     * @see UIReadLocaleIF#getLocale()
     * 
     * @param newLocale
     *        is the new locale to use.
     */
    void setLocale(Locale newLocale);

}
