import blackPawnImg from './chess_image/black_pawn.png';
import whitePawnImg from './chess_image/white_pawn.png';
import blackKnightImg from './chess_image/black_horse.png';
import whiteKnightImg from './chess_image/white_horse.png';
import blackBishopImg from './chess_image/black_bishop.png';
import whiteBishopImg from './chess_image/white_bishop.png';
import blackRookImg from './chess_image/black_rook.png';
import whiteRookImg from './chess_image/white_rook.png';
import blackKingImg from './chess_image/black_king.png';
import whiteKingImg from './chess_image/white_king.png';
import blackQueenImg from './chess_image/black_queen.png';
import whiteQueenImg from './chess_image/white_queen.png';
import BoardHelper from "../../slices/game/BoardHelper";

export const PIECE_CONSTANT = {
    KNIGHT: "N",
    QUEEN: "Q",
    KING: "K",
    PAWN: "P",
    ROOK: "R",
    BISHOP: "B",
    BLACK_ROYALTY: "BLACK",
    WHITE_ROYALTY: "WHITE",
}
export default class Piece {
    constructor(type, royalty, image) {
        this.type = type;
        this.royalty = royalty;
        this.image = image;
    }

    getType() {
        return this.type;
    }

    getRoyalty() {
        return this.royalty;
    }

    getImage() {
        return this.image;
    }

    // Chess piece of this type can move from pos1 to pos2. Check for legality like blockage and suicide (Living king in check).
    canMoveTo(pos1, pos2, pieces) {

    }
}

export class Pawn extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.PAWN, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackPawnImg : whitePawnImg);
        this.canEnpassent = false;
    }
    canBeEnpassented(pos2){
        if (this.royalty === PIECE_CONSTANT.BLACK_ROYALTY) {
            if(pos2.y !== 5) return false;
        }
        else if (this.royalty === PIECE_CONSTANT.BLACK_ROYALTY) {
            if(pos2.y !== 4) return false;
        }
        return true;
    }

    canMoveTo(pos1, pos2, pieces) {
        const pos2hash = BoardHelper.getHashCodeFromPos(pos2);
        if (this.royalty === PIECE_CONSTANT.WHITE_ROYALTY) {
            if (pos2.y !== pos1.y + 1) return false;
            if (pieces.has(pos2hash)) {
                const piece = pieces.get(pos2hash);
                if (piece.royalty === this.royalty) return false;
                // It is a capture
                return true;
            } else {
                let x_diff = pos2.x - pos1.x;
                if (x_diff === 0) return true;
                if (Math.abs(x_diff)) return false;
                let enPassedPos = {x: pos2.x + x_diff, y: pos2.y};
                if (!pieces.has(BoardHelper.getHashCodeFromPos(enPassedPos))) {
                    return false;
                }
                const piece = pieces.get(BoardHelper.getHashCodeFromPos(enPassedPos));
                if (!piece.canBeEnpassented()) return false;
                // Check normal Move
            }
        } else {
            if (pos2.y !== pos1.y - 1) return false;
            if (pieces.has(pos2hash)) {
                const piece = pieces.get(pos2hash);
                if (piece.royalty === this.royalty) return false;
                // It is a capture
                return true;
            } else {
                let x_diff = pos2.x - pos1.x;
                if (x_diff === 0) return true;
                if (Math.abs(x_diff)) return false;
                let enPassedPos = {x: pos2.x + x_diff, y: pos2.y};
                if (!pieces.has(BoardHelper.getHashCodeFromPos(enPassedPos))) {
                    return false;
                }
                const piece = pieces.get(BoardHelper.getHashCodeFromPos(enPassedPos));
                if (!piece.canBeEnpassented()) return false;
            }
        }
        return false;
    }
}

export class Knight extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.KNIGHT, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackKnightImg : whiteKnightImg);
    }

    canMoveTo(pos1, pos2, pieces) {
        if (pieces.has(BoardHelper.getHashCodeFromPos(pos2))) {
            const piece1 = pieces.get(BoardHelper.getHashCodeFromPos(pos1));
            const piece2 = pieces.get(BoardHelper.getHashCodeFromPos(pos2));
            if (piece2.royalty === piece1.royalty) {
                return false;
            }
        }
        return BoardHelper.isConnectedByKnightMove(pos1, pos2);
    }
}

export class Bishop extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.BISHOP, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackBishopImg : whiteBishopImg);
    }

    canMoveTo(pos1, pos2, pieces) {
        if (pieces.has(BoardHelper.getHashCodeFromPos(pos2))) {
            const piece1 = pieces.get(BoardHelper.getHashCodeFromPos(pos1));
            const piece2 = pieces.get(BoardHelper.getHashCodeFromPos(pos2));
            if (piece2.royalty === piece1.royalty) {
                return false;
            }
        }
        return BoardHelper.isConnectedByDiagonal(pos1, pos2) && BoardHelper.isCleanPath(pos1, pos2, pieces);
    }
}

export class Rook extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.ROOK, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackRookImg : whiteRookImg);
    }

    canMoveTo(pos1, pos2, pieces) {
        if (pieces.has(BoardHelper.getHashCodeFromPos(pos2))) {
            const piece1 = pieces.get(BoardHelper.getHashCodeFromPos(pos1));
            const piece2 = pieces.get(BoardHelper.getHashCodeFromPos(pos2));
            if (piece2.royalty === piece1.royalty) {
                return false;
            }
        }
        return (BoardHelper.isConnectedByLine(pos1, pos2)) && BoardHelper.isCleanPath(pos1, pos2, pieces);
    }
}

export class Queen extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.QUEEN, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackQueenImg : whiteQueenImg);
    }

    canMoveTo(pos1, pos2, pieces) {
        if (pieces.has(BoardHelper.getHashCodeFromPos(pos2))) {
            const piece1 = pieces.get(BoardHelper.getHashCodeFromPos(pos1));
            const piece2 = pieces.get(BoardHelper.getHashCodeFromPos(pos2));
            if (piece2.royalty === piece1.royalty) {
                return false;
            }
        }
        return (BoardHelper.isConnectedByDiagonal(pos1, pos2) || BoardHelper.isConnectedByLine(pos1, pos2)) && BoardHelper.isCleanPath(pos1, pos2, pieces);
    }
}

export class King extends Piece {
    constructor(royalty) {
        super(PIECE_CONSTANT.KING, royalty, royalty === PIECE_CONSTANT.BLACK_ROYALTY ? blackKingImg : whiteKingImg);
    }

    canMoveTo(pos1, pos2, pieces) {
        if (pieces.has(BoardHelper.getHashCodeFromPos(pos2))) {
            const piece1 = pieces.get(BoardHelper.getHashCodeFromPos(pos1));
            const piece2 = pieces.get(BoardHelper.getHashCodeFromPos(pos2));
            if (piece2.royalty === piece1.royalty) {
                return false;
            }
        }
        return BoardHelper.isConnectedByKingMove(pos1, pos2, pieces);
    }
}