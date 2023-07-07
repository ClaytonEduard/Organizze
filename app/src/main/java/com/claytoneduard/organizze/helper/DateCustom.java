/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

     public static String dataAtual(){
        long data = System.currentTimeMillis();
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
         String dataString = simpleDateFormat.format(data);
         return dataString;
     }
}
