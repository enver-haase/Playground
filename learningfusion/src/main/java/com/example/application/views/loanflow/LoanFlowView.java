package com.example.application.views.loanflow;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "loan-flow")
@PageTitle("Loan-Flow")
@CssImport("./views/loanflow/loan-flow-view.css")
public class LoanFlowView extends VerticalLayout {

    private final NumberField loan;
    private final NumberField rate;
    private final NumberField interest;
    private final VerticalLayout calculation;

    public LoanFlowView() {
        addClassName("loan-flow-view");

        loan = new NumberField("Kreditsumme");
        loan.setMin(0.01d);
        loan.setValue(60247.99d);
        loan.addValueChangeListener( e -> recalculate() );

        rate = new NumberField("Monatliche Rate");
        rate.setMin(0.01d);
        rate.setValue(1242.5d);
        rate.addValueChangeListener( e -> recalculate() );

        interest = new NumberField("Jährlicher Zins in %");
        interest.setMin(0.0d);
        interest.setMax(100.d);
        interest.setValue(1.15d);
        interest.addValueChangeListener( e -> recalculate() );

        FormLayout formLayout = new FormLayout();
        formLayout.add(loan, rate, interest);

        calculation = new VerticalLayout();
        add(formLayout, calculation);

        recalculate();
    }

    private void recalculate(){
        calculation.removeAll();

        double loan = this.loan.getValue();
        final double rate = this.rate.getValue();
        final double annualInterest = this.interest.getValue() / 100.0d;
        final double monthlyInterest = annualInterest / 12.0d;

        int month = 1;

        while (loan >= rate && month < 500 /* be failsafe */) {
            final double interestAmount = loan * monthlyInterest;
            final double repaymentAmount = rate - interestAmount;

            FormLayout line = new FormLayout();
            line.add(new Label(Integer.toString(month)));
            line.add(new Label(Double.toString(loan)));
            line.add(new Label(Double.toString(interestAmount))); // interest
            line.add(new Label(Double.toString(repaymentAmount))); // repayment

            calculation.add(line);

            loan -= repaymentAmount;
            month++;
        }
    }

}
