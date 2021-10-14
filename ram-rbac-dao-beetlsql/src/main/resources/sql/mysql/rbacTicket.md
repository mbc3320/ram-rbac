findUserAvailableTicket
===

    SELECT
	    ticket 
    FROM
        tb_rbac_ticket
    WHERE
        user_id = #{userId}
    AND 
        TIMESTAMPDIFF( MINUTE, now( ), time_expire ) > 0