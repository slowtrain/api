CREATE OR REPLACE FUNCTION episode.getdomainnm_fn(in_domain_id character varying, in_key character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    domainnm varchar;
BEGIN
    select   value
    into     domainnm
    from     domain tb
    where    tb.domain_id = in_domain_id
    and      tb.key = in_key;
    
    if domainnm is null or  domainnm = '' then
    select  in_key into domainnm;
    end if;
    return domainnm;
end;
$function$
;
