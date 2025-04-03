create schema routify_db;


drop table if exists postgres.routify_db.user_scope_tb;
drop table if exists postgres.routify_db.scope_tb;
drop table if exists postgres.routify_db.user_attributes_tb;
drop table if exists postgres.routify_db.notification_tb;
drop table if exists postgres.routify_db.token_tb;
drop table if exists postgres.routify_db.user_tb;

create table postgres.routify_db.user_tb (
    user_id serial primary key,
    external_id uuid not null unique,
    email text not null,
    password text not null,
    created_at timestamp not null,
    updated_at timestamp
);
create index idx_user_tb_in_email on postgres.routify_db.user_tb (email);
create index idx_user_tb_in_external_id on postgres.routify_db.user_tb (external_id);

create table postgres.routify_db.user_attributes_tb (
    user_id int4 primary key,
    first_name text not null,
    last_name text,
    phone_number text not null,
    active boolean not null,
    confirmed boolean not null,
    created_at timestamp not null,
    updated_at timestamp,

    constraint fk_user foreign key (user_id) references postgres.routify_db.user_tb(user_id) on delete cascade
);

CREATE TABLE postgres.routify_db.notification_tb (
    notification_id SERIAL PRIMARY KEY,
    user_id INT4 NOT NULL,
    type TEXT NOT NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES postgres.routify_db.user_tb(user_id) ON DELETE CASCADE
);

CREATE TABLE postgres.routify_db.token_tb (
    token_id SERIAL PRIMARY KEY,
    user_id INT4 NOT NULL,
    token_hash TEXT NOT NULL,
    token_type TEXT NOT NULL,
    expiration TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES postgres.routify_db.user_tb(user_id) ON DELETE CASCADE
);

create table postgres.routify_db.scope_tb (
    scope_id text primary key,
    path text not null,
    scopes text not null
);

insert into postgres.routify_db.scope_tb (scope_id, path, scopes)
values ('DEFAULT_USER_SCOPE', '/api/v1/users/.*', 'READ, CHANGE, SAVE, DELETE');

create table postgres.routify_db.user_scope_tb (
    user_id int4 not null,
    scope_id text not null,
    created_at timestamp not null,
    updated_at timestamp,

    constraint pk_user_scope primary key (user_id, scope_id),
    constraint fk_user_scope_user foreign key (user_id) references postgres.routify_db.user_tb(user_id) on delete cascade,
    constraint fk_user_scope_scope foreign key (scope_id) references postgres.routify_db.scope_tb(scope_id) on delete cascade
);