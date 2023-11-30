CREATE TABLE User (
                     User_pk INTEGER   PRIMARY KEY AUTOINCREMENT,
                     firstName   varchar not null,
                     lastName    varchar not null,
                     age         integer not null,
                     email       varchar not null,
                     phoneNumber VARCHAR  not null,
                     password    VARCHAR  not null,
                     bio         VARCHAR  not null,
                     role TEXT NOT NULL CHECK (role IN ('Student', 'Professor'))
);
CREATE TABLE professor (

                           professor_id INTEGER   PRIMARY KEY AUTOINCREMENT,
                           firstName   varchar not null,
                           lastName    varchar not null,
                           age         integer not null,
                           email       varchar not null,
                           phoneNumber VARCHAR  not null,
                           password    VARCHAR  not null,
                           bio         VARCHAR  not null

);
CREATE TABLE student (

                           student_id INTEGER   PRIMARY KEY AUTOINCREMENT,
                           firstName   varchar not null,
                           lastName    varchar not null,
                           age         integer not null,
                           email       varchar not null,
                           phoneNumber VARCHAR  not null,
                           password    VARCHAR  not null,
                           bio         VARCHAR  not null

);
CREATE TABLE Session
(
    sessionId   INTEGER PRIMARY KEY AUTOINCREMENT,
    subject     TEXT NOT NULL CHECK (subject IN ('MATHEMATICS', 'PHYSICS', 'CHEMISTRY', 'BIOLOGY', 'COMPUTER_SCIENCE',
                                                 'LITERATURE', 'HISTORY', 'GEOGRAPHY')),
    rate        REAL NOT NULL,
    sessionDate Date NOT NULL,
    nbPlaces INTEGER NOT NULL,
    tutorId INTEGER NOT NULL
);
CREATE TABLE SessionProfessor (
                                sessionId INTEGER,
                                professorId INTEGER,
                                PRIMARY KEY (sessionId, professorId),
                                FOREIGN KEY (sessionId) REFERENCES Session(sessionId),
                                FOREIGN KEY (professorId) REFERENCES Professor(professor_id)

);
CREATE TABLE SessionStudent (
                                sessionId INTEGER,
                                studentId INTEGER,
                                PRIMARY KEY (sessionId, studentId),
                                FOREIGN KEY (sessionId) REFERENCES Session(sessionId)

);

CREATE TABLE Waitlist (
                          waitlistId INTEGER PRIMARY KEY AUTOINCREMENT,
                          sessionId INTEGER,
                          studentId INTEGER,
                        professorId INTEGER,
                          FOREIGN KEY (sessionId) REFERENCES Session(sessionId),
                            FOREIGN KEY (studentId) REFERENCES Student(student_id)  ,
                            FOREIGN KEY (professorId) REFERENCES Professor(professor_id)
);
