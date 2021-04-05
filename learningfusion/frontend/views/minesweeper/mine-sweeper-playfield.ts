export class MineSweeperPlayfield{

    squares : boolean[][];

    constructor(cols : number, rows : number){
        this.squares = [];
        for (var row: number = 0; row < rows; row++) {
            var colToBuild : boolean[] = [];
            for (var col: number = 0; col < cols; col++) {
                colToBuild.push(Math.random() < 0.2);
            }
            this.squares.push(colToBuild);
        }
    }

    hasBomb(col : number, row : number) : boolean {
        return this.squares[row][col];
    }
}
