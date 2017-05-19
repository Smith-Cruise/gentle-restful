package com.app;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

/**
 * Created by Smith on 2017/5/18.
 */
public class Serurity {
    public static void main(String[] args) {
        String secret = "123456";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("smith")
                    .withClaim("email","chendingchao1@126.com")
                    .sign(algorithm);
            System.out.println(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzbWl0aCIsImVtYWlsIjoiY2hlbmRpbmdjaGFvMUAxMjYuY29tIn0.lV9vXCpcd4qYd28aNqLbcZdXU94pWSgLKboWLqsJ0Q0";
        DecodedJWT jwt = JWT.decode(token);
        String algorithm = jwt.getAlgorithm();
        System.out.println(algorithm);
        Claim claim = jwt.getClaim("e");
        System.out.println(claim.asString());
    }
}
