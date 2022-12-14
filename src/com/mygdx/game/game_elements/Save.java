package com.mygdx.game.game_elements;

import java.io.FileWriter;
import java.io.IOException;

public class Save {

    public static void save() {
        System.out.println("3");
        try(FileWriter writer = new FileWriter("assets\\save\\save.json", false))
        {
            System.out.println("2");
            String text = "{" +
                    " \"level\" : " + InfoAboutPlayer.getLevel() + "," +
                    " \"ex\" : " + InfoAboutPlayer.getEx() + "," +
                    " \"moneyGlobal\" : " + InfoAboutPlayer.getMoneyGlobal() + "," +
                    " \"language\" : \"" + InfoAboutPlayer.getLanguage() + "\"";
            System.out.println("4");
            for (int i = 0; i < InfoAboutPlayer.getSkills().size(); i++) {
                if(i == 0) text += ", \"skills\" : [";

                text += "\"" + InfoAboutPlayer.getSkills().get(i) + "\"";
                if(i + 1 < InfoAboutPlayer.getSkills().size()) text += ", ";

                if(i == InfoAboutPlayer.getSkills().size() - 1) text += "]";
                InfoAboutPlayer.getSkills().get(i);
            }
            text += "}";
            System.out.println("1");
            writer.write(text);
            writer.flush();
        }
        catch(IOException e) {System.err.println("mot file!");}
    }

}
