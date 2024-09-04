package org.KikiCourier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void main_ShouldExecuteCommandsProvidedFromFileAndPrintOutput() {
        Main.main(new String[]{"src/test/resources/test_input.txt"});
        assertEquals("PKG1 0 750 3.99\n" +
                "PKG2 0 1475 1.78\n" +
                "PKG3 0 2350 1.42\n" +
                "PKG4 105 1395 0.85\n" +
                "PKG5 0 2125 4.20\n", outputStreamCaptor.toString());
    }

    @Test
    void main_ShouldPrintWarningLogsIfNoInputIsProvided() {
        Main.main(new String[]{});
        assertEquals("No input file provided.\n", outputStreamCaptor.toString());
    }

    @Test
    void main_ShouldPrintErrorIfAnyExceptionWasThrown() {
        String testInputPath = "src/test/resources/invalid_file.txt";
        Main.main(new String[]{testInputPath});
        assertEquals("SOMETHING_WENT_WRONG\n", outputStreamCaptor.toString());
    }
}