--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.3
-- Dumped by pg_dump version 9.3.3
-- Started on 2014-06-23 18:41:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 26535)
-- Name: ap; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA ap;


ALTER SCHEMA ap OWNER TO postgres;

SET search_path = ap, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 26537)
-- Name: agente; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE agente (
    apellido character varying(25),
    nombres character varying(35),
    tipodocumentoid integer,
    numerodocumento character varying(15),
    telefono character varying(25),
    celular character varying(25),
    email character varying(60),
    otroemail character varying(60),
    profesion character varying(100),
    id integer NOT NULL,
    domicilio text,
    dependenciaid integer,
    usuarioid integer,
    especialidad character varying(50)
);


ALTER TABLE ap.agente OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 26543)
-- Name: agente_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE agente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.agente_id_seq OWNER TO postgres;

--
-- TOC entry 2296 (class 0 OID 0)
-- Dependencies: 173
-- Name: agente_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE agente_id_seq OWNED BY agente.id;


--
-- TOC entry 174 (class 1259 OID 26545)
-- Name: archivoproyecto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE archivoproyecto (
    link text,
    proyectoid integer,
    nombre character varying(50),
    archivo bytea,
    id integer NOT NULL
);


ALTER TABLE ap.archivoproyecto OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 26551)
-- Name: archivoproyecto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE archivoproyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.archivoproyecto_id_seq OWNER TO postgres;

--
-- TOC entry 2297 (class 0 OID 0)
-- Dependencies: 175
-- Name: archivoproyecto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE archivoproyecto_id_seq OWNED BY archivoproyecto.id;


--
-- TOC entry 176 (class 1259 OID 26553)
-- Name: avanceproyecto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE avanceproyecto (
    id integer NOT NULL,
    avance character varying(100)
);


ALTER TABLE ap.avanceproyecto OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 26556)
-- Name: avance_proyecto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE avance_proyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.avance_proyecto_id_seq OWNER TO postgres;

--
-- TOC entry 2298 (class 0 OID 0)
-- Dependencies: 177
-- Name: avance_proyecto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE avance_proyecto_id_seq OWNED BY avanceproyecto.id;


--
-- TOC entry 178 (class 1259 OID 26558)
-- Name: beneficiario; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE beneficiario (
    id integer NOT NULL,
    beneficiario character varying(35)
);


ALTER TABLE ap.beneficiario OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 26561)
-- Name: beneficiario_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE beneficiario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.beneficiario_id_seq OWNER TO postgres;

--
-- TOC entry 2299 (class 0 OID 0)
-- Dependencies: 179
-- Name: beneficiario_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE beneficiario_id_seq OWNED BY beneficiario.id;


--
-- TOC entry 180 (class 1259 OID 26563)
-- Name: comitente; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE comitente (
    id integer NOT NULL,
    razonsocial character varying(150),
    cuit character varying(20),
    telefono character varying(25),
    email character varying(100),
    tipocomitenteid integer,
    contacto character varying(150),
    cargocontacto character varying(150),
    sectorid integer
);


ALTER TABLE ap.comitente OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 26569)
-- Name: comitente_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE comitente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.comitente_id_seq OWNER TO postgres;

--
-- TOC entry 2300 (class 0 OID 0)
-- Dependencies: 181
-- Name: comitente_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE comitente_id_seq OWNED BY comitente.id;


--
-- TOC entry 182 (class 1259 OID 26571)
-- Name: configuracion; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE configuracion (
    id integer NOT NULL,
    clave character varying(200),
    valor text
);


ALTER TABLE ap.configuracion OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 26577)
-- Name: configuracion_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE configuracion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.configuracion_id_seq OWNER TO postgres;

--
-- TOC entry 2301 (class 0 OID 0)
-- Dependencies: 183
-- Name: configuracion_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE configuracion_id_seq OWNED BY configuracion.id;


--
-- TOC entry 184 (class 1259 OID 26579)
-- Name: convocatoria; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE convocatoria (
    id integer NOT NULL,
    convocatoria character varying(150),
    descripcion text,
    bases bytea,
    link text,
    formulario bytea,
    tipoproyectoid integer,
    fechapublicacion timestamp with time zone,
    fechainicio timestamp with time zone,
    fechacierre timestamp with time zone,
    tipofinanciamientoid integer,
    estado character(1),
    organismo text,
    beneficiario text,
    montofinanciamiento numeric(9,2)
);


ALTER TABLE ap.convocatoria OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 26585)
-- Name: convocatoria_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE convocatoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.convocatoria_id_seq OWNER TO postgres;

--
-- TOC entry 2302 (class 0 OID 0)
-- Dependencies: 185
-- Name: convocatoria_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE convocatoria_id_seq OWNED BY convocatoria.id;


--
-- TOC entry 186 (class 1259 OID 26587)
-- Name: dependencia; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE dependencia (
    id integer NOT NULL,
    dependencia text
);


ALTER TABLE ap.dependencia OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 26593)
-- Name: dependencia_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE dependencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.dependencia_id_seq OWNER TO postgres;

--
-- TOC entry 2303 (class 0 OID 0)
-- Dependencies: 187
-- Name: dependencia_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE dependencia_id_seq OWNED BY dependencia.id;


--
-- TOC entry 188 (class 1259 OID 26595)
-- Name: estadoproyecto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE estadoproyecto (
    id integer NOT NULL,
    estado character varying(50),
    descripcion text,
    estadoabreviado character(3) NOT NULL,
    proyecto boolean
);


ALTER TABLE ap.estadoproyecto OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 26601)
-- Name: estadoproyecto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE estadoproyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.estadoproyecto_id_seq OWNER TO postgres;

--
-- TOC entry 2304 (class 0 OID 0)
-- Dependencies: 189
-- Name: estadoproyecto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE estadoproyecto_id_seq OWNED BY estadoproyecto.id;


--
-- TOC entry 190 (class 1259 OID 26603)
-- Name: etapa; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE etapa (
    id integer NOT NULL,
    etapa text,
    fechainicio timestamp with time zone,
    fechafin time with time zone,
    duracion integer,
    estado character varying(30),
    proyectoid integer
);


ALTER TABLE ap.etapa OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 26609)
-- Name: etapa_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE etapa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.etapa_id_seq OWNER TO postgres;

--
-- TOC entry 2305 (class 0 OID 0)
-- Dependencies: 191
-- Name: etapa_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE etapa_id_seq OWNED BY etapa.id;


--
-- TOC entry 192 (class 1259 OID 26611)
-- Name: evaluacion; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE evaluacion (
    id integer NOT NULL,
    fecha timestamp with time zone,
    observacion text,
    usuarioid integer,
    proyectoid integer
);


ALTER TABLE ap.evaluacion OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 26617)
-- Name: evaluacion_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE evaluacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.evaluacion_id_seq OWNER TO postgres;

--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 193
-- Name: evaluacion_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE evaluacion_id_seq OWNED BY evaluacion.id;


--
-- TOC entry 194 (class 1259 OID 26619)
-- Name: evaluacion_pregunta; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE evaluacion_pregunta (
    evaluacionid integer NOT NULL,
    preguntaid integer NOT NULL,
    rating integer,
    observacion text,
    aprobado boolean
);


ALTER TABLE ap.evaluacion_pregunta OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 26625)
-- Name: fuentefinanciamiento; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE fuentefinanciamiento (
    id integer NOT NULL,
    fuentefinanciamiento text
);


ALTER TABLE ap.fuentefinanciamiento OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 26631)
-- Name: fuentefinanciamiento_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE fuentefinanciamiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.fuentefinanciamiento_id_seq OWNER TO postgres;

--
-- TOC entry 2307 (class 0 OID 0)
-- Dependencies: 196
-- Name: fuentefinanciamiento_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE fuentefinanciamiento_id_seq OWNED BY fuentefinanciamiento.id;


--
-- TOC entry 197 (class 1259 OID 26633)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 26635)
-- Name: perfil; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    rolid integer NOT NULL,
    rol character varying(30) NOT NULL
);


ALTER TABLE ap.perfil OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 26638)
-- Name: perfil_rolid_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE perfil_rolid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.perfil_rolid_seq OWNER TO postgres;

--
-- TOC entry 2308 (class 0 OID 0)
-- Dependencies: 199
-- Name: perfil_rolid_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE perfil_rolid_seq OWNED BY perfil.rolid;


--
-- TOC entry 200 (class 1259 OID 26640)
-- Name: pregunta; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE pregunta (
    id integer NOT NULL,
    pregunta text,
    proyecto boolean
);


ALTER TABLE ap.pregunta OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 26646)
-- Name: pregunta_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE pregunta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.pregunta_id_seq OWNER TO postgres;

--
-- TOC entry 2309 (class 0 OID 0)
-- Dependencies: 201
-- Name: pregunta_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE pregunta_id_seq OWNED BY pregunta.id;


--
-- TOC entry 202 (class 1259 OID 26648)
-- Name: presupuesto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE presupuesto (
    id integer NOT NULL,
    proyectoid integer,
    fecha timestamp without time zone,
    estado character(1)
);


ALTER TABLE ap.presupuesto OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 26651)
-- Name: presupuesto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE presupuesto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.presupuesto_id_seq OWNER TO postgres;

--
-- TOC entry 2310 (class 0 OID 0)
-- Dependencies: 203
-- Name: presupuesto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE presupuesto_id_seq OWNED BY presupuesto.id;


--
-- TOC entry 204 (class 1259 OID 26653)
-- Name: presupuesto_rubro; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE presupuesto_rubro (
    presupuestoid integer NOT NULL,
    rubroid integer NOT NULL,
    gastocomitente numeric(9,2),
    gastouniversidad numeric(9,2),
    estado character(1),
    gastoorganismo numeric(9,2),
    total numeric(12,2)
);


ALTER TABLE ap.presupuesto_rubro OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 26656)
-- Name: presupuesto_rubroitem; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE presupuesto_rubroitem (
    descripcion text,
    costounitario numeric(10,2),
    cantidad numeric(10,2),
    total numeric(10,2),
    rubroid integer,
    presupuestoid integer,
    aportecomitente numeric(10,2),
    aporteuniversidad numeric(10,2),
    aporteorganismo numeric(10,2),
    id integer NOT NULL
);


ALTER TABLE ap.presupuesto_rubroitem OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 26662)
-- Name: presupuesto_rubroitem_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE presupuesto_rubroitem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.presupuesto_rubroitem_id_seq OWNER TO postgres;

--
-- TOC entry 2311 (class 0 OID 0)
-- Dependencies: 206
-- Name: presupuesto_rubroitem_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE presupuesto_rubroitem_id_seq OWNED BY presupuesto_rubroitem.id;


--
-- TOC entry 207 (class 1259 OID 26664)
-- Name: proyecto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE proyecto (
    comitenteid integer,
    convocatoriaid integer,
    nombre text,
    resumen text,
    documentacion bytea,
    observaciones text,
    duracion smallint,
    agenteid integer,
    tipoproyectoid integer,
    cudap character varying(30),
    fecha timestamp with time zone,
    id integer NOT NULL,
    beneficiarioid integer,
    tipofinanciamientoid integer,
    localizacion text,
    fuentefinanciamientoid integer,
    documentacionnombre character varying(200),
    avanceproyectoid integer,
    estadoproyectoid integer
);


ALTER TABLE ap.proyecto OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 26670)
-- Name: proyecto_agente; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE proyecto_agente (
    agenteid integer NOT NULL,
    proyectoid integer NOT NULL,
    funcionproyecto character varying(35)
);


ALTER TABLE ap.proyecto_agente OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 26673)
-- Name: proyecto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE proyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.proyecto_id_seq OWNER TO postgres;

--
-- TOC entry 2312 (class 0 OID 0)
-- Dependencies: 209
-- Name: proyecto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE proyecto_id_seq OWNED BY proyecto.id;


--
-- TOC entry 210 (class 1259 OID 26675)
-- Name: rubro; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE rubro (
    id integer NOT NULL,
    rubro character varying(60)
);


ALTER TABLE ap.rubro OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 26678)
-- Name: rubro_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE rubro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.rubro_id_seq OWNER TO postgres;

--
-- TOC entry 2313 (class 0 OID 0)
-- Dependencies: 211
-- Name: rubro_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE rubro_id_seq OWNED BY rubro.id;


--
-- TOC entry 212 (class 1259 OID 26680)
-- Name: sector; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE sector (
    id integer NOT NULL,
    sector character varying(50)
);


ALTER TABLE ap.sector OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 26683)
-- Name: sector_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE sector_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.sector_id_seq OWNER TO postgres;

--
-- TOC entry 2314 (class 0 OID 0)
-- Dependencies: 213
-- Name: sector_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE sector_id_seq OWNED BY sector.id;


--
-- TOC entry 214 (class 1259 OID 26685)
-- Name: tarea; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tarea (
    id integer NOT NULL,
    tarea text,
    prioridad character varying(15),
    estado character varying(20),
    fechacreacion timestamp with time zone,
    dias integer,
    descripcion text,
    fechamodificacion timestamp with time zone,
    proyectoid integer,
    fechainicio timestamp with time zone,
    fechafin timestamp with time zone
);


ALTER TABLE ap.tarea OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 26691)
-- Name: tarea_agente; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tarea_agente (
    tareaid integer NOT NULL,
    agenteid integer NOT NULL,
    funcion character varying(50)
);


ALTER TABLE ap.tarea_agente OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 26694)
-- Name: tareaprogreso; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tareaprogreso (
    id integer NOT NULL,
    fecha timestamp with time zone,
    progreso integer,
    estado character(1),
    tareaid integer
);


ALTER TABLE ap.tareaprogreso OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 26697)
-- Name: tareaprogreso_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE tareaprogreso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.tareaprogreso_id_seq OWNER TO postgres;

--
-- TOC entry 2315 (class 0 OID 0)
-- Dependencies: 217
-- Name: tareaprogreso_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE tareaprogreso_id_seq OWNED BY tareaprogreso.id;


--
-- TOC entry 218 (class 1259 OID 26699)
-- Name: tipocomitente; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tipocomitente (
    tipocomitente character varying,
    id integer NOT NULL
);


ALTER TABLE ap.tipocomitente OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 26705)
-- Name: tipocomitente_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE tipocomitente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.tipocomitente_id_seq OWNER TO postgres;

--
-- TOC entry 2316 (class 0 OID 0)
-- Dependencies: 219
-- Name: tipocomitente_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE tipocomitente_id_seq OWNED BY tipocomitente.id;


--
-- TOC entry 220 (class 1259 OID 26707)
-- Name: tipodocumento; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tipodocumento (
    tipodocumentoid integer NOT NULL,
    tipodocumento character varying NOT NULL
);


ALTER TABLE ap.tipodocumento OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 26713)
-- Name: tipodocumento_tipodocumentoid_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE tipodocumento_tipodocumentoid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.tipodocumento_tipodocumentoid_seq OWNER TO postgres;

--
-- TOC entry 2317 (class 0 OID 0)
-- Dependencies: 221
-- Name: tipodocumento_tipodocumentoid_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE tipodocumento_tipodocumentoid_seq OWNED BY tipodocumento.tipodocumentoid;


--
-- TOC entry 222 (class 1259 OID 26715)
-- Name: tipofinanciamiento; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tipofinanciamiento (
    id integer NOT NULL,
    tipofinanciamiento character varying(100)
);


ALTER TABLE ap.tipofinanciamiento OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 26718)
-- Name: tipofinanciamiento_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE tipofinanciamiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.tipofinanciamiento_id_seq OWNER TO postgres;

--
-- TOC entry 2318 (class 0 OID 0)
-- Dependencies: 223
-- Name: tipofinanciamiento_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE tipofinanciamiento_id_seq OWNED BY tipofinanciamiento.id;


--
-- TOC entry 224 (class 1259 OID 26720)
-- Name: tipoproyecto; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE tipoproyecto (
    id integer NOT NULL,
    tipo character varying(100)
);


ALTER TABLE ap.tipoproyecto OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 26723)
-- Name: tipoproyecto_id_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE tipoproyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.tipoproyecto_id_seq OWNER TO postgres;

--
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 225
-- Name: tipoproyecto_id_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE tipoproyecto_id_seq OWNED BY tipoproyecto.id;


--
-- TOC entry 226 (class 1259 OID 26725)
-- Name: usuario; Type: TABLE; Schema: ap; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    usuarioid integer NOT NULL,
    usuarionombre text,
    usuarioclave character varying(255),
    usuarioestado character(1) DEFAULT 'A'::bpchar NOT NULL,
    usuariorol integer NOT NULL,
    usuariofechaalta timestamp without time zone,
    usuariofechabaja timestamp without time zone,
    habilitacion text
);


ALTER TABLE ap.usuario OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 26732)
-- Name: usuario_usuarioid_seq; Type: SEQUENCE; Schema: ap; Owner: postgres
--

CREATE SEQUENCE usuario_usuarioid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ap.usuario_usuarioid_seq OWNER TO postgres;

--
-- TOC entry 2320 (class 0 OID 0)
-- Dependencies: 227
-- Name: usuario_usuarioid_seq; Type: SEQUENCE OWNED BY; Schema: ap; Owner: postgres
--

ALTER SEQUENCE usuario_usuarioid_seq OWNED BY usuario.usuarioid;


--
-- TOC entry 2009 (class 2604 OID 26734)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY agente ALTER COLUMN id SET DEFAULT nextval('agente_id_seq'::regclass);


--
-- TOC entry 2010 (class 2604 OID 26735)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY archivoproyecto ALTER COLUMN id SET DEFAULT nextval('archivoproyecto_id_seq'::regclass);


--
-- TOC entry 2011 (class 2604 OID 26736)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY avanceproyecto ALTER COLUMN id SET DEFAULT nextval('avance_proyecto_id_seq'::regclass);


--
-- TOC entry 2012 (class 2604 OID 26737)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY beneficiario ALTER COLUMN id SET DEFAULT nextval('beneficiario_id_seq'::regclass);


