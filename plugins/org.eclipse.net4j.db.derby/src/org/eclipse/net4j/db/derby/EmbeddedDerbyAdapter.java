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
package org.eclipse.net4j.db.derby;

/**
 * A {@link DerbyAdapter Derby adapter} for <a href="http://db.apache.org/derby/papers/DerbyTut/embedded_intro.html">embedded</a> databases.
 *
 * @author Eike Stepper
 * @since 2.0
 */
public class EmbeddedDerbyAdapter extends DerbyAdapter
{
  public static final String NAME = "derby-embedded"; //$NON-NLS-1$

  public EmbeddedDerbyAdapter()
  {
    super(NAME);
  }
}
