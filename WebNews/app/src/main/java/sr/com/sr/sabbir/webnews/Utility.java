package sr.com.sr.sabbir.webnews;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by SABBIR on 1/2/2016.
 */
public class Utility {
    Context context;
    String AppNname;
    String DATEformat = "dd-MMM-yyyy hh:mm:ss aa";

    public Utility(Context context) {
        this.context = context;
    }

    public String[] Arraylist2Array(ArrayList<String> arrayList) {
        int size = arrayList.size();
        String strings[] = new String[size];
        for (int i = 0; i < size; i++)
            strings[i] = arrayList.get(i);
        return strings;
    }
}
