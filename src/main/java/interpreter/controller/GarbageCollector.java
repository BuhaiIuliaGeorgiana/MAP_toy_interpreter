package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector {

    private GarbageCollector() {}

    public static Set<Integer> getAddrFromSymTable(
            Collection<IValue> symTableValues) {

        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(
            Set<Integer> symTableAddr,
            Map<Integer, IValue> heap) {

        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public static Set<Integer> reachableAddresses(
            Set<Integer> roots,
            Map<Integer, IValue> heap) {

        Set<Integer> reachable = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>(roots);

        while (!queue.isEmpty()) {
            int address = queue.poll();

            if (!heap.containsKey(address)) {
                continue;
            }

            if (reachable.contains(address)) {
                continue;
            }

            reachable.add(address);

            IValue value = heap.get(address);

            if (value instanceof RefValue ref) {
                queue.add(ref.address());
            }
        }

        return reachable;
    }

    public static Map<Integer, IValue> safeGarbageCollector(
            Set<Integer> roots,
            Map<Integer, IValue> heap) {

        Set<Integer> reachable = reachableAddresses(roots, heap);

        return unsafeGarbageCollector(reachable, heap);
    }
}