package com.srjons;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DockerImageCleanupApp {

  private static final String CMD_DOCKER_REMOVE_IMG = "cmd /k docker rmi ";
  private static final String CMD_DOCKER_FIND_IMAGES = "cmd /k docker images | findstr \"<none>\"";

  public static void main(String[] args) throws IOException {
    System.out.println("started docker-image-cleanup");
    executeCommand();
  }

  private static void executeCommand() throws IOException {
    Process process = Runtime.getRuntime().exec(DockerImageCleanupApp.CMD_DOCKER_FIND_IMAGES);
    InputStream inputStream = process.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String line = bufferedReader.readLine();
    List<String> imageIdList = new ArrayList<>();
    while (line != null && !line.equals("")) {
      String imageId = extractImageId(line);
      System.out.println("imageId = " + imageId);
      if (imageId != null && !imageId.equals("")) imageIdList.add(imageId);
      line = bufferedReader.readLine();
    }
    bufferedReader.close();
    inputStream.close();

    System.out.println("image count = " + imageIdList.size());
    System.out.println("imageIdList = " + imageIdList);

    for (String imageId : imageIdList) {
      cleanupImage(imageId);
    }
  }

  private static void cleanupImage(String imageId) throws IOException {
    System.out.println("Deleting imageId = " + imageId);
    Process process = Runtime.getRuntime().exec(CMD_DOCKER_REMOVE_IMG + imageId);
    InputStream inputStream = process.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    String line = bufferedReader.readLine();
    System.out.println(line);

    bufferedReader.close();
    inputStream.close();
  }

  private static String extractImageId(String line) {
    line = line.replaceAll("<none>", "");
    line = line.trim();
    String[] parts = line.split(" ");

    if (parts.length > 0) {
      return parts[0];
    }
    return null;
  }
}
