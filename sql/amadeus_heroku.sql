--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3
-- Dumped by pg_dump version 11.3

-- Started on 2019-05-28 09:40:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16394)
-- Name: airline; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.airline (
    airline_id integer NOT NULL,
    airline_mdm_id test,
    airline_name text
);


ALTER TABLE public.airline OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16402)
-- Name: airline_airline_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.airline ALTER COLUMN airline_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.airline_airline_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2809 (class 0 OID 16394)
-- Dependencies: 196
-- Data for Name: airline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.airline (airline_id, airline_name) FROM stdin;
\.


--
-- TOC entry 2816 (class 0 OID 0)
-- Dependencies: 197
-- Name: airline_airline_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.airline_airline_id_seq', 1, true);


--
-- TOC entry 2687 (class 2606 OID 16401)
-- Name: airline airline_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.airline
    ADD CONSTRAINT airline_pkey PRIMARY KEY (airline_id);


-- Completed on 2019-05-28 09:40:47

--
-- PostgreSQL database dump complete
--

--evangp 
--added a line to test git 
--another line

