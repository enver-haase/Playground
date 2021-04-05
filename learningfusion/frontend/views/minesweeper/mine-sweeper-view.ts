import '!style-loader!css-loader!./mine-sweeper-view.css';
import {customElement, html, TemplateResult} from 'lit-element';
import { View } from '../../views/view';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-text-field/vaadin-number-field';
import {FormLayoutResponsiveStep} from "@vaadin/vaadin-form-layout/@types/interfaces";
import {MineSweeperPlayfield} from "Frontend/views/minesweeper/mine-sweeper-playfield";

@customElement('mine-sweeper-view')
export class MineSweeperView extends View {
  private cols : number = 15;
  private rows : number = 15;

  private playfield: MineSweeperPlayfield;

  constructor(){
    super();
    this.playfield = new MineSweeperPlayfield(this.cols, this.rows);
  }

  responsiveSteps: FormLayoutResponsiveStep[] = [
    { minWidth: 0, columns: 1 },
    { minWidth: '20em', columns: 2 },
  ];

  render() {
    return html`
      <vaadin-vertical-layout class="mine-sweeper-view" theme="padding spacing" style="width: 100%;">
        <vaadin-form-layout .responsiveSteps=${this.responsiveSteps}>
          <vaadin-number-field @value-changed="${this.rowsChanged}" value="${this.rows}" min="5" max="50" step="1" label="Rows" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-number-field @value-changed="${this.colsChanged}" value="${this.cols}" min="5" max="50" step="1" label="Columns" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-button @click="${this.restart}"></vaadin-button>
        </vaadin-form-layout>
        ${this.recalculate()}
      </vaadin-vertical-layout>
    `;
  }

  rowsChanged( e: CustomEvent ){
    this.rows = e.detail.value;
  }

  colsChanged( e: CustomEvent ){
    this.cols = e.detail.value;
  }

  restart() {
    // TODO: Dialog for 'really? yes/no'
    this.playfield = new MineSweeperPlayfield(this.cols, this.rows);
    this.requestUpdate();
  }

  recalculate() {
    let lines : TemplateResult[] = [];
    let cols : TemplateResult[] = [];

    for(let r=0; r<this.rows; r++){
      cols = [];
      for(let c=0; c<this.cols; c++){
        // style="min-width: 42px; width: 42px; height: 42px;"
        cols.push( html`<vaadin-button @click="${() => this.squareClicked(c, r)}">${this.squareText(c, r)}</vaadin-button>` );
      }
      lines.push( html`<vaadin-horizontal-layout>${cols}</vaadin-horizontal-layout>` );
    }

    return html`${lines}`;
  }

  squareClicked(col : number, row: number){
    this.playfield.reveal(col, row);
  }

  squareText(col: number, row: number){
    if (this.playfield.isRevealed(col, row)){
      return ""
    }
    else {
      var toShow : number | null = this.playfield.reveal(col, row);
      if (toShow === null){
        return "BOMB"
      }
      else {
        return toShow;
      }
    }
  }

}
