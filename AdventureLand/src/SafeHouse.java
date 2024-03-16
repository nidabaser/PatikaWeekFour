/**
 * @author Nida Başer
 * March 2024
 */
public class SafeHouse extends NormalLocation {
    public SafeHouse(Player player) {
        super(player, "Safe House");
    }

    public boolean onLocation() {
        System.out.println("YOU ARE IN SAFE HOUSE NOW.\nYOUR HEALTH IS REGENERATE.");
        this.getPlayer().setHealth(this.getPlayer().getOriginalHealth());
        return true;
    }
    public boolean finish(){
        if (this.getPlayer().getInventory().isFood() && this.getPlayer().getInventory().isWater() && this.getPlayer().getInventory().isFirewood()){
            System.out.println("\n" +
                    "         _________ _        _        _______  _______ \n" +
                    "|\\     /|\\__   __/( (    /|( (    /|(  ____ \\(  ____ )\n" +
                    "| )   ( |   ) (   |  \\  ( ||  \\  ( || (    \\/| (    )|\n" +
                    "| | _ | |   | |   |   \\ | ||   \\ | || (__    | (____)|\n" +
                    "| |( )| |   | |   | (\\ \\) || (\\ \\) ||  __)   |     __)\n" +
                    "| || || |   | |   | | \\   || | \\   || (      | (\\ (   \n" +
                    "| () () |___) (___| )  \\  || )  \\  || (____/\\| ) \\ \\__\n" +
                    "(_______)\\_______/|/    )_)|/    )_)(_______/|/   \\__/\n" +
                    "                                                      \n");
            return true;
        }
        return false;
    }

}
