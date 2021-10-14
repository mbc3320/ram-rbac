findUserRoleCode
===

    select
        role_code
    from
        tb_rbac_role
    where
        id in (
            select
                role_id
            from
                tb_rbac_role_user
            where
                user_id = #{userId}
        )
    order by
        role_code asc


findUserPermissionCode
===

    SELECT
	    p.permission_code 
    FROM
        ( SELECT 
            DISTINCT ( permission_id ) 
          FROM 
            tb_rbac_role_permission 
          WHERE 
            role_id IN ( SELECT 
                            role_id
                        FROM 
                            tb_rbac_role_user 
                        WHERE user_id = #{userId} ) ) r
    LEFT JOIN tb_rbac_permission p ON r.permission_id = p.id
    WHERE
        1 = 1
    ORDER BY
    permission_code ASC

findRolePermission
===

    SELECT
	    p.*,
        IF( isnull( rp.permission_id ), FALSE, TRUE ) AS checked
    FROM
        tb_rbac_permission p
    LEFT JOIN 
        tb_rbac_role_permission rp ON p.id = rp.permission_id
    AND 
        rp.role_id = #{roleId}
    WHERE
        1 = 1
    AND 
        p.permission_type = #{permissionType}

authRolePage
===

    SELECT * FROM (
        SELECT
            r.*,IF( ISNULL( ru.user_id ), FALSE, TRUE ) AS checked
        FROM
            tb_rbac_role r
        LEFT JOIN 
            tb_rbac_role_user ru 
        ON 
            ru.role_id = r.id
        AND 
            ru.user_id = #{params.userId}
    ) t
    WHERE
        1 = 1
    -- @if(!isEmpty(params.roleCode)){
        AND t.role_code like #{params.roleCode + '%'}
    }
    -- @if(!isEmpty(params.roleName)){
        AND t.role_name like #{params.roleName + '%'}
    }
    -- @if(!isEmpty(params.checked)){
        AND t.checked = #{params.checked}
    }
    

authRolePage$count
===

    SELECT count(1) FROM (
        SELECT
            r.role_code,r.role_name,IF( ISNULL( ru.user_id ), FALSE, TRUE ) AS checked
        FROM
            tb_rbac_role r
        LEFT JOIN 
            tb_rbac_role_user ru 
        ON 
            ru.role_id = r.id
        AND 
            ru.user_id = #{params.userId}
    ) t
    WHERE
        1 = 1
    -- @if(!isEmpty(params.roleCode)){
        AND t.role_code like #{params.roleCode + '%'}
    }
    -- @if(!isEmpty(params.roleName)){
        AND t.role_name like #{params.roleName + '%'}
    }
    -- @if(!isEmpty(params.checked)){
        AND t.checked = #{params.checked}
    }