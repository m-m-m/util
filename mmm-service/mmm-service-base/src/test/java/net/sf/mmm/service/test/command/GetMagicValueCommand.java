/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.test.command;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;

/**
 * Simple command for void operation returning a magic string value.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GetMagicValueCommand implements RemoteInvocationCommand<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public GetMagicValueCommand() {

    super();
  }

}
