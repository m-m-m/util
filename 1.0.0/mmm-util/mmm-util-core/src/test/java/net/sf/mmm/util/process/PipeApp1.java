/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process;

/**
 * This is a simple application to test a pipe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PipeApp1 {

  public static void main(String[] args) {

    for (int i = 10; i > 0; i--) {
      System.out.println(i);
    }
    System.err.println(PipeApp1.class.getSimpleName() + " done.");
  }

}
