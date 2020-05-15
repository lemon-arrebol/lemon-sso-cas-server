package com.lemon.cas.ticket;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Configuration properties class for {@code ticket}.
 *
 * @author Dmitriy Kopylenko
 * @since 5.0.0
 */
@Getter
@Setter
public class TicketProperties implements Serializable {

    private static final long serialVersionUID = 5586947805593202037L;

    private String tgtSuffix;

    private String pgtSuffix;

    private String ptSuffix;

    private String stSuffix;
}
