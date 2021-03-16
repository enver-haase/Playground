import '!style-loader!css-loader!./hello-fusion-view.css';
import { showNotification } from '@vaadin/flow-frontend/a-notification';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-text-field';
import { customElement, html } from 'lit-element';
import { View } from '../../views/view';
import * as loggingEndpoint from '../../generated/LoggingEndpoint'

@customElement('hello-fusion-view')
export class HelloFusionView extends View {
  name: string = '';

  render() {
    return html`
      <vaadin-text-field label="Your name" @value-changed="${this.nameChanged}"></vaadin-text-field>
      <vaadin-button @click="${this.sayHello}">Say hello</vaadin-button>
    `;
  }
  nameChanged(e: CustomEvent) {
    this.name = e.detail.value;
  }

  sayHello() {
    let message = `Hello ${this.name}`
    showNotification(message);
    this.log(message);
  }

  log(greeting : string){
    loggingEndpoint.log(greeting).then()
  }
}
