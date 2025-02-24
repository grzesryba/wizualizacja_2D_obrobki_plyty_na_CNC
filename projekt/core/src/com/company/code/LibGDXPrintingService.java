package com.company.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.*;

// próba odpalenia przez inną bibliotekę ale działa wolniej

public class LibGDXPrintingService extends ApplicationAdapter implements PrintingService {
	@Override
	public boolean isFrameOpen() {
		return false;
	}

	@Override
	public JFrame getFrame() {
		return new JFrame("");
	}

	private final Board board;
	private ShapeRenderer shapeRenderer;
	private int xDistance;
	private int yDistance;
	private float scale;

	public LibGDXPrintingService(Board board) {
		this.board = board;
	}

	@Override
	public void printBoard(Board board) {

	}

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		double widthScale = calculateScale(board.getWidth(), Gdx.graphics.getWidth() - 50);
		double heightScale = calculateScale(board.getHeight(), Gdx.graphics.getHeight() - 50 - 20);
		scale = (float) Math.min(widthScale, heightScale);
		xDistance = (int) (50 / scale);//(int) (restOfXPlace / scale);//(int) ((frameWidth - board.getWidth()*scale)/2/scale);
		yDistance = (int) (50 / scale);//(int) (restOfYPlace / scale);//(int) ((frameHeight - (board.getHeight()-50/scale)*scale)/2/scale);
		scale *= 0.9;
	}

	@Override
	public void render () {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(Gdx.graphics.getWidth()/2 - board.getWidth()*scale/2, Gdx.graphics.getHeight()/2 - board.getHeight()*scale/2,  board.getWidth()*scale, board.getHeight()*scale);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Drill drill : board.getDrills()) {
			if (drill.isVertical()) {
				if (drill.getDiameter() == 4)
					shapeRenderer.setColor(Color.BLUE);
				else if (drill.getDiameter() == 8)
					shapeRenderer.setColor(Color.RED);
			} else {
				shapeRenderer.setColor(Color.WHITE);
			}
			float x = (float) (Gdx.graphics.getWidth() - drill.getY() * scale - Gdx.graphics.getWidth()/2 + board.getWidth()*scale/2);
			float y = (float) (Gdx.graphics.getHeight() - drill.getX() * scale - Gdx.graphics.getHeight()/2 + board.getHeight()*scale/2);
			float r = (float) (drill.getDiameter() * scale);
			r *= r<=1 ? 2 : 1;
			shapeRenderer.circle(x, y, r);
		}
		for (Saw line : board.getLines()) {
			shapeRenderer.setColor(Color.WHITE);
			float x1 = Gdx.graphics.getWidth() - line.getStartY() * scale  - Gdx.graphics.getWidth()/2 + board.getWidth()*scale/2;
			float y1 = Gdx.graphics.getHeight() - line.getStartX() * scale - Gdx.graphics.getHeight()/2 + board.getHeight()*scale/2;
			float x2 = Gdx.graphics.getWidth() - line.getEndY() * scale  - Gdx.graphics.getWidth()/2 + board.getWidth()*scale/2;
			float y2 = Gdx.graphics.getHeight() - line.getEndX() * scale - Gdx.graphics.getHeight()/2 + board.getHeight()*scale/2;
			shapeRenderer.rectLine(x1, y1, x2, y2, 2);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
	private static Double calculateScale(double boardSize, double frameSize) {
		return frameSize / boardSize;
	}
}
