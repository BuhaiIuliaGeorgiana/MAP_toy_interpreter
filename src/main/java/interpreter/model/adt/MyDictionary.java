package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V> {
    private final HashMap<K,V> hashMap = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return hashMap.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Key does not exist");

        return hashMap.get(key);
    }

    @Override
    public void put(K key, V value) throws ADTException {
        if (isDefined(key))
            throw new ADTException("Key already exists");

        hashMap.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Key does not exist");

        hashMap.put(key, value);
    }

    @Override
    public String toString(){
        return hashMap.toString();
    }
}
