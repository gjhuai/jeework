package com.wcs.base.util;

/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Array;


/**
 * Utility class for managing arrays.
 *
 * @author Anton Koinov (latest modification by $Author: ocrmse $)
 * @version $Revision: 1.1 $ $Date: 2006/06/12 02:18:54 $
 */
@SuppressWarnings("unchecked")
public class ArrayUtils extends org.apache.commons.lang.ArrayUtils{

        /**
         *
         * @param c1
         * @param c2
         * @return
         *
         */
        public static Class commonClass(Class c1, Class c2) {
                if (c1 == c2) {
                        return c1;
                }


                if ((c1 == Object.class) || c1.isAssignableFrom(c2)) {
                        return c1;
                }


                if (c2.isAssignableFrom(c1)) {
                        return c2;
                }


                if (c1.isPrimitive() || c2.isPrimitive()) {
                        throw new IllegalArgumentException("incompatible types " + c1 + " and " + c2);
                }


                return Object.class;
        }


        /**
         * Concatenates two arrays into one. If arr1 is null or empty, returns arr2.
         * If arr2 is null or empty, returns arr1. May return null if both arrays are null,
         * or one is empty and the other null. <br>
         * The concatenated array has componentType which is compatible with both input arrays (or Object[])
         *
         * @param arr1 input array
         * @param arr2 input array
         *
         * @return Object the concatenated array, elements of arr1 first
         */
        public static Object concat(Object arr1, Object arr2) {
                int len1 = (arr1 == null) ? (-1) : Array.getLength(arr1);


                if (len1 <= 0) {
                        return arr2;
                }


                int len2 = (arr2 == null) ? (-1) : Array.getLength(arr2);


                if (len2 <= 0) {
                        return arr1;
                }


                Class commonComponentType = commonClass(arr1.getClass().getComponentType(), arr2.getClass().getComponentType());
                Object newArray = Array.newInstance(commonComponentType, len1 + len2);
                System.arraycopy(arr1, 0, newArray, 0, len1);
                System.arraycopy(arr2, 0, newArray, len1, len2);


                return newArray;
        }

        /**
         *
         * change one dimension to planar.
         *
         * @param oneDimensionArray
         * @param cols
         * @return
         *
         */
        public static String[][] dimension2Planar(String[] oneDimensionArray, int cols) {

                int tempindex = 0;
                int row = oneDimensionArray.length / cols;
                String[][] result = new String[row][];

                for (int i = 0; i < row; i++) {
                        result[i] = new String[cols];
                        for (int j = tempindex; j < tempindex + result[i].length; j++) {
                                result[i][j - tempindex] = oneDimensionArray[j];
                        }
                        tempindex += result[i].length;
                }


                return result;
        }

        /**
         *
         * @param oneDimensionArray
         * @return
         */
        public static String[] safeOneDimensionArray(String[] oneDimensionArray) {
                int oneDimensionArrayLength = oneDimensionArray.length;
                int notNullMaxLength = oneDimensionArrayLength;

                for(int i=oneDimensionArrayLength -1; i >= 0; i--) {
                        if(oneDimensionArray[i] != null){
                                notNullMaxLength = i+1;
                                break;
                        }
                }

                if(notNullMaxLength != oneDimensionArrayLength) {
                        String[] safeArray = new String[notNullMaxLength];
                        for(int index = 0; index < notNullMaxLength; index++){
                                safeArray[index] = oneDimensionArray[index];
                        }
                        return safeArray;
                }

                return oneDimensionArray;
        }
}
