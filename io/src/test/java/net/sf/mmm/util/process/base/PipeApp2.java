/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a simple application to test a pipe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PipeApp2 {

  public static void main(String[] args) throws Exception {

    InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader bufferedReader = new BufferedReader(reader);
    String input = bufferedReader.readLine();
    List<Integer> list = new ArrayList<Integer>();
    while (input != null) {
      list.add(Integer.valueOf(input));
      input = bufferedReader.readLine();
    }
    Collections.sort(list);
    for (Integer integer : list) {
      System.out.println(integer);
    }
    System.err.println(PipeApp2.class.getSimpleName() + " done.");
  }

}
