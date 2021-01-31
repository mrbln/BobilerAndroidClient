package com.example.bobilerandroidclient;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {

    private static final String CONFIG_PROPERTIES = "config.properties";

    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(CONFIG_PROPERTIES);
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}
