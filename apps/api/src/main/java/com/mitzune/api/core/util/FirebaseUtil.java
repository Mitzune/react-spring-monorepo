package com.mitzune.api.core.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mitzune.api.features.auth.exception.AuthException;
import org.springframework.stereotype.Component;

@Component
public class FirebaseUtil {

  public FirebaseToken verifyIdToken(String idToken) {
    try {
      FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(
        idToken
      );
      return firebaseToken;
    } catch (FirebaseAuthException e) {
      throw AuthException.ssoTokenInvalid();
    }
  }
}
