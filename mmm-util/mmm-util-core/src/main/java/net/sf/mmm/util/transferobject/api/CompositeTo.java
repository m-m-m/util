/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

/**
 * This is the abstract base class for a composite {@link AbstractTransferObject}. Such object should contain
 * (aggregate) other {@link AbstractTransferObject}s but not atomic attributes. Though this is not a technical
 * restriction it is a recommended design pattern to establish reuse and avoid redundancy.<br/>
 * <b>Example:</b><br/>
 * Assume you have the following {@link net.sf.mmm.util.entity.api.GenericEntity entities}:
 * <ul>
 * <li><code>Address</code> with attributes such as <code>city</code>, <code>zip</code>, etc.</li>
 * <li><code>ContactInfo</code> with attributes such as <code>phone</code>, <code>email</code>, etc.</li>
 * </ul>
 * Now if you would build the following {@link AbstractTransferObject}s:
 * <ul>
 * <li><code>AddressTo</code> with the attributes from <code>Address</code>.</li>
 * <li><code>ContactInfoTo</code> with the attributes from <code>ContactInfo</code>.</li>
 * <li><code>AddressWithContactInfoTo</code> with the attributes from both <code>Address</code> and
 * <code>ContactInfo</code>.</li>
 * </ul>
 * Due to the lack of multi-inheritance in java, the latter case is a problem. Therefore you would have to
 * duplicate the attributes from <code>Address</code> or <code>ContactInfo</code> (or both). <br/>
 * Therefore our recommendation is to solve this by creating the following {@link AbstractTransferObject}s:
 * <ul>
 * <li><code>AddressEto</code> extending {@link EntityTo} with the attributes from <code>Address</code>.</li>
 * <li><code>ContactInfoEto</code> extending {@link EntityTo} with the attributes from
 * <code>ContactInfo</code>.</li>
 * <li><code>AddressWithContactInfoCto</code> extending {@link CompositeTo} containing <code>AddressEto</code>
 * and <code>ContactInfoEto</code>.</li>
 * </ul>
 * If you separate {@link net.sf.mmm.util.entity.api.PersistenceEntity persistence entities} from
 * {@link AbstractTransferObject}s then <code>Address</code> and <code>ContactInfo</code> should be an interfaces that
 * centralize the JavaDoc for the attributes via the getter methods. Your {@link EntityTo} (e.g.
 * <code>AddressEto</code>) and {@link net.sf.mmm.util.entity.api.PersistenceEntity} (e.g.
 * <code>AddressEntity</code>) then both implement that interface (e.g. <code>Address</code>).<br/>
 * <b>NOTE:</b><br/>
 * Classes extending this class should carry the suffix <code>Cto</code>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class CompositeTo extends AbstractTransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = -5632071222008562903L;

  /**
   * The constructor.
   */
  public CompositeTo() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param template is the object to create a deep-copy from.
   */
  protected CompositeTo(Object template) {

    super(template);
  }

}
