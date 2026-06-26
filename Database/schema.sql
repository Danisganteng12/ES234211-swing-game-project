CREATE DATABASE game_project;
USE game_project;

CREATE TABLE players (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    score INT DEFAULT 0
);

-- Insert 5 sample user untuk testing
INSERT INTO players (username, password, wins, losses, draws, score)
VALUES 
('Danis', '12345', 0, 0, 0, 0),
('Vetto', '12345', 0, 0, 0, 0),
('Axel', '12345', 0, 0, 0, 0),
('Yusuf', '12345', 0, 0, 0, 0),
('Dhira', '12345', 0, 0, 0, 0);
