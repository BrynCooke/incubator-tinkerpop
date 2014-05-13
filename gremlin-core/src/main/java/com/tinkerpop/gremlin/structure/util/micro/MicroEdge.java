package com.tinkerpop.gremlin.structure.util.micro;

import com.tinkerpop.gremlin.structure.Direction;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.util.StringFactory;
import com.tinkerpop.gremlin.util.StreamFactory;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class MicroEdge extends MicroElement<Edge> implements Edge {

    MicroVertex outVertex;
    MicroVertex inVertex;

    private MicroEdge() {

    }

    private MicroEdge(final Edge edge) {
        super(edge);
        this.outVertex = MicroVertex.deflate(edge.getVertex(Direction.OUT));
        this.inVertex = MicroVertex.deflate(edge.getVertex(Direction.IN));
    }

    public Vertex getVertex(final Direction direction) {
        if (direction.equals(Direction.OUT))
            return outVertex;
        else if (direction.equals(Direction.IN))
            return inVertex;
        else
            throw Edge.Exceptions.bothIsNotSupported();
    }

    public String toString() {
        return StringFactory.edgeString(this);
    }

    public Edge inflate(final Vertex hostVertex) {
        return StreamFactory.stream((Iterator<Edge>) hostVertex.outE(this.label))
                .filter(e -> e.getId().equals(this.id))
                .findFirst().orElseThrow(() -> new IllegalStateException("The micro edge could not be be found at the provided vertex"));
    }

    public Edge inflate(final Graph graph) {
        return graph.e(this.id);
    }

    public static MicroEdge deflate(final Edge edge) {
        return new MicroEdge(edge);
    }
}
