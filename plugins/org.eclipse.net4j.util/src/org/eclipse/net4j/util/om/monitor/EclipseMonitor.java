/***************************************************************************
 * Copyright (c) 2004 - 2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.util.om.monitor;

import org.eclipse.net4j.internal.util.om.monitor.RootMonitor;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Eike Stepper
 */
public final class EclipseMonitor extends RootMonitor
{
  private IProgressMonitor progressMonitor;

  private EclipseMonitor(IProgressMonitor progressMonitor)
  {
    this.progressMonitor = progressMonitor;
  }

  public IProgressMonitor getProgressMonitor()
  {
    return progressMonitor;
  }

  public static void startMonitoring(IProgressMonitor progressMonitor)
  {
    MONITOR.startMonitoring(new EclipseMonitor(progressMonitor));
  }

  public static void stopMonitoring()
  {
    MONITOR.stopMonitoring();
  }
}
