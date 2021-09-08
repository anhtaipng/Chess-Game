import {PIECE_CONSTANT} from "../../features/chessgame/Piece";

export const knightMoveDiffArray = [{x_diff: -2,y_diff:1},{x_diff: -1,y_diff:2},{x_diff: 1,y_diff:2},{x_diff: 2,y_diff:1},{x_diff: 2,y_diff:-1},{x_diff: 1,y_diff:-2},{x_diff: -1,y_diff:-2},{x_diff: -2,y_diff:-1},];
export const kingMoveDiffArray = [{x_diff: -1,y_diff:-1},{x_diff: -1,y_diff:0},{x_diff: -1,y_diff:1},{x_diff: 0,y_diff:1},{x_diff: 1,y_diff:1},{x_diff: 1,y_diff:0},{x_diff: 1,y_diff:-1},{x_diff: 0,y_diff:-1},];

// This function merely used for compare equal based on hash code of {x,y}
const BoardHelper = (() => {
    //
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
})();

export default BoardHelper;