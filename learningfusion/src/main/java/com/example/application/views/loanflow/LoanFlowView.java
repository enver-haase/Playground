package com.example.application.views.loanflow;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "loan-flow")
@PageTitle("Loan-Flow")
@CssImport("./views/loanflow/loan-flow-view.css")
public class LoanFlowView extends Div {

    public LoanFlowView() {
        addClassName("loan-flow-view");
        add(new Text("Content placeholder"));
    }

}
