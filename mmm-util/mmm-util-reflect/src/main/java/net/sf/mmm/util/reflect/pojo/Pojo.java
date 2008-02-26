package net.sf.mmm.util.reflect.pojo;

/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */

/**
 * This interface is only used for documentation of what is meant by the term
 * POJO:<br>
 * A <em>POJO</em> is a shortcut for <em>Plain Old Java Object</em> and
 * simply means any Java object containing or providing data. While the java
 * beans specification is generally a good idea to follow, it is sometimes too
 * restrictive. E.g. you might want to name a boolean getter with the prefix
 * "has" or want to have a primitive type as setter argument while the getter
 * has the according object type. A POJO is NOT limited by such restrictions.
 * However the utilities provided in the sub-packages, typically expect a POJO
 * to have a public non-arg constructor.<br>
 * <b>ATTENTION:</b><br>
 * Do NOT use this interface in your code. Its only purpose is to document the
 * meaning of the term POJO.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface Pojo {

}
