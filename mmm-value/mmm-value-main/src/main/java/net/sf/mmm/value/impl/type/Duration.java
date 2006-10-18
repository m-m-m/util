/* $Id$ */
package net.sf.mmm.value.impl.type;


import net.sf.mmm.util.StringUtil;
import net.sf.mmm.value.api.type.DurationIF;
import net.sf.mmm.value.api.type.TimeIF;
import net.sf.mmm.xml.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This is the implementation of a duration value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Duration implements DurationIF {

    /** uid for serialization */
    private static final long serialVersionUID = -2996008315537598480L;

    /** the actual duration */
    private final long duration;

    /** the days */
    private final long days;

    /** the hours (0-23) */
    private final int hours;

    /** the minutes (0-59) */
    private final int minutes;

    /** the seconds (0-59) */
    private final int seconds;

    /** the milliseconds (0-999) */
    private final int milliseconds;

    /**
     * The constructor.
     * 
     * @param durationInMillis
     *        is the time difference in milliseconds.
     */
    public Duration(long durationInMillis) {

        super();
        this.duration = durationInMillis;
        this.milliseconds = (int) (this.duration % TimeIF.MILLISECONDS_PER_SECOND);
        long rest = this.duration / TimeIF.MILLISECONDS_PER_SECOND;
        this.seconds = (int) (rest % TimeIF.SECONDS_PER_MINUTE);
        rest = rest / TimeIF.SECONDS_PER_MINUTE;
        this.minutes = (int) (rest % TimeIF.MINUTES_PER_HOUR);
        rest = rest / TimeIF.MINUTES_PER_HOUR;
        this.hours = (int) (rest % TimeIF.HOURS_PER_DAY);
        rest = rest / TimeIF.HOURS_PER_DAY;
        this.days = rest;
    }

    /**
     * The constructor.
     * 
     * @param durationAsString
     *        is the duration given as String in the format as produced by
     *        {@link Duration#toString()}.
     */
    public Duration(String durationAsString) {

        // TODO:
        this(0);
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#getDays()
     * 
     */
    public long getDays() {

        return this.days;
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#getHours()
     * 
     */
    public int getHours() {

        return this.hours;
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#getMinutes()
     * 
     */
    public int getMinutes() {

        return this.minutes;
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#getSeconds()
     * 
     */
    public int getSeconds() {

        return this.seconds;
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#getMilliseconds()
     * 
     */
    public int getMilliseconds() {

        return this.milliseconds;
    }

    /**
     * @see net.sf.mmm.value.api.type.DurationIF#asMilliseconds()
     * 
     */
    public long asMilliseconds() {

        return this.duration;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     */
    public String toString() {

        StringBuffer result = new StringBuffer();
        if (this.days > 0) {
            result.append(Long.toString(this.days));
            result.append("#");
        }
        result.append(StringUtil.format(this.hours, 2));
        result.append(":");
        result.append(StringUtil.format(this.minutes, 2));
        result.append(":");
        result.append(StringUtil.format(this.seconds, 2));
        if (this.milliseconds == 0) {
            result.append(".");
            result.append(StringUtil.format(this.milliseconds, 4));
        }
        return result.toString();
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     * 
     */
    public void toXml(XmlWriterIF xmlWriter) throws XmlException {

    // TODO
    }
}