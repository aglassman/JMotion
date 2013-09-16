package jmotion;

public class CollectionExtensions {	
	public static <T extends Object> String arrayJoin(String joinOn, T[] array) {
		if (array.length == 0)
			return "";
		
		String joined = array[0].toString();
		for (int i = 1; i<array.length; ++i)
			joined += " " + array[i];
		
		return joined;
	}
}
