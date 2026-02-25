package dev.sakura.lemonchat.client;

import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LemonList {
    @SerializedName("users")
    public List<DataLemon> lemons;

    public LemonList() {
        this(new ArrayList<>());
    }

    public LemonList(List<DataLemon> lemons) {
        this.lemons = lemons;
    }

    public void clear() {
        lemons.clear();
    }

    public void add(List<DataLemon> lemons) {
        this.lemons.addAll(lemons);
    }

    public void add(DataLemon... lemons) {
        this.lemons.addAll(Arrays.stream(lemons).toList());
    }

    public boolean has(int id) {
        return this.lemons.stream().filter(u -> u.entityID == id).findFirst().orElse(null) != null;
    }

    public boolean has(PlayerEntity e) {
        return has(e.getId());
    }

    public DataLemon get(PlayerEntity e) {
        return get(e.getId());
    }

    public DataLemon get(int id) {
        return this.lemons.stream().filter((u) -> u.entityID == id).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "LemonList{" +
                "lemons=" + lemons +
                '}';
    }
}
