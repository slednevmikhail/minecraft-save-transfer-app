# <p align="center">Minecraft save transfer GUI</p>
<ins> </ins>
### <p align="center">A save file manager for Minecraft.
<div align="center">
<img alt="Static Badge" src="https://img.shields.io/badge/Releases-red?link=releases">
<img alt="Static Badge" src="https://img.shields.io/badge/User-Guide-blue?link=releases">
</div>
<p> </p>
<p align="center">Mst-gui lets you easily transfer your player items, exp, and all other stats between single- and multiplayer save files in a few clicks.</p>




# <p align="center">Demonstration</p>
https://github.com/user-attachments/assets/c6328398-e891-49ac-a810-96b26146d5df

### This is an example of transfering player data from world 2 into world 1!

# <p align="center">User Guide</p>
<div align="center">
        <img src="https://github.com/user-attachments/assets/4e38af88-380e-49c0-8c2d-c43dfd398baa">
</div>

In the application, you can see two fields: “To” and ‘From’. The “From” field is used to select the file from which you want to transfer the save. The “To” field is used to select the file to which you want to transfer the save. For example, let's analyze a situation:
        There are two worlds - world #1 and world #2. In world #1 your character has an empty inventory, and in world #2 your character has an inventory full of items. You want to transfer these items from world #2 to world #1. To do this, you follow the steps below:
1) Open mstgui.exe (see [FAQ/SmartScreen](#faq))
2) Select the “SinglePlayer world (selected by default) option in the ‘From’ block. 
3) Next to the text box with the file location is the “View” key, press it. 
4) Locate world folder #2. It is usually located in “C:\Users\%UserName%\AppData\Roaming\.minecraft\saves”. <br>
 For simplicity, I recommend creating a .lnk shortcut to this folder, and placing it in the root folder of the application.
5) In world folder #2 select the world data file (see [FAQ/File-types](#faq)), in our case it is level.dat. 
6) Repeat the process with world file #1.
7) After selecting both files, you should see a green text with “Player_UUID:” and a set of numbers below the file location field on your screen. If so, proceed to the next step.
8) You can choose a name for the backup to make it easier to find later. By default, all backups are stored in the root folder of the application in the Output folder (see [FAQ/Backup](#faq)). You can quickly open this folder by clicking the button with the folder icon next to the “Transfer” button.
9) Press “Transfer”. Done! Go to world #1, now your character in it is an exact copy of the character from world #2!

# <p align="center">FAQ<p>
 > <p align="center">Why does smartscreen say this app is not safe?<p>
_Because this application is built by me in jpackage. It is open source and you can check or build it yourself._

> <p align="center">What is the difference between Multiplayer character and Singleplayer character, how to distinguish them?</p>
_**SinglePlayer world** - single-player game files.  They are stored in the “.minecraft\saves\worldName” format in the level.dat file. Usually, in addition to player data, they also contain data about other creatures, game events, and other world details. <br>
**Multiplayer character** files - multiplayer game files. They are stored in the “.minecraft\saves\worldName\playerdata” folder. Their name is the UUID of the users they belong to. However, my program involves transferring between any type of save file._
> <p align="center">What if I want to roll back changes, how is the backup system organized?</p>
_**All files** that you replace during migration are saved in the root folder of the application in the <br>`{Output\{save-time}` <br>
You can also enter a backup name in the “choose backup name” field in the application. This name will be before “save-time”, like <br> `\Output\{{backup-name}\_{save-time}`. </br>
It is worth mentioning that backups of player files are located in the playerdata folder._
# <p align="center">How?<p>
It is done by parsing both of the .dat files with the usage of [Jnbt Library](https://github.com/Morlok8k/JNBT), then overriding the second, "To" file with the playerData of "From" file.
This can be done with /playerdata files, with level.dat files, basically with any kind of minecraft save files.

# <p align="center">Why?<p>
I feel like it could be useful in a very specific case, but mainly i developed the save-transfer module for a "minecraft-version-control", and then just got an idea to add GUI.
<p> Minecraft Version Control is the mod i am working on, it will allow players to play offline - coop with version-controlling the world file.


