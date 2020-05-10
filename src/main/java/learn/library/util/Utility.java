package learn.library.util;

import java.util.List;

public class Utility {

    public static boolean isNotEmpty(Object object) {
        return object != null && object.toString().trim().length() > 0;
    }

    public static boolean listEquals(List<String> list, List<String> equalList) {
        return list.containsAll(equalList) && equalList.containsAll(list);
    }

}
