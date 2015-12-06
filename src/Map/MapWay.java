package Map;


import java.awt.Point;
import java.util.ArrayList;

import Paint.Tile;

// ���������� � ���� (��� �����)
public class MapWay {
	public ArrayList<Point> way;
public Point p;
//������� ������� 
protected int index = 0;
	public MapWay()
	{
		way=new ArrayList<Point>();
	}
	public void clear()
	{
		way.clear();
	}
	public void addPoint(Point p)
	{
		way.add(p);
	}
	//��������� �����
	public void startPoint(int x, int y)
	{
		p=new Point(x,y);
		index=way.size()-1;
	}
	// ���������� �� �����
	public boolean isNextPoint ()
	{
		if (index>-1) return true;
		return false;
	}
	// ������� �� ��������� ����� (���� ������� ��� ������).
	public Point nextPoint (int step){
	    int x = way.get(index).x * Tile.SIZE,
	            y = way.get(index).y * Tile.SIZE; 
				
	        if(p.x != x)  {
	            p.x += step * ((x < p.x) ? -1: 1);
	        } 
	        if(p.y != y)  {
	            p.y += step * ((y < p.y) ? -1: 1);
	        } 
	            
	        if(p.x == x && p.y == y) {
	            index --; 
	        } 
	         
	        
	        return p;
	    }
	}
