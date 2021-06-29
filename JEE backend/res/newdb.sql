/*create DATABASE tt_app;
-- create tables*/
create table users (
    id                             int not null auto_increment primary key,
    username                       varchar(255) not null ,
    `password`                       varchar(255) not null,
    user_type                      varchar(25) not null,
    UNIQUE(username)
)
;
create table teacher (
    id                             int not null auto_increment primary key,
    `name`                         varchar(255 ) not null,
    `user_id`                      int,
    free_time                      varchar(4000 ) not null,
    color                          varchar(20 ) not null,
    FOREIGN KEY (`user_id`)
    REFERENCES users(id)
    ON DELETE SET NULL
)
;

create table faculty (
    id                             int not null auto_increment primary key,
    `name`                         varchar(255 ),
    abrev                          varchar(20 ) not null,
    `year`                         int(2) not null,
    CONSTRAINT uc_abrev_year       UNIQUE(abrev,year)
)
;

create table class (
    id                             int not null auto_increment primary key,
    faculty_id                     int not null,
    group_num                      int not null,
    free_time                      varchar(4000 ) not null,
    color                          varchar(20 ) not null,
    FOREIGN KEY (faculty_id)
    REFERENCES faculty(id)
    ON DELETE CASCADE,
    CONSTRAINT uc_fcid_grpnum      UNIQUE(faculty_id,group_num)
)
;

create table subject (
    id                             int not null auto_increment primary key,
    module                         varchar(100),
    sub_module                     varchar(100),
    `type`                         varchar(20) not null,
    abrev                          varchar(20) not null,
    color                          varchar(20) not null,
    CONSTRAINT uc_module_sub      UNIQUE(module,sub_module,type),
    CONSTRAINT uc_abrev_type      UNIQUE(abrev,type)
)
;

create table room (
    id                             int not null auto_increment primary key,
    abrev                          varchar(20 ) not null UNIQUE,
    free_time                      varchar(4000 ) not null,
    color                          varchar(20 ) not null
)
;

create table timetable (
    id                             int not null auto_increment primary key,
    `user_id`                      int not null,
    `description`                  varchar(300 ),
    nb_days                        int not null,
    nb_periods                     int not null,
    hours_per_period               int not null,
    free_time                      varchar(4000 ) not null,
    summary                        varchar(4000 ) DEFAULT '{}',
    FOREIGN KEY (`user_id`)
    REFERENCES users(id)
    ON DELETE CASCADE
)
;

create table lesson (
    id                            int not null auto_increment primary key,
    teacher_id                    int not null ,
    class_id                      int not null ,
    room_id                       int not null ,
    subject_id                    int not null ,
    timetable_id                  int not null ,
    total_lessons                 int not null,
    lesson_occ                    varchar(30 ),
    lesson_link                   varchar(200 ),
    color                         varchar(20 ) not null,
    FOREIGN KEY (teacher_id)      REFERENCES teacher(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id)        REFERENCES class(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id)         REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id)      REFERENCES subject(id) ON DELETE CASCADE,
    FOREIGN KEY (timetable_id)    REFERENCES timetable(id) ON DELETE CASCADE,
    CONSTRAINT uc_lesson_data     UNIQUE(teacher_id,class_id,room_id,subject_id,timetable_id)
)
;

create table cluster (
    id                             int not null auto_increment primary key,
    timetable_id                   int not null references timetable(id)
)
;


create table cluster_lesson (
    id                            int not null auto_increment primary key,
    cluster_id                     int not null references cluster(id),
    lesson_id                   int not null references lesson(id)
)
;