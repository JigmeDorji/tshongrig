package com.mvcSpring.util.libs.fileOrFolderOperation;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileFolderOperation {

    //    Save file
    public void fileSaveOperation(String uploadDirectory, int thresholdSize, CommonsMultipartFile file, HttpSession session) throws IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(thresholdSize);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletContext context = session.getServletContext();
        String uploadPath = context.getRealPath(uploadDirectory);
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + File.separator + file.getOriginalFilename())));
        stream.write(bytes);
        stream.flush();
        stream.close();
    }

    public int deleteThisDirectory(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath); //path to the directory
        Files
                .walk(dir) // Traverse the file tree in depth-first order
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        System.out.println("Deleting: " + path);
                        Files.delete(path);  //delete each file or directory
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return 1;
    }

}
