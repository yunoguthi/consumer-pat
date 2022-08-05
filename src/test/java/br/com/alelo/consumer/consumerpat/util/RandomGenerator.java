package br.com.alelo.consumer.consumerpat.util;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public static String randomize(int length) {
//        byte[] array = new byte[length];
//        new Random().nextBytes(array);
//        String retorno = new String(array, Charset.forName("UTF-8"));
//        return retorno;
        return Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, length);
    }

    public static int randomize(int de, int ate) {
        int randomNum = ThreadLocalRandom.current().nextInt(de, ate);
        return randomNum;
    }

    public static void main(String[] args) {
       System.out.println(RandomGenerator.randomize(3));
    }

}
