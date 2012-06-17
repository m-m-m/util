/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import javax.swing.JFrame;

import net.sf.mmm.util.process.base.ProcessUtilImpl;

/**
 * This is a swing application showing a frame but never exists. It is used to test, that child-processes of a
 * {@link Process} are carefully terminated by {@link ProcessUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SwingApp {

  public static void main(String[] args) {

    JFrame frame = new JFrame("Hello");
    frame.setSize(300, 300);
    frame.setVisible(true);
  }

}
