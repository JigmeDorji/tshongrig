package com.spms.mvc.web.fileUpload.services;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class FilePath {

    //    Public API to Get the FilePath
    public String filePath(ArrayList<String> resourcePath) throws URISyntaxException, IOException, XmlPullParserException {
        /*
         * type => 0 for development, 1 for production
         * serverType = > null for development,window for window server,linux for linux Server
         */
        EnvironmentType environmentType;
//        environmentType = new EnvironmentType(0, "development", "tshong-rig");
        environmentType = new EnvironmentType(1, "window", "tshong-rig");
//        environmentType = new EnvironmentType(2, "linux","tshong-rig");

        return filePathConfiguration(environmentType, resourcePath);//method =>1
    }


    //    method <=1
    private String filePathConfiguration(EnvironmentType type, ArrayList<String> resourcePaths) throws URISyntaxException, IOException, XmlPullParserException {
        String path = null;
        if (type.getType() == 0 && type.getServerType().equals("development")) {

            path = filePathDevelopmentServer(resourcePaths) + resourcesPathToString(resourcePaths);//method => 3;//method =>2
        }
        if (type.getType() == 1 && type.getServerType().equals("window")) {
            path = filePathForWindowServer(resourcePaths, type.getProjectName());
        }

//        if (type.getType() == 2 && type.getServerType().equals("linux")) {
//
//        }
        return path;
    }


    //method<=2
    //    Development Server File Path Configuration
    private String filePathDevelopmentServer(ArrayList<String> resourcePaths) throws URISyntaxException {
        String filePath = FilePath.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String[] filePaths = filePath.split("/");
        StringBuilder developmentPath = new StringBuilder();
        for (int i = 0; i < filePaths.length; i++) {
            if (i != 0 && !filePaths[i].equals("WEB-INF") && !filePaths[i].equals("classes")) {
                developmentPath.append(filePaths[i]).append(File.separator);
            }
        }
        return developmentPath.toString();
    }


    //    method<=3
    private String resourcesPathToString(ArrayList<String> paths) {
        StringBuilder pathToReturn = new StringBuilder();
        int lastIndex = paths.size() - 1;
        for (int i = 0; i < paths.size(); i++) {
            pathToReturn.append(paths.get(i));
            if (i != lastIndex) {
                pathToReturn.append(File.separator);
            }
        }
        return pathToReturn.toString();
    }


    //method 4
    private String filePathForWindowServer(ArrayList<String> resourcePaths, String projectName) throws XmlPullParserException, IOException, URISyntaxException {
        File rootFile = new File("");
        String separator = File.separator;
        String webappsPath = rootFile.getAbsolutePath() + separator + "webapps" + File.separator;
        return webappsPath + projectName + File.separator + resourcesPathToString(resourcePaths);
    }


}

class EnvironmentType {
    private final int type;
    private final String serverType;
    private final String projectName;

    public EnvironmentType(int type, String serverType, String projectName) {
        this.type = type;
        this.serverType = serverType;
        this.projectName = projectName;
    }

    public int getType() {
        return type;
    }

    public String getServerType() {
        return serverType;
    }

    public String getProjectName() {
        return projectName;
    }
}