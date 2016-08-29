CREATE TABLE Conventions
(
	ConventionID	INT NOT NULL AUTO_INCREMENT,
	Name			VARCHAR(256) NOT NULL,
	StartDate		DATE NOT NULL,
	EndDate			DATE NOT NULL,
	StreetAddress	VARCHAR(100),
	State			VARCHAR(20),
	ZipCode			INT,
	Description		VARCHAR(256),
	Map1			BLOB,
	Map2			BLOB,
	Map3			BLOB,
	PRIMARY KEY (ConventionId)
);

CREATE TABLE Rooms
(
	RoomID			INT NOT NULL,
	ConventionID	INT NOT NULL,
	Name			VARCHAR(256) NOT NULL,
	Level			INT,
	XCoordinate		INT,
	YCoordinate		INT,
	PRIMARY KEY (RoomID),
	FOREIGN KEY (ConventionID)
		REFERENCES Conventions(ConventionID)
);

CREATE TABLE Events
(
	EventID		INT NOT NULL AUTO_INCREMENT,
	RoomID		INT NOT NULL,
	Name		VARCHAR(100) NOT NULL,
	StartDate	DATE NOT NULL,
	EndDate		DATE NOT NULL,
	Description	VARCHAR(256),
	PRIMARY KEY (EventId),
	FOREIGN KEY (RoomID)
		REFERENCES Rooms(RoomID)
);

CREATE TABLE Users
(
	UserID		INT NOT NULL AUTO_INCREMENT,
	Username	VARCHAR(25) NOT NULL,
	Password	VARCHAR(256) NOT NULL,
	PRIMARY KEY (UserID)
);

CREATE TABLE Schedules
(
	UserID			INT NOT NULL,
	ConventionID	INT NOT NULL,
	EventID			INT NOT NULL,
	PRIMARY KEY (UserID, ConventionID, EventID),
	FOREIGN KEY (UserID)
		REFERENCES Users(UserID),
	FOREIGN KEY (ConventionID)
		REFERENCES Conventions(ConventionID),
	FOREIGN KEY (EventID)
		REFERENCES Events(EventID)
);

CREATE TABLE ConventionRole
(
	UserID			INT NOT NULL,
	ConventionID	INT NOT NULL,
	Role			VARCHAR(25) NOT NULL,
	PRIMARY KEY (ConventionID, UserID),
	FOREIGN KEY (UserID)
		REFERENCES Users(UserID),
	FOREIGN KEY (ConventionID)
		REFERENCES Conventions(ConventionID)
);