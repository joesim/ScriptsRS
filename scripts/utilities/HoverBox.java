package scripts.utilities;

import java.util.ArrayList;

public final class HoverBox {

	public static ArrayList<TBox> hoverBoxes = new ArrayList<TBox>();
	

	public static TBox get(int... i){
		
		if (i.length==1){
			return hoverBoxes.get(i[0]);
		}
		
		
		if (i.length == 3){
			if (Functions.percentageBool(i[1])){
				return hoverBoxes.get(i[0]);
			} else {
				return hoverBoxes.get(i[2]);
			}
		}
		
		return null;
	}
	
	public static TBox getRandom(int i){
		int r = Functions.generateRandomInt(0, hoverBoxes.size()-1);
		return hoverBoxes.get(r);
	}

	public static void load() {
		hoverBoxes.add(new TBox(0,0,750,500));//0
		hoverBoxes.add(new TBox(0,0,750,500));//1
		hoverBoxes.add(new TBox(560,17,723,151));//2
		hoverBoxes.add(new TBox(523,167,760,212));//3
		hoverBoxes.add(new TBox(536,201,753,495));//4
		hoverBoxes.add(new TBox(19,354,508,492));//5
		hoverBoxes.add(new TBox(1,1,507,334));//6
		hoverBoxes.add(new TBox(0,0,514,90));//7
		hoverBoxes.add(new TBox(1,106,510,237));//8
		hoverBoxes.add(new TBox(3,240,515,334));//9
		hoverBoxes.add(new TBox(6,348,507,386));//10
		hoverBoxes.add(new TBox(3,385,513,426));//11
		hoverBoxes.add(new TBox(5,433,514,473));//12
		hoverBoxes.add(new TBox(0,0,157,333));//13
		hoverBoxes.add(new TBox(136,0,385,335));//14
		hoverBoxes.add(new TBox(358,1,511,333));//15
		hoverBoxes.add(new TBox(592,30,621,58));//16
		hoverBoxes.add(new TBox(618,20,664,38));//17
		hoverBoxes.add(new TBox(655,26,704,58));//18
		hoverBoxes.add(new TBox(575,59,608,99));//19
		hoverBoxes.add(new TBox(680,55,706,98));//20
		hoverBoxes.add(new TBox(577,99,625,139));//21
		hoverBoxes.add(new TBox(616,130,671,157));//22
		hoverBoxes.add(new TBox(654,108,697,143));//23
		hoverBoxes.add(new TBox(566,221,584,237));//24
		hoverBoxes.add(new TBox(628, 330, 654, 349));//25
		hoverBoxes.add(new TBox(588, 332, 620, 351));//26
		hoverBoxes.add(new TBox(52,68,139,135));//27
		hoverBoxes.add(new TBox(214,80,306,166));//28
		hoverBoxes.add(new TBox(174,383,350,414));//29
		hoverBoxes.add(new TBox(20,30,138,128));//30
		hoverBoxes.add(new TBox(401,160,510,310));//31
		hoverBoxes.add(new TBox(9,109,85,217));//32
		hoverBoxes.add(new TBox(169,140,277,210));//33
		hoverBoxes.add(new TBox(392,266,472,346));//34
		hoverBoxes.add(new TBox(310,244,436,314));//35
		hoverBoxes.add(new TBox(651,82,702,105));//36
	}
	
	
}
