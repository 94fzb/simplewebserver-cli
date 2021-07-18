package com.hibegin.http.file.intercepter;


import com.hibegin.http.file.server.FileHttpServerApplication;
import com.hibegin.http.server.api.HttpRequest;
import com.hibegin.http.server.api.HttpResponse;
import com.hibegin.http.server.api.Interceptor;
import com.hibegin.http.server.util.PathUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileInterceptor implements Interceptor {


    @Override
    public boolean doInterceptor(HttpRequest httpRequest, HttpResponse httpResponse) {
        String basePath = new File(Objects.requireNonNullElse(FileHttpServerApplication.fileServerConfig.getOptions().getPath(), PathUtil.getRootPath())).getAbsolutePath();
        File file = new File(basePath + "/" + httpRequest.getUri());
        httpResponse.addHeader("Connection", "close");
        if (file.exists()) {
            if (httpRequest.getUri().endsWith("/") || file.isDirectory()) {
                int autoIndex = Objects.requireNonNullElse(FileHttpServerApplication.fileServerConfig.getOptions().getAutoIndex(), 0);
                if (autoIndex == 0) {
                    try {
                        httpResponse.renderHtmlStr(buildHtmlStr(file, basePath));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    httpResponse.renderCode(404);
                }
            } else {
                httpResponse.renderFile(file);
            }
        } else {
            httpResponse.renderCode(404);
        }

        return false;
    }

    private String buildHtmlStr(File file, String basePath) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n" + "<head><title>Index of ")
                .append(changeFileSplitUriPath(file.toString().substring(basePath.length())))
                .append("/</title></head>\n").append("<body bgcolor=\"white\">\n")
                .append("<h1>Index of ").append(changeFileSplitUriPath(file.toString().substring(basePath.length())))
                .append("/</h1><hr><pre><a href=\"../\">../</a>\n");
        File[] fileArr = file.listFiles();
        if (fileArr != null) {
            List<File> files = Arrays.asList(fileArr);
            sortFiles(files);
            for (File f : files) {
                appendFileInfo(sb, f, 48);
            }
        }
        sb.append("</pre><hr></body>\n").append("</html>\n");
        return sb.toString();
    }

    private String changeFileSplitUriPath(String file) {
        return file.replace("\\", "/");
    }

    private void appendFileInfo(StringBuilder sb, File f, int maxLength) throws UnsupportedEncodingException {
        String[] arr = fullBlankChar(f.getName(), maxLength);
        if (f.isDirectory()) {
            sb.append("<a href=\"").append(URLEncoder.encode(changeFileSplitUriPath(f.getName()), "UTF-8")).append("/\">").
                    append(arr[0]).append("/</a>").append(arr[1].subSequence(0, arr[1].length() - 1)).
                    append(formatDateTime(f.lastModified()))
                    .append("                   -\n");
        } else {
            sb.append("<a href=\"").append(URLEncoder.encode(changeFileSplitUriPath(f.getName()), "UTF-8")).append("\">")
                    .append(arr[0]).append("</a>").append(arr[1]).
                    append(formatDateTime(f.lastModified()))
                    .append("                   ").append(f.length()).append("\n");
        }
    }

    private static String[] fullBlankChar(String str, int maxLength) {
        String newStr = str;
        String[] newStrArr = new String[2];
        String blank = "";
        if (str.length() > maxLength) {
            newStr = newStr.substring(0, maxLength - 3) + "...";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = str.length(); i < maxLength; i++) {
                sb.append(" ");
            }
            blank = sb.toString();
        }
        newStrArr[0] = newStr;
        newStrArr[1] = blank + "   ";
        return newStrArr;
    }

    private static String formatDateTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }


    private static void sortFiles(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                //如果传进来的2个文件o1为文件夹 o2 不是文件时需要改变顺序
                return Long.compare(o2.lastModified(), o1.lastModified());
            }
        });
        Collections.sort(files, new Comparator<File>() {
            public int compare(File o1, File o2) {
                //如果传进来的2个文件o1为文件夹 o2 不是文件时需要改变顺序
                if (o1.isDirectory() && !o2.isDirectory())
                    return -1;
                //返回值>=0，则不调用Arrays.swap(Object x[], int a, int b) 方法。
                if (!o1.isDirectory() && o2.isDirectory())
                    return 1;
                return 0;
            }
        });
    }
}
