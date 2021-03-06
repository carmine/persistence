/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence;

import javax.annotation.Nonnull;

/**
 * Manages the context a query is executed in. Such context provides everything the query needs to
 * execute (Database connection for example).
 * 
 * @param <C> type of the context provided to queries to enable execution
 * @author Robert Gagnon
 * @author Fabiel Zuniga
 */
public interface DataStore<C> {

    /**
     * Executes a query.
     * 
     * @param query query to execute
     * @return the query's result
     * @throws PersistenceException if persistence errors occur while executing the operation
     */
    <T> T execute(@Nonnull Query<T, C> query) throws PersistenceException;
}
