/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tinkerpop.gremlin.process.traversal.strategy.finalization;

import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.AbstractStep;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.EmptyStep;
import org.apache.tinkerpop.gremlin.process.traversal.strategy.AbstractTraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.util.FastNoSuchElementException;

import java.util.NoSuchElementException;

/**
 * Adds a step to the end of the traversal that will convert any {@link FastNoSuchElementException}s to regular {@link NoSuchElementException}
 * Created by Bryn Cooke on 28/10/16.
 */
public class ExceptionHandlingStrategy<S> extends AbstractTraversalStrategy<TraversalStrategy.FinalizationStrategy> implements TraversalStrategy.FinalizationStrategy {

    private static final ExceptionHandlingStrategy INSTANCE = new ExceptionHandlingStrategy();


    @Override
    public void apply(Traversal.Admin<?, ?> traversal) {
        if(traversal.getParent() == EmptyStep.instance() && !traversal.asAdmin().getEndStep().equals(INSTANCE)) {
            traversal.addStep(new ExceptionHandlingStep<>(traversal));
        }
    }

    public static final class ExceptionHandlingStep<S, E> extends AbstractStep<S, E> {

        public ExceptionHandlingStep(Traversal.Admin<?, ?> traversal) {
            super(traversal);
        }

        @Override
        protected Traverser.Admin processNextStart() throws NoSuchElementException {
            try {
                return this.starts.next();
            } catch(FastNoSuchElementException e) {
                throw new NoSuchElementException();
            }
        }
    }

    public static ExceptionHandlingStrategy instance() {
        return INSTANCE;
    }
}
