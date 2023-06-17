package Tetris;

public class EL extends Piece {
	public EL(TetrisData data) {
		super(data);
		c[0] = -1;		r[0] = 0;
		c[1] = 0;		r[1] = 0;
		c[2] = 1;		r[2] = 0;
		c[3] = 1;		r[3] = -1;
	}
	
	public int getType() {
		return 3;
	}
	
	public int roteType() {
		return 4;
	}
}
