-- Table: public.customer

-- DROP TABLE IF EXISTS public.customer;

CREATE TABLE IF NOT EXISTS public.customer
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    chat_id integer NOT NULL,
    user_id integer NOT NULL,
    last_msg character varying COLLATE pg_catalog."default" NOT NULL,
    index integer NOT NULL,
    delay_id integer NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT fk_delay FOREIGN KEY (delay_id)
        REFERENCES public.delay (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customer
    OWNER to postgres;