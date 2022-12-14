package com.mygdx.game.game_elements;

public class Base {

    private final int maxHealth;
    private int health;

    public Base() {
        this.maxHealth = 100;
        this.health = this.maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void removeHealth(int value) {
        this.health -= value;
    }
}
