import '!style-loader!css-loader!./mine-sweeper-view.css';
import {customElement, html, internalProperty, TemplateResult} from 'lit-element';
import {View} from '../../views/view';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-button';
import '@vaadin/vaadin-text-field/vaadin-number-field';
import {FormLayoutResponsiveStep} from "@vaadin/vaadin-form-layout/@types/interfaces";
import {MineSweeperPlayfield} from "Frontend/views/minesweeper/mine-sweeper-playfield";
import {showNotification} from "@vaadin/flow-frontend/a-notification";
import {ConnectionState, ConnectionStateStore} from "@vaadin/flow-frontend/ConnectionState"
import * as MineSweeperEndpoint from 'Frontend/generated/MineSweeperHighScores';


@customElement('mine-sweeper-view')
export class MineSweeperView extends View {
  readonly minCols : number = 3;
  readonly maxCols : number = 50;
  readonly minRows : number = 3;
  readonly maxRows : number = 50;

  @internalProperty()
  private cols : number = 3;
  @internalProperty()
  private rows : number = 3;

  @internalProperty()
  private playfield: MineSweeperPlayfield;
  @internalProperty()
  private turnsPlayed: number;
  @internalProperty()
  private gameOver : boolean;
  @internalProperty()
  private win : boolean;

  constructor(){
    super();
    this.playfield = new MineSweeperPlayfield(this.cols, this.rows);
    this.turnsPlayed = 0;
    this.gameOver = false;
    this.win = false;

    let connectionStateStore : ConnectionStateStore = (window as any).Vaadin.connectionState as ConnectionStateStore;

    connectionStateStore.addStateChangeListener( (before: ConnectionState, current: ConnectionState) => {
      if (before === ConnectionState.RECONNECTING && current === ConnectionState.CONNECTED){
        this.updateHighScores();
      }
    });
  }

  updateHighScores() : void {
    console.log("ON LINE AGAIN!");
    this.doUpdateHighScores().then();
  }

  private async doUpdateHighScores(): Promise<void> {
    // TODO: should really be 10 real scores, not 3 that are made up
    let highScores : number[] = await MineSweeperEndpoint.getHighScores(this.cols, this.rows, [456, 123, 0]).then();
    highScores.forEach( hi => console.log(hi) )
  }

  @internalProperty()
  private responsiveSteps: FormLayoutResponsiveStep[] = [
    { minWidth: 0, columns: 1 },
    { minWidth: '20em', columns: 2 },
  ];

  render() {
    return html`
      <vaadin-vertical-layout class="mine-sweeper-view" theme="padding spacing" style="width: 100%;">
        <vaadin-form-layout .responsiveSteps=${this.responsiveSteps}>
          <vaadin-number-field @value-changed="${this.rowsChanged}" value="${this.rows}" min="${this.minRows}" max="${this.maxRows}" step="1" label="Rows" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-number-field @value-changed="${this.colsChanged}" value="${this.cols}" min="${this.minCols}" max="${this.maxCols}" step="1" label="Columns" style="width: calc(99.9% - 0rem); margin-left: 0; margin-right: 0;"></vaadin-number-field>
          <vaadin-button @click="${this.restart}" colspan="2">RESTART THE GAME</vaadin-button>
        </vaadin-form-layout>
        <vaadin-vertical-layout>
          ${this.recalculate()}
        </vaadin-vertical-layout>
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
    if (this.cols >= this.minCols && this.cols <= this.maxCols && this.rows >= this.minRows && this.rows <= this.maxRows){
      // TODO: Dialog for 'really? yes/no'
      this.playfield = new MineSweeperPlayfield(this.cols, this.rows);
      this.turnsPlayed = 0;
      this.gameOver = false;
      this.win = false;
      this.requestUpdate();
    }
    else{
      showNotification("Please correct rows & columns dimensions before re-starting.")
    }
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

  squareClicked(col : number, row: number) {
    if (!this.gameOver){
      if (!this.playfield.isRevealed(col, row)) {
        this.turnsPlayed++;
        if (this.playfield.reveal(col, row) === null){
          this.gameOver = true;
          this.win = false;
          this.playfield.revealAll();
          showNotification("BOMB - YOU DIE.")
        } else {
          if (this.turnsPlayed === this.playfield.numberOfSquares() - this.playfield.numberOfBombs()) {
            this.gameOver = true;
            this.win = true;
            this.playfield.revealAll();
            showNotification("SWEPT THE BOARD - WELL DONE!");
          }
        }
        this.requestUpdate();
      }
    }
    else {
      showNotification(this.turnsPlayed === this.playfield.numberOfSquares() - this.playfield.numberOfBombs() ? "You WON - Restart the game." : "You LOST - Restart the game.");
    }
  }

  squareText(col: number, row: number) : string{
    if (!this.playfield.isRevealed(col, row)){
      return "?"
    }
    else {
      const toShow: number | null = this.playfield.reveal(col, row);
      if (toShow === null){
        return "BOMB"
      }
      else {
        return toShow.toString(10);
      }
    }
  }

}
