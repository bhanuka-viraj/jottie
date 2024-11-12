CREATE DATABASE jottie;
USE jottie;
CREATE TABLE User
(
    user_id    VARCHAR(50) PRIMARY KEY,
    user_name  VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    email      VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    dob        DATE,
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

CREATE TABLE Relationship
(
    relationship_id VARCHAR(50) PRIMARY KEY,
    type            VARCHAR(50) NOT NULL
);

CREATE TABLE User_Relationship
(
    user_relationship_id VARCHAR(50) PRIMARY KEY,
    user_id_1            VARCHAR(50),
    user_id_2            VARCHAR(50),
    relationship_id      VARCHAR(50),
    FOREIGN KEY (user_id_1) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id_2) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (relationship_id) REFERENCES Relationship (relationship_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Category
(
    category_id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE Location
(
    location_id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE Jot
(
    jot_id      VARCHAR(50) PRIMARY KEY,
    user_id     VARCHAR(50),
    title       VARCHAR(255),
    path        VARCHAR(100),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    category_id VARCHAR(50),
    location_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (category_id) REFERENCES Category (category_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (location_id) REFERENCES Location (location_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Shared_Jot
(
    shared_jot_id VARCHAR(50) PRIMARY KEY,
    jot_id        VARCHAR(50),
    user_id_by    VARCHAR(50),
    user_id_with  VARCHAR(50),
    status        VARCHAR(50),
    date          DATE,
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id_by) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id_with) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Mood
(
    mood_id     INT PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE Jot_Mood
(
    jot_id  VARCHAR(50),
    mood_id INT,
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (mood_id) REFERENCES Mood (mood_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Tag
(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(100)
);

CREATE TABLE Jot_Tag
(
    jot_id VARCHAR(50),
    tag_id INT,
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES Tag (tag_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Task
(
    task_id        VARCHAR(50) PRIMARY KEY,
    user_id        VARCHAR(50),
    jot_id         VARCHAR(50),
    description    TEXT,
    due_date       DATE,
    isCompleted    BOOLEAN   DEFAULT FALSE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_task_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES User (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (parent_task_id) REFERENCES Task (task_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reminder
(
    reminder_id        VARCHAR(50) PRIMARY KEY,
    task_id            VARCHAR(50),
    reminder_date_time TIMESTAMP,
    type               VARCHAR(50),
    isSent             BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (task_id) REFERENCES Task (task_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Notification
(
    notification_id VARCHAR(50) PRIMARY KEY,
    jot_id          VARCHAR(50),
    status          VARCHAR(50),
    message         TEXT,
    date_time       TIMESTAMP,
    type            VARCHAR(50),
    FOREIGN KEY (jot_id) REFERENCES Jot (jot_id) ON DELETE CASCADE ON UPDATE CASCADE
);