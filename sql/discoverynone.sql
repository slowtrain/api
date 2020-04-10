CREATE OR REPLACE VIEW episode.discoverynone
AS SELECT disci.id,
    disci.disci_id,
    disci.ci_id,
    disci.isdelete,
    disci.datasrc,
    disci.disigtype,
    disci.datasrckey1,
    disci.datasrckey2,
    disci.datasrcpkey1,
    disci.datasrcpkey2,
    disci.datasrcver,
    disci.discitype,
    disci.disciclass,
    disci.disciname,
    disci.discidescription,
    disci.discihostname,
    disci.disciipaddress,
    disci.disciserialnumber,
    disci.disciappport,
    disci.discovery_dt,
    disci.isupdate,
    disci.isvirtual,
    disci.discivirtype,
    disci.discilocation,
    disci.extaln1,
    disci.extaln2,
    disci.extaln3,
    disci.extaln4,
    disci.extaln5,
    disci.extaln6,
    disci.extaln7,
    disci.extaln8,
    disci.extaln9,
    disci.extaln10,
    disci.extaln11,
    disci.extaln12,
    disci.extaln13,
    disci.extaln14,
    disci.extaln15,
    disci.extaln16,
    disci.extaln17,
    disci.extaln18,
    disci.extaln19,
    disci.extaln20,
    disci.extnum1,
    disci.extnum2,
    disci.extnum3,
    disci.extnum4,
    disci.extnum5,
    disci.extdate1,
    disci.extdate2,
    disci.extdate3,
    disci.extdate4,
    disci.extdate5,
    disci.extbool1,
    disci.extbool2,
    disci.extbool3,
    disci.extbool4,
    disci.extbool5,
    disci.create_dt,
    disci.create_id,
    disci.update_dt,
    disci.update_id
   FROM disci
  WHERE (disci.ci_id::text IN ( SELECT ci.ci_id
           FROM ci
          WHERE (EXISTS ( SELECT 1
                   FROM classancestor
                  WHERE (classancestor.ancestor::text = ANY (ARRAY['SERVER'::character varying::text, 'SW_DBSW'::character varying::text, 'SWMIDDLESW'::character varying::text])) AND classancestor.classstructure_id::text = ci.classstructure_id::text)) AND (EXISTS ( SELECT 1
                   FROM disci disci_1
                  WHERE disci_1.isdelete = false AND disci_1.ci_id::text = ci.ci_id::text AND (disci_1.datasrc::text = ANY (ARRAY['BFI'::character varying::text, 'TADDM'::character varying::text])) AND disci_1.discovery_dt < (timezone('utc'::text, now()) - '2 days'::interval day)))));

-- Permissions

ALTER TABLE episode.discoverynone OWNER TO episode;
GRANT ALL ON TABLE episode.discoverynone TO episode;
