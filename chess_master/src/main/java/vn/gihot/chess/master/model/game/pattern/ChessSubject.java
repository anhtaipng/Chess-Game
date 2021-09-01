package vn.gihot.chess.master.model.game.pattern;

public interface ChessSubject {

        public void registerObserver(ChessObserver o);
        public void removeObserver(ChessObserver o);
        public void notifyObservers();

}
