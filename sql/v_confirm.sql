CREATE OR REPLACE VIEW episode.v_confirm
AS SELECT ci.ci_id,
    cl.classstructure_id,
    cl.description AS classname,
    ci.ciname,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln1::text) AS "加己1",
    ci.extaln1,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln2::text) AS "加己2",
    ci.extaln2,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln3::text) AS "加己3",
    ci.extaln3,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln4::text) AS "加己4",
    ci.extaln4,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln5::text) AS "加己5",
    ci.extaln5,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln6::text) AS "加己6",
    ci.extaln6,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln7::text) AS "加己7",
    ci.extaln7,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln8::text) AS "加己8",
    ci.extaln8,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln9::text) AS "加己9",
    ci.extaln9,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln10::text) AS "加己10",
    ci.extaln10,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln11::text) AS "加己11",
    ci.extaln11,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln12::text) AS "加己12",
    ci.extaln12,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln13::text) AS "加己13",
    ci.extaln13,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln14::text) AS "加己14",
    ci.extaln14,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extaln15::text) AS "加己15",
    ci.extaln15,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extnum1::text) AS "箭磊1",
    ci.extnum1,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extnum2::text) AS "箭磊2",
    ci.extnum2,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extnum3::text) AS "箭磊3",
    ci.extnum3,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extnum4::text) AS "箭磊4",
    ci.extnum4,
    ( SELECT applabel.description
           FROM applabel
          WHERE applabel.app_id::text = 'CI'::text AND applabel.lang::text = 'ko-kr'::text AND applabel.name::text = cl.extnum5::text) AS "箭磊5",
    ci.extnum5
   FROM ci
     JOIN classstructure cl ON ci.classstructure_id::text = cl.classstructure_id::text
  WHERE ci.extbool3 = false AND (EXISTS ( SELECT 1
           FROM classancestor
          WHERE (classancestor.ancestor::text = ANY (ARRAY['SOFTWARE'::character varying, 'HARDWARE'::character varying]::text[])) AND classancestor.classstructure_id::text = ci.classstructure_id::text));

-- Permissions

ALTER TABLE episode.v_confirm OWNER TO episode;
GRANT ALL ON TABLE episode.v_confirm TO episode;
