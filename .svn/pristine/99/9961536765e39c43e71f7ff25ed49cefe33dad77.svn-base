--  new intentions_confessions table --

-- Table: public.intentions_confessions

-- DROP TABLE public.intentions_confessions;

CREATE TABLE public.intentions_confessions
(
    intention_id bigint NOT NULL,
    celebrant_id bigint NOT NULL,
    CONSTRAINT intentions_confession_pkey PRIMARY KEY (intention_id, celebrant_id),
    CONSTRAINT celebrant_id_fk FOREIGN KEY (celebrant_id)
        REFERENCES public.celebrants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT intention_id_fk FOREIGN KEY (intention_id)
        REFERENCES public.intentions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.intentions_confessions
    OWNER to postgres;
	
-- data migration --
INSERT INTO public.intentions_confessions(intention_id, celebrant_id)
SELECT id, confess_id FROM public.intentions WHERE confess_id IS NOT null;

-- drop column --
ALTER TABLE public.intentions DROP COLUMN confess_id