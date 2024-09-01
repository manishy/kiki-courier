package org.KikiCourier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void mainShouldExecuteCommandsProvidedFromFile() {
        Main.main(new String[]{"src/test/resources/test_input1.txt"});
        assertEquals("PKG1 0 175\n" +
                "PKG2 0 275\n" +
                "PKG3 35 665\n", outputStreamCaptor.toString());
    }

    @Test
    void mainShouldPrintErrorIfAnyExceptionWasThrown() {
        String testInputPath = "src/test/resources/invalid_file.txt";
        Main.main(new String[]{testInputPath});
        assertEquals("SOMETHING_WENT_WRONG\n", outputStreamCaptor.toString());
    }
}