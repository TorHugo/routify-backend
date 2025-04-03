drop table if exists postgres.routify_db.email_template_tb;

create table postgres.routify_db.email_template_tb(
    template_key TEXT not null,
    subject TEXT not null,
    body TEXT not null,
    is_html BOOLEAN not null,
    version TEXT not null,
    parameters TEXT not null,
    active BOOLEAN not null,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    constraint pk_email_template primary key (template_key, version, active)
);

insert into postgres.routify_db.scope_tb (scope_id, path, scopes)
values ('ADMIN_TEMPLATE_SCOPE', '/api/v1/templates/.*', 'READ, CHANGE, SAVE, DELETE');