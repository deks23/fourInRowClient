package pl.damiankotynia.fourinrow.client.service;

import pl.damiankotynia.fourinrow.client.exceptions.FullColumnException;
import pl.damiankotynia.fourinrow.model.GameField;

public class MoveService {
    private GameField gameField;
    private int playerPawn;

    public MoveService(int playerSign) {
        gameField = new GameField();
        playerPawn = playerSign;
    }

    /**
     * Method checks first avalible slot in which user can add his Pawn
     * @param columnIndex
     * @return avalible slot
     * @throws FullColumnException when column is full
     */
    public int getFirstAvalibleSlot(int columnIndex) throws FullColumnException {
        int[] column = getColumn(columnIndex);
        for (int i = column.length-1; i>=0; i--){
            if(column[i]==0){
                return i;

            }
        }
        throw new FullColumnException();
    }

    /**
     * Method sets chosen game board cell value with player id
     * @param row
     * @param column
     * @return game board after update
     */
    public GameField performMove(int row, int column){
        gameField.getGameBoard()[row][column] = this.playerPawn;
        return gameField;
    }

    private int[] getColumn(int column){
        int[][] gameBoard = gameField.getGameBoard();
        int[] columnArray = new int[gameBoard.length];

        for (int i = 0; i < gameBoard.length; i++) {
            columnArray[i] = gameBoard[i][column];
        }
        return columnArray;
    }

    public int getPlayerPawn() {
        return playerPawn;
    }

    public void setPlayerPawn(int playerPawn) {
        this.playerPawn = playerPawn;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }
}
