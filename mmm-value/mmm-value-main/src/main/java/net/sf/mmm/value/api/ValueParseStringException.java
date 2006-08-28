/* $Id: ValueParseStringException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.NlsResourceBundle;

/**
 * This exception is thrown if the parsing of a value as string fails.
 * 
 * @see net.sf.mmm.value.api.ValueManagerIF#parse(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseStringException extends ValueParseException {

    /** uid for serialization */
    private static final long serialVersionUID = -5461017585248675363L;

    /**
     * The constructor.
     * 
     * @param stringToParse
     *        is the string that could not be parsed
     * @param valueType
     *        is the {@link ValueManagerIF#getValueType() type} of value the
     *        given string should be parsed as.
     * @param valueName
     *        is the {@link ValueManagerIF#getName() name} of value the given
     *        string should be parsed as.
     */
    public ValueParseStringException(String stringToParse, Class valueType, String valueName) {

        this(stringToParse, valueType, valueName, null);
    }

    /**
     * The constructor.
     * 
     * @param stringToParse
     *        is the string that could not be parsed
     * @param valueType
     *        is the {@link ValueManagerIF#getValueType() type} of value the
     *        given string should be parsed as.
     * @param valueName
     *        is the {@link ValueManagerIF#getName() name} of value the given
     *        string should be parsed as.
     * @param nested
     */
    public ValueParseStringException(String stringToParse, Class valueType, String valueName,
            Throwable nested) {

        super(nested, NlsResourceBundle.ERR_PARSE_STRING, stringToParse, valueType, valueName);
    }

    /**
     * This method gets the expected {@link ValueManagerIF#getValueType() type}
     * of the value that could not be parsed.
     * 
     * @return the value type.
     */
    public Class getValueType() {

        return (Class) getNlsMessage().getArgument(1);
    }

    /**
     * This method gets the expected {@link ValueManagerIF#getName() name} of
     * the value that could not be parsed.
     * 
     * @return the value name.
     */
    public String getName() {

        return (String) getNlsMessage().getArgument(2);
    }

    /**
     * This method gets the string that could not be
     * {@link ValueManagerIF#parse(String) parsed} and is the reason of this
     * exception.
     * 
     * @return the malformed value string.
     */
    public String getStringToParse() {

        return (String) getNlsMessage().getArgument(0);
    }

}
