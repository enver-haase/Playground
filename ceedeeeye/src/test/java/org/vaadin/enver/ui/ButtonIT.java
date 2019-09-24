package org.vaadin.enver.ui;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.testbench.Parameters;
import com.vaadin.testbench.commands.TestBenchCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.vaadin.enver.util.Util;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

/**
 * Integration test for clinking the button on the main screen.
 */
@Slf4j
public class ButtonIT extends TestBenchIT {
    /**
     * We'll want to perform some additional setup functions, so we override the
     * setUp() method.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Set a fixed viewport size so the screenshot is always the same
        // resolution
        try {
            testBench().resizeViewPortTo(500, 400);
        } catch (UnsupportedOperationException unsupportedOperationException) {
            log.warn(unsupportedOperationException.getMessage());
        }

        // Define the directory for reference screenshots and for error files
        Parameters.setScreenshotReferenceDirectory("src/test/screenshots");
        Parameters.setScreenshotErrorDirectory("target/screenshot_errors");
    }

    @Test
    public void testButton() throws Exception {

        final String testName = "main-button";


        ButtonElement buttonElement = $(ButtonElement.class).waitForFirst();
        buttonElement.click();
        Util.trySleeping(2000);

        // Change this calculation after running the test once to see how errors
        // in screenshots are verified.
        // The output is placed in target/screenshot_errors, if not set differently in 'Parameters'.

        generateReferenceIfNotFound(testName);

        // Compare screen with reference image with id "oneplustwo" from the
        // reference image directory. Reference image filenames also contain
        // browser, version and platform.
        assertTrue(
                "Screenshot comparison for '" + testName + "' failed, see "
                        + Parameters.getScreenshotErrorDirectory()
                        + " for error images",
                testBench().compareScreen(testName));
    }

    /**
     * Generates a reference screenshot if no reference exists.
     * <p>
     * This method only exists for demonstration purposes. Normally you should
     * perform this task manually after verifying that the screenshots look
     * correct.
     *
     * @param referenceId the id of the reference image
     * @throws IOException
     */
    private void generateReferenceIfNotFound(String referenceId)
            throws IOException {
        String refName = ((TestBenchCommandExecutor) testBench())
                .getReferenceNameGenerator().generateName(referenceId,
                        ((HasCapabilities) getDriver()).getCapabilities());
        File referenceFile = new File(
                Parameters.getScreenshotReferenceDirectory(), refName + ".png");
        if (referenceFile.exists()) {
            return;
        }

        if (!referenceFile.getParentFile().exists()) {
            referenceFile.getParentFile().mkdirs();
        }

        File errorFile = new File(Parameters.getScreenshotErrorDirectory(),
                referenceFile.getName());

        // Take a screenshot and move it to the reference location
        testBench().compareScreen(referenceId);
        errorFile.renameTo(referenceFile);

        System.out.println("Created new reference file in " + referenceFile);

    }

}
