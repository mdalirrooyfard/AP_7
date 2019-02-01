package Network;

import Model.Items.Item;

import java.util.HashMap;

public class Market {
    private HashMap<String, Integer> items = new HashMap<>();

    public Market(){
        items.put("cagedGrizzly", 3);
        //todo fill this
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
