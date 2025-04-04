insert into postgres.routify_db.scope_tb (scope_id, path, scopes)
values ('DEFAULT_USER_ROUTES', '/api/v1/routes/.*', 'READ, CHANGE, SAVE, DELETE');

drop table if exists postgres.routify_db.route_tb;
create table postgres.routify_db.route_tb (
    route_id uuid primary key,
    user_id int4 not null,
    status text not null,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES postgres.routify_db.user_tb(user_id) ON DELETE CASCADE
);