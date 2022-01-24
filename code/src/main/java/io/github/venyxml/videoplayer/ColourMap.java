package io.github.venyxml.videoplayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColourMap {
    public Map<Material, Color> colourmap = new HashMap<Material, Color>();

    public void initializeMap(){
        //wools
        colourmap.put(Material.BLACK_WOOL, new Color(20,11,25));
        colourmap.put(Material.BLUE_WOOL, new Color(53,57,157));
        colourmap.put(Material.BROWN_WOOL, new Color(114,71,40));
        colourmap.put(Material.CYAN_WOOL, new Color(21,137,145));
        colourmap.put(Material.GRAY_WOOL, new Color(62,68,71));
        colourmap.put(Material.GREEN_WOOL, new Color(84,109,27));
        colourmap.put(Material.LIGHT_BLUE_WOOL, new Color(58,175,217));
        colourmap.put(Material.LIGHT_GRAY_WOOL, new Color(142,142,134));
        colourmap.put(Material.LIME_WOOL, new Color(114,185,25));
        colourmap.put(Material.MAGENTA_WOOL, new Color(189,68,179));
        colourmap.put(Material.ORANGE_WOOL, new Color(240,118,19));
        colourmap.put(Material.PINK_WOOL, new Color(237,141,172));
        colourmap.put(Material.PURPLE_WOOL, new Color(121,42,172));
        colourmap.put(Material.RED_WOOL, new Color(160,39,34));
        colourmap.put(Material.WHITE_WOOL, new Color(233,236,236));
        colourmap.put(Material.YELLOW_WOOL, new Color(248,197,39));

        //concrete
        colourmap.put(Material.BLACK_CONCRETE, new Color(8,10,15));
        colourmap.put(Material.BLUE_CONCRETE, new Color(44,46,143));
        colourmap.put(Material.BROWN_CONCRETE, new Color(96,59,31));
        colourmap.put(Material.CYAN_CONCRETE, new Color(21,119,136));
        colourmap.put(Material.GRAY_CONCRETE, new Color(54,57,61));
        colourmap.put(Material.GREEN_CONCRETE, new Color(73,91,36));
        colourmap.put(Material.LIGHT_BLUE_CONCRETE, new Color(35,137,198));
        colourmap.put(Material.LIGHT_GRAY_CONCRETE, new Color(125,125,115));
        colourmap.put(Material.LIME_CONCRETE, new Color(94, 168, 24));
        colourmap.put(Material.MAGENTA_CONCRETE, new Color(169, 48, 159));
        colourmap.put(Material.ORANGE_CONCRETE, new Color(224, 97, 0));
        colourmap.put(Material.PINK_CONCRETE, new Color(213, 101, 142));
        colourmap.put(Material.PURPLE_CONCRETE, new Color(100, 31, 156));
        colourmap.put(Material.RED_CONCRETE, new Color(142, 32, 32));
        colourmap.put(Material.WHITE_CONCRETE, new Color(207, 213, 214));
        colourmap.put(Material.YELLOW_CONCRETE, new Color(240, 175, 21));

        //terracotta
        colourmap.put(Material.TERRACOTTA, new Color(152, 94, 67));
        colourmap.put(Material.BLACK_TERRACOTTA, new Color(37, 22, 16));
        colourmap.put(Material.BLUE_TERRACOTTA, new Color(74, 59, 91));
        colourmap.put(Material.BROWN_TERRACOTTA, new Color(77, 51, 35));
        colourmap.put(Material.CYAN_TERRACOTTA, new Color(86, 91, 91));
        colourmap.put(Material.GRAY_TERRACOTTA, new Color(57, 42, 35));
        colourmap.put(Material.GREEN_TERRACOTTA, new Color(76, 83, 42));
        colourmap.put(Material.LIGHT_BLUE_TERRACOTTA, new Color(113, 108, 137));
        colourmap.put(Material.LIGHT_GRAY_TERRACOTTA, new Color(135, 106, 97));
        colourmap.put(Material.LIME_TERRACOTTA, new Color(103, 117, 52));
        colourmap.put(Material.MAGENTA_TERRACOTTA, new Color(149, 88, 108));
        colourmap.put(Material.ORANGE_TERRACOTTA, new Color(161, 83, 37));
        colourmap.put(Material.PINK_TERRACOTTA, new Color(161, 78, 78));
        colourmap.put(Material.PURPLE_TERRACOTTA, new Color(118, 70, 86));
        colourmap.put(Material.RED_TERRACOTTA, new Color(143, 61, 46));
        colourmap.put(Material.WHITE_TERRACOTTA, new Color(209, 178, 161));
        colourmap.put(Material.YELLOW_TERRACOTTA, new Color(186, 133, 35));

        //more nuanced colours
        colourmap.put(Material.END_STONE, new Color(219, 222, 158));
        colourmap.put(Material.CUT_SANDSTONE, new Color(217,206,159));
        colourmap.put(Material.PACKED_ICE, new Color(141,180,250));
        colourmap.put(Material.PRISMARINE_BRICKS, new Color(99,171,158));
        colourmap.put(Material.DARK_PRISMARINE, new Color(51, 91, 75));
    }

    public Material getMatch(Color colour, Player p){
        double bestDif = Double.MAX_VALUE;
        double currDif;
        Material bestMaterial = Material.BEDROCK;

        /*
        * Weighted comparison, suggested by: https://stackoverflow.com/questions/1847092/given-an-rgb-value-what-would-be-the-best-way-to-find-the-closest-match-in-the-d
        * */
        for(Map.Entry<Material,Color> entry : colourmap.entrySet()){
            currDif = (Math.pow((entry.getValue().getRed()-colour.getRed())*0.30,2)
                    + Math.pow((entry.getValue().getGreen()-colour.getGreen())*0.59,2) 
                    + Math.pow((entry.getValue().getBlue()-colour.getBlue())*0.11,2));



            if(currDif < bestDif){
                bestDif = currDif;
                //p.sendMessage(String.valueOf(bestDif));
                bestMaterial = entry.getKey();
                //p.sendMessage(bestMaterial.name());
            }
        }
        return bestMaterial;
    } //end getMatch
}
