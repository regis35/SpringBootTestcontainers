Create TABLE if not exists Post (
    id int not null,
    user_id int not null,
    title varchar(250) not null,
    content text not null,
    version int,
    primary key(id)
)