package com.ethpalser.data;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestDataReader {

    @Test
    void testRead_givenMissingFile_thenNull() {
        DataReader reader = new DataReader();
        TestData data = (TestData) reader.read(TestData.class, "/fileNotFound.txt");
        Assertions.assertNull(data);
    }

    @Test
    void testRead_givenNonJsonFormat_thenJsonSyntaxException() {
        DataReader reader = new DataReader();
        Assertions.assertThrows(JsonSyntaxException.class, () -> {
            reader.read(TestData.class, "resources/notTestData.txt");
        });
    }

    @Test
    void testRead_givenJsonFormat_thenObject() {
        DataReader reader = new DataReader();
        TestData data = (TestData) reader.read(TestData.class, "resources/testData.txt");
        Assertions.assertNotNull(data);
        // The following values are defined in the file
        Assertions.assertEquals("Ethan Palser", data.getName());
        Assertions.assertEquals(176, data.getHeight());
        Assertions.assertEquals(268.2, data.getWeight());
        Assertions.assertEquals(TestData.Employment.UNKNOWN, data.getEmployment());
        Assertions.assertTrue(data.isAvailable());
    }

}
