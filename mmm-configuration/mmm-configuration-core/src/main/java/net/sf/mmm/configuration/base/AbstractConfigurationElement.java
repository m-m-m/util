/* $Id: AbstractConfigurationElement.java 205 2006-08-10 19:04:59Z hohwille $ */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.MutableConfigurationIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF} interface for the
 * {@link #getType() type} {@link Type#ELEMENT element}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationElement extends BasicConfiguration {

    /** the child elements */
    private final Map<QName, List<AbstractConfiguration>> childElements;

    /** the child attributes */
    private final Map<QName, AbstractConfiguration> childAttributes;

    /**
     * The constructor.
     * 
     * @param parentConfiguration
     *        is the parent configuration.
     */
    public AbstractConfigurationElement(AbstractConfiguration parentConfiguration) {

        super(parentConfiguration);
        this.childElements = new HashMap<QName, List<AbstractConfiguration>>();
        this.childAttributes = new HashMap<QName, AbstractConfiguration>();
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getType() {@inheritDoc}
     */
    public Type getType() {

        return Type.ELEMENT;
    }

    /**
     * This method creates a new empty and unattached
     * {@link Type#ATTRIBUTE attribute} as {@link #getChildren(Type) child} of
     * this configuration.
     * 
     * @param name
     *        is the {@link #getName() name} of the
     *        {@link Type#ATTRIBUTE attribute} to create (including the
     *        {@link ConfigurationIF#NAME_PREFIX_ATTRIBUTE prefix}
     * @param namespaceUri
     *        is the {@link #getNamespaceUri() namespace} of the attribute.
     * @return the new attribute.
     */
    protected abstract AbstractConfiguration createChildAttribute(String name, String namespaceUri);

    /**
     * This method creates a new empty {@link Type#ELEMENT element} as child of
     * this configuration.
     * 
     * @param name
     *        is the {@link #getName() name} of the
     *        {@link Type#ATTRIBUTE attribute} to create.
     * @param namespaceUri
     *        is the {@link #getNamespaceUri() namespace} of the element.
     * @return the new element.
     */
    protected abstract AbstractConfiguration createChildElement(String name, String namespaceUri);

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
     *      java.lang.String) {@inheritDoc}
     */
    @Override
    public AbstractConfiguration getChild(String name, String namespace)
            throws ConfigurationException {

        if (name.length() == 0) {
            throw new ConfigurationException("Child name must not be empty!");
        }
        AbstractConfiguration child = null;
        QName qName = new QName(name, namespace);
        if (name.charAt(0) == ConfigurationIF.NAME_PREFIX_ATTRIBUTE) {
            child = this.childAttributes.get(qName);
        } else {
            List<AbstractConfiguration> childList = getChildElementList(qName);
            if (!childList.isEmpty()) {
                child = childList.get(0);
            }
        }
        return child;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(java.lang.String,
     *      java.lang.String) {@inheritDoc}
     */
    @Override
    AbstractConfiguration doCreateChild(String name, String namespace)
            throws ConfigurationException {

        AbstractConfiguration child;
        QName qName = new QName(name, namespace);
        if (name.charAt(0) == ConfigurationIF.NAME_PREFIX_ATTRIBUTE) {
            child = this.childAttributes.get(qName);
            if (child == null) {
                child = createChildAttribute(name, namespace);
                this.childAttributes.put(qName, child);
            }
        } else {
            List<AbstractConfiguration> childList = getChildElementList(qName);
            child = createChildElement(name, namespace);
            childList.add(child);
        }
        return child;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.ConfigurationIF.Type)
     *      {@inheritDoc}
     */
    @Override
    public Iterator<AbstractConfiguration> getChildren(Type childType) {

        switch (childType) {
            case ELEMENT:
                return new ChildIterator(null, this.childElements.values().iterator());
            case ATTRIBUTE:
                return new ChildIterator(this.childAttributes.values().iterator(), null);
            default :
                if (childType == null) {
                    return new ChildIterator(this.childAttributes.values().iterator(),
                            this.childElements.values().iterator());
                } else {
                    throw new IllegalArgumentException();
                }
        }
    }

    /**
     * This method is called if a {@link #getChild(String, String) child} has
     * been {@link #addChild(AbstractConfiguration) added} that has the
     * {@link #getNamespaceUri() namespace}
     * {@link ConfigurationDocumentIF#NAMESPACE_URI_CONFIGURATION}.
     * 
     * @param child
     *        is the child to handle.
     */
    protected void handleInternalChild(AbstractConfiguration child) {

        String name = child.getName();
        if (ConfigurationDocumentIF.NAME_CONTEXT.equals(name)) {
            Collection<? extends MutableConfigurationIF> envChildIt = child
                    .getDescendants(ConfigurationDocumentIF.NAME_CONTEXT_VARIABLE);
            for (ConfigurationIF variable : envChildIt) {
                String variableName = variable.getDescendant(
                        ConfigurationDocumentIF.NAME_CONTEXT_VARIABLE_NAME).getValue().getString();
                String variableValue = variable.getValue().getString();
                getOwnerDocument().getMutableContext().setVariable(variableName, variableValue);
            }
        } else if (ConfigurationDocumentIF.NAME_INCLUDE.equals(name)) {
            // getOwnerDocument().isIncludeEnabled();
            // TODO:
            // TODO: allow to disable includes per document for security reasons
            List<AbstractConfiguration> includeList = new ArrayList<AbstractConfiguration>();
            ConfigurationUtil.resolveInclude(child, includeList);
            for (AbstractConfiguration includeChild : includeList) {
                if (includeChild.getType() == Type.ELEMENT) {
                    addChild(includeChild);
                } else {
                    throw new ConfigurationException("Including attributes is NOT allowed!");
                }
            }
        } else if (ConfigurationDocumentIF.NAME_EXCLUDE.equals(name)) {
            // nothing to do...
        } else {
            // TODO
            // throw new ConfigurationException("Illegal child '" + name +
            // "'!");
        }
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     *      {@inheritDoc}
     */
    @Override
    protected void addChild(AbstractConfiguration child) {

        String namespace = child.getNamespaceUri();
        QName qName = new QName(child.getName(), namespace);
        if (ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION.equals(namespace)) {
            handleInternalChild(child);
        }
        switch (child.getType()) {
            case ATTRIBUTE:
                if (this.childAttributes.containsKey(qName)) {
                    throw new IllegalArgumentException("Duplicate Attribute: " + qName + " in "
                            + this);
                }
                this.childAttributes.put(qName, child);
                break;
            case ELEMENT:
                List<AbstractConfiguration> childList = getChildElementList(qName);
                childList.add(child);
                break;
            default :
                throw new IllegalArgumentException("unknown type for " + child);
        }
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     *      {@inheritDoc}
     */
    @Override
    protected void removeChild(AbstractConfiguration child) {

        if (!isEditable()) {
            throw new ConfigurationException("not editable");
        }
        QName qName = new QName(child.getName(), child.getNamespaceUri());
        switch (child.getType()) {
            case ATTRIBUTE:
                this.childAttributes.remove(qName);
                break;
            case ELEMENT:
                List<AbstractConfiguration> childList = getChildElementList(qName);
                childList.remove(child);
                break;
            default :
                throw new IllegalArgumentException("unknown type for " + child);
        }
    }

    /**
     * This method gets the list of all child {@link Type#ELEMENT elements} with
     * the given
     * {@link AbstractConfiguration#createQualifiedName(String, String) "qualified name"}.
     * 
     * @param qName
     *        is the qualified name of the requested child list.
     * @return the requested child list.
     */
    protected List<AbstractConfiguration> getChildElementList(QName qName) {

        List<AbstractConfiguration> childList = this.childElements.get(qName);
        if (childList == null) {
            childList = new ArrayList<AbstractConfiguration>();
            this.childElements.put(qName, childList);
        }
        return childList;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
     *      java.lang.String) {@inheritDoc}
     */
    @Override
    public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

        QName qName = new QName(name, namespaceUri);
        if (name.charAt(0) == NAME_PREFIX_ATTRIBUTE) {
            AbstractConfiguration result = this.childAttributes.get(qName);
            if (result == null) {
                if ((namespaceUri != null) && (namespaceUri.equals(getNamespaceUri()))) {
                    result = this.childAttributes.get(new QName(name, NAMESPACE_URI_NONE));
                }
            }
            if (result == null) {
                return EmptyConfigurationIterator.getInstance();
            } else {
                return new SingleConfigurationIterator(result);
            }
        } else {
            List<AbstractConfiguration> childList = this.childElements.get(qName);
            if (childList == null) {
                return EmptyConfigurationIterator.getInstance();
            }
            return childList.iterator();
        }
    }

}
