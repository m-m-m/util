/* $Id: DateValueManager.java 208 2006-08-22 20:29:11Z hohwille $ */
package net.sf.mmm.value.impl.type;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.sf.mmm.util.DateUtil;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;
import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This is the {@link ValueManagerIF manager} for {@link java.util.Date date}
 * values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DateValueManager extends AbstractValueManager<Date> {

    /** the {@link ValueManagerIF#getName() "logical name"} of the managed value */
    public static final String VALUE_NAME = "Date";

    /** the {@link ValueManagerIF#getValueType() type} of the managed value */
    private static final Class<Date> VALUE_TYPE = Date.class;

    /** the XML tagname for the date */
    private static final String XML_TAG_DATE = "date";

    /** the XML attribute for the year */
    private static final String XML_ATR_DATE_YEAR = "year";

    /** the XML attribute for the month */
    private static final String XML_ATR_DATE_MONTH = "month";

    /** the XML attribute for the day */
    private static final String XML_ATR_DATE_DAY = "day";

    /** the XML tagname for the time */
    private static final String XML_TAG_TIME = "time";

    /** the XML attribute for the hour */
    private static final String XML_ATR_TIME_HOUR = "hour";

    /** the XML attribute for the minute */
    private static final String XML_ATR_TIME_MINUTE = "minute";

    /** the XML attribute for the second */
    private static final String XML_ATR_TIME_SECOND = "second";

    /**
     * The constructor.
     * 
     */
    public DateValueManager() {

        super();
    }

    /**
     * @see net.sf.mmm.value.api.ValueManagerIF#getName() {@inheritDoc}
     */
    public String getName() {

        return VALUE_NAME;
    }

    /**
     * @see net.sf.mmm.value.api.ValueManagerIF#getValueType() {@inheritDoc}
     */
    public Class<Date> getValueType() {

        return VALUE_TYPE;
    }

    /**
     * @see net.sf.mmm.value.api.ValueManagerIF#parse(java.lang.String)
     *      {@inheritDoc}
     */
    public Date parse(String valueAsString) throws ValueParseException {

        try {
            return DateUtil.parse(valueAsString);
        } catch (ParseException e) {
            throw new ValueParseStringException(valueAsString, VALUE_TYPE, VALUE_NAME, e);
        }
    }

    /**
     * 
     * @param element
     * @param attributeName
     * @return the attribute as integer.
     * @throws ValueParseException
     */
    protected int getAttributeAsInteger(Element element, String attributeName)
            throws ValueParseException {

        String value = "";
        if (element != null) {
            value = element.getAttribute(attributeName);
            if (value.length() > 0) {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new ValueParseStringException(value, int.class, getName(), e);
                }
            }
        }
        throw new ValueParseStringException(value, int.class, getName());
    }

    /**
     * 
     * @param element
     * @param attributeName
     * @param fallback
     * @return the attribute as integer or the fallback if the attribute is NOT
     *         defined.
     * @throws ValueParseException
     */
    protected int getAttributeAsInteger(Element element, String attributeName, int fallback)
            throws ValueParseException {

        if (element != null) {
            String value = element.getAttribute(attributeName);
            if (value.length() > 0) {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new ValueParseStringException(value, int.class, getName(), e);
                }
            }
        }
        return fallback;
    }

    /**
     * @see net.sf.mmm.value.base.AbstractValueManager#parse(org.w3c.dom.Element)
     *      {@inheritDoc}
     */
    @Override
    public Date parse(Element valueAsXml) throws ValueParseException {

        Calendar calendar = Calendar.getInstance();
        NodeList childNodes = valueAsXml.getChildNodes();

        Element dateElement = DomUtil.getFirstElement(childNodes, XML_TAG_DATE);
        int year = getAttributeAsInteger(dateElement, XML_ATR_DATE_YEAR);
        calendar.set(Calendar.YEAR, year);
        int month = getAttributeAsInteger(dateElement, XML_ATR_DATE_MONTH);
        calendar.set(Calendar.MONTH, month - 1);
        int day = getAttributeAsInteger(dateElement, XML_ATR_DATE_DAY);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        Element timeElement = DomUtil.getFirstElement(childNodes, XML_TAG_TIME);
        int hour = getAttributeAsInteger(timeElement, XML_ATR_TIME_HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        int min = getAttributeAsInteger(timeElement, XML_ATR_TIME_MINUTE, 0);
        calendar.set(Calendar.MINUTE, min);
        int sec = getAttributeAsInteger(timeElement, XML_ATR_TIME_SECOND, 0);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @see net.sf.mmm.value.api.ValueManagerIF#toString(java.lang.Object)
     *      {@inheritDoc}
     */
    @Override
    public String toString(Date value) {

        return DateUtil.format(value);
    }

    /**
     * @see AbstractValueManager#toXmlValue(XmlWriterIF, Object) {@inheritDoc}
     */
    @Override
    protected void toXmlValue(XmlWriterIF xmlWriter, Date value) throws XmlException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        xmlWriter.writeStartElement(XML_TAG_DATE);
        xmlWriter.writeAttribute(XML_ATR_DATE_YEAR, Integer.toString(calendar.get(Calendar.YEAR)));
        xmlWriter.writeAttribute(XML_ATR_DATE_MONTH, Integer
                .toString(calendar.get(Calendar.MONTH) + 1));
        xmlWriter.writeAttribute(XML_ATR_DATE_DAY, Integer.toString(calendar
                .get(Calendar.DAY_OF_MONTH)));
        xmlWriter.writeEndElement(XML_TAG_DATE);
        xmlWriter.writeStartElement(XML_TAG_TIME);
        xmlWriter.writeAttribute(XML_ATR_TIME_HOUR, Integer.toString(calendar
                .get(Calendar.HOUR_OF_DAY)));
        xmlWriter.writeAttribute(XML_ATR_TIME_MINUTE, Integer.toString(calendar
                .get(Calendar.MINUTE)));
        xmlWriter.writeAttribute(XML_ATR_TIME_SECOND, Integer.toString(calendar
                .get(Calendar.SECOND)));
        xmlWriter.writeEndElement(XML_TAG_TIME);
    }
}
