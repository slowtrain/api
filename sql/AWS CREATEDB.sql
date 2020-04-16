create role episode with password 'Itmsg4u1!'  login;
grant rds_superuser to episode;
alter role episode with CREATEDB;

CREATE DATABASE api encoding UTF8;
CREATE DATABASE itam encoding UTF8;


psql \
   -f episode_itam.dump \
   --host episode.cmw2btsqwf2b.ap-northeast-2.rds.amazonaws.com \
   --port 5432 \
   --username episode \
   --password Itmsg4u1! \
   --dbname api