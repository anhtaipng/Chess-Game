package vn.gihot.chess.master.model.game.board;


import vn.gihot.chess.master.model.exception.InvalidPositionFormatException;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.game.piece.Type;
import vn.gihot.chess.master.utilclass.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

public class BoardHelper {
    // Constant:
//    public static String SAME_LOYALTY = "SAME_LOYALTY";

    public static ArrayList<Pair<Integer, Integer>> knightMoveDiffArray = new ArrayList<>();
    public static ArrayList<Pair<Integer, Integer>> kingMoveDiffArray = new ArrayList<>();
    // This list is used for fast accessing the index and avoid duplicate initilization
    // This list IS NOT MODIFICABLE.
    public static List<Position> positionDist;

    // Hash function effectively reduce 11 - 88 --> 00- 64
    public static int hashPositionCoord(int x, int y) {
        int displayedPos = y * 10 + x;
        int storedPos = displayedPos - 11;
        int index = storedPos - ((y - 1) * 2);
        return index;
    }

    public static ArrayList<Pair<Integer, Integer>> getKnightMoveDiffArray() {
        return knightMoveDiffArray;
    }

    public static Position getPosFromCoord(int x, int y) {
        return positionDist.get(hashPositionCoord(x, y));
    }

    static {
        // Init Knight Move Diff Arr

        knightMoveDiffArray.add(new Pair<Integer, Integer>(-2, 1));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(-1, 2));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(1, 2));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(2, 1));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(2, -1));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(1, -2));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(-1, -2));
        knightMoveDiffArray.add(new Pair<Integer, Integer>(-2, -1));
        // Init Knight Move Diff Arr
        kingMoveDiffArray.add(new Pair<>(-1, -1));
        kingMoveDiffArray.add(new Pair<>(-1, 0));
        kingMoveDiffArray.add(new Pair<>(-1, 1));
        kingMoveDiffArray.add(new Pair<>(0, 1));
        kingMoveDiffArray.add(new Pair<>(1, 1));
        kingMoveDiffArray.add(new Pair<>(1, 0));
        kingMoveDiffArray.add(new Pair<>(1, -1));
        kingMoveDiffArray.add(new Pair<>(0, -1));
        // Create Position dictionary
        ArrayList<Position> tempList = new ArrayList<>();
        for (int y = 1; y <= 8; y++)
            for (int x = 1; x <= 8; x++) {
                int index = hashPositionCoord(x, y);
                tempList.ensureCapacity(index + 1);
                tempList.add(index, new Position(x, y));
            }
        positionDist = Collections.unmodifiableList(tempList);
    }

    // This return the array list of position between two positions (Inclusive):
    public static ArrayList<Position> getConnectedPositionBetweenPos(Position pos1, Position pos2) {
        int x1 = pos1.getX();
        int x2 = pos2.getX();
        int y1 = pos1.getY();
        int y2 = pos2.getY();
        ArrayList<Position> res = new ArrayList<>();
        // On the same diagonal
        if (x1 + y1 == x2 + y2) {
            Pair<Integer, Integer> loopIndexes = getSortedPairForLoop(x1, x2);
            for (int x = loopIndexes.first; x <= loopIndexes.second; x++) {
                int y_diff = x1 + y1 - x;
                res.add(BoardHelper.getPosFromCoord(x, y_diff));
            }
        } else if (Math.abs(x1 - y1) == Math.abs(x2 - y2)) {
            int diagonalConst = Math.abs(x1 - y1);
            boolean negativeDig = x2 - y2 < 0;
            Pair<Integer, Integer> loopIndexes = getSortedPairForLoop(x1, x2);
            for (int x = loopIndexes.first; x <= loopIndexes.second; x++) {
                int y_diff = negativeDig ? x + diagonalConst : x - diagonalConst;
                res.add(BoardHelper.getPosFromCoord(x, y_diff));
            }
        }
        // On the same line
        else if (x1 == x2) {
            Pair<Integer, Integer> loopIndexes = getSortedPairForLoop(y1, y2);
            for (int y = loopIndexes.first; y <= loopIndexes.second; y++) {
                res.add(BoardHelper.getPosFromCoord(x1, y));
            }
        } else if (y2 == y1) {
            Pair<Integer, Integer> loopIndexes = getSortedPairForLoop(x1, x2);
            for (int x = loopIndexes.first; x <= loopIndexes.second; x++) {
                res.add(BoardHelper.getPosFromCoord(x, y1));
            }
        }
        return res;
    }

    public static Type getEnemyRoyalty(Type royalty) {
        return (royalty == Type.WHITE) ? Type.BLACK : Type.WHITE;
    }

    public static Pair<Integer, Integer> getSortedPairForLoop(int x, int y) {
        if (x < y) {
            return new Pair<Integer, Integer>(x, y);
        }
        return new Pair<Integer, Integer>(y, x);
    }

    // This will convert B2 --> 22 | C4 --> 34
    public static String convertCharNumPosToNumNum(String pos) throws InvalidPositionFormatException {
        if (pos.length() > 2 || !Pattern.matches("[A-H][1-8]", pos))
            throw new InvalidPositionFormatException("Failed to recognize pos " + pos);
        String newPos = "";
        int firstPos = (pos.charAt(0) - 'A') + 1;
        return newPos.concat(firstPos + pos.substring(1));
    }

    public static int getXfromCharNum(String pos) throws InvalidPositionFormatException {
        return Integer.parseInt(convertCharNumPosToNumNum(pos).substring(0,1));
    }

    public static int getYfromCharNum(String pos) throws InvalidPositionFormatException {
        return Integer.parseInt(convertCharNumPosToNumNum(pos).substring(1));
    }

    public static int getXfromNumNum(String pos) {
        return Integer.parseInt(pos.substring(0, 1));
    }

    public static int getYfromNumNum(String pos) {
        return Integer.parseInt(pos.substring(1));
    }

    public static Position getPositionFromCharNum(String pos) throws InvalidPositionFormatException {
        int x = getXfromCharNum(pos);
        int y = getYfromCharNum(pos);
        return getPosFromCoord(x, y);
    }

    /*
        Purpose: useful for capturing check
        Return: return type (BLACK/WHITE) of piece 1 if are opposite. Otherwise, return null
     */
    public Type checkOppositeType(Piece piece1, Piece piece2) {
        if (piece1.getType() != piece2.getType()) return piece1.getType();
        return null;
    }

    private BoardHelper() {
    }


}
