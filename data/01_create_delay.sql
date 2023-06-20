-- Table: public.delay

-- DROP TABLE IF EXISTS public.delay;

CREATE TABLE IF NOT EXISTS public.delay
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    value integer NOT NULL,
    CONSTRAINT delay_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.delay
    OWNER to postgres;