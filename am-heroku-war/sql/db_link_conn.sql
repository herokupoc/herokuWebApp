create extension dblink;
CREATE EXTENSION postgres_fdw;
CREATE SERVER server_central FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host 'ec2-35-168-54-239.compute-1.amazonaws.com', dbname 'dbbb4hnf9hie84' , port '5432');
CREATE USER MAPPING FOR u7o65omoun2b31 SERVER server_central OPTIONS (user 'sbbxcgspaiiigm' ,password 'e8b6ce31964fa3fc8e3d5cf6f53ec646f8cf74903691310a55d906bbd5af663b');
GRANT USAGE ON FOREIGN SERVER server_central TO u7o65omoun2b31;
--select * FROM dblink('server_central', 'select id from public.sfcentral_contact') alias(id text);