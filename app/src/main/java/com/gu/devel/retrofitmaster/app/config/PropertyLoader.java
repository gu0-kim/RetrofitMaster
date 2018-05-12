package com.gu.devel.retrofitmaster.app.config;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/** Created by devel on 2018/4/22. */
public class PropertyLoader {
  private static final String SERVER_CONFIG_FILE_NAME = "server_config.properties";
  private static final String KEY_URL = "url";

  public PropertyLoader() {}

  public String getServerBaseURL(Context context) {
    return getValue(context, KEY_URL);
  }

  private String getValue(Context context, String key) {
    Properties properties = getPropertyFile(context, SERVER_CONFIG_FILE_NAME);
    if (properties != null) return properties.getProperty(key);
    return null;
  }

  private Properties getPropertyFile(Context context, String configFileName) {
    try {
      Properties properties = new Properties();
      properties.load(context.getAssets().open(configFileName));
      return properties;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
