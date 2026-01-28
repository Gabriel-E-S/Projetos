package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_RepairKit extends SuperObject{

	public OBJ_RepairKit() {
		name = "RepairKit";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chave reparo.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}	
}