--
-- TOC entry 2013 (class 2604 OID 26738)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY comitente ALTER COLUMN id SET DEFAULT nextval('comitente_id_seq'::regclass);


--
-- TOC entry 2014 (class 2604 OID 26739)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY configuracion ALTER COLUMN id SET DEFAULT nextval('configuracion_id_seq'::regclass);


--
-- TOC entry 2015 (class 2604 OID 26740)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY convocatoria ALTER COLUMN id SET DEFAULT nextval('convocatoria_id_seq'::regclass);


--
-- TOC entry 2016 (class 2604 OID 26741)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY dependencia ALTER COLUMN id SET DEFAULT nextval('dependencia_id_seq'::regclass);


--
-- TOC entry 2017 (class 2604 OID 26742)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY estadoproyecto ALTER COLUMN id SET DEFAULT nextval('estadoproyecto_id_seq'::regclass);


--
-- TOC entry 2018 (class 2604 OID 26743)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY etapa ALTER COLUMN id SET DEFAULT nextval('etapa_id_seq'::regclass);


--
-- TOC entry 2019 (class 2604 OID 26744)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY evaluacion ALTER COLUMN id SET DEFAULT nextval('evaluacion_id_seq'::regclass);


--
-- TOC entry 2020 (class 2604 OID 26745)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY fuentefinanciamiento ALTER COLUMN id SET DEFAULT nextval('fuentefinanciamiento_id_seq'::regclass);


--
-- TOC entry 2021 (class 2604 OID 26746)
-- Name: rolid; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY perfil ALTER COLUMN rolid SET DEFAULT nextval('perfil_rolid_seq'::regclass);


