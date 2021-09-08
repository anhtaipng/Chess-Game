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
import BoardHelper, {kingMoveDiffArray, knightMoveDiffArray} from "../../slices/game/BoardHelper";

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
        this.doubledMoved = false;
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
            if(!this.doubleMoved){
                if((pos2.y === pos1.y +2) && (pos2.x === pos1.x)){
                    this.doubledMoved = true;
                    return true;
                }
            }
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
            if(!this.doubleMoved){
                if((pos2.y === pos1.y -2) && (pos2.x === pos1.x)){
                    this.doubledMoved = true;
                    return true;
                }
            }
            if (pos2.y !== pos1.y - 1) return false;
            if (pieces.has(pos2hash)) {    //
                function getEnemyRoyalty(royalty){
                    return royalty === PIECE_CONSTANT.BLACK_ROYALTY ? PIECE_CONSTANT.WHITE_ROYALTY : PIECE_CONSTANT.BLACK_ROYALTY;
                }
                function getEnemyAlivePieces(royalty,pieces){
                    const enemyRoyalty = getEnemyRoyalty(royalty);
                    return pieces.filter(piece => piece.royalty === enemyRoyalty);
                }

                function isOfOppositeRoyalty(piece1,piece2){
                    return piece1.getRoyalty() !== piece2.getRoyalty();
                }
                //
                function getMinMax(num1, num2) {
                    if (num1 < num2) {
                        return {min: num1, max: num2};
                    }
                    return {min: num2, max: num1}
                }
                function getPosObjectFromXY(x,y){
                    return {x,y};
                }
                // Compute an hashcode from {x,y}
                function getHashCodeFromPos(pos){
                    return pos.y *10 +pos.x;
                }
                // return a hash code from pos {x,y}
                function getPosFromHashCode(hash){
                    return {y:Math.floor(hash/10),x:hash%10};
                }
                function comparesPosEqual(posObject1,posObject2){
                    return posObject1.x === posObject2.x || posObject1.y === posObject2.y;
                }
                // Convert 11 --> A1  |  22 --> B2
                function convertNumNumToCharNum(numNumString) {
                    return String.fromCharCode(parseInt(numNumString.charAt(0)) + 65).concat(numNumString.charAt(1));
                }
                // Convert B1 --> 21  |  C3 --> 33.
                function convertCharNumToNumNum(charNumPos) {
                    return charNumPos;
                }

                // Extract the coords from the CharNum: B3 --> {2,3}
                function getPosObjectFromCharNum(charNumString) {
                    return {x: charNumString.charCodeAt(0) - 64, y: parseInt(charNumString.charAt(1))};
                }

                // Extract the coords from the NumNum: B3 --> {2,3}
                function getPosObjectFromNumNum(numNumString) {
                    return {x: parseInt(numNumString.charAt(0)), y: parseInt(numNumString.charAt(1))};
                }

                // pos1 and pos2 shoudl be in NumNum
                function isConnectedByLine(pos1, pos2) {
                    const {x: srcX, y: srcY} = pos1;
                    const {x: destX, y: destY} = pos2;
                    return srcX === destX || srcY === destY;
                }

                function isConnectedByDiagonal(pos1, pos2) {
                    const {x: srcX, y: srcY} = pos1;
                    const {x: destX, y: destY} = pos2;
                    return srcX + srcY === destX + destY || (srcX - srcY === destX - destY);
                }

                // Generate the list of square EXCLUSIVE between two point:
                // pos1 and po2 should also be numnum
                function getSpotsBetweenTwoPos(pos1, pos2) {
                    const spots = [];
                    const {x: x1, y: y1} = pos1;
                    const {x: x2, y: y2} = pos2;
                    if (x1 === x2) {
                        const {min, max} = getMinMax(y1, y2);
                        for (let i = min + 1; i < max; i++) {
                            spots.push({x: x1, y: i});
                        }
                    } else if(y1===y2){
                        const {min, max} = getMinMax(x1, x2);
                        for (let i = min + 1; i < max; i++) {
                            spots.push({x: i, y: y1});
                        }
                    }
                    else if(x1 + y1 === x2+y2){
                        const {min,max} = getMinMax(x1,x2);
                        const diag_const = x1 + y1;
                        for(let x = min + 1; x< max;x++){
                            let y_diff = diag_const - x;
                            spots.push ({x:x,y:y_diff});
                        }
                    }
                    else if ((x1 - y1) === (x2 - y2)) {
                        let diag_const = Math.abs(x1 - y1);
                        let negative_diag = x2 - y2 < 0;
                        const {min, max} = getMinMax(x1, x2);
                        for(let x = min +1; x<max;x++){
                            let y_diff = negative_diag ? x +diag_const : x - diag_const;
                            spots.push({x, y: y_diff});
                        }
                    }
                    return spots;
                }

                // check if knight can jump from pos1 to pos2
                function isConnectedByKnightMove(pos1, pos2) {
                    for(let ele of knightMoveDiffArray){
                        if (pos1.x + ele.x_diff === pos2.x && pos1.y + ele.y_diff === pos2.y) {
                            return true;
                        }
                    }
                }

                function isConnectedByKingMove(pos1, pos2) {
                    for(let ele of kingMoveDiffArray){
                        if (pos1.x + ele.x_diff === pos2.x && pos1.y + ele.y_diff === pos2.y) {
                            return true;
                        }
                    }
                }

                // Check if the path passed is clean with the alive pieces passed in
                // Pieces is the map in store: <posHashCode, piece>
                // sourceToDestPath is a list of spots returned by getSpotsBetweenTwoPos
                function checkCleanPath(sourceToDestPath, pieces) {
                    for(let ele of sourceToDestPath){
                        if(pieces.has(getHashCodeFromPos(ele))){
                            return false;
                        }
                    }
                    return true;
                }

                function isCleanPath(src,dest,pieces){
                    const path = getSpotsBetweenTwoPos(src,dest);
                    return checkCleanPath(path,pieces);
                }
                // Check if king is checked
                // Royalty: The king side to be check for check
                function checkIfKingIsChecked(royalty,pieces) {
                    let enemyRoyalty = getEnemyRoyalty(royalty);
                    let kingPos;
                    pieces.forEach((hashCode,piece) => {
                        if(piece.royalty === royalty && piece.type === PIECE_CONSTANT.KING){
                            kingPos = getPosFromHashCode(hashCode);
                        }
                    })
                    let enemyPieces = pieces.filter(piece => piece.royalty === enemyRoyalty);
                    for(let piece of enemyPieces){
                        if(piece.canMoveTo(kingPos,pieces)){
                            return true;
                        }
                    }
                }
                // Get the piece at pos: May return null
                function getPieceAtPos(pos,pieces){
                    if (!pieces.has(getHashCodeFromPos(pos))) {
                        throw new Error("No pieces is at Pos");
                    }
                    console.log("At getPieceAtPos,returned pieces is:",pieces.get(getHashCodeFromPos(pos)));
                    return pieces.get(getHashCodeFromPos(pos));
                }
                // check move validity:
                function checkMoveValidity(pos1,pos2,pieces){
                    console.log(`checking ${pos1.toString()} can move to ${pos2.toString()}`,pos1,pos2);
                    const p = getPieceAtPos(pos1, pieces);
                    console.log("Pawn checkging move can execute:",p.canMoveTo(pos1, pos2, pieces));
                    return p.canMoveTo(pos1, pos2, pieces);
                }
                return {checkIfKingIsChecked,checkCleanPath,isConnectedByDiagonal,isConnectedByLine,isConnectedByKingMove,
                    isConnectedByKnightMove,getPosFromHashCode,getPosObjectFromXY,getPosObjectFromCharNum,getPosObjectFromNumNum,getSpotsBetweenTwoPos
                    ,convertCharNumToNumNum,convertNumNumToCharNum,isOfOppositeRoyalty,getMinMax,comparesPosEqual,isCleanPath,getHashCodeFromPos,checkMoveValidity,getPieceAtPos};

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
        console.log("bishop move:", pos1, pos2);
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
                console.log("King check genocide:",piece1.royalty,piece2.royalty);
                return false;
            }
        }
        return BoardHelper.isConnectedByKingMove(pos1, pos2, pieces);
    }
}