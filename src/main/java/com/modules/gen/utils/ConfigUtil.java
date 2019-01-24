package com.modules.gen.utils;

import com.commons.utils.PropertiesLoader;
import com.modules.gen.entity.Configuration;
import com.modules.gen.entity.Configuration.Db;
import com.modules.gen.entity.Configuration.Path;

public class ConfigUtil {
	public static Configuration configuration;
	private static PropertiesLoader loader = new PropertiesLoader("generator.properties");
	static {
		configuration = new Configuration();
		configuration.setAuthor(loader.getProperty("Author"));
		Db db = new Db(loader.getProperty("url"), loader.getProperty("username"), loader.getProperty("password"));
		configuration.setDb(db);
		configuration.setPackageName(loader.getProperty("packageName"));
		Path pa = new Path(loader.getProperty("controller"), loader.getProperty("service"), loader.getProperty("dao"),
				loader.getProperty("entity"), loader.getProperty("mapper"));
		configuration.setPath(pa);
		configuration.setDbName(loader.getProperty("dbName"));
	}

}
