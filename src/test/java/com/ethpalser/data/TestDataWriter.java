package com.ethpalser.data;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestDataWriter {

    @Test
    void testWrite_givenMissingFile_thenFileCreated() {
        TestData testData = new TestData.TestDataBuilder("Bruce Wayne")
                .height(188)
                .weight(210)
                .employment(TestData.Employment.FULL_TIME)
                .isAvailable(false)
                .build();
        String writePath = "resources/newTestData.txt";
        Saves.writeAsJson(testData, TestData.class, writePath);

        TestData writtenData = (TestData) Saves.readAsJson(TestData.class, writePath);
        Assertions.assertNotNull(writtenData);
        Assertions.assertEquals("Bruce Wayne", writtenData.getName());

        // Delete the file for future tests
        File file = new File(writePath);
        if(!file.delete()) {
            Assertions.fail();
            System.err.println("Failed to delete test file.");
        }
    }

    @Test
    void testWrite_givenExistingFile_thenFileOverwritten() {
        TestData testData = new TestData.TestDataBuilder("Batman")
                .height(188)
                .weight(210)
                .employment(TestData.Employment.FREELANCE)
                .isAvailable(true)
                .build();
        String writePath = "resources/testData2.txt";
        Saves.writeAsJson(testData, TestData.class, writePath);

        // Read what was written to file for assertions
        TestData writenData = (TestData) Saves.readAsJson(TestData.class, writePath);
        Assertions.assertNotNull(writenData);
        Assertions.assertEquals("Batman", writenData.getName());

        // Reset back to original
        TestData original = new TestData.TestDataBuilder("Bruce Wayne")
                .height(188)
                .weight(210)
                .employment(TestData.Employment.FULL_TIME)
                .isAvailable(false)
                .build();
        Saves.writeAsJson(original, TestData.class, writePath);
    }

    @Test
    void testWrite_givenNullObject_thenJsonException() {
        String writePath = "resources/testData3.txt";
        Saves.writeAsJson(null, TestData.class, writePath);

        TestData writenData = (TestData) Saves.readAsJson(TestData.class, writePath);
        Assertions.assertNull(writenData);
    }

}
