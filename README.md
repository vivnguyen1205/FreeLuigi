# FreeLuigi - 2D Game Engine in Java

Welcome to the repository for **FreeLuigi**, a 2D game engine coded in Java. 

## To Run

1. You must have Gradle 6.3+ and Java 1.8+ installed. If you do not have these installed, you should install them and add them to your environment variables.
    * Once you have them installed you should be able to run:

        > ```gradle --version```

        and

        > ```java -version```
      
2. Open a Command Prompt in the projects root directory.
    * If you run ```ls``` (Mac, Linux) or ```dir``` (Windows) you should see ```libs```, ```src```, and ```assets``` listed as *part* of the output.
3. Run:
    >```gradle fatJar```
    * This will create a fat JAR containing all the dependencies for the project.
4. Run
    >```java -jar build/libs/mario-1.0-SNAPSHOT-all.jar```
    * This should open a new Window with the Mario game running.
    * NOTE: The ImGui Windows will probably be placed in very weird places. This is because I can't export default settings for ImGui, so you'll have to reposition the windows in the dock before it looks correct.
