package com.example.application.views.layouting;

import com.example.application.data.entity.SampleAddress;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

import java.math.BigDecimal;
import java.util.List;

@CssImport("./views/layouting/layouting-view.css")
@Route(value = "layouting", layout = MainView.class)
@PageTitle("Layouting")
public class LayoutingView extends Div {

    private TextField street = new TextField("Street address");
    private TextField postalCode = new TextField("Postal code");
    private TextField city = new TextField("City");
    private ComboBox<String> state = new ComboBox<>("State");
    private ComboBox<String> country = new ComboBox<>("Country");

    private MyBigDecimalField amount = new MyBigDecimalField(2, "Amount");
    private NumberField wrongNameForAutoBind_fine = new NumberField("Fine");

    private TextField wrongNameForAutoBind_period = new TextField("Period");

    private Binder<SampleAddress> binder = new Binder<>(SampleAddress.class);

    public LayoutingView(SampleAddress sampleAddress) {
        addClassName("layouting-view");

        add(createTitle());
        add(createFormLayout());

        binder.bindInstanceFields(this);

        binder.setBean(sampleAddress);
    }

    private Component createTitle() {
        return new H3("Address");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(street, 2);
        postalCode.setPattern("\\d*");
        postalCode.setPreventInvalidInput(true);
        country.setItems("Country 1", "Country 2", "Country 3");
        state.setItems("State A", "State B", "State C", "State D");
        formLayout.add(postalCode, city, state, country);

        formLayout.setColspan(country, 3);

        formLayout.add(amount);

        binder.forField(wrongNameForAutoBind_fine).withConverter(
                new Converter<Double, BigDecimal>() {
                    @Override
                    public Result<BigDecimal> convertToModel(Double value, ValueContext context) {
                        if (value != null) {
                            return Result.ok(new BigDecimal(value));
                        }
                        else{
                            return Result.error("NULL cannot be converted.");
                        }
                    }

                    @Override
                    public Double convertToPresentation(BigDecimal value, ValueContext context) {
                        return value.doubleValue();
                    }
                }
        ).withNullRepresentation(new BigDecimal("0")).bind("fine");
        formLayout.add(wrongNameForAutoBind_fine);


        binder.forField(wrongNameForAutoBind_period)
                .withValidator(  (value, context) -> {
                    if (value == null || value.length() != 7){
                        return ValidationResult.error("Periode muss sieben Zeichen lang sein,");
                    }
                    if (value.charAt(4) != '/'){
                        return ValidationResult.error("Das fünfte Zeichen einer Periode muss der Schrägstrich sein.");
                    }
                    for (int i=0; i<7; i++){
                        if (i == 4)
                            continue;
                        if (value.charAt(i) < '0' || value.charAt(i) > '9'){
                            return ValidationResult.error("Außer dem Schrägstrich werden nur Ziffern erwartet");
                        }
                    }
                    int month = Integer.parseInt(value.substring(5));
                    if (month < 1 || month > 12){
                        return ValidationResult.error("Letzte zwei Ziffern sollen einen Monat bezeichnen. Erlaubt sind 01 bis 12.");
                    }
                    return ValidationResult.ok();
                })
                .bind("period");
        formLayout.add(wrongNameForAutoBind_period);


        makeResponsive(formLayout);

        return formLayout;
    }

    private void makeResponsive(FormLayout formLayout) {
        dumpResponsiveSteps(formLayout);

        formLayout.setResponsiveSteps(  new FormLayout.ResponsiveStep("1px", 1),
                                        new FormLayout.ResponsiveStep("400px", 2),
                                        new FormLayout.ResponsiveStep("800px", 3));

        dumpResponsiveSteps(formLayout);
    }

    private static void dumpResponsiveSteps(FormLayout formLayout){
        List<FormLayout.ResponsiveStep> steps = formLayout.getResponsiveSteps();
        for (Object step : steps){
            System.err.println(step.toString() + " : " + step.getClass().getCanonicalName());
        }
        System.err.println(steps.size() + " responsive steps defined.");
    }



}
