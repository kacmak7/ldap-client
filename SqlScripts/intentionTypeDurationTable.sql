-- new public.intention_type_duration table --

-- Table: public.intention_type_duration

-- DROP TABLE public.intention_type_duration;

CREATE TABLE public.intention_type_duration
(
    id bigint NOT NULL,
    duration integer NOT NULL,
    CONSTRAINT intention_type_duration_id PRIMARY KEY (id),
    CONSTRAINT duration_unique UNIQUE (duration)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.intention_type_duration
    OWNER to postgres;
	
-- add column to intentions table --
ALTER TABLE public.intentions ADD COLUMN duration_id integer;

-- add constraint to intentions table --
ALTER TABLE public.intentions ADD
	CONSTRAINT duration_id_fk FOREIGN KEY (duration_id)
        REFERENCES public.intention_type_duration (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
		
-- insert value to intention_type_duration table --
INSERT INTO public.intention_type_duration(id, duration) VALUES (1, 30);
INSERT INTO public.intention_type_duration(id, duration) VALUES (2, 60);

-- update intentions table --
UPDATE public.intentions SET duration_id = 1 WHERE duration_id IS null;