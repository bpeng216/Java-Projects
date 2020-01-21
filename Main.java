import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Variable declaration
        int huh;
        String[][] board = new String[6][7];
        String turn = "X";
        int move;
        Scanner s = new Scanner(System.in);
        boolean AI = false;
        System.out.println("Do you want to face AI (1 = yes, 2 = no)");
        huh = s.nextInt();
        System.out.println(huh);
        if (1 == huh) {
            AI = true;
        } else {
            AI = false;
        }
        //Start of game
        for (String[] row: board) {
            Arrays.fill(row, " ");
        }
        while (checkVictory(board)) {
            if (!AI && turn == "O" || turn == "X") {
                printBoard(board, turn, AI);
                System.out.println("Choose a column 1-7:");
                move = s.nextInt();
                makeMove(board, turn, move, AI);

            } else {
                move = 1;
                makeMove(board, turn, move, AI);
            }

            //make sure move is available, switch turns
            if (!AI) {
                if (move >= 1 && move <= 7 && board[0][move - 1] == " ") {
                    if (turn == "X") {
                        turn = "O";
                    } else {
                        turn = "X";
                    }
                } else {
                    System.out.println("==========================\nTHAT COLUMN IS FULL\n==========================");
                }
            } else {
                if (turn == "X") {
                    turn = "O";
                } else {
                    turn = "X";
                }
            }
        }
    }
    public static void printBoard(String[][] array, String turn, boolean AI) {
        if (!AI) {
            System.out.println("__________________________\nIt is " + turn + "'s turn to move");
        } else {
            System.out.println("__________________________\nIt is the player's turn to move");
        }
        for (String[] row: array) {
            System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
        }
    }
    public static void makeMove(String[][] array, String turn, int move, boolean AI) {
        //
        if (turn == "X" || turn == "O" && AI == false) {

            if (move >= 1 && move <= 7) {
                for (int i = 5; i >= 0; i--) {
                    if (array[i][move - 1] == " ") {
                        array[i][move - 1] = turn;
                        break;
                    }
                }
            }
            //=============================
            //AI code
            //=============================
        }

        if (turn == "O" && AI) {
            //create all possible moves
            for (int v = 0; v <= 6; v++) {
                for (int l = 5; l >= 0; l--) {
                    if (array[l][v] == " ") {
                        array[l][v] = Integer.toString(v);
                        break;
                    }
                }
            }
            //for (String[] row : array) {
            //    System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
            //}
            //MAKING THE MOVE
            String winning;
            int error;
            int streak;
            int idk;
            int choice = -1;
            boolean moveMade = false;
            //1st priority: winning the game or defending

            for (int r = 0; r <= 5; r++) {
                for (int c = 0; c <= 6; c++) {
                    //if the tile has a piece in it

                    if (array[r][c] != " ") {
                        winning = array[r][c];
                        //UP and DOWN
                        streak = 0;
                        idk = 0;
                        error = 0;
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                            if (r - 1 >= 0 && (array[r - 1][c] == "X" || array[r - 1][c] == "O")) {
                                winning = array[r][c - 1];
                            } else {
                                winning = "X";
                            }
                        }
                        while (idk <= 3) {
                            if (r - idk >= 0) {
                                if (winning == array[r - idk][c]) {
                                    streak += 1;
                                }
                                if (array[r - idk][c] != "X" && array[r - idk][c] != "O" && array[r - idk][c] != " ") {
                                    error += 1;
                                    streak += 1;
                                    choice = Integer.valueOf(array[r - idk][c]);
                                }
                            }
                            idk++;
                            if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                for (int i = 5; i >= 0; i--) {
                                    if (array[i][choice] != "O" && array[i][choice] != "X") {
                                        array[i][choice] = "O";
                                        moveMade = true;
                                        break;
                                    }
                                }
                            } else if (error > 1) {
                                choice = -1;
                            }
                        }

                        //LEFT and RIGHT
                        streak = 0;
                        idk = 0;
                        error = 0;
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                            if (c - 1 >= 0 && (array[r][c - 1] == "X" || array[r][c - 1] == "O")) {
                                winning = array[r][c - 1];
                            } else {
                                winning = "X";
                            }
                        }
                        while (idk <= 3) {
                            if (c - idk >= 0) {
                                if (winning == array[r][c - idk]) {
                                    streak += 1;
                                }
                                if (array[r][c - idk] != "X" && array[r][c - idk] != "O" && array[r][c - idk] != " ") {
                                    error += 1;
                                    streak += 1;
                                    choice = Integer.valueOf(array[r][c - idk]);
                                }
                            }
                            idk++;
                            if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                for (int i = 5; i >= 0; i--) {
                                    if (array[i][choice] != "O" && array[i][choice] != "X") {
                                        array[i][choice] = "O";
                                        moveMade = true;

                                        break;
                                    }
                                }
                            } else if (error > 1) {
                                choice = -1;
                            }
                        }
                        //DOWN DIAGONAL
                        streak = 0;
                        idk = 0;
                        error = 0;
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                            if (c - 1 >= 0 && r - 1 >= 0 && (array[r - 1][c - 1] == "X" || array[r - 1][c - 1] == "O")) {
                                winning = array[r - 1][c - 1];
                            } else {
                                winning = "X";
                            }
                        }
                        while (idk <= 3) {
                            if (c - idk >= 0 && r - idk >= 0) {
                                if (winning == array[r - idk][c - idk]) {
                                    streak += 1;
                                }
                                if (array[r - idk][c - idk] != "X" && array[r - idk][c - idk] != "O" && array[r - idk][c - idk] != " ") {
                                    error += 1;
                                    streak += 1;
                                    choice = Integer.valueOf(array[r - idk][c - idk]);
                                }
                            }
                            idk++;
                            if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                for (int i = 5; i >= 0; i--) {
                                    if (array[i][choice] != "O" && array[i][choice] != "X") {
                                        array[i][choice] = "O";
                                        moveMade = true;

                                        break;
                                    }
                                }
                            } else if (error > 1) {
                                choice = -1;
                            }
                        }
                        //UP DIAGONAL
                        streak = 0;
                        idk = 0;
                        error = 0;
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                            if (c - 1 >= 0 && r + 1 <= 5 && (array[r + 1][c - 1] == "X" || array[r + 1][c - 1] == "O")) {
                                winning = array[r + 1][c - 1];
                            } else {
                                winning = "X";
                            }
                        }
                        while (idk <= 3) {
                            if (c - idk >= 0 && r + idk <= 5) {
                                if (winning == array[r + idk][c - idk]) {
                                    streak += 1;
                                }
                                if (array[r + idk][c - idk] != "X" && array[r + idk][c - idk] != "O" && array[r + idk][c - idk] != " ") {
                                    error += 1;
                                    streak += 1;
                                    choice = Integer.valueOf(array[r + idk][c - idk]);
                                }
                            }

                            idk++;
                            if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                for (int i = 5; i >= 0; i--) {
                                    if (array[i][choice] != "O" && array[i][choice] != "X") {
                                        array[i][choice] = "O";
                                        moveMade = true;

                                        break;
                                    }
                                }
                            } else if (error > 1) {
                                choice = -1;
                            }
                        }
                    }
                }
            }


            //2nd priority: making sure the player doesn't get 3 in a row to prep for a victory
            if (!moveMade) {
                for (int r = 0; r <= 5; r++) {
                    for (int c = 0; c <= 6; c++) {
                        //if the tile has a piece in it

                        if (array[r][c] != " ") {
                            winning = array[r][c];
                            //UP and DOWN
                            streak = 1;
                            idk = 0;
                            error = 0;
                            if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                                if (r - 1 >= 0 && (array[r - 1][c] == "X" || array[r - 1][c] == "O")) {
                                    winning = array[r - 1][c];
                                } else {
                                    winning = "X";
                                }
                            }
                            while (idk <= 2) {
                                if (r - idk >= 0) {
                                    if (winning == array[r - idk][c]) {
                                        streak += 1;
                                    }
                                    if (array[r - idk][c] != "X" && array[r - idk][c] != "O" && array[r - idk][c] != " ") {
                                        error += 1;
                                        streak += 1;
                                        choice = Integer.valueOf(array[r - idk][c]);
                                    }
                                }
                                idk++;
                                if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                    for (int r2 = 5; r2 >= 0; r2--) {
                                        if (array[r2][choice] != "O" && array[r2][choice] != "X") {
                                            if (array[r2][choice] != "X" && array[r2][choice] != "O") {
                                                //CHECK IF MOVE IS GOOD
                                                //UP and DOWN
                                                boolean badMove = false;
                                                streak = 1;
                                                idk = 1;
                                                while (idk <= 3) {
                                                    if (r2 - idk - 1 >= 0) {
                                                        if (array[r2 - idk - 1][choice] == "X") {
                                                            streak += 1;
                                                        }
                                                    }
                                                    idk++;
                                                    if (streak == 4) {
                                                        badMove = true;
                                                    }
                                                }
                                                //LEFT and RIGHT
                                                streak = 1;
                                                idk = 1;
                                                while (idk <= 3) {
                                                    if (choice - idk >= 0) {
                                                        if (array[r2 - 1][choice - idk] == "X") {
                                                            streak += 1;
                                                        }
                                                    }
                                                    idk++;
                                                    if (streak == 4) {

                                                        badMove = true;
                                                    }
                                                }
                                                //DOWN DIAGONAL
                                                streak = 1;
                                                idk = 1;
                                                while (idk <= 3) {
                                                    if (choice - idk >= 0 && r2 - idk - 1 >= 0) {
                                                        if ("X" == array[r2 - idk - 1][choice - idk]) {
                                                            streak += 1;
                                                        }
                                                    }
                                                    idk++;
                                                    if (streak == 4) {
                                                        badMove = true;
                                                    }
                                                }
                                                //UP DIAGONAL
                                                streak = 1;
                                                idk = 1;
                                                while (idk <= 3) {
                                                    if (c - idk >= 0 && r2 + idk - 1 <= 5) {
                                                        if ("X" == array[r2 + idk - 1][c - idk]) {
                                                            streak += 1;
                                                        }
                                                    }
                                                    idk++;
                                                    if (streak == 4) {
                                                        badMove = true;
                                                    }
                                                }
                                                if (!badMove) {
                                                    array[r2][choice] = "O";
                                                    moveMade = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else if (error > 1) {
                                    choice = -1;
                                }
                            }

                            //LEFT and RIGHT
                            streak = 1;
                            idk = 0;
                            error = 0;
                            if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                                if (c - 1 >= 0 && (array[r][c - 1] == "X" || array[r][c - 1] == "O")) {
                                    winning = array[r][c - 1];
                                } else {
                                    winning = "X";
                                }
                            }
                            while (idk <= 2) {
                                if (c - idk >= 0) {
                                    if (winning == array[r][c - idk]) {
                                        streak += 1;
                                    }
                                    if (array[r][c - idk] != "X" && array[r][c - idk] != "O" && array[r][c - idk] != " ") {
                                        error += 1;
                                        streak += 1;
                                        choice = Integer.valueOf(array[r][c - idk]);
                                    }
                                }
                                idk++;
                                if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                    for (int r2 = 5; r2 >= 0; r2--) {
                                        if (array[r2][choice] != "X" && array[r2][choice] != "O") {
                                            //CHECK IF MOVE IS GOOD
                                            //UP and DOWN
                                            boolean badMove = false;
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (r2 - idk - 1 >= 0) {
                                                    if (array[r2 - idk - 1][choice] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //LEFT and RIGHT
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0) {
                                                    if (array[r2 - 1][choice - idk] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {

                                                    badMove = true;
                                                }
                                            }
                                            //DOWN DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0 && r2 - idk - 1 >= 0) {
                                                    if ("X" == array[r2 - idk - 1][choice - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //UP DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (c - idk >= 0 && r2 + idk - 1 <= 5) {
                                                    if ("X" == array[r2 + idk - 1][c - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            if (!badMove) {
                                                array[r2][choice] = "O";
                                                moveMade = true;
                                                break;
                                            }
                                        }
                                    }
                                } else if (error > 1) {
                                    choice = -1;
                                }
                            }
                            //DOWN DIAGONAL
                            streak = 1;
                            idk = 0;
                            error = 0;
                            if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                                if (c - 1 >= 0 && r - 1 >= 0 && (array[r - 1][c - 1] == "X" || array[r - 1][c - 1] == "O")) {
                                    winning = array[r - 1][c - 1];
                                } else {
                                    winning = "X";
                                }
                            }
                            while (idk <= 2) {
                                if (c - idk >= 0 && r - idk >= 0) {
                                    if (winning == array[r - idk][c - idk]) {
                                        streak += 1;
                                    }
                                    if (array[r - idk][c - idk] != "X" && array[r - idk][c - idk] != "O" && array[r - idk][c - idk] != " ") {
                                        error += 1;
                                        streak += 1;
                                        choice = Integer.valueOf(array[r - idk][c - idk]);
                                    }
                                }
                                idk++;
                                if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                    for (int r2 = 5; r2 >= 0; r2--) {
                                        if (array[r2][choice] != "X" && array[r2][choice] != "O") {
                                            //CHECK IF MOVE IS GOOD
                                            //UP and DOWN
                                            boolean badMove = false;
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (r2 - idk - 1 >= 0) {
                                                    if (array[r2 - idk - 1][choice] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //LEFT and RIGHT
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0) {
                                                    if (array[r2 - 1][choice - idk] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {

                                                    badMove = true;
                                                }
                                            }
                                            //DOWN DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0 && r2 - idk - 1 >= 0) {
                                                    if ("X" == array[r2 - idk - 1][choice - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //UP DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (c - idk >= 0 && r2 + idk - 1 <= 5) {
                                                    if ("X" == array[r2 + idk - 1][c - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            if (!badMove) {
                                                array[r2][choice] = "O";
                                                moveMade = true;
                                                break;
                                            }
                                        }
                                    }
                                } else if (error > 1) {
                                    choice = -1;
                                }
                            }
                            //UP DIAGONAL
                            streak = 1;
                            idk = 0;
                            error = 0;
                            if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                                if (c - 1 >= 0 && r + 1 <= 5 && (array[r + 1][c - 1] == "X" || array[r + 1][c - 1] == "O")) {
                                    winning = array[r + 1][c - 1];
                                } else {
                                    winning = "X";
                                }
                            }
                            while (idk <= 2) {
                                if (c - idk >= 0 && r + idk <= 5) {
                                    if (winning == array[r + idk][c - idk]) {
                                        streak += 1;
                                    }
                                    if (array[r + idk][c - idk] != "X" && array[r + idk][c - idk] != "O" && array[r + idk][c - idk] != " ") {
                                        error += 1;
                                        streak += 1;
                                        choice = Integer.valueOf(array[r + idk][c - idk]);
                                    }
                                }

                                idk++;
                                if (streak == 4 && choice >= 0 && error == 1 && !moveMade) {
                                    for (int r2 = 5; r2 >= 0; r2--) {
                                        if (array[r2][choice] != "X" && array[r2][choice] != "O") {
                                            //CHECK IF MOVE IS GOOD
                                            //UP and DOWN
                                            boolean badMove = false;
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (r2 - idk - 1 >= 0) {
                                                    if (array[r2 - idk - 1][choice] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //LEFT and RIGHT
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0) {
                                                    if (array[r2 - 1][choice - idk] == "X") {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {

                                                    badMove = true;
                                                }
                                            }
                                            //DOWN DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (choice - idk >= 0 && r2 - idk - 1 >= 0) {
                                                    if ("X" == array[r2 - idk - 1][choice - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            //UP DIAGONAL
                                            streak = 1;
                                            idk = 1;
                                            while (idk <= 3) {
                                                if (c - idk >= 0 && r2 + idk - 1 <= 5) {
                                                    if ("X" == array[r2 + idk - 1][c - idk]) {
                                                        streak += 1;
                                                    }
                                                }
                                                idk++;
                                                if (streak == 4) {
                                                    badMove = true;
                                                }
                                            }
                                            if (!badMove) {
                                                array[r2][choice] = "O";
                                                moveMade = true;
                                                break;
                                            }
                                        }
                                    }
                                } else if (error > 1) {
                                    choice = -1;
                                }
                            }
                        }
                    }
                }
            }
            //last priority: Making a random move
            if (!moveMade) {
                for (int r = 0; r <= 5; r++) {
                    for (int c = 0; c <= 6; c++) {
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " " && !moveMade) {
                            if (r - 1 >= 0) {
                                //UP and DOWN
                                boolean badMove = false;
                                streak = 1;
                                idk = 1;
                                while (idk <= 3) {
                                    if (r - idk - 1 >= 0) {
                                        if (array[r - idk - 1][c] == "X") {
                                            streak += 1;
                                        }
                                    }

                                    idk++;
                                    if (streak == 4) {
                                        badMove = true;
                                    }
                                }
                                //LEFT and RIGHT
                                streak = 1;
                                idk = 1;
                                while (idk <= 3) {
                                    if (c - idk >= 0) {
                                        if (array[r - 1][c - idk] == "X") {
                                            streak += 1;
                                        }
                                    }

                                    idk++;
                                    if (streak == 4) {

                                        badMove = true;
                                    }
                                }
                                //DOWN DIAGONAL
                                streak = 1;
                                idk = 1;
                                while (idk <= 3) {
                                    if (c - idk >= 0 && r - idk - 1 >= 0) {
                                        if ("X" == array[r - idk - 1][c - idk]) {
                                            streak += 1;
                                        }
                                    }

                                    idk++;
                                    if (streak == 4) {
                                        badMove = true;

                                    }
                                }
                                //UP DIAGONAL
                                streak = 1;
                                idk = 1;
                                while (idk <= 3) {
                                    if (c - idk >= 0 && r + idk - 1 <= 5) {
                                        if ("X" == array[r + idk - 1][c - idk]) {
                                            streak += 1;
                                        }
                                    }

                                    idk++;
                                    if (streak == 4) {
                                        badMove = true;

                                    }
                                    if (!badMove) {
                                        array[r][c] = "O";
                                        moveMade = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //And if there are absolutely no good moves left, basically gg
            if (!moveMade) {
                for (int r = 0; r <= 5; r++) {
                    for (int c = 0; c <= 6; c++) {
                        if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {

                        }
                    }
                }
            }



            //delete placeholders
            for (int r = 0; r <= 5; r++) {
                for (int c = 0; c <= 6; c++) {
                    //if the tile has a piece in it
                    if (array[r][c] != "X" && array[r][c] != "O" && array[r][c] != " ") {
                        array[r][c] = " ";
                    }
                }
            }
        }
    }

    public static boolean checkVictory(String[][] array) {
        String winning;
        int idk;
        int streak;
        //iterates through board
        for (int r = 0; r <= 5; r++) {
            for (int c = 0; c <= 6; c++) {
                //if the tile has a piece in it
                if (!(array[r][c] == " ")) {
                    winning = array[r][c];

                    //UP and DOWN
                    streak = 1;
                    idk = 1;
                    while (idk <= 3) {
                        if (r - idk >= 0) {
                            if (winning == array[r - idk][c]) {
                                streak += 1;
                            }
                        }

                        idk++;
                        if (streak == 4) {
                            System.out.println("__________________________");
                            for (String[] row: array) {
                                System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
                            }
                            System.out.println("==========================\n" + winning + " wins!\n==========================");

                            return false;
                        }
                    }
                    //LEFT and RIGHT
                    streak = 1;
                    idk = 1;
                    while (idk <= 3) {
                        if (c - idk >= 0) {
                            if (winning == array[r][c - idk]) {
                                streak += 1;
                            }
                        }

                        idk++;
                        if (streak == 4) {
                            System.out.println("__________________________");
                            for (String[] row: array) {
                                System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
                            }
                            System.out.println("==========================\n" + winning + " wins!\n==========================");

                            return false;
                        }
                    }
                    //DOWN DIAGONAL
                    streak = 1;
                    idk = 1;
                    while (idk <= 3) {
                        if (c - idk >= 0 && r - idk >= 0) {
                            if (winning == array[r - idk][c - idk]) {
                                streak += 1;
                            }
                        }

                        idk++;
                        if (streak == 4) {
                            System.out.println("__________________________");
                            for (String[] row: array) {
                                System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
                            }
                            System.out.println("==========================\n" + winning + " wins!\n==========================");

                            return false;
                        }
                    }
                    //UP DIAGONAL
                    streak = 1;
                    idk = 1;
                    while (idk <= 3) {
                        if (c - idk >= 0 && r + idk <= 5) {
                            if (winning == array[r + idk][c - idk]) {
                                streak += 1;
                            }
                        }

                        idk++;
                        if (streak == 4) {
                            System.out.println("__________________________");
                            for (String[] row: array) {
                                System.out.println(Arrays.toString(row).replaceAll("[,]", " |"));
                            }
                            System.out.println("==========================\n" + winning + " wins!\n==========================");

                            return false;
                        }
                    }
                }
            }
        }

        //System.out.println(two);

        return true;
    }
}