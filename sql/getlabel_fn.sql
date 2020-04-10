CREATE OR REPLACE FUNCTION episode.getlabel_fn(in_appid character varying, in_labelid character varying, in_lang character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
declare 
    label varchar;
BEGIN
    select   coalesce(tb.description, in_labelId)
    into     label
    from     applabel tb
    where    tb.app_id = in_appId
    and      tb.name = in_labelId
    and      tb.lang = in_lang;
    
    if label is null or  label = '' then
    select  in_labelId into label;
    end if;
    return label;
end;
$function$
;