--
-- TOC entry 2022 (class 2604 OID 26747)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY pregunta ALTER COLUMN id SET DEFAULT nextval('pregunta_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 26748)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto ALTER COLUMN id SET DEFAULT nextval('presupuesto_id_seq'::regclass);


--
-- TOC entry 2024 (class 2604 OID 26749)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto_rubroitem ALTER COLUMN id SET DEFAULT nextval('presupuesto_rubroitem_id_seq'::regclass);


--
-- TOC entry 2025 (class 2604 OID 26750)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto ALTER COLUMN id SET DEFAULT nextval('proyecto_id_seq'::regclass);


--
-- TOC entry 2026 (class 2604 OID 26751)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY rubro ALTER COLUMN id SET DEFAULT nextval('rubro_id_seq'::regclass);


--
-- TOC entry 2027 (class 2604 OID 26752)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY sector ALTER COLUMN id SET DEFAULT nextval('sector_id_seq'::regclass);


--
-- TOC entry 2028 (class 2604 OID 26753)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tareaprogreso ALTER COLUMN id SET DEFAULT nextval('tareaprogreso_id_seq'::regclass);


--
-- TOC entry 2029 (class 2604 OID 26754)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tipocomitente ALTER COLUMN id SET DEFAULT nextval('tipocomitente_id_seq'::regclass);


--
-- TOC entry 2030 (class 2604 OID 26755)
-- Name: tipodocumentoid; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tipodocumento ALTER COLUMN tipodocumentoid SET DEFAULT nextval('tipodocumento_tipodocumentoid_seq'::regclass);


--
-- TOC entry 2031 (class 2604 OID 26756)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tipofinanciamiento ALTER COLUMN id SET DEFAULT nextval('tipofinanciamiento_id_seq'::regclass);


--
-- TOC entry 2032 (class 2604 OID 26757)
-- Name: id; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tipoproyecto ALTER COLUMN id SET DEFAULT nextval('tipoproyecto_id_seq'::regclass);


--
-- TOC entry 2034 (class 2604 OID 26758)
-- Name: usuarioid; Type: DEFAULT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN usuarioid SET DEFAULT nextval('usuario_usuarioid_seq'::regclass);


--
-- TOC entry 2236 (class 0 OID 26537)
-- Dependencies: 172
-- Data for Name: agente; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('Leguiza', 'Marcelo', 1, '12345678', '011-12345678', '011-1234567', 'mleguiza@undec.edu.ar', 'mleguiza@gmail.com', 'Sistemas', 28, 'Buesnos AIRES', 1, 59, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('Dario', ' Wolberg', 1, '12345678', NULL, NULL, 'dwolberg@undec.edu.ar', 'darwol@gmail.com', 'PROYECTOS', 50, 'Buenos Aires', 2, 81, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('ooooooooo', 'oooooooo', 3, 'oooooooo', 'oooooooo', 'oooooooo', 'oo@oooo.com', 'oo@ooo.com', 'dfgh', 82, 'fghj', NULL, NULL, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('iii', 'iii', 2, 'iiiiiiii', 'iiiiiiii', 'iiiiiiii', 'iiii@iii.com', 'iiii@iiii.com', 'iiii', 83, 'iiii', NULL, NULL, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('Maza', 'Hugo', 1, '20261282985', '03825-15411736', '03825-15411736', 'hmaza@undec.edu.ar', 'hcmaza@yahoo.com.ar', 'Desarrollador', 25, 'Martin Fierro !054', 1, 57, 'JAVA');
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('u apellido', 'u nombres', 1, '3456723', '345678567', '34567834567', 'hmaza@undec.edu.ar', 'hcmaza@gmail.com', 'Biologo', 79, 'M F 123', 2, 114, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('Maza23', 'Hugo2', 1, '26128298', '423488', '03825-15411736', 'hmaza@undec.edu.ar', 'hcmaza@gmail.com', 'BIOLOGIA2', 43, 'Martin Fierro 1054', 1, 1, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('Sun2', 'Java', 1, '98765432', '987665543', '1234556787', 'hmaza@undec.edu.ar', 'hcmaza@gmail.com', 'SISTEMAS', 46, 'USA', 2, 77, '');
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('A', 'B', 2, '888999999', NULL, NULL, NULL, NULL, NULL, 80, NULL, NULL, NULL, NULL);
INSERT INTO agente (apellido, nombres, tipodocumentoid, numerodocumento, telefono, celular, email, otroemail, profesion, id, domicilio, dependenciaid, usuarioid, especialidad) VALUES ('k1', 'k1', 3, 'k1k1k1k1k1', 'k1', 'k1', 'k1@k1.com', 'k1@k1.com', 'k1', 81, 'k1', NULL, NULL, NULL);


--
-- TOC entry 2321 (class 0 OID 0)
-- Dependencies: 173
-- Name: agente_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('agente_id_seq', 83, true);


--
-- TOC entry 2238 (class 0 OID 26545)
-- Dependencies: 174
-- Data for Name: archivoproyecto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO archivoproyecto (link, proyectoid, nombre, archivo, id) VALUES ('cars.pdf', 87, 'cars.pdf', '\x255044462d312e340a25e2e3cfd30a332030206f626a0a3c3c2f4c656e677468203630382f46696c7465722f466c6174654465636f64653e3e73747265616d0a789c8595514fdb301485dff32bfcb83dacb31dc7b15f8b0069521fd8aa211e4de300232322c0aafdfbb9f1459a72738caaaa95be733827eef5e5b9daeeabda0a27add87795145f543b7f3bdf5757d5f3fcd6e25b029795dc34e25839b7a945eba550d26cac50564cb1fa415079bdf1906adfa64f448dd41b05698e6d4d2916508a05946201cdb18d2bc5024ab180522ca039d6e8522ca0140b28c5029a63b52dc5024ab180522ca0395615470a508a059462019d63ad2f8d14a23916d11c8b688e6d4b238528c5024ab180e6585b1a29442916508a0534c79ad248214ab180522ca039b62e8d14a2140b28c5327a755aa44ac8f452c2cb79476ab1ff5d7dbd5042a56f7df569377671f8bcff755aaeffa99537f3d25cca6f6298b85a7b3f2f51f6c7c3d35b1f0eaf6f535c71995456adb8cec6615c91e70738fd2738a9f5bb5a05d3442d3bf80cdce1ad82cfc0d4dbdd35accec497538c4fa8ba950b7563a56cfba86175e650deb5b03a535fc4690ad303accf0cd7f70faf11d53766a10eb24ba31e560c549f39b494f8e499fae738fc19617926df4ee3119ebd76cb9354bdd131ac9c2695e70edf3a589ea9cbe599fc260ec37844ed955e1ebded948ba686ed9923b5c747cfd4e5f64c5e1a7beb9797a493ae69f50196e78e5279ae3ebb9ffebe0c78e370476974ac5bde925e76be73d2c3fecc51bab65cfdc1b5e586ef7165fbe5f276794b0e32a48d53f7b03c73a475696079a64e93f3f8720c776bf340fd99a7d0df2c2f4a2d4d675c0bd73d77a4e1b1b03f5317d63d1797f6a5add9a595b2696ef52dacce1ccafb955b42d599faa3b961865d98c6f1fd77fa07bea3cc9a0a656e6473747265616d0a656e646f626a0a352030206f626a0a3c3c2f506172656e742034203020522f436f6e74656e74732033203020522f547970652f506167652f5265736f75726365733c3c2f50726f63536574205b2f504446202f54657874202f496d61676542202f496d61676543202f496d616765495d2f466f6e743c3c2f46312031203020522f46322032203020523e3e3e3e2f4d65646961426f785b30203020353935203834325d3e3e0a656e646f626a0a312030206f626a0a3c3c2f4c61737443686172203131372f42617365466f6e742f54696d65732d426f6c642f547970652f466f6e742f456e636f64696e673c3c2f547970652f456e636f64696e672f446966666572656e6365735b36372f432037372f4d2038392f592039372f612039392f632f642f652f66203130382f6c203131302f6e2f6f203131342f72203131362f742f755d3e3e2f537562747970652f54797065312f5769647468735b3732322030203020302030203020302030203020302039343420302030203020302030203020302030203020302030203732322030203020302030203020302030203530302030203434342035353620343434203333332030203020302030203020323738203020353536203530302030203020343434203020333333203535365d2f4669727374436861722036373e3e0a656e646f626a0a322030206f626a0a3c3c2f4c61737443686172203132312f42617365466f6e742f54696d65732d526f6d616e2f547970652f466f6e742f456e636f64696e673c3c2f547970652f456e636f64696e672f446966666572656e6365735b34382f7a65726f2f6f6e652f74776f2f74687265652f666f75722f666976652f7369782f736576656e2f65696768742f6e696e652036362f422f432037302f462f472037372f4d2038322f522038362f562f572038392f592039372f612f622f632f642f652f662f672f682f69203130372f6b2f6c203131302f6e2f6f203131342f722f732f74203131382f762f77203132312f795d3e3e2f537562747970652f54797065312f5769647468735b353030203530302035303020353030203530302035303020353030203530302035303020353030203020302030203020302030203020302036363720363637203020302035353620373232203020302030203020302038383920302030203020302036363720302030203020373232203934342030203732322030203020302030203020302030203434342035303020343434203530302034343420333333203530302035303020323738203020353030203237382030203530302035303020302030203333332033383920323738203020353030203732322030203530305d2f4669727374436861722034383e3e0a656e646f626a0a342030206f626a0a3c3c2f4954585428322e312e37292f547970652f50616765732f436f756e7420312f4b6964735b35203020525d3e3e0a656e646f626a0a362030206f626a0a3c3c2f547970652f436174616c6f672f50616765732034203020523e3e0a656e646f626a0a372030206f626a0a3c3c2f50726f647563657228695465787420322e312e37206279203154335854292f4d6f644461746528443a32303133313232373139353532322b303127303027292f4372656174696f6e4461746528443a32303133313232373139353532322b303127303027293e3e0a656e646f626a0a787265660a3020380a303030303030303030302036353533352066200a30303030303030383536203030303030206e200a30303030303031313833203030303030206e200a30303030303030303135203030303030206e200a30303030303031363936203030303030206e200a30303030303030363930203030303030206e200a30303030303031373539203030303030206e200a30303030303031383034203030303030206e200a747261696c65720a3c3c2f526f6f742036203020522f4944205b3c62643135386432373663616338316334373161373762613764333863663263313e3c64613034393365303735393134626332376231333066363031306133626162353e5d2f496e666f2037203020522f53697a6520383e3e0a7374617274787265660a313932360a2525454f460a', 8);
INSERT INTO archivoproyecto (link, proyectoid, nombre, archivo, id) VALUES ('10311989_10203498282876276_378700643_n.jpg', 88, '10311989_10203498282876276_378700643_n.jpg', '\xffd8ffe000104a46494600010200000100010000fffe00042a00ffe2021c4943435f50524f46494c450001010000020c6c636d73021000006d6e74725247422058595a2007dc00010019000300290039616373704150504c0000000000000000000000000000000000000000000000000000f6d6000100000000d32d6c636d7300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000a64657363000000fc0000005e637072740000015c0000000b777470740000016800000014626b70740000017c000000147258595a00000190000000146758595a000001a4000000146258595a000001b80000001472545243000001cc0000004067545243000001cc0000004062545243000001cc0000004064657363000000000000000363320000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000074657874000000004642000058595a20000000000000f6d6000100000000d32d58595a20000000000000031600000333000002a458595a200000000000006fa2000038f50000039058595a2000000000000062990000b785000018da58595a2000000000000024a000000f840000b6cf63757276000000000000001a000000cb01c903630592086b0bf6103f15511b3421f1299032183b92460551775ded6b707a0589b19a7cac69bf7dd3c3e930ffffffdb004300090607080706090808080a0a090b0e170f0e0d0d0e1c14151117221e2323211e2020252a352d2527322820202e3f2f3237393c3c3c242d4246413a46353b3c39ffdb0043010a0a0a0e0c0e1b0f0f1b392620263939393939393939393939393939393939393939393939393939393939393939393939393939393939393939393939393939ffc2001108015b020e03002200011101021101ffc4001a000002030101000000000000000000000000010203040506ffc4001801010101010100000000000000000000000001020304ffc4001801010101010100000000000000000000000001020304ffda000c03000001110211000001f12354c00060260000009a1a08600345340000000000109490c0000001a6a8020001301340d304d0d8a34659c1a4395cb4d6b2260c0e7b88d001b8000d31300000004c10d430280620621a000000001343020001a29a68000000081340d3a400012804a260a49ef000920b3974a95d5d900370062600000000000000000d3000134000d34000260098000d304d0000000009a18020013254d3941a81a7d398d30d793473e96e5b2bb2a07a8000000000c43400000000c10d301371127000281a006458c4000304d0000000009a180200133340250051a974e498c5ab2eae7b965bebaa186a0000034d0c4c430134000030000016acb3cef7e1dd8f9f4a40edc01821a000000681820000680004d0c01028604d09a80458e70974c369c2d145bcfa0a7490037900000626000000009a06000000081c68a62677176d372da7a8268138920d19d667ab2c343de5000304000000093250081316138bb97284b7925194369f2da8ce2b103a640069a189800000010cb3b78e9c05d2e6eb2346b2004651259098e129441e9cc0994a36a961751b62dc5d1ece7a7933b5c6d66ecf1770c658aca92dd5ca33621eb9c643451b3bb2f9f059d26aca84a71d14a2ef3728be5d08c92c40e980000437170c0a0014e128eaf4b074f8fa7070bb9c3df20174e6c8916373c74ce06f0c0976e2d58b3a968cbd5a8e2f57c0ceead5bb232fadccdd359f87d8e16b111ad66d9d0f36e5505c52169505ae82afbb111a2bac2c750b714b59102e66ea249a894c0de4003565d58d5fcedf81a00df369829475cd43bde7fd963a794c7e83cfeb991e8ed4e14f5d98e986422968e9ca4e139a23bfab8df9a3d25166fe6f4b04e962e6f459d3b29670726ba59a2bd506a32b1456ae2292e454ae555ab594970525a254ad2aa2c12a7605458aa048480cde1305b3d5f99f418e92e0f639935cc6ce9c10c5365919d21ebfcafb497caf17d17053d5df923953a63a4f1c9437802cb9aefa7a93597d3f9fd99e9dde7e97c7a72f9f62f6f972ecc95f2d772be44e2ca94b1b43537249cae2e236804d0c12005218086449222dab10d09356356d1acd8e966ae8f2a5cfb75b34b394c77e0b807196cb3a14dabd8798f44e9e6f9fd3e038f4ade3e8b9e85592e9aa32eaa3588595bdf304cd5b70ebf571d142ebf3df1fb3cfa2d9e1ef723874cd28cf32b946ec74aa6137124421a513088c449b223290d003129c449c6e4184548b0d19a3acae8f3ac8ea439a72e9d4a6582de9e5baa5cec8eae8ebf07d0c947638bd49db272b573262304faf96cb2fe7b534c2869ef9938e9e99d91ebae3789dadfb796b977eba3c9d783939efd9276573ebce16425cfa0d29b4c492481b489288ad2680f7d73cd986e510765a93cee235600904c05137ce644872ac5eaf3e467a685402adc35cfa1ab3dd9f56fddc9df8d65e27a1bb7cfc8595dbaf3efe74e9b26284d2946572fb95473d29d7c4d9d38fb39f8d5c6fa5e0d3577c50e2f975981a928a866cd252cc8324441910928c69902c9f47941bf1c0b9b7465d8b44624d05625a41c348a92b82a57850694536c8ceebb472e62e37cf4cf3e9e7eeb9ceacf5d5b6df23d3cd2b2a9df3657746cb2a9f4e5e259e83996639c14d76716c7db95146fcbe9e198b2bc6f34ac7e3f4155f1211b54545a5545a1516a2b56a2a57866340998bcaa0bc2a9ca4b0b60e6aea5c16b94932eb9c5a992590394449350d19e69bb2b2b8cd5cc69bf56a73e7e8b0263ddd1cb2f314263aafa0968cd2977638c1138bad91ba8f4f0b2ea7bfbe5e7a8d39dd22d1e2f4c84592489a6250c40c40c4020b01037190a328a1384a9314a465112658949134c8538cd652566772a6fac75595880b0f41c7eecbcfbf954ef9f5f903c6e326ec75d8880e2b064ae62a48b5bee7ab8703d0f11c8eaebcdae0392f2f75252b20d39a44a52d6ad8911a04d000886086094954490252424d22196454954849253adcb7df974e772a2ece1115cb480ea72fa1be2b9fd7c32d16573cf6d8450d20cb19f4939566f8d9576ecc19defab8598d7549af4b4d1ae3cdb73d66b732e6924674a516a2688920892420104000a802000135426904d524d592011b4d6dd19b467467d19848572f7e0dacf461359e79b2eacb359e754f7d34b94655194569f55e53bd73d3f39ebfc86f196972e7d22c4b729935afb3c6e927061bb3595bb84cc352a015a01a40021884000001354818804435624d08158da63064acaec9a55d9048b00d5975c6a44312545b9a2943eb6658888e32c7adc869e87ceecc5a85955934938c5f641cd6ba277ea4ced68df2f232e9f2f9f4cb17154260d000026904d911a242645a6204342b1a40008a2d522074e56158b7cf338d31a12e879c34db85c746cc3a71ab33a76c18134d4ae3144a2ef339b32119a641588b1c24d6ad5cfdcceebf3cee25c4e87366b345c5a0086860200010c0043945d4414311621c4682c1308a692b03af2001b4c0001a0632ed397573eb9a44eca21a29b2338173a2cc6675b7767e9e379f95dae42d5290b08dc92125227753aa6b72e5971667515ad49086580295884626a8608103010912706342d41a49222410946ab07d390004a2121048403096cbab9e7a2929ac6b9ab9a1ef9a64bea8e35774f8fd09abb9f6c082be59d645757a9072692dd975677cf5643594e0c88d22400086e20d0c8926905354a75c22d511588b1912c684488a8941ab3fffc4002e1000020201030303040300020301000000010200031104101220213113304022324150051433233424426070ffda0008010000010502fdd7e3f7bc8ee3b43ffe121499c0c3dbff0080afc47f3fbf5eca18c6f3fbf4ce3bc6f3f1c2968cbc7f4ebd972d2cf3f1ea8c830f81fa6032a11a5be7e3a9c1cf2962e0fe943627a863367e4a5b88edc8fcf1fa0c4edb1f1ec5398f5fc51f1f3ece7dcac9c91ce32107c40092d4ba8e855e51ab65fd1569cca69131aaa427b6a066c5006fe671d90710b2aac01aec648ebe6d09dbccc4ef311f03f8adc09c7e2a2966a2b2ac3906d42965e95192cb8e81e6cfb3608c2147741da229624032b5cb228075fe3237522769da769f4cfa67d33b4ed3b4ed0dac6bc898130276df0276f84a4a9177a815be9bdcfa7d35f9b3a079b1b09b55a80c34dc4aea9425da7ff23d9aac8941cb7f22763b0f9b4af7b9463ab19884d67238ea6d776e8e3d93cd9e771e5ce5b89df47fe5ac5e56695c11755deaaf2a02d6baa7e76f1846c167198989c6626262713313131313131313131b6262626263aa82737b9eb5f0a39d96f044d52576e9e69f4ad74b7468b5aa802c23824b3ceebe538faa544b74aac9a7d32b268bfcb5403d89ff001b8b896370c6394d4ffaf69c76c7c1c6f8f62b00bb85aeaac8b06b2a1559d03cbf65d18cea750bcaa72069c79d30e35b0e4129c3debff0d6bc998f7debad9c8052d03287edb4b2d146a16b4d43976463957a9cb594e99ffbad2db0d8d99983a73d39df3efe228196b47a751553aa3ea5b89898989569ae29e95cf5e8bfdcf8d47fd6afee5bd70b7f29ea11188f4dc7d7b221733476d693526b76a2cfab3ca3615320c35388db887c9f97c96660ef3115f88a54da2cadab5e0d62f12b31310ce57714d4dd58ac14bc4d4396d35390dcc330b384b2d5319be8b476d9199679dab1dbec4fedda2333d8718646046a138d87a7bfca45ade329539332663b231566b2d74a2e746bf9593061ccef2a3c95d007b9786a9dc2aebbb527304169c20568318568c307a165870d5f10f9092ffaa2e4b7722f5e2dbe4ce4666676cfbe66667a5700ea2c166d53d6abeb246b50cae1b1254d2deedde7e2bb82cc735d4b72b3907a2d21aa6f48c751b2af62c209674289a34e5759d9e54bea1fea8c7a75d55ff656c3a93f5413f3933bccccfb3999e8cccef9db3be763b7e31b53f69fba9f367dc666549ceebdf85e41b668fbd344b538d9b38c69e09674278d37d1436985b6d7a7ad763e6dff001dc4fcc3ee282c4e95a7f5da30c6c3da3074516a286ff4a6c28d6333b4134233abd49ff9abfb34df7d69ff009772325c209aaec0ecde208064bd2ab559aa26ad0ddc1b2040c25da94ae5da8b1d7710f9cccccccf5e6676ce22eb1c0b352f64cc1882b6b232b56d3333d38eaaf966cad94d7cf2dc836dfc7ffdbd47fa584aae81b32c715eb7f90a390fca8c9bb21fa313418e3accce304efbb8fa20d843ed9f605d685877cec7b6f8db131b02446b1982f696586c18989a2fa752c4b4bbecd3161358a668edf551abf4edf48439240ee0096228854ac00714b0d66db8dad4ad4d547aca2c07b9eeb042538887d8c6d88419dface31c562f18eb5ce208e260c455cc3ec1104231bd6407c76b47d348e2fae7ac526d683bcc087b19da6996a27d1cd9780b618250e8b5e90817ea6cf52dc46131bfe3dccccccccf5e21ed001edac68b1ba7260b196273bdbfa0632fa6c2583bed58533e805ce5a082b04788aa58d88d5c3d1db6ed3b4c0981313131313131313138ce338ce338cc4c741db1ed2fdbf9487cefa6a858c74f489e92d962d4b5cbb5183e6096799981809cc67236062b0e32b7e2dab5f5288dd8fc0c19de60efde77dbbf5676c0844c4c098138cc76e3076e8033294e0babb78ae9b8fa57dbc7a0eedd23c6262689f9d24619ff004221eac4f1d1a719b25c79ba9f4e672662626211b1e94a9896d3294b14a1aac6a99152f2d5a00477e9c19c1a71698f8e3a17a33d08595c5dce1d219f98bd0fd0312aa4d92ba5547d2235a258dea3cd20c1bce1663a7244e6d3919c8fc8c6ebe73d7994ab63d1ba5f8e7167258489911f044ab49ce93a36034eaa6eecb2cd5011ee77dab9f9a3eeb86522fe8d7cec6779de66695973a9f52b885b857e9bcbc7168b388981302381b7f1a4369ec4e52e415cb2c7b263759f9ab2ade438c38fd1af9ea5babe35df4703e892da7c9fecd8939662e27686031f6fe2a5adc9754ead641ba43e7ff5a1f926a5716af9c4e3fa11d695820575818ae72067151b62719c4cc43b7f1ad8b75d6baaec21d921f23ed5760534e5c35494ee7cfbc7e08ebae3343de08fb0821d9b65255b51a837ee21d927e4f8a5035540532c55e274aa6187e666729ca728184e42721322646eb99de0f10f51ea10ec8613df90e3a5d57a41b5abcbfb14e1f588a861f7ff001f073d5995e672603d469ce7259f4cc4e27a2b5e4d757c3710ec36fc69c855c07270030565e50f9fd063aeb87c1d8ee1c89eac1c1a70c4d2acbd730ee7a50fd28d2c7ec6ced0ed933333ede3e4acfc18218663a133c6afb2d8c3bf4f69da28ec4f72667af1be7f4020d86cdb0ef0544c01121b2576612cb0187b9e26621e849f827bf4e3d8c6f9f963c0d961d91161ed09dd7ec306edd090f86f33333f37ffc400281100020201030304030003000000000000000102111203102120303113404151042261425071ffda0008010211013f01f7ac5fe818bdeade42efd7796f2177a0f935171de5b58c5dfc9f4c55928d7616ec5da51b24aba9aa114447c9e9babdd0d47e366c8ec9592d36b9176508d4deb8d979353c9a757c93e18b6ca87e4c8cccbf867fc325f464be8c90a67a85a322f787927e778ab232fa27e7928c783fc7646060ec92b625b455928d9823d330304608f4cc0c11818a31312b682352256ca4945a2a9264f97b793f9b22325f23f02d49197d1643bd26c523e0b55c9450b493359711245a6b923c12fbdffa26e7f269cf0744e15fb2da1df83a336396d1925e4836d9292943fe1aab0189f07c6d263d3fd7821a3f66290df1b43aecc8cba6cb323cc6cb757459f89e5938351b357f6a1962956d8ac79252713d73d47265ed74666464cc9993322f7436cb2cc4c4c0a95152a313f1155938b6b83d48fa74ca3128c4e4d45e097934f966262626262626262625142422dfc91e07b22b6f8d9a66321417cf56b3f8230b46856ebbeba519f3d34513d17e51a509590d19295eebda64c8f9e9bdd6cf65ec10f67c942fe17bc88df8dd742f6aba18b7437f665c97dfb32664646466265965f521ecbdac766b6522c5d35d37d57db88f6c44b65d0bb15b5f5fffc400251100020202010403010003000000000000000102111012032021304004314113155162ffda0008010111013f01f69e50fdd7988fdd7988fdd7943f34883efe67943f3d74b74277e07944bc5645df52763c317636e8578489654897858c866fbe19018b358a28a28ee5145145145667f4432c7111659fb9d8d845e1b148d8d8d8d8d8d8b2cd8b2cbc59222cb2c7f658bb74b1c5b5659fce35dc71aec50fcc921ac567722fec4ecaa2447fd65255438c38bba4724378ec43914953c3f3cd59fc90a39944fd146c451fb8e356c5f252e57b1cbf3d3ed143e79cfed918ebf587e0a35e9a28a3b5d14513fa23f62e9d9a7d88714669a3fc6ffd11f8f0e28e68a28a28a28a2b34562cd8d8b8971b3626c453b2cb2cb3b1c52d53671b6e36ce6ed12cb3636363636362cd8d8b2f17e04cd91b757c78fe9c9ccf8e715f8cf929d7a4fab5edd7c7ce97667ca719468973c5c2bd797643974d7b544bebb8bbfd159437ed318974be8434579e8a353535351c4d4af13f55e161aebbf59e6cbe8587e8ff00ffc4003410000103020307040005030500000000000100021121311012200322415051617130324081133352609143a1e162707280c1ffda0008010000063f02ff00a0dc3fdc5b0561fb04af6fcba7ec181ca2c3f73d94fec2e8ad8405240fe74dc2a8e450ab5523d3aaa69a1c2a15d56a808f42a75b6d25fd3e54050ea15dbca71f5f316d14b5800c28154caa2ee81d3c170560acacacada06cc9dc160ac3e4c856de183a447a918e4dafd2ddb22386175997540721989546c7a19859070a4acaee1a67d4a9016595385178e451ac4d8268ea50cc3747f64768cfe9db09b0462eaaa835cc4a10009448a1599d816aa28268b70495bdc8466b2dd6850fd9b5ab76c7515b3f2884f0db1180c2a1394660dee74ee84d0eeaab7c1d9565825660b7aa8348cbfea950c77e377145bac6852e856f9e37c2015a11de6c0d39c406f7598365bd90f28a73780145695504286801705528e303021f443f0cca01c54356f597b69c215b90db45029cc029badd00a822345df9540753c200df360477c0e556525d270cc319698d023dc6aa293e15492aa150a26377905e0a83a242ab81ecb75a0a9ddfad226c4aed2bb9b2681fab1ad574442836d71d10cff6b2c81e135c2d1080698256572a58fcf994230efe17f85fe14a8d3c709ec9ae3d166228b82a1c332efaa50a505d3bace01d06d16596602710db05fa4f551c94ea6b7a951c214c2737a220a73470c40ebac26f045f9b74aa371da78f870bafdaad3efe141d9e62ad0a8d0549118b13bca08a76cfca2d755d8b06a853364366ca0594fb5cbdc15c2a6f1465d4e83e1d15580a8a018c3185de022c78208e1eb6e95256e182b7af8b13bcad9c7e94f06e838a1b5176e10a0bb36a78354d68e3c354f7f91946d5e1bd27e04152aa31695278ad9ffc567694c7f508ec9dd289cc26c54870fe5570a95ba655410aea8818b23bb8349b918c4c636766ebf16d0bddfd9446550c398ae1874c2baedaa55d33c21e5192150c2ae9f76f7053ff00a8db139e9e10448b0a732b9542b267bafccfecb2ccc69aaf6ab69817433089e615145ec0b746e8540256506ba2fe8870e0b30e15f46cacacad8dbe45959595b4c61038a96d5471f5ceccf0a223a727e3ac607a2a293ea59528541521179fe1446bb15ed3fc2f69f9b6d72d2a091f4a58e9f2ab8df45f47656c2ebb60513aee57b8ab9573c91ce22842dcac71eabbf1f43f13329cc164757a614578d279665025c551b9bbc2df0413daea21c0f495ec70f3e847429c2dd102d76f0b2a9d40ce0472b8da6ca6388a15946d5ccf2bf38b8f734598ed87f0b2ce618dd5d5f17a12216e8b527d1f3caeb0ad2bdaa037d1cbd539bf5e8eebb2fd226497752b79ce9f14e6c1c2e15a15bd0b19990a32c79473442395df5f3acacacacacad8dd5c2bfc7ca5a4b7b2f6185f9b4e842c9b11f7f067e4dd5f0a8562bdca87d6e0aca617b42902398dd56aba613e8d95b9bd70bfc5b722beab2ad30a2a956f956e45654e5fffc4002b1000030001030401040203000300000000000111211031514161718120304091a150b1d1f0f160c1e1ffda0008010000013f21fe598f77abbfceb3d34726783b4329fce53264cff14c5f7ab45b883e4095fc0afbd5a6e7fe00ad19b5a197bff3fad2914f234877bfee368c219d57f855a383b0fc9fd7f712f0c72688e9f42bef168e6a5c1c0fd982f8fb8dc0508e3e81317db3d56a883d709690e7ad2fb8a21766c659fcdeb1b7843abecde8abafc17c1dfb7656e2e5a43f95eb91ca1cf289b367f5dfd12f83faddcf02fcd997d1c9d7453aa0e5904150d6c68826b3d30123167afc1bb29c524fc443f8af8ad5f9fadd20c4a75ea25bcc43c3dbe9b93b050693e0843b91f06763989ec3d7c9f43ab836960be9a266fb93be94e41887a46290970371329ee8bfcbe1cc251a9aad71f590155b19530d84431e4318a2576f92415b17c3604a8f55cb71ea4431d68d792f9cfd0c6b1918b18aa0897783ed0f482385e23c7449c24e3fc9383f24ff00b1e1fb3fd6743feb6d16c53f51d97f93b2cf62aee55dcec327031dcc7d065f9a5bf285ae3adc2d729151bc2f85d31f9640ea63667712d752dc5048145596448b82da963c113473b95dd7dc61eefc4842108426909a4f87b33c99e7e9b581d4b78f9a74b3027e40a0ce3484d24fb2f8df02f990e25c03423ac259d03232669d46605ec8eae08848320d40db93068e94f247996516597a165965165f057051447a28a28af92983c7523e1cfcd134eca8e68ac541db571607608372b4dc06c4a7c9934945f730452dc8df0ed988067146c37d44a493c7526166ef91a4c6371d852988e82eabb52296b6781736780d2522e08222112d1e4c2ea55a45ac308dfae908e4a8c1e5a63922f8acc1129c61724a41f4dc8de44e3e3b07e84f02b1e85269ee58ac8756ee8b508aec29a87740425a27427f9a622df04b584412094b61df04a83683d3d68f4cc74248d18639b7bb6189fce20ffb1324815d9101f68c9f41b2f63a17b1e1a39c181b478157051846383060a888c22a3063829191919544db7ed1985ed592ebf68a792b918f2c64ae0ae0a1ed0e4cb9694de0b1ef4ac63c36c60ac15888875f901cabb9a298490a495b5c7c02762c1cba893c0dd9b246faf236c8bcb136b4bbdea276345ec21df52370be03718e114a5298e0a8bd8c1e8f460c0e69e8c706383f1a4ec7a31c18e346d2e87704f445268c1034d0e0be369ca1fa21ea37329c1e257027568784fb4b8111a49d187a9864f9360c34c2a5cca665c4972670a39b28bf46117415b0b27512d351ad2dd86db56f3a297709b630665c2204afbc33ad16131762868b8e094e4115ab6d11d46cbc3f464a5f1a442839c174c7ce18d594ab4a602ba5d47754643a898f730c989116fb8e5dc209d61dc5dbd2e2246e9947b13709509847b8f02f1741a241be820bce327d59ccc669e1980448337550f62a82137bc31da2d51b6975782aa2bb05719abb0fd7351b72e40b700160b9fec4cf40b616e4b158f90ae4ae4bdcf21b294aca52b2e94f652a303150a33c19e346c54ffa4a5dc96889568655107594d8e62f82edac8db086b425dc869dfe82929d54992cabc1109abb460239568dc4e31b5761bb0937dc469ababe460a776fac6366e0bfbfd9bd52b278b1b11b26e376736b5ea4e5381b61c91b7d1b06c1f70bdc267bb1b6971a5294bdca514529ec6a8ec330a515945656513570131068aeda2ea919388c4ef0760c42db18c60c0212d8cad65a1155c754352db4d824d68499d0db69df44de5867905f6630b712e4cba6f7c8b1a1fbd16bb467250a9923f87a3d17b19d1450e81afc0436790a1a4e946ee7e74ea64c99324652089aa7819c03d66b7361f9bdf147959b89074dcc93ef4a3bb87caf830c38689318b052e43fce8dc5f60d5b1a6cd86253a88b85a65f22c668597c991fff00433a8075552f2639bb284849830243371d41a196a5f25ec545ec528e07da54277ad936497582cc3848ee28f2c7945e43708b374d1c8ab4baf913be9ecf67b62bc395df2491b8983c2315c4ed8a64fef9fbd28119eab083b184c4a4f37742dc40a95ee238cc75172d979c17455d44640aba1bc26e61f721209b61b6fc91aca6cb4cfadb434635dc56f8d334c698d31f4cb7257ae893ae591c8f52c99349db442b441cd4cae71d90f70dd10122aea51e07a131c3eb28ed60625647646439c0d8c63aff002225a3c5531b5346c661ab15b872a57e480ab7d10fa79c4663165bb8d92288231e72b1acd46252a85ebb320c84a34b73623ae4c2acdeac1bff00ce88b92108444ee43c8851c264e0ccf0cf5a4647c1e86645ec1773e0323c09d5f537a7b084f38771393706651ac0ab0d22ae460ab82ae0ab82f63d09510dd095edaea25339862470918d9d4723692e8180d7090c3aef119e7b84ee45a22f6136dc746da02775c5c750b49e8322381919a61a1719188e200862381a6ef4439d0a8be37e6fc3458d8a5ec5ec525d8c10c4585c1b82ae0c18fa1e431bc6c9747b15f2cffb074acce1e65924ab40b7a819d443cb4a3fc5b136d04322218b91ed9360a2b6dba1965d01b1b0f2414ee7b09227fa64ff004f43b846af911c91cfc0797ecf22062cf6152231a21447c7c57c26b6d139268c157255ca2a28a2924f62b063a89aade07e4c3813dcdd6c4cac13d4542391857fd8d959fce954228b3c791b484ef86a2979f63c69b02f665f25f227d9fe0aaecff07a7f83dbf05ee3d8cf22be19e99919e0cf047a138686c3a7fbce8ce8c99326782f62b82b17008bb09381710b8847429114055d08695d9117088b8450916fd858648c8c0621343ccdd94af92e2ade8bcfc8dcca7b9685ff00f220fb3ba1b297b9ef5cddfef5bb4544248450596e5f21f3af870f682dcdd1c394df9c8d9dbcc478b2f8653e48ad326426895d909db109ef664039125fda178937b87c142644445c91cdf4cf72db7e0d052dff00015c327621ebed4f45e4dc4ec1edb17be8a52889241b5e682f5347d03b87469bc4b01f916fb8f8cb1b42ae4a8c034bb1d981a18e344b92bb56363ac86f01dda0fc8aba3279263667e4a2da23d9ff0074ff00aba2565652974f47a3d14bf0f5f1cf1ac3c88f9fd11f261d465bb65c6e3949dc84237b26d8956e9a7c337d4aab7b0f693b27815c102460fa0a036206c0d1af78ed0dab47462c3d86d7e0c5b57d879d0e10ebdde8670f4263b68aa93b131b7d3f44eda64c97b7d94f0349aca307865e65e078322b2c6fc1d11858a33a8cc50773f43d1fb8ad44c86f2281db4760436c6962990dd71c87dd640cba5c213926897670f229949e0687cf42be74a5460c7c6fd2f4444d7f3f4c6d189b21bc94a5272cfb1e133866f9ecf58eec2e7a0815981e72346af0356e2c03a08e03a9ba28cfc45b164c93eb4bdcd13b0d76d1ba181c3309f5581cc6bb847a2f01ac99e0acacceb9e4f6b4bad2eb4f7a5d7dfc3deb1f24ee2677221c1a21111158c94ac60d4eaf4d017736f239d08e057b31a7813f542a5d58a7225ecf6c104c26d098d36ee6ed37e952ca0cd84d87e9daa9a3e98bc4d098dff6778a365294c7c294a5133078295705297e9528c51fa306356ca3a48c06c63bba6e1307b0bcbd10732d328b955e5dcac51d4df453c0734306ddb20b5263c723f7ca5766df01374c9ba427d2f5a3fa5ef5f168408ffa3903be3ca77dfe0eccc14ec21b15b919e036ccde88ac6d89bf8ee89810cf23af43825371bc741185dfb5189d155cb2ac3c4a453e5b2ae8fe85f851a7b18d31ae0c7d4a2e94a5d0ccf70c2f8bf04f44ed9ecadfe484ef618d3ba66745e4c60a6c690413b10de3b773217cd2d328c8de1b0cf266c8dbbaf894ba52983060c7731dcc0de37f9e4c991e9efe69f09d886e3d86e260418bc9b1389ba506c69d0df664cadd6c3b286e31c8a73a918e1954dec9c7217c7ec8b63f66ca154115d8dba9e03bd11afa3d69eb58647dc64c98d2975cfd1c974c7c3ccac6e1b1a138288432878049960983349d85e07e0f5a79b12e86de8e3077595dc7eff3acee25dc69724195b15f511c42eb4ce8ca53d9ecf67bd3d97e9639f96c1987e4b6639d48f04cf4e3ab567005272312383a31ff00b32d147bd513661623af065f423e09e066595a63e33464ac772141ad31acf863ebad0ddab791324b23c740e6f7d11b26d37e8d7c8c0306cae4ae7eb3d531fd3fffda000c03000001110211000010d14e1c00051f5ac73cf282f28c75c55eb4d7ad7bfcd694edfc0f28d04110d94e38d3cf291037c5555a435bcf38810d16dbbf3881473ca04528014d14f1877d25556d054438e6c1d5aac978100f38d287141385c3f34a31f79514f05f04a7b4e2cb2a59814f1423013ce04b3c4bcc3453890473453c02fb9f5ac1b479453ca14f00b00f3c8fefa8534dba634c3ca28c2bc6433ef7143c50843cde4facf2a21b8d612f50f5bbf6663f4c6abcb24c0073cbc003f017907e9450f98169836a3dd353b1f7dee78bf973cdd0f10a5cad3ceab4cdfd61c00194ddaa79ce0b14f8f42f3bb572b08ce14c46b2dc381671f9721217b9e9f1d7dce5bb4fa82c69773e63bd226b49be046e5d026b19a4ff00e74538f5a47cda74c2d3b0309f04298471863b1400cf985cef817e634c76b53b1ac69f2f8fe76c6ba3c16e644c58a6ba73ad9ff7888375a5b0fbe04888096ce18d00e508011b08143308fec6d5f95a4d3f432879f33ed5c020ed5b5465f5c9eae322421a7a46dfc79f74828e2628d1b9d009148234b6ad1a42c701ff00793c30fa40f6ebbac4a8d6c0083386ddf8290c57e26ea62db53efdc3dcf3de0b98f045f959705013cc51f7321be608f87afb080badfe5b9f2b9b8b193f79bb3a50ff00389b5896ad8114f73ebd75f6be3771f330047e3ddfd24ed62701cc7f022d39f2194fa599de3b075f4d569693bb10a5fa4e4578b57204a0cac64b4da94d47f39675c41fa753d766f090ec2eed38c7ccbaff00b4667b5a3d591fffc4002111010101000203000301010100000000000100112131104151203061714081ffda0008010211013f10ff00a7dc7719dd9ceddeed13ff00580d965deef1ff005974f3daef0f0b08f5fb015c250e3fb4eee9e1bb5de3a603f601ca0741fb4eee93c7877bbc7b839fd870eca18f527e02f191f3f9871b2e349f1deef11f986bcc9cc95e3e4f01a813bc61acacd923b1cec361d4e67124c3b3661ab1f971ee24c2e47877811da23cc11dfe19e51a6f5100c78b3a678c609bf7e1606ca20c9d20387fe48d74b70c86b0ea38a3262163e167e2c7c5fc505d4fe501d16f32fe563e59f9e40f399c0f3b95e8ee65cf6ea088ed0de609a59e3c39213a55603848d9dc9b93af10a76d8711958d93ebcd62cdf0b037f6b362d58fc83053dc0aa196ac7e4cc396d95c2c79c9103656aa0f8137984e2e5962ce25aa1a7b8d0316badad5217ddb6c2cdacec1b71d4d9e1cbdacff00c8303dd9cdde603bb6d1d909e0253371b2c27aa84147623c9d88f0f2a8b02c7816be346d1e97b3c1dd83fb65c4f94f1c4f06ca44ce038489425cc42601255319b45ea7d9eb9897d502c392cae864a6020d6f8c8e2447b320ea876e4ce63b9f0c3cdbccb6db6ce240750971b74ee6dbb96991930b53b80eacbd4fb3b761a9fc9e1ead6af490c723065893ec38ec3246ecc0fcbfcc8be896f163b90fa5ab7f855ab7f655ee1e249c109c61bd12be5a876a0903c40b078b700d76dcbd81ba7db5f6c78b49b0bbba6329f7003078b643c6c0187443848673b6b77656dfeaddfeeff76fedab70ec6d8e84b080ff0060ad26a704990e3c3a32f8db3fb23319c33601103a8cc804d6d3ad8c8dc1dc0efb27b44e652d243dd85858587cb0f961f2c3e487c80f9184f50139921f207cb0cea433a90c8cc81d938c871289241c58663e44aa6db0e71eec110246e7dc8cb4b8fcb98d9db9b19257e4390e92f1c5aca86d96e33f6b998970e603d300169ea1cb1d41b21c58585967f6c7c6dc3e126c9b61958b73dc519b6fa45b0f2b13d417b6584ba4f96b8bb8e23ae43ea4e6c656db2db6db2dc49e36d65f004496170730b9b007890b0b33ab698dc4c613c7780e7a46387505ea3f92f96d6d6d7c6cdb2f8dfdbfa43fb2e1dae92d0dc83b67ec6bab67a860e64e678f71d11b0f760ea1b6db6df1be765b65fc0cfc171063cc1d90a4877723c0ee7b871835bb5858589d36b7f5696f95f05fd243887b9ea35096710b0923bba5eff2c245890a1d99f2dfffc4002211010101000202030101000300000000000100112131105120304161718191b1ffda0008010111013f10f867d39f2cf98f1bc5d6e8f8df9be0fa8f1967926ede471757e96cb3e863e86ed6db74bab1dcfd8a06b089a7daf5f075bac773d7d854e2c30bf163e6f5e05975badb8dbbf63047487e0c348c7cdf577f3d6e931f35c24962df2f85c3621a4b8879b2992de103ab58675e99071bbea71d4b8be340e625cba491f01f214c24ef25bc7c692b8f8e8da0766e39732e738939b0f31c925776ff0056bddaf77fa94f6daf76bdf8ebddfebe0f45acd7cbc33ddb72c4ce24f52d7023c570b80012739981ad80dbfb21604adb56ad7e5afdb50ed96addbb769625cf737562c7b8284e8b9292c6308bc40166bbe3a7162070591f7fc91fc1fcd9d2ac0713b0ce2cb2c8f5e38f25be0ba12eefe582ec72d253b18f70cec80536419494e996e124e5e03789419c4c76d8093886de8574cf53e65e3ab7c73f1e61d81f04729ac6415ff88174581797a80ddb17a80348157f635ba904337679f2df83c1f37e5d6a0e089ffb136099ea35e93cc79bf2e1836cb2cb0d8dfec80b31f1970401d9d3acd31a3cd8dc1e63065d5230310dceeee4d76cd464d20dd64883bffd809cc7ce6bed80ddf086cd9b1658367f24bd900e091d9060e5fd590647ab3ea51d4e65039cd8b60c903c9cd96cebf646e12631cc1975b928c6a1d45c7179c969bfb25fcb39d58f563d58f563d58f563f08fe7c8ee465a0f0cefb84fd87657d5bfcbb76fdb5f501d97f296bc5fbaceef13bbc58963272baeaca2746ff006c247881b191db9b9b9b9f70dafbb5f76beed6d636e6361764fb857f6d7ddadafb9d8deafe44f773b6db04161614f3d996ea3b90dc4d8dc96fc78f0781848c906cc82c2017206996d865c464c1b28372558199398f0db6be34b8b8b2cb61f079c98b2c25b71ae3f67ae5b97f8f0dc1b20f7753ee7a9f71d5a49e32cfa4cb3c330dac90d929cb20f11a5ace477b2d7646fc98eb687f650f33e7882cb0f8059e02c5990c09125bf2b43b6bd496466c92e43c4e64429d32579b2cb2c8b3e21241f41e765d3893fb210bd5d33f0dcba5cdada3dd859eac7e1964127d0dda23b9ccb6c9b7c13c3f3e5b0ddd9b2647713e7fffc40028100100020201040202020301010000000001001121314151617191108120a1b1d130e1f0c1f1ffda0008010000013f10fc88eff33e0ff3f338ff0033f2466a5085f48009473062e67e661642ef1b8d25c435f1c90d44951fc986e3f99bf83e48ff0082be799c7e27f85f92318e00ed1453b117c581d888b86fc4aea7a8acaa54a8ea157738f80cc0653595892be4fc0dff0085dc3e48fc57f85dc35f81f9bf2fe0ee31545e7896b8971e533d667afc1a8c3662e1388caf93e1d4e273f9b0f83f07fca3f243f27e5fc1dc75389f7f1f72c98e3e0d46609351c2807560bfba3d0a5fe151d42731fc2be18461fe0bfcdd7c1dfcf3f9bf2fc1f0ee3a8ea63e310debe3a435f06d8b98dc0d622ade5f5007d21f8710f8744afc587f85dfc1f9308ff95f8271f0c789de66f50b8dfc7486be36fa9cc4129ac4083cba1197f1f15f8112a3a3f323bfc5e273f0fc1f9710dc727e2ebfc44e3e1ed1d13efe2e33cdc69aaf936fd44a8d201ce4ff0013481e06118054bc1f854a8449c7e6198eff000a9c7cb4e01cad12a301d48379fcf9864fc5ff00111d4225c75f72b12be7ebf0c6ea319416d605dc1d040eb01066b0f93e4dc671f2caf923b87795f3c7c306413384271436ac13420651bcfc908c231c4a7f07fc447e1dc4c1e6563e165f7977a836fc9dea33248bd550a97ec20a0e1a5fc9f83a2710fc8dc7f13e1840dab9a94292b4a911eea9fc8f8a8e7f17fc247e3371d1f88783a6676c3e39c61b08b295b946bdcff701da05631f9f1f01f91f8b38f8660f69427b0ac44a547074999c621f27c4842a917b102d363d1864f87e78fcd8c371d1e67dcc5436625443cb10dbf1ccc4655fc2461c7f1f3cfe17fe0253f06be4e61368802d3921cacdf00437da65a810c541fc399a41b9c42dca98cb5330259070441011a6ace65ff88971606750501352fe3962861f8268c3e19b6ebfc5c7cb03968eb2aa00d654a8956b6731a4be7e7887c2ce22803c15f23653a9a59772fe15e90034c71161b8cd1ea01b96420694a58cca9c35195a1ef0921da0226c2ed21e41844e95c5444732f1f1841268405976604b1d8fc711c132c3cbb9b4b357995f0efe26fe2ea0e18b32e2ca5ff4fcc6a5cb832e5c5977e6b60ec16a170c04dd6d5fe204940d3b7e2b88e5863e480ecb3a39954374f9029474e486d5fb88ec0b45284f2b2868add6a21d01584cd4ae0b20031442a5172ad30a68854429f1e203229f3326ae30dc2c680bb58b3204ed1bae90436349ccb2a80d0b732214f32e0ca0fbb8ec8be08ad2aef2d4b33d0812c4a20401cbb2cbf1158ee5a5a43888cdac6494d75af834f98e9dfc3156c7dfc9fe3e0748ba8ea0c22c4cf2758bf749764afd711469415b29b29e271f892131ce6037467adc21f18a7781d93e3788ed6d41416c5410541543b4b02c77083ca8d2e87dca432aa0a0f10c430706187c6016dab1ff004202d2e30e4f89bbe7e0881bdc977f441aee9973ff00de67ff0055fee775fb7f7166eb3a0bfb8ef01e14ae2bde01d0fbc45947dc4004c4a176f766020ea11f75194e7cbe65202abe7fd40747bffa8a6c7f7fea3c1ec855b7b95c4f68bddee29dfdcc747dfe493441f243f2a4e3d8f1e236370e584e6a03e4a60e5ed035b751f2b5b9bea55f351516dc6057561f214016cb2f564e8447960840a2646138508955d908091874ab8620a49d177500cd0541aab9739937a4cc056b945b6354a704d98cdabc4404a8291ed0dc02e653a4afc4313292928949e52a7932bbb2bbb13bb32691f72fa9ee2adaf73ed95de57795de53f91a402d23c46e0872505888d3b30fc1f0fc514aad2f7e92b201a6f03da602ac8606e29421037d1be712fe1e250e420edd8f2903462513507e1a04bbed18adad0778cdb335a94289981b10d371ad1b422320207650dffec09b2e85cf73b4254a0dd9c112c95c38acc460267119208a079be600ba759656cb2223988cc17c305fec8a33482384678fdca3744b8b299d99cd5fb88f1fb9d5278276cf72d701ee23fedf010dd1f73c13b51756c1b367b9dc19ff001728e3f7f8b12d8132ab083054e54afcd582db0d2195eead12e70a89452e825d1c18b9e043a788bad5c619bc11bcf385f80ad55cb56be212242c6cdcc041dba87cbc2ad143cce8f36119179104c4583e65f1fcc465d5e6102012d1e1eb1201006aa78021b0351384751d32d685285d59d654ab4207b0b33184a5b48bc40259cd2d6c8c19b90042e2c0710f7a80a46b75d11c4142155281b5f98874e74b0669b1042e9f72ed5c6b8854a58d5ddbf72984a3ab5f4a9e4bf12edacfd1283998ef285e7ea51e5aef1430a429613f50074bf71bf35282d4fd4a716fa8db94fa94ff00e2507311728f52bf16f913487300cc05a6177a8d083826501a51a3adc9e3f12b51688eb1be63b2016043bb6ff112b702fa9995a5daa0cafd44f416e0008d2ef15109576cb8ca68b8adc78e6c46396e7b2f23d20b4aaf012ef3bb5057541954742afe0b952aa11955a08489053c3986d074dcae302a6ccb508aa55b064e55d258a8e112a351140a1b4a94b4385a6092804085da959224721550576736f8eb11747c595ff90e02358a64d3ed82154af311fed00e2fccf1cc649f57126bf69a647b97a6aa2b66df72f02a9f3095828efb94787b832e1ee54744bb82513260ef2c6a8f98ab5fa658ce7c5cb37fccb1e7dc7a0af98b1a7dc05c3f6cc18474676196689dafdc101833b5d412b4ca6e3b5d541003af2aae3886a90196caf9b6070d01707ad50dc51082384d3e2511d0fda21b2bcb024fac1b06c4ed1430b658ad29836c70c880a71505dc29488738d43654a91b54c7ed816d81bad5c70b01c64d460886c52afd7112024f004f4c6abd32ad111512ec725e25fc131cb1115611a48ad00d7412e60b5361db88ca13036c2ea77801a9c8ea31506b6e0568eab1a67450a15c679fb8f45816272436d0a6ad2a0bdca0f82b819583dd8a0333e254d0078957411051464dc7ac1836d18f48af112f0082b818b5c1837b24c9543c54be820a68816141ea60dc0a401e6783f89474f694378202b49f495d08a340f12c78940522da47bb1a329e597341b8f505f98e00be5399d100a6bdc307664d43cc51823443d231489b61965d3dca9c1f64570f1aa97c422b314e952b72204a0301a9702224dd9cafee000bacac0d1618b6b104d68805b795d011060100742fdc3f75556027ee08521423ecae630cb0536b75082b13337a844341d5d2e204295576bf0c2cb59fa84a1964c8683cb6cb8222b1d9e674bdc32d75a0c4645020a11a85898c534afea757544302ee185b6e1e18970e22e6dd6626eafbd5cb9dea050d805748a0d03e219e4f4853b0f51e97fe419592ad07c93a683c106dac4768a3d3d40add114edea14f4f52846ea76088a942ad2344a1e3dc61614ccf8260c84b3a110f0304bf5f145e9e22020792acea7680308990255e2612a2e6920b0571c2e018c982a1db0c676ed3740878b74883e2d631404d8c08441d20294d365b883cd60a4e8c4d5194716abfb8ac6d3b718a206056832abcd742e1d3ae854691a601844eedcb550907831a66cf4b01a023684b00b6dcb3eb9a5e25d9a4d88ecf8d65530ca1cb2d1d2001dae225b8a13a06332d31706c1fd41424d629095637bd414c4e855a22d8bdec6575541bc0f79603019e4ee3b311682c3a32a9a82af2ddcdc70183a1026fbd541b6863fecc69586ba41744252db9da9f70b94e66c78f131ebea5f627dcb8df3a09f7f5144d37e2615c45a86c99b85dbaf100729f51030abc621b0be2a5cd063c451fcb1056d078968e8af116f47a943986d2d52fa28854ab8073a9a50edcbf72f377f599403e047e6e39525d402e9653f7294515852328852dbab82ab53ee223fe6021d2e50c5406f0053c3b23b94a52b3614929e50da5a8dd7a84ecded111c586cefde5b944e30912bc2e906e562182c1e4c3f72c228290e5814da9c02c95901aac68edf034c36521010d3823355a42c1343f72ce65bbcdb0ded6b6795fd46a0333d8aac7c47b837992556f82b8945c28e96e8eaf30615e4aecf4f30052a0d8888ea925ae60356174d7301a3d237da7954db1e59730d9e20b86fea2859fd456aedfb82eb2dcad4475676a7cc5d6dbf13b8cb3afa8bef01d58a72aa2954be25d36af10564b1a70dcb1cff112313a3716db18763f535e1fb82935f50bba0800a85a6e12da217389632349c4cb0cdb0c8a1a97a63439886cfdc21a967782c16381d173fa95fa19873830f9e2275329552f9894eb007813fb94dc2b49a4ba47dc7701299c6c97b8888d230d57118ec50517bdc581505d46894d665a9cb4e5e18e255e1a82d76948351be8c56f955d5d85cbde2b452735c321c044c4472d4fb841400740a82fe92b9327fe2c5800e20364702f68ae8d40d02ed498bb7dc082ad6fac03631b686a2a9b0f3152aee2b80b7bac6eaebf71572a82ded15d5ea2f0b8a866fdca5815ab5c4106a32829eee67958cdc21e8846246acd7c6d7425d6eddc60f28fb8db80c4dc127da20e3f7017ff006752bdc006c8d553369ab8a7092ef49ee1148ee1ca12b1ff00788290148f8ed10932a954fa222025228f4c2aeebdce5cbdb42f418c9721eb12cc429b2aae316204b8c08d4b689a89b072278c3037662b97a5f091559554c16039420b301950eb1022722f113188edb69c12ee3143b439ad603c660a33d471143973a5be6ba1771c58314aeed3e1d313142baa117a63685228894a1703dd8fa0e94515d3bcaa6311d6f10519421a0894f7868d8032db01929f113a0ca5e994e8fa81e8bc540976a3b29f728f280a0afdc72fe9842f7f705d45c6fdc33583624294229c1ed8e6d1b1cbe58b51a27420bb67412105dc03ff00023c92a2d2b7fc245c710968596eaaa5d6f1294e162f6a97e67923430a175b499eaf728797b81ffd23c018529679dc388a7cdbfb63958b15529e673332addfdc4672c2cb55f70a59801fe9811dd83fb8b68085e9c9fc47544c45e42663113d2f0bf53536ee73bff9767dc6bc96bc4a3af02c6bbc56b129958971c896ab0074b798ab738657318b5486684220b139b752c15b1338035ffb10b3434e658a305045141c897d40088720e9ee409b2bea5874d0506ac7fa851d672017d58e6c2a5d0dcc8d90ead415847ddc628e2fdca11a82389656a5d3116fff00b2ea5c565779552afe33d5ae972a066101749d48dc4d578d7d5455762de55b5fb81182aeb10239834dcc8a596183dcc8145ecd92d362798daaa9d65bbbf52fdfd4ec32dde5ba3ea10709da0605740129402d8d4315e262fea3e626f859dd465b52537d698aa0aa9eeb280a2adf1b6215ac400038cae23c0d342dfd441c684ee9a4ee9fc404d151113667f98159e413fc4094685eb180c0bc5944c625d28a22e7bab2b52ce0aba4baeb1f614d1571239b6d11a7fdccbf9159bce5622e1054056b647c456ea82d48f22f5e521740c72c8e7bd3f52c4a2f35c432bb4ad65489a65fa95b62c717167603500e81595eabf5116315c5a01ab29368ddc51d83c44f57d4af57d44f5fd4ef7ea55e5ea54e4f52dd116727a823467c10159bea55fd5106c3ea23d5ea02f21e676ae64d8829b4f5c405805dd32f97a768b2f2744cfdc02d1c2dabae5d7d4dd90b32076b8e141db46e5fd80e5503fdc3c21c82d61320828a5e65cec531d609bb2fea38abf49ff42147f4206f57c846aa83e6a23c08f5a4be498e074d4e5a5bc19a8d7466455388d88002f0a210d02ecd4439118e765b5052142a71b957b80a6a3c6b50ed034ba4fb21a35bdbcb12044b080307580ba863811c40ac5017dd73ea08d1e6d6fa3d250807354422ba7a9638b47d1c621578bbe3b110f1c96850d58e7c4722c85ad86dfb6236cb3b90a0e09a7a799791142866201d840311b19e6a0bd8440e02574a9e025d707a96e87a96f35ea5bc57a8af6f5153a7a89e20d3907cc50a0076833515c4439af52dd0f52fd1ea5ce1ea0d7212e2c5f5c4a0d3c76820c81cdc0916df16405aaec04296ddf0dcaa0b9605d11711c704bec7b9f47b871a3ee5678f701780fb95dcf7037603c312c157bb12ec1f701ae228e49bbc93c5a94380f0d416ada8a2d62322a8546852fc92c0d4b990a1b43980366f02fee663216a565e22aa4cbd228a894948e2083355e23cb0ac0285cacb782edfe58c24dd005417c10383ea56d042ed614655581371c52501cb0cc30b90dd6e5ec2ef4798a99b3b7494efee1b67d2387f63fa9c2bfb49932aff00ced3369ff9e23d44fbbffc9de7a8f0b0be8c75967afa89374fa971a7ee63ff006883571bf10b727da4a74460e9f4ceb2fb20f0ab12e1831d4005027dcb96f7e600a804d7ea749fc468c8bf52ae5111d445e2155b23be222b47b945710a7a4f50708d74cc51576fddcdac6b922d83717a89d87dc587f922f360570b0cecacadb7fb8f6b588c237ea042ada65ee020022b6b31c7dc568116ab958832aaf831100df5ce266d3f99834612a1f148f294ff009d25d7565b8b6e2da483c93f72b71650472d7710443d20545a31d5bfe6559b99c85bce05817fa181e9e8c4bb3d1807f622c3f952ef5ef4582dd5fa8dbfd12ffd1f81b5fee2793d933e7ec95ffd48af0fb2760fb498b23d90b3407921637fb237a4f64aeab83a81fb823667ccb5e3c92ffe3178662ae3d247b9ee58e5fcca3ff89671118c3ed82e5fb67ff459432672e03f2f8b8e049e482aa0614e26313cbd27ff00208818f492b42d514207c051c1b636118acd844061594d8f3670dc35edb383acb195b5caa5dccb4fea16148f12aba76625f6fb81122f8c9f44cf56175b65a69fdcbf3ee15765cd5c48292f57145db7b223e2850f2f4fd465ab477d9828d92b7501d5ea59c7e93eff005068dbea65254e62ac0885fca329f1f71b99ed33d49999e194f58d9d6577636732d82cfa65f6f958bdc8b16b889b286fcc4b958fb85f560a9463adc2d9998591e1883157cb32320f3016c96eb999ad8aed37d23adfea5086cb2c75c4067304097116b3c53c32e38ec2a503d13a7795b8bab9baed0db8df68275f41398c1da352ff0049bc11ee404cd3132f0fdb0c75fb8812dd22f62e644ba5a95100a70763d923260b02644ed0c82d591627786e2764d030ce4b12f6ce8414d42f78f1326111d00fd406f34fdca58a5e082a94f41305d51e51060f96442ecab76454781ee226c47c11fa9ebe31d3f70098eff15e25789ae08f8255ebf894f3fc449ae08f8220f4f528ed10721f72cb6e61511d90092efb1898260fb84bd87b0d4a0b403a6e3b64f529d5f5f059e3bc70cd668ba3b9c9087438a110d2a3af310361b3ab3ba6182e073aa5ba7b3d21d88473c7dcaf15ea0432fec2274fe308c4d7588349e6fd4c9b893b5f50eb1bf694be20a00ef59666b054030f329836262c5cf7886247d4dbcd834c0070abcd4b5b5b2e5c4658c0704bdebd228ffc1069d7a47ce3dea112dc5a79ff0071773775b452c547ab73b6be496f0cb1c3f598f627927fcc92fcbf72fba5f74b7abdc574678bee5af0c3c7ee37d3f715eaf72de8fb8bd9f733d1f733d1f71ee447a7ee67a7ee67b4f52838a7d41245dd10215978b8e0a1e4a8e54555d48126d188eaf4ca75650d2c711d6805b88507e171e132443daa855772aea104348b487a1d5ae26d301438bfee03a0252d4a7886037706c7ee092c2bb133871c9138a3e886c00286ce257cb60cb59788a242c8c0cdce0e03d4b2ec652a4aff0092d89051f2dcfb33a36e62a444b0eeee5145dafe20391358dc45515e9751028adf2c4b82f72a9acca235d582555b17a4df0fb952bb3293315e9107832b7c3ee5575f713a87c904703f73b992c78943aa81daa5c57e2b112b988c6e36fc17337b60bd582f5625397f887016feee28f64168957c4a2c0a7794707c331d93e2e3eb764a15348731541cb17856f1c4a8b0d7423b594f897b84d1b37c703e26306ed526fbbbf3731e12998358f352b94bf29120c9f705690c270fb47061fd460d6036df7cc64000b22ac4453ceced0d6dc5595a7b688a1a572513ae264a6bdcb2e943ea30c3f48f0daf75122b298710dcdd9110529c42a0c77a96bb7eee3436fb962f596b98b6dcc70acf72e2f5984b792e5f69e065f9f731d235aa9f52877510355ea21c8659c27dca70b113958df765bd214be7ee2873162f58bd083e3dc1f305e217cc58d98ef898b407dc6eed200b5be097358fa25de93bd8c3515521dd372c02bc335f61444f3057762a13c3463e88285762403dc60ca10ad13ee2d212da0511c68fb8d1c6fa59026a7d900ffe91c113c65981819729c06aead8586950e28eb1188ee0dc6eebb6a5ab92e583b7d4a9feb2b3a66e47b810a2cb8834010b3b4379c16f24adca05252dd569a65edc62f1988273ee35a837286a1e50f50e425c5af212dea4f04cc1f24b1ccb7ab2fbca757d432dd7d45beaccf78aef2fba0f7f85de99744c3d110bd8c4a8f9629d503b8c06f70e825a1dd8c399630397d407301dfdfc17392fee0203a5b9876cf77a195e815a6089100556f50808daaa1b2013bc1781e220b50e929b73b93999e3333e81dca8918b7dca4e5e1bed88b4d6ea5214aa3e883caf1dd95dff007052b5f5116cb2ceefdc17731e65515570974559034f3cb6fb3bca815c269c01c0c20abb5352f03cc32ae4cf589bac282a4e7ac572bee775f705d6794fb4b514b961c87d4b3b3f51f0991a9438867a86a098be23132ae267c7ee2a14af9b713a6a50e1f729d258cb3a117a15e22bd65a7fb22af4f51fa83da08405ec21dd732ec203a9194e7d22f63f707812c782086c3d425516baea105094550d1120e682b31a6226869f2ccf4259045aff0053c87886e01e72779add7a20e7406e14a822aeed5fa9499b78813627922b0856ee14d11ab8fda559df7a60035755d6385b48ab584b7198240102456325de21b52338807dc393a94160fbe900c869482dafe6278f711d4fb65078f7353d7a8ffd886bfd4cf68f88274fdcc76f711e832d340fb85de8875751ad513158635da3825f78f98f96592ceac6b8f68e7989983e7f64a1c3db0e4fda1cabea02ff00e90e83e98b1547e896ecbcc09a2f230be9fee1770be18abc1f0cbf93ec4b4ee3882135f512ae5fa94bc3ea5879bf10ae7451db94737e99b287db2dbac3f6c6cda134c8337c1ee06c0906dac7860071ee4001b7ea517505ec2e2464b55921df536c5a5ee015aa0d48f17989591d2da1d333b4a060ee5e58c16d8daead843bb332fbb10f2c3caca5335ee67bfbf80f32fb3ea2d75949674824c7152c712912b606a59d1f52fa3f514e92cefea2f54b8c3d7d44ae098e621c54d748fd4bf8cf12d3882992a1d88b9772c6a5bbfa9df04694fb98791e598825ef98bed6778132b7b591673e0da70d1da02e1fb33483b85c41bb3cc7329d10cb6b6a0503ba627af111d250f15de5ae14f0afb85360f0c3a03e599d1c3ba42addd9b83e2472cb1695a4cc5001b2965c4a828d6463b591f11c39979d5c1c7496ea7a8aa972929ca9ea5f7b2fa5879c5befd25da07bbdc5096ae8b16f8bfb8bdbf731d7f52b77981dbf513cfd12969620dff102dfea0758f5131c4afc417a8773ea2879954c0cc0967aa15e254cecf128ac23bc0e1457485f43504bc9e2e08ab221ed89a67d9cc714278a60b617861d787b44140e97d65c3b377552831125706401bb3e20b70a9e25575f537d2fd4dadfa08254ee2ea550425b7085d0c1860aa94ac422a82cabb588bc1ee29203c3111b0f7c910ea80e4fa826bf7886b2d78883d596568cb3a2e0df111e8f32ce8fa987865b895d4310068df07130d63ea6d95f72f95bf72e8d35d98db87dcaf47dcbe73ee2898b650705fdc57fe638351cb06b52e5c18352fa196823b85b965f41b97d6e01d58d1b01e608ad1c74806c14c4d86112f07d445c899328cdf092ac042b2f1531267aa6666809712d7585f13045a75950ab5f7841846a84726928bb4afb80b63f5302e3a24a0345ef52e6c94c247d245acb07923c72f68b9e620edafa88eefa86b907926951fa8dd8bfa656e5f994e84ecc5b979403a496f9969588a7a106dd6037ac469cfdd459cbea21da9f535a5ea5a7265f5a450ea962ee347578851db171862c5ccac5fc843cc11c38ef1e85ea04c75266b0ca4e580e2efd42e4581cc715e91e765f10585beae21c297b9014e8975b58a5abb132d83bb999869f2e7f515c007548c68e1626bb4c445b78267b72712df302edc7dc09a1a889ca0b3d7a82edafa81b2508c668dea5ea1f706d882ac03c92e2e4fa982949cd9889bb81dd728eac30f1f72fb9ea61e914f48ae17e98879bf32929b7727707d44bbfea5954352ab167a974a6bd44389b3827724aae913c4c405c91c46b3a8d5fc1a9c7e1c7c13997135b9afcc24b397c15325c5848f5635541d88905279807484a21f4cfe69ba06607480e87c016e213722d18c46e71ab72b768a9b4dc65bd6731d7c73f347c1a631310779969a5896e626a711d138fc58fc7ffd9', 9);


--
-- TOC entry 2322 (class 0 OID 0)
-- Dependencies: 175
-- Name: archivoproyecto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('archivoproyecto_id_seq', 9, true);


--
-- TOC entry 2323 (class 0 OID 0)
-- Dependencies: 177
-- Name: avance_proyecto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('avance_proyecto_id_seq', 4, true);


--
-- TOC entry 2240 (class 0 OID 26553)
-- Dependencies: 176
-- Data for Name: avanceproyecto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO avanceproyecto (id, avance) VALUES (1, 'Esbozo Conceptual');
INSERT INTO avanceproyecto (id, avance) VALUES (2, 'Formulado');
INSERT INTO avanceproyecto (id, avance) VALUES (3, 'Validado por el Sector/Comitente');
INSERT INTO avanceproyecto (id, avance) VALUES (4, 'Parcialmente Ejecutado');


--
-- TOC entry 2242 (class 0 OID 26558)
-- Dependencies: 178
-- Data for Name: beneficiario; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO beneficiario (id, beneficiario) VALUES (1, 'Comitente');
INSERT INTO beneficiario (id, beneficiario) VALUES (2, 'Universidad');
INSERT INTO beneficiario (id, beneficiario) VALUES (3, 'Comitente/Universidad');


--
-- TOC entry 2324 (class 0 OID 0)
-- Dependencies: 179
-- Name: beneficiario_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('beneficiario_id_seq', 3, true);


--
-- TOC entry 2244 (class 0 OID 26563)
-- Dependencies: 180
-- Data for Name: comitente; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (5, 'yyyyy', 'yyyy', 'yyy', 'yyy', 1, NULL, NULL, NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (6, 'JAVA', '1234567', '12343545', 'hcmaza@hotmail.com', 2, NULL, NULL, NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (7, 'kkk', 'kkk', 'kkk', 'kkk', 1, NULL, NULL, NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (8, 'UNIV SL', '67564534', '1234567', 'ertyu@gmail.com', 2, NULL, NULL, NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (1, 'NO', '-', '-', '-', NULL, '-', '-', NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (3, 'uuu', 'uuu', 'uuu', 'uu', 2, 'hugo', 'Sistemas', NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (4, '444', '444', '444', '444', 1, 'chincha', 'Redes', NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (9, 'olivares', '33444', '45678', 'yañez@glhl.com', 1, 'Yañez', 'Gte Planta', NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (2, 'Universidad Nacional de Chilecito', '2012829811', '03825-426291', 'info@undec.edu.ar', 2, 'Dario Wolberg', 'Vinculacion tecnologica', NULL);
INSERT INTO comitente (id, razonsocial, cuit, telefono, email, tipocomitenteid, contacto, cargocontacto, sectorid) VALUES (10, 'Dario Wolber', '20202020', NULL, 'darwol@gmail.com', 2, 'Dario Wolber', 'Viculacion tecnologica', NULL);


--
-- TOC entry 2325 (class 0 OID 0)
-- Dependencies: 181
-- Name: comitente_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('comitente_id_seq', 10, true);


--
-- TOC entry 2246 (class 0 OID 26571)
-- Dependencies: 182
-- Data for Name: configuracion; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO configuracion (id, clave, valor) VALUES (1, 'Empresa', 'Universidad Nacional de Chilecito');


--
-- TOC entry 2326 (class 0 OID 0)
-- Dependencies: 183
-- Name: configuracion_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('configuracion_id_seq', 1, true);


--
-- TOC entry 2248 (class 0 OID 26579)
-- Dependencies: 184
-- Data for Name: convocatoria; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO convocatoria (id, convocatoria, descripcion, bases, link, formulario, tipoproyectoid, fechapublicacion, fechainicio, fechacierre, tipofinanciamientoid, estado, organismo, beneficiario, montofinanciamiento) VALUES (1, 'Convocatoria1', 'Convocatoria1', NULL, 'http://www.undec.edu.ar/ejemploformulario1.docx', '\x504b03041400080808001d747a440000000000000000000000000b0000005f72656c732f2e72656c73ad924d4b03410c86effd1543eedd6c2b88c8cef622426f22f5078499eceed0ce073369adffde410aba508aa0c7bc79f3f01cd26dcefea04e9c8b8b41c3aa69417130d1ba306a78db3d2f1f60d32fba573e90d44a995c2aaade84a26112498f88c54ceca9343171a89b21664f52c73c6222b3a79171ddb6f7987f32a09f31d5d66ac85bbb02b5fb48fc37367a16b2248426665ea65cafb3382e154e7964d160a379a971f96a34950c785d68fd7ba1380ccef0533447cf41ae79f1593858b6b79528a55b4677ff69346f7ccbbcc76cd15ee28bcda2c3d91bf49f504b0708e8d00123d90000003d020000504b03041400080808001d747a440000000000000000000000001c000000776f72642f5f72656c732f646f63756d656e742e786d6c2e72656c73ad914d0ac2301085f79e22ccdea6551091a66e44702bf500319db6c13609c9287a7b038a5a28e2c2e5fc7def312f5f5ffb8e5dd0076d8d802c4981a151b6d2a6117028b7d325ac8b49bec74e525c09ad7681c51b1304b4446ec579502df63224d6a18993dafa5e522c7dc39d5427d9209fa5e982fb4f06140326db5502fcaeca80953787bfb06d5d6b851babce3d1a1a91e0816e1d864894be4112f0a893c8013e2e3ffba77c6d0d95f2d8e1dbc1abf5cdc4fcaf3f40a298e5e7179e9da78549ce07e11677504b0708f92f30c0c500000013020000504b03041400080808001d747a4400000000000000000000000011000000776f72642f73657474696e67732e786d6c458e4b0ec2300c44f79c22f21e1258f0a948bbe302c001426ba0526247b1a1c0e9092b96a337337afbee95a279629191c9c372e1c020f53c8c74f3703e1de65b30a281861099d0c31b05ba76b69f1a41d5da12531f489ac9c35d3537d64a7fc71464c119a9b22b9714b4c672b313972117ee51a44e53b42be7d6368591a0ad971fe664a62663e991b4ea3807f60706bc8647d453b81c9573ad3c43f4b071bb1fb67f97f60b504b070876d58eada5000000d0000000504b03041400080808001d747a4400000000000000000000000011000000776f72642f646f63756d656e742e786d6cb553c16edb300cbdef2b0cdd53d9eb1074469d5e82de5a0468f6018a4ddb022451a0e8b8d9d74faaedb8c58021975d4c108fef3d92a21f9fdeadc9ce4041a3ab4471978b0c5c8d8d765d257e1d9f370f220bac5ca30c3aa8c4058278da7d7b1ccb06ebc182e32c2ab8506225067265a87bb02a6cacae0903b6bca9d196d8b6ba863988994195e8997d29e54cba430f2e622d92551c53eae444d9cf5ef27b9e6f2581511cfb0dbdf661513bffcbff6ccd5237dee23a22359eb08610e222ac997cadd2ee2a53e4370c9c74ae0c7f8b73436afc64f9b591fd048a5d5cff099b4b8afee373a08ff0c61703d9589e95a94448492e64422815c835ce04fa0b1b4bde3dc77e06a348a3a7014eaa48284f3513fb3f187f150f50f3dccec55f851dbcf341753049fbeeed7744fa78b6db87fb78a7637adca2f8996f97821745595a1533da04ddff48552d2243bcbe3c25ddc06bd2836aae8981965712e9aeff9432fa3999ad5e077b9c5a6d6dac6ba0d65699094d0f7c20e4658e5699300fc171a4bda6386e3ce80537743c2558ae8b90cb8bcbf5cfdbfd01504b07081a2047eb94010000be030000504b03041400080808001d747a440000000000000000000000000f000000776f72642f7374796c65732e786d6ca554cd6ee23010beef5344bed300daa5346aa810daaa955ab62aed618f4332106b1ddbf2384de11df6a9f6c5d63149052da8815ee2783233fe7e26bebc7acd45f08286b89231eb9d75598032512997cb983d3f5d77862c200b3205a124c66c85c4ae46df2ecb88ec4a2005ae5e5254c62cb356476148498639d099d228ddb785323958b735cbb05426d5462548e4dae722ec77bb8330072ed9a86918d4eb6d1a33ffe20095915d6977b606034b033af3e912f22afb0544cca6d52982853e8eafb689d71d7c5c3f986a2979aaca8992d628d1a42d40106eb2a8d0da3880e3c2aa9b95ce505293654de193c2b75ea65eae5dbb2a0d28e13c664f3c77c24cb10c1e550eb2229050ccee412e1d48b743203b260e319bf17c56f88c6c2c694fad079528a14c03031cb21aebba89f5bf379109bd8f0977ac8bcd79eada67bc733bdd85b0ce3a131ff255489df163cdd2d30b6b430e3bd4fbd1c6a29f328139ae21adc1cf8130fd25f739f5c1c1de60c7c23f887aea7236943524dc33848545e366b8ef47668e6ef4b092a1db8828ad2b2a40ccde4a76ddffd4d8b1e11b030fd979cf13a3482d6cf01b6e906ff9ba297d6fdb708f6dc323d51fb4517f52a0d12a4831a834f8ba059fa97eaae647f03e6fc3fb8ebbcbeb30db86d547bae75b740fcfc5d61c1c67dab00df8078e271835dc35aabecfeeb8c46991cfdd4ddfeab7a9b65ffe6d76e52923be794ea8f5f57584a4176d24fdf757a63c3945d68b16b29e3e28cd1b8dfe03504b0708e59497702402000092070000504b03041400080808001d747a4400000000000000000000000012000000776f72642f666f6e745461626c652e786d6cad8fb16ec3300c44f77e85a03d919ba1288cd8418022638736fd0046a66301926888aa55ff7d15db99ea2143378977bc77dc1f7e9c15030636e42bf9bc2da440afa931fe5ac9aff369f32a0547f00d58f258c911591eeaa77d2a5bf291455ef75c864a7631f6a552ac3b74c05bead167ada5e020e66fb82a6a5ba3f18df4b7431fd5ae285e54400b31a3b9333dcb252d3d929628347d208dccb9abb3739e03e365bdb413a9f4e072e9b371c8e21d93f82007b3417710186f9e016c258b42aa690f9cb1e37d1a26fb24f426eaee3e1f2018b858bc496a86fd817e8eee427695b5fb6fd6315bd651ab677132cc8fa29607d7bf504b0708b26d050df20000002c020000504b03041400080808001d747a4400000000000000000000000010000000646f6350726f70732f6170702e786d6c9dcf310b83301005e0bdbf42b26b6c875224ea523a77b0dd43726ac0dc497215fdf74d29d4bde3e3c1c77baa5dfd942d10a223acc5b128450668c83a1c6af1e86ef9456491355a3d11422d3688a26d0eea1e6886c00e6296048cb51899e74aca6846f03a16a9c6d4f414bce614c320a9ef9d812b99970764792acbb38495012dd87cfe81e22b560bff8b5a329f7df1d96d73f21ad511eba9731e9a52c93d28b9df68de504b0708e9c3998fa30000000b010000504b03041400080808001d747a4400000000000000000000000011000000646f6350726f70732f636f72652e786d6c6d915d4bc3301486effd1525f76d9256868436bb5076a5205851bc0bc9b10b366948e2bafd7bd3bad5397679789ff3703eeaf5def4d90e7cd0836d102d08cac0ca4169db35e8b5dde477280b515825fac142830e10d09adfd4d231397878f683031f35842c896c60d235681ba3631807b905234291089bc2cfc11b1153e93bec84fc121de09290153610851251e04998bbc5888e4a2517a5fbf6fd2c5012430f066c0c981614ffb146c78383ab1da7f08c8ee04db80acfc942ee835ea8711c8bb19ab9343fc5ef4f8f2ff3aab9b6d3a924205e1fd54c7a1011549604ec77b053f256dd3fb41bc44b426f7352e5e5aaa594552523f4a3c617fd7c3ab8879d9efec4498dcfcbb9faff0dfe03504b0708d6843d270b010000d9010000504b03041400080808001d747a44000000000000000000000000130000005b436f6e74656e745f54797065735d2e786d6cbd94314fc3301085f7fe8ac82b4a1c181042493a203142873023635f128bc4b67ca6b4ff9e73682304a811b4b058b2eedefbde9d2c17cbcdd0276bf0a8ad29d97996b3048cb44a9bb6640ff56d7ac596d5a2a8b70e30a15e8325eb4270d79ca3ec6010985907862a8df5830874f52d77423e8b16f8459e5f72694d0013d2103d5855dc13ce6b05c94af870270628197ff4d023cfe2c9929b774164964c38d76b2902e5e36ba33ed1d21d292ac71eecb4c3336a60fc7bd2abf56a875356be0c04caa8fddfd00821d07231427fc6b34da3254ca1a39bf3560222f9d1047be7d9081fe73e6d84a932086de65711b63dfcc52246df597c43c85a3cf570fa0493f5a110245f79eb9013ece808b021a50295520e073ee8c30b98d8d2fa5fccbf7ffa51fd95b828f8f85f546f504b07087844c9e4290100005e040000504b010214001400080808001d747a44e8d00123d90000003d0200000b00000000000000000000000000000000005f72656c732f2e72656c73504b010214001400080808001d747a44f92f30c0c5000000130200001c0000000000000000000000000012010000776f72642f5f72656c732f646f63756d656e742e786d6c2e72656c73504b010214001400080808001d747a4476d58eada5000000d0000000110000000000000000000000000021020000776f72642f73657474696e67732e786d6c504b010214001400080808001d747a441a2047eb94010000be030000110000000000000000000000000005030000776f72642f646f63756d656e742e786d6c504b010214001400080808001d747a44e594977024020000920700000f00000000000000000000000000d8040000776f72642f7374796c65732e786d6c504b010214001400080808001d747a44b26d050df20000002c020000120000000000000000000000000039070000776f72642f666f6e745461626c652e786d6c504b010214001400080808001d747a44e9c3998fa30000000b01000010000000000000000000000000006b080000646f6350726f70732f6170702e786d6c504b010214001400080808001d747a44d6843d270b010000d901000011000000000000000000000000004c090000646f6350726f70732f636f72652e786d6c504b010214001400080808001d747a447844c9e4290100005e0400001300000000000000000000000000960a00005b436f6e74656e745f54797065735d2e786d6c504b050600000000090009003c020000000c00000000', 1, NULL, NULL, NULL, 1, 'A', NULL, NULL, NULL);
INSERT INTO convocatoria (id, convocatoria, descripcion, bases, link, formulario, tipoproyectoid, fechapublicacion, fechainicio, fechacierre, tipofinanciamientoid, estado, organismo, beneficiario, montofinanciamiento) VALUES (3, 'Convocatoria 2', NULL, NULL, 'http://www.undec.edu.ar/formularioejemplo2.docx', '\x504b030414000808080029747a440000000000000000000000000b0000005f72656c732f2e72656c73ad924d4b03410c86effd1543eedd6c2b88c8cef622426f22f5078499eceed0ce073369adffde410aba508aa0c7bc79f3f01cd26dcefea04e9c8b8b41c3aa69417130d1ba306a78db3d2f1f60d32fba573e90d44a995c2aaade84a26112498f88c54ceca9343171a89b21664f52c73c6222b3a79171ddb6f7987f32a09f31d5d66ac85bbb02b5fb48fc37367a16b2248426665ea65cafb3382e154e7964d160a379a971f96a34950c785d68fd7ba1380ccef0533447cf41ae79f1593858b6b79528a55b4677ff69346f7ccbbcc76cd15ee28bcda2c3d91bf49f504b0708e8d00123d90000003d020000504b030414000808080029747a440000000000000000000000001c000000776f72642f5f72656c732f646f63756d656e742e786d6c2e72656c73ad914d0ac2301085f79e22ccdea6551091a66e44702bf500319db6c13609c9287a7b038a5a28e2c2e5fc7def312f5f5ffb8e5dd0076d8d802c4981a151b6d2a6117028b7d325ac8b49bec74e525c09ad7681c51b1304b4446ec579502df63224d6a18993dafa5e522c7dc39d5427d9209fa5e982fb4f06140326db5502fcaeca80953787bfb06d5d6b851babce3d1a1a91e0816e1d864894be4112f0a893c8013e2e3ffba77c6d0d95f2d8e1dbc1abf5cdc4fcaf3f40a298e5e7179e9da78549ce07e11677504b0708f92f30c0c500000013020000504b030414000808080029747a4400000000000000000000000011000000776f72642f73657474696e67732e786d6c458e4b0ec2300c44f79c22f21e1258f0a948bbe302c001426ba0526247b1a1c0e9092b96a337337afbee95a279629191c9c372e1c020f53c8c74f3703e1de65b30a281861099d0c31b05ba76b69f1a41d5da12531f489ac9c35d3537d64a7fc71464c119a9b22b9714b4c672b313972117ee51a44e53b42be7d6368591a0ad971fe664a62663e991b4ea3807f60706bc8647d453b81c9573ad3c43f4b071bb1fb67f97f60b504b070876d58eada5000000d0000000504b030414000808080029747a4400000000000000000000000011000000776f72642f646f63756d656e742e786d6cb553416ee3300cbcf71586eea99c7611b4469d5e82de7611a0d90728366d0b904481a2e3665fbf526dc72d0a14b9f4628218ce0c49d14fcf6fd66427a0a0d195627d9b8b0c5c85b5766d29fe1e5e560f220bac5cad0c3a28c5198278dede3c0d458d556fc17116155c28b0143db922541d581556565784011b5e55680b6c1a5dc114c4c4a05274ccbe907222dda20717b106c92a8e29b572a4ec262f7997e71b496014c77e43a77d98d54edff99fac99eb866b5c07a4da135610425c8435a3af55da5d64d6f91503279d0bc35fe35c931a3e587e6e643782621bd77fc4fa9ca27fffece93dbcf2d940361427654a1152920b99104a0572891381be6043c1db97d84f6f1469f4d4c351dd2594c79a91fd03c69fc503543cb573f61761076fbc572d8cd2be7dfd17912e9eede6e13edee9901e77bd7ecc3773c16f45595a1533da04ddff4a550d2243bcbe3c256dcf4bd281aa2f8981861712e9b6fb9032fa2999acfef4f630b6dad8585743a5ad32239a1e784fc8f31c8d32611a82e3483b4d71dc78d0336ee8704cb05c1621e71797cb9fb7fd0f504b07081fc91e9594010000be030000504b030414000808080029747a440000000000000000000000000f000000776f72642f7374796c65732e786d6ca554cd6ee23010beef5344bed300daa5346aa810daaa955ab62aed618f4332106b1ddbf2384de11df6a9f6c5d63149052da8815ee2783233fe7e26bebc7acd45f08286b89231eb9d75598032512997cb983d3f5d77862c200b3205a124c66c85c4ae46df2ecb88ec4a2005ae5e5254c62cb356476148498639d099d228ddb785323958b735cbb05426d5462548e4dae722ec77bb8330072ed9a86918d4eb6d1a33ffe20095915d6977b606034b033af3e912f22afb0544cca6d52982853e8eafb689d71d7c5c3f986a2979aaca8992d628d1a42d40106eb2a8d0da3880e3c2aa9b95ce505293654de193c2b75ea65eae5dbb2a0d28e13c664f3c77c24cb10c1e550eb2229050ccee412e1d48b743203b260e319bf17c56f88c6c2c694fad079528a14c03031cb21aebba89f5bf379109bd8f0977ac8bcd79eada67bc733bdd85b0ce3a131ff255489df163cdd2d30b6b430e3bd4fbd1c6a29f328139ae21adc1cf8130fd25f739f5c1c1de60c7c23f887aea7236943524dc33848545e366b8ef47668e6ef4b092a1db8828ad2b2a40ccde4a76ddffd4d8b1e11b030fd979cf13a3482d6cf01b6e906ff9ba297d6fdb708f6dc323d51fb4517f52a0d12a4831a834f8ba059fa97eaae647f03e6fc3fb8ebbcbeb30db86d547bae75b740fcfc5d61c1c67dab00df8078e271835dc35aabecfeeb8c46991cfdd4ddfeab7a9b65ffe6d76e52923be794ea8f5f57584a4176d24fdf757a63c3945d68b16b29e3e28cd1b8dfe03504b0708e59497702402000092070000504b030414000808080029747a4400000000000000000000000012000000776f72642f666f6e745461626c652e786d6cad8fb16ec3300c44f77e85a03d919ba1288cd8418022638736fd0046a66301926888aa55ff7d15db99ea2143378977bc77dc1f7e9c15030636e42bf9bc2da440afa931fe5ac9aff369f32a0547f00d58f258c911591eeaa77d2a5bf291455ef75c864a7631f6a552ac3b74c05bead167ada5e020e66fb82a6a5ba3f18df4b7431fd5ae285e54400b31a3b9333dcb252d3d929628347d208dccb9abb3739e03e365bdb413a9f4e072e9b371c8e21d93f82007b3417710186f9e016c258b42aa690f9cb1e37d1a26fb24f426eaee3e1f2018b858bc496a86fd817e8eee427695b5fb6fd6315bd651ab677132cc8fa29607d7bf504b0708b26d050df20000002c020000504b030414000808080029747a4400000000000000000000000010000000646f6350726f70732f6170702e786d6c9d90c14ec3301044ef7c4564716dec9a90a695930a09714282438063e5acd7ad51625bb629e9df6340a23d731c3de9cdec8aed3c8dc5114334ceb664593252a005a78cddb7e4a57f5834a488495a254767b125278c64db5d89e7e03c86643016d960634b0e29f90da5110e38c958666c33d12e4c32e518f6d4696d00ef1d7c4c6813e58cd514e78456a15af83f21f9356e8ee9bf52e5e07b5f7ced4f3efb3ad1bb24c7de4cd83141cf41dc793f1a90291fdf3d9a21e0d38f8d56e5b2ac4a7efd66ac729f7137377571c17779ec3b42a24cb2aa6200c0385f49b56ef85ae1adaeea016f56a0871a15e81517f4b248d0f3f3ba2f504b070835f3f2d6f300000081010000504b030414000808080029747a4400000000000000000000000011000000646f6350726f70732f636f72652e786d6c6d915d4bc3301486effd1525f76d9256868436bb5076a5205851bc0bc9b10b366948e2bafd7bd3bad5397679789ff3703eeaf5def4d90e7cd0836d102d08cac0ca4169db35e8b5dde477280b515825fac142830e10d09adfd4d231397878f683031f35842c896c60d235681ba3631807b905234291089bc2cfc11b1153e93bec84fc121de09290153610851251e04998bbc5888e4a2517a5fbf6fd2c5012430f066c0c981614ffb146c78383ab1da7f08c8ee04db80acfc942ee835ea8711c8bb19ab9343fc5ef4f8f2ff3aab9b6d3a924205e1fd54c7a1011549604ec77b053f256dd3fb41bc44b426f7352e5e5aaa594552523f4a3c617fd7c3ab8879d9efec4498dcfcbb9faff0dfe03504b0708d6843d270b010000d9010000504b030414000808080029747a44000000000000000000000000130000005b436f6e74656e745f54797065735d2e786d6cbd94314fc3301085f7fe8ac82b4a1c181042493a203142873023635f128bc4b67ca6b4ff9e73682304a811b4b058b2eedefbde9d2c17cbcdd0276bf0a8ad29d97996b3048cb44a9bb6640ff56d7ac596d5a2a8b70e30a15e8325eb4270d79ca3ec6010985907862a8df5830874f52d77423e8b16f8459e5f72694d0013d2103d5855dc13ce6b05c94af870270628197ff4d023cfe2c9929b774164964c38d76b2902e5e36ba33ed1d21d292ac71eecb4c3336a60fc7bd2abf56a875356be0c04caa8fddfd00821d07231427fc6b34da3254ca1a39bf3560222f9d1047be7d9081fe73e6d84a932086de65711b63dfcc52246df597c43c85a3cf570fa0493f5a110245f79eb9013ece808b021a50295520e073ee8c30b98d8d2fa5fccbf7ffa51fd95b828f8f85f546f504b07087844c9e4290100005e040000504b0102140014000808080029747a44e8d00123d90000003d0200000b00000000000000000000000000000000005f72656c732f2e72656c73504b0102140014000808080029747a44f92f30c0c5000000130200001c0000000000000000000000000012010000776f72642f5f72656c732f646f63756d656e742e786d6c2e72656c73504b0102140014000808080029747a4476d58eada5000000d0000000110000000000000000000000000021020000776f72642f73657474696e67732e786d6c504b0102140014000808080029747a441fc91e9594010000be030000110000000000000000000000000005030000776f72642f646f63756d656e742e786d6c504b0102140014000808080029747a44e594977024020000920700000f00000000000000000000000000d8040000776f72642f7374796c65732e786d6c504b0102140014000808080029747a44b26d050df20000002c020000120000000000000000000000000039070000776f72642f666f6e745461626c652e786d6c504b0102140014000808080029747a4435f3f2d6f30000008101000010000000000000000000000000006b080000646f6350726f70732f6170702e786d6c504b0102140014000808080029747a44d6843d270b010000d901000011000000000000000000000000009c090000646f6350726f70732f636f72652e786d6c504b0102140014000808080029747a447844c9e4290100005e0400001300000000000000000000000000e60a00005b436f6e74656e745f54797065735d2e786d6c504b050600000000090009003c020000500c00000000', 2, '2014-03-29 20:04:03.986-03', '2014-03-29 20:04:03.986-03', '2014-03-29 20:04:03.986-03', 2, 'C', NULL, NULL, NULL);


--
-- TOC entry 2327 (class 0 OID 0)
-- Dependencies: 185
-- Name: convocatoria_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('convocatoria_id_seq', 3, true);


--
-- TOC entry 2250 (class 0 OID 26587)
-- Dependencies: 186
-- Data for Name: dependencia; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO dependencia (id, dependencia) VALUES (1, 'Departamento Aagronomia');
INSERT INTO dependencia (id, dependencia) VALUES (2, 'Departamento Ciencias Biologicas');


--
-- TOC entry 2328 (class 0 OID 0)
-- Dependencies: 187
-- Name: dependencia_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('dependencia_id_seq', 2, true);


--
-- TOC entry 2252 (class 0 OID 26595)
-- Dependencies: 188
-- Data for Name: estadoproyecto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (6, 'Proyecto en Evaluacion', NULL, 'PE ', true);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (5, 'Proyecto PreAProvado', NULL, 'PP ', true);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (8, 'Proyecto Aprobado', NULL, 'PA ', true);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (9, 'Proyecto Rechazado', NULL, 'PR ', true);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (10, 'Proyecto Cancelado', NULL, 'PC ', true);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (1, 'Alta Idea Proyecto', NULL, 'S  ', false);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (2, 'Idea Proyecto Aprobada', NULL, 'SA ', false);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (3, 'Idea Proyecto Rechazada', NULL, 'SR ', false);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (7, 'Idea Proyecto pendiente de Correccion', NULL, 'SPC', false);
INSERT INTO estadoproyecto (id, estado, descripcion, estadoabreviado, proyecto) VALUES (4, 'Idea Proyecto Cancelada', NULL, 'SE ', false);


--
-- TOC entry 2329 (class 0 OID 0)
-- Dependencies: 189
-- Name: estadoproyecto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('estadoproyecto_id_seq', 11, true);


--
-- TOC entry 2254 (class 0 OID 26603)
-- Dependencies: 190
-- Data for Name: etapa; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2330 (class 0 OID 0)
-- Dependencies: 191
-- Name: etapa_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('etapa_id_seq', 1, false);


--
-- TOC entry 2256 (class 0 OID 26611)
-- Dependencies: 192
-- Data for Name: evaluacion; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2331 (class 0 OID 0)
-- Dependencies: 193
-- Name: evaluacion_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('evaluacion_id_seq', 9, true);


--
-- TOC entry 2258 (class 0 OID 26619)
-- Dependencies: 194
-- Data for Name: evaluacion_pregunta; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2259 (class 0 OID 26625)
-- Dependencies: 195
-- Data for Name: fuentefinanciamiento; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO fuentefinanciamiento (id, fuentefinanciamiento) VALUES (1, 'Concurso / Convocatoria');
INSERT INTO fuentefinanciamiento (id, fuentefinanciamiento) VALUES (2, 'Ventanilla Permanente');
INSERT INTO fuentefinanciamiento (id, fuentefinanciamiento) VALUES (3, 'Aporte Comitente');
INSERT INTO fuentefinanciamiento (id, fuentefinanciamiento) VALUES (4, 'Aporte Universidad');


--
-- TOC entry 2332 (class 0 OID 0)
-- Dependencies: 196
-- Name: fuentefinanciamiento_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('fuentefinanciamiento_id_seq', 4, true);


--
-- TOC entry 2333 (class 0 OID 0)
-- Dependencies: 197
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 2262 (class 0 OID 26635)
-- Dependencies: 198
-- Data for Name: perfil; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO perfil (rolid, rol) VALUES (1, 'Administrador');
INSERT INTO perfil (rolid, rol) VALUES (2, 'Docente');
INSERT INTO perfil (rolid, rol) VALUES (3, 'Empleado');


--
-- TOC entry 2334 (class 0 OID 0)
-- Dependencies: 199
-- Name: perfil_rolid_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('perfil_rolid_seq', 3, true);


--
-- TOC entry 2264 (class 0 OID 26640)
-- Dependencies: 200
-- Data for Name: pregunta; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO pregunta (id, pregunta, proyecto) VALUES (1, '¿La idea proyecto resulta pertinente a nivel institucional ?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (2, 'El diagnostico describe de forma clara el problema a resolver ?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (3, 'La decripcion de la idea-proyecto resulta clara y precisa?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (4, 'Los objetivos de la idea-proyecto responden al dianostico presentado?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (5, 'La idea proyecto dentro bases y conidciones del instrumento de financiamiento?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (6, 'El prespuesto resulta acorde a los objertivos planteados y encuadra dentro de las bases y conidciones del instrumento de financiamiento?', false);
INSERT INTO pregunta (id, pregunta, proyecto) VALUES (7, 'Los resultados son alcanzables en funcion de los objetivos planteados?', false);


--
-- TOC entry 2335 (class 0 OID 0)
-- Dependencies: 201
-- Name: pregunta_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('pregunta_id_seq', 7, true);


--
-- TOC entry 2266 (class 0 OID 26648)
-- Dependencies: 202
-- Data for Name: presupuesto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (67, 87, '2014-04-25 11:30:59.961', 'P');
INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (68, 88, '2014-05-04 18:48:55.824', 'P');
INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (69, 90, '2014-05-20 13:58:36', 'P');
INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (70, 93, '2014-05-29 18:50:03.22', 'P');
INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (71, 96, '2014-05-29 19:04:02.165', 'P');
INSERT INTO presupuesto (id, proyectoid, fecha, estado) VALUES (72, 97, '2014-06-10 16:57:51.602', 'P');


--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 203
-- Name: presupuesto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('presupuesto_id_seq', 72, true);


--
-- TOC entry 2268 (class 0 OID 26653)
-- Dependencies: 204
-- Data for Name: presupuesto_rubro; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 1, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 2, 66.00, 0.00, NULL, 0.00, 66.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 3, 77.00, 0.00, NULL, 0.00, 77.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 4, 200.00, 888.00, NULL, 0.00, 1088.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 5, 0.00, 99.00, NULL, 0.00, 99.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (67, 10, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 1, 100.00, 0.00, NULL, 0.00, 100.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 2, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 3, 99.00, 99.00, NULL, 99.00, 297.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 4, 88.00, 88.00, NULL, 88.00, 264.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 5, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (68, 10, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 1, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 2, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 3, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 4, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 5, 10000.00, 30000.00, NULL, 0.00, 40000.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (69, 10, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 1, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 2, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 3, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 4, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 5, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (70, 10, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 1, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 2, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 3, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 4, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 5, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (71, 10, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 1, 0.00, 0.00, NULL, 10000.00, 10000.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 2, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 3, 0.00, 0.00, NULL, 40000.00, 40000.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 4, 0.00, 0.00, NULL, 50000.00, 50000.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 5, 0.00, 32000.00, NULL, 0.00, 32000.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 6, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 7, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 8, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 9, 0.00, 0.00, NULL, 0.00, 0.00);
INSERT INTO presupuesto_rubro (presupuestoid, rubroid, gastocomitente, gastouniversidad, estado, gastoorganismo, total) VALUES (72, 10, 0.00, 0.00, NULL, 0.00, 0.00);


--
-- TOC entry 2269 (class 0 OID 26656)
-- Dependencies: 205
-- Data for Name: presupuesto_rubroitem; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 206
-- Name: presupuesto_rubroitem_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('presupuesto_rubroitem_id_seq', 1, false);


--
-- TOC entry 2271 (class 0 OID 26664)
-- Dependencies: 207
-- Data for Name: proyecto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (8, 1, 'oo', 'ooo', NULL, 'ooo', 5, 25, 1, NULL, '2014-05-04 18:48:55.413-03', 88, 1, 1, 'oo', 1, NULL, 2, 2);
INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (10, NULL, 'Desarrollo de Ssitema de Gestión y Administración de Proyectos', 'Desarrollo de un Sistema de Gestión y Administración de Proyectos de La Universidad Nacionalde Chilecito', NULL, NULL, 7, 25, 2, NULL, '2014-05-20 13:58:36-03', 90, 2, 5, 'Chilecito - La Rioja', 4, NULL, 1, 2);
INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (2, NULL, 's', 's', NULL, 's', 5, 25, 1, NULL, '2014-05-29 19:04:02.142-03', 96, 2, 5, 's', 3, NULL, 2, 1);
INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (NULL, 1, 'Desarrollo  nuev ESPECIE VID', 'SDNVNSOIVNSOEVNSE', NULL, 'IFJIWSOPFIJSWJFFJ', 18, 25, 4, NULL, '2014-06-10 16:57:50.833-03', 97, 2, 1, 'CHILECITO L', 1, NULL, 1, 1);
INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (8, 1, 'k', 'k', NULL, 'k', 1, 25, 1, NULL, '2014-05-29 18:50:01.311-03', 93, 3, 1, 'k', 3, NULL, 4, 7);
INSERT INTO proyecto (comitenteid, convocatoriaid, nombre, resumen, documentacion, observaciones, duracion, agenteid, tipoproyectoid, cudap, fecha, id, beneficiarioid, tipofinanciamientoid, localizacion, fuentefinanciamientoid, documentacionnombre, avanceproyectoid, estadoproyectoid) VALUES (8, 1, 'Proyecto 1', 'DRD', NULL, 'ORE', 5, 25, 1, NULL, '2014-04-25 11:30:57.632-03', 87, 1, 1, 'Chilecito', 1, NULL, 1, 2);


--
-- TOC entry 2272 (class 0 OID 26670)
-- Dependencies: 208
-- Data for Name: proyecto_agente; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 209
-- Name: proyecto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('proyecto_id_seq', 97, true);


--
-- TOC entry 2274 (class 0 OID 26675)
-- Dependencies: 210
-- Data for Name: rubro; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO rubro (id, rubro) VALUES (1, 'Equipos e Instrumentos');
INSERT INTO rubro (id, rubro) VALUES (2, 'Infraestructura');
INSERT INTO rubro (id, rubro) VALUES (3, 'Materiales e Insumos');
INSERT INTO rubro (id, rubro) VALUES (4, 'Consultoría o Servicios de Terceros');
INSERT INTO rubro (id, rubro) VALUES (5, 'Recursos Humanos');
INSERT INTO rubro (id, rubro) VALUES (6, 'Otros');
INSERT INTO rubro (id, rubro) VALUES (7, 'Otros 1');
INSERT INTO rubro (id, rubro) VALUES (8, 'Otros 2');
INSERT INTO rubro (id, rubro) VALUES (9, 'Otros 3');
INSERT INTO rubro (id, rubro) VALUES (10, 'Otros 4');


--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 211
-- Name: rubro_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('rubro_id_seq', 10, true);


--
-- TOC entry 2276 (class 0 OID 26680)
-- Dependencies: 212
-- Data for Name: sector; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 213
-- Name: sector_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('sector_id_seq', 1, false);


--
-- TOC entry 2278 (class 0 OID 26685)
-- Dependencies: 214
-- Data for Name: tarea; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2279 (class 0 OID 26691)
-- Dependencies: 215
-- Data for Name: tarea_agente; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2280 (class 0 OID 26694)
-- Dependencies: 216
-- Data for Name: tareaprogreso; Type: TABLE DATA; Schema: ap; Owner: postgres
--



--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 217
-- Name: tareaprogreso_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('tareaprogreso_id_seq', 1, false);


--
-- TOC entry 2282 (class 0 OID 26699)
-- Dependencies: 218
-- Data for Name: tipocomitente; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO tipocomitente (tipocomitente, id) VALUES ('Empresa Privada', 1);
INSERT INTO tipocomitente (tipocomitente, id) VALUES ('Organismo Estatal', 2);


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 219
-- Name: tipocomitente_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('tipocomitente_id_seq', 2, true);


--
-- TOC entry 2284 (class 0 OID 26707)
-- Dependencies: 220
-- Data for Name: tipodocumento; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO tipodocumento (tipodocumentoid, tipodocumento) VALUES (1, 'DNI');
INSERT INTO tipodocumento (tipodocumentoid, tipodocumento) VALUES (2, 'Pasaporte');
INSERT INTO tipodocumento (tipodocumentoid, tipodocumento) VALUES (3, 'CI');
INSERT INTO tipodocumento (tipodocumentoid, tipodocumento) VALUES (4, 'NN');


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 221
-- Name: tipodocumento_tipodocumentoid_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('tipodocumento_tipodocumentoid_seq', 4, true);


--
-- TOC entry 2286 (class 0 OID 26715)
-- Dependencies: 222
-- Data for Name: tipofinanciamiento; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO tipofinanciamiento (id, tipofinanciamiento) VALUES (1, 'Instrumento de Subsidio por Convocatoria');
INSERT INTO tipofinanciamiento (id, tipofinanciamiento) VALUES (2, 'Instrumento de Credito');
INSERT INTO tipofinanciamiento (id, tipofinanciamiento) VALUES (3, 'Especies Contraparte');
INSERT INTO tipofinanciamiento (id, tipofinanciamiento) VALUES (5, 'Otro');
INSERT INTO tipofinanciamiento (id, tipofinanciamiento) VALUES (4, 'Efectivo por Contrato comitente');


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 223
-- Name: tipofinanciamiento_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('tipofinanciamiento_id_seq', 5, true);


--
-- TOC entry 2288 (class 0 OID 26720)
-- Dependencies: 224
-- Data for Name: tipoproyecto; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO tipoproyecto (id, tipo) VALUES (1, 'Investigación y Desarrollo Experimental');
INSERT INTO tipoproyecto (id, tipo) VALUES (2, 'Desarrollo Tecnológico');
INSERT INTO tipoproyecto (id, tipo) VALUES (3, 'Asistencia Técnica');
INSERT INTO tipoproyecto (id, tipo) VALUES (4, 'Servicios Tecnológicos');
INSERT INTO tipoproyecto (id, tipo) VALUES (5, 'Capacitación');
INSERT INTO tipoproyecto (id, tipo) VALUES (6, 'Administración de Fondos UVT');


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 225
-- Name: tipoproyecto_id_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('tipoproyecto_id_seq', 2, true);


--
-- TOC entry 2290 (class 0 OID 26725)
-- Dependencies: 226
-- Data for Name: usuario; Type: TABLE DATA; Schema: ap; Owner: postgres
--

INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (59, 'mleguiza', '17756315ebd47b7110359fc7b168179bf6f2df3646fcc888bc8aa05c78b38ac1', 'A', 2, '2014-03-10 16:33:36.317', NULL, NULL);
INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (77, 'java', '6336a5bd6cff3cbf7397f0f526c11bd6bb17e20a21669794d68270c192126198', 'A', 2, '2014-03-20 10:02:53.817', NULL, NULL);
INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (81, 'dwolberg', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'A', 1, '2014-03-20 00:00:00', NULL, NULL);
INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (57, 'enano', '6336a5bd6cff3cbf7397f0f526c11bd6bb17e20a21669794d68270c192126198', 'A', 2, '2014-03-09 22:05:04.111', NULL, NULL);
INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (114, 'usuario', '9250e222c4c71f0c58d4c54b50a880a312e9f9fed55d5c3aa0b0e860ded99165', 'P', 2, '2014-04-25 10:30:59.035', NULL, NULL);
INSERT INTO usuario (usuarioid, usuarionombre, usuarioclave, usuarioestado, usuariorol, usuariofechaalta, usuariofechabaja, habilitacion) VALUES (1, 'hcmaza', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'P', 1, '2012-12-12 09:00:00', NULL, 's');


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 227
-- Name: usuario_usuarioid_seq; Type: SEQUENCE SET; Schema: ap; Owner: postgres
--

SELECT pg_catalog.setval('usuario_usuarioid_seq', 114, true);


--
-- TOC entry 2038 (class 2606 OID 26764)
-- Name: archivoproyecto_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY archivoproyecto
    ADD CONSTRAINT archivoproyecto_pkey PRIMARY KEY (id);


--
-- TOC entry 2040 (class 2606 OID 26766)
-- Name: avance_proyecto_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY avanceproyecto
    ADD CONSTRAINT avance_proyecto_pkey PRIMARY KEY (id);


--
-- TOC entry 2042 (class 2606 OID 26768)
-- Name: beneficiario_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT beneficiario_pkey PRIMARY KEY (id);


--
-- TOC entry 2046 (class 2606 OID 26770)
-- Name: configuracion_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configuracion
    ADD CONSTRAINT configuracion_pkey PRIMARY KEY (id);


--
-- TOC entry 2048 (class 2606 OID 26772)
-- Name: convocatoriaid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY convocatoria
    ADD CONSTRAINT convocatoriaid PRIMARY KEY (id);


--
-- TOC entry 2050 (class 2606 OID 26774)
-- Name: dependenciaid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dependencia
    ADD CONSTRAINT dependenciaid PRIMARY KEY (id);


--
-- TOC entry 2044 (class 2606 OID 26776)
-- Name: entidadbenefiaciariaid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY comitente
    ADD CONSTRAINT entidadbenefiaciariaid PRIMARY KEY (id);


--
-- TOC entry 2052 (class 2606 OID 26778)
-- Name: estadoproyecto_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estadoproyecto
    ADD CONSTRAINT estadoproyecto_pkey PRIMARY KEY (id);


--
-- TOC entry 2054 (class 2606 OID 26780)
-- Name: etapa_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY etapa
    ADD CONSTRAINT etapa_pkey PRIMARY KEY (id);


--
-- TOC entry 2056 (class 2606 OID 26782)
-- Name: evaluacion_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY evaluacion
    ADD CONSTRAINT evaluacion_pkey PRIMARY KEY (id);


--
-- TOC entry 2058 (class 2606 OID 26784)
-- Name: evaluacion_pregunta_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY evaluacion_pregunta
    ADD CONSTRAINT evaluacion_pregunta_pkey PRIMARY KEY (evaluacionid, preguntaid);


--
-- TOC entry 2060 (class 2606 OID 26786)
-- Name: fuentefinanciamiento_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fuentefinanciamiento
    ADD CONSTRAINT fuentefinanciamiento_pkey PRIMARY KEY (id);


--
-- TOC entry 2036 (class 2606 OID 26788)
-- Name: id; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY agente
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 2064 (class 2606 OID 26790)
-- Name: preguntaevaluacion_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pregunta
    ADD CONSTRAINT preguntaevaluacion_pkey PRIMARY KEY (id);


--
-- TOC entry 2068 (class 2606 OID 26792)
-- Name: presu_rubro; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY presupuesto_rubro
    ADD CONSTRAINT presu_rubro PRIMARY KEY (presupuestoid, rubroid);


--
-- TOC entry 2070 (class 2606 OID 26794)
-- Name: presupuesto_rubroitem_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY presupuesto_rubroitem
    ADD CONSTRAINT presupuesto_rubroitem_pkey PRIMARY KEY (id);


--
-- TOC entry 2066 (class 2606 OID 26796)
-- Name: presupuestoid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY presupuesto
    ADD CONSTRAINT presupuestoid PRIMARY KEY (id);


--
-- TOC entry 2074 (class 2606 OID 26798)
-- Name: proyecto_agente_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proyecto_agente
    ADD CONSTRAINT proyecto_agente_pkey PRIMARY KEY (agenteid, proyectoid);


--
-- TOC entry 2072 (class 2606 OID 26800)
-- Name: proyectoid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT proyectoid PRIMARY KEY (id);


--
-- TOC entry 2062 (class 2606 OID 26802)
-- Name: rolid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT rolid PRIMARY KEY (rolid);


--
-- TOC entry 2076 (class 2606 OID 26804)
-- Name: rubroid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rubro
    ADD CONSTRAINT rubroid PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 26806)
-- Name: sector_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sector
    ADD CONSTRAINT sector_pkey PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 26808)
-- Name: tarea_agente_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tarea_agente
    ADD CONSTRAINT tarea_agente_pkey PRIMARY KEY (tareaid, agenteid);


--
-- TOC entry 2080 (class 2606 OID 26810)
-- Name: tarea_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (id);


--
-- TOC entry 2084 (class 2606 OID 26812)
-- Name: tareaprogreso_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tareaprogreso
    ADD CONSTRAINT tareaprogreso_pkey PRIMARY KEY (id);


--
-- TOC entry 2088 (class 2606 OID 26814)
-- Name: tipodocumentoid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipodocumento
    ADD CONSTRAINT tipodocumentoid PRIMARY KEY (tipodocumentoid);


--
-- TOC entry 2086 (class 2606 OID 26816)
-- Name: tipoentidadid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipocomitente
    ADD CONSTRAINT tipoentidadid PRIMARY KEY (id);


--
-- TOC entry 2090 (class 2606 OID 26818)
-- Name: tipofinanciamiento_pkey; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipofinanciamiento
    ADD CONSTRAINT tipofinanciamiento_pkey PRIMARY KEY (id);


--
-- TOC entry 2092 (class 2606 OID 26820)
-- Name: tipoproyectoid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipoproyecto
    ADD CONSTRAINT tipoproyectoid PRIMARY KEY (id);


--
-- TOC entry 2094 (class 2606 OID 26822)
-- Name: username; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT username UNIQUE (usuarionombre);


--
-- TOC entry 2096 (class 2606 OID 26824)
-- Name: usuarioid; Type: CONSTRAINT; Schema: ap; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuarioid PRIMARY KEY (usuarioid);


--
-- TOC entry 2115 (class 2606 OID 26825)
-- Name: agenteid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT agenteid FOREIGN KEY (agenteid) REFERENCES agente(id);


--
-- TOC entry 2125 (class 2606 OID 26830)
-- Name: agenteid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tarea_agente
    ADD CONSTRAINT agenteid FOREIGN KEY (agenteid) REFERENCES agente(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2116 (class 2606 OID 26835)
-- Name: avanceproyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT avanceproyectoid FOREIGN KEY (avanceproyectoid) REFERENCES avanceproyecto(id);


--
-- TOC entry 2117 (class 2606 OID 26840)
-- Name: beneficiarioid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT beneficiarioid FOREIGN KEY (beneficiarioid) REFERENCES beneficiario(id);


--
-- TOC entry 2118 (class 2606 OID 26845)
-- Name: comitenteid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT comitenteid FOREIGN KEY (comitenteid) REFERENCES comitente(id);


--
-- TOC entry 2119 (class 2606 OID 26850)
-- Name: convocatoriaid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT convocatoriaid FOREIGN KEY (convocatoriaid) REFERENCES convocatoria(id);


--
-- TOC entry 2097 (class 2606 OID 26855)
-- Name: dependenciaid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY agente
    ADD CONSTRAINT dependenciaid FOREIGN KEY (dependenciaid) REFERENCES dependencia(id);


--
-- TOC entry 2120 (class 2606 OID 26860)
-- Name: estadoproyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT estadoproyectoid FOREIGN KEY (estadoproyectoid) REFERENCES estadoproyecto(id);


--
-- TOC entry 2108 (class 2606 OID 26865)
-- Name: evaluacionid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY evaluacion_pregunta
    ADD CONSTRAINT evaluacionid FOREIGN KEY (evaluacionid) REFERENCES evaluacion(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2121 (class 2606 OID 26870)
-- Name: fuentefinanciamientoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT fuentefinanciamientoid FOREIGN KEY (fuentefinanciamientoid) REFERENCES fuentefinanciamiento(id);


--
-- TOC entry 2109 (class 2606 OID 26875)
-- Name: preguntaid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY evaluacion_pregunta
    ADD CONSTRAINT preguntaid FOREIGN KEY (preguntaid) REFERENCES pregunta(id);


--
-- TOC entry 2111 (class 2606 OID 26880)
-- Name: presupuestoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto_rubro
    ADD CONSTRAINT presupuestoid FOREIGN KEY (presupuestoid) REFERENCES presupuesto(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2113 (class 2606 OID 26885)
-- Name: presupuestoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto_rubroitem
    ADD CONSTRAINT presupuestoid FOREIGN KEY (presupuestoid) REFERENCES presupuesto(id);


--
-- TOC entry 2100 (class 2606 OID 26890)
-- Name: proyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY archivoproyecto
    ADD CONSTRAINT proyectoid FOREIGN KEY (proyectoid) REFERENCES proyecto(id);


--
-- TOC entry 2106 (class 2606 OID 26895)
-- Name: proyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY evaluacion
    ADD CONSTRAINT proyectoid FOREIGN KEY (proyectoid) REFERENCES proyecto(id);


--
-- TOC entry 2105 (class 2606 OID 26900)
-- Name: proyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY etapa
    ADD CONSTRAINT proyectoid FOREIGN KEY (proyectoid) REFERENCES proyecto(id);


--
-- TOC entry 2110 (class 2606 OID 26905)
-- Name: proyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto
    ADD CONSTRAINT proyectoid FOREIGN KEY (proyectoid) REFERENCES proyecto(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2124 (class 2606 OID 26910)
-- Name: proyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tarea
    ADD CONSTRAINT proyectoid FOREIGN KEY (proyectoid) REFERENCES proyecto(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2112 (class 2606 OID 26915)
-- Name: rubroid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto_rubro
    ADD CONSTRAINT rubroid FOREIGN KEY (rubroid) REFERENCES rubro(id);


--
-- TOC entry 2114 (class 2606 OID 26920)
-- Name: rubroid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY presupuesto_rubroitem
    ADD CONSTRAINT rubroid FOREIGN KEY (rubroid) REFERENCES rubro(id);


--
-- TOC entry 2101 (class 2606 OID 26925)
-- Name: sectorid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY comitente
    ADD CONSTRAINT sectorid FOREIGN KEY (sectorid) REFERENCES sector(id);


--
-- TOC entry 2127 (class 2606 OID 26930)
-- Name: tareaid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tareaprogreso
    ADD CONSTRAINT tareaid FOREIGN KEY (tareaid) REFERENCES tarea(id);


--
-- TOC entry 2126 (class 2606 OID 26935)
-- Name: tareaid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY tarea_agente
    ADD CONSTRAINT tareaid FOREIGN KEY (tareaid) REFERENCES tarea(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2102 (class 2606 OID 26940)
-- Name: tipocomitenteid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY comitente
    ADD CONSTRAINT tipocomitenteid FOREIGN KEY (tipocomitenteid) REFERENCES tipocomitente(id);


--
-- TOC entry 2098 (class 2606 OID 26945)
-- Name: tipodocumentoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY agente
    ADD CONSTRAINT tipodocumentoid FOREIGN KEY (tipodocumentoid) REFERENCES tipodocumento(tipodocumentoid);


--
-- TOC entry 2103 (class 2606 OID 26950)
-- Name: tipofinanciamientoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY convocatoria
    ADD CONSTRAINT tipofinanciamientoid FOREIGN KEY (tipofinanciamientoid) REFERENCES tipofinanciamiento(id);


--
-- TOC entry 2122 (class 2606 OID 26955)
-- Name: tipofinanciamientoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT tipofinanciamientoid FOREIGN KEY (tipofinanciamientoid) REFERENCES tipofinanciamiento(id);


--
-- TOC entry 2104 (class 2606 OID 26960)
-- Name: tipoproyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY convocatoria
    ADD CONSTRAINT tipoproyectoid FOREIGN KEY (tipoproyectoid) REFERENCES tipoproyecto(id);


--
-- TOC entry 2123 (class 2606 OID 26965)
-- Name: tipoproyectoid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY proyecto
    ADD CONSTRAINT tipoproyectoid FOREIGN KEY (tipoproyectoid) REFERENCES tipoproyecto(id);


--
-- TOC entry 2107 (class 2606 OID 26970)
-- Name: usuarioid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY evaluacion
    ADD CONSTRAINT usuarioid FOREIGN KEY (usuarioid) REFERENCES usuario(usuarioid);


--
-- TOC entry 2099 (class 2606 OID 26975)
-- Name: usuarioid; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY agente
    ADD CONSTRAINT usuarioid FOREIGN KEY (usuarioid) REFERENCES usuario(usuarioid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2128 (class 2606 OID 26980)
-- Name: usuariorol; Type: FK CONSTRAINT; Schema: ap; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuariorol FOREIGN KEY (usuariorol) REFERENCES perfil(rolid);


-- Completed on 2014-06-23 18:41:40

--
-- PostgreSQL database dump complete
--

