export class MineSweeperPlayfield{

    private readonly bombs : boolean[][];
    private readonly revealed : boolean[][];
    private readonly numBombs : number;
    private readonly numSquares : number;

    constructor(cols : number, rows : number){
        this.numSquares = cols * rows;
        this.numBombs = 0;
        this.bombs = [];
        this.revealed = [];
        for (var row: number = 0; row < rows; row++) {
            var bombsColumn: boolean[] = [];
            var revealedColumn : boolean[] = [];
            for (var col: number = 0; col < cols; col++) {
                let bomb : boolean = (Math.random() < 0.2);
                bombsColumn.push(bomb);
                if (bomb){
                    this.numBombs++;
                }
                revealedColumn.push(false);
            }
            this.bombs.push(bombsColumn);
            this.revealed.push(revealedColumn);
        }
    }

    numberOfBombs() : number {
        return this.numBombs;
    }

    numberOfSquares() : number {
        return this.numSquares;
    }

    hasBomb(col : number, row : number) : boolean {
        return this.bombs[row][col];
    }

    isRevealed(col : number, row: number) : boolean {
        return this.revealed[row][col];
    }

    revealAll() {
        for (let row : number = 0; row < this.bombs.length; row++){
            for (let col: number = 0; col < this.bombs[0].length; col++){
                this.reveal(col, row);
            }
        }
    }

    public reveal(col: number, row: number) : (number | null){
        this.revealed[row][col] = true;
        if (this.hasBomb(col, row)){
            return null;
        }else {
            return this.numberOfAdjacentBombs(col, row);
        }
    }

    private numberOfAdjacentBombs(col : number, row: number) : (number | null){
        // this describes a 'circle' of adjacent squares around the current square
        let relativePositions : [number, number][] = [  [-1, -1], [0, -1], [1, -1],
                                                        [-1, 0], [1, 0],
                                                        [-1, 1], [0, 1], [+1, 1]
                                                    ];
        let num : number = 0;
        for (let i : number = 0; i<relativePositions.length; i++){
            let col_offset : number = relativePositions[i][0];
            let row_offset : number = relativePositions[i][1];
            try {
                if (this.hasBomb(col + col_offset, row + row_offset)) {
                    num++;
                }
            }
            catch (Error){
                // out-of-bounds, okay, so that square did not exist. So what.
            }
        }
        return num;
    }
}
