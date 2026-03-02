package io.github.scuba10steve.s3.ref;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RefStringsTest {

    @Test
    void modId() {
        assertEquals("s3", RefStrings.MODID);
    }

    @Test
    void modName() {
        assertEquals("Steve's Simple Storage", RefStrings.NAME);
    }

    @Test
    void modVersion() {
        assertEquals("2.5.0", RefStrings.VERSION);
    }
}
