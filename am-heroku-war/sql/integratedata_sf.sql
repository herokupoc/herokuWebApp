CREATE OR REPLACE FUNCTION public.integratedata_sf()
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
declare
    flag integer:=0;
	curr_date text:= (select to_char(current_date, 'YYYYMMDD'));
	external_aux text;	
	rec record ;
	rec_container record ;
	external_exist integer:=0;
	aux_exist integer:=0;
	external_exist_o integer:=0;
	aux_exist_o integer:=0;
	aux_flag text;
	qty_err_rec integer:=0;
	qty_sync_rec integer:=0;
	container_id_aux integer:=0;
Begin
     --exception when others then 
    for rec_container in 
    	select distinct(filecontainerid) filecontainerid from public.record where  flagto_SF !='SYNC' order by filecontainerid asc
   
	loop 
		for rec in 
	             select file_id, filecontainerid , (select substring(name,0,3) from filecontainer f2 where f2.id = filecontainerid) org , account_flag , 
				contact_flag, err_msg, err_type, contact_org, owner_flag, file_line, firstname, lastname, midname, salutation, sfcontact_id,
	            mail, cellular, account_segmentation, validated, flagto_sf, sfaccount_id, sfowner_id  
	       		from public.record where  flagto_SF !='SYNC' and filecontainerid = rec_container.filecontainerid 
	       		--and account_flag= 'A'  
	       		--where midname='retail'
	       		order by filecontainerid asc
	
	     loop
			
			external_aux = ('CO'||'_'||REPLACE(rec.org, ' ', '-')||'_'||curr_date);
		 
	     	 --if( UPPER(rec.account_segmentation) = UPPER('retail')) then
		    raise notice 'aux_exist %', aux_exist;
	     	if( UPPER(rec.account_segmentation) = UPPER('retail')) then
	     		case 
		    	when rec.flagto_SF = 'INS' then
		    	--when rec.contact_flag= 'A' then
			    PERFORM util.insert_log('integratedata_sf','herokuacu.contact insert!');     	
		    	raise notice 'herokuacu.contact insert:';
				    
			     if(aux_exist = 0) then 
			    	select count(*) into external_exist from herokuacu.contact c where substring(c.acu_externalid__c, 0, 15) = external_aux ;  
				 end if;	
			    raise notice 'external_exist: %', external_exist ;
					if((external_exist >0) or (aux_exist > 0)) then 
					   	--add next in sequence
					   	external_aux = external_aux||'_'||(SELECT nextval('external_s'));
						raise notice 'external_aux1: %', external_aux ;
						aux_exist = 1 ;	     		
					else
						--reset sequence 
					   	ALTER SEQUENCE external_s RESTART WITH 1;	
						external_aux = external_aux||'_'||(SELECT nextval('external_s'));
						aux_exist = 1 ;
						raise notice 'external_aux2: %', external_aux ;
				     	
				    end if;
				        	
				  INSERT INTO herokuacu.contact
					(middlename, lastname, acu_decisionmaker__c, accountid, "name", mobilephone, lastmodifieddate, ownerid, isdeleted, systemmodstamp, lastmodifiedbyid, acu_externalid__c, createddate, salutation, firstname, email)
					VALUES(rec.midname, rec.lastname, false, rec.sfaccount_id, concat(rec.firstname,' ', rec.midname,' ',rec.lastname), rec.cellular, current_timestamp, rec.sfowner_id, false, current_timestamp, rec.sfowner_id,   external_aux, current_timestamp,  rec.salutation, rec.firstname, rec.mail);	
					aux_flag:='SYNC';
				qty_sync_rec:= qty_sync_rec +1;
					
			--update public.recordh set flagto_SF = 'SYNC' where file_id=rec.file_id; 
					raise notice 'record integrated on SF! :%d ', rec.file_id ;
				--when rec.flagto_SF = 'UPD' then
				when rec.flagto_SF = 'UPD' then
				raise notice 'vai fazer update na herokuacu.contact sfid: %s', rec.sfcontact_id ;
			perform util.insert_log('integratedata_sf','herokuacu.contact update!');     	
			UPDATE herokuacu.contact 
			     	SET middlename=rec.midname, lastname=rec.lastname, acu_decisionmaker__c=false, isdeleted=false, lastmodifieddate=current_timestamp , 
			     		salutation = rec.salutation, firstname = rec.firstname, acu_externalid__c = rec.sfcontact_id, email = rec.mail , mobilephone = rec.cellular  
					WHERE sfid = rec.sfcontact_id;
				 aux_flag:='SYNC';
				qty_sync_rec:= qty_sync_rec +1;
	
				when rec.flagto_SF = 'ERR' then
					aux_flag:='ERR';
				qty_err_rec:= qty_err_rec +1;
				end case;
			
			else if( UPPER(rec.account_segmentation) = UPPER('online')) then
				case 
		    	--when rec.flagto_SF = 'INS' then
		    	when rec.flagto_SF = 'INS' then
			    PERFORM util.insert_log('integratedata_sf','herokuapt.contact insert'); 	
		    	raise notice 'vai fazer insert na herokuapt.contact' ;
			     	raise notice 'external_aux0: %', external_aux ;
				    
			      if(aux_exist_o = 0) then 
			     	select count(*) into external_exist_o from herokuapt.contact c where substring(c.acu_externalid__c, 0, 15) = external_aux ;  
				end if;	 
			     
					if((external_exist_o >0) or (aux_exist_o > 0)) then 
					   	--add next in sequence
					   	external_aux = external_aux||'_'||(SELECT nextval('external_s'));
						raise notice 'external_aux3: %', external_aux ;
						aux_exist_o = 1 ;	     		
					else
						--reset sequence 
					   	ALTER SEQUENCE external_s RESTART WITH 1;	
						external_aux = external_aux||'_'||(SELECT nextval('external_s'));
						raise notice 'external_aux4: %', external_aux ;
				     	aux_exist_o = 1 ;	
				    end if;
				 
				   INSERT INTO herokuapt.contact
					(middlename, lastname, acu_decisionmaker__c, accountid, "name", mobilephone, lastmodifieddate, ownerid, isdeleted, systemmodstamp, lastmodifiedbyid, acu_externalid__c, createddate, salutation, firstname, email)
					VALUES(rec.midname, rec.lastname, false, rec.sfaccount_id, concat(rec.firstname,' ', rec.midname,' ',rec.lastname), rec.cellular, current_timestamp, rec.sfowner_id, false, current_timestamp, rec.sfowner_id,   external_aux, current_timestamp,  rec.salutation, rec.firstname, rec.mail);	
	 			aux_flag:='SYNC';
	 		qty_sync_rec:= qty_sync_rec +1;
						
				--when rec.flagto_SF = 'UPD' then
				when rec.flagto_SF = 'UPD' then
				PERFORM util.insert_log('integratedata_sf','herokuapt.contact update!'); 
			     	UPDATE herokuapt.contact 
			     	SET middlename=rec.midname, lastname=rec.lastname, acu_decisionmaker__c=false, isdeleted=false,lastmodifieddate=current_timestamp , salutation = rec.salutation, firstname = rec.firstname, acu_externalid__c = rec.sfcontact_id , email = rec.mail , mobilephone = rec.cellular 
					WHERE sfid = rec.sfcontact_id;
				aux_flag:='SYNC';
				qty_sync_rec:= qty_sync_rec +1;
				--update public.recordh set flagto_SF = 'SYNC' where file_id=rec.file_id; 
				when rec.flagto_SF = 'ERR' then
					aux_flag:='ERR';
					qty_err_rec:= qty_err_rec +1;
				end case;
			 
			else 
			 	raise notice 'not found' ;
		   		qty_err_rec:= qty_err_rec +1;
			end if;
		end if;
	
			INSERT INTO public.recordh
	             (account_flag, account_segmentation, cellular, contact_flag, contact_org, err_msg, err_type, file_line, firstname, 
	             flagto_sf,  lastname, mail, midname, owner_flag, salutation, sfcontact_id, validated,filecontainerid,sfaccount_id,sfowner_id)
	       VALUES(rec.account_flag, rec.account_segmentation, rec.cellular, rec.contact_flag, rec.contact_org, rec.err_msg, rec.err_type, rec.file_line, rec.firstname, 
	             aux_flag, rec.lastname, rec.mail, rec.midname, rec.owner_flag, rec.salutation, rec.sfcontact_id, rec.validated,rec.filecontainerid,rec.sfaccount_id,rec.sfowner_id);
	       
	            INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data','depois inser recordh');
	
				--update public.recordh set flagto_SF = 'SYNC' where filecontainerid =rec.filecontainerid ; 
	
		--update public.filecontainer set sf_qty_record_sync = sync_records where  id=rec.filecontainerid and file_line= rec.file_line;
		delete from public.record where filecontainerid = rec.filecontainerid ;
		
		end loop ; 
	
	raise notice 'integrate qty_sync_rec: %', qty_sync_rec ;
	--itereation for file containers
	update public.filecontainer 
	set load_status ='LOADED', record_err_qty = qty_err_rec, sf_qty_record_sync = qty_sync_rec 
	where id = rec.filecontainerid;
		
	qty_err_rec:=0;
	qty_sync_rec:=0;

	end loop ;
	
	return true;
	Exception
	    --when unique_violation then
	    -- RAISE NOTICE '%', v_query;
	     when others then
	        RAISE NOTICE 'exception';
	        raise notice '% %', SQLERRM, sqlstate;
	       	return false;
END;
$function$
;
