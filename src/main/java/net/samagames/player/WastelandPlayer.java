package net.samagames.player;

import net.samagames.WastelandItem;
import net.samagames.api.games.GamePlayer;
import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by werter on 21.03.2017.
 */
public class WastelandPlayer extends GamePlayer {

    private Player player;
    private Entity armorStand;
    private ObjectiveSign scoreBoard;
    private Team team;
    private Kit kit;
    private int wheat;

    public WastelandPlayer(Player player){
        super(player);
        this.player = player;
        this.armorStand = player.getWorld().spawn(player.getLocation(),ArmorStand.class);
    }

    public void initArmorStand(){
        ((ArmorStand) this.armorStand).setSmall(true);
        ((ArmorStand) this.armorStand).setVisible(true);
        ((ArmorStand) this.armorStand).setBasePlate(false);
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.setInvulnerable(true);
        this.player.setPassenger(this.armorStand);
    }

    public Entity getArmorStand(){ return this.armorStand;}

    public ObjectiveSign getScoreBoard(){ return this.scoreBoard;}

    public void setScoreBoard(ObjectiveSign scoreBoard){
        this.scoreBoard = scoreBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public int getWheat(){
        return this.wheat;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean hasTeam(){
       return team != null;
    }

    public void setKit(Kit kit){
         this.kit = kit;
        player.sendMessage("Tu as pris le kit: " + kit.getName());
    }

    public Kit getKit(){ return this.kit;}

    public boolean isInTeam(TeamColor color){
        boolean isInTeam = false;
        if(hasTeam())
            if(team.getTeamColor().equals(color))
                isInTeam = true;
        return isInTeam;
    }


    public void openKitSelector(){
        Inventory inventory = Bukkit.createInventory(null, InventoryType.PLAYER, "Kit selector");
        for(WastelandItem wastelandItem : WastelandItem.values())
            if(!wastelandItem.isStarterItem())
                inventory.setItem(wastelandItem.getSlot(),wastelandItem.getItemStack());
        player.openInventory(inventory);
    }

    public void updateScoreBoard(){
        getScoreBoard().setLine(6,"Sur vous :" + getWheat());
        getScoreBoard().updateLines();
    }

    public void setWheat(int wheat) {
        this.wheat = wheat;

        this.armorStand.setCustomName(this.wheat + " blés");

        updateScoreBoard();

        player.setLevel(wheat);

        if(wheat == 50){
            player.sendMessage("Vous ne pouvez plus rammaser de blés");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,(float) 1 , (float) 1);
        }
    }

    public void addWheat(int number){
        setWheat(wheat + number);
        updateScoreBoard();

    }

}
