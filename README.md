Fly-Payment
===========

**_Note: This project was my first major Java project started when I was a Freshman in High School. Hence, the coding practices, project page verbage, and overall design heavily reflects this!_**

Pay-To-Fly Java plugin built using the Bukkit API. This plugin is loaded on Minecraft Multiplayer servers.

Project Page: https://dev.bukkit.org/projects/fly-payment

# Description
Make flying cost something! When a player uses the fly command, it'll cost them a(n) item(s), and/or Economy Currency (whatever you specify in the config), and/or EXP to activate flying. Once they disable flying, it'll cost the player another set of item(s) and/or Economy Currency (whatever you specify in the config), and/or EXP to start flying again. Also, you can set a time limit for how long their flying is enabled until they must pay again. If you want another feature added, just say so in the comments.

Fly Payment 3.4 has been tested with CB 3074 (Development build for Minecraft 1.7.9) as well as Spigot 1449 (Development build for Minecraft 1.7.9)

# Features
- 2 Custom groups
- The ability to charge through Economy money (Like iConomy, BOSEconomy, etc.) or through items
- Edit what item and how much of that item to charge for Flying
- Edit how much EXP is charged
- Edit how long the flying will be enabled until the player must buy it again
- Edit how much money will be charged, including decimal values (if your economy plugin supports it)
- Edit type of fly mode
- Ban worlds that Fly Payment can be used in
- Timed Free Fly mode
- Fly ban system (ban on Fly Payment usage, not flying with any other plugin
- Combat Temporary Ban System - if a player engages in PvP, you can temp ban them from flying
- Custom messages
- When flying is disabled, edit whether the player takes damage from the fall

# Requirements
- Vault API (https://dev.bukkit.org/projects/vault)

(See project page for more information)
