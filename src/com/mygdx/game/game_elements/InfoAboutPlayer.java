package com.mygdx.game.game_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class InfoAboutPlayer {
    private static int level = 0;
    private static int ex = 0;
    private static int exNextLevel = 10;
    private static int moneyGlobal = 0;
    private static int moneyInGame = 0;
    private static Difficulty difficultyInGame = Difficulty.EASY;
    private static final ArrayList<Skills> skills = new ArrayList<>();
    private static String language;



    public static void loadOptions() {
        JsonValue fromJson = new JsonReader().parse(Gdx.files.internal("assets\\save\\save.json"));

        level = Integer.parseInt(fromJson.getString("level"));
        ex = Integer.parseInt(fromJson.getString("ex"));
        moneyGlobal = Integer.parseInt(fromJson.getString("moneyGlobal"));
        language = fromJson.getString("language");

        String[] skillsFromJson = fromJson.get("skills").asStringArray();
        System.out.println(skillsFromJson.length);

        for (int i = 0; i < skillsFromJson.length; i++) {
            if(skillsFromJson[i].equals(Skills.TOWER_FREEZE.toString())) skills.add(Skills.TOWER_FREEZE);
            if(skillsFromJson[i].equals(Skills.TOWER_LONG.toString())) skills.add(Skills.TOWER_LONG);
            if(skillsFromJson[i].equals(Skills.TOWER_TURRET.toString())) skills.add(Skills.TOWER_TURRET);
        }
    }

    public static int getLevel() {
        return level;
    }
    public static int getEx() {
        return ex;
    }
    public static int getExNextLevel() {
        return exNextLevel;
    }

    public static int getMoneyGlobal() {
        return moneyGlobal;
    }

    public static int getMoneyInGame() {
        return moneyInGame;
    }
    public static void setMoneyInGame(int value) {
        moneyInGame = value;
    }
    public static Difficulty getDifficultyInGame() {
        return difficultyInGame;
    }

    public static void setDifficultyInGame(Difficulty difficulty) {
        difficultyInGame = difficulty;
    }

    public static String getLanguage() {
        return language;
    }

    public static void addLevel(int value) {
        level += value;
    }
    public static void addEx(int value) {
        ex += value;
    }
    public static void setExNextLevel() {
        exNextLevel = 500;
        switch (level) {
            case 1:
                exNextLevel = 500;
                break;
            case 2:
                exNextLevel = 1000;
                break;
            case 3:
                exNextLevel = 2000;
                break;
            case 4:
                exNextLevel = 3000;
                break;
        }
    }

    public static void setLanguage(String name) {
        language = name;
    }
    public static void resetEx() {
        ex = 0;
    }

    public static void addMoneyGlobal(int value) {
        moneyGlobal += value;
    }

    public static void removeMoneyGlobal(int value) {
        moneyGlobal -= value;
    }

    public static void addMoneyInGame(int value) {
        moneyInGame += value;
    }

    public static void removeMoneyInGame(int value) {
        moneyInGame -= value;
    }
    public static ArrayList<Skills> getSkills() {
        return skills;
    }

    public static void addSkill(Skills skill) {
        for (Skills _skill : skills) {
            System.out.println(_skill + " = " + skill);
            if(_skill.equals(skill)) return;
        }
        System.out.println(true);
        skills.add(skill);
    }

    public static boolean hasSkill(Skills skill) {
        for (Skills _skill : skills) {
            if(_skill.equals(skill)) return true;
        }
        return false;
    }
}
