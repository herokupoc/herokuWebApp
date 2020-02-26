CREATE OR REPLACE FUNCTION public.checkdataintegrity(input_filecontanerid numeric)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
declare
    cnt_contact INTEGER;
       cnt_rec INTEGER;
       rec record ;
    err_msg_local varchar := ' ';
       validate_local varchar ;
    err_type_local integer;
    record_err_qty_local integer:= 0;
    error_type_acum integer := 0;
    operacao_sf_local varchar;
    type_error_db varchar;
Begin
--select checkdataintegrity(1)
for rec in             
            SELECT file_id, account_flag, account_segmentation, cellular, contact_flag, contact_org, err_msg, err_type, file_line, 
            firstname, flagto_sf, lastname, mail, midname, owner_flag, salutation, sfcontact_id, validated, filecontainerid ,sfaccount_id,sfowner_id          
                   from public.record 
                   where filecontainerid = input_filecontanerid                   
    loop
    INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data rec.firstname ', 'antes inicializacao variaveis');
    err_type_local := 0;
    err_msg_local := '';
	type_error_db := '';
   
   select into cnt_rec count(*) from  public.record 
                   where filecontainerid = input_filecontanerid   ;
   
   
   INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data rec.firstname ', 'depois inicializacao variaveis');
    raise notice 'record % will be validated', rec.file_id ; 
    --make the validations to the records
         --If Account Segmentation is empty within the container. Priority1         
        if rec.account_segmentation IS NULL or rec.account_segmentation = '' then
             err_type_local = 1;  
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            err_msg_local := err_msg_local || 'Account segmentation is null \n';
        end if;
       if rec.sfaccount_id IS NULL or rec.sfaccount_id = '' then
                    err_type_local = 1;    
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            err_msg_local := err_msg_local || 'SF Account ID is null \n';
        end if;
    
     if rec.contact_flag = 'I' then
     operacao_sf_local := 'INS';
     elseif rec.contact_flag = 'U' then
     operacao_sf_local := 'UPD';
     else 
      -- aqui deve procurar se ja existe numa das bases de dados que replicam com salesforce
     -- se houver e UPD se n houver e INS
     INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data rec.firstname ', 'dentro contact_flag vazio');
     if rec.account_segmentation IS not null and (upper(rec.account_segmentation) = 'ONLINE' and rec.sfcontact_id <> '' ) then
             --  online --> schema herokuapt
             select into cnt_contact count(*) from herokuapt.contact where sfid=rec.sfcontact_id;
             if cnt_contact > 0 then
                    operacao_sf_local := 'UPD';
             else
                    operacao_sf_local := 'INS';
             end if;
     elsif rec.account_segmentation IS not null and (upper(rec.account_segmentation) = 'RETAIL' and rec.sfcontact_id <> '' )  then
             --  retail -> schema herokuacu
             select into cnt_contact count(*) from herokuacu.contact where sfid=rec.sfcontact_id;
             if cnt_contact > 0 then
                    operacao_sf_local := 'UPD';
             else
                    operacao_sf_local := 'INS';
             end if;
     else
             operacao_sf_local := 'INS';
     end if;         
     end if;
    
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data rec.firstname ', 'antes U'); 
        if rec.contact_flag = 'U' then      
             if rec.sfcontact_id IS null or rec.sfcontact_id = '' then
             	err_type_local = 1;
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Salesforce Contact Id is null \n';
            else 
             err_msg_local :=  'Salesforce Contact Id is null \n';
            end if;
        end if;
       
       if rec.contact_org IS NULL or rec.contact_org = '' then
             err_type_local = 1;
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Contact Org is null \n';
            else 
             err_msg_local :=  'Salesforce Contact Id is null \n';
            end if;
        end if;
       
        end if;
       
     	if rec.contact_flag = 'I' then
         --If SF Contact ID is empty within the container in case of contact update. Priority1 
        /*if rec.sfcontact_id IS NULL or rec.sfcontact_id ='' then
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Salesforce Contact Id is null \n';
            else 
             err_msg_local :=  'Salesforce Contact Id is null \n';
            end if;
        end if;*/
        --If Contact Org is empty within the container in case of contact creation. Priority1 
        if rec.contact_org IS null or rec.contact_org = '' then
             err_type_local = 1;
            if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'contact_org is null \n';
               else
                    err_msg_local := 'contact_org is null \n';
            end if;
        end if;
       --If Contact First Name is empty within the container in case of contact creation. Priority1 
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data rec.firstname ', rec.firstname);             
        if rec.firstname IS NULL or rec.firstname = '' then
              INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data err_msg_local ', 'entrei insert first name');
             err_type_local = 1;
            if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'First Name is null \n';
               INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data err_msg_local ', err_msg_local);
             else
                    err_msg_local := 'First Name is null \n';
           end if;
        end if;
        --If Contact Last Name is empty within the container in case of contact creation. Priority1              
        if rec.lastname IS NULL or rec.lastname = '' then
             err_type_local = 1;
            if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Last Name is null \n';
             else
                    err_msg_local := 'Last Name is null \n';
           end if;
        end if;
        --If Salutation is empty within the container in case of contact creation. Priority1
        if rec.salutation IS NULL or rec.salutation = '' then
             err_type_local = 1;
            if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Salutation is null \n';
           else
                    err_msg_local := 'Salutation is null \n';
           end if;
        end if;
        --If email is empty within the container in case of contact creation. Priority1
        -- IF email !~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' THEN
        if rec.mail is NULL or rec.mail = '' or rec.mail !~ '^[^@\s]+@[^@\s]+(\.[^@\s]+)+$' then
            err_type_local = 1;
             if (record_err_qty_local<cnt_rec) then
                    record_err_qty_local := record_err_qty_local +1;
             end if;
            if err_type_local > 0 then
                err_msg_local := err_msg_local || 'Email is null \n' ;
            else
                    err_msg_local := 'Email is null \n' ;
             end if;
        end if;
        -- if INSERT
     end if;
     --verify in wich account the contact will be created
     --validate_flags();    
     --after validations 
     
    if(err_type_local=1) then 
      validate_local := 'N';
      operacao_sf_local:= 'ERR';
      type_error_db := 'Error'; 
     --PERFORM util.insert_log('checkdataintegrity','records validated error!');
    else
       validate_local := 'Y';
       --raise notice 'records validated correctly!';
    end if;
    -- UPDATE RECORD       
    update public.record 
        set
            err_msg = err_msg_local,
            validated = validate_local,
            flagto_sf = operacao_sf_local,
            err_type = type_error_db
            where filecontainerid = input_filecontanerid
            and file_line=rec.file_line;
        --raise notice 'record in error!'; 
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data err_msg_local ', ''||err_msg_local);
       --record_err_qty_local err_type_local = 1;
   --PERFORM util.insert_log('checkdataintegrity','records validated correct!');   
   
  -- insere dados na RECORDH
       /*
      INSERT INTO public.recordh
             (account_flag, account_segmentation, cellular, contact_flag, contact_org, err_msg, err_type, file_line, firstname, 
             flagto_sf,  lastname, mail, midname, owner_flag, salutation, sfcontact_id, validated,filecontainerid,sfaccount_id,sfowner_id)
       VALUES(rec.account_flag, rec.account_segmentation, rec.cellular, rec.contact_flag, rec.contact_org, err_msg_local, err_type_local, rec.file_line, rec.firstname, 
             operacao_sf_local, rec.lastname, rec.mail, rec.midname, rec.owner_flag, rec.salutation, rec.sfcontact_id, validate_local,rec.filecontainerid,rec.sfaccount_id,rec.sfowner_id);
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data','depois inser recordh');
       */
    end loop ; 
    -- update the File container status
    INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data record_err_qty_local ', ''||record_err_qty_local);
   update public.filecontainer 
    set 
        load_status ='VALIDATED',
        record_err_qty = record_err_qty_local 
    where id = input_filecontanerid;
   
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data','depois de update VALIDATED file container -> ' ||input_filecontanerid);
  /* 
  delete from public.record 
   where filecontainerid = input_filecontanerid; 
   */
       INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data','depois delete record');  
    return true; 
    
        Exception
          --when unique_violation then
           -- RAISE NOTICE '%', v_query;
         when others then
            RAISE NOTICE 'exception err: %', SQLERRM ;
                    INSERT INTO util.t_util_log (insert_time, log_locale, log_msg) VALUES(current_timestamp, 'validate_data',SQLERRM);
                    
                return false;
END;
$function$
;
