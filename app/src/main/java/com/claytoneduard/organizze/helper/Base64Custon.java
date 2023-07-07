/*
 * Copyright (c) 2023.
 * Clayton Eduard F Chaves
 */

package com.claytoneduard.organizze.helper;

import android.util.Base64;

public class Base64Custon {

    public static String codificarBase64(String texto) {
        // remover espacos no comeco e no fim do texto
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodificarBase64(String textoCodificado) {
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
