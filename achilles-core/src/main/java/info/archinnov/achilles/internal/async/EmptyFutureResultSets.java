/*
 * Copyright (C) 2012-2014 DuyHai DOAN
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package info.archinnov.achilles.internal.async;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.datastax.driver.core.ResultSet;
import com.google.common.util.concurrent.ListenableFuture;

public class EmptyFutureResultSets implements ListenableFuture<List<ResultSet>> {

    private static final EmptyFutureResultSets INSTANCE = new EmptyFutureResultSets();

    public static EmptyFutureResultSets instance() {
        return INSTANCE;
    }

    /**
     * Use {@link #instance()} to get an instance.
     */
    private EmptyFutureResultSets() {
    }

    @Override
    public void addListener(Runnable listener, Executor executor) {
        executor.execute(listener);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public List<ResultSet> get() throws InterruptedException, ExecutionException {
        return Collections.emptyList();
    }

    @Override
    public List<ResultSet> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return Collections.emptyList();
    }
}
