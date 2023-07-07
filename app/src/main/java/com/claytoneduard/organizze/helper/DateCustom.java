/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    // metodo para retornar somente o mes e o ano
    public static String mesAnoDataEscolhida(String data) {
        String retornoData[] = data.split("/");

        String dia = retornoData[0];//dia
        String mes = retornoData[1];//mes
        String ano = retornoData[2];//ano

        String mesAno = mes + ano;
        return mesAno;
    }
}
