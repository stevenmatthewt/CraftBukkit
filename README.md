# CraftBukkit
This is our awesome Minecraft server!

# Running the server
There are a couple of jars you can choose from to run the server.
* craftbukkit-1.8.jar in the root folder.
* CraftBukkit/target/craftbukkit-1.8-R0.1-SNAPSHOT.jar (I think this is what we should be using)

# Changing Code and Compiling
* Make modifications to the source code. Primarily CraftBukkit/src/main/java.
* Run `mvn install` to compile your changes.
* Launch the jar! (described above)

# Plugins
Adding plugins is a relatively simple process:
* Copy your plugins to the CraftBukkit/target/plugins folder.
* Start the server. Or, if it's already running, do a reload.
