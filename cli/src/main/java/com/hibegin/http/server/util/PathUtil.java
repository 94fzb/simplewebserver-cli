package com.hibegin.http.server.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 提供给一些路径供程序更方便的调用
 *
 * @author Chun
 */
public class PathUtil {

    private static String ROOT_PATH = "";

    public static String getConfPath() {
        return getRootPath() + "/conf/";
    }

    public static String getRootPath() {
        if (ROOT_PATH != null && ROOT_PATH.length() > 0) {
            return ROOT_PATH;
        } else {
            String path;
            if (PathUtil.class.getResource("/") != null) {
                String tPath = PathUtil.class.getResource("/").getPath();
                try {
                    path = URLDecoder.decode(tPath, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    //e.printStackTrace();
                    path = tPath;
                }
                path = new File(path).getParentFile().getParentFile().toString();
            } else {
                path = System.getProperty("java.home");
            }
            return path;
        }
    }

    public static void setRootPath(String rootPath) {
        ROOT_PATH = rootPath;
    }

    public static String getConfFile(String file) {
        return getConfPath() + file;
    }

    public static String getStaticPath() {
        return getRootPath() + "/static/";
    }

    public static String getTempPath() {
        String str = getRootPath() + "/temp/";
        new File(str).mkdirs();
        return str;
    }
}