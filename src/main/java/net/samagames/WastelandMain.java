package net.samagames;

import net.samagames.Listerner.CancelledEvent;
import net.samagames.Listerner.PlayerEvent;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.GamePlayer;
import net.samagames.entity.Turret;
import net.samagames.player.Team;
import net.samagames.player.TeamColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by werter on 04.03.2017.
 */
public class WastelandMain extends JavaPlugin {

    private Wasteland wasteland;

    @Override
    public void onEnable(){
        wasteland =  new Wasteland("gameCode", "Wasteland", "by Werter", GamePlayer.class, this);
        this.getServer().getPluginManager().registerEvents(new CancelledEvent(wasteland.getInstance()), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvent(wasteland.getInstance()),this);
        SamaGamesAPI.get().getGameManager().setFreeMode(true);
        SamaGamesAPI.get().getGameManager().registerGame(wasteland);
    }

    @Override
    public void onDisable() {
        for(Turret turret : getInstance ().getTeamBlue().getTurrets()) turret.disable();
        for(Turret turret : getInstance ().getTeamRed().getTurrets()) turret.disable();
    }

    public Wasteland getInstance() {return wasteland;}

    public WastelandMain getMain(){
        return this;
    }
}
