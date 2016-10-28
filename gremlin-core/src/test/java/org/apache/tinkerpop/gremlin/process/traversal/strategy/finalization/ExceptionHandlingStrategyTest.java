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

import org.apache.tinkerpop.gremlin.process.traversal.Step;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategies;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.process.traversal.step.TraversalParent;
import org.apache.tinkerpop.gremlin.process.traversal.strategy.optimization.FilterRankingStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.util.DefaultTraversalStrategies;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.has;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by bryn on 28/10/16.
 */
@RunWith(Parameterized.class)
public class ExceptionHandlingStrategyTest {
    public static Iterable<Object[]> data() {
        return generateTestParameters();
    }

    @Parameterized.Parameter(value = 0)
    public Traversal original;

    @Test
    public void doTest() {
        final TraversalStrategies strategies = new DefaultTraversalStrategies();
        strategies.addStrategies(FilterRankingStrategy.instance());
        strategies.addStrategies(ExceptionHandlingStrategy.instance());

        this.original.asAdmin().setStrategies(strategies);
        this.original.asAdmin().applyStrategies();
        List<Step> steps = this.original.asAdmin().getSteps();
        assertEquals("Last step was not an exception handler " + this.original, ExceptionHandlingStrategy.ExceptionHandlingStep.class, steps.get(steps.size() - 1).getClass());
        assertEquals(1, steps.stream().filter(s->s instanceof ExceptionHandlingStrategy.ExceptionHandlingStep).count());
        steps.stream().filter(s->!(s instanceof ExceptionHandlingStrategy.ExceptionHandlingStep)).forEach(s->assertChildrenDontHaveExceptionSteps(s));

    }

    private void assertChildrenDontHaveExceptionSteps(Step step) {
        if(step instanceof TraversalParent) {
            ((TraversalParent) step).getLocalChildren().forEach(g->g.getSteps().forEach(s->assertChildrenDontHaveExceptionSteps(s)));
        }
        assertFalse("Multiple exception steps detected in:" + this.original.toString(), step instanceof ExceptionHandlingStrategy.ExceptionHandlingStep);

    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> generateTestParameters() {
        final Predicate testP = t -> true;
        return Arrays.asList(new Object[][]{
                {__.has("value", 0)},
                {__.dedup().has("value", 0).or(not(has("age")), has("age", 10)).has("value", 1)},
                {__.dedup().has("value", 0).or(not(has("age")), has("age", 10)).has("value", 1).profile()},
        });
    }
}
