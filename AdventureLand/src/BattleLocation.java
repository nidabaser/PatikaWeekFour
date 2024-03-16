/*
 * @author Nida Başer
 * March 2024
 */

import java.util.Random;

public abstract class BattleLocation extends Location {
    private Enemy enemy;
    private String reward;
    private int maxEnemy;

    public BattleLocation(Player player, String locationName, Enemy enemy, String reward, int maxEnemy) {
        super(player, locationName);
        this.enemy = enemy;
        this.reward = reward;
        this.maxEnemy = maxEnemy;
    }

    @Override
    public boolean onLocation() {
        int enemyNumber = this.randomEnemyNumber();
        System.out.println("\n------>> You are now in the "
                + this.getLocationName()
                + ", YES ! There is "
                + this.getReward()
                + " ! But wait...");
        System.out.println("------>> Appeared "
                + enemyNumber + " "
                + this.getEnemy().getEnemyName()
                + " in front of you ! Be Careful "
                + this.getPlayer().getPlayersName() + " !");
        System.out.print("------>> <F>IGHT OR <R>UN: ");
        String selectToDo = s.nextLine().toUpperCase();
        if (selectToDo.equals("F") && combat(enemyNumber)) {
            System.out.println("You have defeat all enemies in the " + this.getLocationName());
            return true;
        }
        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("YOU DIED...");
            return false;
        }
        return true;
    }

    public boolean combat(int enemyNumber) {


        for (int i = 1; i <= enemyNumber; i++) {
            this.getEnemy().setEnemyHealth(this.getEnemy().getOriginalHealth());
            playerStats();
            enemyStats(i);

            while (this.getPlayer().getHealth() > 0 && this.getEnemy().getEnemyHealth() > 0) {
                System.out.print("------>> <H>IT or <R>UN: ");
                String selectCombat = s.nextLine().toUpperCase();
                if (selectCombat.equals("H")) {

                    // İlk hamle için %50 şans
                    double chance = Math.random() * 100;
                    if (chance > 50) {
                        System.out.println("\nYou hit !");
                        this.getEnemy().setEnemyHealth(this.getEnemy().getEnemyHealth() - this.getPlayer().getTotalDamage());
                        afterHit();

                        if (this.getEnemy().getEnemyHealth() > 0) {
                            System.out.println("\nEnemy hits you !");
                            int enemyDamage = this.getEnemy().getEnemyDamage() - this.getPlayer().getInventory().getArmor().getBlockDamage();
                            if (enemyDamage < 0) {
                                enemyDamage = 0;
                            }
                            this.getPlayer().setHealth(this.getPlayer().getHealth() - enemyDamage);
                            afterHit();
                        } else {
                            return true;
                        }
                    } else {
                        System.out.println("\nEnemy hits you !");
                        int enemyDamage = this.getEnemy().getEnemyDamage() - this.getPlayer().getInventory().getArmor().getBlockDamage();
                        if (enemyDamage < 0) {
                            enemyDamage = 0;
                        }
                        this.getPlayer().setHealth(this.getPlayer().getHealth() - enemyDamage);
                        afterHit();

                        if (this.getPlayer().getHealth() > 0) {
                            System.out.println("\nYou hit !");
                            this.getEnemy().setEnemyHealth(this.getEnemy().getEnemyHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                        }
                    }
                    if (this.getEnemy().getEnemyHealth() < this.getPlayer().getHealth()) {
                        System.out.println("You have defeat the enemy");

                        // YENİ SAVAŞ BÖLGESİNDEKİ DÜŞMANI ÖLDÜRÜNCE
                        if (this.getEnemy().getEnemyName().equals("Wendigo")) {
                            chanceForDropItems(); // ITEM DÜŞÜRME OLASILIKLARI İÇİN METOD
                        } else {
                            System.out.println("Your reward: " + this.getEnemy().getRewardCoins() + " coins");
                            this.getPlayer().setCoin(this.getPlayer().getCoin() + this.getEnemy().getRewardCoins());
                            System.out.println("Your current coins: " + this.getPlayer().getCoin());
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void afterHit() {
        System.out.println("Your Health: " + this.getPlayer().getHealth());
        System.out.println(this.getEnemy().getId() + ". " + this.getEnemy().getEnemyName() + " 's Health: " + this.getEnemy().getEnemyHealth());
    }

    public void playerStats() {
        System.out.println("Player Stats: \n");
        System.out.println("Health: " + this.getPlayer().getHealth());
        System.out.println("Damage: " + this.getPlayer().getTotalDamage());
        System.out.println("Coins: " + this.getPlayer().getCoin());
        System.out.println("Weapon: " + this.getPlayer().getInventory().getWeapon().getWeaponName());
        System.out.println("Armor: " + this.getPlayer().getInventory().getArmor().getArmorName());
        System.out.println("Block Damage: " + this.getPlayer().getInventory().getArmor().getBlockDamage());
    }

    public void enemyStats(int i) {
        System.out.println("\n" + i + ". " + this.getEnemy().getEnemyName() + " Stats: \n");
        System.out.println("Health: " + this.getEnemy().getEnemyHealth());
        System.out.println("Damage: " + this.getEnemy().getEnemyDamage());
        System.out.println("Rewards: " + this.getEnemy().getRewardCoins());
    }

    public int randomEnemyNumber() {
        Random rand = new Random();
        return rand.nextInt(this.getMaxEnemy()) + 1;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public String getReward() {
        return reward;
    }

    public int getMaxEnemy() {
        return maxEnemy;
    }
    // YENİ SAVAŞ BÖLGESİNDE DÜŞMAN ÖLDÜRÜNCE ITEM KAZANMA OLASILIKLARI İÇİN METOD
    public void chanceForDropItems() {
        Random rand = new Random();
        int randItem = rand.nextInt(1, 101);

        if (randItem <= 15) {
            int randWeapon = rand.nextInt(1, 101);

            if (randWeapon <= 20) {
                System.out.println("Enemy dropped Rifle !");
                this.getPlayer().getInventory().setWeapon(Weapon.getSelectedWeaponById(3));
            } else if (randWeapon <= 50) {
                System.out.println("Enemy dropped Sword !");
                this.getPlayer().getInventory().setWeapon(Weapon.getSelectedWeaponById(2));
            } else {
                System.out.println("Enemy dropped Gun !");
                this.getPlayer().getInventory().setWeapon(Weapon.getSelectedWeaponById(1));
            }

        } else if (randItem <= 30) {
            int randArmor = rand.nextInt(1, 101);

            if (randArmor <= 20) {
                System.out.println("Enemy dropped Heavy Armor !");
                this.getPlayer().getInventory().setArmor(Armor.getSelectedArmorById(3));
            } else if (randArmor <= 50) {
                System.out.println("Enemy dropped Medium Armor !");
                this.getPlayer().getInventory().setArmor(Armor.getSelectedArmorById(2));
            } else {
                System.out.println("Enemy dropped Light Armor !");
                this.getPlayer().getInventory().setArmor(Armor.getSelectedArmorById(1));
            }

        } else if (randItem <= 55) {

            int randCoins = rand.nextInt(1, 101);

            if (randCoins <= 20) {


                System.out.println("Enemy dropped 10 coins !");
                this.getPlayer().setCoin(this.getPlayer().getCoin() + 10);


            } else if (randCoins <= 50) {


                System.out.println("Enemy dropped 5 coins !");
                this.getPlayer().setCoin(this.getPlayer().getCoin() + 5);


            } else {

                System.out.println("Enemy dropped 1 coins !");
                this.getPlayer().setCoin(this.getPlayer().getCoin() + 1);
            }

            System.out.println("Your current coins: " + this.getPlayer().getCoin());
        } else {
            System.out.println("Nothing dropped from this enemy...");
        }


    }


}