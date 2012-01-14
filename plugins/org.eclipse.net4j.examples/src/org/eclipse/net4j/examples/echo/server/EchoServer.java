/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.examples.echo.server;

import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.internal.examples.bundle.OM;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class EchoServer
{
  public static void main(String[] args) throws Exception
  {
    // Send all traces and logs to the console
    OMPlatform.INSTANCE.setDebugging(true);
    OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);
    OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);

    // Use this container to create and wire the components
    IManagedContainer container = ContainerUtil.createContainer();
    Net4jUtil.prepareContainer(container);
    TCPUtil.prepareContainer(container);
    container.registerFactory(new EchoServerProtocol.Factory());
    container.activate();

    try
    {
      // Start an acceptor
      IAcceptor acceptor = (IAcceptor)container.getElement("org.eclipse.net4j.acceptors", "tcp", "0.0.0.0:2036"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      OM.LOG.info("Accepting connections: " + acceptor); //$NON-NLS-1$

      System.out.println("Press any key to shutdown"); //$NON-NLS-1$
      while (IOUtil.IN().read() == -1)
      {
        Thread.sleep(200);
      }
    }
    finally
    {
      LifecycleUtil.deactivate(container);
    }
  }
}
