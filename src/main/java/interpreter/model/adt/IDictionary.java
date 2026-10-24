package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IDictionary<K,V> {
    boolean isDefined(K Key);
    V lookup(K key) throws ADTException;
    void put(K key, V value) throws ADTException;
    void update (K key, V value) throws ADTException;


}
