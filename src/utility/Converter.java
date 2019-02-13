package utility;

import java.util.BitSet;

public class Converter {

	public static String bitSetToString(BitSet bitSet, int length){
		StringBuffer s = new StringBuffer();
		for(int i=0;i<length;i++){
			if(bitSet.get(i))
				s.append(1);
			else
				s.append(0);	
		}		
		return s.toString();
		
	}

}