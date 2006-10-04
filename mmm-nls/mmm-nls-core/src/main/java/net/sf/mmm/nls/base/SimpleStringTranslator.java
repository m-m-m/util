/* $Id$ */
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

    /** the original bundle */
    private final AbstractResourceBundle nlsBundle;
    
    /** the nationalized bundle */
    private final ResourceBundle localeBundle;
    
    /**
     * The constructor.
     * 
     * @param internationalBundle 
     * @param locale 
     */
    public SimpleStringTranslator(AbstractResourceBundle internationalBundle, Locale locale) {
        super();
        this.nlsBundle = internationalBundle;
        this.localeBundle = ResourceBundle.getBundle(this.nlsBundle.getClass().getName(), locale);
    }

    /**
     * @see net.sf.mmm.nls.api.StringTranslatorIF#translate(java.lang.String)
     * 
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
