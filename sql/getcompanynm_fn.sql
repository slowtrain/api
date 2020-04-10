CREATE OR REPLACE FUNCTION episode.getcompanynm_fn(in_org_id character varying, in_company_id character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    companynm varchar;
BEGIN
    select   coalesce(tb.companyname, in_company_id)
    into     companynm
    from     company tb
    where    tb.org_id = in_org_id
    and      tb.company_id = in_company_id;
    
    if companynm is null or  companynm = '' then
    select  in_company_id into companynm;
    end if;
    return companynm;
end;
$function$
;
