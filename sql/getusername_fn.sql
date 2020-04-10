CREATE OR REPLACE FUNCTION episode.getusername_fn(in_username character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    userdisplayname varchar;
BEGIN
    select   tb.first_name
    into     userdisplayname
    from     esuser tb
    where    tb.username = in_username;
    return userdisplayname;
end;
$function$
;
