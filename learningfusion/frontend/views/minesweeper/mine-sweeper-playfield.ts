export class MineSweeperPlayfield{

    bombs : boolean[][];
    revealed : boolean[][];

    constructor(cols : number, rows : number){
        this.bombs = [];
        this.revealed = [];
        for (var row: number = 0; row < rows; row++) {
            var bombsColumn: boolean[] = [];
            var revealedColumn : boolean[] = [];
            for (var col: number = 0; col < cols; col++) {
                bombsColumn.push(Math.random() < 0.2);
                revealedColumn.push(false);
            }
            this.bombs.push(bombsColumn);
            this.revealed.push(revealedColumn);
        }
    }

    hasBomb(col : number, row : number) : boolean {
        return this.bombs[row][col];
    }

    isRevealed(col : number, row: number) : boolean {
        return this.revealed[row][col];
    }

    public reveal(col: number, row: number) : (number | null){
        this.revealed[row][col] = true;
        return this.numberOfAdjacentBombs(col, row);
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
