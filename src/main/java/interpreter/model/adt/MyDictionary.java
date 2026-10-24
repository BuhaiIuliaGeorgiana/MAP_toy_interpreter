package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V> {
    private final HashMap<K,V> hashMap = new HashMap<>();

    @Override
    public boolean isDefined(K Key) {
        for(Map.Entry<K,V> entry: hashMap.entrySet()){
            if(entry.getKey() == Key){
                return true;
            }
        }
        return false;
    }

    @Override
    public V lookup(K key) throws ADTException {
        if(!isDefined(key)) throw new ADTException("dict: lookup on an item that does not exist");
        for(Map.Entry<K,V> entry: hashMap.entrySet()){
            if(entry.getKey() == key){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) throws ADTException {
        if(isDefined(key)) throw new ADTException("dict: put a key that already exists");
        hashMap.put(key,value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if(!isDefined(key)) throw new ADTException("dict: update on an item that does not exist");
        for(Map.Entry<K,V> entry: hashMap.entrySet()){
            if(entry.getKey() == key){
                entry.setValue(value);
                return;
            }
        }
    }

    @Override
    public String toString(){
        return hashMap.toString();
    }
}
