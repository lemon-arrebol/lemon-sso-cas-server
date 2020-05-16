package com.lemon.cas.logout;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.logout.LogoutPostProcessor;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.springframework.core.Ordered;

/**
 * @author houjuntao
 * @version 1.0
 * @description: 自定义退出后处理器
 * {@link org.apereo.cas.logout.DefaultLogoutManager} 调用
 * @date Create by houjuntao on 2020-05-16 14:58
 */
@Slf4j
public class CustomLogoutPostProcessor implements LogoutPostProcessor {
    /**
     * Handle.
     *
     * @param ticketGrantingTicket the ticket granting ticket
     */
    @Override
    public void handle(TicketGrantingTicket ticketGrantingTicket) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("TicketGrantingTicket is ", ticketGrantingTicket);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
