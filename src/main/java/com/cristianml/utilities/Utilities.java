package com.cristianml.utilities;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

// This class provides utility methods for various operations such as file upload,
// image validation, slug generation, and number formatting.

public class Utilities {
    // Method to save a file to a specified path
    // The method checks the file extension before saving it
    public static String saveFile(MultipartFile multiPart, String path) {
        // Check the file extension using the validateImage method
        if (Utilities.validateImage(multiPart.getContentType()) == false) {
            return "no";  // Does not save if validation fails
        } else {
            // If validation is successful, generate a unique name for the file using the current time
            long time = System.currentTimeMillis(); // Get the current time in milliseconds
            String name = time + Utilities.getExtension(multiPart.getContentType());
            try {
                // Create a File object with the path and file name
                File imageFile = new File(path + name); // Combine the path and file name
                // Save the file to the file system
                multiPart.transferTo(imageFile);
                return name;
            } catch (IOException e) {
                return null;  // Returns null if an exception occurs
            }
        }
    }

    // Method to validate if a file is an image
    // Checks the MIME type of the file to ensure it is an allowed image
    public static boolean validateImage(String mime) {
        boolean isValid = false;

        // Compare the MIME type with the allowed types
        switch (mime) {
            case "image/jpeg":
                isValid = true;
                break;
            case "image/jpg":
                isValid = true;
                break;
            case "image/png":
                isValid = true;
                break;
            default:
                isValid = false;
                break;
        }
        return isValid;  // Returns true if it is an allowed image, otherwise false
    }

    // Method to get the file extension based on the MIME type
    public static String getExtension(String mime) {
        String extension = "";
        // Assign the correct extension based on the MIME type
        switch (mime) {
            case "image/jpeg":
                extension = ".jpeg";
                break;
            case "image/jpg":
                extension = ".jpg";
                break;
            case "image/png":
                extension = ".png";
                break;
        }
        return extension;  // Returns the file extension
    }

    // Pattern for non-Latin characters
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    // Pattern for whitespace
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    // Pattern for leading or trailing dashes in the string
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    // Method to generate a slug from a text string
    // A slug is a URL-friendly string commonly used in SEO
    public static String getSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);  // Convert the slug to lowercase
    }

    // Method to format a number as a currency string
    public static final String numberFormat(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);  // Returns the number formatted as currency
    }
}