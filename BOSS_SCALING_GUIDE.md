@@ .. @@
 # Boss Scaling Guide - Fallen God Testament
 
 ## 🏔️ Overview
 
-The Boss Scaling System ensures that all god bosses provide an appropriate challenge regardless of your server's difficulty setting. Bosses automatically detect the world difficulty and scale their health, damage, and abilities accordingly.
+The Boss Scaling System ensures that all god bosses, including the enhanced Ender Dragon guardian, provide an appropriate challenge regardless of your server's difficulty setting. Bosses automatically detect the world difficulty and scale their health, damage, and abilities accordingly.
 
 ## ⚙️ How Scaling Works
 
@@ -1,5 +1,5 @@
 ### **Practical Examples**
 
-### **Fallen Warden Boss on Different Difficulties**
+### **Enhanced Ender Dragon (Veil God Guardian) on Different Difficulties**
+
+#### **Peaceful Mode**
+- **Health**: 200 HP (50% of 400)
+- **Dragon Breath**: 3 breath clouds instead of 5, 3 damage instead of 6
+- **Veil Spawns**: 2-3 endermites instead of 4-5, reduced health
+- **Teleport Range**: 30 blocks instead of 50
+- **Abilities**: Every 30 seconds instead of 20
+
+#### **Hard Mode**
+- **Health**: 600 HP (150% of 400)
+- **Dragon Breath**: 7 breath clouds instead of 5, 9 damage instead of 6
+- **Veil Spawns**: 6-7 endermites instead of 4-5, enhanced health and Strength II
+- **Teleport Range**: 70 blocks instead of 50
+- **Abilities**: Every 15 seconds instead of 20
+- **Resurrected Form**: 900 HP with Strength IV
+
+### **Fallen Warden Boss on Different Difficulties**
 
 #### **Peaceful Mode**
 - **Health**: 250 HP (50% of 500)
@@ -1,5 +1,5 @@
 ### **Boss-Specific Adaptations**
 Each boss type applies scaling to their unique abilities:
 
+- **Enhanced Ender Dragon**: Breath cloud count, spawn numbers, teleport range, ability frequency
 - **Fallen Warden**: Soul drain range, death pulse radius, minion spawning
 - **Banishment Blaze**: Fireball count, meteor frequency, fire ring size
 - **Abyssal Guardian**: Tidal wave range, whirlpool strength, pressure damage
@@ -1,5 +1,5 @@
 ### **Admin Commands**
 - **`/boss info <god>`**: Shows current boss stats including scaling
 - **`/boss list`**: View all active bosses with their scaled health
+- **`/summondragon [player]`**: Spawn the enhanced Ender Dragon with automatic scaling
 - **Debug logs**: Enable in config for detailed scaling information
 
 ## 🎯 Best Practices