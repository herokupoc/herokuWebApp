CREATE OR REPLACE FUNCTION util.insert_log(log_cod text, log_error text)
 RETURNS void
 LANGUAGE plpgsql
AS $function$
declare
    flag integer:=0;
    rec record ;
Begin

	INSERT INTO util.t_util_log
		(insert_time, log_locale, log_msg)
		VALUES(current_timestamp, log_cod, log_error);

	Exception
	      --when unique_violation then
	       -- RAISE NOTICE '%', v_query;
	     when others then
	        RAISE NOTICE 'exception on error log';
	 
END;
$function$
;
