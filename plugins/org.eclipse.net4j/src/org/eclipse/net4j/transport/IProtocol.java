/***************************************************************************
 * Copyright (c) 2004-2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.transport;

/**
 * @author Eike Stepper
 */
public interface IProtocol extends IBufferHandler
{
  public static final String PRODUCT_GROUP_ID = "org.eclipse.net4j.protocol";

  public String getProtocolID();

  public IChannel getChannel();
}
