package utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigFile {

	private static final String defaultConfigFile = System.getProperty("user.dir") + "/src/test/resources/config/default.properties";
	
	public static Properties getProperties() {
		Properties configFile = null;
		try {
			configFile = new Properties();
			InputStream in = new FileInputStream(defaultConfigFile);
			configFile.load(in);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getCause());
			ex.printStackTrace();
		}
		return configFile;
	}
	
	public static String getProperty(String propertyName) {
		return getProperties().getProperty(propertyName);
	}

	public static boolean setProperties(Properties configFile, String name, String value) {
		try {
			OutputStream out = new FileOutputStream(defaultConfigFile);
			configFile.setProperty(name, value);
			configFile.store(out, null); //comment is optional
			return true;
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getCause());
			ex.printStackTrace();
			return false;
		}
	}

}
