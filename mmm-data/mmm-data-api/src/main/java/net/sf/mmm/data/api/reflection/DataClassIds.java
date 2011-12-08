/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataClassIds {

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.DataObject}.
   */
  long ID_OBJECT = 1;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.DataNodeView}.
   */
  long ID_NODE = 2;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject}.
   */
  long ID_REFLECTIONOBJECT = 3;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.reflection.DataClass}.
   */
  long ID_CLASS = 4;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.reflection.DataField}.
   */
  long ID_FIELD = 4;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.DataSelectionGenericTree}.
   */
  long ID_SELECTIONLIST = 5;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.entity.DataEntity}.
   */
  long ID_ENTITY = 10;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.entity.resource.DataEntityResource}.
   */
  long ID_ENTITYRESOURCE = 11;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.entity.resource.DataFolder}.
   */
  long ID_FOLDER = 12;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.entity.DataEntityWithFile}.
   */
  long ID_ENTITYWITHFILE = 14;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * {@link net.sf.mmm.data.api.DataSelectionGenericTree}.
   */
  long ID_SELECTIONGENERICTREE = 5;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataCity</code>.
   */
  long ID_CITY = 101;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataCountry</code>.
   */
  long ID_COUNTRY = 102;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataAppointment</code>.
   */
  long ID_POSTALCODE = 103;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataEvent</code>.
   */
  long ID_EVENT = 105;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataAppointment</code>.
   */
  long ID_APPOINTMENT = 106;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataAddress</code>.
   */
  long ID_ADDRESS = 108;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataContactInfo</code>.
   */
  long ID_CONTACTINFO = 109;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataContact</code>.
   */
  long ID_CONTACT = 110;

  /**
   * {@link net.sf.mmm.data.api.reflection.DataClass#getId() Class ID} for
   * <code>DataContact</code>.
   */
  long ID_PERSON = 111;

}
