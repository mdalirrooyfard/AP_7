package Network;

import Model.Constants;

import java.io.Serializable;
import java.util.HashMap;

public class Market implements Serializable {
    private HashMap<String, Integer> items = new HashMap<>();

    public Market(){
        for (String kind : Constants.ITEM_NAMES)
            items.put(kind, 10);
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void add(String kind){
        if (items.keySet().contains(kind))
            items.replace(kind, items.get(kind)+1);
        else
            items.put(kind, 1);
    }

    public void remove(String kind){
        if (items.get(kind) > 1){
            items.replace(kind, items.get(kind)-1);
        }
        else
            items.remove(kind);
    }
}
