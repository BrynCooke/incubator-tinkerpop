package com.tinkerpop.gremlin.process.graph.filter

import com.tinkerpop.gremlin.process.T
import com.tinkerpop.gremlin.process.Traversal
import com.tinkerpop.gremlin.structure.Edge
import com.tinkerpop.gremlin.structure.Vertex

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
class GroovyHasTestG extends HasTest {

    public Traversal<Vertex, Vertex> get_g_v1_hasXprop(final Object v1Id, final String prop) {
        g.v(v1Id).has(prop)
    }

    public Traversal<Vertex, Vertex> get_g_v1_hasXname_markoX(final Object v1Id) {
        g.v(v1Id).has('name', 'marko')
    }

    public Traversal<Vertex, Vertex> get_g_V_hasXname_markoX() {
        g.V.has('name', 'marko')
    }

    public Traversal<Vertex, Vertex> get_g_V_hasXname_blahX() {
        g.V.has('name', 'blah')
    }

    public Traversal<Vertex, Vertex> get_g_V_hasXblahX() {
        g.V.has('blah')
    }

    public Traversal<Vertex, Vertex> get_g_v1_hasXage_gt_30X(final Object v1Id) {
        g.v(v1Id).has('age', T.gt, 30)
    }

    public Traversal<Vertex, Vertex> get_g_v1_out_hasXid_2X(final Object v1Id, final Object v2Id) {
        g.v(v1Id).out.has('id', v2Id)
    }

    public Traversal<Vertex, Vertex> get_g_V_hasXage_gt_30X() {
        g.V.has('age', T.gt, 30)
    }

    public Traversal<Edge, Edge> get_g_e1_hasXlabelXknowsX(final Object e1Id) {
        g.e(e1Id).has('label', 'knows')
    }

    public Traversal<Edge, Edge> get_g_E_hasXlabelXknowsX() {
        g.E.has('label', 'knows')
    }

    public Traversal<Edge, Edge> get_g_E_hasXlabelXknows_createdX() {
        g.E.has('label', T.in, ['knows', 'created'])
    }

}
