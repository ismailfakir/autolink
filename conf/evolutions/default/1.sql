# --- !Ups

CREATE TABLE "user" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL,"password" VARCHAR NOT NULL);

# --- !Downs

DROP TABLE "user";


