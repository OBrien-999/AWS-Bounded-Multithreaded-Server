package taskone;

import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public String remove(){

	int size = strings.size() - 1;
	
	if(size >= 0){
	    
	String removed = strings.get(size);
	strings.remove(size);
	return removed;

	}else{

	    String error = "null";
	    return error;

	}

    }

    public void flip(int x, int y){

	String first = strings.get(x);
	String last = strings.get(y);

       	strings.set(x, last);
       	strings.set(y, first);

    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public String toString() {
        return strings.toString();
    }
}
