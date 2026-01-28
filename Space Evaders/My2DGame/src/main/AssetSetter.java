package main;

import entity.NPC_Jet;
import object.OBJ_AcidPit;
import object.OBJ_Door;
import object.OBJ_FinalDoor;
import object.OBJ_GasGaloon;
import object.OBJ_RepairKit;
import tiles.TileManager;

// COLOCADOR DE OBJETOS E NPCS

public class AssetSetter {

	GamePanel gp;
	TileManager tm;
	
	// CONTADORES PARA OBJETOS
	
	public int objCount = 0;
	public int doorCounter = 0;
	public int repairKitCounter = 0;
	public int galoonCounter = 0;
	public int acidPitCounter = 0;
	
	// CONTADORES PARA NPCS
	
	public int npcCount = 0;
	public int npc1Counter = 0;
	
	// CONSTRUTOR
	
	public AssetSetter(GamePanel gp, TileManager tm) {
		this.gp = gp;
		this.tm = tm;
	}
	
	// COLOCAR OBJETOS
	
	public void setObject() {
			
		objCount = 0;
		
		boolean objPlaced = false;
		
		for(int i = 0; i <gp.maxWorldCol && objCount < gp.obj.length;i++) {
			for(int j = 0; j < gp.maxWorldRow && objCount < gp.obj.length; j++) {
				
				objPlaced = false;
				
				switch(tm.mapTileNum[i][j]) {
					case 2:{
						gp.obj[objCount] = new OBJ_AcidPit(gp);
						objPlaced = true;
						acidPitCounter++;
						break;
					}	
					case 3:{
						gp.obj[objCount] = new OBJ_RepairKit();
						repairKitCounter++;
						objPlaced = true;
						break;
					}
					case 4:{
						gp.obj[objCount] = new OBJ_Door(gp);
						doorCounter++;
						objPlaced = true;
						break;
					}
					case 5:{
						gp.obj[objCount] = new OBJ_FinalDoor(gp);
						objPlaced = true;
						break;
					}
					case 6:{
						gp.obj[objCount] = new OBJ_GasGaloon(gp);
						galoonCounter++;
						objPlaced = true;
						break;
					}			
				}
				if(objPlaced == true) {
					gp.obj[objCount].worldX = i * gp.tileSize;
					gp.obj[objCount].worldY = j * gp.tileSize;
					objCount++;
				}		
			}
		}
	}
	
	// COLOCAR NPCS
	
	public void setNPC() {

		npcCount = 0 ;
		
		boolean npcPlaced = false;
		
		for(int i = 0; i <gp.maxWorldCol && npcCount < gp.npc.length && npcCount < gp.maxNPCS;i++) {
			for(int j = 0; j < gp.maxWorldRow && npcCount < gp.npc.length && npcCount < gp.maxNPCS; j++) {
				
				npcPlaced = false;
				
				switch(tm.mapTileNum[i][j]) {
					case 7:
						gp.npc[npcCount] = new NPC_Jet(gp);
						npcPlaced = true;
						npc1Counter++;
						break;
				}
				
				if(npcPlaced == true) {
					gp.npc[npcCount].worldX = i * gp.tileSize;
					gp.npc[npcCount].worldY = j * gp.tileSize;
					npcCount++;
				}
			}
		}
	}
	
	// CONTAR OBJETOS
	
	public int countObjects(int tileIndex) {
		int result = 0;
		
		
		for (int i = 0; i< gp.maxWorldCol;i++) {
			for(int j = 0; j< gp.maxScreenRow; j++) {
				if(tm.mapTileNum[i][j] == tileIndex) {
					result ++;
				}
			}
		}
		return result;
	}
}
	