package com.tinkerpop.gremlin.process.graph.map;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.util.SOptional;

import java.util.function.Supplier;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PropertyValueStep<E, F extends Element> extends MapStep<Element<F>, E> {

    public String key;
    public SOptional<E> defaultValue;
    public SOptional<Supplier<E>> defaultSupplier;

    public PropertyValueStep(final Traversal traversal, final String key) {
        super(traversal);
        this.key = key;
        this.defaultValue = SOptional.empty();
        this.defaultSupplier = SOptional.empty();
        this.setFunction(holder -> holder.get().<E>getProperty(key).orElse((E) NO_OBJECT));
    }

    public PropertyValueStep(final Traversal traversal, final String key, final E defaultValue) {
        super(traversal);
        this.key = key;
        this.defaultValue = SOptional.of(defaultValue);
        this.defaultSupplier = SOptional.empty();
        this.setFunction(holder -> holder.get().<E>getProperty(key).orElse(this.defaultValue.get()));
    }

    public PropertyValueStep(final Traversal traversal, final String key, final Supplier<E> defaultSupplier) {
        super(traversal);
        this.key = key;
        this.defaultValue = SOptional.empty();
        this.defaultSupplier = SOptional.of(defaultSupplier);
        this.setFunction(holder -> holder.get().<E>getProperty(key).orElse(this.defaultSupplier.get().get()));
    }
}
