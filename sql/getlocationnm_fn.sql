CREATE OR REPLACE FUNCTION episode.getlocationnm_fn(in_org_id character varying, in_location_id character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    locationnm varchar;
BEGIN
    select   coalesce(tb.description, in_location_id)
    into     locationnm
    from     location tb
    where    tb.org_id = in_org_id
    and      tb.location_id = in_location_id;
    
    if locationnm is null or  locationnm = '' then
    select  in_location_id into locationnm;
    end if;
    return locationnm;
end;
$function$
;
