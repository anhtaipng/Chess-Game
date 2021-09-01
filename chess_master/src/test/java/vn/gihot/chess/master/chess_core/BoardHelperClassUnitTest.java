package vn.gihot.chess.master.chess_core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import vn.gihot.chess.master.model.game.board.Board;
import vn.gihot.chess.master.model.game.board.BoardHelper;
import vn.gihot.chess.master.model.game.board.ClassicBoard;
import vn.gihot.chess.master.model.game.board.Position;
import vn.gihot.chess.master.model.game.piece.Pawn;
import vn.gihot.chess.master.model.game.piece.Piece;
import vn.gihot.chess.master.model.move.MoveInfo;
import vn.gihot.chess.master.utilclass.Pair;

import java.util.ArrayList;

public class BoardHelperClassUnitTest {
//    ClassicBoard board = new ClassicBoard();

    @Test
    @DisplayName("Test Hasher1")
    void testHasher1() {
        Assertions.assertEquals(63, BoardHelper.hashPositionCoord(8, 8));
    }

    @Test
    @DisplayName("Test Hasher2")
    void testHasher2() {
        Assertions.assertEquals(0, BoardHelper.hashPositionCoord(1, 1));
    }


    @Test
    @DisplayName("Test Hasher3")
    void testHasher3() {
        Assertions.assertEquals(12, BoardHelper.hashPositionCoord(5, 2));
    }

    @Test
    @DisplayName("Test Hasher4")
    void testHasher4() {
        Assertions.assertEquals(35, BoardHelper.hashPositionCoord(4, 5));
    }

    @Test
    @DisplayName("Test Hasher5")
    void testHasher5() {
        Assertions.assertEquals(36, BoardHelper.hashPositionCoord(5, 5));
    }

    @Test
    @DisplayName("Test Get Pos from Coordinates")
    void testCoord() {
        Assertions.assertEquals(new Position(3, 4), BoardHelper.getPosFromCoord(3, 4));
    }

    @Test
    @DisplayName("Test Get Pos from Coordinates")
    void testCoord1() {
        Assertions.assertEquals(new Position(1, 1), BoardHelper.getPosFromCoord(1, 1));
    }

    @Test
    @DisplayName("Test Get Pos from Coordinates")
    void testCoord2() {
        Assertions.assertEquals(new Position(8, 7), BoardHelper.getPosFromCoord(8, 7));
    }

    @Test
    @DisplayName("Test Get Pos from Coordinates")
    void testCoord3() {
        Assertions.assertEquals(new Position(8, 1), BoardHelper.getPosFromCoord(8, 1));
    }

    @Test
    @DisplayName("Test Pair Sorting Function")
    void testPairSort() {
        Assertions.assertEquals(new Pair<Integer, Integer>(3, 8), BoardHelper.getSortedPairForLoop(8, 3));
    }

    @Test
    @DisplayName("Test Pair Sorting Function")
    void testPairSort1() {
        Assertions.assertEquals(new Pair<Integer, Integer>(2, 5), BoardHelper.getSortedPairForLoop(2, 5));
    }


    @Test
    @DisplayName("Test Get Pos from Coordinates")
    void testCoord4() {
        Assertions.assertEquals(new Position(1, 8), BoardHelper.getPosFromCoord(1, 8));
    }

