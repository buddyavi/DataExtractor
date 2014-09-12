package com.usc.avi.util;



public class ApachePropertyWrapper {

	/**
     * Wrapper of java.util.Property.
     *
     * @param inputStr
     * @return
     */
    public static String getProperty(String inputStr) {
        return Init.config.getString(inputStr);
    }
}
