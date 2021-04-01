import '!style-loader!css-loader!./about-flow-view.css';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';

@customElement('about-flow-view')
export class AboutFlowView extends View {
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
