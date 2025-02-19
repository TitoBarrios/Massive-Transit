package com.titobarrios.services.JsonServices;

import com.google.gson.Gson;
import com.titobarrios.db.DB;

public class JsonService {
    private static Gson gson;

    public static void initialize() {
        gson = new Gson();
        readArchive();
        readDB();
    }

    public static void save() {
        writeArchive();
        writeDB();
    }

    public static void readArchive() {
        try {
            JsonArchiveContainer archiveContainer = gson.fromJson(DB.getPropCtrl().getValue("ArchiveJsonRoute"),
                    JsonArchiveContainer.class);
            archiveContainer.SynchronizeArchive();
        } catch (Exception e) {
        }
    }

    public static void readDB() {
        try {
            JsonDBContainer dbContainer = gson.fromJson(DB.getPropCtrl().getValue("DBJsonRoute"),
                    JsonDBContainer.class);
            dbContainer.SynchronizeDB();
        } catch (Exception e) {
        }
    }

    public static void writeArchive() {
        JsonArchiveContainer archiveContainer = new JsonArchiveContainer();
        archiveContainer.SynchronizeThis();
        gson.toJson(archiveContainer);
    }

    public static void writeDB() {
        JsonDBContainer dbContainer = new JsonDBContainer();
        dbContainer.SynchronizeThis();
        gson.toJson(dbContainer);
    }

}
