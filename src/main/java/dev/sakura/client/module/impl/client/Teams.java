package dev.sakura.client.module.impl.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;

import java.util.Objects;

public class Teams extends Module {
    public Teams() {
        super("Teams", "团队", Category.Client);
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Scoreboard);

    public boolean isSameTeam(Entity player) {
        if (!Sakura.MODULES.getModule(Teams.class).isEnabled()) {
            return false;
        } else if (player instanceof PlayerEntity) {
            if (mode.is(Mode.Color)) {
                Integer c1 = player.getTeamColorValue();
                Integer c2 = mc.player.getTeamColorValue();
                return c1.equals(c2);
            } else {
                String playerTeam = getTeam(player);
                String targetTeam = getTeam(mc.player);
                return Objects.equals(playerTeam, targetTeam);
            }
        } else {
            return false;
        }
    }

    public String getTeam(Entity entity) {
        Scoreboard scoreboard = mc.getNetworkHandler().getScoreboard();
        if (scoreboard == null) {
            return null;
        } else {
            Team team = scoreboard.getTeam(entity.getName().getString());
            return team != null ? team.getName() : null;
        }
    }

    public enum Mode {
        Scoreboard,
        Color
    }
}