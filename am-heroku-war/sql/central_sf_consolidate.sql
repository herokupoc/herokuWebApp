CREATE OR REPLACE FUNCTION public.central_sf_consolidate(tg_operation text, tg_schema text, tg_sfid text, tg_ownerid text, tg_accountid text, tg_salutation text, tg_firstname text, tg_middlename text, tg_lastname text, tg_mobilephone text, tg_workphone text, tg_current_timestamp timestamp with time zone, tg_title text, tg_email text, tg_acu_decisionmaker__c boolean, tg_isdeleted boolean, tg_lastmodifiedbyid text, tg_createddate timestamp with time zone)
 RETURNS void
 LANGUAGE plpgsql
AS $function$
    DECLARE
        sforg text;
    	query_statement TEXT;
        res TEXT;
	    count_aux integer:=0;
       --v_connection_string varchar(2000):='port=5432 host=ec2-35-168-54-239.compute-1.amazonaws.com dbname=dbbb4hnf9hie84 user=sbbxcgspaiiigm password=e8b6ce31964fa3fc8e3d5cf6f53ec646f8cf74903691310a55d906bbd5af663b';
    BEGIN 
	
	raise notice 'BEGIN: central_sf_consolidate';      
	INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'central_sf_consolidate', 'init function');
	

	if(tg_operation = 'UPDATE') then
		INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'central_sf_consolidate', 'insert!');	    
		--select 
		query_statement = concat('UPDATE sfcentral_contact set ', (SELECT FORMAT(' ownerid=''%s'', accountid=''%s'', salutation=''%s'',
							firstname=''%s'', middlename=''%s'', lastname=''%s'', mobilephone=''%s'', workphone=''%s'',	lastmodifieddate=''%s'', title=''%s'', email=''%s'',
							decisionmaker=''%s'', isdeleted=''%s'', lastmodifiedbyid=''%s'', createddate=''%s'' ', 
							tg_ownerid,tg_accountid,tg_salutation,
							tg_firstname,tg_middlename,tg_lastname, tg_mobilephone, tg_workphone, current_timestamp, tg_title,tg_email,
							tg_acu_decisionmaker__c, tg_isdeleted, tg_lastmodifiedbyid, tg_createddate)),
								(select format ('where sfid=''%s'' AND sforg=''%s'' ;',tg_sfid, tg_schema)) );
		
	else 
		if(tg_operation = 'INSERT') then
	    	INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'central_sf_consolidate', 'update');
	
	    	query_statement = concat('insert into sfcentral_contact (sfid, sforg, ownerid, accountid, salutation,
							firstname, middlename, lastname, mobilephone, workphone,	
							lastmodifieddate, title, email, decisionmaker, isdeleted,
							lastmodifiedbyid, createddate ) values (', (SELECT FORMAT(' ''%s'', ''%s'', ''%s'', ''%s'', ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' , ''%s'' ,''%s'' ',
							tg_sfid, tg_schema, tg_ownerid,tg_accountid,tg_salutation,
							tg_firstname,tg_middlename,tg_lastname, tg_mobilephone, tg_workphone, 
							tg_current_timestamp, tg_title, tg_email, tg_acu_decisionmaker__c, tg_isdeleted, 
							tg_lastmodifiedbyid, tg_createddate)), ');');
		end if;
	end if;							
		
		INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'QUERIE', query_statement);	    
		raise notice 'QUERIE FORMATED: %', query_statement ;                      
		raise notice 'INSERT ON CENTRAL...'; 	
			res := dblink_exec('server_central', query_statement, true);
      	raise notice 'INSERTED RECORD ON CENTRAL!'; 	
       --perform dblink_disconnect('db2');
     	--return NEW; 
       
     EXCEPTION WHEN others THEN  -- more specific
    	INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'central_sf_consolidate', format('excepcao: %', SQLERRM));
      	RAISE NOTICE 'exception central_sf_consolidate err: %', SQLERRM ;
	    --RETURN NULL;
	   
	  INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'central_sf_consolidate', 'END-OK');	         
 END;
    $function$
;
