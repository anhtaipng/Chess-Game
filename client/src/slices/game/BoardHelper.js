/* eslint-disable no-unused-vars */

import {PIECE_CONSTANT} from "../../features/chessgame/Piece";

const knightMoveDiffArray = [{x_diff: -2,y_diff:1},{x_diff: -1,y_diff:2},{x_diff: 1,y_diff:2},{x_diff: 2,y_diff:1},{x_diff: 2,y_diff:-1},{x_diff: 1,y_diff:-2},{x_diff: -1,y_diff:-2},{x_diff: -2,y_diff:-1},];
const kingMoveDiffArray = [{x_diff: -1,y_diff:-1},{x_diff: -1,y_diff:0},{x_diff: -1,y_diff:1},{x_diff: 0,y_diff:1},{x_diff: 1,y_diff:1},{x_diff: 1,y_diff:0},{x_diff: 1,y_diff:-1},{x_diff: 0,y_diff:-1},];

// This function merely used for compare equal based on hash code of {x,y}
const BoardHelper = (() => {
})();

export default BoardHelper;