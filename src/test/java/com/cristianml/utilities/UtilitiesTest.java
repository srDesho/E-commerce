package com.cristianml.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilitiesTest {

    private Utilities utilities;

    @BeforeEach
    public void init() {
        this.utilities = new Utilities();
    }

    // saveFile method
    @Test
    public void testSaveFile_ValidImage() {
        // Mocking MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "filename.png", "image/png", "some image".getBytes());

        // Use a temporary path for testing
        String tempFile = System.getProperty("java.io.tmpdir") + File.separator;
        // Call the method
        String result = utilities.saveFile(mockFile, tempFile);

        // Perform assertions
        assertNotNull(result);
        assertTrue(result.endsWith(".png"));
    }

    @Test
    public void testSaveFile_InvalidImage() {
        // Mocking MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "filename.txt", "text/plain", "some image".getBytes());

        // Use a temporary path for testing
        String tempFile = System.getProperty("java.io.tmpdir") + File.separator;

        // Call the method
        String result = utilities.saveFile(mockFile, tempFile);

        // assertions
        assertEquals("no", result);
    }

    @Test
    public void testSaveFile_Exception() {
        // Mocking MultipartFile with a subclass to throw an exception
        MockMultipartFile mockFile = new MockMultipartFile("file", "filename.png", "image/png", "some image".getBytes()) {
            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                throw new IOException("Simulated IOException");
            }
        };

        // Use a temporary path for testing
        String tempFile = System.getProperty("java.io.tmpdir") + File.separator;

        // Call the method and assert that it handles the exception by returning null
        String result = this.utilities.saveFile(mockFile, tempFile);
        assertNull(result);
    }

    // validateImage method
    @Test
    public void testValidateImage() {
        assertTrue(utilities.validateImage("image/jpg"));
        assertTrue(utilities.validateImage("image/png"));
        assertTrue(utilities.validateImage("image/jpeg"));
        assertFalse(utilities.validateImage("image/gif"));
    }


    @Test
    public void testGetExtension() {
        // Test cases for valid MIME types
        assertEquals(".jpeg", utilities.getExtension("image/jpeg"));
        assertEquals(".jpg", utilities.getExtension("image/jpg"));
        assertEquals(".png", utilities.getExtension("image/png"));

        // Test case for an unknown MIME type (should return an empty string or handle gracefully)
        assertEquals("", utilities.getExtension("image/gif"));

        // Test case for an empty MIME type (should return an empty string or handle gracefully)
        assertEquals("", utilities.getExtension(""));

        // Test case for an unsupported MIME type (should return an empty string or handle gracefully)
        assertEquals("", utilities.getExtension("application/pdf"));
    }
    // getSlug method
    @Test
    public void testGetSlug() {
        assertEquals("getting-slug", utilities.getSlug("Getting slug"));
        assertEquals("hello-everyone", utilities.getSlug("Hello everyonE"));
        assertEquals("im-the-best", utilities.getSlug("I'm The Best"));
    }

    // numberFormat test
    @Test
    public void testNumberFormat() {
        assertEquals("100,000", utilities.numberFormat(100000));
        assertEquals("1,000,000", Utilities.numberFormat(1000000));
        assertEquals("0", Utilities.numberFormat(0));
    }
}