    @Test
    @DisplayName("Test get connected positions: Vertical Line")
    void testBetween1() {
        ArrayList<Position> expectedPosList = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            expectedPosList.add(BoardHelper.getPosFromCoord(1, i));
        }
        Assertions.assertEquals(expectedPosList,
                BoardHelper.getConnectedPositionBetweenPos(BoardHelper.getPosFromCoord(1, 1),
                        BoardHelper.getPosFromCoord(1, 8))
        );
    }

    @Test
    @DisplayName("Test get connected positions: Horizontal Line")
    void testBetween2() {
        ArrayList<Position> expectedPosList = new ArrayList<>();
        for (int i = 3; i <= 8; i++) {
            expectedPosList.add(BoardHelper.getPosFromCoord(i, 4));
        }
        Assertions.assertEquals(expectedPosList,
                BoardHelper.getConnectedPositionBetweenPos(BoardHelper.getPosFromCoord(3, 4),
                        BoardHelper.getPosFromCoord(8, 4))
        );
    }

    @Test
    @DisplayName("Test get connected positions: First Diagonal")
    void testBetween3() {
        ArrayList<Position> expectedPosList = new ArrayList<>();
        expectedPosList.add(BoardHelper.getPosFromCoord(1, 8));
        expectedPosList.add(BoardHelper.getPosFromCoord(2, 7));
        expectedPosList.add(BoardHelper.getPosFromCoord(3, 6));
        expectedPosList.add(BoardHelper.getPosFromCoord(4, 5));
        expectedPosList.add(BoardHelper.getPosFromCoord(5, 4));
        expectedPosList.add(BoardHelper.getPosFromCoord(6, 3));
        expectedPosList.add(BoardHelper.getPosFromCoord(7, 2));
        expectedPosList.add(BoardHelper.getPosFromCoord(8, 1));
        Assertions.assertEquals(expectedPosList,
                BoardHelper.getConnectedPositionBetweenPos(BoardHelper.getPosFromCoord(1, 8),
                        BoardHelper.getPosFromCoord(8, 1))
        );
    }

    @Test
    @DisplayName("Test get connected positions: Second Diagonal")
    void testBetween4() {
        ArrayList<Position> expectedPosList = new ArrayList<>();
        expectedPosList.add(BoardHelper.getPosFromCoord(2, 1));
        expectedPosList.add(BoardHelper.getPosFromCoord(3, 2));
        expectedPosList.add(BoardHelper.getPosFromCoord(4, 3));
        expectedPosList.add(BoardHelper.getPosFromCoord(5, 4));
        expectedPosList.add(BoardHelper.getPosFromCoord(6, 5));
        expectedPosList.add(BoardHelper.getPosFromCoord(7, 6));
        expectedPosList.add(BoardHelper.getPosFromCoord(8, 7));
        Assertions.assertEquals(expectedPosList,
                BoardHelper.getConnectedPositionBetweenPos(BoardHelper.getPosFromCoord(2, 1),
                        BoardHelper.getPosFromCoord(8, 7))
        );
    }

    @Test
    @DisplayName("Convert CharNum Position Notation to NumNum Notation")
    void testConvertCharNumToNumNum() {
        try {
            assertEquals("11", BoardHelper.convertCharNumPosToNumNum("A1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Convert CharNum Position Notation to NumNum Notation")
    void testConvertCharNumToNumNum0() {
        try {
            assertEquals("31", BoardHelper.convertCharNumPosToNumNum("C1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Convert CharNum Position Notation to NumNum Notation")
    void testConvertCharNumToNumNum1() {
        try {
            assertEquals("51", BoardHelper.convertCharNumPosToNumNum("E1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Convert CharNum Position Notation to NumNum Notation")
    void testConvertCharNumToNumNum2() {
        try {
            assertEquals("65", BoardHelper.convertCharNumPosToNumNum("F5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Convert CharNum Position Notation to NumNum Notation")
    void testConvertCharNumToNumNum3() {
        try {
            assertEquals("73", BoardHelper.convertCharNumPosToNumNum("G3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction() {
        try {
            assertEquals("73", BoardHelper.convertCharNumPosToNumNum("G3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction1() {
        try {
            assertEquals(7, BoardHelper.getXfromCharNum("G3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction2() {
        try {
            assertEquals(4, BoardHelper.getXfromCharNum("D3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction3() {
        try {
            assertEquals(2, BoardHelper.getXfromCharNum("B3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction4() {
        try {
            assertEquals(2, BoardHelper.getXfromCharNum("B3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract X from CharNum")
    void testCharNumXextraction5() {
        try {
            assertEquals(8, BoardHelper.getXfromCharNum("H3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction() {
        try {
            assertEquals(3, BoardHelper.getYfromCharNum("G3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction1() {
        try {
            assertEquals(1, BoardHelper.getYfromCharNum("G1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction2() {
        try {
            assertEquals(8, BoardHelper.getYfromCharNum("D8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction3() {
        try {
            assertEquals(5, BoardHelper.getYfromCharNum("B5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction4() {
        try {
            assertEquals(6, BoardHelper.getYfromCharNum("B6"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("extract Y from CharNum")
    void testCharNumYextraction5() {
        try {
            assertEquals(4, BoardHelper.getYfromCharNum("H4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    @DisplayName("Get position from charnum")
    void testGetPosFromCharNum() {
        try {
            assertEquals(new Position(8, 4), BoardHelper.getPositionFromCharNum("H4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get position from charnum")
    void testGetPosFromCharNum1() {
        try {
            assertEquals(new Position(6, 5), BoardHelper.getPositionFromCharNum("F5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get position from charnum")
    void testGetPosFromCharNum2() {
        try {
            assertEquals(new Position(5, 1), BoardHelper.getPositionFromCharNum("E1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get position from charnum")
    void testGetPosFromCharNum3() {
        try {
            assertEquals(new Position(3, 3), BoardHelper.getPositionFromCharNum("C3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get position from charnum")
    void testGetPosFromCharNum4() {
        try {
            assertEquals(new Position(4, 2), BoardHelper.getPositionFromCharNum("D2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
