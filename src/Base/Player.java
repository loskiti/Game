package Base;
// �����

import Paint.BaseActionUnitSprite;

public class Player /*implements IRenderToConvas*/extends BaseActionUnitSprite{
	// �������� ���������
	/*protected Image imagePlayer;
	// �������� ���������� ���������
	public int X;
	public int Y; 
    //����������, ������� �� ������
        public int posPlayerY;
        public int posPlayerX;
	public int speed=5;
	 public int width = 40;
	 public int height = 40; 
	public int directX=0;
	public int directY=0;
	//���� �������� ���������
	 public MapWay mapway;*/
/*	@Override
	public void render (Graphics g)
	{
		update();
		g.drawImage(imagePlayer, posPlayerX, posPlayerY,width,height, null); 
	}*/
public Player()
{
	String name = "/data/hero/warr.png";
    offsetRenderY = - 74;
    offsetRenderX = - 27;
    paintWidth = 128;
    paintHeight = 128;
    layerDeep=10000;
    initiation(name);
    actionStand();
    turnRight();
}
// //����������� ����������� �������� ����� (��� �����, �� ����� ����, ��� � ����������� �� �������� ����� ���� �� ����)
/*public void update(){
	if(mapway != null && mapway.isNextPoint()) {
        if(mapway.p == null) {
           mapway.startPoint(X, Y);
        } else {
            mapway.nextPoint(speed);
            if(X != mapway.p.x) {
                directX = (X > mapway.p.x) ? -1 : 1;
            } else {
                 directX = 0;
            }
            if(Y != mapway.p.y) {
                directY = (Y > mapway.p.y) ? -1 : 1;
            } else {
                 directY = 0; 
            }
        } 
    } else {
       directX = 0;
       directY = 0; 
    }
}*/
}
