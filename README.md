# MCVidPlayer
screengrabs and plays videos in minecraft

<b>! this is a very user-unfriendly plugin, don't try using unless you have some familiarity with coding !</b>

I may come revist the code to be much more customizeable and user-friendly, but as a full-time student, dedicating extra time is difficult without the financial support to incentivize it so I don't want to make any promises. There are much more optimized versions out there I'm sure, this was mostly just a project for funsies :)

## Versions Supported & Tested

*! denotes untested versions, please contact me if there's concerns

Java 1.16.5 (Tested with Paper-777)
```
VidPlayer-1.16.5r1.jar
```

<!-- GETTING STARTED -->
## How to Install & Use -- REQUIRES ABILITY TO RUN PYTHON SCRIPT

1. Download the appropriate .jar file for your version and place in your server's plugins folder* 
2. Download screen_grabber.py and put it in the directory of your server
3. Run your Spigot-based server

4. AFTER using the ```/vid-start``` command, run ```screen_grabber.py``` with Python 3

If working, the script will continue running until ```/vid-stop``` is run

5. Once working, frames will update depending on changes detected in any redstone signal detected on the server lmao

There are MUCH better ways of doing this, but I coded this a hot minute ago and I have since grown as a person. Not enough to go back and rewrite the code tho :p It'll screengrab a region of your computer's screen, which you can edit in the script. I currently have it set up to grab the upper left corner of my second monitor.

<i>*If you're new to github, just click the file and a download button should show up above it and to the right</i>

If you have questions beyond this, just DM me on TikTok or Twitter (listed at the bottom).

<!-- USAGE EXAMPLES -->
## In-Game Commands

```/vid-start <pos1 x,y,z> <pos2 xyz>```
Tells the plugin to start looking for frames to display, pos1 and pos2 define the area of the screen

<i>Example: /vid-start -200 167 -75 -72 71 -75 is what I used for the screen in <a href="https://www.tiktok.com/@im.venyx/video/7056385966524747054">this</a> TikTok</i>

```/vid-stop```
Stops the plugin from looking for frames.

<i>PLEASE use this when you're done playing around with the video, this can be a very taxing plugin</i>

<!-- LICENSE -->
## License

I haven't included an explicit license, but if you use this plugin or modify the source please do not sell it, use it malevolently, or claim the source as your own.

Credit not required, but nonetheless appreciated.

<!-- CONTACT -->
## Contact

<b>Twitter:</b> @venyxtalks
<b>TikTok:</b> @im.venyx

<b>Email:</b> TBA

