package Tetris;

public class ES extends Piece {
	public ES(TetrisData data) {
		super(data);
		c[0] = -1;		r[0] = 0;
		c[1] = -0;		r[1] = 0;
		c[2] = 0;		r[2] = -1;
		c[3] = 1;		r[3] = -1;
	}
	
	public int getType() {
		return 5;
	}
	
	public int roteType() {
		return 4;
	}
}
