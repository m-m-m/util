/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for transfer-objects.
 * <a name="documentation"/><h2>Transfer-object API</h2>
 * Provides the API and base classes for {@link net.sf.mmm.util.transferobject.api.TransferObject}s and utilities based on
 * well-established concepts.<br/>
 * A {@link net.sf.mmm.util.entity.api.PersistenceEntity} often contains relations (see
 * {@link javax.persistence.OneToOne}, {@link javax.persistence.OneToMany} {@link javax.persistence.ManyToOne}
 * , or {@link javax.persistence.ManyToMany}). While a {@link net.sf.mmm.util.entity.api.PersistenceEntity}
 * can make use of lazy loading this is not possible in {@link net.sf.mmm.util.transferobject.api.TransferObject}.
 * Therefore you have to express which <em>selection</em> of your data model you want to transfer for services that load, save, update,
 * delete or process data. If you would add all relations to your {@link net.sf.mmm.util.transferobject.api.TransferObject}s
 * you might end up with one of the following problems:
 * <ul>
 * <li>loading way too much data causing serious performance problems</li>
 * <li>relations sometimes being available and sometimes being <code>null</code> without a transparent concept causing
 * serious runtime errors.</li>
 * </ul>
 * Hence you want to avoid using relations in the same way in your
 * {@link net.sf.mmm.util.transferobject.api.TransferObject}s. To avoid a naive way of doing this, this package introduces
 * {@link net.sf.mmm.util.transferobject.api.DataTo DTO}, {@link net.sf.mmm.util.transferobject.api.EntityTo ETO}, and
 * {@link net.sf.mmm.util.transferobject.api.CompositeTo CTO}.<br/>
 * <b>Example:</b><br/>
 * Assume you have the following {@link net.sf.mmm.util.entity.api.GenericEntity entities}:
 * <p>
 * <img src="{@docRoot}/doc-files/AddressExampleEntityAndTo.png"/>
 * </p>
 * Now you could build the following {@link net.sf.mmm.util.transferobject.api.TransferObject TO}s:
 * <ul>
 * <li><code>AddressTo</code> with the attributes from <code>Address</code>.</li>
 * <li><code>ContactInfoTo</code> with the attributes from <code>ContactInfo</code>.</li>
 * <li><code>AddressWithContactInfoTo</code> with the attributes from both <code>Address</code> and
 * <code>ContactInfo</code>.</li>
 * </ul>
 * Due to the lack of multi-inheritance in java, the latter case is a problem. Therefore you would have to
 * duplicate the attributes from <code>Address</code> or <code>ContactInfo</code> (or both). <br/>
 * Therefore our recommendation is to solve this by creating the following {@link net.sf.mmm.util.transferobject.api.TransferObject}s:
 * <ul>
 * <li><code>AddressEto</code> extending {@link net.sf.mmm.util.transferobject.api.EntityTo} with the attributes from <code>Address</code>.</li>
 * <li><code>ContactInfoEto</code> extending {@link net.sf.mmm.util.transferobject.api.EntityTo} with the attributes from
 * <code>ContactInfo</code>.</li>
 * <li><code>AddressWithContactInfoCto</code> extending {@link net.sf.mmm.util.transferobject.api.CompositeTo} containing <code>AddressEto</code>
 * and <code>ContactInfoEto</code>.</li>
 * </ul>
 * If you separate {@link net.sf.mmm.util.entity.api.PersistenceEntity persistence entities} from
 * {@link net.sf.mmm.util.transferobject.api.TransferObject}s then <code>Address</code> and <code>ContactInfo</code> should be an
 * interfaces that centralize the JavaDoc for the attributes via the getter methods. Your {@link net.sf.mmm.util.transferobject.api.EntityTo}
 * (e.g. <code>AddressEto</code>) and {@link net.sf.mmm.util.entity.api.PersistenceEntity} (e.g.
 * <code>AddressEntity</code>) then both implement that interface (e.g. <code>Address</code>).<br/>
 * <b>NOTE:</b><br/>
 *
 *
 */
package net.sf.mmm.util.transferobject.api;

