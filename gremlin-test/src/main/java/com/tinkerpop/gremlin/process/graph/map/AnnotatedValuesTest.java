package com.tinkerpop.gremlin.process.graph.map;

import com.tinkerpop.gremlin.AbstractGremlinTest;
import com.tinkerpop.gremlin.LoadGraphWith;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.structure.AnnotatedValue;
import com.tinkerpop.gremlin.structure.FeatureRequirement;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.util.StreamFactory;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.tinkerpop.gremlin.LoadGraphWith.GraphData.MODERN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public abstract class AnnotatedValuesTest extends AbstractGremlinTest {

    public abstract Traversal<Vertex, AnnotatedValue<String>> get_g_v1_annotatedValuesXlocationsX_intervalXstartTime_2004_2006X(final Object v1Id);

    public abstract Traversal<Vertex, String> get_g_V_annotatedValuesXlocationsX_hasXstartTime_2005X_value();

    @Test
    @LoadGraphWith(MODERN)
    @FeatureRequirement(featureClass = Graph.Features.VertexAnnotationFeatures.class, feature = Graph.Features.VertexAnnotationFeatures.FEATURE_STRING_VALUES)
    public void g_v1_annotatedValuesXlocationsX_intervalXstartTime_2004_2006X() {
        final Iterator<AnnotatedValue<String>> step = get_g_v1_annotatedValuesXlocationsX_intervalXstartTime_2004_2006X(convertToId("marko"));
        System.out.println("Testing: " + step);
        final List<AnnotatedValue<String>> locations = StreamFactory.stream(step).collect(Collectors.toList());
        assertEquals(2, locations.size());
        locations.forEach(av -> assertTrue(av.getValue().equals("brussels") || av.getValue().equals("santa fe")));
    }

    @Test
    @LoadGraphWith(MODERN)
    @FeatureRequirement(featureClass = Graph.Features.VertexAnnotationFeatures.class, feature = Graph.Features.VertexAnnotationFeatures.FEATURE_STRING_VALUES)
    public void g_V_annotatedValuesXlocationsX_hasXstartTime_2005X_value() {
        final Iterator<String> step = get_g_V_annotatedValuesXlocationsX_hasXstartTime_2005X_value();
        System.out.println("Testing: " + step);
        final List<String> locations = StreamFactory.stream(step).collect(Collectors.toList());
        assertEquals(2, locations.size());
        locations.forEach(location -> assertTrue(location.equals("kaiserslautern") || location.equals("santa fe")));
    }

    public static class JavaAnnotatedValuesTest extends AnnotatedValuesTest {

        public Traversal<Vertex, AnnotatedValue<String>> get_g_v1_annotatedValuesXlocationsX_intervalXstartTime_2004_2006X(final Object v1Id) {
            return g.v(v1Id).<Vertex, Vertex>annotatedValues("locations").interval("startTime", 2004, 2006);
        }

        public Traversal<Vertex, String> get_g_V_annotatedValuesXlocationsX_hasXstartTime_2005X_value() {
            return g.V().annotatedValues("locations").has("startTime", 2005).value();
        }
    }

    public static class JavaComputerAnnotatedValuesTest extends AnnotatedValuesTest {

        public Traversal<Vertex, AnnotatedValue<String>> get_g_v1_annotatedValuesXlocationsX_intervalXstartTime_2004_2006X(final Object v1Id) {
            return g.v(v1Id).<Vertex, Vertex>annotatedValues("locations").<AnnotatedValue<String>>interval("startTime", 2004, 2006).submit(g.compute());
        }

        public Traversal<Vertex, String> get_g_V_annotatedValuesXlocationsX_hasXstartTime_2005X_value() {
            return g.V().annotatedValues("locations").has("startTime", 2005).<String>value().submit(g.compute());
        }
    }
}
