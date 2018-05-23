package sample.module;

import sample.principal.SamplePrincipal;

import java.util.*;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;


public class SampleLoginModule implements javax.security.auth.spi.LoginModule {

// initial state
private Subject subject;
private CallbackHandler callbackHandler;
private Map sharedState;
private Map options;

private boolean debug = false;
private boolean succeeded = false;
private boolean commitSucceeded = false;

private String username;
private char[] password;
private SamplePrincipal userSamplePrincipal;

public void initialize(Subject subject,
CallbackHandler callbackHandler,
Map<java.lang.String, ?> sharedState,
Map<java.lang.String, ?> options) {

    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;

    debug = "true".equalsIgnoreCase((String) options.get("debug"));
}

public boolean login() throws LoginException {

    if (callbackHandler == null)
        throw new LoginException("Error: no CallbackHandler available " +
                "to garner authentication information from the user");

    Callback[] callbacks = new Callback[2];
    callbacks[0] = new NameCallback("user name: ");
    callbacks[1] = new PasswordCallback("password: ", false);

    try {
        callbackHandler.handle(callbacks);
        username = ((NameCallback) callbacks[0]).getName();
        char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
        if (tmpPassword == null) {
            tmpPassword = new char[0];
        }
        password = new char[tmpPassword.length];
        System.arraycopy(tmpPassword, 0,
                password, 0, tmpPassword.length);
        ((PasswordCallback) callbacks[1]).clearPassword();

    } catch (java.io.IOException ioe) {
        throw new LoginException(ioe.toString());
    } catch (UnsupportedCallbackException uce) {
        throw new LoginException("Error: " + uce.getCallback().toString() +
                " not available to garner authentication information " +
                "from the user");
    }

    // print debugging information
    if (debug) {
        System.out.println("\t\t[LoginModule] " +
                "user entered user name: " +
                username);
        System.out.print("\t\t[LoginModule] " +
                "user entered password: ");
        for (int i = 0; i < password.length; i++)
            System.out.print(password[i]);
        System.out.println();
    }

    // verify the username/password
    boolean usernameCorrect = false;
    boolean passwordCorrect = false;
    if (username.equals("testUser"))
        usernameCorrect = true;
    if (usernameCorrect &&
            password.length == 12 &&
            password[0] == 't' &&
            password[1] == 'e' &&
            password[2] == 's' &&
            password[3] == 't' &&
            password[4] == 'P' &&
            password[5] == 'a' &&
            password[6] == 's' &&
            password[7] == 's' &&
            password[8] == 'w' &&
            password[9] == 'o' &&
            password[10] == 'r' &&
            password[11] == 'd') {

        // authentication succeeded!!!
        passwordCorrect = true;
        if (debug)
            System.out.println("\t\t[LoginModule] " +
                    "authentication succeeded");
        succeeded = true;
        return true;
    } else {

        // authentication failed -- clean out state
        if (debug)
            System.out.println("\t\t[LoginModule] " +
                    "authentication failed");
        succeeded = false;
        username = null;
        for (int i = 0; i < password.length; i++)
            password[i] = ' ';
        password = null;
        if (!usernameCorrect) {
            throw new FailedLoginException("User Name Incorrect");
        } else {
            throw new FailedLoginException("Password Incorrect");
        }
    }
}

public boolean commit() throws LoginException {
    if (succeeded == false) {
        return false;
    } else {
        userSamplePrincipal = new SamplePrincipal(username);
        if (!subject.getPrincipals().contains(userSamplePrincipal))
            subject.getPrincipals().add(userSamplePrincipal);

        if (debug) {
            System.out.println("\t\t[LoginModule] " +
                    "added SamplePrincipal to Subject");
        }

        // in any case, clean out state
        username = null;
        for (int i = 0; i < password.length; i++)
            password[i] = ' ';
        password = null;

        commitSucceeded = true;
        return true;
    }
}

public boolean abort() throws LoginException {
    if (succeeded == false) {
        return false;
    } else if (succeeded == true && commitSucceeded == false) {
        // login succeeded but overall authentication failed
        succeeded = false;
        username = null;
        if (password != null) {
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;
        }
        userSamplePrincipal = null;
    } else {
        logout();
    }
    return true;
}

public boolean logout() throws LoginException {
    subject.getPrincipals().remove(userSamplePrincipal);
    succeeded = false;
    succeeded = commitSucceeded;
    username = null;
    if (password != null) {
        for (int i = 0; i < password.length; i++)
            password[i] = ' ';
        password = null;
    }
    userSamplePrincipal = null;
    return true;
}
}