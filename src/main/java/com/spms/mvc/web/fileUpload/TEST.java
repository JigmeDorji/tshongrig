package com.spms.mvc.web.fileUpload;

public class TEST {

    public static void main(String[] args) {


        System.out.println(isFileExtensionDuplicateExists("as.jpg",".jpg"));

    }

    private static boolean isFileExtensionDuplicateExists(String fileName, String strFileExtension) {
        // Add a dot prefix to the file extension

        // Check if the file name ends with the given file extension
        if (fileName.endsWith(strFileExtension)) {
            // Extract the substring representing the file extension from the file name
            String extractedExtension = fileName.substring(fileName.lastIndexOf("."));
            // Compare the extracted file extension with the given file extension
            return extractedExtension.equalsIgnoreCase(strFileExtension); // The file extension already exists in the file name
        }

        return false; // The file extension does not exist in the file name
    }

}
