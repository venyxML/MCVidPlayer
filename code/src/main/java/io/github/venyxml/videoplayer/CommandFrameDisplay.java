package io.github.venyxml.videoplayer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandFrameDisplay implements CommandExecutor, Listener {

    ImageUtil imUtil = new ImageUtil();
    ColourMap cm = new ColourMap();

    public static Player pubP;
    public static String[] pubArgs = new String[6];

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length != 6){
            return false;
        }

        pubP = p;
        pubArgs = args.clone();

        File key = new File("./plugins/depot/run.venyx");

        try {
            key.createNewFile();
        } catch (IOException e) {
            p.sendMessage("Unable to create start key, what happened here?");
            return false;
        }

        return true;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRedstoneUpdate(BlockRedstoneEvent e){

        if(new File("./plugins/depot/run.venyx").exists()){

            File RGB_data = new File("./plugins/depot/frame.txt");
            if(RGB_data.exists()){

                try (BufferedReader br = new BufferedReader(new FileReader(RGB_data))) {
                    String line;
                    ArrayList<Color> pixels = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");

                        //System.out.println(Arrays.toString(values));
                        if(values.length == 3){
                            Color RGB_pixel = new Color(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]));
                            pixels.add(RGB_pixel);
                        } else{
                            System.out.println("Frame dropped: Null or Corrupt Pixel");
                            return;
                        }
                    }

                    int xDif = Integer.parseInt(pubArgs[0]) - Integer.parseInt(pubArgs[3]);
                    int yDif = Integer.parseInt(pubArgs[1]) - Integer.parseInt(pubArgs[4]);
                    int zDif = Integer.parseInt(pubArgs[2]) - Integer.parseInt(pubArgs[5]);
                    int heading;

                    if(xDif == 0){
                        if(zDif < 0){ //facing east
                            heading = 0;
                        } else{ //facing west
                            heading = 1;
                        }
                    } else {
                        if(xDif < 0){ //north
                            heading = 2;
                        } else{ //south
                            heading = 3;
                        }
                    }
                    //System.out.println(pixels.size());
                    //System.out.println(Math.abs((xDif+1)*(yDif+1)));
                    //System.out.println(Math.abs((zDif+1)*(yDif+1)));

                    if((pixels.size() == ((Math.abs(xDif)+1)*(Math.abs(yDif)+1))) || (pixels.size() == ((Math.abs(zDif)+1)*(Math.abs(yDif)+1)))){
                        //System.out.println("SUCCESS");
                        cm.initializeMap();
                        placeBlocks(pixels, heading, pubArgs, pubP);
                    } else{
                        System.out.println("Frame Dropped: Too Few Pixels Received");
                        return;
                    }

                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    System.out.println("Frame Dropped: Unknown");
                }

            }
        }

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
