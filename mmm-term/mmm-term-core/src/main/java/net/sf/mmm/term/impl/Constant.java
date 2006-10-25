/* $Id$ */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.term.base.AbstractTerm;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlSerializerIF;
import net.sf.mmm.xml.api.XmlWriterIF;

/**
 * This is the implementation of a constant
 * {@link net.sf.mmm.term.api.TermIF term}. It simply holds a value as constant
 * that is always returned as {@link #evaluate(ContextIF) evaluation} result.
 * 
 * @param <C>
 *        is the templated type of the constant.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Constant<C> extends AbstractTerm {

    /** @see XmlSerializer */
    public static final String XML_TAG_VALUE = "value";

    /** @see XmlSerializer */
    public static final String XML_TAG_NULL = "null";
    
    /** @see XmlSerializer */
    public static final String XML_ATR_VALUE_CLASS = "class";
    
    /**
     * This inner class is the default seriailizer used if none is supplied.
     * 
     * @see Constant#Constant(Object)
     * 
     * @param <C>
     *        is the templated type of the constant.
     */
    private static class XmlSerializer<C> implements XmlSerializerIF<C> {

        /**
         * @see net.sf.mmm.xml.api.XmlSerializerIF#toXml(net.sf.mmm.xml.api.XmlWriterIF,
         *      java.lang.Object) 
         */
        public void toXml(XmlWriterIF xmlWriter, C object) throws XmlException {

            // TODO: check for XmlSerializable, use ValueManager, ...
            xmlWriter.writeStartElement(XML_TAG_VALUE);
            if (object == null) {
                xmlWriter.writeStartElement(XML_TAG_NULL);
                xmlWriter.writeEndElement(XML_TAG_NULL);
            } else {
                xmlWriter.writeAttribute(XML_ATR_VALUE_CLASS, object.getClass().getName());
                xmlWriter.writeCharacters(object.toString());
            }
            xmlWriter.writeEndElement(XML_TAG_VALUE);
        }

    }

    /** uid for serialization */
    private static final long serialVersionUID = 8680487402807187621L;

    /** the <code>null</code> constant */
    public static final Constant<Object> NULL = new Constant<Object>(null);

    /** the <code>true</code> constant */
    public static final Constant<Boolean> TRUE = new Constant<Boolean>(Boolean.TRUE);

    /** the <code>false</code> constant */
    public static final Constant<Boolean> FALSE = new Constant<Boolean>(Boolean.FALSE);

    /** the constant value */
    private final C value;

    /** the serializer used for {@link #toXml(XmlWriterIF)} */
    private final XmlSerializerIF<C> serializer;

    /**
     * The dummy constructor. Only use for testing or outside the project.
     * 
     * @param constantValue
     *        is the value of this constant.
     */
    public Constant(C constantValue) {

        this(constantValue, new XmlSerializer<C>());
    }

    /**
     * The constructor.
     * 
     * @param constantValue
     *        is the value of this constant.
     * @param valueSerializer
     *        is the serializer used for {@link #toXml(XmlWriterIF)}
     */
    public Constant(C constantValue, XmlSerializerIF<C> valueSerializer) {

        super();
        this.value = constantValue;
        this.serializer = valueSerializer;
    }

    /**
     * @see net.sf.mmm.term.api.TermIF#evaluate(net.sf.mmm.context.api.ContextIF)
     *      
     */
    public C evaluate(ContextIF variableSet) {

        return this.value;
    }

    /**
     * @see net.sf.mmm.xml.api.XmlSerializableIF#toXml(XmlWriterIF)
     *      
     */
    public void toXml(XmlWriterIF xmlWriter) throws XmlException {

        xmlWriter.writeStartElement(XML_TAG_CONSTANT);
        this.serializer.toXml(xmlWriter, this.value);
        xmlWriter.writeEndElement(XML_TAG_CONSTANT);
    }

    /**
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString() {

        return this.value.toString();
    }

}