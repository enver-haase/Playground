
package org.vaadin.enver;

import org.junit.*;

public class HelloWorldTest {

    @BeforeClass
    public static void beforeAll() {
        System.out.println("Before all");
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("After all");
    }

    @Before
    public void beforeEach() {
        System.out.println("Before each test");
    }

    @After
    public void afterEach() {
        System.out.println("Before after test");
    }

    @Test
    public void shouldEquals() {
        Assert.assertEquals(5, 2 + 3);
    }

    @Test
    public void shouldNotEquals() {
        Assert.assertNotEquals("Brazil", "Argetina");
    }

    @Test
    public void shouldNotNull() {
        Assert.assertNotNull("Brazil");
    }

}
