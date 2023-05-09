package hu.rivalsnetwork.rivalsislands.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import hu.rivalsnetwork.rivalsapi.storage.Storage;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Executor {
    private static final List<String> profileNames = Arrays.asList("Blueberry", "Mango", "Kiwi", "Lemon", "Watermelon", "Strawberry");

    public static String newIsland(Player player) {
        String name = profileNames.get(ThreadLocalRandom.current().nextInt(profileNames.size()));

        Storage.mongo(database -> {
            MongoCollection<Document> collection = database.getCollection("island-data");
            Document document = new Document();
            List<String> profiles = new ArrayList<>(20);
            document.put("uuid", player.getUniqueId());
            document.put("name", player.getName());

            Document searchQuery = new Document();
            searchQuery.put("uuid", player.getUniqueId());
            FindIterable<Document> cursor = collection.find(searchQuery);
            try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                if (cursorIterator.hasNext()) {
                    Document doc = cursorIterator.next();
                    profiles = doc.getList("islands", String.class);
                    profiles.add(name);
                    Document newDocument = new Document();
                    newDocument.put("islands", profiles);
                    newDocument.put("current-island", name);
                    Document updateObject = new Document();
                    updateObject.put("$set", newDocument);

                    collection.updateMany(searchQuery, updateObject);
                } else {
                    profiles.add(name);
                    document.put("islands", profiles);
                    document.put("current-island", name);
                    collection.insertOne(document);
                }
            }
        });
        return name;
    }

    public static String getCurrentIsland(Player player) {
        final String[] profile = {"Banana"};
        Storage.mongo(database -> {
            MongoCollection<Document> collection = database.getCollection("island-data");
            Document searchQuery = new Document();
            searchQuery.put("uuid", player.getUniqueId());
            FindIterable<Document> cursor = collection.find(searchQuery);

            try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                if (cursorIterator.hasNext()) {
                    Document doc = cursorIterator.next();
                    profile[0] = doc.getString("current-island");
                }
            }
        });

        return profile[0];
    }

    public static boolean hasIsland(Player player) {
        final boolean[] hasIsland = {false};
        Storage.mongo(database -> {
            MongoCollection<Document> collection = database.getCollection("island-data");
            Document searchQuery = new Document();
            searchQuery.put("uuid", player.getUniqueId());
            FindIterable<Document> cursor = collection.find(searchQuery);

            try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                if (cursorIterator.hasNext()) {
                    hasIsland[0] = true;
                }
            }
        });

        return hasIsland[0];
    }

    public static void deleteIslands(Player player) {
        Storage.mongo(database -> {
            MongoCollection<Document> collection = database.getCollection("island-data");
            Document searchQuery = new Document();
            searchQuery.put("uuid", player.getUniqueId());
            collection.deleteOne(searchQuery);
        });
    }
}
