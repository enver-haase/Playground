import '!style-loader!css-loader!./loan-fusion-view.css';
import {customElement, html, TemplateResult} from 'lit-element';
import { View } from '../../views/view';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-text-field/vaadin-number-field';
import {FormLayoutResponsiveStep} from "@vaadin/vaadin-form-layout/@types/interfaces";

@customElement('loan-fusion-view')
export class LoanFusionView extends View {
  loan : number = 0.0;
  rate : number = 0.0;
  interest : number = 0.0;

  responsiveSteps: FormLayoutResponsiveStep[] = [
    { minWidth: 0, columns: 1 },
    { minWidth: '20em', columns: 2 },
  ];

  render() {
    return html`
      <vaadin-vertical-layout class="loan-fusion-view" theme="padding spacing" style="width: 100%;">
        <vaadin-form-layout .responsiveSteps=${this.responsiveSteps}>
          <vaadin-number-field @value-changed="${this.loanChanged}" value="60247.99" min="0.01" label="Kreditsumme" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-number-field @value-changed="${this.rateChanged}" value="1242.50" min="0.01" label="Monatliche Rate" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-number-field @value-changed="${this.interestChanged}" value="1.15" min="0" max="100" label="J&auml;hrlicher Zins in %" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
        </vaadin-form-layout>
        ${this.recalculate()}
      </vaadin-vertical-layout>
    `;
  }

  loanChanged( e: CustomEvent ){
    this.loan = e.detail.value;
    this.requestUpdate();
  }
  rateChanged( e: CustomEvent ){
    this.rate = e.detail.value;
    this.requestUpdate();
  }
  interestChanged( e: CustomEvent ){
    this.interest = e.detail.value;
    this.requestUpdate();
  }


  recalculate() {
    let loan = this.loan;
    const rate = this.rate;
    const annualInterest = this.interest / 100.0;
    const monthlyInterest = annualInterest / 12.0;

    let month = 1;

    let lines : TemplateResult[] = [];

    while (loan >= rate && month < 500 /* be failsafe */) {
      let interestAmount = loan * monthlyInterest;
      let repaymentAmount = rate - interestAmount;

      // TODO: form layout should have two columns?
      //       entries that span two lines are not vertically aligned.
      lines.push( html`<vaadin-form-layout .responsiveSteps=${this.responsiveSteps}>
            <label style="width: calc(99.9% + 0rem); margin-left: 0px; margin-right: 0px;">${month}</label>
            <label style="width: calc(99.9% + 0rem); margin-left: 0px; margin-right: 0px;">${loan}</label>
            <label style="width: calc(99.9% + 0rem); margin-left: 0px; margin-right: 0px;">${interestAmount}</label>
            <label style="width: calc(99.9% + 0rem); margin-left: 0px; margin-right: 0px;">${repaymentAmount}</label>
          </vaadin-form-layout>` );

      loan -= repaymentAmount;
      month++;
    }
    return html`${lines}`;
  }
}
