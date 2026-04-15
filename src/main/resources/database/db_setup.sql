CREATE DATABASE IF NOT EXISTS webapp_db;
USE webapp_db;

CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    role ENUM('admin', 'user') DEFAULT 'user'
);
