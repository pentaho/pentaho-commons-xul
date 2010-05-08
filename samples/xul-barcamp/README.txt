This Sample app includes main classes for Swing, Swt and GWT

Getting Started:

Running SWT and Swing demos
1. Link to your OS's SWT jar (found in eclipse or in one of our SWT based applications (Spoon, PME, etc)
2. Run the "Resolve" task from the build.xml file.
3. Run the "install-gwt-dev" task from the included gwt_common_build.xml file to install your platform's GWT jars
4. Add the jars to in lib, test-lib and dev-lib to your classpath.
5. Run one of the main classes!


PLEASE NOTE:
I'm having an issue with the IVY resolution of SWT platform jars. You may need to attach your own manually to the
classpath. If anyone finds a better Maven repo than ibiblio, please let me know!