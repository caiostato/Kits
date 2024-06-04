package br.com.me.util;

import org.bukkit.ChatColor;

public class StringUtil {

    public static String colorString(String str){

        StringBuilder finalReturn = new StringBuilder(ChatColor.BOLD + "");
        char[] letters = str.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            ChatColor cor = ChatColor.values()[i % ChatColor.values().length];
            finalReturn.append(cor).append(letters[i]);
        }
        return finalReturn.toString();
    }

}
