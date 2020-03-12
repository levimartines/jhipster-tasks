package com.levimartines;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.levimartines");

        noClasses()
            .that()
                .resideInAnyPackage("com.levimartines.service..")
            .or()
                .resideInAnyPackage("com.levimartines.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.levimartines.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
