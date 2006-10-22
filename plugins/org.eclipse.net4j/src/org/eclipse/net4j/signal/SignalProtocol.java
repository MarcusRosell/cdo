/***************************************************************************
 * Copyright (c) 2004, 2005, 2006 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.signal;

import org.eclipse.net4j.transport.Buffer;
import org.eclipse.net4j.transport.BufferProvider;
import org.eclipse.net4j.transport.Channel;
import org.eclipse.net4j.util.stream.BufferInputStream;
import org.eclipse.net4j.util.stream.BufferOutputStream;
import org.eclipse.net4j.util.stream.ChannelOutputStream;

import org.eclipse.internal.net4j.transport.AbstractProtocol;
import org.eclipse.internal.net4j.transport.BufferUtil;
import org.eclipse.internal.net4j.transport.ChannelImpl;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author Eike Stepper
 */
public abstract class SignalProtocol extends AbstractProtocol
{
  private ExecutorService executorService;

  private Map<Integer, Signal> signals = new ConcurrentHashMap();

  private int nextCorrelationID = 0;

  protected SignalProtocol(Channel channel, ExecutorService executorService)
  {
    super(channel);

    if (executorService == null)
    {
      throw new IllegalArgumentException("executorService == null");
    }

    this.executorService = executorService;
  }

  protected SignalProtocol(Channel channel)
  {
    this(channel, ((ChannelImpl)channel).getReceiveExecutor());
  }

  public void handleBuffer(Buffer buffer)
  {
    ByteBuffer byteBuffer = buffer.getByteBuffer();
    int correlationID = byteBuffer.getInt();
    System.out.println("Received buffer for signal " + correlationID);

    Signal signal = signals.get(correlationID);
    if (signal == null)
    {
      short signalID = byteBuffer.getShort();
      System.out.println("Got signal id " + signalID);

      signal = createSignalReactor(signalID);
      signal.setProtocol(this);
      signal.setCorrelationID(correlationID);
      signal.setInputStream(createInputStream());
      signal.setOutputStream(createOutputStream(correlationID, signalID));
      signals.put(correlationID, signal);
      executorService.execute(signal);
    }

    signal.getInputStream().handleBuffer(buffer);
  }

  @Override
  public String toString()
  {
    return "SignalProtocol[" + getProtocolID() + ", " + getChannel() + "]";
  }

  protected abstract SignalReactor createSignalReactor(short signalID);

  void startSignal(SignalActor signalActor)
  {
    if (signalActor.getProtocol() != this)
    {
      throw new IllegalArgumentException("signalActor.getProtocol() != this");
    }

    short signalID = signalActor.getSignalID();
    int correlationID = signalActor.getCorrelationID();
    signalActor.setInputStream(createInputStream());
    signalActor.setOutputStream(createOutputStream(correlationID, signalID));
    signals.put(correlationID, signalActor);
    signalActor.run();
  }

  void stopSignal(Signal signal)
  {
    int correlationID = signal.getCorrelationID();
    signals.remove(correlationID);
  }

  int getNextCorrelationID()
  {
    int correlationID = nextCorrelationID;
    if (nextCorrelationID == Integer.MAX_VALUE)
    {
      System.out.println(toString() + ": Correlation wrap around");
      nextCorrelationID = 0;
    }
    else
    {
      ++nextCorrelationID;
    }

    return correlationID;
  }

  BufferInputStream createInputStream()
  {
    return new BufferInputStream();
  }

  BufferOutputStream createOutputStream(int correlationID, short signalID)
  {
    return new SignalOutputStream(correlationID, signalID);
  }

  class SignalInputStream extends BufferInputStream
  {
    public SignalInputStream()
    {
    }
  }

  class SignalOutputStream extends ChannelOutputStream
  {
    public SignalOutputStream(final int correlationID, final short signalID)
    {
      super(getChannel(), new BufferProvider()
      {
        private BufferProvider delegate = BufferUtil.getBufferProvider(getChannel());

        private boolean firstBuffer = true;

        public short getBufferCapacity()
        {
          return delegate.getBufferCapacity();
        }

        public Buffer provideBuffer()
        {
          Buffer buffer = delegate.provideBuffer();
          ByteBuffer byteBuffer = buffer.startPutting(getChannel().getChannelID());

          System.out.println("Providing buffer for signal " + correlationID);
          byteBuffer.putInt(correlationID);
          if (firstBuffer)
          {
            System.out.println("Setting signal id " + signalID);
            byteBuffer.putShort(signalID);
            firstBuffer = false;
          }

          return buffer;
        }

        public void retainBuffer(Buffer buffer)
        {
          delegate.retainBuffer(buffer);
        }
      });
    }
  }
}
