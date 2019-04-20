# User_Manager
Personal Project to build experience

## Configuring development environment
### For this configuring I will be using IntelliJ IDEA

**1**. Install JavaFX by following the instructions here:  https://openjfx.io/openjfx-doc1

**2**. Download the software for the Intellij IDEA website https://www.jetbrains.com/idea/

**3**. Clone thee repository and then import the project or import from version control.

**4**. Once the project is loaded go to "File -> Project Structure -> select the main folder in the user_manger folder -> under sources select java folder as the sources and the resources folder as the resources then save.

![](https://i.imgur.com/xxsxnAs.png)

**5**. Attempt to run the program 1 time from main  to generate the configurations then open the Run/Debug configurations panel.  Under VM actions enter the following:

`--module-path "path\to\java\fix\library\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml`

![](https://i.imgur.com/Ix5qdRm.png)

