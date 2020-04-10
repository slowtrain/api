CREATE OR REPLACE FUNCTION episode.getclassnm_fn(in_org_id character varying, in_classstructure_id character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    classnm varchar;
BEGIN
    select   coalesce(tb.description, in_classstructure_id)||'('||tb.classstructure_id||')'
    into     classnm
    from     classstructure tb
    where    tb.org_id = in_org_id
    and      tb.classstructure_id = in_classstructure_id;
    
    if classnm is null or  classnm = '' then
    select  coalesce(in_classstructure_id, 'N/A') into classnm;
    end if;
    return classnm;
end;
$function$
;
