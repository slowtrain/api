CREATE OR REPLACE FUNCTION episode.getautonum_fn(in_orgid character varying, in_tablename character varying, in_colname character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    seq varchar;
BEGIN
    select  prefix||lpad(cast(curnum+1 as varchar),10,'0')
    from    autonum
    into    seq
    where   org_id = in_orgid
    and     tablename = in_tablename
    and     colname = in_colname;
    
    update  autonum
    set     curnum = curnum+1    
    where   org_id = in_orgid
    and     tablename = in_tablename
    and     colname = in_colname;

    return seq;
commit;
end;
$function$
;
