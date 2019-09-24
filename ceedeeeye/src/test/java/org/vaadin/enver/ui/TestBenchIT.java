package org.vaadin.enver.ui;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Base class for all our tests, allowing us to change the applicable driver,
 * test URL or other configurations in one place.
 */
public abstract class TestBenchIT extends TestBenchTestCase {

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this,
            true);

    @Before
    public void setUp() throws Exception {

        //Util.dumpSystemProperties();

        final String chrome = "webdriver.chrome.driver";

        setDriver(new ChromeDriver()); // System property 'webdriver.chrome.driver' is set from pom.xml, in the 'failsafe' plugin's configuration.
        String url = "http://127.0.0.1:8080/" + System.getProperty("root.route"); // similarly

        getDriver().get(url);
    }

}
