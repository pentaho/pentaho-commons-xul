This Sample app includes main classes for Swing, Swt and GWT

Getting Started:

Running SWT and Swing demos
1. Link to your OS's SWT jar (found in eclipse or in one of our SWT based applications (Spoon, PME, etc)
2. Run the "Resolve" task from the build.xml file.
4. Add the jars to in lib and codegen-lib directories to your classpath.
5. Run one of the main classes!

Running GWT:
  Create a run target with the main class of:
    com.google.gwt.dev.DevMode
  with program arguments:
    -whitelist '^http[:][/][/]localhost[:]8888' -startupUrl GwtContactManager/contactManager.html org.pentaho.barcamp.GwtContactManager
  make sure that the "src" directory is added to your classpath.

PLEASE NOTE:
I'm having an issue with the IVY resolution of SWT platform jars. You may need to attach your own manually to the
classpath. If anyone finds a better Maven repo than ibiblio, please let me know!