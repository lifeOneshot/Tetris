package Tetris;

public class EJ extends Piece {
	public EJ(TetrisData data) {
		super(data);
		c[0] = -1;		r[0] = -1;
		c[1] = -1;		r[1] = 0;
		c[2] = 0;		r[2] = 0;
		c[3] = 1;		r[3] = 0;
	}
	
	public int getType() {
		return 2;
	}
	
	public int roteType() {
		return 4;
	}
}
