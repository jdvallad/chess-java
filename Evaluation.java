public class Evaluation {
    static final float PAWN = 1.0f;
    static final float KNIGHT = 3.0f;
    static final float BISHOP = 3.0f;
    static final float ROOK = 5.0f;
    static final float QUEEN = 9.0f;
    static final float KING = 90.0f;
    static final float[][] PT = new float[][]{
            {0, 0, 0, 0, 0, 0, 0, 0,},
            {50, 50, 50, 50, 50, 50, 50, 50,},
            {10, 10, 20, 30, 30, 20, 10, 10,},
            {5, 5, 10, 25, 25, 10, 5, 5,},
            {0, 0, 0, 20, 20, 0, 0, 0,},
            {5, -5, -10, 0, 0, -10, -5, 5,},
            {5, 10, 10, -20, -20, 10, 10, 5,},
            {0, 0, 0, 0, 0, 0, 0, 0,}};
    static final float[][] NT = new float[][]{
            {-50, -40, -30, -30, -30, -30, -40, -50,},
            {-40, -20, 0, 0, 0, 0, -20, -40,},
            {-30, 0, 10, 15, 15, 10, 0, -30,},
            {-30, 5, 15, 20, 20, 15, 5, -30,},
            {-30, 0, 15, 20, 20, 15, 0, -30,},
            {-30, 5, 10, 15, 15, 10, 5, -30,},
            {-40, -20, 0, 5, 5, 0, -20, -40,},
            {-50, -40, -30, -30, -30, -30, -40, -50,}};
    static final float[][] BT = new float[][]{
            {-20, -10, -10, -10, -10, -10, -10, -20,},
            {-10, 0, 0, 0, 0, 0, 0, -10,},
            {-10, 0, 5, 10, 10, 5, 0, -10,},
            {-10, 5, 5, 10, 10, 5, 5, -10,},
            {-10, 0, 10, 10, 10, 10, 0, -10,},
            {-10, 10, 10, 10, 10, 10, 10, -10,},
            {-10, 5, 0, 0, 0, 0, 5, -10,},
            {-20, -10, -10, -10, -10, -10, -10, -20,}};
    static final float[][] RT = new float[][]{
            {0, 0, 0, 0, 0, 0, 0, 0,},
            {5, 10, 10, 10, 10, 10, 10, 5,},
            {-5, 0, 0, 0, 0, 0, 0, -5,},
            {-5, 0, 0, 0, 0, 0, 0, -5,},
            {-5, 0, 0, 0, 0, 0, 0, -5,},
            {-5, 0, 0, 0, 0, 0, 0, -5,},
            {-5, 0, 0, 0, 0, 0, 0, -5,},
            {0, 0, 0, 5, 5, 0, 0, 0,}};
    static final float[][] QT = new float[][]{
            {-20, -10, -10, -5, -5, -10, -10, -20,},
            {-10, 0, 0, 0, 0, 0, 0, -10,},
            {-10, 0, 5, 5, 5, 5, 0, -10,},
            {-5, 0, 5, 5, 5, 5, 0, -5,},
            {0, 0, 5, 5, 5, 5, 0, -5,},
            {-10, 5, 5, 5, 5, 5, 0, -10,},
            {-10, 0, 5, 0, 0, 0, 0, -10,},
            {-20, -10, -10, -5, -5, -10, -10, -20,}};
    static final float[][] KMT = new float[][]{
            {-30, -40, -40, -50, -50, -40, -40, -30,},
            {-30, -40, -40, -50, -50, -40, -40, -30,},
            {-30, -40, -40, -50, -50, -40, -40, -30,},
            {-30, -40, -40, -50, -50, -40, -40, -30,},
            {-20, -30, -30, -40, -40, -30, -30, -20,},
            {-10, -20, -20, -20, -20, -20, -20, -10,},
            {20, 20, 0, 0, 0, 0, 20, 20,},
            {20, 30, 10, 0, 0, 10, 30, 20,}};
    static final float[][] KET = new float[][]{
            {-50, -40, -30, -20, -20, -30, -40, -50,},
            {-30, -20, -10, 0, 0, -10, -20, -30,},
            {-30, -10, 20, 30, 30, 20, -10, -30,},
            {-30, -10, 30, 40, 40, 30, -10, -30,},
            {-30, -10, 30, 40, 40, 30, -10, -30,},
            {-30, -10, 20, 30, 30, 20, -10, -30,},
            {-30, -30, 0, 0, 0, 0, -30, -30,},
            {-50, -30, -30, -30, -30, -30, -30, -50,}
    };

    public static boolean endGame(chessboard board) {
        boolean whiteQueen = chessboard.longToStrings(board.pieces("Q")).size() == 1;
        boolean blackQueen = chessboard.longToStrings(board.pieces("q")).size() == 1;
        if (!(whiteQueen || blackQueen))
            return true;
        if (whiteQueen) {
            int minorPieceCount = chessboard.longToStrings(board.pieces("B")).size()
                    + chessboard.longToStrings(board.pieces("N")).size();
            int rookCount = chessboard.longToStrings(board.pieces("R")).size();
            if (rookCount != 0)
                return false;
            if (minorPieceCount > 1)
                return false;
        }
        if (blackQueen) {
            int minorPieceCount = chessboard.longToStrings(board.pieces("b")).size()
                    + chessboard.longToStrings(board.pieces("n")).size();
            int rookCount = chessboard.longToStrings(board.pieces("r")).size();
            if (rookCount != 0)
                return false;
            return minorPieceCount <= 1;
        }
        return true;
    }

    public static float evaluate(chessboard board) {
        char[][] pieces = fillBoard(board);
        float res = 0;
        boolean endGame = endGame(board);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                float temp = switch (pieces[r][c]) {
                    case 'P' -> PAWN + (PT[r][c] / 100f);
                    case 'R' -> ROOK + (RT[r][c] / 100f);
                    case 'N' -> KNIGHT + (NT[r][c] / 100f);
                    case 'B' -> BISHOP + (BT[r][c] / 100f);
                    case 'Q' -> QUEEN + (QT[r][c] / 100f);
                    case 'K' -> KING + ((endGame ? KET[r][c] : KMT[r][c]) / 100f);
                    case 'p' -> -PAWN - (PT[7 - r][7 - c] / 100f);
                    case 'r' -> -ROOK - (RT[7 - r][7 - c] / 100f);
                    case 'n' -> -KNIGHT - (NT[7 - r][7 - c] / 100f);
                    case 'b' -> -BISHOP - (BT[7 - r][7 - c] / 100f);
                    case 'q' -> -QUEEN - (QT[7 - r][7 - c] / 100f);
                    case 'k' -> -KING - ((endGame ? KET[7 - r][7 - c] : KMT[7 - r][7 - c]) / 100f);
                    default -> 0;
                };
                res += temp;
            }
        }
        if (board.gameOver) {
            switch (board.result.split(" ")[0]) {
                case "draw":
                    res = 0;
                    break;
                case "white":
                    res = 1000;
                    break;
                case "black":
                    res = -1000;
                    break;
                default:
            }
        }
        return res;
    }

    public static char[][] fillBoard(chessboard board) {
        char[][] res = new char[8][8];
        for (char ch : new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}) {
            for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8}) {
                res[8 - i][ch - 97] = board.pieceAt("" + ch + i);
            }
        }
        return res;
    }
}