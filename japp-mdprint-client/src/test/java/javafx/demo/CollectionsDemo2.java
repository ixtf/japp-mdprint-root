package javafx.demo;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class CollectionsDemo2 {

    public static void main(String[] args) {

        // Use Java Collections to create the List.
        Map<String, String> map = new HashMap<String, String>();

        // Now add observability by wrapping it with ObservableList.
        ObservableMap<String, String> observableMap = FXCollections
                .observableMap(map);
        observableMap.addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
                System.out.println("Detected a change! ");
            }
        });

        // Changes to the observableMap WILL be reported.
        observableMap.put("key 1", "value 1");
        System.out.println("Size: " + observableMap.size());

        // Changes to the underlying map will NOT be reported.
        map.put("key 2", "value 2");
        System.out.println("Size: " + observableMap.size());

    }
}
