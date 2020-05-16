package com.lemon.cas.exception.auth.login;

import javax.security.auth.login.AccountException;

/**
 * @author lemon
 * @description 账号不存在
 * @date 2020-05-16 11:38
 */
public class AccountNotPresenceException extends AccountException {

    private static final long serialVersionUID = 1498349563916294614L;

    /**
     * Constructs a AccountNotFoundException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public AccountNotPresenceException() {
        super();
    }

    /**
     * Constructs a AccountNotFoundException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception.
     *
     * @param msg the detail message.
     */
    public AccountNotPresenceException(String msg) {
        super(msg);
    }
}