CREATE OR REPLACE FUNCTION herokuapt.hc_contact_status()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
                    BEGIN
                      IF (get_xmlbinary() = 'base64') THEN  -- user op
                        NEW._hc_lastop = 'PENDING';
                        NEW._hc_err = NULL;
                       	RETURN NEW;
                      ELSE  -- connect op
                        IF (TG_OP = 'UPDATE' AND NEW._hc_lastop IS NOT NULL AND NEW._hc_lastop != OLD._hc_lastop) THEN
                         	RETURN NEW;
                        END IF;

                        NEW._hc_lastop = 'SYNCED';
                        NEW._hc_err = NULL;
                        	INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'herokuapt TG_OP:', TG_OP);
			                INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '18 NEW._hc_lastop:', NEW._hc_lastop);
			                INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '19 OLD._hc_lastop:', OLD._hc_lastop);  
		                    if(NEW._hc_lastop = 'SYNCED') then
		                       	
		                    	--INSERT/OR/UPDATE CENTRAL
		                  		if (coalesce(OLD._hc_lastop,'SYNCED') = 'SYNCED' and (TG_OP = 'INSERT' or TG_OP = 'UPDATE'))  THEN 
			                  		--INSERT//OR//UPDATE//FROM//SALESFORCE
									INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '25 insert/upd do salesforce hclastop', NEW._hc_lastop);
									INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '26 insert/upd do salesforce TG_OP', TG_OP);
									perform central_sf_consolidate(TG_OP ,'SF-ONLINE' ,new.sfid , new.ownerid, new.accountid, new.salutation ,
			                  			new.firstname, new.middlename, new.lastname, new.mobilephone, new.acu_workphone__c, current_timestamp, 
			                  			new.title, new.email, new.acu_decisionmaker__c, new.isdeleted, new.lastmodifiedbyid, NEW.createddate); 	
										
			                  		end if;
							     
                          		if(OLD._hc_lastop = 'INSERTED' and TG_OP = 'UPDATE') then 
	                    			--INSERT//FROM//DB//AND//SYNC//ONSALESFORCE
		                          	INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '35 INsert da DB: old ', OLD._hc_lastop);
			                        INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, '36 INsert da DB: tp_trg', TG_OP);
			                        perform central_sf_consolidate('INSERT','SF-ONLINE' ,new.sfid , new.ownerid, new.accountid, new.salutation ,
		    	              			new.firstname, new.middlename, new.lastname, new.mobilephone, new.acu_workphone__c, current_timestamp, 
		        	          			new.title, new.email, new.acu_decisionmaker__c, new.isdeleted, new.lastmodifiedbyid, NEW.createddate); 	
	                	    end if;
                          
								
			                end if;
	                  
                      RETURN NEW;
                      END IF;
                    END;
                 $function$
;
