package icu.koodeau.bmi;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.html.H1;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Body Mass Index Calculator",
        shortName = "BMI Calculator",
        description = "This is simple open source Spring Boot BMI Calculator.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        H1 title = new H1("Body Mass Index Calculator");
        add(title);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        add(progressBar);

        FormLayout cForm = new FormLayout();

        Select<String> unitSelect = new Select<>();
        unitSelect.setItems("Imperial", "Metric");
        unitSelect.setValue("Imperial");
        if (unitSelect.getValue() == "Imperial") {
            Notification notification = Notification.show(
                "You selected "+unitSelect.getValue()+" units."
                );
            add(notification);
        } else if (unitSelect.getValue() == "Metric") {
            Notification notification = Notification.show(
                "You selected "+unitSelect.getValue()+" units."
                );
            add(notification);
        }

        NumberField Height = new NumberField();
        Height.setHasControls(true);
        Height.setLabel("Your Height");
        Height.setPlaceholder("In cm or inch. (Ex. 180)");
        Height.setMin(0);

        NumberField Weight = new NumberField();
        Weight.setHasControls(true);
        Weight.setLabel("Your Weight");
        Weight.setPlaceholder("In kg or lb. (Ex. 59.7 or 128.8)");
        Weight.setStep(0.1d);
        Weight.setMin(0);


        cForm.add(unitSelect, Height, Weight);

        // cForm.setResponsiveSteps(
        //         new ResponsiveStep("25em", 1),
        //         new ResponsiveStep("32em", 2),
        //         new ResponsiveStep("40em", 3));
        add(cForm);


        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Calculate",
                e -> Notification.show(service.greet(unitSelect.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(button);
    }

}
