INSERT INTO public.filecontainer(
	header, load_status, name, record_err_qty, record_qty, sf_qty_record_sync, upload_by, upload_date)
	VALUES ('header1', '', '', 0, 0, 0, '', '2016-06-22 19:10:25-07');
	
INSERT INTO public.record(
	account_flag, contact_flag, contact_org, err_msg, err_type, file_line, firstname, lastname, midname, owner_flag, salutation, sfcontact_id, filecontainerid)
	VALUES ('flag', 'contact', 'xxx', 'Error message 1', '1', 3, '', '', '', '', '', '', 1);
	
select * from public.filecontainer;

select * from public.record