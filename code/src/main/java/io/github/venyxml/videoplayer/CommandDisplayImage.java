package io.github.venyxml.videoplayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommandDisplayImage implements CommandExecutor {

    ImageUtil imUtil = new ImageUtil();
    ColourMap cm = new ColourMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long startTime = System.nanoTime();
        Player p = (Player) sender; // kinda risky to not test but for now it's fine
        //p.sendMessage("Attempting to grab image...");

        long convToArrStartTime,convToArrEndTime,  placeStartTime, placeEndTime, imStartTime, imEndTime;

        try {
            imStartTime = System.nanoTime();
            File ref = imUtil.saveToImage(args[6], "image.png");
            imEndTime = System.nanoTime();

            int xDif = Integer.parseInt(args[0]) - Integer.parseInt(args[3]);
            int yDif = Integer.parseInt(args[1]) - Integer.parseInt(args[4]);
            int zDif = Integer.parseInt(args[2]) - Integer.parseInt(args[5]);
            int heading;

            ArrayList<Color> convertedIm;

            //p.sendMessage("Attempting to convert to RGB array...");

            convToArrStartTime = System.nanoTime();
            if(xDif == 0){
                convertedIm = imUtil.convertToArr(ref, Math.abs(zDif), Math.abs(yDif));
                if(zDif < 0){ //facing east
                    heading = 0;
                } else{ //facing west
                    heading = 1;
                }
            } else {
                convertedIm = imUtil.convertToArr(ref, Math.abs(xDif), Math.abs(yDif));
                if(xDif < 0){ //north
                    heading = 2;
                } else{ //south
                    heading = 3;
                }
            }
            convToArrEndTime = System.nanoTime();

            //p.sendMessage("Initializing colour map...");
            cm.initializeMap();

            //p.sendMessage("Attempting to draw image...");

            placeStartTime = System.nanoTime();
            placeBlocks(convertedIm, heading, args, p);
            placeEndTime = System.nanoTime();

        } catch (IOException e) {
            p.sendMessage("Command failed, uh oh spaghetti o's. Go harass Venyx to fix it.");
            e.printStackTrace();
            return false;
        }

        p.sendMessage("Finished!");
        long endTime = System.nanoTime();

        long durationConvToArr = (convToArrEndTime - convToArrStartTime)/1000000;
        long durationPlace = (placeEndTime - placeStartTime)/1000000;
        long durationIm = (imEndTime - imStartTime)/1000000;
        long duration = (endTime - startTime)/1000000;

        p.sendMessage("Get Image: " + durationIm + "ms");
        p.sendMessage("Convert to RGB: " + durationConvToArr + "ms");
        p.sendMessage("Place Blocks: " + durationPlace + "ms");
        p.sendMessage("Total: " + duration + "ms");
        return true;
    }

    public void placeBlocks(ArrayList<Color> pixelColours, int heading, String[] args, Player p){
        int counter;
        World usedWorld = p.getWorld();
        //p.sendMessage(usedWorld.toString());
        switch(heading){
            case 0: //east
                counter = 0;
                //p.sendMessage("East");
                for(int z = Integer.parseInt(args[2]); z <= Integer.parseInt(args[5]); z++){
                    for(int y = Integer.parseInt(args[1]); y >= Integer.parseInt(args[4]); y--){
                        Location loc = new Location(p.getWorld(), Integer.parseInt(args[0]), y, z);
                        loc.getBlock().setType(cm.getMatch(pixelColours.get(counter),p));
                        counter++;
                    }
                }
                break;
            case 1: //west
                counter = 0;
                //p.sendMessage("West");
                for(int z = Integer.parseInt(args[2]); z >= Integer.parseInt(args[5]); z--){
                    for(int y = Integer.parseInt(args[1]); y >= Integer.parseInt(args[4]); y--){
                        Location loc = new Location(p.getWorld(), Integer.parseInt(args[0]), y, z);
                        loc.getBlock().setType(cm.getMatch(pixelColours.get(counter),p));
                        counter++;
                    }
                }
                break;
            case 2: //north
                counter = 0;
                //p.sendMessage("North");
                for(int x = Integer.parseInt(args[0]); x <= Integer.parseInt(args[3]); x++){
                    for(int y = Integer.parseInt(args[1]); y >= Integer.parseInt(args[4]); y--){
                        Location loc = new Location(p.getWorld(), x, y, Integer.parseInt(args[2]));
                        loc.getBlock().setType(cm.getMatch(pixelColours.get(counter), p));
                        counter++;
                    }
                }
                break;
            case 3: //south
                counter = 0;
                //p.sendMessage("South");
                //p.sendMessage("x:" + args[0] + ", y:" + args[1] + ", z:" + args[2]);
                //p.sendMessage(String.valueOf(Integer.parseInt(args[0])));
                for(int x = Integer.parseInt(args[0]); x >= Integer.parseInt(args[3]); x--){
                    for(int y = Integer.parseInt(args[1]); y >= Integer.parseInt(args[4]); y--){
                        //p.sendMessage("x:" + String.valueOf(x) + ", y:" + String.valueOf(y) + ", z:" + args[2]);
                        Location loc = new Location(usedWorld, x, y, Integer.parseInt(args[2]));
                        //p.sendMessage(loc.toString());
                        Material newMat = cm.getMatch(pixelColours.get(counter), p);
                        //p.sendMessage(newMat.toString());
                        loc.getBlock().setType(newMat);
                        counter++;
                    }
                }
                break;
            default:
                p.sendMessage("Something unexpected happened. No directions match? Where are you?");
                break;
        }
    }

}
