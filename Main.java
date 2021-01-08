import processing.core.*;

public class Main extends PApplet {
    float widthP;
    float heightP;
    PImage[] images;
    PImage tempImage;
    String[] sounds;
    String move = "";
    String lastMove = "";
    SoundPlayer soundPlayer;
    char[][] pieceBoard;
    boolean startupFlag = false;

    static public void main(String[] passedArgs) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        try {
            String[] appletArgs = new String[]{"--present", "--window-color=#666666", "--stop-color=#cccccc", "Main"};
            if (passedArgs != null) {
                PApplet.main(concat(appletArgs, passedArgs));
            } else {
                PApplet.main(appletArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settings() {
        fullScreen();
        widthP = (float) displayWidth / 1920f;
        heightP =  (float) displayHeight/ 1080f;
        soundPlayer = new SoundPlayer();
        images = new PImage[20];
        sounds = new String[9];
        sounds[0] = ("./data/sounds/game_start.mp3");
        soundPlayer.play(sounds[0]);
        sounds[1] = ("./data/sounds/game_end.mp3");
        sounds[2] = ("./data/sounds/move_self.mp3");
        sounds[3] = ("./data/sounds/move_opponent.mp3");
        sounds[4] = ("./data/sounds/move_capture.mp3");
        sounds[5] = ("./data/sounds/move_check.mp3");
        sounds[6] = ("./data/sounds/move_castle.mp3");
        sounds[7] = ("./data/sounds/move_promote.mp3");
        sounds[8] = ("./data/sounds/move_illegal.mp3");
        images[0] = loadImage("./data/images/king_white.png");
        images[1] = loadImage("./data/images/king_black.png");
        images[2] = loadImage("./data/images/queen_white.png");
        images[3] = loadImage("./data/images/queen_black.png");
        images[4] = loadImage("./data/images/rook_white.png");
        images[5] = loadImage("./data/images/rook_black.png");
        images[6] = loadImage("./data/images/bishop_white.png");
        images[7] = loadImage("./data/images/bishop_black.png");
        images[8] = loadImage("./data/images/knight_white.png");
        images[9] = loadImage("./data/images/knight_black.png");
        images[10] = loadImage("./data/images/pawn_white.png");
        images[11] = loadImage("./data/images/pawn_black.png");
        images[12] = loadImage("./data/images/board.png");
        images[13] = loadImage("./data/images/rank_top.png");
        images[14] = loadImage("./data/images/rank_bottom.png");
        images[15] = loadImage("./data/images/file_left.png");
        images[16] = loadImage("./data/images/file_right.png");
        images[17] = loadImage("./data/images/default_board.png");
        images[18] = loadImage("./data/images/light_square.png");
        images[19] = loadImage("./data/images/dark_square.png");
        pieceBoard = new char[][]{
                {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
                {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}
        };
    }

    public void initialStartup() {
        drawScreen();
        tempImage = get();
        startupFlag = true;
    }

    public void draw() {
        if (!startupFlag) {
            initialStartup();
        }
        if(!mousePressed && mouseOnBoard() && move.length() == 0){
            int x = (int) ((( (mouseX)) - 448 * widthP) / (128 * widthP));
            int y = (int) ((( (mouseY)) - 28 * heightP) / (128 * heightP));
            if(pieceBoard[y][x] != ' '){
                background(tempImage);
                tempImage = get();
                fill(13, 213, 252, 80);
                strokeWeight(0);
                rect(447 + 128 * x, 27 + 128 * y, 129, 129);
            }
        }
        if (mousePressed && mouseButton == LEFT && move.length() == 2) {
            drawFloatingPiece(move);
        }
        if (move.length() == 4) {
            makeMove(move);
            move = "";
        }
        print("\r\n" + move);
        //print("\r\n"+convertFileToInt('a')+" "+(8-1));
    }

    public boolean mouseOnBoard() {
        return 448 * widthP < ((mouseX)) && ( (mouseX)) < 1472 * widthP && 28 * heightP < ( (mouseY)) && ((mouseY)) < 1052 * heightP;
    }
    public void mouseReleased() {

        if (move.length() == 2) {
            if (mouseOnBoard()) {

                int x = (int) ((( (mouseX)) - 448 * widthP) / (128 * widthP));
                char xFile = (char) (97 + x);
                int y = (int) ((( (mouseY)) - 28 * heightP) / (128 * heightP));
                int yRank = 8 - y;
                if ((move.equals("" + xFile + yRank))) {
                    move = "";
                    background(tempImage);
                } else {
                    move += "" + xFile + yRank;
                    lastMove = move;
                }
            } else {
                move = "";
                background(tempImage);
            }
        }
    }

    public void mousePressed() {
        if (mouseOnBoard() && mouseButton == LEFT && move.length() == 0) {
            background(tempImage);
            tempImage = get();
            int x = (int) ((( (mouseX)) - 448 * widthP) / (128 * widthP));
            char xFile = (char) (97 + x);
            int y = (int) ((( (mouseY)) - 28 * heightP) / (128 * heightP));
            int yRank = 8 - y;
            if (getPiece("" + xFile + yRank) != ' ') {
                move = "" + xFile + yRank;
            }
        }
    }

    public void drawScreen() {
        strokeWeight(0);
        background(173, 216, 230);
        fill(255);//board.turn ? 255 : 0);
        rect(448 - 20, 28 - 20, 1024 + 40, 1024 + 40);
        image(images[12], 448, 28, 1024, 1024);
        image(images[15], 448, 28);
        image(images[14], 448, 1052 - 38);
        drawLastMove();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                drawPiece(r, c);
            }
        }
        tempImage = get();
    }


    public void drawLastMove() {
        if (lastMove.length() == 0) {
            return;
        }
        fill(250, 247, 39, 80);
        strokeWeight(0);
        int a = convertFileToInt(lastMove.charAt(0));
        int b = 8 - Integer.parseInt("" + lastMove.charAt(1));
        int c = convertFileToInt(lastMove.charAt(2));
        int d = 8 - Integer.parseInt("" + lastMove.charAt(3));
        // print("\r\n" +a+" "+b+" "+" "+c+" "+d);
        rect(447 + 128 * a, 27 + 128 * b, 129, 129);
        rect(447 + 128 * c, 27 + 128 * d, 129, 129);
    }

    public int convertFileToInt(char c) {
        return ((int) c) - 97;
    }

    public void drawPiece(int r, int c) {
        String name = ("" + pieceBoard[r][c]);
        int temp = switch (name.toLowerCase()) {
            case "q" -> 2;
            case "r" -> 4;
            case "b" -> 6;
            case "n" -> 8;
            case "p" -> 10;
            case "k" -> 0;
            default -> -1;
        };
        if (temp == -1) {
            return;
        }
        int team;
        if (Character.isUpperCase(name.charAt(0))) {
            team = 0;
        } else {
            team = 1;
        }
        image(images[temp + (team == 0 ? 0 : 1)], 448 + 128 * c, 28 + 128 * r, 128, 128);
    }

    public void drawFloatingPiece(String location) {
        int r = 8 - Integer.parseInt("" + location.charAt(1));
        int c = convertFileToInt(location.charAt(0));
        String name = ("" + pieceBoard[r][c]);
        int temp = switch (name.toLowerCase()) {
            case "q" -> 2;
            case "r" -> 4;
            case "b" -> 6;
            case "n" -> 8;
            case "p" -> 10;
            case "k" -> 0;
            default -> -1;
        };
        if (temp == -1) {
            return;
        }
        int team;
        if (Character.isUpperCase(name.charAt(0))) {
            team = 0;
        } else {
            team = 1;
        }
        background(tempImage);
        image(images[(r + c) % 2 == 0 ? 18 : 19], 448 + 128 * c, 28 + 128 * r, 128, 128);
        if ((!lastMove.equals("")) && (location.equals(lastMove.substring(0, 2)) || location.equals(lastMove.substring(2)))) {
            fill(250, 247, 39, 80);
            strokeWeight(0);
            rect(447 + 128 * c, 27 + 128 * r, 129, 129);
        }
        fill(13, 213, 252, 80);
        strokeWeight(0);
        int x = (int) ((( (mouseX)) - 448 * widthP) / (128 * widthP));
        int y = (int) ((( (mouseY)) - 28 * heightP) / (128 * heightP));
        rect(447 + 128 * x, 27 + 128 * y, 129, 129);
        image(images[temp + (team == 0 ? 0 : 1)], mouseX - 64, mouseY - 64, 128, 128);
    }

    public void makeMove(String m) {
        setPiece(m.substring(2), getPiece(m.substring(0, 2)));
        setPiece(m.substring(0, 2), ' ');
        drawScreen();
    }

    public void setPiece(String s, char c) {
        int x = convertFileToInt(s.charAt(0));
        int y = 8 - Integer.parseInt("" + s.charAt(1));
        pieceBoard[y][x] = c;
    }

    public char getPiece(String s) {
        int x = convertFileToInt(s.charAt(0));
        int y = 8 - Integer.parseInt("" + s.charAt(1));
        return pieceBoard[y][x];
    }

    public void rect(float a, float b, float c, float d) {
        super.rect(widthP * a, heightP * b, widthP * c, heightP * d);
    }

    public void textSize(float s) {
        super.textSize(widthP * s);
    }

    public void text(String s, float a, float b) {
        super.text(s, widthP * a, heightP * b);
    }

    public void image(PImage img, float a, float b) {
        super.image(img, widthP * a, heightP * b, widthP * img.width, widthP * img.height);
    }

    public void image(PImage img, float a, float b, float x, float y) {
        super.image(img, widthP * a, heightP * b, widthP * x, widthP * y);
    }

}