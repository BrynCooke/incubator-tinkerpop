package com.tinkerpop.gremlin.process.traverser;

import com.tinkerpop.gremlin.process.Step;
import com.tinkerpop.gremlin.process.traverser.util.AbstractPathTraverser;
import com.tinkerpop.gremlin.process.util.ImmutablePath;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class B_O_P_PA_S_SE_SL_Traverser<T> extends AbstractPathTraverser<T> {

    private final static Set<Component> COMPONENTS = new HashSet<>(Arrays.asList(Component.OBJECT, Component.STEP_ID, Component.BULK, Component.LOOPS));

    protected B_O_P_PA_S_SE_SL_Traverser() {
    }

    public B_O_P_PA_S_SE_SL_Traverser(final T t, final Step<T, ?> step) {
        super(t, step);
        final Optional<String> stepLabel = step.getLabel();
        this.path = stepLabel.isPresent() ?
                ImmutablePath.make().extend(t, stepLabel.get()) :
                ImmutablePath.make().extend(t);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.path.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        return (object instanceof B_O_P_PA_S_SE_SL_Traverser)
                && ((B_O_P_PA_S_SE_SL_Traverser) object).path().equals(this.path) // TODO: path equality
                && ((B_O_P_PA_S_SE_SL_Traverser) object).get().equals(this.t)
                && ((B_O_P_PA_S_SE_SL_Traverser) object).getStepId().equals(this.getStepId())
                && ((B_O_P_PA_S_SE_SL_Traverser) object).loops() == this.loops()
                && (null == this.sack);
    }

    @Override
    public Set<Component> getComponents() {
        return COMPONENTS;
    }
}
