package com.demuelle.fake_football.utils;

import com.demuelle.fake_football.exception.NonParsableIntegerListException;
import com.demuelle.fake_football.exception.NonParsableRangeException;

public class ParseUtils {
    public static int[] parseSeriesOrRange(String s) {
        int[] returnVal;
        if (s.contains("-")) {
            try {
                String[] strings = s.split("-");
                if (strings.length != 2) {
                    throw new NonParsableRangeException();
                }

                int low = Integer.parseInt(strings[0].trim());
                int high = Integer.parseInt(strings[1].trim());

                if (low > high) {
                    throw new NonParsableRangeException();
                }

                returnVal = new int[high - low + 1];
                for (int i = low; i <= high; i++) returnVal[i - low] = i;

                if (returnVal[0] <= 0 || returnVal[1] <= 0) {
                    throw new NonParsableRangeException();
                }
            } catch (Exception ex) {
                throw new NonParsableRangeException(s);
            }
        } else {
            try {
                String[] numberStrings = s.split(",");
                returnVal = new int[numberStrings.length];
                for (int i = 0; i < numberStrings.length; i++) {
                    returnVal[i] = Integer.parseInt(numberStrings[i].trim());
                }
            } catch (Exception ex) {
                throw new NonParsableIntegerListException(s);
            }
        }
        return returnVal;
    }
}
