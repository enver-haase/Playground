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

    numberOfAdjacentBombs(col : number, row: number){
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
