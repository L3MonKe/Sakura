package dev.sakura.lemonchat.client;

import com.google.gson.annotations.SerializedName;

public class DataLemon {
    @SerializedName("entityID")
    public int entityID;
    @SerializedName("name")
    public String name;
    @SerializedName("cape")
    public String cape;

    public DataLemon(int entityID, String name, String cape) {
        this.entityID = entityID;
        this.name = name;
        this.cape = cape;
    }

    @Override
    public String toString() {
        return "DataLemon{" +
                "entityID=" + entityID +
                ", name='" + name + '\'' +
                ", cape='" + cape + '\'' +
                '}';
    }
}
