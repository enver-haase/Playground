package com.infraleap.playground.scribbler14.legacy;

import com.infraleap.playground.scribbler14.AbstractViewTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class ScribblerIT extends AbstractViewTest {

    @Test
    public void thereIsASnapshotButton() {
        findElements(By.className("v-app")).stream().findFirst().ifPresentOrElse(
                div ->
                {
                    List<WebElement> buttonCaptions = div.findElements(By.className("v-button-caption"));
                    Optional<WebElement> snapShot = buttonCaptions.stream().filter(buttonCaption -> buttonCaption.getText().equals("Snapshot!")).findFirst();

                    Assert.assertTrue("Expected a 'Snapshot!' button to be present.", snapShot.isPresent());
                },
                () -> {
                    Assert.fail("Could not find MPR legacy V8 <div> element.");
                }
        );
    }

}
