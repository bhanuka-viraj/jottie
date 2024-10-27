CREATE
    DATABASE jottie;

CREATE TABLE User
(
    user_id     INT PRIMARY KEY AUTO_INCREMENT,
    user_name   VARCHAR(50)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(100),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    dob         DATE,
    first_name        VARCHAR(100),
    last_name   VARCHAR(100)
);

CREATE TABLE Relationship
(
    relationship_id INT PRIMARY KEY AUTO_INCREMENT,
    type            VARCHAR(50) NOT NULL
);

CREATE TABLE User_Relationship
(
    user_relationship_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id_1            INT,
    user_id_2            INT,
    relationship_id      INT,
    FOREIGN KEY (user_id_1) REFERENCES User (user_id),
    FOREIGN KEY (user_id_2) REFERENCES User (user_id),
    FOREIGN KEY (relationship_id) REFERENCES Relationship (relationship_id)
);
CREATE TABLE Category
(
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255)
);
CREATE TABLE Jot
(
    jot_id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT,
    title       VARCHAR(255),
    content     TEXT,
    date        DATE,
    visibility  VARCHAR(50),
    category_id INT,
    location_id INT,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (category_id) REFERENCES Category (category_id),
    FOREIGN KEY (location_id) REFERENCES Location (location_id)
);
CREATE TABLE Shared_Jot
(
    shared_jot_id INT PRIMARY KEY AUTO_INCREMENT,
    jot_id        INT,
    user_id_by    INT,
    user_id_with  INT,
    status        VARCHAR(50),
    date          DATE,
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id),
    FOREIGN KEY (user_id_by) REFERENCES User (user_id),
    FOREIGN KEY (user_id_with) REFERENCES User (user_id)
);
CREATE TABLE Location
(
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255)
);
CREATE TABLE Mood
(
    mood_id     INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255)
);
CREATE TABLE Jot_Mood
(
    jot_id  INT,
    mood_id INT,
    PRIMARY KEY (jot_id, mood_id),
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id),
    FOREIGN KEY (mood_id) REFERENCES Mood (mood_id)
);
CREATE TABLE Tag
(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(100)
);
CREATE TABLE Jot_Tag
(
    jot_id INT,
    tag_id INT,
    PRIMARY KEY (jot_id, tag_id),
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id),
    FOREIGN KEY (tag_id) REFERENCES Tag (tag_id)
);
CREATE TABLE Task
(
    task_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id        INT,
    jot_id         INT,
    description    TEXT,
    due_date       DATE,
    isCompleted    BOOLEAN   DEFAULT FALSE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_task_id INT,
    FOREIGN KEY (user_id) REFERENCES User (user_id),
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id),
    FOREIGN KEY (parent_task_id) REFERENCES Task (task_id)
);
CREATE TABLE Reminder
(
    reminder_id        INT PRIMARY KEY AUTO_INCREMENT,
    task_id            INT,
    reminder_date_time TIMESTAMP,
    type               VARCHAR(50),
    isSent             BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (task_id) REFERENCES Task (task_id)
);
CREATE TABLE Notification
(
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    jot_id          INT,
    status          VARCHAR(50),
    message         TEXT,
    date_time       TIMESTAMP,
    type            VARCHAR(50),
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id)
);
