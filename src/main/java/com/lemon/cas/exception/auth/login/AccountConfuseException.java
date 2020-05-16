package com.lemon.cas.exception.auth.login;

import javax.security.auth.login.AccountException;

/**
 * @author houjuntao
 * @description 账号存在多个
 * @date 2020-05-16 11:38
 */
public class AccountConfuseException extends AccountException {

    private static final long serialVersionUID = 1498349563916294614L;

    /**
     * Constructs a AccountNotFoundException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public AccountConfuseException() {
        super();
    }

    /**
     * Constructs a AccountNotFoundException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception.
     *
     * @param msg the detail message.
     */
    public AccountConfuseException(String msg) {
        super(msg);
    }
}