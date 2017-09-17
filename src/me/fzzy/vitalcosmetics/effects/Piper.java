package me.fzzy.vitalcosmetics.effects;

import me.fzzy.vitalcosmetics.Effect;
import me.fzzy.vitalcosmetics.EffectType;
import me.fzzy.vitalcosmetics.FzzyCosmetics;
import me.fzzy.vitalcosmetics.User;
import me.fzzy.vitalcosmetics.util.EffectInfo;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Piper implements Listener {

    public static EffectInfo getEffectInfo() {
        String configName = "piper";
        return EffectInfo.createNewEffectInfo("Piper",
                configName,
                Material.NOTE_BLOCK);
    }

    private Random random = new Random();
    private HashMap<UUID, Integer> tickCounter = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().clone().subtract(0, 0.001, 0).getBlock().getType().isSolid()) {
            User user = FzzyCosmetics.getUser(player);
            if (user.hasEffect(EffectType.PIPER)) {
                Effect effect = user.getEffect(EffectType.PIPER);
                if (effect.isEnabled()) {
                    if (event.getFrom().distanceSquared(event.getTo()) > 0.001) {
                        if (!tickCounter.containsKey(player.getUniqueId()))
                            tickCounter.put(player.getUniqueId(), 0);
                        int timePassed = tickCounter.get(player.getUniqueId());
                        if (timePassed > 5) {
                            double randomX = random.nextInt(10) / 10D;
                            double randomZ = random.nextInt(10) / 10D;
                            player.getWorld().spawnParticle(Particle.NOTE, event.getFrom().clone().add(randomX - 0.5D, 2.2, randomZ - 0.5D), 1);
                            tickCounter.put(player.getUniqueId(), timePassed - 5);
                        }
                        tickCounter.put(player.getUniqueId(), tickCounter.get(player.getUniqueId()) + 1);
                    }
                }
            }
        }
    }
}
