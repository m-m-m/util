/* $Id: SimpleStringTranslator.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.nls.base;

import java.util.Locale;
import java.util.ResourceBundle;

import net.sf.mmm.nls.api.StringTranslatorIF;


/**
 * TODO This type ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleStringTranslator implements StringTranslatorIF {

    private final AbstractResourceBundle nlsBundle;
    
    private final ResourceBundle localeBundle;
    
    /**
     * The constructor.
     *
     */
    public SimpleStringTranslator(AbstractResourceBundle internationalBundle, Locale locale) {
        super();
        this.nlsBundle = internationalBundle;
        this.localeBundle = ResourceBundle.getBundle(this.nlsBundle.getClass().getName(), locale);
    }

    /**
     * @see net.sf.mmm.nls.api.StringTranslatorIF#translate(java.lang.String)
     * {@inheritDoc}
     */
    public String translate(String message) {

        String result = message;
        String key = this.nlsBundle.getKey(message);
        if (key != null) {
            Object localMessage = this.localeBundle.getObject(key);
            if (localMessage != null) {
                result = localMessage.toString();
            }
        }
        return result;
    }

}
