package com.tinkerpop.gremlin.structure.strategy;

import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Vertex;

/**
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class StrategyWrappedEdge extends StrategyWrappedElement<Edge> implements Edge, StrategyWrapped {
    private final Edge baseEdge;
    private final Strategy.Context<StrategyWrappedEdge> strategyContext;

    public StrategyWrappedEdge(final Edge baseEdge, final StrategyWrappedGraph strategyWrappedGraph) {
        super(baseEdge, strategyWrappedGraph);
        this.baseEdge = baseEdge;
        strategyContext = new Strategy.Context<>(this.strategyWrappedGraph.getBaseGraph(), this);
    }

    public Edge getBaseEdge() {
        return this.baseEdge;
    }

    @Override
    public Vertex getVertex(final Direction direction) throws IllegalArgumentException {
        return this.baseEdge.getVertex(direction);
    }
}
