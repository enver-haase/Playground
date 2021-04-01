import '!style-loader!css-loader!./about-fusion-view.css';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';

@customElement('about-fusion-view')
export class AboutFusionView extends View {
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
