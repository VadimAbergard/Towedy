package com.mygdx.game.game_elements;

public enum Skills {
    TOWER_LONG,
    TOWER_FREEZE,
    TOWER_TURRET;

    @Override
    public String toString() {
        switch(this) {
            case TOWER_LONG: return "towerLong";
            case TOWER_FREEZE: return "towerFreeze";
            case TOWER_TURRET: return "towerTurret";
            default: throw new IllegalArgumentException();
        }
    }
}
