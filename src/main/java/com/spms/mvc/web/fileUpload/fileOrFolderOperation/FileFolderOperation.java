package com.spms.mvc.web.fileUpload.fileOrFolderOperation;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public class FileFolderOperation {


    private static final String UPLOAD_DIRECTORY = "/resources/fileCenter";


    public boolean saveOperation(CommonsMultipartFile file, String fileNameToReplace, HttpSession session, Integer companyId) throws IOException {
        String uploadPath = session.getServletContext().getRealPath(UPLOAD_DIRECTORY);

        // Create the upload directory if it doesn't exist
        File uploadPathDirectory = new File(uploadPath, "companyId_" + companyId);
        if (!uploadPathDirectory.exists()) {
            uploadPathDirectory.mkdirs();
        }

        // Save the file with the new name inside the folder
        Path destinationPath = uploadPathDirectory.toPath().resolve(fileNameToReplace);

        // Use Java NIO to copy the file contents to the destination path
        try {
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
            return false;
        }
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
