/* $Id$ */
package net.sf.mmm.value.api.type;

import net.sf.mmm.xml.api.XmlSerializableIF;

/**
 * This is the interface for a time value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TimeIF extends XmlSerializableIF {

    /**
     * The {@link net.sf.mmm.value.api.ValueManagerIF#getName() name} of this
     * value type.
     */
    String VALUE_NAME = "Time";
    
    /** the number of hours per day */
    long HOURS_PER_DAY = 24;
    
    /** the number of minutes per hour */
    long MINUTES_PER_HOUR = 60;
    
    /** the number of seconds per minute */
    long SECONDS_PER_MINUTE = 60;

    /** the number of milliseconds per second */
    long MILLISECONDS_PER_SECOND = 1000L;
    
    /** the number of milliseconds per minute */
    long MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * SECONDS_PER_MINUTE;
    
    /** the number of milliseconds per hour */
    long MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    
    /** the number of milliseconds per day */
    long MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * HOURS_PER_DAY;
    
    /**
     * This method gets the hours of the time.
     * 
     * @return the hours in the range of 0-23.
     */
    int getHours();

    /**
     * This methode gets the minutes of the time.
     * 
     * @return the minutes in the range of 0-59.
     */
    int getMinutes();

    /**
     * This method gets the seconds of the time.
     * 
     * @return the seconds in the range of 0-59.
     */
    int getSeconds();

    /**
     * This method gets the milliseconds of the time.
     * 
     * @return the milliseconds in the range of 0-999.
     */
    int getMilliseconds();
    
    /**
     * This method gets a string representation of this object in the format
     * "HH:mm:ss".
     * 
     * @see java.lang.Object#toString()
     */
    public String toString();

}