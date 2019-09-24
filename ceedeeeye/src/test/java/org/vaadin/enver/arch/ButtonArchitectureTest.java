package org.vaadin.enver.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ButtonArchitectureTest {

		@Test
		public void button_creation_goes_through_buttons_package() {
			JavaClasses importedClasses = new ClassFileImporter().importPackages("org.vaadin");

			ArchRule rule = classes().that().areAssignableTo(com.vaadin.flow.component.button.Button.class).should().resideInAPackage("org.vaadin.enver.buttons");
			rule.check(importedClasses);
		}
}
