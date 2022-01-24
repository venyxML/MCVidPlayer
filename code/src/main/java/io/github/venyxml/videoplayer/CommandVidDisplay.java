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

public class CommandVidDisplay implements CommandExecutor {
    ImageUtil imUtil = new ImageUtil();
    ColourMap cm = new ColourMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender; // kinda risky to not test but for now it's fine

        File key = new File("./plugins/depot/run.venyx");
        File frame = new File("./plugins/depot/frame.png");
        //key.getParentFile().mkdirs();

        try {
            key.createNewFile();
        } catch (IOException e) {
            p.sendMessage("Unable to create start key, what happened here?");
            return false;
        }

        while(key.exists()){
            if(frame.exists()){
                try{
                    frame = new File("./plugins/depot/frame.png");

                    System.out.println("DOES THE IMAGE EVEN EXIST?: " + frame.exists());
                    int xDif = Integer.parseInt(args[0]) - Integer.parseInt(args[3]);
                    int yDif = Integer.parseInt(args[1]) - Integer.parseInt(args[4]);
                    int zDif = Integer.parseInt(args[2]) - Integer.parseInt(args[5]);
                    int heading;

                    ArrayList<Color> convertedIm;

                    if(xDif == 0){
                        convertedIm = imUtil.convertToArr(frame, Math.abs(zDif), Math.abs(yDif));
                        if(zDif < 0){ //facing east
                            heading = 0;
                        } else{ //facing west
                            heading = 1;
                        }
                    } else {
                        convertedIm = imUtil.convertToArr(frame, Math.abs(xDif), Math.abs(yDif));
                        if(xDif < 0){ //north
                            heading = 2;
                        } else{ //south
                            heading = 3;
                        }
                    }

                    cm.initializeMap();

                    placeBlocks(convertedIm, heading, args, p);

                    frame.delete();

                } catch (IOException e) {
                    p.sendMessage("Command failed, uh oh spaghetti o's. Go harass Venyx to fix it.");
                    e.printStackTrace();
                    return false;
                }
            } //end if frame exists
            else {
                continue;
            }
        } //end while key exists

        return true;
    }

    public void placeBlocks(ArrayList<Color> pixelColours, int heading, String[] args, Player p){
        int counter;
        World usedWorld = p.getWorld();
        switch(heading){
            case 0: //east
                counter = 0;
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
