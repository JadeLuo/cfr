package org.benf.cfr.reader.util.graph;

import org.benf.cfr.reader.util.ListFactory;
import org.benf.cfr.reader.util.SetFactory;
import org.benf.cfr.reader.util.functors.BinaryProcedure;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 13/03/2012
 * Time: 06:10
 * To change this template use File | Settings | File Templates.
 */
public class GraphVisitorDFS<T> implements GraphVisitor<T> {
    private final T start;
    private final Set<T> visited = SetFactory.newSet();
    private final BinaryProcedure<T, GraphVisitor<T>> callee;
    private final LinkedList<T> pending = ListFactory.newLinkedList();
    private final LinkedList<T> enqueued = ListFactory.newLinkedList();

    public GraphVisitorDFS(T first, BinaryProcedure<T, GraphVisitor<T>> callee) {
        this.start = first;
        this.callee = callee;
    }

    @Override
    public void enqueue(T next) {
        // These will be enqueued in the order they should be visited...
        enqueued.add(next);
    }

    @Override
    public void process() {
        pending.clear();
        enqueued.clear();
        pending.add(start);
        while (!pending.isEmpty()) {
            T current = pending.removeFirst();
            if (!visited.contains(current)) {
                visited.add(current);
                callee.call(current, this);
                // Prefix pending with enqueued.
                while (!enqueued.isEmpty()) pending.addFirst(enqueued.removeLast());
            }
        }

    }
}
