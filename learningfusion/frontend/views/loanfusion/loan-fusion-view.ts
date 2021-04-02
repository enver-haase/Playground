import '!style-loader!css-loader!./loan-fusion-view.css';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';

@customElement('loan-fusion-view')
export class LoanFusionView extends View {
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
