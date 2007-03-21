/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.sf.mmm.upnp.ssdp.api.SsdpRequest;
import net.sf.mmm.upnp.ssdp.api.SsdpSender;

/**
 * This is the implementation of the {@link SsdpSender} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SsdpSenderImpl implements SsdpSender {

  /** @see #setAddress(InetAddress) */
  private InetAddress address;

  /** the socket used to send the data */
  private MulticastSocket socket;

  /** the port used */
  private int port;

  /**
   * The constructor
   * 
   * @see #initialize()
   * @see #dispose()
   */
  public SsdpSenderImpl() {

    super();
    this.socket = null;
    this.address = null;
    this.port = SsdpRequest.MULTICAST_PORT;
  }

  /**
   * This method sets the <code>multicastAddress</code> where requests are
   * {@link #send(SsdpRequest) send} to. Only use this method to override the
   * default.
   * 
   * @see SsdpRequest#MULTICAST_ADDRESS
   * 
   * @param multicastAddress
   *        the address to set.
   */
  public void setAddress(InetAddress multicastAddress) {

    this.address = multicastAddress;
  }

  /**
   * This method sets the <code>multicastPort</code> where requests are
   * {@link #send(SsdpRequest) send} to. Only use this method to override the
   * default.
   * 
   * @see SsdpRequest#MULTICAST_PORT
   * 
   * @param multicastPort
   *        the port to set.
   */
  public void setPort(int multicastPort) {

    this.port = multicastPort;
  }

  /**
   * This method initializes this object.
   * 
   * @throws IOException
   *         if the operation failed with an I/O problem.
   */
  @PostConstruct
  public void initialize() throws IOException {

    if (this.address == null) {
      this.address = InetAddress.getByName(SsdpRequest.MULTICAST_ADDRESS);
    }
    if (this.socket == null) {
      //this.socket = new MulticastSocket(new InetSocketAddress(this.address, this.port));
      this.socket = new MulticastSocket();
      // default TTL
      this.socket.setTimeToLive(4);
    }
  }

  /**
   * This method frees resources allocated by this object.
   */
  @PreDestroy
  public void dispose() {

    this.socket.close();
    this.socket = null;
  }

  /**
   * {@inheritDoc}
   */
  public void send(SsdpRequest request) throws IOException {

    String message = request.toString();
    byte[] buffer = message.getBytes();
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.address, this.port);
    this.socket.send(packet);
  }

}
